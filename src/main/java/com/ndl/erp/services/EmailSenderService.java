package com.ndl.erp.services;

import com.ndl.erp.fe.helpers.BillHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailSenderService {

    @Autowired
    public JavaMailSender emailSender;

    @Value( "${bill.facturacion.email}" )
    private String facturacionEmail;

    @Value( "${bill.recepcion.email}" )
    private String recepcionEmail;

    @Value( "${bill.proceso.target.email}" )
    private String targetProcesoEmail;

    @Async
    public Integer sendMessageWithAttachment(
            String to, String subject, String text, String xmlAttachment, String pdfAttachment, String respuestaXML, String consecutivo, String tipoDocumento) {
        Integer result = 0;
        text = String.format(text);


        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);


            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            //message.setRecipients(Message.RecipientType.BCC,myBccList);
//            helper.setFrom("facturacion@rfslogistica.com");

            String titleAttachment = getTituloAttachment(tipoDocumento);

            FileSystemResource file = null;
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment( titleAttachment + consecutivo + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("enviado_" + consecutivo + ".xml", file);
            }

            if (respuestaXML!=null) {
                file = new FileSystemResource(new File(respuestaXML));
                helper.addAttachment("respuesta_" + consecutivo + ".xml", file);
            }
            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
        // ...
    }

    @Async
    public Integer sendMessageWithAttachment(
            String to, String subject, String text, String xmlAttachment, String pdfAttachment, String consecutivo, String tipoDocumento) {
        text = String.format(text);

        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
//            InternetAddress[] myBccList = InternetAddress.parse("recepcion@rfslogistica.com");

            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);
//            helper.setFrom("facturacion@rfslogistica.com");

            String titleAttachment = getTituloAttachment(tipoDocumento);

            FileSystemResource file = null;
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment( titleAttachment + consecutivo + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("Xml # " + consecutivo + ".xml", file);
            }
            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
        // ...
    }

    private String getTituloAttachment(String tipoDocumento) {


        if (StringUtils.isEmpty(tipoDocumento) || tipoDocumento.equals(BillHelper.FACTURA_ELECTRONICA_TIPO)) {
            return "Factura # ";
        } else if (tipoDocumento.equals(BillHelper.NOTA_CREDITO_TIPO)) {
            return "Nota Credito # ";
        }
        return "Factura # ";

    }

    public String getFacturacionEmail() {
        return facturacionEmail;
    }

    public void setFacturacionEmail(String facturacionEmail) {
        this.facturacionEmail = facturacionEmail;
    }

    public String getRecepcionEmail() {
        return recepcionEmail;
    }

    public void setRecepcionEmail(String recepcionEmail) {
        this.recepcionEmail = recepcionEmail;
    }

    @Async
    public Integer sendMessageProcessResult(String subject, String msg) {
        msg = String.format(msg);

        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse(this.targetProcesoEmail);
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            FileSystemResource file = null;

            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Async
    public Integer sendMessageProcessResultWithAttachments(String subject, String msg, String xmlAttachment, String pdfAttachment, String respuestaXML,String title, String clave) {
        msg = String.format(msg);
        Integer result = 0;
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            InternetAddress[] myToList = InternetAddress.parse(this.targetProcesoEmail);
            InternetAddress[] myBccList = InternetAddress.parse(recepcionEmail);
            //helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.setFrom(facturacionEmail);
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.setRecipients(Message.RecipientType.BCC,myBccList);

            FileSystemResource file = null;
            if (pdfAttachment!=null) {
                file = new FileSystemResource(new File(pdfAttachment));
                helper.addAttachment( title + clave + ".pdf", file);
            }

            if (xmlAttachment!=null) {
                file = new FileSystemResource(new File(xmlAttachment));
                helper.addAttachment("enviado_" + clave + ".xml", file);
            }

            if (respuestaXML!=null) {
                file = new FileSystemResource(new File(respuestaXML));
                helper.addAttachment("respuesta_" + clave + ".xml", file);
            }

            emailSender.send(message);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = 1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JavaMailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


}
