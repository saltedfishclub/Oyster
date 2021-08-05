package cc.sfclub.game.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Get a translation for requested lang.
 */
@ApiStatus.AvailableSince("0.0.1")
@RequiredArgsConstructor
public class Locale {
    @NonNull
    private final Map<String, String> fallback = new HashMap<>();
    private final Map<String, Map<String, String>> locales = new HashMap<>();

    public Map<String, String> getLocale(String locale) {
        return locales.getOrDefault(locale, fallback);
    }

    public void registerLocale(String lang, Map<String, String> locale) throws LocaleExistsException {
        locales.put(lang, locale);
    }

    public static class LocaleExistsException extends Exception {
        public LocaleExistsException(String msg) {
            super(msg + " was already registered.");
        }
    }
}
