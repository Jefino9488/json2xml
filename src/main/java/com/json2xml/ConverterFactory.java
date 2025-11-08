package com.json2xml;
public final class ConverterFactory {
    private ConverterFactory() {
    }

    public static XMLJSONConverterI newConverter() {
        return new XMLJSONConverter();
    }
}