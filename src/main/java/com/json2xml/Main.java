package com.json2xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            printUsage();
            System.exit(1);
            return;
        }
        File inFile = new File(args[0]);
        File outFile = new File(args[1]);
        if (!inFile.isFile()) {
            System.err.println("Input file does not exist: " + inFile.getPath());
            printUsage();
            System.exit(2);
            return;
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inFile);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(in);
            XMLJSONConverter converter = new XMLJSONConverter();
            Document doc = converter.convert(root);
            out = new FileOutputStream(outFile);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            try {
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            } catch (IllegalArgumentException e) {
            }
            transformer.transform(new DOMSource(doc), new StreamResult(out));
        } catch (Exception e) {
            System.err.println("Conversion failed: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(3);
        } finally {
            try { if (in != null) in.close(); } catch (Exception ignore) {}
            try { if (out != null) out.close(); } catch (Exception ignore) {}
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar json2xml-2.0.jar <input.json> <output.xml>");
    }
}