/**
 * Recipe Organizer - CSE 310 Module 4 (Kotlin Language)
 * Author: Israel Brown
 *
 * Demonstrates:
 *  - Variables (val / var)
 *  - Data classes
 *  - Collections (ArrayList, MutableList)
 *  - Loops (for, while)
 *  - Conditionals (if/else)
 *  - Functions (CRUD operations)
 *  - Stretch: data classes, collections, when keyword
 */

// ─────────────────────────────────────────────
// DATA CLASSES
// ─────────────────────────────────────────────

/**
 * Represents a single recipe with all its details.
 * Uses a data class so Kotlin auto-generates equals/hashCode/toString/copy.
 */
data class Recipe(
    val id: Int,
    var name: String,
    var category: String,
    var ingredients: MutableList<String>,
    var instructions: String,
    var prepTimeMinutes: Int = 0,
    var servings: Int = 1
)

/**
 * Represents a recipe category with an optional description.
 */
data class Category(
    val name: String,
    var description: String = ""
)

// ─────────────────────────────────────────────
// RECIPE MANAGER (handles collections & logic)
// ─────────────────────────────────────────────

class RecipeManager {
    // Mutable collections — stretch challenge requirement
    private val recipes: MutableList<Recipe> = mutableListOf()
    private val categories: MutableList<Category> = mutableListOf()
    private var nextId: Int = 1  // var — mutable counter

    init {
        // Seed with default categories
        categories.addAll(listOf(
            Category("Breakfast", "Morning meals and snacks"),
            Category("Lunch",     "Midday dishes"),
            Category("Dinner",    "Evening main meals"),
            Category("Desserts",  "Sweet treats"),
            Category("Snacks",    "Light bites between meals")
        ))

        // Seed with two sample recipes so the app isn't empty on first run
        addRecipe(
            name         = "Pancakes",
            category     = "Breakfast",
            ingredients  = mutableListOf("1 cup flour", "1 cup milk", "1 egg", "2 tbsp butter", "1 tsp baking powder"),
            instructions = "Mix dry ingredients. Add wet ingredients. Cook on griddle 2-3 min per side.",
            prepTime     = 15,
            servings     = 4
        )
        addRecipe(
            name         = "Spaghetti Bolognese",
            category     = "Dinner",
            ingredients  = mutableListOf("200g spaghetti", "300g ground beef", "1 onion", "2 garlic cloves", "1 can tomatoes"),
            instructions = "Brown beef. Sauté onion and garlic. Add tomatoes, simmer 20 min. Serve over pasta.",
            prepTime     = 35,
            servings     = 3
        )
    }

    // ── CREATE ──────────────────────────────────

    /** Adds a new recipe and returns it. */
    fun addRecipe(
        name: String,
        category: String,
        ingredients: MutableList<String>,
        instructions: String,
        prepTime: Int = 0,
        servings: Int = 1
    ): Recipe {
        val recipe = Recipe(
            id           = nextId++,
            name         = name,
            category     = category,
            ingredients  = ingredients,
            instructions = instructions,
            prepTimeMinutes = prepTime,
            servings     = servings
        )
        recipes.add(recipe)
        return recipe
    }

    // ── READ ────────────────────────────────────

    /** Returns an immutable view of all recipes. */
    fun getAllRecipes(): List<Recipe> = recipes.toList()

    /** Finds a recipe by its numeric ID, or null if not found. */
    fun findById(id: Int): Recipe? = recipes.find { it.id == id }

    /** Returns recipes whose name contains the query (case-insensitive). */
    fun searchByName(query: String): List<Recipe> =
        recipes.filter { it.name.contains(query, ignoreCase = true) }

    /** Returns all recipes that belong to the given category. */
    fun filterByCategory(categoryName: String): List<Recipe> =
        recipes.filter { it.category.equals(categoryName, ignoreCase = true) }

    // ── UPDATE ──────────────────────────────────

    /**
     * Updates a recipe field identified by [id].
     * Returns true on success, false if not found.
     */
    fun updateRecipe(
        id: Int,
        name: String? = null,
        category: String? = null,
        ingredients: MutableList<String>? = null,
        instructions: String? = null,
        prepTime: Int? = null,
        servings: Int? = null
    ): Boolean {
        val recipe = findById(id) ?: return false
        // Only update fields that were provided (non-null)
        if (name         != null) recipe.name             = name
        if (category     != null) recipe.category         = category
        if (ingredients  != null) recipe.ingredients      = ingredients
        if (instructions != null) recipe.instructions     = instructions
        if (prepTime     != null) recipe.prepTimeMinutes  = prepTime
        if (servings     != null) recipe.servings         = servings
        return true
    }

    // ── DELETE ──────────────────────────────────

    /** Removes a recipe by ID. Returns true if removed, false if not found. */
    fun deleteRecipe(id: Int): Boolean = recipes.removeIf { it.id == id }

    // ── CATEGORIES ──────────────────────────────

    fun getCategories(): List<Category> = categories.toList()

    fun addCategory(name: String, description: String = ""): Boolean {
        // Prevent duplicates (case-insensitive)
        if (categories.any { it.name.equals(name, ignoreCase = true) }) return false
        categories.add(Category(name, description))
        return true
    }
}

// ─────────────────────────────────────────────
// DISPLAY HELPERS
// ─────────────────────────────────────────────

/** Prints a horizontal divider line. */
fun divider(char: Char = '─', width: Int = 50) = println(char.toString().repeat(width))

/** Formats and prints a single recipe in a readable block. */
fun printRecipe(recipe: Recipe) {
    divider()
    println("  ID       : ${recipe.id}")
    println("  Name     : ${recipe.name}")
    println("  Category : ${recipe.category}")
    println("  Prep Time: ${recipe.prepTimeMinutes} min  |  Servings: ${recipe.servings}")
    println("  Ingredients:")
    // Loop through ingredients and print each on its own line
    for (ingredient in recipe.ingredients) {
        println("    - $ingredient")
    }
    println("  Instructions:")
    println("    ${recipe.instructions}")
    divider()
}

/** Prints a compact one-line summary for list views. */
fun printRecipeSummary(recipe: Recipe) {
    println("  [${recipe.id}] ${recipe.name}  (${recipe.category})  — ${recipe.prepTimeMinutes} min")
}

// ─────────────────────────────────────────────
// INPUT HELPERS
// ─────────────────────────────────────────────

/** Reads a non-blank line from stdin with a prompt. Loops until valid input. */
fun readRequired(prompt: String): String {
    var input: String
    do {
        print(prompt)
        input = readLine()?.trim() ?: ""
        if (input.isEmpty()) println("  ⚠  This field cannot be empty. Please try again.")
    } while (input.isEmpty())
    return input
}

/** Reads an optional line (may be blank). */
fun readOptional(prompt: String): String {
    print(prompt)
    return readLine()?.trim() ?: ""
}

/** Reads a positive integer. Returns defaultVal on blank or invalid input. */
fun readInt(prompt: String, defaultVal: Int = 0): Int {
    print(prompt)
    return readLine()?.trim()?.toIntOrNull() ?: defaultVal
}

// ─────────────────────────────────────────────
// MENU ACTIONS (each is its own function)
// ─────────────────────────────────────────────

/** Interactively collects recipe data and adds it to the manager. */
fun addRecipeInteractive(manager: RecipeManager) {
    println("\n  ── Add New Recipe ──")
    val name         = readRequired("  Recipe name    : ")
    val category     = readRequired("  Category       : ")
    val prepTime     = readInt("  Prep time (min): ")
    val servings     = readInt("  Servings       : ", 1)
    println("  Enter ingredients one per line. Leave blank to finish.")
    val ingredients  = mutableListOf<String>()
    while (true) {
        val ingredient = readOptional("    Ingredient: ")
        if (ingredient.isEmpty()) break
        ingredients.add(ingredient)
    }
    val instructions = readRequired("  Instructions   : ")

    val recipe = manager.addRecipe(name, category, ingredients, instructions, prepTime, servings)
    println("\n  ✓ Recipe '${recipe.name}' added with ID ${recipe.id}.")
}

/** Lists all recipes or shows a message if none exist. */
fun listAllRecipes(manager: RecipeManager) {
    val recipes = manager.getAllRecipes()
    if (recipes.isEmpty()) {
        println("\n  No recipes found.")
        return
    }
    println("\n  ── All Recipes (${recipes.size}) ──")
    // for-loop over list — demonstrates loops requirement
    for (recipe in recipes) {
        printRecipeSummary(recipe)
    }
}

/** Prompts for a search term and shows matching recipes. */
fun searchRecipes(manager: RecipeManager) {
    val query   = readRequired("\n  Search by name: ")
    val results = manager.searchByName(query)
    if (results.isEmpty()) {
        println("  No recipes matched '$query'.")
    } else {
        println("  Found ${results.size} result(s):")
        for (r in results) printRecipeSummary(r)
    }
}

/** Lets the user pick a category to filter by — uses when for display logic. */
fun browseByCategory(manager: RecipeManager) {
    val categories = manager.getCategories()
    println("\n  ── Categories ──")
    // when keyword — stretch challenge requirement (display category emoji/icon)
    for ((index, cat) in categories.withIndex()) {
        val icon = when (cat.name.lowercase()) {
            "breakfast"  -> "🌅"
            "lunch"      -> "🥗"
            "dinner"     -> "🍽"
            "desserts"   -> "🍰"
            "snacks"     -> "🍎"
            else         -> "📁"
        }
        println("  ${index + 1}. $icon ${cat.name}  — ${cat.description}")
    }
    val choice   = readRequired("\n  Enter category name: ")
    val filtered = manager.filterByCategory(choice)
    if (filtered.isEmpty()) {
        println("  No recipes in category '$choice'.")
    } else {
        println("\n  Recipes in '$choice' (${filtered.size}):")
        for (r in filtered) printRecipeSummary(r)
    }
}

/** Shows a full recipe detail by ID. */
fun viewRecipeDetail(manager: RecipeManager) {
    val id     = readInt("\n  Enter recipe ID: ")
    val recipe = manager.findById(id)
    if (recipe == null) {
        println("  Recipe with ID $id not found.")
    } else {
        printRecipe(recipe)
    }
}

/** Edits an existing recipe field-by-field. Blank = keep existing value. */
fun editRecipe(manager: RecipeManager) {
    val id     = readInt("\n  Enter ID of recipe to edit: ")
    val recipe = manager.findById(id)
    if (recipe == null) {
        println("  Recipe not found.")
        return
    }
    println("  Editing '${recipe.name}' — leave blank to keep current value.")
    val name         = readOptional("  New name [${recipe.name}]: ").ifEmpty { null }
    val category     = readOptional("  New category [${recipe.category}]: ").ifEmpty { null }
    val prepTimeStr  = readOptional("  New prep time [${recipe.prepTimeMinutes}]: ")
    val prepTime     = if (prepTimeStr.isEmpty()) null else prepTimeStr.toIntOrNull()
    val servingsStr  = readOptional("  New servings [${recipe.servings}]: ")
    val servings     = if (servingsStr.isEmpty()) null else servingsStr.toIntOrNull()

    println("  Update ingredients? (y/n)")
    val updateIngredients = readOptional("  > ").equals("y", ignoreCase = true)
    val ingredients: MutableList<String>? = if (updateIngredients) {
        println("  Enter new ingredients one per line. Leave blank to finish.")
        val list = mutableListOf<String>()
        while (true) {
            val ing = readOptional("    Ingredient: ")
            if (ing.isEmpty()) break
            list.add(ing)
        }
        list
    } else null

    val instructions = readOptional("  New instructions [keep current]: ").ifEmpty { null }

    manager.updateRecipe(id, name, category, ingredients, instructions, prepTime, servings)
    println("  ✓ Recipe updated.")
}

/** Deletes a recipe after confirmation. */
fun deleteRecipe(manager: RecipeManager) {
    val id = readInt("\n  Enter ID of recipe to delete: ")
    val recipe = manager.findById(id)
    if (recipe == null) {
        println("  Recipe not found.")
        return
    }
    print("  Delete '${recipe.name}'? (y/n): ")
    val confirm = readLine()?.trim() ?: ""
    if (confirm.equals("y", ignoreCase = true)) {
        manager.deleteRecipe(id)
        println("  ✓ Deleted.")
    } else {
        println("  Cancelled.")
    }
}

/** Adds a custom category. */
fun addCategoryInteractive(manager: RecipeManager) {
    val name        = readRequired("\n  Category name  : ")
    val description = readOptional("  Description    : ")
    val added       = manager.addCategory(name, description)
    if (added) println("  ✓ Category '$name' added.")
    else println("  A category named '$name' already exists.")
}

// ─────────────────────────────────────────────
// MAIN – the program entry point
// ─────────────────────────────────────────────

fun main() {
    val manager = RecipeManager()  // val — immutable reference, mutable internal state

    println("╔══════════════════════════════════════════╗")
    println("║        🍴 Kotlin Recipe Organizer 🍴       ║")
    println("╚══════════════════════════════════════════╝")

    // Main menu loop — demonstrates loops requirement
    var running = true
    while (running) {
        println("""
  
  ── Main Menu ─────────────────────────────
  1. List all recipes
  2. View recipe detail
  3. Add recipe
  4. Edit recipe
  5. Delete recipe
  6. Search by name
  7. Browse by category
  8. Add custom category
  9. Exit
  ──────────────────────────────────────────""")

        print("  Your choice: ")
        val input = readLine()?.trim() ?: ""

        // when keyword replaces if-else chain — stretch challenge requirement
        when (input) {
            "1"  -> listAllRecipes(manager)
            "2"  -> viewRecipeDetail(manager)
            "3"  -> addRecipeInteractive(manager)
            "4"  -> editRecipe(manager)
            "5"  -> deleteRecipe(manager)
            "6"  -> searchRecipes(manager)
            "7"  -> browseByCategory(manager)
            "8"  -> addCategoryInteractive(manager)
            "9"  -> { println("\n  Goodbye! Happy cooking! 🍳"); running = false }
            else -> println("  ⚠  Invalid choice. Please enter 1–9.")
        }
    }
}