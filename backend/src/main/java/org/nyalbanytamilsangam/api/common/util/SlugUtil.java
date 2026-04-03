package org.nyalbanytamilsangam.api.common.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SlugUtil {
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
    private static final Pattern EDGE_DASHES = Pattern.compile("(^-|-$)");
    private static final Pattern MULTIPLE_DASHES = Pattern.compile("-{2,}");

    private SlugUtil() {}

    public static String toSlug(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return EDGE_DASHES.matcher(
            MULTIPLE_DASHES.matcher(
                NON_LATIN.matcher(
                    WHITESPACE.matcher(normalized.toLowerCase(Locale.ENGLISH))
                        .replaceAll("-")
                ).replaceAll("")
            ).replaceAll("-")
        ).replaceAll("");
    }
}
