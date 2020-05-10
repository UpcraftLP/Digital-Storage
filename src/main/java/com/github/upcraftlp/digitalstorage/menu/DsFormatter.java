package com.github.upcraftlp.digitalstorage.menu;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Locale;

public enum DsFormatter {
    ONE("one", 1L, false),
    THOUSAND("thousand", 1_000L, true),
    TEN_THOUSAND("ten_thousand", 10_000L, false),
    MILLION("million", 1_000_000L, true),
    TEN_MILLION("ten_million", 10_000_000L, false),
    BILLION("billion", 1_000_000_000L, true),
    TEN_BILLION("ten_billion", 10_000_000_000L, false),
    TRILLION("trillion", 1_000_000_000_000L, true),
    TEN_TRILLION("ten_trillion", 10_000_000_000_000L, false),
    QUADRILLION("quadrillion", 1_000_000_000_000_000L, true),
    TEN_QUADRILLION("ten_quadrillion", 10_000_000_000_000_000L, false),
    QUINTILLION("quintillion", 1_000_000_000_000_000_000L, true); //largest value possible due to constraints of Long.MAX_VALUE

    private final String translationKey;
    private final double value;
    private final boolean decimal;

    DsFormatter(String name, long minValue, boolean decimal) {
        this.translationKey = Util.createTranslationKey("format", new Identifier(DigitalStorage.NAMESPACE, name));
        this.value = minValue;
        this.decimal = decimal;
    }

    private static final String MAX = Util.createTranslationKey("format", new Identifier(DigitalStorage.NAMESPACE, "max"));

    public static String format(long value) {
        if(value < 0 || value == Long.MAX_VALUE) {
            return MAX;
        }
        DsFormatter formatter = ONE;
        for(DsFormatter f : values()) {
            if(f.value < value) {
                formatter = f;
            }
        }
        // need to manually format decimals because TranslationStorage replaces all special formatting with %s
        // need to specify locale to always get dot as decimal separator
        String valueString = String.format(Locale.ROOT, formatter.decimal ? "%.1f" : "%.0f", value / formatter.value);
        return I18n.translate(formatter.translationKey, valueString);
    }
}
