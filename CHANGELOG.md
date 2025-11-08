# Changelog

All notable changes to this project will be documented in this file.

## [2.0.0] - 2025

### Changed
- Updated version to 2.0
- Refactored codebase for simplicity
- Removed factory pattern and interface abstractions
- Instantiate converter directly in Main.java
- Cleaned up class-level comments and unused code
- Updated README with clearer documentation

### Removed
- Removed ConverterFactory.java
- Removed XMLJSONConverterI.java interface
- Removed IDE configuration files from repository
- Removed sample JSON files from repository
- Removed unused test dependencies (JUnit)

## [1.0.0] - 2025

### Added
- Initial release
- JSON to XML conversion with type preservation
- Support for all JSON data types (string, number, boolean, null, object, array)
- Pretty-printed XML output
- Command-line interface
- GitHub Actions workflow for automated releases
- Comprehensive README documentation
- Self-contained executable JAR with shaded dependencies

### Features
- Java 7 compatibility
- Handles nested objects and arrays
- Preserves field names as XML attributes
- Proper XML formatting with indentation
