# JSON to XML Converter

A command-line tool that converts JSON files to XML format with a structured mapping specification.

## Features

- Converts any valid JSON to XML
- Preserves data types with specific XML tags
- Handles nested objects and arrays
- Pretty-printed XML output with proper indentation
- Command-line interface
- Self-contained executable JAR

## Download

Download the latest pre-built JAR from [GitHub Releases](https://github.com/yourname/json2xml/releases).

## Prerequisites

- Java 7 or newer
- Apache Maven 3+ (for building from source)

## Building

Build the project:

```bash
mvn clean package
```

This produces an executable JAR at:
```
target/json2xml-2.0.jar
```

## Usage

Run the converter with two arguments:

```bash
java -jar target/json2xml-2.0.jar <input.json> <output.xml>
```

Example:

```bash
java -jar target/json2xml-2.0.jar test3.json test3.xml
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
└── main/java/com/json2xml/
    ├── Main.java              # Entry point and CLI handling
    └── XMLJSONConverter.java  # Core conversion logic
```

## Dependencies

- Jackson Databind 2.9.10 - JSON parsing and processing
