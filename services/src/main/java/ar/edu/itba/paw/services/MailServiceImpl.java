package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private static final String EMAIL_ENCODING = StandardCharsets.UTF_8.displayName();

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    @Qualifier("applicationBasePath")
    private String applicationBasePath;

    private static final String MOOVIFY_EMAIL_ADDRESS = "moovifyCo@gmail.com";

    // Send image reference

    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
    // final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
    // message.addInline(imageResourceName, imageSource, imageContentType);
    // message.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

    @Override
    public void sendEmail(String destination, String subject, String template, Map<String, Object> variables) throws MessagingException {

        // Prepare the evaluation context
        final Context context = new Context();

        context.setVariable("applicationBasePath", applicationBasePath);
        context.setVariables(variables);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = emailSender.createMimeMessage();

        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, EMAIL_ENCODING);

        message.setSubject(subject);
        message.setFrom(MOOVIFY_EMAIL_ADDRESS);
        message.setTo(destination);

        message.setText(templateEngine.process(template, context), true);

        emailSender.send(mimeMessage);
    }

}
