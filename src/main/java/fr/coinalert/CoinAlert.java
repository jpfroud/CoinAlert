package fr.coinalert;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.v1.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinAlert {

	public static void main(String[] args) throws IOException, InterruptedException {
		Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
		Exchange bittrexExchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
		Exchange poloniexExchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());

		// Interested in the public market data feed (no authentication)
		final MarketDataService krakenMarketDataService = krakenExchange.getMarketDataService();
		final MarketDataService bittrexMarketDataService = bittrexExchange.getMarketDataService();
		final MarketDataService poloniexMarketDataService = poloniexExchange.getMarketDataService();

		new CoinWatcher(CurrencyPair.ETH_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.XRP_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.LTC_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.BTC_EUR, krakenMarketDataService).start();
		new CoinWatcher(CurrencyPair.XEM_BTC, bittrexMarketDataService).start();
		new CoinWatcher(CurrencyPair.XMR_EUR, krakenMarketDataService).start();
		new CoinWatcher(new CurrencyPair("EOS/EUR"), krakenMarketDataService).start();
		new CoinWatcher(new CurrencyPair("BTS/BTC"), poloniexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("STRAT/BTC"), poloniexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("ZEC/EUR"), krakenMarketDataService).start();
		new CoinWatcher(new CurrencyPair("WAVES/BTC"), bittrexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("ANS/BTC"), bittrexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("STEEM/BTC"), bittrexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("GNT/BTC"), bittrexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("GNO/BTC"), bittrexMarketDataService).start();
		new CoinWatcher(new CurrencyPair("BCN/BTC"), poloniexMarketDataService).start();

		while (true) {
			Thread.sleep(10000000000L);
		}
	}
}
