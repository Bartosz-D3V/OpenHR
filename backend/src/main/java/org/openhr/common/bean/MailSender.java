package org.openhr.common.bean;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSender {
  @Value(value = "${spring.mail.host}")
  private String host;

  @Value(value = "${spring.mail.port}")
  private int port;

  @Value(value = "${spring.mail.username}")
  private String username;

  @Value(value = "${spring.mail.password}")
  private String password;

  @Value(value = "${mail.transport.protocol}")
  private String smtp;

  @Value(value = "${mail.smtp.auth}")
  private boolean smtpAuth;

  @Value(value = "${mail.smtp.starttls.enable}")
  private boolean startTls;

  @Bean
  public JavaMailSender getJavaMailSender() {
    final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);

    mailSender.setUsername(username);
    mailSender.setPassword(password);

    final Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", smtp);
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", startTls);

    return mailSender;
  }
}
