## Custom Weather Library

### Overview
Created a custom weather utilities library to handle weather data formatting and condition mapping, showcasing modular code organization and reusability.

### Features
- Temperature unit conversion and formatting
- Weather condition mapping and icons
- Date formatting utilities for weather timestamps

### Implementation
- Published to Maven Local repository
- Modular design with clear separation of concerns
- Comprehensive unit test coverage
- Well-documented API

### Usage
```kotlin
// Add dependency in build.gradle
implementation 'com.itstanany:weather-utils:1.0.0'

// Use in code
val formattedTemp = TemperatureUtils.formatTemperature(23.5, "°C")
val date = DateUtils.parseDate("2024-01-05")
```

The library encapsulates common weather-related functionality, making it reusable across different modules and potentially different applications. All components are thoroughly tested and follow clean architecture principles.
