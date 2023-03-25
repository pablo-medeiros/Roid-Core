package com.roidmc.core.util.java;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NumberFormatter {

    private static final NavigableMap<Long, String> KMBSuffixes = new TreeMap<>();
    static {
        KMBSuffixes.put(1_000L, "k");
        KMBSuffixes.put(1_000_000L, "M");
        KMBSuffixes.put(1_000_000_000L, "G");
        KMBSuffixes.put(1_000_000_000_000L, "T");
        KMBSuffixes.put(1_000_000_000_000_000L, "P");
        KMBSuffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String kmb(double value) {
        if (value == Long.MIN_VALUE) return kmb(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + kmb(-value);
        if (value < 1000) return Double.toString(value);
        Map.Entry<Long, String> e = KMBSuffixes.floorEntry((long) value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = (long) (value / (divideBy / 10));
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
