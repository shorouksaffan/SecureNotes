# SecureNotes
Note-taking app that allows users to save, view, delete and lock their personal notes using a password.
 
---

## Branch Strategy

- `master` – Final stable code
- `dev` – Shared development branch
- `feature/*` – Each team member’s task

---

## Project Structure

### `presentation/` — UI Layer  
Contains all user interface components and their ViewModels, grouped by feature:  
- `notes/`: Notes list screen (`NotesFragment`, `NotesViewModel`)  
- `editor/`: Note editor screen (`EditorFragment`, `EditorViewModel`)  
- `settings/`: Settings screen (`SettingsFragment`, `SettingsViewModel`)  
- `MainActivity.kt`: Hosts the navigation container and coordinates navigation between screens.

### `data/` — Data Layer  
Responsible for all data handling, abstracting data sources from the UI layer:  
- `repository/`: Implements `NoteRepository`, the single source of truth for note data.  
- `model/`: Contains data classes like `Note.kt`.  
- `local/`: Manages local storage and security:  
  - `database/`: Room database schemas, DAOs, and migrations.  
  - `preferences/`: DataStore and SharedPreferences migration.  
  - `encryption/`: Encryption utilities using Jetpack Security.

### `domain/` — Business Logic Layer
- `usecase/`: Contains operations like `ExportNoteUseCase.kt` for exporting notes.

### `di/` — Dependency Injection Layer  
Contains Hilt modules such as `AppModule.kt` that provide dependencies across the app.

### `utils/` — Utilities  
General-purpose helpers and extensions:  
- `FileExporter.kt`: File I/O and scoped storage handling.  
- `Extensions.kt`: Kotlin extension functions used app-wide.

---
## Tasks division

### Task 1: Database Core 
- **Files:**
  - `Note.kt` (data class)
  - `NoteDao.kt` (suspend functions)
  - `NoteDatabase.kt` (Room setup + `exportSchema = true`)
    
---

### Task 2: Encryption 
- **Files:**
  - `CryptoManager.kt` (Keystore wrapper)
  - `NoteEncryptor.kt` (AES encrypt/decrypt)
  - `EncryptedPrefs.kt` (password storage)

---

###Task 3: Settings & DataStore 
- **Files:**
  - `SettingsRepository.kt`
  - `settings.proto` (schema)
  - `SettingsViewModel.kt`

---

### Task 4: Repository & ViewModels (Now Possible)
- **Files:**
  - `NoteRepository.kt` (combines DB + encryption)
  - `NotesViewModel.kt`
  - `EditorViewModel.kt`

---

### Task 5: Export & Settings UI (Needs Core)**
- **Files:**
  - `SettingsScreen.kt`
  - `FileExporter.kt` (SAF handling)
  - `ExportNoteUseCase.kt`
---

### Task 6: Main UI (Most Dependent)**
- **Files:**
  - `NotesScreen.kt`
  - `NoteCard.kt`
  - `EditorScreen.kt`
