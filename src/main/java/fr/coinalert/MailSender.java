package fr.coinalert;

import javax.mail.Message;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

public class MailSender {

	static void sendMail(String subject, String emailText, String recipientName, String recipientAddress) {
		String host = Messages.getString("CoinAlert.mail.host");
		String username = Messages.getString("CoinAlert.mail.username");
		String password = Messages.getString("CoinAlert.mail.password");
		Email email = new Email();
		email.setFromAddress(Messages.getString("CoinAlert.sender.name"), username);
		email.setReplyToAddress("Noreply", "noreply@null.com");
		email.addRecipient(recipientName, recipientAddress, Message.RecipientType.TO);
		email.setSubject(subject);
		email.setText(emailText);

		new Mailer(new ServerConfig(host, 25, username, password), TransportStrategy.SMTP_PLAIN).sendMail(email);
	}
}