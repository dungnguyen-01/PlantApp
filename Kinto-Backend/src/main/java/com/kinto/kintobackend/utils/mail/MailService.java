package com.kinto.kintobackend.utils.mail;

import javax.mail.MessagingException;

public interface MailService {

    void  send(Mail mail) throws MessagingException;

    void addToQueue(Mail mail);

    void sendPasswordToken(String token, String email);

}
