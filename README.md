# 🏋️ Momentum

Momentum is a helper application where you can browse various workouts and explore how they are performed, complete with persistent cloud storage and a smooth authentication experience.

---

## 📱 Features

- 🔐 **Firebase Authentication** with Email & Password
- 🏋️ **Workout Browsing** through an intuitive interface
- ☁️ **Firestore-backed Data Persistence** for reliable access across devices
- ⚡ **Jetpack Compose UI** for modern, declarative UIs
- 🔁 **Local Caching** to support offline mode and faster access
- 💉 **Hilt for Dependency Injection** across all modules
- 🌐 **Retrofit Integration** for future API-based expansion
- 🧭 **Navigation Component** for modular and scalable routing
- 🧱 **Clean Modular Architecture** ensuring maintainability
- 🔄 **Reactive UI** using StateFlow and event-based MVI patterns

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **Architecture:** MVI (Model-View-Intent), Clean Architecture (with Layer & Feature Modularization)
- **UI:** Jetpack Compose
- **DI Framework:** Hilt
- **Networking:** Retrofit (future support)
- **Authentication & Storage:** Firebase (Auth + Firestore)
- **Navigation:** Jetpack Navigation (Compose Navigation)

---

## 📂 Project Structure

The project uses both **feature** and **layered** modularization to promote scalability and separation of concerns.

```
📁 app               # Main launcher module, handles DI setup and navigation
📁 core              # Contains shared code like UI themes, utils, and common components
📁 auth              # Manages user authentication logic and UI (Login, SignUp)
📁 workout           # Handles all workout-related features and presentation
📁 build-logic       # Custom Gradle plugins, version catalogs, and build conventions
```

Each module follows Clean Architecture principles, typically divided into:

- `data`: Data sources (remote/local), models, and repositories
- `domain`: Use-cases and business logic
- `presentation`: UI logic, screens, and view models (with MVI)

---

## 🔐 Authentication

- **Method:** Firebase Email & Password Authentication
- **Flow:** Sign Up, Login, Session Management, Logout
- **Security:** All user data is securely stored and validated using Firebase Authentication

---

## ☁️ Data Storage

- **Remote:** Firebase Firestore (Stores user data, workouts)
- **Local:** In-memory caching mechanism for offline availability (can be upgraded to Room)
- **Realtime Sync:** Firestore ensures changes reflect near-instantly across devices

---

## 📆 Modules Breakdown

### `auth`

- Firebase Auth integration (Email/Password)
- Input validation, ViewModels, and MVI intents for Login & SignUp

### `workout`

- Workout models and browsing logic
- Firestore integration for reading workout content
- Displays movement names, descriptions, categories (future: videos, gifs)

### `core`

- Shared resources and utilities (e.g., colors, themes, typography)
- Common Compose components (buttons, headers, etc.)

### `build-logic`

- Centralized Gradle configuration
- Convention plugins to keep the build system DRY and scalable

### `app`

- App-wide navigation setup
- Initializes DI and application themes

---

## 🧪 Testing

> *Testing implementation is ongoing. Suggestions for tools below:*

- **Unit Testing:** JUnit 5, Kotlinx Coroutines Test
- **UI Testing:** Jetpack Compose Test, Espresso (fallback)
- **Mocking:** MockK
- **CI/CD (optional):** GitHub Actions for automated builds and tests

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Kotlin 1.9+
- Android SDK 34+
- Firebase Project set up with Firestore and Authentication enabled

### Setup Steps

1. **Clone the Repository**
```bash
git clone https://github.com/yourusername/momentum.git
cd momentum
```

2. **Open in Android Studio**

3. **Place your `google-services.json` file** under the `app/` directory

4. **Sync Gradle and Run** on a device/emulator

5. **Optional:** Configure Firebase rules and Firestore indexes for production use

---

## 📦 Deployment

- APK will be available in `releases/` folder once finalized
- No Play Store deployment yet

---

## 🌟 Future Enhancements

- 💪 Personalized workout plans
- 📆 Workout history with calendar integration
- 📊 BMI & fitness calculators
- 🔔 Notifications & reminders
- 🧠 ML-based recommendations

---

## 🧑‍💻 Author

Vyceros,
Gublera

## 📝 License

License license license

---

 *Momentum is built with modularity and developer happiness in mind. Fork it, improve it, or contribute to make it even better!* 💥
