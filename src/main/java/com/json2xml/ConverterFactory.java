package com.json2xml;

/**
 * Factory class to provide instances of XMLJSONConverterI.
 */
public final class ConverterFactory {
    private ConverterFactory() {
    }

    public static XMLJSONConverterI newConverter() {
        return new XMLJSONConverter();
    }
}