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

/**
 * The mail service, used to send email with sendinblue
 */
public final class MailService {
    private final TransactionalEmailsApi api;
    private final SendSmtpEmailSender sender;

    /**
     * Constructor
     *
     * @param apiKey      The Sendinblue API key
     * @param senderEmail The sender's email
     * @param senderName  The sender's name
     */
    public MailService(final String apiKey, final String senderEmail, final String senderName) {
        final ApiClient defaultClient = Configuration.getDefaultApiClient();
        this.api = new TransactionalEmailsApi();

        final ApiKeyAuth keyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        keyAuth.setApiKey(apiKey);

        this.sender = new SendSmtpEmailSender();
        this.sender.setEmail(senderEmail);
        this.sender.setName(senderName);
    }

    /**
     * Send an email with a subject, content and attachment to a specific email
     *
     * @param to                The receiver
     * @param subject           Subject of the email
     * @param htmlContent       Content of the email
     * @param attachmentContent Content of the attachment
     * @return The email
     */
    public CreateSmtpEmail send(final String to, final String subject, final String htmlContent, final String attachmentContent) throws ApiException {
        // prepare the receiver
        final SendSmtpEmailTo receiver = new SendSmtpEmailTo();
        receiver.setEmail(to);

        // prepare tha attachment
        final SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
        attachment.setName("result.txt");
        attachment.setContent(attachmentContent.getBytes());

        // prepare the email
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
