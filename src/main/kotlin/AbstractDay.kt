import java.io.File

abstract class AbstractDay (private val dayNumber: Int) {

    abstract fun partOne(): Number

    abstract fun partTwo(): Number

    fun inputLines(): List<String> {

        return File(javaClass.classLoader.getResource("day${dayNumber.toString().padStart(2, '0')}.txt").toURI()).readLines()

    }

    fun inputString(): String {

        return File(javaClass.classLoader.getResource("day${dayNumber.toString().padStart(2, '0')}.txt").toURI()).readText()

    }

    fun inputNumbers(): List<Int> {

        return inputLines().map { it -> it.toInt() }

    }

}