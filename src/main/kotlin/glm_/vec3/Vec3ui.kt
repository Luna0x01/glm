package glm_.vec3

import glm_.*
import glm_.vec1.Vec1bool
import glm_.vec1.Vec1t
import glm_.vec2.Vec2bool
import glm_.vec2.Vec2t
import glm_.vec2.Vec2ui
import glm_.vec3.operators.vec3ui_operators
import glm_.vec4.Vec4bool
import glm_.vec4.Vec4t
import kool.*
import unsigned.Uint
import unsigned.UintArray
import unsigned.toUint
import java.io.PrintStream
import java.nio.*

/**
 * Created by elect on 09/10/16.
 */

class Vec3ui(@JvmField var ofs: Int, var array: UintArray) : Vec3t<Uint>, ToIntBuffer {

    inline var x: Uint
        get() = array[ofs]
        set(value) = array.set(ofs, value)
    inline var y: Uint
        get() = array[ofs + 1]
        set(value) = array.set(ofs + 1, value)
    inline var z: Uint
        get() = array[ofs + 2]
        set(value) = array.set(ofs + 2, value)

    inline var vX: Int
        get() = array[ofs].toInt()
        set(value) = array.set(ofs, value.toUint())
    inline var vY: Int
        get() = array[ofs + 1].toInt()
        set(value) = array.set(ofs + 1, value.toUint())
    inline var vZ: Int
        get() = array[ofs + 2].toInt()
        set(value) = array.set(ofs + 2, value.toUint())

    // -- Implicit basic constructors --

    constructor() : this(0, 0, 0)
    constructor(v: Vec3ui) : this(v.x, v.y, v.z)
    constructor(v: Vec2ui) : this(v.x, v.y, Uint(0))

    // -- Explicit basic constructors --

    @JvmOverloads
    constructor(x: Uint, y: Uint = x, z: Uint = x) : this(0, UintArray(intArrayOf(x.i, y.i, z.i)))

    @JvmOverloads
    constructor(x: Int, y: Int = x, z: Int = x) : this(0, UintArray(intArrayOf(x, y, z)))

    // -- Conversion scalar constructors --

    constructor(v: Vec1t<out Number>) : this(v._x)

    // Explicit conversions (From section 5.4.1 Conversion and scalar constructors of GLSL 1.30.08 specification)

    constructor(v: Number) : this(v.ui)
    constructor(x: Number, y: Number, z: Number) : this(x.ui, y.ui, z.ui)

    constructor(x: Vec1t<out Number>, y: Number, z: Number) : this(x._x, y, z)
    constructor(x: Number, y: Vec1t<out Number>, z: Number) : this(x, y._x, z)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Number) : this(x._x, y._x, z)
    constructor(x: Number, y: Number, z: Vec1t<out Number>) : this(x, y, z._x)
    constructor(x: Vec1t<out Number>, y: Number, z: Vec1t<out Number>) : this(x._x, y, z._x)
    constructor(x: Number, y: Vec1t<out Number>, z: Vec1t<out Number>) : this(x, y._x, z._x)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Vec1t<out Number>) : this(x._x, y._x, z._x)

    // -- Conversion vector constructors --

    // Explicit conversions (From section 5.4.1 Conversion and scalar constructors of GLSL 1.30.08 specification)

    @JvmOverloads
    constructor(xy: Vec2t<out Number>, z: Number = 0) : this(xy._x, xy._y, z)

    constructor(xy: Vec2t<out Number>, z: Vec1t<out Number>) : this(xy._x, xy._y, z._x)
    constructor(x: Number, yz: Vec2t<out Number>) : this(x, yz._x, yz._y)
    constructor(x: Vec1t<out Number>, yz: Vec2t<out Number>) : this(x._x, yz._x, yz._y)
    constructor(v: Vec3t<out Number>) : this(v._x, v._y, v._z)
    constructor(v: Vec4t<out Number>) : this(v._x, v._y, v._z)

    constructor(v: Vec1bool) : this(v.x.ui, 0, 0)
    constructor(v: Vec2bool) : this(v.x.ui, v.y.ui, 0)
    constructor(v: Vec3bool) : this(v.x.ui, v.y.ui, v.z.ui)
    constructor(v: Vec4bool) : this(v.x.ui, v.y.ui, v.z.ui)

    constructor(bytes: ByteArray, index: Int = 0, oneByteOneUint: Boolean = false, bigEndian: Boolean = true) : this(
            if (oneByteOneUint) bytes[index].ui else bytes.getUint(index, bigEndian),
            if (oneByteOneUint) bytes[index + 1].ui else bytes.getUint(index + Uint.BYTES, bigEndian),
            if (oneByteOneUint) bytes[index + 2].ui else bytes.getUint(index + Uint.BYTES * 2, bigEndian))

    constructor(chars: CharArray, index: Int = 0) : this(chars[index].ui, chars[index + 1].ui, chars[index + 2].ui)
    constructor(shorts: ShortArray, index: Int = 0) : this(shorts[index], shorts[index + 1], shorts[index + 2])
    constructor(ints: IntArray, index: Int = 0) : this(ints[index], ints[index + 1], ints[index + 2])
    constructor(longs: LongArray, index: Int = 0) : this(longs[index], longs[index + 1], longs[index + 2])
    constructor(floats: FloatArray, index: Int = 0) : this(floats[index], floats[index + 1], floats[index + 2])
    constructor(doubles: DoubleArray, index: Int = 0) : this(doubles[index], doubles[index + 1], doubles[index + 2])
    constructor(booleans: BooleanArray, index: Int = 0) : this(booleans[index].ui, booleans[index + 1].ui, booleans[index + 2].ui)

    constructor(numbers: Array<out Number>, index: Int = 0) : this(numbers[index], numbers[index + 1], numbers[index + 2])
    constructor(chars: Array<Char>, index: Int = 0) : this(chars[index].ui, chars[index + 1].ui, chars[index + 2].ui)
    constructor(booleans: Array<Boolean>, index: Int = 0) : this(booleans[index].ui, booleans[index + 1].ui, booleans[index + 2].ui)

    constructor(list: Iterable<*>, index: Int = 0) : this(list.elementAt(index)!!.toInt, list.elementAt(index + 1)!!.toInt,
            list.elementAt(index + 2)!!.toInt)

    constructor(bytes: ByteBuffer, index: Int = bytes.pos, oneByteOneUint: Boolean = false) : this(
            if (oneByteOneUint) bytes[index].ui else bytes.getInt(index).ui,
            if (oneByteOneUint) bytes[index + 1].ui else bytes.getInt(index + Uint.BYTES).ui,
            if (oneByteOneUint) bytes[index + 2].ui else bytes.getInt(index + Uint.BYTES * 2).ui)

    constructor(chars: CharBuffer, index: Int = chars.pos) : this(chars[index].ui, chars[index + 1].ui, chars[index + 2].ui)
    constructor(shorts: ShortBuffer, index: Int = shorts.pos) : this(shorts[index], shorts[index + 1], shorts[index + 2])
    constructor(ints: IntBuffer, index: Int = ints.pos) : this(ints[index], ints[index + 1], ints[index + 2])
    constructor(longs: LongBuffer, index: Int = longs.pos) : this(longs[index], longs[index + 1], longs[index + 2])
    constructor(floats: FloatBuffer, index: Int = floats.pos) : this(floats[index], floats[index + 1], floats[index + 2])
    constructor(doubles: DoubleBuffer, index: Int = doubles.pos) : this(doubles[index], doubles[index + 1], doubles[index + 2])

    constructor(block: (Int) -> Uint) : this(block(0), block(1), block(2))
    constructor(ptr: Ptr<Uint>) : this() {
        val p = ptr.toPtr<Int>()
        x.v = p[0]
        y.v = p[1]
        z.v = p[2]
    }


    fun set(bytes: ByteArray, index: Int = 0, oneByteOneUint: Boolean = false, bigEndian: Boolean = true) {
        x.v = if (oneByteOneUint) bytes[index].i else bytes.getInt(index, bigEndian)
        y.v = if (oneByteOneUint) bytes[index + 1].i else bytes.getInt(index + Uint.BYTES, bigEndian)
        z.v = if (oneByteOneUint) bytes[index + 2].i else bytes.getInt(index + Uint.BYTES * 2, bigEndian)
    }

    fun set(bytes: ByteBuffer, index: Int = bytes.pos, oneByteOneUint: Boolean = false) {
        x.v = if (oneByteOneUint) bytes[index].i else bytes.getInt(index)
        y.v = if (oneByteOneUint) bytes[index + 1].i else bytes.getInt(index + Uint.BYTES)
        z.v = if (oneByteOneUint) bytes[index + 2].i else bytes.getInt(index + Uint.BYTES * 2)
    }


    fun put(x: Uint, y: Uint, z: Uint) {
        this.x = x
        this.y = y
        this.z = z
    }

    operator fun invoke(x: Uint, y: Uint, z: Uint): Vec3ui {
        this.x = x
        this.y = y
        this.z = z
        return this
    }

    fun put(x: Int, y: Int, z: Int) {
        this.x.v = x
        this.y.v = y
        this.z.v = z
    }

    operator fun invoke(x: Int, y: Int, z: Int): Vec3ui {
        this.x.v = x
        this.y.v = y
        this.z.v = z
        return this
    }

    override fun put(x: Number, y: Number, z: Number) {
        this.x = x.ui
        this.y = y.ui
        this.z = z.ui
    }

    override operator fun invoke(x: Number, y: Number, z: Number): Vec3ui {
        this.x = x.ui
        this.y = y.ui
        this.z = z.ui
        return this
    }

    fun to(bytes: ByteArray, index: Int) = to(bytes, index, true)
    override fun to(bytes: ByteArray, index: Int, bigEndian: Boolean): ByteArray {
        bytes.putInt(index, x.v)
        bytes.putInt(index + Int.BYTES, y.v)
        bytes.putInt(index + Int.BYTES * 2, z.v)
        return bytes
    }

    fun toIntArray(): IntArray = to(IntArray(length), 0)
    infix fun to(ints: IntArray): IntArray = to(ints, 0)
    fun to(ints: IntArray, index: Int): IntArray {
        System.arraycopy(array, ofs, ints, index, length)
        return ints
    }

    override fun to(buf: ByteBuffer, offset: Int): ByteBuffer {
        buf.putInt(offset, x.v)
        buf.putInt(offset + Int.BYTES, y.v)
        buf.putInt(offset + Int.BYTES * 2, z.v)
        return buf
    }

    fun to(buf: IntBuffer, index: Int): IntBuffer {
        buf[index] = x.v
        buf[index + 1] = y.v
        buf[index + 2] = z.v
        return buf
    }

    infix fun to(ptr: Ptr<Uint>) {
        val p = ptr.toPtr<Int>()
        p[0] = x.v
        p[1] = y.v
        p[2] = z.v
    }

    // -- Component accesses --

    override operator fun set(index: Int, value: Number) = when (index) {
        0 -> x = value.ui
        1 -> y = value.ui
        2 -> z = value.ui
        else -> throw ArrayIndexOutOfBoundsException()
    }


    // -- Unary arithmetic operators --

    operator fun unaryPlus() = this

    // no unaryMinus operator, only signed

    // -- Increment main.and decrement operators --

    operator fun inc(res: Vec3ui = Vec3ui()) = plus(res, this, 1, 1, 1)
    fun incAssign() = plus(this, this, 1, 1, 1)


    operator fun dec(res: Vec3ui = Vec3ui()) = minus(res, this, 1, 1, 1)
    fun decAssign() = minus(this, this, 1, 1, 1)


    // -- Specific binary arithmetic operators --

    operator fun plus(b: Uint) = plus(Vec3ui(), this, b, b, b)
    operator fun plus(b: Int) = plus(Vec3ui(), this, b, b, b)
    operator fun plus(b: Vec3ui) = plus(Vec3ui(), this, b.x, b.y, b.z)

    fun plus(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = plus(res, this, bX, bY, bZ)
    fun plus(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = plus(res, this, bX, bY, bZ)
    fun plus(b: Uint, res: Vec3ui = Vec3ui()) = plus(res, this, b, b, b)
    fun plus(b: Int, res: Vec3ui = Vec3ui()) = plus(res, this, b, b, b)
    fun plus(b: Vec3ui, res: Vec3ui = Vec3ui()) = plus(res, this, b.x, b.y, b.z)

    fun plusAssign(bX: Uint, bY: Uint, bZ: Uint) = plus(this, this, bX, bY, bZ)
    fun plusAssign(bX: Int, bY: Int, bZ: Int) = plus(this, this, bX, bY, bZ)
    infix operator fun plusAssign(b: Uint) {
        plus(this, this, b, b, b)
    }

    infix operator fun plusAssign(b: Int) {
        plus(this, this, b, b, b)
    }

    infix operator fun plusAssign(b: Vec3ui) {
        plus(this, this, b.x, b.y, b.z)
    }


    operator fun minus(b: Uint) = minus(Vec3ui(), this, b, b, b)
    operator fun minus(b: Int) = minus(Vec3ui(), this, b, b, b)
    operator fun minus(b: Vec3ui) = minus(Vec3ui(), this, b.x, b.y, b.z)

    fun minus(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = minus(res, this, bX, bY, bZ)
    fun minus(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = minus(res, this, bX, bY, bZ)
    fun minus(b: Uint, res: Vec3ui = Vec3ui()) = minus(res, this, b, b, b)
    fun minus(b: Int, res: Vec3ui = Vec3ui()) = minus(res, this, b, b, b)
    fun minus(b: Vec3ui, res: Vec3ui = Vec3ui()) = minus(res, this, b.x, b.y, b.z)

    fun minusAssign(bX: Uint, bY: Uint, bZ: Uint) = minus(this, this, bX, bY, bZ)
    fun minusAssign(bX: Int, bY: Int, bZ: Int) = minus(this, this, bX, bY, bZ)
    infix operator fun minusAssign(b: Uint) {
        minus(this, this, b, b, b)
    }

    infix operator fun minusAssign(b: Int) {
        minus(this, this, b, b, b)
    }

    infix operator fun minusAssign(b: Vec3ui) {
        minus(this, this, b.x, b.y, b.z)
    }


    operator fun times(b: Uint) = times(Vec3ui(), this, b, b, b)
    operator fun times(b: Int) = times(Vec3ui(), this, b, b, b)
    operator fun times(b: Vec3ui) = times(Vec3ui(), this, b.x, b.y, b.z)

    fun times(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = times(res, this, bX, bY, bZ)
    fun times(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = times(res, this, bX, bY, bZ)
    fun times(b: Uint, res: Vec3ui = Vec3ui()) = times(res, this, b, b, b)
    fun times(b: Int, res: Vec3ui = Vec3ui()) = times(res, this, b, b, b)
    fun times(b: Vec3ui, res: Vec3ui = Vec3ui()) = times(res, this, b.x, b.y, b.z)

    fun timesAssign(bX: Uint, bY: Uint, bZ: Uint) = times(this, this, bX, bY, bZ)
    fun timesAssign(bX: Int, bY: Int, bZ: Int) = times(this, this, bX, bY, bZ)
    infix operator fun timesAssign(b: Uint) {
        times(this, this, b, b, b)
    }

    infix operator fun timesAssign(b: Int) {
        times(this, this, b, b, b)
    }

    infix operator fun timesAssign(b: Vec3ui) {
        times(this, this, b.x, b.y, b.z)
    }


    operator fun div(b: Uint) = div(Vec3ui(), this, b, b, b)
    operator fun div(b: Int) = div(Vec3ui(), this, b, b, b)
    operator fun div(b: Vec3ui) = div(Vec3ui(), this, b.x, b.y, b.z)

    fun div(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = div(res, this, bX, bY, bZ)
    fun div(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = div(res, this, bX, bY, bZ)
    fun div(b: Uint, res: Vec3ui) = div(res, this, b, b, b)
    fun div(b: Int, res: Vec3ui) = div(res, this, b, b, b)
    fun div(b: Vec3ui, res: Vec3ui) = div(res, this, b.x, b.y, b.z)

    fun divAssign(bX: Uint, bY: Uint, bZ: Uint) = div(this, this, bX, bY, bZ)
    fun divAssign(bX: Int, bY: Int, bZ: Int) = div(this, this, bX, bY, bZ)
    infix operator fun divAssign(b: Uint) {
        div(this, this, b, b, b)
    }

    infix operator fun divAssign(b: Int) {
        div(this, this, b, b, b)
    }

    infix operator fun divAssign(b: Vec3ui) {
        div(this, this, b.x, b.y, b.z)
    }


    operator fun rem(b: Uint) = rem(Vec3ui(), this, b, b, b)
    operator fun rem(b: Int) = rem(Vec3ui(), this, b, b, b)
    operator fun rem(b: Vec3ui) = rem(Vec3ui(), this, b.x, b.y, b.z)

    fun rem(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = rem(res, this, bX, bY, bZ)
    fun rem(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = rem(res, this, bX, bY, bZ)
    fun rem(b: Uint, res: Vec3ui) = rem(res, this, b, b, b)
    fun rem(b: Int, res: Vec3ui) = rem(res, this, b, b, b)
    fun rem(b: Vec3ui, res: Vec3ui) = rem(res, this, b.x, b.y, b.z)

    fun remAssign(bX: Uint, bY: Uint, bZ: Uint) = rem(this, this, bX, bY, bZ)
    fun remAssign(bX: Int, bY: Int, bZ: Int) = rem(this, this, bX, bY, bZ)
    infix operator fun remAssign(b: Uint) {
        rem(this, this, b, b, b)
    }

    infix operator fun remAssign(b: Int) {
        rem(this, this, b, b, b)
    }

    infix operator fun remAssign(b: Vec3ui) {
        rem(this, this, b.x, b.y, b.z)
    }


    // -- Generic binary arithmetic operators --

    operator fun plus(b: Number) = plus(Vec3ui(), this, b.i, b.i, b.i)
    operator fun plus(b: Vec3t<out Number>) = plus(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    fun plus(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = plus(res, this, bX.i, bY.i, bZ.i)
    fun plus(b: Number, res: Vec3ui = Vec3ui()) = plus(res, this, b.i, b.i, b.i)
    fun plus(b: Vec3t<out Number>, res: Vec3ui = Vec3ui()) = plus(res, this, b._x.i, b._y.i, b._z.i)

    fun plusAssign(bX: Number, bY: Number, bZ: Number) = plus(this, this, bX.i, bY.i, bZ.i)
    infix operator fun plusAssign(b: Number) {
        plus(this, this, b.i, b.i, b.i)
    }

    infix operator fun plusAssign(b: Vec3t<out Number>) {
        plus(this, this, b._x.i, b._y.i, b._z.i)
    }


    operator fun minus(b: Number) = minus(Vec3ui(), this, b.i, b.i, b.i)
    operator fun minus(b: Vec3t<out Number>) = minus(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    fun minus(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = minus(res, this, bX.i, bY.i, bZ.i)
    fun minus(b: Number, res: Vec3ui = Vec3ui()) = minus(res, this, b.i, b.i, b.i)
    fun minus(b: Vec3t<out Number>, res: Vec3ui = Vec3ui()) = minus(res, this, b._x.i, b._y.i, b._z.i)

    fun minusAssign(bX: Number, bY: Number, bZ: Number) = minus(this, this, bX.i, bY.i, bZ.i)
    infix operator fun minusAssign(b: Number) {
        minus(this, this, b.i, b.i, b.i)
    }

    infix operator fun minusAssign(b: Vec3t<out Number>) {
        minus(this, this, b._x.i, b._y.i, b._z.i)
    }


    operator fun times(b: Number) = times(Vec3ui(), this, b.i, b.i, b.i)
    operator fun times(b: Vec3t<out Number>) = times(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    fun times(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = times(res, this, bX.i, bY.i, bZ.i)
    fun times(b: Number, res: Vec3ui = Vec3ui()) = times(res, this, b.i, b.i, b.i)
    fun times(b: Vec3t<out Number>, res: Vec3ui = Vec3ui()) = times(res, this, b._x.i, b._y.i, b._z.i)

    fun timesAssign(bX: Number, bY: Number, bZ: Number) = times(this, this, bX.i, bY.i, bZ.i)
    infix operator fun timesAssign(b: Number) {
        times(this, this, b.i, b.i, b.i)
    }

    infix operator fun timesAssign(b: Vec3t<out Number>) {
        times(this, this, b._x.i, b._y.i, b._z.i)
    }


    operator fun div(b: Number) = div(Vec3ui(), this, b.i, b.i, b.i)
    operator fun div(b: Vec3t<out Number>) = div(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    fun div(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = div(res, this, bX.i, bY.i, bZ.i)
    fun div(b: Number, res: Vec3ui) = div(res, this, b.i, b.i, b.i)
    fun div(b: Vec3t<out Number>, res: Vec3ui) = div(res, this, b._x.i, b._y.i, b._z.i)

    fun divAssign(bX: Number, bY: Number, bZ: Number) = div(this, this, bX.i, bY.i, bZ.i)
    infix operator fun divAssign(b: Number) {
        div(this, this, b.i, b.i, b.i)
    }

    infix operator fun divAssign(b: Vec3t<out Number>) {
        div(this, this, b._x.i, b._y.i, b._z.i)
    }


    operator fun rem(b: Number) = rem(Vec3ui(), this, b.i, b.i, b.i)
    operator fun rem(b: Vec3t<out Number>) = rem(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    fun rem(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = rem(res, this, bX.i, bY.i, bZ.i)
    fun rem(b: Number, res: Vec3ui) = rem(res, this, b.i, b.i, b.i)
    fun rem(b: Vec3t<out Number>, res: Vec3ui) = rem(res, this, b._x.i, b._y.i, b._z.i)

    fun remAssign(bX: Number, bY: Number, bZ: Number) = rem(this, this, bX.i, bY.i, bZ.i)
    infix operator fun remAssign(b: Number) {
        rem(this, this, b.i, b.i, b.i)
    }

    infix operator fun remAssign(b: Vec3t<out Number>) {
        rem(this, this, b._x.i, b._y.i, b._z.i)
    }


    // -- Specific bitwise operators --

    infix fun and(b: Uint) = and(Vec3ui(), this, b, b, b)
    infix fun and(b: Int) = and(Vec3ui(), this, b, b, b)
    infix fun and(b: Vec3ui) = and(Vec3ui(), this, b.x, b.y, b.z)

    infix fun andAssign(b: Uint) = and(this, this, b, b, b)
    infix fun andAssign(b: Int) = and(this, this, b, b, b)
    infix fun andAssign(b: Vec3ui) = and(this, this, b.x, b.y, b.z)

    fun and(b: Uint, res: Vec3ui) = and(res, this, b, b, b)
    fun and(b: Int, res: Vec3ui) = and(res, this, b, b, b)
    fun and(b: Vec3ui, res: Vec3ui) = and(res, this, b.x, b.y, b.z)

    fun and(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = and(res, this, bX, bY, bZ)
    fun and(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = and(res, this, bX, bY, bZ)

    fun andAssign(bX: Uint, bY: Uint, bZ: Uint) = and(this, this, bX, bY, bZ)
    fun andAssign(bX: Int, bY: Int, bZ: Int) = and(this, this, bX, bY, bZ)


    infix fun or(b: Uint) = or(Vec3ui(), this, b, b, b)
    infix fun or(b: Int) = or(Vec3ui(), this, b, b, b)
    infix fun or(b: Vec3ui) = or(Vec3ui(), this, b.x, b.y, b.z)

    infix fun orAssign(b: Uint) = or(this, this, b, b, b)
    infix fun orAssign(b: Int) = or(this, this, b, b, b)
    infix fun orAssign(b: Vec3ui) = or(this, this, b.x, b.y, b.z)

    fun or(b: Uint, res: Vec3ui) = or(res, this, b, b, b)
    fun or(b: Int, res: Vec3ui) = or(res, this, b, b, b)
    fun or(b: Vec3ui, res: Vec3ui) = or(res, this, b.x, b.y, b.z)

    fun or(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = or(res, this, bX, bY, bZ)
    fun or(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = or(res, this, bX, bY, bZ)

    fun orAssign(bX: Uint, bY: Uint, bZ: Uint) = or(this, this, bX, bY, bZ)
    fun orAssign(bX: Int, bY: Int, bZ: Int) = or(this, this, bX, bY, bZ)


    infix fun xor(b: Uint) = xor(Vec3ui(), this, b, b, b)
    infix fun xor(b: Int) = xor(Vec3ui(), this, b, b, b)
    infix fun xor(b: Vec3ui) = xor(Vec3ui(), this, b.x, b.y, b.z)

    infix fun xorAssign(b: Uint) = xor(this, this, b, b, b)
    infix fun xorAssign(b: Int) = xor(this, this, b, b, b)
    infix fun xorAssign(b: Vec3ui) = xor(this, this, b.x, b.y, b.z)

    fun xor(b: Uint, res: Vec3ui) = xor(res, this, b, b, b)
    fun xor(b: Int, res: Vec3ui) = xor(res, this, b, b, b)
    fun xor(b: Vec3ui, res: Vec3ui) = xor(res, this, b.x, b.y, b.z)

    fun xor(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = xor(res, this, bX, bY, bZ)
    fun xor(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = xor(res, this, bX, bY, bZ)

    fun xorAssign(bX: Uint, bY: Uint, bZ: Uint) = xor(this, this, bX, bY, bZ)
    fun xorAssign(bX: Int, bY: Int, bZ: Int) = xor(this, this, bX, bY, bZ)


    infix fun shl(b: Uint) = shl(Vec3ui(), this, b, b, b)
    infix fun shl(b: Int) = shl(Vec3ui(), this, b, b, b)
    infix fun shl(b: Vec3ui) = shl(Vec3ui(), this, b.x, b.y, b.z)

    infix fun shlAssign(b: Uint) = shl(this, this, b, b, b)
    infix fun shlAssign(b: Int) = shl(this, this, b, b, b)
    infix fun shlAssign(b: Vec3ui) = shl(this, this, b.x, b.y, b.z)

    fun shl(b: Uint, res: Vec3ui) = shl(res, this, b, b, b)
    fun shl(b: Int, res: Vec3ui) = shl(res, this, b, b, b)
    fun shl(b: Vec3ui, res: Vec3ui) = shl(res, this, b.x, b.y, b.z)

    fun shl(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = shl(res, this, bX, bY, bZ)
    fun shl(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = shl(res, this, bX, bY, bZ)

    fun shlAssign(bX: Uint, bY: Uint, bZ: Uint) = shl(this, this, bX, bY, bZ)
    fun shlAssign(bX: Int, bY: Int, bZ: Int) = shl(this, this, bX, bY, bZ)


    infix fun shr(b: Uint) = shr(Vec3ui(), this, b, b, b)
    infix fun shr(b: Int) = shr(Vec3ui(), this, b, b, b)
    infix fun shr(b: Vec3ui) = shr(Vec3ui(), this, b.x, b.y, b.z)

    infix fun shrAssign(b: Uint) = shr(this, this, b, b, b)
    infix fun shrAssign(b: Int) = shr(this, this, b, b, b)
    infix fun shrAssign(b: Vec3ui) = shr(this, this, b.x, b.y, b.z)

    fun shr(b: Uint, res: Vec3ui) = shr(res, this, b, b, b)
    fun shr(b: Int, res: Vec3ui) = shr(res, this, b, b, b)
    fun shr(b: Vec3ui, res: Vec3ui) = shr(res, this, b.x, b.y, b.z)

    fun shr(bX: Uint, bY: Uint, bZ: Uint, res: Vec3ui = Vec3ui()) = shr(res, this, bX, bY, bZ)
    fun shr(bX: Int, bY: Int, bZ: Int, res: Vec3ui = Vec3ui()) = shr(res, this, bX, bY, bZ)

    fun shrAssign(bX: Uint, bY: Uint, bZ: Uint) = shr(this, this, bX, bY, bZ)
    fun shrAssign(bX: Int, bY: Int, bZ: Int) = shr(this, this, bX, bY, bZ)


    fun inv(res: Vec3ui = Vec3ui()) = inv(res, this)
    fun invAssign() = inv(this, this)


    // -- Generic bitwise operators --

    infix fun and(b: Number) = and(Vec3ui(), this, b.i, b.i, b.i)
    infix fun and(b: Vec3t<out Number>) = and(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    infix fun andAssign(b: Number) = and(this, this, b.i, b.i, b.i)
    infix fun andAssign(b: Vec3t<out Number>) = and(this, this, b._x.i, b._y.i, b._z.i)

    fun and(b: Number, res: Vec3ui) = and(res, this, b.i, b.i, b.i)
    fun and(b: Vec3t<out Number>, res: Vec3ui) = and(res, this, b._x.i, b._y.i, b._z.i)

    fun and(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = and(res, this, bX.i, bY.i, bZ.i)

    fun andAssign(bX: Number, bY: Number, bZ: Number) = and(this, this, bX.i, bY.i, bZ.i)


    infix fun or(b: Number) = or(Vec3ui(), this, b.i, b.i, b.i)
    infix fun or(b: Vec3t<out Number>) = or(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    infix fun orAssign(b: Number) = or(this, this, b.i, b.i, b.i)
    infix fun orAssign(b: Vec3t<out Number>) = or(this, this, b._x.i, b._y.i, b._z.i)

    fun or(b: Number, res: Vec3ui) = or(res, this, b.i, b.i, b.i)
    fun or(b: Vec3t<out Number>, res: Vec3ui) = or(res, this, b._x.i, b._y.i, b._z.i)

    fun or(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = or(res, this, bX.i, bY.i, bZ.i)

    fun orAssign(bX: Number, bY: Number, bZ: Number) = or(this, this, bX.i, bY.i, bZ.i)


    infix fun xor(b: Number) = xor(Vec3ui(), this, b.i, b.i, b.i)
    infix fun xor(b: Vec3t<out Number>) = xor(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    infix fun xorAssign(b: Number) = xor(this, this, b.i, b.i, b.i)
    infix fun xorAssign(b: Vec3t<out Number>) = xor(this, this, b._x.i, b._y.i, b._z.i)

    fun xor(b: Number, res: Vec3ui) = xor(res, this, b.i, b.i, b.i)
    fun xor(b: Vec3t<out Number>, res: Vec3ui) = xor(res, this, b._x.i, b._y.i, b._z.i)

    fun xor(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = xor(res, this, bX.i, bY.i, bZ.i)

    fun xorAssign(bX: Number, bY: Number, bZ: Number) = xor(this, this, bX.i, bY.i, bZ.i)


    infix fun shl(b: Number) = shl(Vec3ui(), this, b.i, b.i, b.i)
    infix fun shl(b: Vec3t<out Number>) = shl(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    infix fun shlAssign(b: Number) = shl(this, this, b.i, b.i, b.i)
    infix fun shlAssign(b: Vec3t<out Number>) = shl(this, this, b._x.i, b._y.i, b._z.i)

    fun shl(b: Number, res: Vec3ui) = shl(res, this, b.i, b.i, b.i)
    fun shl(b: Vec3t<out Number>, res: Vec3ui) = shl(res, this, b._x.i, b._y.i, b._z.i)

    fun shl(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = shl(res, this, bX.i, bY.i, bZ.i)

    fun shlAssign(bX: Number, bY: Number, bZ: Number) = shl(this, this, bX.i, bY.i, bZ.i)


    infix fun shr(b: Number) = shr(Vec3ui(), this, b.i, b.i, b.i)
    infix fun shr(b: Vec3t<out Number>) = shr(Vec3ui(), this, b._x.i, b._y.i, b._z.i)

    infix fun shrAssign(b: Number) = shr(this, this, b.i, b.i, b.i)
    infix fun shrAssign(b: Vec3t<out Number>) = shr(this, this, b._x.i, b._y.i, b._z.i)

    fun shr(b: Number, res: Vec3ui) = shr(res, this, b.i, b.i, b.i)
    fun shr(b: Vec3t<out Number>, res: Vec3ui) = shr(res, this, b._x.i, b._y.i, b._z.i)

    fun shr(bX: Number, bY: Number, bZ: Number, res: Vec3ui = Vec3ui()) = shr(res, this, bX.i, bY.i, bZ.i)

    fun shrAssign(bX: Number, bY: Number, bZ: Number) = shr(this, this, bX.i, bY.i, bZ.i)


    infix fun allLessThan(ui: Uint): Boolean = x < ui && y < ui && z < ui
    infix fun anyLessThan(ui: Uint): Boolean = x < ui || y < ui || z < ui
    infix fun lessThan(ui: Uint): Vec3bool = Vec3bool { get(it) < ui }

    infix fun allLessThanEqual(ui: Uint): Boolean = x <= ui && y <= ui && z <= ui
    infix fun anyLessThanEqual(ui: Uint): Boolean = x <= ui || y <= ui || z <= ui
    infix fun lessThanEqual(ui: Uint): Vec3bool = Vec3bool { get(it) <= ui }

    infix fun allEqual(ui: Uint): Boolean = x == ui && y == ui && z == ui
    infix fun anyEqual(ui: Uint): Boolean = x == ui || y == ui || z == ui
    infix fun equal(ui: Uint): Vec3bool = Vec3bool { get(it) == ui }

    infix fun allNotEqual(ui: Uint): Boolean = x != ui && y != ui && z != ui
    infix fun anyNotEqual(ui: Uint): Boolean = x != ui || y != ui || z != ui
    infix fun notEqual(ui: Uint): Vec3bool = Vec3bool { get(it) != ui }

    infix fun allGreaterThan(ui: Uint): Boolean = x > ui && y > ui && z > ui
    infix fun anyGreaterThan(ui: Uint): Boolean = x > ui || y > ui || z > ui
    infix fun greaterThan(ui: Uint): Vec3bool = Vec3bool { get(it) > ui }

    infix fun allGreaterThanEqual(ui: Uint): Boolean = x >= ui && y >= ui && z >= ui
    infix fun anyGreaterThanEqual(ui: Uint): Boolean = x >= ui || y >= ui || z >= ui
    infix fun greaterThanEqual(ui: Uint): Vec3bool = Vec3bool { get(it) >= ui }


    infix fun allLessThan(v: Vec3ui): Boolean = x < v.x && y < v.y && z < v.z
    infix fun anyLessThan(v: Vec3ui): Boolean = x < v.x || y < v.y || z < v.z
    infix fun lessThan(v: Vec3ui): Vec3bool = Vec3bool { get(it) < v[it] }

    infix fun allLessThanEqual(v: Vec3ui): Boolean = x <= v.x && y <= v.y && z <= v.z
    infix fun anyLessThanEqual(v: Vec3ui): Boolean = x <= v.x || y <= v.y || z <= v.z
    infix fun lessThanEqual(v: Vec3ui): Vec3bool = Vec3bool { get(it) <= v[it] }

    infix fun allEqual(v: Vec3ui): Boolean = x == v.x && y == v.y && z == v.z
    infix fun anyEqual(v: Vec3ui): Boolean = x == v.x || y == v.y || z == v.z
    infix fun equal(v: Vec3ui): Vec3bool = Vec3bool { get(it) == v[it] }

    infix fun allNotEqual(v: Vec3ui): Boolean = x != v.x && y != v.y && z != v.z
    infix fun anyNotEqual(v: Vec3ui): Boolean = x != v.x || y != v.y || z != v.z
    infix fun notEqual(v: Vec3ui): Vec3bool = Vec3bool { get(it) != v[it] }

    infix fun allGreaterThan(v: Vec3ui): Boolean = x > v.x && y > v.y && z > v.z
    infix fun anyGreaterThan(v: Vec3ui): Boolean = x > v.x || y > v.y || z > v.z
    infix fun greaterThan(v: Vec3ui): Vec3bool = Vec3bool { get(it) > v[it] }

    infix fun allGreaterThanEqual(v: Vec3ui): Boolean = x >= v.x && y >= v.y && z >= v.z
    infix fun anyGreaterThanEqual(v: Vec3ui): Boolean = x >= v.x || y >= v.y || z >= v.z
    infix fun greaterThanEqual(v: Vec3ui): Vec3bool = Vec3bool { get(it) >= v[it] }


    companion object : vec3ui_operators {
        const val length = 3
        @JvmField
        val size = length * Uint.BYTES

        @JvmStatic
        fun fromPointer(ptr: Ptr<Uint>) = Vec3ui(ptr)
    }

    override fun size() = size

    override fun equals(other: Any?) = other is Vec3ui && this[0] == other[0] && this[1] == other[1] && this[2] == other[2]
    override fun hashCode() = 31 * (31 * x.v.hashCode() + y.v.hashCode()) + z.v.hashCode()

    @JvmOverloads
    fun print(name: String = "", stream: PrintStream = System.out) = stream.print("$name$this")

    @JvmOverloads
    fun println(name: String = "", stream: PrintStream = System.out) = stream.println("$name$this")


    //@formatter:off
    override inline var _x get() = x; set(value) { x = value }
    override inline var r get() = x; set(value) { x = value }
    override inline var s get() = x; set(value) { x = value }

    override inline var _y get() = y; set(value) { y = value }
    override inline var g get() = y; set(value) { y = value }
    override inline var t get() = y; set(value) { y = value }

    override inline var _z get() = z; set(value) { z = value }
    override inline var b get() = z; set(value) { z = value }
    override inline var p get() = z; set(value) { z = value }
    //@formatter:on

    override inline operator fun get(index: Int) = array[ofs + index]

    inline operator fun set(index: Int, value: Uint) {
        array[ofs + index] = value
    }

    override inline operator fun component1() = x
    override inline operator fun component2() = y
    override inline operator fun component3() = z

    override fun toString(): String = "($x, $y, $z)"
}

