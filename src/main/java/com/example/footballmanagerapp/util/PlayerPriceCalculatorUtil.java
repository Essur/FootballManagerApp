package com.example.footballmanagerapp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerPriceCalculatorUtil {
    private PlayerPriceCalculatorUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static BigDecimal calculateTotalPrice(int experience, int age, int commissionPercent) {
        BigDecimal playerPrice = BigDecimal.valueOf(experience)
                .multiply(BigDecimal.valueOf(100_000))
                .divide(BigDecimal.valueOf(age), 2, RoundingMode.HALF_UP);

        BigDecimal commissionMultiplier = BigDecimal.valueOf(commissionPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        return playerPrice.multiply(commissionMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
}
