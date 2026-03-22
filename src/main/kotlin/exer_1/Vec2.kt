package org.example.exer_1

import kotlin.math.sqrt

data class Vec2 (val x : Double, val y : Double) : Comparable<Vec2> {
    // Below this comment is the first approach to implement the operator compareTo using the magnitude. I changed it
    // later on because it was asked to implement the member function magnitude so, in order to not have repeated code,
    // I used this new function inside the implementation of the operator compareTo.
    //
    /*private val magnitude: Double
        get() {
            return sqrt(x * x + y * y)
        }*/

    operator fun plus(other : Vec2) : Vec2 {
        return Vec2(x + other.x, y + other.y)
    }

    operator fun minus(other : Vec2) : Vec2 {
        return Vec2(x - other.x, y - other.y)
    }

    operator fun times(scalar : Double) : Vec2 {
        return Vec2(x * scalar, y * scalar)
    }

    operator fun unaryMinus() : Vec2 {
        return Vec2(-x, -y)
    }

    fun magnitude() : Double {
        return sqrt(x * x + y * y)
    }

    override operator fun compareTo(other: Vec2): Int {
        return this.magnitude().compareTo(other.magnitude())
    }

    fun dot(other : Vec2) : Double {
        return x * other.x + y * other.y
    }

    fun normalized() : Vec2 {
        val magnitude = magnitude()
        if (magnitude == 0.0) {
            throw IllegalStateException("Cannot normalize a zero vector.")
        }
        return Vec2(x / magnitude, y / magnitude)
    }

    operator fun get(idx : Int) : Double {
        return when (idx) {
            0 -> x
            1 -> y
            else -> throw IndexOutOfBoundsException("Index: $idx is out of bounds.")
        }
    }

    // Since it's a data class, Kotlin already handles the destructuring declarations automatically, therefore there
    // is no need to manually implement them. Either way, to fulfill the topic of the challenge for this part of the
    // project, I'll leave both functions commented here.
    //
    // operator fun component1(): Double = x
    // operator fun component2(): Double = y
}

operator fun Double.times(v: Vec2): Vec2 {
    return v * this
}

fun main () {
    val a = Vec2 (3.0, 4.0)
    val b = Vec2 (1.0, 2.0)
    val (x, y) = a
    println("a = $a") // a = Vec2(x=3.0, y=4.0)
    println("b = $b") // b = Vec2(x=1.0, y=2.0)
    println("x=$x and y=$y") // x=3.0 and y=4.0
    println("a + b = ${a + b}") // a + b = Vec2(x=4.0, y=6.0)
    println("a - b = ${a - b}") // a - b = Vec2(x=2.0, y=2.0)
    println("a * 2.0 = ${a * 2.0} ") // a * 2.0 = Vec2(x=6.0, y=8.0)
    println("2.0 * a = ${2.0 * a}") // 2.0 * a = Vec2(x=6.0, y=8.0)
    println("-a = ${-a}") // -a = Vec2(x=-3.0, y=-4.0)
    println("|a| = ${a.magnitude()}") // |a| = 5.0
    println("a dot b = ${a.dot(b)}") // a dot b = 11.0
    println("norm (a) = ${a.normalized()}") // norm (a) = Vec2(x=0.6, y =0.8)
    println("a[0] = ${a[0]} ") // a[0] = 3.0
    println("a[1] = ${a[1]} ") // a[1] = 4.0
    println("a > b = ${a > b}") // a > b = true
    println("a < b = ${a < b}") // a < b = false

    val vectors = listOf(Vec2(1.0, 0.0), Vec2(3.0, 4.0), Vec2(0.0, 2.0))
    println("Longest = ${vectors.max()}") // Longest = Vec2(x=3.0, y =4.0)
    println("Shortest = ${vectors.min()}") // Shortest = Vec2(x=1.0, y=0.0)
}

