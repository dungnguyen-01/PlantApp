package com.kinto.kintobackend.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseError {
    private Date timeStamp = new Date(System.currentTimeMillis());
    private Integer status;
    private String message;
    private String error;
    private String path;


    public ResponseError(Integer status, String message, String error,String path) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }
}
