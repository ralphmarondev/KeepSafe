# 🔐 Firebase API Key Configuration (Constants.kt)

This file contains your **Firebase API key** used for authentication via the Firebase REST API in
your Compose Multiplatform app.

---

## 📁 File Path

```

composeApp/src/commonMain/kotlin/com/ralphmarondev/keepsafe/Constants.kt

````

---

## 📝 Sample `Constants.kt`

```kotlin
package com.ralphmarondev.keepsafe

const val API_KEY = "i_am_a_secret_api_key_dont_share_me_hehe"
````

---

## 🚫 Important

**Do NOT commit this file to version control!**

Add it to your `.gitignore`:

```
# Secrets
composeApp/src/commonMain/kotlin/com/ralphmarondev/keepsafe/Constants.kt
```

---

## ✅ Usage Example

```kotlin
import com.ralphmarondev.keepsafe.Constants

val apiKey = API_KEY
```

You can also combine this with `System.getenv()` as a fallback mechanism for local dev and CI/CD
environments.

---

## 🔐 Reminder

Firebase API keys are public by design, but it's still good practice to keep your source repo clean
and avoid hardcoding credentials directly into tracked files.

