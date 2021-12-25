class Day24 : AbstractDay(24) {

    private val validZMaps = Array<MutableMap<Int, MutableSet<Int>>>(14) { mutableMapOf() }
    private val parameterList = mutableListOf<Parameters>()

    override fun partOne(): Number {
        parseParameters()
        constructZMaps()
        return findSerialNumbers(0,0).maxOf { it.toLong() }
    }

    override fun partTwo(): Number {
        return findSerialNumbers(0,0).minOf { it.toLong() }
    }

    private fun computeZ(w : Int, initialZ: Int, params: Parameters): Int {
        var z = initialZ
        var x = 0
        x += z
        x %= 26
        z /= params.zDiv
        x += params.xAdd
        x = if (x == w ) 1 else 0
        x = if(x == 0) 1 else 0
        var y = 25
        y *= x
        y++
        z *= y
        y = w
        y += params.yAdd
        y *= x
        z += y
        return z
    }

    private fun constructZMaps() {
        var zRange = setOf(0)
        var idx = parameterList.size - 1

        parameterList.reversed().forEach { args ->
            val validZ = mutableSetOf<Int>()
            for (w in 1..9) {
                for (z in 0..10000000) {
                    if (computeZ(w, z, args) in zRange) {
                        val set = validZMaps[idx].getOrPut(w) { mutableSetOf() }
                        set.add(z)
                        validZ.add(z)
                    }
                }
            }
            if (validZ.isEmpty()) {
                println("No valid z for input input[$idx]?")
            }
            idx--
            zRange = validZ
        }
    }

    private fun findSerialNumbers(index: Int, z: Int): List<String> {
        if (index == 14) return listOf("")

        val wMap = validZMaps[index].entries.filter { z in it.value }
        return wMap.flatMap { (w, _) ->
            val newZ = computeZ(w, z, parameterList[index])

            findSerialNumbers(index + 1, newZ).map { w.toString() + it }
        }
    }

    private fun parseParameters() {

        var divz = 0
        var addx = 0
        var addy = 0

        inputLines().forEachIndexed { index, line ->

            if(index % 18 == 4) {
                divz = line.split(" ").last().toInt()
            }
            if(index % 18 == 5) {
                addx = line.split(" ").last().toInt()
            }
            if(index % 18 == 15) {
                addy = line.split(" ").last().toInt()
            }
            if(index % 18 == 17) {
                parameterList.add(Parameters(divz, addx, addy))
            }

        }
    }

}

data class Parameters(val zDiv: Int, val xAdd: Int, val yAdd: Int)
