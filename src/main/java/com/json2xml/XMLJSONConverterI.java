package com.json2xml;

import com.fasterxml.jackson.databind.JsonNode;
import org.w3c.dom.Document;

/**
 * Interface for converting a JSON tree into an XML Document following the required mapping.
 *
 * Mapping rules:
 * - number -> <number>VALUE</number>
 * - string -> <string>VALUE</string>
 * - boolean -> <boolean>true|false</boolean>
 * - null -> <null/>
 * - array -> <array> ...items... </array>
 * - object -> <object ...> ...fields... </object>
 *
 * Additional rules:
 * - Only elements that represent object fields may carry name attributes: name="key".
 * - Array elements never carry name attributes (the <array> tag never has a name attribute).
 */
public interface XMLJSONConverterI {
    /**
     * Converts the given JsonNode (must be an object or array at top level) to a DOM Document.
     * @param json the parsed JSON root
     * @return XML Document representing the JSON according to mapping
     * @throws Exception if conversion fails or top-level is not object/array
     */
    Document convert(JsonNode json) throws Exception;
}