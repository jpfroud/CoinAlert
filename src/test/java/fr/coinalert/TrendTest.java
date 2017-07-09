package fr.coinalert;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import fr.coinalert.Trend.State;

public class TrendTest {

	@Test
	public void testStart() {
		Trend trend = new Trend();
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSingleUpdateIncrease() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSingleUpdateDecrease() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSingleUpdateIncreaseDecrease() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testIncreasing() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(0.02));
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
	}

	@Test
	public void testIncreasing2() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
	}

	@Test
	public void testDecreasing() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
	}

	@Test
	public void testDecreasing2() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
	}

	@Test
	public void testSwitchWhileIncreasing() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SELL_TIME);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSwitchWhileIncreasing2() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.SELL_TIME);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSwitchWhileDecreasing() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.SWITCH_UP);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.SWITCH_UP);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.BUY_TIME);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testSwitchWhileDecreasing2() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.SWITCH_UP);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.SWITCH_UP);
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(trend.getState() == State.BUY_TIME);
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(trend.getState() == State.START);
	}

	@Test
	public void testLifecycle() {
		Trend trend = new Trend();
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(1 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(0 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(1 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(2 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(2 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_UP);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(3 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(4 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(4 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_UP);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(4 == trend.getDecreasingCount());
		assertTrue(2 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.BUY_TIME);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(1 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(2 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(3 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(3 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_UP);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(4 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(5 == trend.getDecreasingCount());
		assertTrue(0 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_DECREASE);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(5 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_UP);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(5 == trend.getDecreasingCount());
		assertTrue(2 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.BUY_TIME);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(0 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(0 == trend.getDecreasingCount());
		assertTrue(2 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(1 == trend.getDecreasingCount());
		assertTrue(2 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(0 == trend.getDecreasingCount());
		assertTrue(3 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.CONTINUE_INCREASE);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(1 == trend.getDecreasingCount());
		assertTrue(3 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SWITCH_DOWN);
		
		trend.update(BigDecimal.valueOf(-0.02));
		assertTrue(2 == trend.getDecreasingCount());
		assertTrue(3 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.SELL_TIME);
		
		trend.update(BigDecimal.valueOf(0.02));
		assertTrue(0 == trend.getDecreasingCount());
		assertTrue(1 == trend.getIncreasingCount());
		assertTrue(trend.getState() == State.START);
	}
}
