# 🎯 Jabberpoint - Modern Presentation Software

![JabberPoint Demo](JabberPoint.gif)

A modern, well-architected presentation software built with Java, featuring clean design patterns and comprehensive testing.

## 🚀 Features

- **Command Pattern** - Clean separation of actions and commands
- **Observer Pattern** - Real-time updates and decoupled components
- **Factory Pattern** - Flexible slide item creation
- **Comprehensive Testing** - High code coverage and quality assurance

## 📊 Code Coverage

The link below opens the automatically generated **JaCoCo coverage report**, which includes a detailed table showing:
- Percentage of tested code for each package
- Missed lines, branches, and methods
- Visual indicators of what parts are covered

[🔍 View Interactive JaCoCo Report](https://hakanegeozerol.github.io/Jabberpoint-IT/coverage/index.html)


## 🏗️ Architecture

### Core Patterns

- **Command Pattern**
  - Clean separation of actions
  - Unified handling of keyboard and menu commands
  - Easy extensibility for new commands

- **Observer Pattern**
  - Publisher-Subscriber design
  - Real-time updates
  - Decoupled components

- **Factory Pattern**
  - Flexible slide item creation
  - Easy addition of new item types

### Package Structure

```
jabberpoint/
├── accessor/     # Data access and file handling
├── command/      # Command pattern implementations
├── constants/    # Application constants
├── controller/   # Application controllers
├── observer/     # Observer pattern implementations
├── presentation/ # Core presentation model
├── slideitem/    # Slide item implementations
└── ui/           # User interface components
```

## 🛠️ Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Running the Application
```bash
mvn clean install
java -jar target/jabberpoint-1.0-SNAPSHOT.jar [filename]
```

If no filename is provided, a demo presentation will be shown.

## 🧪 Testing

### Running Tests
The project uses Maven for building and testing. Here's how to run different types of tests:

```bash
# Run all unit tests
mvn test

# Run integration tests with the integration-test profile
mvn verify -P integration-test

# Run acceptance tests with the acceptance-test profile
mvn verify -P acceptance-test

# Run a specific test class
mvn test -Dtest=TestClassName

# Run a specific test method
mvn test -Dtest=TestClassName#testMethodName
```

### Test Coverage Requirements
- Minimum 80% code coverage for core packages
- Headless testing for CI compatibility
- Comprehensive unit and integration tests

## 📄 Documentation

- [Software Quality Advice](SQ_Advice.pdf)

