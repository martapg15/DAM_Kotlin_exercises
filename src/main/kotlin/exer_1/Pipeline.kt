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
}

fun buildPipeline(action: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.action()
    return pipeline
}

fun main() {
    val logs = listOf (
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
}