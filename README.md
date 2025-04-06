# Jabberpoint - Improved Architecture

This is an improved version of the JabberPoint presentation software with significant architectural enhancements.

## Key Architectural Improvements

1. **Command Pattern**
   - Added a Command interface with concrete command implementations
   - Separated menu and keyboard actions into their own command classes
   - Makes it easier to extend with new commands
   - Unified handling of actions across keyboard and menu interfaces

2. **Observer Pattern**
   - Implemented Publisher-Subscriber design pattern
   - Presentation acts as a Publisher that notifies Subscribers of changes
   - SlideViewerComponent subscribes to receive updates
   - Allows for better decoupling of components and real-time updates

3. **Factory Pattern**
   - Uses a Factory for SlideItem creation
   - Concrete factories for TextItem and BitmapItem
   - Makes it easy to add new types of slide items

## Running the Application

```
java jabberpoint.JabberPoint [filename]
```

If no filename is provided, a demo presentation will be shown.

## Classes and Packages

### Command Pattern Classes
- `Command` (interface)
- `PresentationCommand` (abstract)
- `UICommand` (abstract)
- `NextSlideCommand`, `PreviousSlideCommand`, etc.

### Observer Pattern Classes
- `Publisher` (interface)
- `Subscriber` (interface)
- `Event` (enum)

### Factory Pattern Classes
- `SlideItemFactory` (interface)
- `TextItemFactory`, `BitmapItemFactory`

### Controllers
- `KeyController` - Handles keyboard input
- `MenuController` - Manages menu actions

### View Components
- `SlideViewerFrame` - Main window
- `SlideViewerComponent` - Renders slides

### Model Classes
- `Presentation` - Manages slides and presentation state
- `Slide` - Individual slide with content
- `SlideItem` - Base class for slide content
- `TextItem`, `BitmapItem` - Concrete slide content types

## Future Improvements
- File chooser dialog for opening and saving presentations
- More slide transition effects
- Better image handling and caching
- Consider implementing true MVC pattern in future versions

## Testing

### Running Tests
The application uses JUnit 5 and Mockito for testing. To run all tests:

```bash
mvn test
```

For integration tests only:
```bash
mvn verify -P integration-test
```

For acceptance tests only:
```bash
mvn verify -P acceptance-test
```

### Headless Testing
All tests are configured to run in headless mode, making them suitable for CI environments. This is achieved through:
- System property `-Djava.awt.headless=true`
- Mocking GUI components instead of creating actual windows
- Using PowerMock for static method mocking

### Code Coverage Requirements
The project enforces a minimum of 80% code coverage for the following key packages:
- `jabberpoint.command` - Command pattern implementations
- `jabberpoint.controller` - Application controllers
- `jabberpoint.accessor` - Data access classes
- `jabberpoint.presentation` - Core presentation model

See the `TEST_GUIDELINES.md` file for detailed best practices for writing tests.
