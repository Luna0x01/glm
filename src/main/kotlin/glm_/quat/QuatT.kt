package glm_.quat

/**
 * Created by GBarbieri on 08.11.2016.
 */

abstract class QuatT<T : Number>(_w: T, _x: T, _y: T, _z: T) {

    @JvmField
    var w = _w

    @JvmField
    var x = _x

    @JvmField
    var y = _y

    @JvmField
    var z = _z

    inline operator fun component1() = x
    inline operator fun component2() = y
    inline operator fun component3() = z
    inline operator fun component4() = w

    inline operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> throw IndexOutOfBoundsException()
    }

    abstract operator fun set(index: Int, value: Number)
}