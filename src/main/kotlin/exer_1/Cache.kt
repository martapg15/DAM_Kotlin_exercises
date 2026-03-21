package org.example.exer_1

class Cache<K: Any, V: Any> {
    private val entries: MutableMap<K, V> = LinkedHashMap()

    fun put(key: K, value: V) {
        entries[key] = value
    }

    fun get(key: K): V? {
        if (!entries.containsKey(key)) {
            return null
        }
        return entries[key]
    }

    fun evict(key: K) {
        entries.remove(key)
    }

    fun size(): Int {
        return entries.size
    }

    fun getOrPut(key: K, default: () -> V): V {
        val currentKey = get(key)

        if (currentKey != null) {
            return currentKey
        } else {
            val newValue = default()
            put(key, newValue)
            return newValue
        }
    }

    fun transform(key: K, action: (V) -> V): Boolean {
        val currentKey = get(key)

        if (currentKey != null) {
            val newValue = action(currentKey)
            put(key, newValue)
            return true
        } else {
            return false
        }
    }

    fun snapshot(): Map<K, V> {
        return entries.toMap() // toMap will return a read-only copy of the existing cache contents
    }

    fun filterValues(predicate: (V) -> Boolean): Map<K, V> {
        return entries.filterValues(predicate).toMap()
    }
}

fun main() {
    print("--- Word frequency cache ---")

    val wordCount = Cache<String, Int>()
    wordCount.put("kotlin", 1)
    wordCount.put("scala", 1)
    wordCount.put("haskell", 1)

    println("\nSize: ${wordCount.size()}")
    println("Frequency of \"kotlin\": ${wordCount.get("kotlin")}")

    println("getOrPut \"kotlin\": ${wordCount.getOrPut("kotlin") { 0 }}")
    println("getOrPut \"java\": ${wordCount.getOrPut("java") { 0 }}")

    println("Size after getOrPut: ${wordCount.size()}")

    val t1 = wordCount.transform("kotlin") { it + 1 }
    println("Transform \"kotlin\" (+1): $t1")

    val t2 = wordCount.transform("cobol") { it + 1 }
    println("Transform \"cobol\" (+1): $t2")

    println("Snapshot: ${wordCount.snapshot()}")

    val filterValuesTest = wordCount.filterValues { it > 0 }
    println("Words with count > 0: $filterValuesTest")

    println("\n--- Id registry cache ---")

    val registry = Cache<Int, String>()
    registry.put(1, "Alice")
    registry.put(2, "Bob")

    println("Id 1 -> ${registry.get(1)}")
    println("Id 2 -> ${registry.get(2)}")

    registry.evict(1)
    println("After evict id 1, size: ${registry.size()}")
    println("Id 1 after evict -> ${registry.get(1)}")
}