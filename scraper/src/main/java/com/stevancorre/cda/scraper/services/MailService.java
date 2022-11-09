package com.stevancorre.cda.scraper.services;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmailAttachment;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;
import sibModel.SendSmtpEmail;

import java.util.ArrayList;
import java.util.List;

public final class MailService {
    private final TransactionalEmailsApi api;
    private final SendSmtpEmailSender sender;

    public MailService(final String apiKey, final String senderEmail, final String senderName) {
        final ApiClient defaultClient = Configuration.getDefaultApiClient();
        this.api = new TransactionalEmailsApi();

        final ApiKeyAuth keyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        keyAuth.setApiKey(apiKey);

        this.sender = new SendSmtpEmailSender();
        this.sender.setEmail(senderEmail);
        this.sender.setName(senderName);
    }

    public CreateSmtpEmail send(final String to, final String subject, final String htmlContent, final String attachmentContent) throws ApiException {
        final SendSmtpEmailTo receiver = new SendSmtpEmailTo();
        receiver.setEmail(to);

        final SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
        attachment.setName("result.txt");
        attachment.setContent(attachmentContent.getBytes());

        final SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(new ArrayList<>() {{
            add(receiver);
        }});
        sendSmtpEmail.setSubject(subject);
        sendSmtpEmail.setHtmlContent(htmlContent);
        sendSmtpEmail.setAttachment(List.of(attachment));

        return api.sendTransacEmail(sendSmtpEmail);
    }
}
