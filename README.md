# 🔒 SecureCheck - Email Leak Checker

<p align="center">
  <img src="screenshots/logo.png" alt="SecureCheck Logo" width="120"/>
</p>

<p align="center">
  <strong>An Android application to check if your email has been compromised in data breaches</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-blue.svg" alt="Language">
  <img src="https://img.shields.io/badge/Min%20SDK-24-orange.svg" alt="Min SDK">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License">
</p>

---

## 📱 About

SecureCheck is an educational Android application that helps users check if their email addresses have been involved in known data breaches. The app provides information about:
- Number of data breaches found
- Affected websites/platforms
- Dates of breaches
- Types of data that were leaked

**⚠️ Disclaimer:** This app is for educational and awareness purposes only.

---

## ✨ Features

- ✅ Email validation
- ✅ Real-time breach checking via API
- ✅ Clean and intuitive Material Design UI
- ✅ Detailed breach information display
- ✅ Offline-friendly error handling
- ✅ No data storage (privacy-focused)
- ✅ About page with team information

---

## 📸 Screenshots

<p align="center">
  <img src="screenshots/main_screen.png" alt="Main Screen" width="250"/>
  <img src="screenshots/results_screen.png" alt="Results Screen" width="250"/>
  <img src="screenshots/about_screen.png" alt="About Screen" width="250"/>
</p>

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** XML Layouts with Material Design
- **Architecture:** MVVM pattern
- **Networking:** OkHttp
- **JSON Parsing:** Gson
- **Async Operations:** Kotlin Coroutines
- **Min SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 34 (Android 14)

---

## 🚀 Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24 or higher
- Kotlin plugin

### Steps

1. **Clone the repository**
```bash
   git clone https://github.com/YOUR_USERNAME/SecureCheck-Android.git
   cd SecureCheck-Android
```

2. **Open in Android Studio**
   - Open Android Studio
   - Click "Open an Existing Project"
   - Navigate to the cloned folder
   - Click "OK"

3. **Sync Gradle**
   - Wait for Gradle sync to complete
   - Click "Sync Now" if prompted

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button (▶️)

---

## 📦 Project Structure
```bash
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/leakedcheck/
│   │   │   ├── MainActivity.kt          # Main entry point
│   │   │   ├── ResultActivity.kt        # Results display
│   │   │   ├── AboutActivity.kt         # About page
│   │   │   └── BreachData.kt           # Data models
│   │   ├── res/
│   │   │   ├── layout/                  # XML layouts
│   │   │   ├── drawable/                # Icons & backgrounds
│   │   │   ├── values/                  # Strings, colors, themes
│   │   │   └── anim/                    # Animations
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts
```
---

## 🔌 API Integration

The app uses the LeakCheck public API to check for data breaches: GET https://leakcheck.io/api/public?check={email}

**Response Format:**
```json
{
  "success": true,
  "found": 3,
  "fields": ["username", "email", "password"],
  "sources": [
    {
      "name": "Example.com",
      "date": "2020-01"
    }
  ]
}
```

---

## 🎨 UI Components

- **MainActivity:** Email input with validation
- **ResultActivity:** Display breach results with cards
- **AboutActivity:** Team information and credits
- **Custom Drawables:** Material Design elements
- **Animations:** Smooth transitions

---

## 👥 Team

- **Mostafa Ibrahim** - [mostafaebrahim24420042@gmail.com](mailto:mostafaebrahim24420042@gmail.com)
- **Anas Ibrahim** - [anas.safwat88@gmail.com](mailto:anas.safwat88@gmail.com)
- **Rem Khalid** - [reemmoslem34@gmail.com](mailto:reemmoslem34@gmail.com)
- **Fady Ashraf** - [fadyashrafesakandr@gmail.com](mailto:fadyashrafesakandr@gmail.com)

---

## 📄 License

This project is licensed under the MIT License

