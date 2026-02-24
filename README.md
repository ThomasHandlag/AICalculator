# AiCalculator

A simple and robust Android calculator app built with Kotlin. Supports basic arithmetic operations and maintains calculation history.

## Features

- **Basic Arithmetic** — addition, subtraction, multiplication, division
- Basic arithmetic: addition, subtraction, multiplication, division
- Calculation history with timestamps
- User-friendly interface
- Responsive design for phones and tablets
- Android SDK 31+
- JDK 11

## Installation
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/calculator2.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Run on an emulator or physical device (API 31+).

## Usage

- **Calculator**: Enter numbers and operators using the keypad. Tap `=` to evaluate. Results are saved to history automatically.
- **Unit Converter**: Select a unit type from the top row (Length, Mass, etc.), choose the source and target units, then enter a value using the keyboard.

## Known Issues / Notes

- `CalHistoryData` objects held in `MutableState` cannot be saved through `rememberSaveable` by default, since `Bundle` does not support custom types. A custom `Saver` should be implemented for `CalHistoryData`, or the state should be managed entirely in the `ViewModel` and observed via `collectAsState()`.

## Contributing

1. Fork the repository and create your feature branch:
   ```sh
   git checkout -b feature/your-feature-name
   ```
2. Make changes following Kotlin and Compose best practices.
3. Test on at least one physical or emulated device.
4. Submit a pull request with a clear description of your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
For questions or support, open an issue in this repository.
