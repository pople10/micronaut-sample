/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils;

import io.micronaut.email.javamail.sender.SessionProvider;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import com.sample.utils.enumerations.EmailHolder;
import com.sample.utils.logging.LoggerPrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@RequiredArgsConstructor
public class EmailService {
    private static final String DEFAULT_FROM = "No Reply";
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final SessionProvider sessionProvider;
    private final VelocityViewsRenderer velocityViewsRenderer;

    private final LocalizationUtils localizationUtils;

    @Async
    public void sendMailOrDie(
            String subject,
            String body,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies)
            throws MessagingException, UnsupportedEncodingException {
        if (recipients == null) recipients = new ArrayList<>();
        if (bcc == null) bcc = new ArrayList<>();
        if (cc == null) cc = new ArrayList<>();
        if (replies == null) replies = new ArrayList<>();
        Session session = sessionProvider.session();
        Object debugString = session.getProperties().get("debug");
        if (Boolean.valueOf(debugString.toString())) {
            session.setDebug(true);
            session.setDebugOut(new PrintStream(new LoggerPrintStream(logger)));
        }
        Message msg = new MimeMessage(session);
        subject = localizationUtils.getMessage(subject);
        msg.addRecipients(Message.RecipientType.TO, buildAddresses(recipients));
        msg.addRecipients(Message.RecipientType.BCC, buildAddresses(bcc));
        msg.addRecipients(Message.RecipientType.CC, buildAddresses(cc));
        msg.setReplyTo(buildAddresses(replies));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");
        msg.setFrom(new InternetAddress(session.getProperty("mail.user"), from));
        Transport.send(msg);
        logger.debug("Email successfully sent");
    }

    @Async
    public void sendMailTemplateOrDie(
            String subject,
            String templateName,
            Map<String, String> data,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies)
            throws Throwable {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            data.put(entry.getKey(), localizationUtils.getMessage(entry.getValue()));
        }
        sendMailOrDie(
                subject,
                velocityViewsRenderer.render(templateName, data),
                from,
                recipients,
                bcc,
                cc,
                replies);
    }

    @Async
    public void sendMail(
            String subject,
            String body,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies) {
        try {
            sendMailOrDie(subject, body, from, recipients, bcc, cc, replies);
        } catch (Throwable e) {
            logger.warn("Could not send mail: {}", e.getMessage());
        }
    }

    @Async
    public void sendMailTemplate(
            String subject,
            String templateName,
            Map<String, String> data,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies) {
        try {
            sendMailTemplateOrDie(subject, templateName, data, from, recipients, bcc, cc, replies);
        } catch (Throwable e) {
            logger.warn("Could not send mail: {}", e.getMessage());
        }
    }

    @Async
    public void sendMailTemplate(
            String subject,
            String templateName,
            Map<String, String> data,
            List<String> recipients) {
        sendMailTemplate(subject, templateName, data, DEFAULT_FROM, recipients, null, null, null);
    }

    @Async
    public void sendMailTemplate(
            EmailHolder emailHolder, Map<String, String> data, List<String> recipients) {
        sendMailTemplate(
                emailHolder.getSubject(),
                emailHolder.getTemplateName(),
                data,
                DEFAULT_FROM,
                recipients,
                null,
                null,
                null);
    }

    @Async
    public void sendMailTemplate(
            EmailHolder emailHolder, Map<String, String> data, String recipient) {
        sendMailTemplate(
                emailHolder.getSubject(),
                emailHolder.getTemplateName(),
                data,
                DEFAULT_FROM,
                Collections.singletonList(recipient),
                null,
                null,
                null);
    }

    private Address[] buildAddresses(List<String> list) {
        List<InternetAddress> out =
                list.stream()
                        .map(
                                e -> {
                                    try {
                                        return new InternetAddress(e);
                                    } catch (AddressException ex) {
                                        return new InternetAddress();
                                    }
                                })
                        .collect(Collectors.toList());
        Address[] addresses = new Address[out.size()];
        int i = 0;
        for (InternetAddress address : out) {
            addresses[i] = address;
            i++;
        }
        return addresses;
    }

    public void sendMailOrDieNonAsync(
            String subject,
            String body,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies)
            throws MessagingException, UnsupportedEncodingException {
        if (recipients == null) recipients = new ArrayList<>();
        if (bcc == null) bcc = new ArrayList<>();
        if (cc == null) cc = new ArrayList<>();
        if (replies == null) replies = new ArrayList<>();
        Session session = sessionProvider.session();
        Object debugString = session.getProperties().get("debug");
        if (Boolean.valueOf(debugString.toString())) {
            session.setDebug(true);
            session.setDebugOut(new PrintStream(new LoggerPrintStream(logger)));
        }
        Message msg = new MimeMessage(session);
        subject = localizationUtils.getMessage(subject);
        msg.addRecipients(Message.RecipientType.TO, buildAddresses(recipients));
        msg.addRecipients(Message.RecipientType.BCC, buildAddresses(bcc));
        msg.addRecipients(Message.RecipientType.CC, buildAddresses(cc));
        msg.setReplyTo(buildAddresses(replies));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");
        msg.setFrom(new InternetAddress(session.getProperty("mail.user"), from));
        Transport.send(msg);
        logger.debug("Email successfully sent");
    }

    public void sendMailTemplateOrDieNonAsync(
            String subject,
            String templateName,
            Map<String, String> data,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies)
            throws Throwable {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            data.put(entry.getKey(), localizationUtils.getMessage(entry.getValue()));
        }
        sendMailOrDieNonAsync(
                subject,
                velocityViewsRenderer.render(templateName, data),
                from,
                recipients,
                bcc,
                cc,
                replies);
    }

    public void sendMailNonAsync(
            String subject,
            String body,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies) {
        try {
            sendMailOrDieNonAsync(subject, body, from, recipients, bcc, cc, replies);
        } catch (Throwable e) {
            logger.warn("Could not send mail: {}", e.getMessage());
        }
    }

    public void sendMailTemplateNonAsync(
            String subject,
            String templateName,
            Map<String, String> data,
            String from,
            List<String> recipients,
            List<String> bcc,
            List<String> cc,
            List<String> replies) {
        try {
            sendMailTemplateOrDieNonAsync(
                    subject, templateName, data, from, recipients, bcc, cc, replies);
        } catch (Throwable e) {
            logger.warn("Could not send mail: {}", e.getMessage());
        }
    }

    public void sendMailTemplateNonAsync(
            String subject,
            String templateName,
            Map<String, String> data,
            List<String> recipients) {
        sendMailTemplateNonAsync(
                subject, templateName, data, DEFAULT_FROM, recipients, null, null, null);
    }

    public void sendMailTemplateNonAsync(
            EmailHolder emailHolder, Map<String, String> data, List<String> recipients) {
        sendMailTemplateNonAsync(
                emailHolder.getSubject(),
                emailHolder.getTemplateName(),
                data,
                DEFAULT_FROM,
                recipients,
                null,
                null,
                null);
    }
}
