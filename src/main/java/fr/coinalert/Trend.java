package fr.coinalert;

import java.math.BigDecimal;

public class Trend {

	public enum State {
		START, CONTINUE_INCREASE, CONTINUE_DECREASE, SWITCH_UP, SWITCH_DOWN, BUY_TIME, SELL_TIME;
	}

	private int increasingCount = 0;
	private int decreasingCount = 0;
	private State state = State.START;

	// not proud of this one
	public void update(BigDecimal variation) {
		// increasing
		if (variation.signum() == 1) {
			increasingCount++;
			if (decreasingCount <= 1) {
				decreasingCount = 0;
			} else {
				// switch
				if (state == State.CONTINUE_DECREASE) {
					state = State.SWITCH_UP;
				}
			}
			if (state == State.SWITCH_UP && increasingCount == 2) {
				state = State.BUY_TIME;
			} else if (increasingCount > 1) {
				if (state == State.SELL_TIME || state == State.BUY_TIME) {
					decreasingCount = 0;
					increasingCount = 1;
					state = State.START;
				} else {
					state = State.CONTINUE_INCREASE;
				}
			}
		}
		// decreasing
		else if (variation.signum() == -1) {
			decreasingCount++;
			if (increasingCount <= 1) {
				increasingCount = 0;
			} else {
				// switch
				if (state == State.CONTINUE_INCREASE) {
					state = State.SWITCH_DOWN;
				}
			}
			if (state == State.SWITCH_DOWN && decreasingCount == 2) {
				state = State.SELL_TIME;
			} else if (decreasingCount > 1) {
				if (state == State.SELL_TIME || state == State.BUY_TIME) {
					decreasingCount = 1;
					increasingCount = 0;
					state = State.START;
				} else {
					state = State.CONTINUE_DECREASE;
				}
			}
		}
	}

	public BigDecimal getTopPercent() {
		BigDecimal percent = new BigDecimal(Messages.getString("CoinAlert.percent"));
		BigDecimal halfPercent = percent.divide(BigDecimal.valueOf(2));
		if (state == State.CONTINUE_DECREASE || state == State.SWITCH_UP) {
			return halfPercent;
		} else {
			return percent;
		}
	}

	public BigDecimal getBottomPercent() {
		BigDecimal percent = new BigDecimal(Messages.getString("CoinAlert.percent"));
		BigDecimal halfPercent = percent.divide(BigDecimal.valueOf(2));
		if (state == State.CONTINUE_INCREASE || state == State.SWITCH_DOWN) {
			return halfPercent;
		} else {
			return percent;
		}
	}

	public String getMessage() {
		StringBuilder message = new StringBuilder();

		switch (state) {
		case BUY_TIME:
			message.append("Increasing for the second time after ").append(decreasingCount).append(" decreases");
			message.append("\nTime to buy?");
			break;
		case CONTINUE_DECREASE:
			message.append("Decrease count: ").append(decreasingCount);
			break;
		case CONTINUE_INCREASE:
			message.append("Increase count: ").append(increasingCount);
			break;
		case SELL_TIME:
			message.append("Decreasing for the second time after ").append(increasingCount).append(" increases");
			message.append("\nTime to sell?");
			break;
		case START:
			break;
		case SWITCH_DOWN:
			message.append("Decreasing for the first time after ").append(increasingCount).append(" increases");
			break;
		case SWITCH_UP:
			message.append("Increasing for the first time after ").append(decreasingCount).append(" decreases");
			break;
		default:
			break;

		}

		return message.toString();
	}

	public State getState() {
		return state;
	}

}
