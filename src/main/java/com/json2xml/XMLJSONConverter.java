package com.json2xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLJSONConverter implements XMLJSONConverterI {

    public XMLJSONConverter() {
    }

    public Document convert(JsonNode json) throws Exception {
        if (json == null || (!json.isObject() && !json.isArray())) {
            throw new IllegalArgumentException("JSON must be an object or an array");
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element root;
        if (json.isObject()) {
            root = doc.createElement("object");
            doc.appendChild(root);
            writeObject((ObjectNode) json, root, doc);
        } else {
            root = doc.createElement("array");
            doc.appendChild(root);
            writeArray((ArrayNode) json, root, doc);
        }
        return doc;
    }

    private void writeObject(ObjectNode objectNode, Element parent, Document doc) {
        java.util.Iterator<java.util.Map.Entry<String, JsonNode>> fields = objectNode.fields();
        while (fields.hasNext()) {
            java.util.Map.Entry<String, JsonNode> e = fields.next();
            String name = e.getKey();
            JsonNode value = e.getValue();
            Element child = createElementForNode(value, doc);
            child.setAttribute("name", name);
            parent.appendChild(child);
            appendContent(value, child, doc);
        }
    }

    private void writeArray(ArrayNode arrayNode, Element parent, Document doc) {
        for (int i = 0; i < arrayNode.size(); i++) {
            JsonNode value = arrayNode.get(i);
            Element child = createElementForNode(value, doc);
            parent.appendChild(child);
            appendContent(value, child, doc);
        }
    }

    private Element createElementForNode(JsonNode node, Document doc) {
        if (node == null || node.isNull()) {
            return doc.createElement("null");
        } else if (node.isTextual()) {
            return doc.createElement("string");
        } else if (node.isNumber()) {
            return doc.createElement("number");
        } else if (node.isBoolean()) {
            return doc.createElement("boolean");
        } else if (node.isArray()) {
            return doc.createElement("array");
        } else if (node.isObject()) {
            return doc.createElement("object");
        } else {
            // Fallback: treat as string
            return doc.createElement("string");
        }
    }

    private void appendContent(JsonNode node, Element element, Document doc) {
        if (node == null || node.isNull()) {
            return;
        } else if (node.isTextual()) {
            element.appendChild(doc.createTextNode(node.textValue()));
        } else if (node.isNumber()) {
            element.appendChild(doc.createTextNode(node.numberValue().toString()));
        } else if (node.isBoolean()) {
            element.appendChild(doc.createTextNode(node.booleanValue() ? "true" : "false"));
        } else if (node.isArray()) {
            writeArray((ArrayNode) node, element, doc);
        } else if (node.isObject()) {
            writeObject((ObjectNode) node, element, doc);
        }
    }
}