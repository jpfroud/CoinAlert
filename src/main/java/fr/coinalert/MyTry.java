package fr.coinalert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.mail.Message;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

public class MyTry {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Use the factory to get Kraken exchange API using default settings
		Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

		// Interested in the public market data feed (no authentication)
		KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange
				.getMarketDataService();

		// Get the latest ticker data showing BTC to EUR
		CurrencyPair pair = CurrencyPair.ETH_EUR;
		KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(pair);
		BigDecimal base = ticker.getClose().getPrice();
		BigDecimal before = base;
		BigDecimal percent = BigDecimal.valueOf(0.03);
		BigDecimal plusPercent = increase(base, percent);
		BigDecimal minusPercent = decrease(base, percent);

		while (true) {
			// Get the latest ticker data showing BTC to EUR
			ticker = krakenMarketDataService.getKrakenTicker(pair);
			BigDecimal close = ticker.getClose().getPrice();
			if (greaterThan(close, plusPercent) || lesserThan(close, minusPercent)) {
				base = close;
				plusPercent = increase(base, percent);
				minusPercent = decrease(base, percent);
				BigDecimal variation = getVariation(before, close);

				// send mail
				sendMail(pair, before, close, variation);

				before = close;
			}
			Thread.sleep(10000);
		}
	}

	private static void sendMail(CurrencyPair pair, BigDecimal before, BigDecimal close, BigDecimal variation) {
		String subject = "Update on " + pair; 
		String text = "before = " + before + " - current = " + close;
		String host = Messages.getString("MyTry.mail.host"); 
		String username = Messages.getString("MyTry.mail.username"); 
		String password = Messages.getString("MyTry.mail.password"); 
		String rep1 = Messages.getString("MyTry.recipient.name"); 
		String rep1Adress = Messages.getString("MyTry.recipient.email"); 

		System.out.println(variation);
		System.out.println(text);

		Email email = new Email();
		email.setFromAddress(Messages.getString("MyTry.sender.name"), username); 
		email.setReplyToAddress("Noreply", "noreply@null.com");
		email.addRecipient(rep1, rep1Adress, Message.RecipientType.TO);
		email.setSubject(subject);
		email.setText(variation + "%\n" + text);

		new Mailer(new ServerConfig(host, 25, username, password), TransportStrategy.SMTP_PLAIN).sendMail(email);

	}

	private static BigDecimal getVariation(BigDecimal before, BigDecimal after) {
		return (after.subtract(before)).divide(before, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
	}

	private static boolean lesserThan(BigDecimal value, BigDecimal compared) {
		return -1 == value.compareTo(compared);
	}

	private static boolean greaterThan(BigDecimal value, BigDecimal compared) {
		return 1 == value.compareTo(compared);
	}

	private static BigDecimal decrease(BigDecimal base, BigDecimal percent) {
		return base.subtract(base.multiply(percent));
	}

	private static BigDecimal increase(BigDecimal base, BigDecimal percent) {
		return base.add(base.multiply(percent));
	}
}
