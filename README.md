# Tally Counter (Meditation Tally)

A minimal, offline-first Android app for counting meditation repetitions, breaths, mantras, or rounds. Designed for calm, distraction-free use with reliable local persistence and per-day history.

## Features

- **Home Screen** – Large tappable circular counter, progress arc toward daily target, undo and reset
- **Daily History** – View past daily counts grouped by date
- **Settings** – Configure daily target (21, 54, 108), haptic feedback, and theme (light/dark)
- **Offline-first** – All data stored locally; no internet required
- **Auto-save** – Count persists after every tap, undo, or reset
- **Date awareness** – New day starts a fresh count; previous day remains in history

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM
- **Storage:** Room (daily records), DataStore (preferences)
- **Navigation:** Navigation Compose

## Requirements

- Android Studio Ladybug (2024.2.1) or newer
- JDK 17
- Android SDK 34
- minSdk 26

## Build & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/TallyCounter.git
   cd TallyCounter
   ```

2. Open the project in Android Studio.

3. Sync Gradle and run on a device or emulator:
   ```bash
   ./gradlew assembleDebug
   ```
   Or use **Run** (▶) in Android Studio.

## Project Structure

```
app/src/main/java/com/meditation/tally/
├── MainActivity.kt
├── MeditationTallyApplication.kt
├── di/               # App container, dependency setup
├── data/
│   ├── local/          # Room database, DAO, entities
│   └── preferences/    # DataStore preferences
└── ui/
    ├── home/           # Main counter screen
    ├── history/        # Daily history list
    ├── settings/      # App settings
    ├── navigation/    # Nav graph
    └── theme/         # Compose theme, colors, typography
```

## License

MIT
