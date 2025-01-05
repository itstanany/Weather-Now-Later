# Weather Now & Later

A modern Android weather application showcasing clean architecture and best practices in Android development.

<p align="center">
  <img src="docs/ezgif.com-animated-gif-maker.gif" width="42%" height="70%"/>
  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
  <img src="docs/ezgif.com-animated-gif-maker -dark.gif" width="42%" height="70%"/>
</p>


## Features
- Current weather conditions
- 7-day weather forecast
- City search functionality
- Last searched city persistence
- Dark Mode Support

## Technical Stack & Architecture

### Clean Architecture
- **Domain Layer**: Business logic and entities(Use case - repository Interface, domain entity)
- **Data Layer**: Repositories implementation and data sources (local and remote)
- **Presentation Layer**: MVVM and MVI presentation architecture patterns (Uni Directional Data Flow)

| Architecture Pattern | Key Benefits | Why Used |
|---------------------|--------------|-----------|
| MVVM | - Clear separation of concerns<br>- Lifecycle-aware components<br>- Easy unit testing<br>| - Enables isolated testing of ViewModels<br>- Handles Android lifecycle automatically<br>- Provides clean data flow patterns<br>- Works well with Kotlin coroutines |
| MVI | - Immutable state management<br>- Single source of truth<br>- Predictable data flow<br>- Clear user intent handling<br>- Better debugging | - Prevents state inconsistencies<br>- Makes testing more reliable<br>- Simplifies state management<br>- Improves error tracking |



## Object-Oriented Programming Implementation

The project implements core OOP principles through a clean and maintainable architecture:

### Encapsulation
- ViewModels encapsulate UI state and business logic
- Repositories encapsulate data operations and transformations
- Custom mappers handle data conversion between layers
- Private properties and methods control access to internal state

### Abstraction
- Interfaces define contracts between layers (NetworkConfig, WeatherRepository)
- Abstract base classes provide common functionality (BaseUseCase)
- Use cases abstract business logic implementation details

### Inheritance
- ViewModels inherit from Android ViewModel class.
- Base classes like BaseUseCase are reused across multiple use cases in the domain module.
This allows shared logic (e.g., coroutine dispatching) to be inherited and reused, reducing duplication.

### Polymorphism
- Weather conditions implemented as sealed classes
- Dependency injection (e.g., through Dagger Hilt) allows polymorphic behavior: The domain layer depends on abstractions (e.g., WeatherRepository), while different implementations (e.g., WeatherRepositoryImpl) can be injected at runtime. This ensures flexibility and extensibility, enabling easy swapping of implementations.



## SOLID Principles Implementation

The project demonstrates adherence to SOLID principles through practical implementations:

### Single Responsibility Principle (SRP)
- WeatherMapper handles only data mapping
- Each ViewModel manages single screen state and logic

### Open/Closed Principle (OCP)
```kotlin
sealed class WeatherCondition {
    data class Sunny(val description: String) : WeatherCondition()
    data class Cloudy(val description: String) : WeatherCondition()
    data class Rainy(val description: String) : WeatherCondition()
}
```
Weather conditions are extensible without modifying existing code.

### Liskov Substitution Principle (LSP)
```kotlin
interface WeatherRepository {
    suspend fun getCurrentWeather(city: City): DailyWeather
    suspend fun getForecast(city: City): List<DailyWeather>
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository
```
Repository implementations can be substituted without affecting functionality.

### Interface Segregation Principle (ISP)
```kotlin
interface NetworkConfig {
    val forecastApiBaseUrl: String
    val cityApiBaseUrl: String
}
```
Interfaces are kept focused and minimal.

### Dependency Inversion Principle (DIP)
```kotlin
interface WeatherRemoteDataSource {
    suspend fun getForecast(latitude: Float, longitude: Float): WeatherForecastResponse
}

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRemoteDataSource
```
High-level modules depend on abstractions rather than concrete implementations.

These implementations ensure the codebase remains maintainable, testable, and scalable while reducing coupling between components[2][3].



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
