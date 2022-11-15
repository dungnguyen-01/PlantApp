package com.kinto.kintobackend.config;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/*
Fahim created at 6/5/2021
*/
@Service
@Log4j2
public class AmazonS3Client {

    private AmazonS3 s3client;

    @PostConstruct
    private void initializeAmazon() {
        this.s3client =
                AmazonS3ClientBuilder
                        .standard()
                        .withRegion(Regions.AP_SOUTHEAST_1)
                        .withCredentials(getAwsCredentialPovider())
                        .build();
    }

    private AWSCredentialsProvider getAwsCredentialPovider() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials("AKIAUPLY6W2MPSOIINMY",
                        "veUzPN05NTG7SVfLsbyJaMS/e1NxDSiTt7giuUb5");
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    public URL uploadFile(String bucketName, MultipartFile file) throws IOException {
        //rename file
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString().replace("-", "");
        fileName = fileName +"."+ fileExtension;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        PutObjectResult result = s3client.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata));
        URL s3Url = s3client.getUrl(bucketName, fileName);
        //System.out.println("S3 url is " + s3Url.toExternalForm());
        // Set file access to private
        s3client.setObjectAcl(bucketName, fileName, CannedAccessControlList.BucketOwnerFullControl);
        log.info("File Uploaded successfully");

        return s3Url;
    }

    public String getPreSignedDownloadUrl(String bucketName, String fileName) {
        if (s3client.doesObjectExist(bucketName,fileName) == true){
            try {
                java.util.Date expiration = new java.util.Date();
                long expTimeMillis = expiration.getTime();
                expTimeMillis += 1000 * 60 * 60; //1 HR expiration time
                expiration.setTime(expTimeMillis);
                // Generate the presigned URL.
                //System.out.println("Generating pre-signed URL.");
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest(bucketName, fileName)
                                .withMethod(HttpMethod.GET)
                                .withExpiration(expiration);
                URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
                return url.toString();
            } catch (AmazonServiceException e) {
                e.printStackTrace();
            } catch (SdkClientException e) {
                e.printStackTrace();
            }
            return bucketName;
        }else {
            return "Filename not found.....";
        }
    }
    public void deleteFile(String bucketName, String fileName) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        log.info(fileName + " deleted");
    }
}
