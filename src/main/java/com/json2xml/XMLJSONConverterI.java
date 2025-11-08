package com.json2xml;

import com.fasterxml.jackson.databind.JsonNode;
import org.w3c.dom.Document;

public interface XMLJSONConverterI {
    Document convert(JsonNode json) throws Exception;
}