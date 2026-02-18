# 🛡️ KeepSafe

**KeepSafe** is a personal family member management system built with **Kotlin Multiplatform** and **Compose Multiplatform**.

It helps you securely organize and manage family records using a shared UI across platforms, with Firebase support on Android.

> ⚠️ Intended for **personal use**.
> Anyone interested can fork the project, configure Firebase, and compile their own version.

---

## ✨ Features

* 📱 **Android** - full functionality with Firebase
* 🖥️ **Desktop (JVM)** - shared UI preview
* 🍎 **iOS** - entry point included (requires macOS)
* 🔄 Shared Compose UI & business logic
* ☁️ Firebase integration (Android)

---

## ⚠️ Platform Support

| Platform  | Status         | Notes                  |
| --------- | -------------- | ---------------------- |
| 📱 Android | ✅ Full support | Firebase SDK available |
| 🖥️ Desktop | 🟡 UI only      | Firebase not supported |
| 🍎 iOS     | ⚠️ Untested     | Requires macOS & Xcode |

> iOS builds cannot be tested on Windows environments.

---

## 🛠️ Tech Stack

* 🧩 Kotlin Multiplatform
* 🎨 Compose Multiplatform
* ⚙️ Gradle 9
* ☁️ Firebase (Android)

---

## 📂 Project Structure

### Root (Gradle 9 Layout)

```
root
├── androidApp      📱 Android application
├── composeApp      🔄 Shared multiplatform module
├── iosApp          🍎 iOS entry point
├── build.gradle.kts
└── settings.gradle.kts
```

---

### Shared Module Structure

```
composeApp/src
├── commonMain    🔄 Shared UI & logic
├── androidMain   📱 Android-specific code
├── iosMain       🍎 iOS-specific code
└── desktopMain   🖥️ Desktop-specific code
```

### Source Set Purpose

* 🔄 **commonMain** → shared UI, models, business logic
* 📱 **androidMain** → Firebase & Android integrations
* 🍎 **iosMain** → Apple APIs & platform code
* 🖥️ **desktopMain** → desktop-only implementations

---

## 🚀 Build & Run

### 📱 Android

**Windows**

```bash
.\gradlew.bat :androidApp:assembleDebug
```

**macOS/Linux**

```bash
./gradlew :androidApp:assembleDebug
```

---

### 🖥️ Desktop (UI Preview)

**Windows**

```bash
.\gradlew.bat :composeApp:run
```

**macOS/Linux**

```bash
./gradlew :composeApp:run
```

---

### 🍎 iOS

> ⚠️ Requires macOS and Xcode.

1. Open `/iosApp` in Xcode
2. Build and run the project

---

## 🔧 Firebase Setup (Optional)

To enable Firebase:

1. Create a Firebase project
2. Add configuration files:

   * `androidApp/google-services.json`
   * `iosApp/GoogleService-Info.plist`
3. Rebuild the project

---

## 📌 Notes

* 🏠 Designed for personal/family use
* 🖥️ Desktop version is for UI preview only
* 🍎 iOS support included but untested on Windows
* 🔐 Users must provide their own Firebase configuration

---

## 📚 Learn More

🔗 Kotlin Multiplatform
[https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)

---