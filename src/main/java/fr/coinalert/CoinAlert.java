package fr.coinalert;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;

public class CoinAlert {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Use the factory to get Kraken exchange API using default settings
		Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

		// Interested in the public market data feed (no authentication)
		final KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange
				.getMarketDataService();

		new CoinWatcher(CurrencyPair.ETH_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.XRP_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.LTC_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.BTC_EUR, krakenMarketDataService).start();

		while (true) {
			Thread.sleep(10000000000L);
		}
	}
}
