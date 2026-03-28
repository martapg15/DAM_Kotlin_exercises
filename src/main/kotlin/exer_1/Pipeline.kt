package org.example.exer_1

class Pipeline {
    private val steps = mutableListOf<Pair<String, (List<String>) -> List<String>>>()

    fun addStage(name: String, transform: (List<String>) -> List<String>) {
        steps.add(Pair(name, transform))
    }

    fun execute(input: List<String>): List<String> {
        var result = input
        steps.forEach { (_, transform) ->
            result = transform(result) // Updates the result with the output of the current stage
        }
        return result
    }

    fun describe() {
        println("Pipeline stages:")
        steps.forEachIndexed { idx, (name, _) ->
            println(" ${idx + 1}. $name")
        }
    }

    fun compose(name1: String, name2: String, newStageName: String) {
        val func1 = steps.indexOfFirst { it.first == name1 }
        val func2 = steps.indexOfFirst { it.first == name2 }

        val stage1 = steps[func1].second
        val stage2 = steps[func2].second

        val composedFun: (List<String>) -> List<String> = { stage2(stage1(it))}

        val firstIdx = minOf(func1, func2)
        val secondIdx = maxOf(func1, func2)

        steps.removeAt(secondIdx)
        steps.removeAt(firstIdx)

        steps.add(firstIdx, Pair(newStageName, composedFun))
    }
}

fun buildPipeline(action: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.action()
    return pipeline
}

fun main() {
    val logs = listOf(
        " INFO: server started ",
        " ERROR: disk full ",
        " DEBUG: checking config ",
        " ERROR: out of memory ",
        " INFO: request received ",
        " ERROR: connection timeout "
    )

    val logPipeline = buildPipeline {
        addStage("Trim") { it.map { line -> line.trim() } }
        addStage("Filter errors") { it.filter { line -> line.contains("ERROR") } }
        addStage("Uppercase") { it.map { line -> line.uppercase() } }
        addStage("Add index") { it.mapIndexed { idx, string -> "${idx + 1}. $string" } }
    }

    logPipeline.describe()

    print("\nResult:\n")
    val result = logPipeline.execute(logs)
    result.forEach { println(" $it") }

    println("\n--- After composition ---")
    logPipeline.compose("Trim", "Filter errors", "Trim & Filter Errors")
    logPipeline.describe()
    val resultComposed = logPipeline.execute(logs)
    println("Result:")
    resultComposed.forEach { println(" $it") }
}