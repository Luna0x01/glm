package glm_.vec4

import glm_.*
import glm_.vec1.Vec1bool
import glm_.vec1.Vec1t
import glm_.vec2.Vec2bool
import glm_.vec2.Vec2t
import glm_.vec2.Vec2ub
import glm_.vec3.Vec3bool
import glm_.vec3.Vec3t
import glm_.vec3.Vec3ub
import glm_.vec4.operators.vec4ub_operators
import kool.Ptr
import kool.get
import kool.pos
import kool.set
import unsigned.Ubyte
import unsigned.UbyteArray
import unsigned.toUbyte
import java.io.PrintStream
import java.nio.*

/**
 * Created by elect on 09/10/16.
 */

class Vec4ub(@JvmField var ofs: Int, var array: UbyteArray) : Vec4t<Ubyte>, ToBuffer {

    inline var x: Ubyte
        get() = array[ofs]
        set(value) = array.set(ofs, value)
    inline var y: Ubyte
        get() = array[ofs + 1]
        set(value) = array.set(ofs + 1, value)
    inline var z: Ubyte
        get() = array[ofs + 2]
        set(value) = array.set(ofs + 2, value)
    inline var w: Ubyte
        get() = array[ofs + 3]
        set(value) = array.set(ofs + 3, value)

    inline var vX: Byte
        get() = array[ofs].toByte()
        set(value) = array.set(ofs, value.toUbyte())
    inline var vY: Byte
        get() = array[ofs + 1].toByte()
        set(value) = array.set(ofs + 1, value.toUbyte())
    inline var vZ: Byte
        get() = array[ofs + 2].toByte()
        set(value) = array.set(ofs + 2, value.toUbyte())
    inline var vW: Byte
        get() = array[ofs + 3].toByte()
        set(value) = array.set(ofs + 3, value.toUbyte())

    // -- Implicit basic constructors --

    constructor() : this(0)
    constructor(v: Vec4ub) : this(v.x, v.y, v.z, v.w)
    constructor(v: Vec3ub) : this(v.x, v.y, v.z, Ubyte(0))
    constructor(v: Vec2ub) : this(v.x, v.y, Ubyte(0), Ubyte(0))

    // -- Explicit basic constructors --

    constructor(x: Ubyte) : this(x, x, x, x)
    constructor(x: Ubyte, y: Ubyte, z: Ubyte, w: Ubyte) : this(0, UbyteArray(byteArrayOf(x.v, y.v, z.v, w.v)))
    constructor(x: Int) : this(x, x, x, x)
    constructor(x: Int, y: Int, z: Int, w: Int) : this(0, UbyteArray(byteArrayOf(x.b, y.b, z.b, w.b)))

    // -- Conversion scalar constructors --

    constructor(v: Vec1t<out Number>) : this(v._x)

    // Explicit conversions (From section 5.4.1 Conversion and scalar constructors of GLSL 1.30.08 specification)

    constructor(v: Number) : this(v.ub)
    constructor(x: Number, y: Number, z: Number, w: Number) : this(x.ub, y.ub, z.ub, w.ub)

    constructor(x: Vec1t<out Number>, y: Number, z: Number, w: Number) : this(x._x, y, z, w)
    constructor(x: Number, y: Vec1t<out Number>, z: Number, w: Number) : this(x, y._x, z, w)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Number, w: Number) : this(x._x, y._x, z, w)
    constructor(x: Number, y: Number, z: Vec1t<out Number>, w: Number) : this(x, y, z._x, w)
    constructor(x: Vec1t<out Number>, y: Number, z: Vec1t<out Number>, w: Number) : this(x._x, y, z._x, w)
    constructor(x: Number, y: Vec1t<out Number>, z: Vec1t<out Number>, w: Number) : this(x, y._x, z._x, w)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Vec1t<out Number>, w: Number) : this(x._x, y._x, z._x, w)
    constructor(x: Vec1t<out Number>, y: Number, z: Number, w: Vec1t<out Number>) : this(x._x, y, z, w._x)
    constructor(x: Number, y: Vec1t<out Number>, z: Number, w: Vec1t<out Number>) : this(x, y._x, z, w._x)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Number, w: Vec1t<out Number>) : this(x._x, y._x, z, w._x)
    constructor(x: Number, y: Number, z: Vec1t<out Number>, w: Vec1t<out Number>) : this(x, y, z._x, w._x)
    constructor(x: Vec1t<out Number>, y: Number, z: Vec1t<out Number>, w: Vec1t<out Number>) : this(x._x, y, z._x, w._x)
    constructor(x: Number, y: Vec1t<out Number>, z: Vec1t<out Number>, w: Vec1t<out Number>) : this(x, y._x, z._x, w._x)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, z: Vec1t<out Number>, w: Vec1t<out Number>) : this(x._x, y._x, z._x, w._x)

    constructor(xy: Vec2t<out Number>, z: Number, w: Number) : this(xy._x, xy._y, z, w)
    constructor(xy: Vec2t<out Number>, z: Vec1t<out Number>, w: Number) : this(xy._x, xy._y, z._x, w)
    constructor(xy: Vec2t<out Number>, z: Number, w: Vec1t<out Number>) : this(xy._x, xy._y, z, w._x)
    constructor(xy: Vec2t<out Number>, z: Vec1t<out Number>, w: Vec1t<out Number>) : this(xy._x, xy._y, z._x, w._x)
    constructor(x: Number, yz: Vec2t<out Number>, w: Number) : this(x, yz._x, yz._y, w)
    constructor(x: Vec1t<out Number>, yz: Vec2t<out Number>, w: Number) : this(x._x, yz._x, yz._y, w)
    constructor(x: Number, yz: Vec2t<out Number>, w: Vec1t<out Number>) : this(x, yz._x, yz._y, w._x)
    constructor(x: Vec1t<out Number>, yz: Vec2t<out Number>, w: Vec1t<out Number>) : this(x._x, yz._x, yz._y, w._x)
    constructor(x: Number, y: Number, zw: Vec2t<out Number>) : this(x, y, zw._x, zw._y)
    constructor(x: Vec1t<out Number>, y: Number, zw: Vec2t<out Number>) : this(x._x, y, zw._x, zw._y)
    constructor(x: Number, y: Vec1t<out Number>, zw: Vec2t<out Number>) : this(x, y, zw._x, zw._y)
    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>, zw: Vec2t<out Number>) : this(x._x, y._x, zw._x, zw._y)
    constructor(xyz: Vec3t<out Number>, w: Number) : this(xyz._x, xyz._y, xyz._z, w)
    constructor(xyz: Vec3t<out Number>, w: Vec1t<out Number>) : this(xyz._x, xyz._y, xyz._z, w._x)
    constructor(x: Number, yzw: Vec3t<out Number>) : this(x, yzw._x, yzw._y, yzw._z)
    constructor(x: Vec1t<out Number>, yzw: Vec3t<out Number>) : this(x._x, yzw._x, yzw._y, yzw._z)
    constructor(xy: Vec2t<out Number>, zw: Vec2t<out Number>) : this(xy._x, xy._y, zw._x, zw._y)
    constructor(v: Vec4t<out Number>) : this(v._x, v._y, v._z, v._w)

    constructor(v: Vec1bool) : this(v.x.ub, 0, 0, 1)
    constructor(v: Vec2bool) : this(v.x.ub, v.y.ub, 0, 1)
    constructor(v: Vec3bool) : this(v.x.ub, v.y.ub, v.z.ub, 1)
    constructor(v: Vec4bool) : this(v.x.ub, v.y.ub, v.z.ub, v.w.ub)

    constructor(bytes: ByteArray, index: Int = 0) : this(bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3])
    constructor(chars: CharArray, index: Int = 0) : this(chars[index].ub, chars[index + 1].ub, chars[index + 2].ub, chars[index + 3].ub)
    constructor(shorts: ShortArray, index: Int = 0) : this(shorts[index], shorts[index + 1], shorts[index + 2], shorts[index + 3])
    constructor(ints: IntArray, index: Int = 0) : this(ints[index], ints[index + 1], ints[index + 2], ints[index + 3])
    constructor(longs: LongArray, index: Int = 0) : this(longs[index], longs[index + 1], longs[index + 2], longs[index + 3])
    constructor(floats: FloatArray, index: Int = 0) : this(floats[index], floats[index + 1], floats[index + 2], floats[index + 3])
    constructor(doubles: DoubleArray, index: Int = 0) : this(doubles[index], doubles[index + 1], doubles[index + 2], doubles[index + 3])
    constructor(booleans: BooleanArray, index: Int = 0) : this(booleans[index].ub, booleans[index + 1].ub, booleans[index + 2].ub, booleans[index + 3].ub)

    constructor(numbers: Array<out Number>, index: Int = 0) : this(numbers[index], numbers[index + 1], numbers[index + 2], numbers[index + 3])
    constructor(chars: Array<Char>, index: Int = 0) : this(chars[index].ub, chars[index + 1].ub, chars[index + 2].ub, chars[index + 3].ub)
    constructor(booleans: Array<Boolean>, index: Int = 0) : this(booleans[index].ub, booleans[index + 1].ub, booleans[index + 2].ub, booleans[index + 3].ub)

    constructor(list: Iterable<*>, index: Int = 0) : this(list.elementAt(index)!!.toByte, list.elementAt(index + 1)!!.toByte,
            list.elementAt(index + 2)!!.toByte, list.elementAt(index + 3)!!.toByte)

    constructor(bytes: ByteBuffer, index: Int = bytes.pos) : this(bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3])
    constructor(chars: CharBuffer, index: Int = chars.pos) : this(chars[index].ub, chars[index + 1].ub, chars[index + 2].ub, chars[index + 3].ub)
    constructor(shorts: ShortBuffer, index: Int = shorts.pos) : this(shorts[index], shorts[index + 1], shorts[index + 2], shorts[index + 3])
    constructor(ints: IntBuffer, index: Int = ints.pos) : this(ints[index], ints[index + 1], ints[index + 2], ints[index + 3])
    constructor(longs: LongBuffer, index: Int = longs.pos) : this(longs[index], longs[index + 1], longs[index + 2], longs[index + 3])
    constructor(floats: FloatBuffer, index: Int = floats.pos) : this(floats[index], floats[index + 1], floats[index + 2], floats[index + 3])
    constructor(doubles: DoubleBuffer, index: Int = doubles.pos) : this(doubles[index], doubles[index + 1], doubles[index + 2], doubles[index + 3])

    constructor(block: (Int) -> Ubyte) : this(block(0), block(1), block(2), block(3))
    constructor(ptr: Ptr<Ubyte>) : this() {
        val p = ptr.toPtr<Byte>()
        x.v = p[0]
        y.v = p[1]
        z.v = p[2]
        w.v = p[3]
    }


    fun put(x: Ubyte, y: Ubyte, z: Ubyte, w: Ubyte) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    fun put(x: Byte, y: Byte, z: Byte, w: Byte) {
        this.x.v = x
        this.y.v = y
        this.z.v = z
        this.w.v = w
    }

    operator fun invoke(x: Ubyte, y: Ubyte, z: Ubyte, w: Ubyte): Vec4ub {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
        return this
    }

    operator fun invoke(x: Byte, y: Byte, z: Byte, w: Byte): Vec4ub {
        this.x.v = x
        this.y.v = y
        this.z.v = z
        this.w.v = w
        return this
    }

    override fun put(x: Number, y: Number, z: Number, w: Number) {
        this.x = x.ub
        this.y = y.ub
        this.z = z.ub
        this.w = w.ub
    }

    override operator fun invoke(x: Number, y: Number, z: Number, w: Number): Vec4ub {
        this.x = x.ub
        this.y = y.ub
        this.z = z.ub
        this.w = w.ub
        return this
    }

    fun to(bytes: ByteArray, index: Int): ByteArray = to(bytes, index, true)
    override fun to(bytes: ByteArray, index: Int, bigEndian: Boolean): ByteArray {
        System.arraycopy(array, ofs, bytes, index, length)
        return bytes
    }

    override fun to(buf: ByteBuffer, offset: Int): ByteBuffer {
        buf[offset] = x.v
        buf[offset + 1] = y.v
        buf[offset + 2] = z.v
        buf[offset + 3] = w.v
        return buf
    }

    infix fun to(ptr: Ptr<Ubyte>) {
        val p = ptr.toPtr<Byte>()
        p[0] = x.v
        p[1] = y.v
        p[2] = z.v
        p[3] = w.v
    }


    // -- Component accesses --

    operator fun set(index: Int, value: Byte) = when (index) {
        0 -> x.v = value
        1 -> y.v = value
        2 -> z.v = value
        3 -> w.v = value
        else -> throw ArrayIndexOutOfBoundsException()
    }

    override operator fun set(index: Int, value: Number) = when (index) {
        0 -> x = value.ub
        1 -> y = value.ub
        2 -> z = value.ub
        3 -> w = value.ub
        else -> throw ArrayIndexOutOfBoundsException()
    }


    // -- Unary arithmetic operators --

    operator fun unaryPlus() = this

    // no unaryMinus operator, only signed

    // -- Increment main.and decrement operators --

    operator fun inc(res: Vec4ub = Vec4ub()) = plus(res, this, 1, 1, 1, 1)
    fun incAssign() = plus(this, this, 1, 1, 1, 1)


    operator fun dec(res: Vec4ub = Vec4ub()) = minus(res, this, 1, 1, 1, 1)
    fun decAssign() = minus(this, this, 1, 1, 1, 1)


    // -- Specific binary arithmetic operators --

    operator fun plus(b: Ubyte) = plus(Vec4ub(), this, b, b, b, b)
    operator fun plus(b: Byte) = plus(Vec4ub(), this, b, b, b, b)
    operator fun plus(b: Int) = plus(Vec4ub(), this, b, b, b, b)
    operator fun plus(b: Vec4ub) = plus(Vec4ub(), this, b.x, b.y, b.z, b.w)

    fun plus(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = plus(res, this, bX, bY, bZ, bW)
    fun plus(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = plus(res, this, bX, bY, bZ, bW)
    fun plus(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = plus(res, this, bX, bY, bZ, bW)
    fun plus(b: Ubyte, res: Vec4ub = Vec4ub()) = plus(res, this, b, b, b, b)
    fun plus(b: Byte, res: Vec4ub = Vec4ub()) = plus(res, this, b, b, b, b)
    fun plus(b: Int, res: Vec4ub = Vec4ub()) = plus(res, this, b, b, b, b)
    fun plus(b: Vec4ub, res: Vec4ub = Vec4ub()) = plus(res, this, b.x, b.y, b.z, b.w)

    fun plusAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = plus(this, this, bX, bY, bZ, bW)
    fun plusAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = plus(this, this, bX, bY, bZ, bW)
    fun plusAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = plus(this, this, bX, bY, bZ, bW)
    infix operator fun plusAssign(b: Ubyte) {
        plus(this, this, b, b, b, b)
    }

    infix operator fun plusAssign(b: Byte) {
        plus(this, this, b, b, b, b)
    }

    infix operator fun plusAssign(b: Int) {
        plus(this, this, b, b, b, b)
    }

    infix operator fun plusAssign(b: Vec4ub) {
        plus(this, this, b.x, b.y, b.z, b.w)
    }


    operator fun minus(b: Ubyte) = minus(Vec4ub(), this, b, b, b, b)
    operator fun minus(b: Byte) = minus(Vec4ub(), this, b, b, b, b)
    operator fun minus(b: Int) = minus(Vec4ub(), this, b, b, b, b)
    operator fun minus(b: Vec4ub) = minus(Vec4ub(), this, b.x, b.y, b.z, b.w)

    fun minus(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = minus(res, this, bX, bY, bZ, bW)
    fun minus(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = minus(res, this, bX, bY, bZ, bW)
    fun minus(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = minus(res, this, bX, bY, bZ, bW)
    fun minus(b: Ubyte, res: Vec4ub = Vec4ub()) = minus(res, this, b, b, b, b)
    fun minus(b: Byte, res: Vec4ub = Vec4ub()) = minus(res, this, b, b, b, b)
    fun minus(b: Int, res: Vec4ub = Vec4ub()) = minus(res, this, b, b, b, b)
    fun minus(b: Vec4ub, res: Vec4ub = Vec4ub()) = minus(res, this, b.x, b.y, b.z, b.w)

    fun minusAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = minus(this, this, bX, bY, bZ, bW)
    fun minusAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = minus(this, this, bX, bY, bZ, bW)
    fun minusAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = minus(this, this, bX, bY, bZ, bW)
    infix operator fun minusAssign(b: Ubyte) {
        minus(this, this, b, b, b, b)
    }

    infix operator fun minusAssign(b: Byte) {
        minus(this, this, b, b, b, b)
    }

    infix operator fun minusAssign(b: Int) {
        minus(this, this, b, b, b, b)
    }

    infix operator fun minusAssign(b: Vec4ub) {
        minus(this, this, b.x, b.y, b.z, b.w)
    }


    operator fun times(b: Ubyte) = times(Vec4ub(), this, b, b, b, b)
    operator fun times(b: Byte) = times(Vec4ub(), this, b, b, b, b)
    operator fun times(b: Int) = times(Vec4ub(), this, b, b, b, b)
    operator fun times(b: Vec4ub) = times(Vec4ub(), this, b.x, b.y, b.z, b.w)

    fun times(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = times(res, this, bX, bY, bZ, bW)
    fun times(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = times(res, this, bX, bY, bZ, bW)
    fun times(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = times(res, this, bX, bY, bZ, bW)
    fun times(b: Ubyte, res: Vec4ub = Vec4ub()) = times(res, this, b, b, b, b)
    fun times(b: Byte, res: Vec4ub = Vec4ub()) = times(res, this, b, b, b, b)
    fun times(b: Int, res: Vec4ub = Vec4ub()) = times(res, this, b, b, b, b)
    fun times(b: Vec4ub, res: Vec4ub = Vec4ub()) = times(res, this, b.x, b.y, b.z, b.w)

    fun timesAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = times(this, this, bX, bY, bZ, bW)
    fun timesAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = times(this, this, bX, bY, bZ, bW)
    fun timesAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = times(this, this, bX, bY, bZ, bW)
    infix operator fun timesAssign(b: Ubyte) {
        times(this, this, b, b, b, b)
    }

    infix operator fun timesAssign(b: Byte) {
        times(this, this, b, b, b, b)
    }

    infix operator fun timesAssign(b: Int) {
        times(this, this, b, b, b, b)
    }

    infix operator fun timesAssign(b: Vec4ub) {
        times(this, this, b.x, b.y, b.z, b.w)
    }


    operator fun div(b: Ubyte) = div(Vec4ub(), this, b, b, b, b)
    operator fun div(b: Byte) = div(Vec4ub(), this, b, b, b, b)
    operator fun div(b: Int) = div(Vec4ub(), this, b, b, b, b)
    operator fun div(b: Vec4ub) = div(Vec4ub(), this, b.x, b.y, b.z, b.w)

    fun div(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = div(res, this, bX, bY, bZ, bW)
    fun div(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = div(res, this, bX, bY, bZ, bW)
    fun div(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = div(res, this, bX, bY, bZ, bW)
    fun div(b: Ubyte, res: Vec4ub) = div(res, this, b, b, b, b)
    fun div(b: Byte, res: Vec4ub) = div(res, this, b, b, b, b)
    fun div(b: Int, res: Vec4ub) = div(res, this, b, b, b, b)
    fun div(b: Vec4ub, res: Vec4ub) = div(res, this, b.x, b.y, b.z, b.w)

    fun divAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = div(this, this, bX, bY, bZ, bW)
    fun divAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = div(this, this, bX, bY, bZ, bW)
    fun divAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = div(this, this, bX, bY, bZ, bW)
    infix operator fun divAssign(b: Ubyte) {
        div(this, this, b, b, b, b)
    }

    infix operator fun divAssign(b: Byte) {
        div(this, this, b, b, b, b)
    }

    infix operator fun divAssign(b: Int) {
        div(this, this, b, b, b, b)
    }

    infix operator fun divAssign(b: Vec4ub) {
        div(this, this, b.x, b.y, b.z, b.w)
    }


    operator fun rem(b: Ubyte) = rem(Vec4ub(), this, b, b, b, b)
    operator fun rem(b: Byte) = rem(Vec4ub(), this, b, b, b, b)
    operator fun rem(b: Int) = rem(Vec4ub(), this, b, b, b, b)
    operator fun rem(b: Vec4ub) = rem(Vec4ub(), this, b.x, b.y, b.z, b.w)

    fun rem(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = rem(res, this, bX, bY, bZ, bW)
    fun rem(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = rem(res, this, bX, bY, bZ, bW)
    fun rem(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = rem(res, this, bX, bY, bZ, bW)
    fun rem(b: Ubyte, res: Vec4ub) = rem(res, this, b, b, b, b)
    fun rem(b: Byte, res: Vec4ub) = rem(res, this, b, b, b, b)
    fun rem(b: Int, res: Vec4ub) = rem(res, this, b, b, b, b)
    fun rem(b: Vec4ub, res: Vec4ub) = rem(res, this, b.x, b.y, b.z, b.w)

    fun remAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = rem(this, this, bX, bY, bZ, bW)
    fun remAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = rem(this, this, bX, bY, bZ, bW)
    fun remAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = rem(this, this, bX, bY, bZ, bW)
    infix operator fun remAssign(b: Ubyte) {
        rem(this, this, b, b, b, b)
    }

    infix operator fun remAssign(b: Byte) {
        rem(this, this, b, b, b, b)
    }

    infix operator fun remAssign(b: Int) {
        rem(this, this, b, b, b, b)
    }

    infix operator fun remAssign(b: Vec4ub) {
        rem(this, this, b.x, b.y, b.z, b.w)
    }


    // -- Generic binary arithmetic operators --

    operator fun plus(b: Number) = plus(Vec4ub(), this, b.i, b.i, b.i, b.i)
    operator fun plus(b: Vec4t<out Number>) = plus(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun plus(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = plus(res, this, bX.i, bY.i, bZ.i, bW.i)
    fun plus(b: Number, res: Vec4ub = Vec4ub()) = plus(res, this, b.i, b.i, b.i, b.i)
    fun plus(b: Vec4t<out Number>, res: Vec4ub = Vec4ub()) = plus(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun plusAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = plus(this, this, bX.i, bY.i, bZ.i, bW.i)
    infix operator fun plusAssign(b: Number) {
        plus(this, this, b.i, b.i, b.i, b.i)
    }

    infix operator fun plusAssign(b: Vec4t<out Number>) {
        plus(this, this, b._x.i, b._y.i, b._z.i, b._w.i)
    }


    operator fun minus(b: Number) = minus(Vec4ub(), this, b.i, b.i, b.i, b.i)
    operator fun minus(b: Vec4t<out Number>) = minus(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun minus(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = minus(res, this, bX.i, bY.i, bZ.i, bW.i)
    fun minus(b: Number, res: Vec4ub = Vec4ub()) = minus(res, this, b.i, b.i, b.i, b.i)
    fun minus(b: Vec4t<out Number>, res: Vec4ub = Vec4ub()) = minus(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun minusAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = minus(this, this, bX.i, bY.i, bZ.i, bW.i)
    infix operator fun minusAssign(b: Number) {
        minus(this, this, b.i, b.i, b.i, b.i)
    }

    infix operator fun minusAssign(b: Vec4t<out Number>) {
        minus(this, this, b._x.i, b._y.i, b._z.i, b._w.i)
    }


    operator fun times(b: Number) = times(Vec4ub(), this, b.i, b.i, b.i, b.i)
    operator fun times(b: Vec4t<out Number>) = times(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun times(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = times(res, this, bX.i, bY.i, bZ.i, bW.i)
    fun times(b: Number, res: Vec4ub = Vec4ub()) = times(res, this, b.i, b.i, b.i, b.i)
    fun times(b: Vec4t<out Number>, res: Vec4ub = Vec4ub()) = times(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun timesAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = times(this, this, bX.i, bY.i, bZ.i, bW.i)
    infix operator fun timesAssign(b: Number) {
        times(this, this, b.i, b.i, b.i, b.i)
    }

    infix operator fun timesAssign(b: Vec4t<out Number>) {
        times(this, this, b._x.i, b._y.i, b._z.i, b._w.i)
    }


    operator fun div(b: Number) = div(Vec4ub(), this, b.i, b.i, b.i, b.i)
    operator fun div(b: Vec4t<out Number>) = div(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun div(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = div(res, this, bX.i, bY.i, bZ.i, bW.i)
    fun div(b: Number, res: Vec4ub) = div(res, this, b.i, b.i, b.i, b.i)
    fun div(b: Vec4t<out Number>, res: Vec4ub) = div(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun divAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = div(this, this, bX.i, bY.i, bZ.i, bW.i)
    infix operator fun divAssign(b: Number) {
        div(this, this, b.i, b.i, b.i, b.i)
    }

    infix operator fun divAssign(b: Vec4t<out Number>) {
        div(this, this, b._x.i, b._y.i, b._z.i, b._w.i)
    }


    operator fun rem(b: Number) = rem(Vec4ub(), this, b.i, b.i, b.i, b.i)
    operator fun rem(b: Vec4t<out Number>) = rem(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun rem(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = rem(res, this, bX.i, bY.i, bZ.i, bW.i)
    fun rem(b: Number, res: Vec4ub) = rem(res, this, b.i, b.i, b.i, b.i)
    fun rem(b: Vec4t<out Number>, res: Vec4ub) = rem(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun remAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = rem(this, this, bX.i, bY.i, bZ.i, bW.i)
    infix operator fun remAssign(b: Number) {
        rem(this, this, b.i, b.i, b.i, b.i)
    }

    infix operator fun remAssign(b: Vec4t<out Number>) {
        rem(this, this, b._x.i, b._y.i, b._z.i, b._w.i)
    }


    // -- Specific bitwise operators --

    infix fun and(b: Ubyte) = and(Vec4ub(), this, b, b, b, b)
    infix fun and(b: Byte) = and(Vec4ub(), this, b, b, b, b)
    infix fun and(b: Int) = and(Vec4ub(), this, b, b, b, b)
    infix fun and(b: Vec4ub) = and(Vec4ub(), this, b.x, b.y, b.z, b.w)

    infix fun andAssign(b: Ubyte) = and(this, this, b, b, b, b)
    infix fun andAssign(b: Byte) = and(this, this, b, b, b, b)
    infix fun andAssign(b: Int) = and(this, this, b, b, b, b)
    infix fun andAssign(b: Vec4ub) = and(this, this, b.x, b.y, b.z, b.w)

    fun and(b: Ubyte, res: Vec4ub) = and(res, this, b, b, b, b)
    fun and(b: Byte, res: Vec4ub) = and(res, this, b, b, b, b)
    fun and(b: Int, res: Vec4ub) = and(res, this, b, b, b, b)
    fun and(b: Vec4ub, res: Vec4ub) = and(res, this, b.x, b.y, b.z, b.w)

    fun and(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = and(res, this, bX, bY, bZ, bW)
    fun and(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = and(res, this, bX, bY, bZ, bW)
    fun and(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = and(res, this, bX, bY, bZ, bW)

    fun andAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = and(this, this, bX, bY, bZ, bW)
    fun andAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = and(this, this, bX, bY, bZ, bW)
    fun andAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = and(this, this, bX, bY, bZ, bW)


    infix fun or(b: Ubyte) = or(Vec4ub(), this, b, b, b, b)
    infix fun or(b: Byte) = or(Vec4ub(), this, b, b, b, b)
    infix fun or(b: Int) = or(Vec4ub(), this, b, b, b, b)
    infix fun or(b: Vec4ub) = or(Vec4ub(), this, b.x, b.y, b.z, b.w)

    infix fun orAssign(b: Ubyte) = or(this, this, b, b, b, b)
    infix fun orAssign(b: Byte) = or(this, this, b, b, b, b)
    infix fun orAssign(b: Int) = or(this, this, b, b, b, b)
    infix fun orAssign(b: Vec4ub) = or(this, this, b.x, b.y, b.z, b.w)

    fun or(b: Ubyte, res: Vec4ub) = or(res, this, b, b, b, b)
    fun or(b: Byte, res: Vec4ub) = or(res, this, b, b, b, b)
    fun or(b: Int, res: Vec4ub) = or(res, this, b, b, b, b)
    fun or(b: Vec4ub, res: Vec4ub) = or(res, this, b.x, b.y, b.z, b.w)

    fun or(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = or(res, this, bX, bY, bZ, bW)
    fun or(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = or(res, this, bX, bY, bZ, bW)
    fun or(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = or(res, this, bX, bY, bZ, bW)

    fun orAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = or(this, this, bX, bY, bZ, bW)
    fun orAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = or(this, this, bX, bY, bZ, bW)
    fun orAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = or(this, this, bX, bY, bZ, bW)


    infix fun xor(b: Ubyte) = xor(Vec4ub(), this, b, b, b, b)
    infix fun xor(b: Byte) = xor(Vec4ub(), this, b, b, b, b)
    infix fun xor(b: Int) = xor(Vec4ub(), this, b, b, b, b)
    infix fun xor(b: Vec4ub) = xor(Vec4ub(), this, b.x, b.y, b.z, b.w)

    infix fun xorAssign(b: Ubyte) = xor(this, this, b, b, b, b)
    infix fun xorAssign(b: Byte) = xor(this, this, b, b, b, b)
    infix fun xorAssign(b: Int) = xor(this, this, b, b, b, b)
    infix fun xorAssign(b: Vec4ub) = xor(this, this, b.x, b.y, b.z, b.w)

    fun xor(b: Ubyte, res: Vec4ub) = xor(res, this, b, b, b, b)
    fun xor(b: Byte, res: Vec4ub) = xor(res, this, b, b, b, b)
    fun xor(b: Int, res: Vec4ub) = xor(res, this, b, b, b, b)
    fun xor(b: Vec4ub, res: Vec4ub) = xor(res, this, b.x, b.y, b.z, b.w)

    fun xor(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = xor(res, this, bX, bY, bZ, bW)
    fun xor(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = xor(res, this, bX, bY, bZ, bW)
    fun xor(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = xor(res, this, bX, bY, bZ, bW)

    fun xorAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = xor(this, this, bX, bY, bZ, bW)
    fun xorAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = xor(this, this, bX, bY, bZ, bW)
    fun xorAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = xor(this, this, bX, bY, bZ, bW)


    infix fun shl(b: Ubyte) = shl(Vec4ub(), this, b, b, b, b)
    infix fun shl(b: Byte) = shl(Vec4ub(), this, b, b, b, b)
    infix fun shl(b: Int) = shl(Vec4ub(), this, b, b, b, b)
    infix fun shl(b: Vec4ub) = shl(Vec4ub(), this, b.x, b.y, b.z, b.w)

    infix fun shlAssign(b: Ubyte) = shl(this, this, b, b, b, b)
    infix fun shlAssign(b: Byte) = shl(this, this, b, b, b, b)
    infix fun shlAssign(b: Int) = shl(this, this, b, b, b, b)
    infix fun shlAssign(b: Vec4ub) = shl(this, this, b.x, b.y, b.z, b.w)

    fun shl(b: Ubyte, res: Vec4ub) = shl(res, this, b, b, b, b)
    fun shl(b: Byte, res: Vec4ub) = shl(res, this, b, b, b, b)
    fun shl(b: Int, res: Vec4ub) = shl(res, this, b, b, b, b)
    fun shl(b: Vec4ub, res: Vec4ub) = shl(res, this, b.x, b.y, b.z, b.w)

    fun shl(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = shl(res, this, bX, bY, bZ, bW)
    fun shl(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = shl(res, this, bX, bY, bZ, bW)
    fun shl(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = shl(res, this, bX, bY, bZ, bW)

    fun shlAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = shl(this, this, bX, bY, bZ, bW)
    fun shlAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = shl(this, this, bX, bY, bZ, bW)
    fun shlAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = shl(this, this, bX, bY, bZ, bW)


    infix fun shr(b: Ubyte) = shr(Vec4ub(), this, b, b, b, b)
    infix fun shr(b: Byte) = shr(Vec4ub(), this, b, b, b, b)
    infix fun shr(b: Int) = shr(Vec4ub(), this, b, b, b, b)
    infix fun shr(b: Vec4ub) = shr(Vec4ub(), this, b.x, b.y, b.z, b.w)

    infix fun shrAssign(b: Ubyte) = shr(this, this, b, b, b, b)
    infix fun shrAssign(b: Byte) = shr(this, this, b, b, b, b)
    infix fun shrAssign(b: Int) = shr(this, this, b, b, b, b)
    infix fun shrAssign(b: Vec4ub) = shr(this, this, b.x, b.y, b.z, b.w)

    fun shr(b: Ubyte, res: Vec4ub) = shr(res, this, b, b, b, b)
    fun shr(b: Byte, res: Vec4ub) = shr(res, this, b, b, b, b)
    fun shr(b: Int, res: Vec4ub) = shr(res, this, b, b, b, b)
    fun shr(b: Vec4ub, res: Vec4ub) = shr(res, this, b.x, b.y, b.z, b.w)

    fun shr(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte, res: Vec4ub = Vec4ub()) = shr(res, this, bX, bY, bZ, bW)
    fun shr(bX: Byte, bY: Byte, bZ: Byte, bW: Byte, res: Vec4ub = Vec4ub()) = shr(res, this, bX, bY, bZ, bW)
    fun shr(bX: Int, bY: Int, bZ: Int, bW: Int, res: Vec4ub = Vec4ub()) = shr(res, this, bX, bY, bZ, bW)

    fun shrAssign(bX: Ubyte, bY: Ubyte, bZ: Ubyte, bW: Ubyte) = shr(this, this, bX, bY, bZ, bW)
    fun shrAssign(bX: Byte, bY: Byte, bZ: Byte, bW: Byte) = shr(this, this, bX, bY, bZ, bW)
    fun shrAssign(bX: Int, bY: Int, bZ: Int, bW: Int) = shr(this, this, bX, bY, bZ, bW)


    fun inv(res: Vec4ub = Vec4ub()) = inv(res, this)
    fun invAssign() = inv(this, this)


    // -- Generic bitwise operators --

    infix fun and(b: Number) = and(Vec4ub(), this, b.i, b.i, b.i, b.i)
    infix fun and(b: Vec4t<out Number>) = and(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    infix fun andAssign(b: Number) = and(this, this, b.i, b.i, b.i, b.i)
    infix fun andAssign(b: Vec4t<out Number>) = and(this, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun and(b: Number, res: Vec4ub) = and(res, this, b.i, b.i, b.i, b.i)
    fun and(b: Vec4t<out Number>, res: Vec4ub) = and(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun and(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = and(res, this, bX.i, bY.i, bZ.i, bW.i)

    fun andAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = and(this, this, bX.i, bY.i, bZ.i, bW.i)


    infix fun or(b: Number) = or(Vec4ub(), this, b.i, b.i, b.i, b.i)
    infix fun or(b: Vec4t<out Number>) = or(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    infix fun orAssign(b: Number) = or(this, this, b.i, b.i, b.i, b.i)
    infix fun orAssign(b: Vec4t<out Number>) = or(this, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun or(b: Number, res: Vec4ub) = or(res, this, b.i, b.i, b.i, b.i)
    fun or(b: Vec4t<out Number>, res: Vec4ub) = or(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun or(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = or(res, this, bX.i, bY.i, bZ.i, bW.i)

    fun orAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = or(this, this, bX.i, bY.i, bZ.i, bW.i)


    infix fun xor(b: Number) = xor(Vec4ub(), this, b.i, b.i, b.i, b.i)
    infix fun xor(b: Vec4t<out Number>) = xor(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    infix fun xorAssign(b: Number) = xor(this, this, b.i, b.i, b.i, b.i)
    infix fun xorAssign(b: Vec4t<out Number>) = xor(this, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun xor(b: Number, res: Vec4ub) = xor(res, this, b.i, b.i, b.i, b.i)
    fun xor(b: Vec4t<out Number>, res: Vec4ub) = xor(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun xor(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = xor(res, this, bX.i, bY.i, bZ.i, bW.i)

    fun xorAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = xor(this, this, bX.i, bY.i, bZ.i, bW.i)


    infix fun shl(b: Number) = shl(Vec4ub(), this, b.i, b.i, b.i, b.i)
    infix fun shl(b: Vec4t<out Number>) = shl(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    infix fun shlAssign(b: Number) = shl(this, this, b.i, b.i, b.i, b.i)
    infix fun shlAssign(b: Vec4t<out Number>) = shl(this, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun shl(b: Number, res: Vec4ub) = shl(res, this, b.i, b.i, b.i, b.i)
    fun shl(b: Vec4t<out Number>, res: Vec4ub) = shl(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun shl(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = shl(res, this, bX.i, bY.i, bZ.i, bW.i)

    fun shlAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = shl(this, this, bX.i, bY.i, bZ.i, bW.i)


    infix fun shr(b: Number) = shr(Vec4ub(), this, b.i, b.i, b.i, b.i)
    infix fun shr(b: Vec4t<out Number>) = shr(Vec4ub(), this, b._x.i, b._y.i, b._z.i, b._w.i)

    infix fun shrAssign(b: Number) = shr(this, this, b.i, b.i, b.i, b.i)
    infix fun shrAssign(b: Vec4t<out Number>) = shr(this, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun shr(b: Number, res: Vec4ub) = shr(res, this, b.i, b.i, b.i, b.i)
    fun shr(b: Vec4t<out Number>, res: Vec4ub) = shr(res, this, b._x.i, b._y.i, b._z.i, b._w.i)

    fun shr(bX: Number, bY: Number, bZ: Number, bW: Number, res: Vec4ub = Vec4ub()) = shr(res, this, bX.i, bY.i, bZ.i, bW.i)

    fun shrAssign(bX: Number, bY: Number, bZ: Number, bW: Number) = shr(this, this, bX.i, bY.i, bZ.i, bW.i)


    infix fun allLessThan(ub: Ubyte): Boolean = x < ub && y < ub && z < ub && w < ub
    infix fun anyLessThan(ub: Ubyte): Boolean = x < ub || y < ub || z < ub || w < ub
    infix fun lessThan(ub: Ubyte): Vec4bool = Vec4bool { get(it) < ub }

    infix fun allLessThanEqual(ub: Ubyte): Boolean = x <= ub && y <= ub && z <= ub && w <= ub
    infix fun anyLessThanEqual(ub: Ubyte): Boolean = x <= ub || y <= ub || z <= ub || w <= ub
    infix fun lessThanEqual(ub: Ubyte): Vec4bool = Vec4bool { get(it) <= ub }

    infix fun allEqual(ub: Ubyte): Boolean = x == ub && y == ub && z == ub && w == ub
    infix fun anyEqual(ub: Ubyte): Boolean = x == ub || y == ub || z == ub || w == ub
    infix fun equal(ub: Ubyte): Vec4bool = Vec4bool { get(it) == ub }

    infix fun allNotEqual(ub: Ubyte): Boolean = x != ub && y != ub && z != ub && w != ub
    infix fun anyNotEqual(ub: Ubyte): Boolean = x != ub || y != ub || z != ub || w != ub
    infix fun notEqual(ub: Ubyte): Vec4bool = Vec4bool { get(it) != ub }

    infix fun allGreaterThan(ub: Ubyte): Boolean = x > ub && y > ub && z > ub && w > ub
    infix fun anyGreaterThan(ub: Ubyte): Boolean = x > ub || y > ub || z > ub || w > ub
    infix fun greaterThan(ub: Ubyte): Vec4bool = Vec4bool { get(it) > ub }

    infix fun allGreaterThanEqual(ub: Ubyte): Boolean = x >= ub && y >= ub && z >= ub && w >= ub
    infix fun anyGreaterThanEqual(ub: Ubyte): Boolean = x >= ub || y >= ub || z >= ub || w >= ub
    infix fun greaterThanEqual(ub: Ubyte): Vec4bool = Vec4bool { get(it) >= ub }


    infix fun allLessThan(v: Vec4ub): Boolean = x < v.x && y < v.y && z < v.z && w < v.w
    infix fun anyLessThan(v: Vec4ub): Boolean = x < v.x || y < v.y || z < v.z || w < v.w
    infix fun lessThan(v: Vec4ub): Vec4bool = Vec4bool { get(it) < v[it] }

    infix fun allLessThanEqual(v: Vec4ub): Boolean = x <= v.x && y <= v.y && z <= v.z && w <= v.w
    infix fun anyLessThanEqual(v: Vec4ub): Boolean = x <= v.x || y <= v.y || z <= v.z || w <= v.w
    infix fun lessThanEqual(v: Vec4ub): Vec4bool = Vec4bool { get(it) <= v[it] }

    infix fun allEqual(v: Vec4ub): Boolean = x == v.x && y == v.y && z == v.z && w == v.w
    infix fun anyEqual(v: Vec4ub): Boolean = x == v.x || y == v.y || z == v.z || w == v.w
    infix fun equal(v: Vec4ub): Vec4bool = Vec4bool { get(it) == v[it] }

    infix fun allNotEqual(v: Vec4ub): Boolean = x != v.x && y != v.y && z != v.z && w != v.w
    infix fun anyNotEqual(v: Vec4ub): Boolean = x != v.x || y != v.y || z != v.z || w != v.w
    infix fun notEqual(v: Vec4ub): Vec4bool = Vec4bool { get(it) != v[it] }

    infix fun allGreaterThan(v: Vec4ub): Boolean = x > v.x && y > v.y && z > v.z && w > v.w
    infix fun anyGreaterThan(v: Vec4ub): Boolean = x > v.x || y > v.y || z > v.z || w > v.w
    infix fun greaterThan(v: Vec4ub): Vec4bool = Vec4bool { get(it) > v[it] }

    infix fun allGreaterThanEqual(v: Vec4ub): Boolean = x >= v.x && y >= v.y && z >= v.z && w >= v.w
    infix fun anyGreaterThanEqual(v: Vec4ub): Boolean = x >= v.x || y >= v.y || z >= v.z || w >= v.w
    infix fun greaterThanEqual(v: Vec4ub): Vec4bool = Vec4bool { get(it) >= v[it] }


    companion object : vec4ub_operators {
        const val length = Vec4t.LENGTH

        @JvmField
        val size = length * Ubyte.BYTES

        @JvmStatic
        fun fromPointer(ptr: Ptr<Ubyte>) = Vec4ub(ptr)
    }

    override fun size() = size


    override fun equals(other: Any?) = other is Vec4ub && this[0] == other[0] && this[1] == other[1] && this[2] == other[2] && this[3] == other[3]
    override fun hashCode() = 31 * (31 * (31 * x.v.hashCode() + y.v.hashCode()) + z.v.hashCode()) + w.v.hashCode()

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

    override inline var _w get() = w; set(value) { w = value }
    override inline var a get() = w; set(value) { w = value }
    override inline var q get() = w; set(value) { w = value }
    //@formatter:on

    override inline operator fun get(index: Int) = array[ofs + index]

    inline operator fun set(index: Int, value: Ubyte) {
        array[ofs + index] = value
    }

    override inline fun component1() = x
    override inline fun component2() = y
    override inline fun component3() = z
    override inline fun component4() = w

    override fun toString(): String = "($x, $y, $z, $w)"
}
