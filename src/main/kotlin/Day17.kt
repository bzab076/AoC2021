class Day17 : AbstractDay(17) {

    private val xTarget = 48..70
    private val yTarget = -189..-148

    private var part1result = 0
    private var part2result = 0

    override fun partOne(): Number {
        simulateProbe()
        return part1result
    }

    override fun partTwo(): Number {
        return part2result
    }

    private fun simulateProbe() {

        var maxxY = 0 // maximum y overall
        var initialVelocityCount = 0
        val maxIter = 1000

        for(xinitialVel in 1..100)
            for(yinitialVel in -500..500) {

                var maxY = 0 // maximum y for this initial velocity
                var xVel = xinitialVel
                var yVel = yinitialVel
                var x = 0
                var y = 0

                var iter = 0
                while((x !in xTarget || y !in yTarget) && iter<maxIter) {
                    x += xVel
                    y += yVel
                    if(xVel>0) xVel--
                    if(xVel<0) xVel++
                    yVel--
                    if(y>maxY)  maxY = y
                    iter ++
                }

                if((x in xTarget) && (y in yTarget)) {
                    println("target reached  ${xinitialVel}, ${yinitialVel}")
                    if(maxY > maxxY) maxxY = maxY
                    initialVelocityCount++
                }

            }

        part1result = maxxY
        part2result = initialVelocityCount
    }
}