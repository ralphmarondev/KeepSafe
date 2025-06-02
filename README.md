## 🛡️ **App Name:** KeepSafe

A **family member management** app built with **Jetpack Compose Multiplatform**, targeting **Android
and Desktop** initially. Uses **Firebase** as backend with local database support for offline
access.

---

## 🧠 **Core Concept**

* **Admin-Managed Family Roster**
  Only the **admin** can create a family and add members.
* **Role-Based Access**

    * **Admin**: full control — can manage family, update profiles, and configure app settings.
    * **Members**: can log in, view family list, view and edit their username/password, and access
      their profile.

---

## 📱 **App Flow**

### 1. **Splash Screen**

* Logo and version.
* Auto-navigation based on login status.

### 2. **Onboarding**

* First-time launch only.
* Short 2–3 screen intro about app purpose and privacy.

### 3. **Login Screen**

* **No self-registration.**
* Admin is responsible for creating all user accounts.

### 4. **Main App Navigation**

**Adaptive UI:**

* **Mobile** → Bottom Navigation Bar.
* **Desktop** → Navigation Rail (Gmail-style layout).

---

## 🧭 **Navigation Sections**

### 🏠 Home

* List of family members with:

    * Name
    * Role
    * Birthday
    * Birthplace

* Click/tap to view details.

### 👤 Profile

* View personal information.

* Members can edit:

    * Username
    * Password

* Admin can edit:

    * Full personal info (including birthday and birthplace)

### ⚙️ Settings

* **Keep me signed in** ✅ (default ON)
* **Sync on app launch** ✅ (default ON)
* **Manual sync** 🔄 (sync now button)
* **Logout** 🔓

---

## 🔐 **Authentication & Roles**

* Only **one admin** per Firebase instance.
* Admin adds all members.
* No public access or sign-up flow.

---

## 🔄 **Offline Support & Syncing**

### 📥 Local Database

* On login, data is saved locally.
* App is fully usable offline (read-only).

### 🔁 Sync Options

| Trigger                 | Behavior                                        |
|-------------------------|-------------------------------------------------|
| App launch (if enabled) | Syncs with Firebase and updates local database. |
| Manual sync             | Button-based refresh of family data.            |
| Offline                 | Shows cached data, disables syncing.            |

---

## 📝 **Feature Summary**

| Feature                    | Admin | Member |
|----------------------------|-------|--------|
| Login                      | ✅     | ✅      |
| View family list           | ✅     | ✅      |
| Add/edit family members    | ✅     | ❌      |
| Edit own username/password | ✅     | ✅      |
| View personal profile      | ✅     | ✅      |
| Sync (manual/auto)         | ✅     | ✅      |
| Toggle keep signed in      | ✅     | ✅      |
| Logout                     | ✅     | ✅      |

---

## 🧑‍💻 Developer Note

Made with ❤️ by **Ralph Maron Eda**
GitHub: [**Ralph Maron Eda**](https://github.com/ralphmarondev)

---

## 📄 License

This project is licensed under the **MIT License**.
