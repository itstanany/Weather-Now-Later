# Weather Now & Later

A modern Android weather application showcasing clean architecture and best practices in Android development.

## Features
- Current weather conditions
- 7-day weather forecast
- City search functionality
- Last searched city persistence

## Technical Stack & Architecture

### Clean Architecture
- **Domain Layer**: Business logic and entities
- **Data Layer**: Repositories and data sources
- **Presentation Layer**: ViewModels and UI components

### Key Technologies
- Kotlin Coroutines & Flow for reactive programming
- Hilt for dependency injection
- StateFlow for UI state management
- MockK & JUnit4 for testing

## Project Structure

### Modularization

| **Module**              | **Depends On**                              | **Purpose**                                                                 |
|--------------------------|---------------------------------------------|-----------------------------------------------------------------------------|
| **`app`**               | `features`, `domain`, `data`, `core`, `weather-utils` | Main entry point. Wires modules together, handles DI, navigation, and app lifecycle. |
| **`features:city-input`** | `domain`, `core`, `weather-utils`           | Manages the city input screen. Implements UI and ViewModel logic (MVVM).    |
| **`features:current-weather`** | `domain`, `core`, `weather-utils`           | Displays current weather data. Implements UI and ViewModel logic (MVVM).    |
| **`features:forecast`** | `domain`, `core`, `weather-utils`           | Displays 7-day forecast. Implements UI and ViewModel logic (MVVM).    |
| **`features:no-internet`** | None                                     | Display Error Screen with retry action.                          |
| **`features:splash`** | `domain`,                                     | Splash Screen to either to city input or current weather screen. Implements UI and ViewModel logic (MVI).    |
| **`domain`**            | None                                        | Contains business logic, use cases, and repository interfaces.              |
| **`data`**              | `domain`, `core`                            | Implements repository interfaces, handles local (Room/DataStore) and remote (Retrofit) data sources. |
| **`core`**              |  `domain`                         | Provides shared utilities (e.g., `NetworkUtils`, `Logger`) and base classes.|
| **`weather-utils`**     | None                                        | Contains reusable weather-specific utilities (e.g., formatting, conversions, icon mapping). |


### Core Components
- Repository pattern for data management
- Custom mappers for data transformation
- Sealed classes for error handling
- Immutable state management

## Engineering Practices

### Testing Strategy
- Comprehensive unit test coverage
- Isolated testing with mocked dependencies
- Edge case handling
- Coroutine testing utilities

### Code Quality
- SOLID principles implementation
- Clear dependency boundaries
- Consistent code style
- Descriptive naming conventions

### Error Handling
- Structured exception handling
- Graceful error recovery
- User-friendly error messages
- Network error management

## Getting Started
[Installation and setup instructions]

## Contributing
[Contribution guidelines]

## License
[License information]

Citations:
[1] https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/2499385/ad672fb4-13e3-4bbc-8688-bea98315ca29/paste.txt
