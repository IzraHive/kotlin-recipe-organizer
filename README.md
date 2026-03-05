# Overview

**AI Disclosure:** AI assistance (Claude) was used to help improve grammar and clarity in writing. All app logic, class structure, collection design, feature implementation, and design decisions represent original work.

As a software engineer continually working to broaden my language toolkit, I wanted to get hands-on experience with Kotlin — a modern, concise JVM language that is quickly becoming the standard for Android development and clean backend work. Rather than just reading about the syntax, I built a fully functional console-based **Recipe Organizer** that forces me to use every core language feature in a realistic way.

The app lets users manage their favorite recipes through a command-line menu. It supports adding, viewing, editing, deleting, and searching recipes, as well as organizing them into categories. Behind the scenes the program demonstrates Kotlin variables (`val`/`var`), data classes, mutable collections (`MutableList`), `for` loops, `while` loops, `if`/`else` conditionals, and the `when` keyword for both the main menu and category icon logic.

My goal was not only to learn the syntax but to build something with enough real complexity that I would encounter common patterns used in production Kotlin code — nullable types, default parameters, `init` blocks, extension-style helper functions, and clean separation between data, logic, and UI layers.

[Software Demo Video](https://youtu.be/aOhGVkzpGXU)

---

## Quick Setup (Run from Clean Clone)

```powershell
# Clone the repository
git clone https://github.com/IzraHive/kotlin-recipe-organizer.git
cd kotlin-recipe-organizer

# Compile the program
kotlinc src/RecipeOrganizer.kt -include-runtime -d RecipeOrganizer.jar

# Run the program
java -jar RecipeOrganizer.jar
```

**Requirements:**
- Java JDK 21 or higher
- Kotlin compiler (`kotlinc`)
- The file `src/RecipeOrganizer.kt` must be in the `src/` directory

**Output:**
- Interactive console menu with 9 options (List, View, Add, Edit, Delete, Search, Browse by Category, Add Category, Exit)
- Pre-loaded with 2 sample recipes on first run

---

## Installing Java and Kotlin (First Time Setup)

You need both Java AND Kotlin installed. Follow these steps in order.

### Step 1 – Install Java (JDK 21)

1. Go to **https://adoptium.net**
2. Click the big **"Latest LTS Release"** button — it auto-detects Windows x64
3. Download the `.msi` installer
4. Run it — **important:** on the install screen, check the box that says **"Add to PATH"** and **"Set JAVA_HOME"**
5. Click through and finish

### Step 2 – Install Kotlin Compiler

1. Go to **https://github.com/JetBrains/kotlin/releases/latest**
2. Under "Assets", download **`kotlin-compiler-X.X.X.zip`**
3. Extract the zip — you'll get a folder called `kotlinc`
4. Move that folder to `C:\kotlinc`
5. Add to PATH:
   - Press `Win + S` → search **"environment variables"** → click **"Edit the system environment variables"**
   - Click the **"Environment Variables"** button
   - Under **System variables**, click **Path** → **Edit**
   - Click **New** → type `C:\kotlinc\bin`
   - Click **OK** on all 3 windows

### Step 3 – Verify Installation

Close PowerShell completely and reopen it, then run:

```powershell
java -version
kotlinc -version
```

Both should print version numbers. **The key thing is closing and reopening PowerShell after each install** — PATH changes are invisible to any window that was already open.

### Step 4 – Compile and Run in VS Code

1. Open VS Code and install the **"Kotlin"** extension by fwcd (search in Extensions tab)
2. Open the `kotlin-recipe-organizer` folder: **File → Open Folder**
3. Open the built-in terminal: **Terminal → New Terminal**
4. In the terminal, compile and run:

```
kotlinc src/RecipeOrganizer.kt -include-runtime -d RecipeOrganizer.jar
java -jar RecipeOrganizer.jar
```

5. The program starts in the terminal — use number keys `1–9` to navigate the menu

---

# Development Environment

I developed this project using VS Code as my primary editor with the Kotlin extension for syntax highlighting. The Kotlin compiler (`kotlinc`) handles compilation to a runnable JAR file, and the Java Virtual Machine (JDK 21) provides the runtime. Git and GitHub provided version control, allowing me to track changes and maintain a public repository.

**Tools:**
- VS Code with Kotlin extension (by fwcd)
- Java JDK 21
- Kotlin Compiler 2.x
- Git / GitHub for version control

**Language & Libraries:**
- **Kotlin** — statically typed, object-oriented and functional language that compiles to JVM bytecode
- No external libraries used — relies entirely on the Kotlin standard library (`kotlin.collections`, `kotlin.io`)

# Useful Websites

- [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html) - Complete language reference and guides
- [Kotlin Tutorial — Programiz](https://www.programiz.com/kotlin-programming) - Beginner-friendly syntax walkthroughs
- [Kotlin — Wikipedia](https://en.wikipedia.org/wiki/Kotlin_(programming_language)) - Language history and overview
- [Kotlin Comparison to Java](https://kotlinlang.org/docs/comparison-to-java.html) - Helpful for understanding Kotlin's improvements
- [Kotlin `when` Expression](https://kotlinlang.org/docs/control-flow.html#when-expression) - Reference for the stretch challenge
- [Kotlin Data Classes](https://kotlinlang.org/docs/data-classes.html) - Reference for the stretch challenge
- [Kotlin Collections Overview](https://kotlinlang.org/docs/collections-overview.html) - MutableList and collection operations
- [Adoptium — JDK Downloads](https://adoptium.net) - Free OpenJDK installer for Windows

---

# Future Work

- **Persistent storage** — Save recipes to a JSON or SQLite file so data survives between sessions instead of being lost on exit
- **Import / Export** — Let users export their recipe collection to a `.txt` or `.csv` file and import recipes from the same format
- **Ingredient scaling** — Automatically multiply ingredient quantities when the user changes the serving count
- **Favorites / Ratings** — Allow users to star recipes and sort the list by rating
- **Android port** — Move the same data classes and manager logic into an Android project with a Jetpack Compose UI, since Kotlin is the official Android language