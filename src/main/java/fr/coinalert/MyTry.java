package fr.coinalert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;

public class MyTry {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Use the factory to get Kraken exchange API using default settings
		Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

		// Interested in the public market data feed (no authentication)
		KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange
				.getMarketDataService();

		// Get the latest ticker data showing BTC to EUR
		KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(CurrencyPair.ETH_EUR);
		BigDecimal base = ticker.getClose().getPrice();
		BigDecimal before = base;
		BigDecimal percent = BigDecimal.valueOf(0);
		BigDecimal plus3percent = increase(base, percent);
		BigDecimal minus3percent = decrease(base, percent);

		while (true) {
			// Get the latest ticker data showing BTC to EUR
			ticker = krakenMarketDataService.getKrakenTicker(CurrencyPair.ETH_EUR);
			BigDecimal close = ticker.getClose().getPrice();
			if (greaterThan(close, plus3percent)) {
				base = close;
				plus3percent = increase(base, percent);
				minus3percent = decrease(base, percent);
				// +3% increase
				BigDecimal increase = getVariation(before, close);
				System.out.println("+" + increase + "% !!");
				System.out.println("before = " + before + " - current = " + close);
				before = close;
			} else if (lesserThan(close, minus3percent)) {
				base = close;
				plus3percent = increase(base, percent);
				minus3percent = decrease(base, percent);
				// -3% decrease
				BigDecimal decrease = getVariation(before, close);
				System.out.println(decrease + "% !!");
				System.out.println("before = " + before + " - current = " + close);
				before = close;
			}
			Thread.sleep(5000);
		}
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
