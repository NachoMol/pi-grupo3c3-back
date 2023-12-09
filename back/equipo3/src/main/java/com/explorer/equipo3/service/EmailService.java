package com.explorer.equipo3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Date;

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
        mimeMessageHelper.setSubject("Welcome to Explorer App");

        Context context = new Context();
        context.setVariable("nameUser", nameUser);
        context.setVariable("addressee", addressee);
        String contentMail = templateEngine.process("Welcome", context);

        mimeMessageHelper.setText(contentMail,true);

        javaMailSender.send(mimeMessage);
    }

    public void sendMailConfirmationReservation(String email, String nameUser, String nameProduct,LocalDate checkin, LocalDate checkout)throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Booking Confirmation");

        Context context = new Context();
        context.setVariable("nameUser",nameUser);
        context.setVariable("nameProduct",nameProduct);
        context.setVariable("checkin",checkin);
        context.setVariable("checkout",checkout);
        String contentMail = templateEngine.process("ConfirmationReserva", context);

        mimeMessageHelper.setText(contentMail,true);

        javaMailSender.send(mimeMessage);
    }

    public void reSendMailConfirmationReservation(String email, String nameUser, String nameProduct, LocalDate checkin, LocalDate checkout)throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Booking Confirmation");

        Context context = new Context();
        context.setVariable("nameUser",nameUser);
        context.setVariable("nameProduct",nameProduct);
        context.setVariable("checkin",checkin);
        context.setVariable("checkout",checkout);
        String contentMail = templateEngine.process("ConfirmationReservationUpdate", context);

        mimeMessageHelper.setText(contentMail,true);

        javaMailSender.send(mimeMessage);
    }

}
