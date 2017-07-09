package fr.coinalert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

	static BigDecimal getVariation(BigDecimal before, BigDecimal after) {
		return (after.subtract(before)).divide(before, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
	}

	static boolean lesserThan(BigDecimal value, BigDecimal compared) {
		return -1 == value.compareTo(compared);
	}

	static boolean greaterThan(BigDecimal value, BigDecimal compared) {
		return 1 == value.compareTo(compared);
	}

	static BigDecimal decrease(BigDecimal base, BigDecimal percent) {
		return base.subtract(base.multiply(percent));
	}

	static BigDecimal increase(BigDecimal base, BigDecimal percent) {
		return base.add(base.multiply(percent));
	}
}
