package fr.coinalert;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

public class MailSender {

	static void sendMail(String subject, String emailText, Recipient... recipients) {
		if (null == recipients || recipients.length == 0) {
			recipients = collectRecipients();
		}
		
		String host = Messages.getString("CoinAlert.mail.host");
		String username = Messages.getString("CoinAlert.mail.username");
		String password = Messages.getString("CoinAlert.mail.password");
		Email email = new Email();
		email.setFromAddress(Messages.getString("CoinAlert.sender.name"), username);
		email.setReplyToAddress("Noreply", "noreply@null.com");
		for (Recipient recipient : recipients) {
			email.addRecipient(recipient.getName(), recipient.getAddress(), recipient.getType());
		}
		email.setSubject(subject);
		email.setText(emailText);

		new Mailer(new ServerConfig(host, 25, username, password), TransportStrategy.SMTP_PLAIN).sendMail(email);
	}
	
	private static Recipient[] collectRecipients() {
		String[] addresses = Messages.getString("CoinAlert.recipient.email").split(",");
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (String address : addresses) {
			recipients.add(new Recipient(address, address, Message.RecipientType.TO));
		}
		return recipients.toArray(new Recipient[recipients.size()]);
	}
}
