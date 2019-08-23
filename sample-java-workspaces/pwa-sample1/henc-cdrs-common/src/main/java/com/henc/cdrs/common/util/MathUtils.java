package com.henc.cdrs.common.util;

/**
 * @author YongSang
 */
public class MathUtils {
    public static float rint(float value) {
        double floor = Math.floor(value);
        double fraction = value - floor;
        if (fraction < 1 && fraction > 0) {
            fraction = 0.5;
        } else if (fraction < 0) {
            fraction = 0;
        }
        float result = (float) (floor + fraction);
        return (result > 0) ? (float) result : 0;
    }
}