package com.alta.hello.tools.core;

/**
 * 地理GEO工具.
 *
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class Geos {
    private static final float CIRCUMFERENCE = 40044000;

    private static double square(double x) {
        return x * x;
    }

    /**
     * 计算两组坐标的GEO距离.
     *
     * @param latSourceF 坐标1纬度
     * @param lonSourceF 坐标1经度
     * @param latCheckF 坐标2纬度
     * @param lonCheckF 坐标2经度
     * @return 距离
     */
    public static float distance(float latSourceF, float lonSourceF, float latCheckF, float lonCheckF) {
        double latSource = Math.toRadians(latSourceF);
        double lonSource = Math.toRadians(lonSourceF);
        double latCheck = Math.toRadians(latCheckF);
        double lonCheck = Math.toRadians(lonCheckF);
        double d = square(Math.sin((latSource - latCheck) / 2)) + Math.cos(latSource)
                * Math.cos(latCheck) * square(Math.sin((lonSource - lonCheck) / 2));
        d = 2 * Math.asin(Math.sqrt(d));
        return new Float(d * CIRCUMFERENCE / (2 * Math.PI)).floatValue();
    }
}
