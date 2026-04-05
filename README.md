# Assignment TP2 - Kotlin Exercises

**Course:** Mobile Application Development (DAM)  
**Student:** Marta Garcia (nº 51564)  
**Date:** 2026-04-12  
**Repository URL:** https://github.com/martapg15/DAM_Kotlin_exercises

---

## 1. Introduction

This project is a collection of Kotlin programming exercises developed as part of Assignment TP2 for the Mobile Application Development (DAM) course. The goal of the assignment is to practice and demonstrate key Kotlin language features, including object-oriented programming, functional programming patterns, operator overloading, sealed classes, generics, and DSL-style builders.

The exercises are grouped under a single Maven project and implemented in pure Kotlin, targeting the JVM. Each exercise explores a different concept and includes a `main()` function to demonstrate its behaviour.

## 2. System Overview

The project is a Kotlin/JVM application built with Apache Maven. It contains one exercise group (`exer_1`) with four independent components, each addressing a specific set of Kotlin language features:

| File | Concept |
|------|---------|
| `Vec2.kt` | Data classes, operator overloading, extension functions |
| `Event.kt` | Sealed classes, extension functions, higher-order functions |
| `Cache.kt` | Generics, higher-order functions, lambda parameters |
| `Pipeline.kt` | Higher-order functions, DSL builder pattern, function composition |

**Technology stack:**
- **Language:** Kotlin 2.3.0
- **Build tool:** Apache Maven
- **JVM target:** Java 1.8
- **Testing framework:** JUnit Jupiter 5.10.0 (configured)
- **IDE:** IntelliJ IDEA

## 3. Architecture and Design

The project follows the standard Maven directory structure:

```
DAM_Kotlin_exercises/
├── pom.xml
└── src/
    └── main/
        └── kotlin/
            ├── Main.kt
            └── exer_1/
                ├── Vec2.kt
                ├── Event.kt
                ├── Cache.kt
                └── Pipeline.kt
```

All exercise files are placed under the `org.example.exer_1` package. Each file is self-contained and includes its own `main()` function for standalone demonstration. There are no dependencies between the exercise files.

Key design decisions:
- **`Vec2`** is implemented as a `data class` to leverage automatic `equals`, `hashCode`, `toString`, and destructuring support provided by Kotlin.
- **`Event`** uses a `sealed class` hierarchy to model a closed set of event types, enabling exhaustive `when` expressions.
- **`Cache`** uses a generic class with type bounds (`K: Any, V: Any`) and a `LinkedHashMap` internally to preserve insertion order.
- **`Pipeline`** uses a lambda receiver (`Pipeline.() -> Unit`) in its `buildPipeline` DSL builder function, enabling a clean builder syntax.

## 4. Implementation

### Exercise 1.1 - `Vec2.kt`: 2D Vector with Operator Overloading

Implements a 2D vector as a `data class Vec2(val x: Double, val y: Double)` with the following operations:

| Member / Operator | Description |
|---|---|
| `operator fun plus(other: Vec2)` | Vector addition |
| `operator fun minus(other: Vec2)` | Vector subtraction |
| `operator fun times(scalar: Double)` | Scalar multiplication (Vec2 × Double) |
| `operator fun unaryMinus()` | Negation |
| `fun magnitude()` | Euclidean magnitude using `sqrt(x² + y²)` |
| `override fun compareTo(other: Vec2)` | Comparison by magnitude (implements `Comparable<Vec2>`) |
| `fun dot(other: Vec2)` | Dot product |
| `fun normalized()` | Unit vector; throws `IllegalStateException` for zero vectors |
| `operator fun get(idx: Int)` | Index access (`[0]` for x, `[1]` for y) |

An extension function `operator fun Double.times(v: Vec2)` is also defined to support commutative scalar multiplication (`2.0 * a`).

Since `Vec2` is a `data class`, Kotlin automatically generates `component1()` and `component2()`, so destructuring (`val (x, y) = a`) works without manual implementation.

### Exercise 1.2 - `Event.kt`: Event Processing with Sealed Classes

Models a system of application events using a `sealed class Event` with three subtypes:

- `Event.Login(username: String, timestamp: Long)` — user login
- `Event.Purchase(username: String, amount: Double, timestamp: Long)` — purchase transaction
- `Event.Logout(username: String, timestamp: Long)` — user logout

Additional functions:

| Function | Description |
|---|---|
| `List<Event>.filterByUser(username)` | Extension function returning all events for a given user |
| `List<Event>.totalSpent(username)` | Extension function summing purchase amounts for a given user |
| `processEvents(events, handler)` | Higher-order function that applies a handler lambda to each event |

The sealed class ensures that `when` expressions on `Event` are exhaustive without requiring an `else` branch.

### Exercise 1.3 - `Cache.kt`: Generic Key-Value Cache

Implements a generic `Cache<K: Any, V: Any>` backed by a `LinkedHashMap`, which preserves insertion order. Supports the following operations:

| Method | Description |
|---|---|
| `put(key, value)` | Inserts or updates an entry |
| `get(key)` | Returns the value for a key, or `null` if absent |
| `evict(key)` | Removes an entry by key |
| `size()` | Returns the number of cached entries |
| `getOrPut(key, default)` | Returns the cached value or inserts and returns the result of `default()` |
| `transform(key, action)` | Applies `action` to the value at `key` and updates it; returns `true` if found, `false` otherwise |
| `snapshot()` | Returns a read-only copy of all current entries |
| `filterValues(predicate)` | Returns a read-only map of entries matching the predicate |

### Exercise 1.4 - `Pipeline.kt`: Text Processing Pipeline with DSL Builder

Implements a `Pipeline` class for composing sequential string-list transformation stages. Each stage is a named function `(List<String>) -> List<String>`.

| Method | Description |
|---|---|
| `addStage(name, transform)` | Appends a named transformation stage |
| `execute(input)` | Runs all stages in order and returns the final result |
| `describe()` | Prints the ordered list of stage names |
| `compose(name1, name2, newName)` | Merges two existing stages into one composed stage, replacing both at the position of the first |

A `buildPipeline { }` DSL builder function using a lambda with receiver (`Pipeline.() -> Unit`) allows pipelines to be constructed with a clean, readable syntax.

**Example pipeline used in `main()`:** Trim → Filter errors → Uppercase → Add index, applied to a list of log strings.

## 5. Testing and Validation

The tets done for this part of the assigment were primarily manual, by running a main function and validating output behavior. Therefore, each exercise file includes a `main()` function that demonstrates the implemented functionality with representative sample inputs and prints results to the console. These serve as manual validation scenarios:

- **`Vec2.kt` main():** Exercises all operators, `magnitude()`, `dot()`, `normalized()`, indexing, comparison, and `max()`/`min()` on a list of vectors.
- **`Event.kt` main():** Processes a mixed event list, printing formatted output and computing total spending per user.
- **`Cache.kt` main():** Demonstrates word frequency tracking and an ID registry, exercising all cache methods.
- **`Pipeline.kt` main():** Builds and runs a log-processing pipeline, then demonstrates stage composition.

## 6. Usage Instructions
### Requirements
- Kotlin/JVM environment (run from IntelliJ IDEA or via Maven project configuration).
- A JDK installed (e.g., JDK 17+ is commonly used for Kotlin/JVM projects).

### How to run
1. Clone the repository:
   - `git clone https://github.com/martapg15/DAM_Kotlin_exercises`
2. Open the project in IntelliJ IDEA.
3. Navigate to `src/main/kotlin/exer_1`.
4. Run any of the `main()` function inside each Kotlin file. For example:
   - Open `Cache.kt` and click the green run arrow next to the `main()` function.

---

# Development Process

## 12. Version Control and Commit History

Version control was used throughout the development of this project to document the progression of the Kotlin exercises. The commit history begins with the initialization of the project structure and configuration, followed by incremental commits corresponding to the implementation of each exercise. The final commit includes the challenge of exercise 1.3 that was left behind.

## 13. Difficulties and Lessons Learned

No significant difficulties were encountered during this stage. The main lessons learned are summarized below:

- **Destructuring in data classes:** When implementing destructuring support for `Vec2`, it became clear that Kotlin's `data class` already auto-generates `component1()` and `component2()` functions. Manually implementing them would have been unnecessary. This highlighted the importance of knowing which language features are provided automatically.

- **Generic type constraints in `Cache`:** Using `K: Any` and `V: Any` as type bounds was necessary to prevent nullable types from being used as keys or values, which would have complicated null-safety logic throughout the cache implementation.

- **Pipeline composition:** Implementing `compose()` required careful index management when removing and re-inserting stages from a mutable list. The order of removal (highest index first) needed to be correct to avoid index shifting issues.

- **Lambda receivers for DSLs:** The `buildPipeline { }` builder using a lambda with receiver (`Pipeline.() -> Unit`) was an interesting way to create a clean DSL-style API. Understanding the difference between a regular lambda and a lambda with receiver was a key learning point for this exercise.

## 14. Future Improvements

The current exercise solutions provide a solid foundation in Kotlin fundamentals. Future improvements will focus on elevating the codebase to professional standards by implementing formal unit tests and exploring more advanced architectural patterns, such as making the Pipeline execution asynchronous using Kotlin Coroutines.

## 15. AI Usage Disclosure (Mandatory)

In this part of the assignment, the use of AI tools for code generation was explicitly forbidden. Therefore, all code in this repository was implemented manually by the student.

However, AI tools were used only to assist in revising and improving the wording of the README report, as permitted by the assignment guidelines. I remain fully responsible for the accuracy and content of this documentation.
