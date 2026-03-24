# Android Base Project

Android Kotlin base project using MVVM with a practical clean-architecture split:

- `presentation`: Compose UI, navigation, and `ViewModel`s
- `domain`: models, repository contracts, and use cases
- `data`: Retrofit API, Room cache, mappers, and repository implementations
- `di` / `core`: Hilt modules plus shared database/network utilities

## Included stack

- Kotlin + Android Gradle Plugin
- Jetpack Compose + Material 3
- MVVM + Clean Architecture
- Hilt for dependency injection
- Retrofit + OkHttp logging
- Room
- Coroutines + Flow
- Navigation Compose