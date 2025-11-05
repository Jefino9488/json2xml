# JSON to XML Converter

A Java 7 compatible command-line tool that converts JSON files to XML format with a structured mapping specification.

## Overview

This project provides a simple yet powerful JSON to XML converter that transforms any valid JSON (object or array at top-level) into well-formed, pretty-printed XML using a consistent mapping scheme.

## Features

- ✅ Converts any valid JSON to XML
- ✅ Preserves data types with specific XML tags
- ✅ Handles nested objects and arrays
- ✅ Pretty-printed XML output with proper indentation
- ✅ Java 7 compatible
- ✅ Command-line interface
- ✅ Self-contained executable JAR

## Prerequisites

- Java 7 or newer (JDK configured to compile with source/target 1.7)
- Apache Maven 3+

## Building

To build the project:

```bash
mvn clean package
```

This produces an executable, shaded JAR at:
```
target/json2xml-1.0-SNAPSHOT.jar
```

## Usage

### Command Line

```bash
java -jar target/json2xml-1.0-SNAPSHOT.jar <input.json> <output.xml>
```

The program expects exactly two arguments:
1. The input JSON file path
2. The output XML file path

### Example

```bash
java -jar target/json2xml-1.0-SNAPSHOT.jar test3.json test3.xml
```

## Mapping Specification

The converter recursively transforms JSON to XML using these exact tags:

| JSON Type | XML Mapping | Example |
|-----------|-------------|---------|
| `number` | `<number>VALUE</number>` | `42` → `<number>42</number>` |
| `string` | `<string>VALUE</string>` | `"hello"` → `<string>hello</string>` |
| `boolean` | `<boolean>true|false</boolean>` | `true` → `<boolean>true</boolean>` |
| `null` | `<null/>` (self-closing) | `null` → `<null/>` |
| `array` | `<array>...</array>` | `[1,2,3]` → `<array><number>1</number>...</array>` |
| `object` | `<object>...</object>` | `{"key":"value"}` → `<object><string name="key">value</string></object>` |

### Object Fields

For object fields, the corresponding element includes a `name` attribute with the field key:

```xml
<string name="title">Hello</string>
<object name="meta">...</object>
```

**Note:** Array items never carry a `name` attribute.

## Example Conversion

### Input JSON (`test3.json`)
```json
{
  "simpleString": "Hello World",
  "simpleNumber": 42,
  "simpleBoolean": true,
  "simpleNull": null,
  "emptyObject": {},
  "emptyArray": [],
  "nested": {
    "level1": {
      "level2": {
        "deepValue": "found it"
      }
    }
  },
  "mixedArray": [
    "string",
    123,
    true,
    null,
    {"nested": "object"},
    [1, 2, 3]
  ]
}
```

### Output XML (`test3.xml`)
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<object>
  <string name="simpleString">Hello World</string>
  <number name="simpleNumber">42</number>
  <boolean name="simpleBoolean">true</boolean>
  <null name="simpleNull"/>
  <object name="emptyObject"/>
  <array name="emptyArray"/>
  <object name="nested">
    <object name="level1">
      <object name="level2">
        <string name="deepValue">found it</string>
      </object>
    </object>
  </object>
  <array name="mixedArray">
    <string>string</string>
    <number>123</number>
    <boolean>true</boolean>
    <null/>
    <object>
      <string name="nested">object</string>
    </object>
    <array>
      <number>1</number>
      <number>2</number>
      <number>3</number>
    </array>
  </array>
</object>
```

## Project Structure

```
src/
├── main/java/com/json2xml/
│   ├── Main.java                    # Entry point and CLI handling
│   ├── XMLJSONConverterI.java       # Interface for converter
│   ├── XMLJSONConverter.java        # Core conversion logic
│   └── ConverterFactory.java        # Factory for converter instances
└── test/java/org/json2xml/
    └── AppTest.java                 # Unit tests
```

## Dependencies

- **Jackson Databind 2.9.10** - JSON parsing and processing
  - [GitHub Repository](https://github.com/FasterXML/jackson-databind)
- **JUnit 3.8.1** - Unit testing (test scope)

## Java 7 Compatibility

- The project is configured to compile with `source=1.7` and `target=1.7` via the Maven Compiler Plugin
- No Java 8+ language features are used
- Compatible with Java 7 runtime environments

## Build Configuration

The project uses Maven with the following key plugins:
- **Maven Compiler Plugin** - Java compilation (configured for Java 21 in practice, but compatible with Java 7)
- **Maven Shade Plugin** - Creates executable JAR with all dependencies

## License

This project is open source. Please check the license file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## Issues

If you encounter any issues or have feature requests, please file them in the project's issue tracker.