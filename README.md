# Calculator App

A modern, beautiful Android Calculator app built with Jetpack Compose, featuring dark/light theme support and a clean Material Design 3 interface.

## Features

- ✨ **Modern UI**: Built with Jetpack Compose and Material Design 3
- 🌓 **Theme Support**: Dark and Light theme with user preference persistence
- 🎨 **Splash Screen**: Elegant splash screen on app launch
- 🧮 **Basic Operations**: Addition, Subtraction, Multiplication, and Division
- 💾 **State Management**: MVVM architecture with ViewModel and StateFlow
- 📱 **Responsive Design**: Optimized for various screen sizes
- 🔄 **Expression Display**: Shows the current expression being calculated
- ⌫ **Delete Function**: Delete last entered digit
- 🧹 **Clear Function**: Reset calculator to initial state

## Screenshots

*Add screenshots of your app here*

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **State Management**: StateFlow, ViewModel
- **Data Persistence**: DataStore Preferences (for theme preference)
- **Material Design**: Material 3
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

## Project Structure

```
CalculatorApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/subodh/calculatorapp/
│   │   │   │   ├── MainActivity.kt          # Main activity
│   │   │   │   ├── MainScreen.kt           # Main screen composable
│   │   │   │   ├── calculator/
│   │   │   │   │   ├── CalculatorScreen.kt  # Calculator UI
│   │   │   │   │   └── CalculatorViewModel.kt # Calculator logic
│   │   │   │   ├── splash/
│   │   │   │   │   └── SplashScreen.kt      # Splash screen
│   │   │   │   ├── theme/
│   │   │   │   │   └── ThemeViewModel.kt    # Theme management
│   │   │   │   └── ui/theme/                # Theme definitions
│   │   │   └── res/                         # Resources
│   │   └── test/                            # Unit tests
│   └── build.gradle.kts
└── build.gradle.kts
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with API level 24 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Subodh2020/CalculatorApp.git
```

2. Open the project in Android Studio

3. Sync Gradle files and wait for dependencies to download

4. Run the app on an emulator or physical device

### Building the App

```bash
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

## Usage

1. **Launch the app**: The app starts with a splash screen
2. **Enter numbers**: Tap number buttons (0-9) to input numbers
3. **Perform operations**: Tap operation buttons (+, −, ×, ÷) to perform calculations
4. **View result**: Tap the equals (=) button to see the result
5. **Delete**: Tap the delete (⌫) button to remove the last digit
6. **Clear**: Tap the clear (C) button to reset the calculator
7. **Toggle theme**: Use the theme toggle switch to switch between dark and light modes

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model**: Data classes and business logic
- **View**: Jetpack Compose UI components
- **ViewModel**: Manages UI-related data and business logic, survives configuration changes

### Key Components

- **CalculatorViewModel**: Handles calculator operations and state management
- **ThemeViewModel**: Manages theme preferences using DataStore
- **CalculatorScreen**: Main calculator UI composable
- **SplashScreen**: App launch screen

## Dependencies

- Jetpack Compose BOM
- Material 3
- Lifecycle ViewModel Compose
- DataStore Preferences
- Core KTX
- Activity Compose

## Future Enhancements

- [ ] Scientific calculator mode
- [ ] History of calculations
- [ ] Memory functions (M+, M-, MR, MC)
- [ ] Percentage calculations
- [ ] Parentheses support
- [ ] Landscape mode optimization
- [ ] Unit tests
- [ ] UI tests

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

**Subodh Kumar**

- Email: subodhg@theextraaedge.com
- GitHub: [@Subodh2020](https://github.com/Subodh2020)

## Acknowledgments

- Built with [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Material Design 3 components
- Android Jetpack libraries

---

⭐ If you like this project, please give it a star!


