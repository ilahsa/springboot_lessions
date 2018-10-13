package com.alta.hello.tools.core;

import java.math.BigDecimal;

/**
 * 权重简单算法.
 *
 * @author GD
 */
public class Weight {

    /**
     * 权重开关.
     *
     * @return
     */
    public static boolean switchWeight(String value) {
        Float percent = Float.parseFloat(value);
        if (percent == null || percent <= 0 || randomWith2PointNum() > percent) {
            return false;
        }
        return true;
    }

    private static float randomWith2PointNum() {
        float max = 100.00F;
        float min = 0.00F;
        BigDecimal bd = new BigDecimal(Math.random() * (max - min) + min);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
