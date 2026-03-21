package org.example.exer_1

sealed class Event {
    // Represents a user login event
    data class Login(val username: String, val timestamp: Long) : Event()
    // Represents a purchase event
    data class Purchase(val username: String, val amount: Double, val timestamp: Long) : Event()
    // Represents a user logout event
    data class Logout(val username: String, val timestamp: Long) : Event()
}

fun List<Event>.filterByUser(username: String): List<Event> {
    val filtered = mutableListOf<Event>()

    for (event in this) {
        val user = when (event) {
            is Event.Login -> event.username
            is Event.Purchase -> event.username
            is Event.Logout -> event.username
        }
        if (user == username) {
            filtered.add(event)
        }
    }
    return filtered
}

fun List<Event>.totalSpent(username: String): Double {
    return this.filterIsInstance<Event.Purchase>().filter { it.username == username }.sumOf { it.amount }
}

fun processEvents(events: List<Event>, handler: (Event) -> Unit) {
    for (event in events) {
        handler(event)
    }
}

fun main() {
    // Sample data given by the teachers
    val events = listOf(
        Event.Login("alice", 1_000),
        Event.Purchase("alice", 49.99, 1_100),
        Event.Purchase("bob", 19.99, 1_200),
        Event.Login("bob", 1_050),
        Event.Purchase("alice", 15.00, 1_300),
        Event.Logout("alice", 1_400),
        Event.Logout("bob", 1_500)
    )

    processEvents(events) {
        event -> when (event) {
            is Event.Login -> println("[LOGIN]    ${event.username} logged in at t=${event.timestamp}")
            is Event.Purchase -> println("[PURCHASE] ${event.username} spent $${event.amount} at t=${event.timestamp}")
            is Event.Logout -> println("[LOGOUT]   ${event.username} logged out at t=${event.timestamp}")
        }
    }

    println("\nTotal spent by alice: $${events.totalSpent("alice")}")
    println("Total spent by bob: $${events.totalSpent("bob")}")

    println("\nEvent for alice:")
    val aliceEvents = events.filterByUser("alice")
    for (event in aliceEvents) {
        println(" $event")
    }
}