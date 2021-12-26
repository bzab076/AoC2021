import java.lang.Integer.max
import java.lang.Integer.min

class Day22 : AbstractDay(22) {

    private val inputCuboids = inputLines().map { parseCommand(it) }

    override fun partOne(): Number {
        return computeVolume(50)
    }

    override fun partTwo(): Number {
        return computeVolume(0)
    }

    private fun computeVolume(limit : Int): Long {

        val interval = IntRange(-limit,limit)
        val cuboids = if(limit == 0) inputCuboids
            else inputCuboids.filter { interval.contains(it.xRange.first) && interval.contains(it.xRange.last) &&
                interval.contains(it.yRange.first) && interval.contains(it.yRange.last) &&
                interval.contains(it.zRange.first) && interval.contains(it.zRange.last) }

        val intersections = mutableListOf<Cuboid>()
        cuboids.forEach { cuboid ->
            intersections.addAll(intersections.mapNotNull { it.intersect(cuboid) })
            if (cuboid.on) intersections.add(cuboid)
        }

        return intersections.sumOf { it.volume() }
    }

    private fun parseCommand(command : String) : Cuboid {

        val cmd : Boolean = command.startsWith("on")
        val coords = command.split(" ").last().split(",")
        var xRange = IntRange(0,0)
        var yRange = IntRange(0,0)
        var zRange = IntRange(0,0)

        coords.forEach {
            if(it.startsWith("x=")) xRange = IntRange(it.drop(2).split("..").first().toInt(), it.drop(2).split("..").last().toInt())
            if(it.startsWith("y=")) yRange = IntRange(it.drop(2).split("..").first().toInt(), it.drop(2).split("..").last().toInt())
            if(it.startsWith("z=")) zRange = IntRange(it.drop(2).split("..").first().toInt(), it.drop(2).split("..").last().toInt())
        }

        return Cuboid(cmd, xRange,yRange,zRange)
    }

    class Cuboid(val on : Boolean, val xRange : IntRange, val yRange : IntRange, val zRange : IntRange) {

        fun volume() : Long = xRange.count().toLong() * yRange.count().toLong() * zRange.count().toLong() *  if (on) 1 else -1

        fun overlaps(other : Cuboid) : Boolean =
                (other.xRange.first <= xRange.last && other.xRange.last >= xRange.first) &&
                (other.yRange.first <= yRange.last && other.yRange.last >= yRange.first) &&
                (other.zRange.first <= zRange.last && other.zRange.last >= zRange.first)

        fun intersect(other : Cuboid) : Cuboid? =
            if(!overlaps(other)) null
            else Cuboid(!on, IntRange(max(xRange.first,other.xRange.first),min(xRange.last,other.xRange.last)),
                IntRange(max(yRange.first,other.yRange.first),min(yRange.last,other.yRange.last)),
                IntRange(max(zRange.first,other.zRange.first),min(zRange.last,other.zRange.last)))
    }
}