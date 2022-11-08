package com.stevancorre.cda.scraper.services;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.util.ArrayList;

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

    public CreateSmtpEmail send(final String to, final String subject, final String htmlContent) throws ApiException {
        final SendSmtpEmailTo receiver = new SendSmtpEmailTo();
        receiver.setEmail(to);

        final SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(new ArrayList<>() {{
            add(receiver);
        }});
        sendSmtpEmail.setSubject(subject);
        sendSmtpEmail.setHtmlContent(htmlContent);

        return api.sendTransacEmail(sendSmtpEmail);
    }
}
