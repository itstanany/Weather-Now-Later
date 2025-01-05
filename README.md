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
| **`app`**               | `features`, `domain`, `data`, `core`, `weather-utils`, `:features:city-input`, `:features:current-weather`, `:features:forecast`, `:features:no-internet`, `:features:splash` | Main entry point. Wires modules together, handles DI, navigation, and app lifecycle. |
| **`core`**              |  `domain`                                   | Provides shared utilities (e.g., `NetworkUtils`)|
| **`domain`**            | None                                        | Contains business logic, use cases, and repository interfaces.              |
| **`data`**              | `domain`, `core`                            | Implements repository interfaces, handles local (DataStore) and remote (Retrofit) data sources. |
| **`weather-utils`**     | None                                        | Contains reusable weather-specific utilities (e.g., formatting, conversions). |
| **`features:city-input`** | `domain`, `core`, `weather-utils`         | Manages the city input screen. Implements UI and ViewModel logic (MVVM).    |
| **`features:current-weather`** | `domain`, `core`, `weather-utils`    | Displays current weather data. Implements UI and ViewModel logic (MVVM).    |
| **`features:forecast`** | `domain`, `core`, `weather-utils`           | Displays 7-day forecast. Implements UI and ViewModel logic (MVI).    |
| **`features:no-internet`** | None                                     | Display Error Screen with retry action.                          |
| **`features:splash`** | `domain`,                                     | Splash Screen to either to city input or current weather screen. Implements UI and ViewModel logic (MVI).    |


## Weather Utils Library

A modular library providing essential weather data handling utilities.

* Published to local Maven, review [commit](https://github.com/itstanany/Weather-Now-Later/commit/50acc6c58379194bc0466faf1dae11b9f2fdeecc) of publishing implementation

### Features
- Temperature formatting and unit conversion
- Date parsing and formatting for weather data

### Integration
```gradle
implementation 'com.itstanany:weather-utils:1.0.0'
```

### Example Usage
```kotlin
// Format temperature
val temp = TemperatureUtils.formatTemperature(23.5, "°C")

// Parse dates
val date = DateUtils.parseDate("2024-01-05")

```

Built with comprehensive test coverage and clean architecture principles, this library simplifies weather data handling across the application.


## CI/CD Pipeline

Implemented continuous integration and deployment using GitHub Actions, featuring:

### Key Features
- Code linting with ktlint
- Unit test execution with coverage reporting
- Automated APK generation (debug/release)

### Why GitHub Actions?
- Seamless GitHub repository integration
- Zero configuration overhead
- Free tier for public repositories
- Built-in Android support

### Workflow Triggers
- On push to main branch
- On pull request creation
- Manual workflow dispatch

This automation ensures consistent code quality and reliable builds across the development lifecycle.


### Core Components
- Repository pattern for data management
- Custom mappers for data transformation
- Sealed classes for Weather Condition
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
