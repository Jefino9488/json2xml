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

/**
 * Main entry point. Accepts exactly two arguments: input.json output.xml
 */
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

            XMLJSONConverterI converter = ConverterFactory.newConverter();
            Document doc = converter.convert(root);

            // Pretty-print XML to output file
            out = new FileOutputStream(outFile);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Xalan-specific property for indentation amount (works with default JDK transformer)
            try {
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            } catch (IllegalArgumentException e) {
                // ignore if transformer doesn't support this property
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
        System.out.println("Usage: java -jar target/json2xml-1.0-SNAPSHOT.jar <input.json> <output.xml>");
    }
}