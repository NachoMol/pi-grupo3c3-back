package com.explorer.equipo3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendMailConfirmation(String addressee, String nameUser )throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        mimeMessageHelper.setTo(addressee);
        mimeMessageHelper.setSubject("Confirmación de Registro");

        Context context = new Context();
        context.setVariable("nameUser", nameUser);
        context.setVariable("addressee", addressee);
        String contentMail = templateEngine.process("Welcome", context);

        mimeMessageHelper.setText(contentMail,true);

        javaMailSender.send(mimeMessage);
    }
}
