package fr.coinalert;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.mail.Message;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.simplejavamail.email.Recipient;

final class CoinWatcher extends Thread {
	private final CurrencyPair pair;
	private final MarketDataService marketDataService;

	CoinWatcher(CurrencyPair pair, MarketDataService marketDataService) {
		this.pair = pair;
		this.marketDataService = marketDataService;
	}

	@Override
	public void run() {
		// Get the latest ticker data
		boolean initOK = false;
		Ticker ticker = null;
		do {
			try {
				ticker = marketDataService.getTicker(pair);
				initOK = true;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		} while (!initOK);
		BigDecimal base = ticker.getLast();
		BigDecimal before = base;
		BigDecimal percent = new BigDecimal(Messages.getString("CoinAlert.percent"));
		BigDecimal plusPercent = BigDecimalUtil.increase(base, percent);
		BigDecimal minusPercent = BigDecimalUtil.decrease(base, percent);
		Trend trend = new Trend();
		while (true) {
			try {
				// Get the latest ticker data
				ticker = marketDataService.getTicker(pair);
				BigDecimal close = ticker.getLast();
				System.out.println("Last " + pair + " : " + close);
				if (BigDecimalUtil.greaterThan(close, plusPercent) || BigDecimalUtil.lesserThan(close, minusPercent)) {
					BigDecimal variation = BigDecimalUtil.getVariation(before, close);
					trend.update(variation);
					base = close;

					plusPercent = BigDecimalUtil.increase(base, trend.getTopPercent());
					minusPercent = BigDecimalUtil.decrease(base, trend.getBottomPercent());

					// send mail
					sendAlert(pair, before, close, variation, trend);

					before = close;
				}
				Thread.sleep(5000);

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				MailSender.sendMail("CoinAlert exception", errors.toString(),
						new Recipient(Messages.getString("CoinAlert.admin.name"),
								Messages.getString("CoinAlert.admin.email"), Message.RecipientType.TO));
				// wait 5 sec
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
		}
	}

	static void sendAlert(CurrencyPair pair, BigDecimal before, BigDecimal close, BigDecimal variation, Trend trend) {
		String subject = "Update on " + pair;
		String text = pair + " - before = " + before + " - current = " + close;
		String emailText = variation + "\n" + text + "\n" + trend.getMessage();
		System.out.println(variation);
		System.out.println(text);
		System.out.println(trend.getMessage());

		MailSender.sendMail(subject, emailText);

	}
}