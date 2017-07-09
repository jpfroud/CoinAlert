package fr.coinalert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;

final class CoinWatcher extends Thread {
	private final CurrencyPair pair;
	private final KrakenMarketDataServiceRaw krakenMarketDataService;

	CoinWatcher(CurrencyPair pair, KrakenMarketDataServiceRaw krakenMarketDataService) {
		this.pair = pair;
		this.krakenMarketDataService = krakenMarketDataService;
	}

	@Override
	public void run() {
		try {
			// Get the latest ticker data
			KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(pair);
			BigDecimal base = ticker.getClose().getPrice();
			BigDecimal before = base;
			BigDecimal percent = new BigDecimal(Messages.getString("CoinAlert.percent"));
			BigDecimal plusPercent = BigDecimalUtil.increase(base, percent);
			BigDecimal minusPercent = BigDecimalUtil.decrease(base, percent);
			while (true) {
				// Get the latest ticker data
				ticker = krakenMarketDataService.getKrakenTicker(pair);
				BigDecimal close = ticker.getClose().getPrice();
				if (BigDecimalUtil.greaterThan(close, plusPercent) || BigDecimalUtil.lesserThan(close, minusPercent)) {
					base = close;
					plusPercent = BigDecimalUtil.increase(base, percent);
					minusPercent = BigDecimalUtil.decrease(base, percent);
					BigDecimal variation = BigDecimalUtil.getVariation(before, close);

					// send mail
					sendAlert(pair, before, close, variation);

					before = close;
				}
				Thread.sleep(10000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			MailSender.sendMail("CoinAlert exception", errors.toString(), Messages.getString("CoinAlert.admin.name"),
					Messages.getString("CoinAlert.admin.email"));
			System.exit(-1);
		}
	}

	static void sendAlert(CurrencyPair pair, BigDecimal before, BigDecimal close, BigDecimal variation) {
		String subject = "Update on " + pair;
		String text = pair + " - before = " + before + " - current = " + close;
		String emailText = variation + "%\n" + text;
		String rep1 = Messages.getString("CoinAlert.recipient.name");
		String rep1Adress = Messages.getString("CoinAlert.recipient.email");

		System.out.println(variation);
		System.out.println(text);

		MailSender.sendMail(subject, emailText, rep1, rep1Adress);

	}
}