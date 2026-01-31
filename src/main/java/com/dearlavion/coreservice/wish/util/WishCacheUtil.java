package com.dearlavion.coreservice.wish.util;

import com.dearlavion.coreservice.wish.Wish;
import com.dearlavion.coreservice.wish.search.WishSearchRequest;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WishCacheUtil {

    // Hash where all Wish objects are stored
    public static final String HASH_KEY = "WISH_HASH_KEY";

    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String STATUS = "status";
    public static final String RATE_TYPE = "ratetype";

    private static final String NULL = "_";
    private static final int MIN_TOKEN_LENGTH = 3;

    public static final Map<String, Function<Wish, String>> INDEX_FIELDS = Map.of(
            //LOCATION, Wish::getLocation,
            STATUS, Wish::getStatus,
            RATE_TYPE, Wish::getRateType
    );

    public static Set<String> extractTokens(String title) {
        return Arrays.stream(title.toLowerCase().split("\\W+"))
                .filter(token -> token.length() >= MIN_TOKEN_LENGTH)
                .limit(5)               // prevent explosion
                .collect(Collectors.toSet());
    }

    /**
     * Normalize any value:
     * - lowercase strings
     * - trim spaces
     * - convert Instant to epoch seconds
     * - convert BigDecimal to plain string
     */
    public static String formatToString(Object value) {
        if (value == null) return NULL;

        if (value instanceof String s) {
            String v = s.trim().toLowerCase();
            return v.isEmpty() ? NULL : v;
        }

        if (value instanceof java.time.Instant i) {
            return String.valueOf(i.getEpochSecond());
        }

        if (value instanceof java.math.BigDecimal bd) {
            return bd.stripTrailingZeros().toPlainString();
        }

        return value.toString();
    }

    /**
     * Generate the key used in SINTER sets
     * Example: location:manila, status:open, ratetype:paid
     */
    public static String setFieldKey(String fieldName, String fieldValue) {
        return fieldName + ":" + fieldValue;
    }

    /**
     * Generate the hash key field for storing the Wish object
     * Must match the ID used in SINTER sets
     */
    public static String hashFieldKey(Wish wish) {
        // Use the Wish ID as the field key
        return String.valueOf(wish.getId());
    }

    public static List<String> buildFilterKey(WishSearchRequest req) {
        // STEP 1: Build Redis keys for each filter
        List<String> filterKeys = new ArrayList<>();

        /*if (req.getLocation() != null && !req.getLocation().isBlank()) {
            filterKeys.add("location:" + req.getLocation().toLowerCase());
        }*/

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            filterKeys.add("status:" + req.getStatus().toUpperCase());
        }

        return filterKeys;
    }
}
