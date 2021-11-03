import java.io.File
import java.util.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}\n\n\n")

    var result: Number
    var time: Long

    if(args.isNullOrEmpty()) {
        // run all available days


        File("./src/main/resources").list().forEach {
            val className = it.substringBefore('.')
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

            val dayClass = Class.forName(className)?.getDeclaredConstructor()?.newInstance() as AbstractDay

            try {

                time = measureTimeMillis { result = dayClass.partOne() }
                println("Result for day${className.substring(3,5)} part one is ${result}   (${time} ms)")
                time = measureTimeMillis { result = dayClass.partTwo() }
                println("Result for day${className.substring(3,5)} part two is ${result}   (${time} ms)\n")
            }
            catch (e: NotImplementedError) { }

        }


    }

    else {

        // run a specific day passed as an argument

        val className = "Day${args.joinToString()}"
        val dayClass = Class.forName(className)?.getDeclaredConstructor()?.newInstance() as AbstractDay

        try {
            time = measureTimeMillis { result = dayClass.partOne() }
            println("Result for day${args.joinToString()} part one is ${result}   (${time} ms)")
            time = measureTimeMillis { result = dayClass.partTwo() }
            println("Result for day${args.joinToString()} part two is ${result}   (${time} ms)\n")
            //println("Result for day${args.joinToString()} part one is ${dayClass.partOne()}")
            //println("Result for day${args.joinToString()} part two is ${dayClass.partTwo()}")
        }
        catch (e: NotImplementedError) { }
    }


}