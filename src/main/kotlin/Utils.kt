import java.lang.Exception

typealias Point = Pair<Int, Int>

fun <T, U> cartesianProduct(c1: Collection<T>, c2: Collection<U>): List<Pair<T, U>> {
    return c1.flatMap { lhsElem -> c2.map { rhsElem -> lhsElem to rhsElem } }
}


class Point3D (val x : Int, val y : Int, val z : Int){

    fun transform(orientation : Int) =
        when(orientation % 24) {
            0 -> Point3D(x, y, z)
            1 -> Point3D(x, -y, -z)
            2 -> Point3D(x, z, -y)
            3 -> Point3D(x, -z, y)
            4 -> Point3D(-x, y, -z)
            5 -> Point3D(-x, -y, z)
            6 -> Point3D(-x, z, y)
            7 -> Point3D(-x, -z, -y)
            8 -> Point3D(y, x, -z)
            9 -> Point3D(y, -x, z)
            10 -> Point3D(y, z, x)
            11 -> Point3D(y, -z, -x)
            12 -> Point3D(-y, x, z)
            13 -> Point3D(-y, -x, -z)
            14 -> Point3D(-y, z, -x)
            15 -> Point3D(-y, -z, x)
            16 -> Point3D(z, x, y)
            17 -> Point3D(z, -x, -y)
            18 -> Point3D(z, y, -x)
            19 -> Point3D(z, -y, x)
            20 -> Point3D(-z, x, -y)
            21 -> Point3D(-z, -x, y)
            22 -> Point3D(-z, y, x)
            23 -> Point3D(-z, -y, -x)
            else -> throw Exception("Invalid orientation")
        }

    fun diff(otherPoint : Point3D) : Point3D = Point3D(this.x - otherPoint.x, this.y - otherPoint.y, this.z - otherPoint.z)
    fun add(otherPoint : Point3D) : Point3D = Point3D(this.x + otherPoint.x, this.y + otherPoint.y, this.z + otherPoint.z)

    fun longID() : Long = x.toLong() + 10000*y.toLong() + 100000000*z.toLong()

    override fun equals(other: Any?): Boolean {
        return other is Point3D && x==other.x && y==other.y && z==other.z
    }

    override fun hashCode(): Int {
        return longID().toInt()
    }

}