package glm_.vec2

import glm_.*
import glm_.vec1.Vec1bool
import glm_.vec1.Vec1t
import glm_.vec2.operators.opVec2ul
import glm_.vec3.Vec3bool
import glm_.vec3.Vec3t
import glm_.vec4.Vec4bool
import glm_.vec4.Vec4t
import kool.*
import org.lwjgl.system.MemoryStack
import unsigned.Ulong
import unsigned.UlongArray
import unsigned.toUlong
import java.io.PrintStream
import java.nio.*

/**
 * Created by elect on 08/10/16.
 */

class Vec2ul(@JvmField var ofs: Int, var array: UlongArray) : Vec2t<Ulong>, ToBuffer {

    inline var x: Ulong
        get() = array[ofs]
        set(value) = array.set(ofs, value)
    inline var y: Ulong
        get() = array[ofs + 1]
        set(value) = array.set(ofs + 1, value)

    inline var vX: Long
        get() = array[ofs].toLong()
        set(value) = array.set(ofs, value.toUlong())
    inline var vY: Long
        get() = array[ofs + 1].toLong()
        set(value) = array.set(ofs + 1, value.toUlong())

    // -- Implicit basic constructors --

    constructor() : this(0)
    constructor(v: Vec2ul) : this(v.x, v.y)

    // -- Explicit basic constructors --

    @JvmOverloads
    constructor(x: Long, y: Long = x) : this(x.ul, y.ul)

    @JvmOverloads
    constructor(x: Ulong, y: Ulong = x) : this(0, UlongArray(longArrayOf(x.v, y.v)))

    // -- Conversion constructors --


    constructor(v: Number) : this(v.ul)
    constructor(x: Number, y: Number) : this(x.ul, y.ul)

    // Explicit conversions (From section 5.4.1 Conversion and scalar constructors of GLSL 1.30.08 specification)

    constructor(x: Number, v: Vec1t<out Number>) : this(x, v._x)

    @JvmOverloads
    constructor(v: Vec1t<out Number>, y: Number = v._x) : this(v._x, y)

    constructor(x: Vec1t<out Number>, y: Vec1t<out Number>) : this(x._x, y._x)

    constructor(v: Vec2t<out Number>) : this(v._x, v._y)
    constructor(v: Vec3t<out Number>) : this(v._x, v._y)
    constructor(v: Vec4t<out Number>) : this(v._x, v._y)

    @JvmOverloads
    constructor(x: Boolean, y: Boolean = x) : this(x.ul, y.ul)

    constructor(x: Boolean, v: Vec1bool) : this(x.ul, v.x.ul)

    @JvmOverloads
    constructor(v: Vec1bool, y: Boolean = v.x) : this(v.x.ul, y.ul)

    constructor(x: Vec1bool, y: Vec1bool) : this(x.x.ul, y.x.ul)

    constructor(v: Vec2bool) : this(v.x.ul, v.y.ul)
    constructor(v: Vec3bool) : this(v.x.ul, v.y.ul)
    constructor(v: Vec4bool) : this(v.x.ul, v.y.ul)

    constructor(bytes: ByteArray, index: Int = 0, oneByteOneUlong: Boolean = false, bigEndian: Boolean = true) : this(
        if (oneByteOneUlong) bytes[index].ul else bytes.getUlong(index, bigEndian),
        if (oneByteOneUlong) bytes[index + 1].ul else bytes.getUlong(index + Ulong.BYTES, bigEndian))

    constructor(chars: CharArray, index: Int = 0) : this(chars[index].ul, chars[index + 1].ul)
    constructor(shorts: ShortArray, index: Int = 0) : this(shorts[index], shorts[index + 1])
    constructor(ints: IntArray, index: Int = 0) : this(ints[index], ints[index + 1])
    constructor(longs: LongArray, index: Int = 0) : this(longs[index], longs[index + 1])
    constructor(floats: FloatArray, index: Int = 0) : this(floats[index], floats[index + 1])
    constructor(doubles: DoubleArray, index: Int = 0) : this(doubles[index], doubles[index + 1])
    constructor(booleans: BooleanArray, index: Int = 0) : this(booleans[index].ul, booleans[index + 1].ul)

    constructor(numbers: Array<out Number>, index: Int = 0) : this(numbers[index], numbers[index + 1])
    constructor(chars: Array<Char>, index: Int = 0) : this(chars[index].ul, chars[index + 1].ul)
    constructor(booleans: Array<Boolean>, index: Int = 0) : this(booleans[index].ul, booleans[index + 1].ul)

    constructor(list: Iterable<*>, index: Int = 0) : this(list.elementAt(index)!!.toLong, list.elementAt(index + 1)!!.toLong)

    constructor(bytes: ByteBuffer, index: Int = bytes.pos, oneByteOneUlong: Boolean = false) : this(
        if (oneByteOneUlong) bytes[index].ul else bytes.getLong(index).ul,
        if (oneByteOneUlong) bytes[index + 1].ul else bytes.getLong(index + Ulong.BYTES).ul)

    constructor(chars: CharBuffer, index: Int = chars.pos) : this(chars[index].ul, chars[index + 1].ul)
    constructor(shorts: ShortBuffer, index: Int = shorts.pos) : this(shorts[index], shorts[index + 1])
    constructor(ints: IntBuffer, index: Int = ints.pos) : this(ints[index], ints[index + 1])
    constructor(longs: LongBuffer, index: Int = longs.pos) : this(longs[index], longs[index + 1])
    constructor(floats: FloatBuffer, index: Int = floats.pos) : this(floats[index], floats[index + 1])
    constructor(doubles: DoubleBuffer, index: Int = doubles.pos) : this(doubles[index], doubles[index + 1])

    constructor(block: (Int) -> Ulong) : this(block(0), block(1))
    // clashing
//    constructor(ptr: Ptr<Ulong>) : this() {
//        val p = ptr.toPtr<Long>()
//        x.v = p[0]
//        y.v = p[1]
//    }


    fun set(bytes: ByteArray, index: Int = 0, oneByteOneUlong: Boolean = false, bigEndian: Boolean = true) {
        x.v = if (oneByteOneUlong) bytes[index].L else bytes.getLong(index, bigEndian)
        y.v = if (oneByteOneUlong) bytes[index + 1].L else bytes.getLong(index + Ulong.BYTES, bigEndian)
    }

    fun set(bytes: ByteBuffer, index: Int = bytes.pos, oneByteOneUlong: Boolean = false) {
        x.v = if (oneByteOneUlong) bytes[index].L else bytes.getLong(index)
        y.v = if (oneByteOneUlong) bytes[index + 1].L else bytes.getLong(index + Ulong.BYTES)
    }


    fun put(x: Ulong, y: Ulong) {
        this.x = x
        this.y = y
    }

    operator fun invoke(x: Ulong, y: Ulong): Vec2ul {
        this.x = x
        this.y = y
        return this
    }

    fun put(x: Long, y: Long) {
        this.x.v = x
        this.y.v = y
    }

    operator fun invoke(x: Long, y: Long): Vec2ul {
        this.x.v = x
        this.y.v = y
        return this
    }

    override fun put(x: Number, y: Number) {
        this.x = x.ul
        this.y = y.ul
    }

    override operator fun invoke(x: Number, y: Number): Vec2ul {
        this.x = x.ul
        this.y = y.ul
        return this
    }

    fun to(bytes: ByteArray, index: Int) = to(bytes, index, true)
    override fun to(bytes: ByteArray, index: Int, bigEndian: Boolean): ByteArray {
        bytes.putLong(index, x.v)
        bytes.putLong(index + Long.BYTES, y.v)
        return bytes
    }

    fun toLongArray(): LongArray = to(LongArray(length), 0)
    infix fun to(longs: LongArray): LongArray = to(longs, 0)
    fun to(longs: LongArray, index: Int): LongArray {
        System.arraycopy(array, ofs, longs, index, length)
        return longs
    }

    override fun to(buf: ByteBuffer, offset: Int): ByteBuffer {
        buf.putLong(offset, x.v)
        buf.putLong(offset + Long.BYTES, y.v)
        return buf
    }

    fun toLongBufferStack(): LongBuffer = to(MemoryStack.stackGet().mallocLong(length), 0)
    infix fun toLongBuffer(stack: MemoryStack): LongBuffer = to(stack.mallocLong(length), 0)
    fun toLongBuffer(): LongBuffer = to(LongBuffer(length), 0)
    infix fun to(buf: LongBuffer): LongBuffer = to(buf, buf.pos)
    fun to(buf: LongBuffer, index: Int): LongBuffer {
        buf[index] = x.v
        buf[index + 1] = y.v
        return buf
    }

    infix fun to(ptr: Ptr<Ulong>) {
        val p = ptr.toPtr<Long>()
        p[0] = x.v
        p[1] = y.v
    }

    // -- Component accesses --

    override operator fun set(index: Int, value: Number) = when (index) {
        0 -> x = value.ul
        1 -> y = value.ul
        else -> throw ArrayIndexOutOfBoundsException()
    }

    // -- Unary arithmetic operators --

    operator fun unaryPlus() = this

    // no unaryMinus operator, only signed

    // -- Increment main.and decrement operators --

    operator fun inc(res: Vec2ul = Vec2ul()) = plus(res, this, 1, 1)
    fun incAssign() = plus(this, this, 1, 1)


    operator fun dec(res: Vec2ul = Vec2ul()) = minus(res, this, 1, 1)
    fun decAssign() = minus(this, this, 1, 1)


    // -- Specific binary arithmetic operators --

    infix operator fun plus(b: Ulong) = plus(Vec2ul(), this, b, b)
    infix operator fun plus(b: Long) = plus(Vec2ul(), this, b, b)
    infix operator fun plus(b: Vec2ul) = plus(Vec2ul(), this, b.x, b.y)

    @JvmOverloads
    fun plus(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = plus(res, this, bX, bY)

    @JvmOverloads
    fun plus(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = plus(res, this, bX, bY)

    fun plus(b: Ulong, res: Vec2ul) = plus(res, this, b, b)
    fun plus(b: Long, res: Vec2ul) = plus(res, this, b, b)
    fun plus(b: Vec2ul, res: Vec2ul) = plus(res, this, b.x, b.y)

    fun plusAssign(bX: Ulong, bY: Ulong) = plus(this, this, bX, bY)
    fun plusAssign(bX: Long, bY: Long) = plus(this, this, bX, bY)
    infix operator fun plusAssign(b: Ulong) {
        plus(this, this, b, b)
    }

    infix operator fun plusAssign(b: Long) {
        plus(this, this, b, b)
    }

    infix operator fun plusAssign(b: Vec2ul) {
        plus(this, this, b.x, b.y)
    }


    infix operator fun minus(b: Ulong) = minus(Vec2ul(), this, b, b)
    infix operator fun minus(b: Long) = minus(Vec2ul(), this, b, b)
    infix operator fun minus(b: Vec2ul) = minus(Vec2ul(), this, b.x, b.y)

    @JvmOverloads
    fun minus(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = minus(res, this, bX, bY)

    @JvmOverloads
    fun minus(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = minus(res, this, bX, bY)

    fun minus(b: Ulong, res: Vec2ul) = minus(res, this, b, b)
    fun minus(b: Long, res: Vec2ul) = minus(res, this, b, b)
    fun minus(b: Vec2ul, res: Vec2ul) = minus(res, this, b.x, b.y)

    fun minusAssign(bX: Ulong, bY: Ulong) = minus(this, this, bX, bY)
    fun minusAssign(bX: Long, bY: Long) = minus(this, this, bX, bY)
    infix operator fun minusAssign(b: Ulong) {
        minus(this, this, b, b)
    }

    infix operator fun minusAssign(b: Long) {
        minus(this, this, b, b)
    }

    infix operator fun minusAssign(b: Vec2ul) {
        minus(this, this, b.x, b.y)
    }


    infix operator fun times(b: Ulong) = times(Vec2ul(), this, b, b)
    infix operator fun times(b: Long) = times(Vec2ul(), this, b, b)
    infix operator fun times(b: Vec2ul) = times(Vec2ul(), this, b.x, b.y)

    @JvmOverloads
    fun times(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = times(res, this, bX, bY)

    @JvmOverloads
    fun times(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = times(res, this, bX, bY)

    fun times(b: Ulong, res: Vec2ul) = times(res, this, b, b)
    fun times(b: Long, res: Vec2ul) = times(res, this, b, b)
    fun times(b: Vec2ul, res: Vec2ul) = times(res, this, b.x, b.y)

    fun timesAssign(bX: Ulong, bY: Ulong) = times(this, this, bX, bY)
    fun timesAssign(bX: Long, bY: Long) = times(this, this, bX, bY)
    infix operator fun timesAssign(b: Ulong) {
        times(this, this, b, b)
    }

    infix operator fun timesAssign(b: Long) {
        times(this, this, b, b)
    }

    infix operator fun timesAssign(b: Vec2ul) {
        times(this, this, b.x, b.y)
    }


    infix operator fun div(b: Ulong) = div(Vec2ul(), this, b, b)
    infix operator fun div(b: Long) = div(Vec2ul(), this, b, b)
    infix operator fun div(b: Vec2ul) = div(Vec2ul(), this, b.x, b.y)

    @JvmOverloads
    fun div(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = div(res, this, bX, bY)

    @JvmOverloads
    fun div(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = div(res, this, bX, bY)

    fun div(b: Ulong, res: Vec2ul) = div(res, this, b, b)
    fun div(b: Long, res: Vec2ul) = div(res, this, b, b)
    fun div(b: Vec2ul, res: Vec2ul) = div(res, this, b.x, b.y)

    fun divAssign(bX: Ulong, bY: Ulong) = div(this, this, bX, bY)
    fun divAssign(bX: Long, bY: Long) = div(this, this, bX, bY)
    infix operator fun divAssign(b: Ulong) {
        div(this, this, b, b)
    }

    infix operator fun divAssign(b: Long) {
        div(this, this, b, b)
    }

    infix operator fun divAssign(b: Vec2ul) {
        div(this, this, b.x, b.y)
    }


    infix operator fun rem(b: Ulong) = rem(Vec2ul(), this, b, b)
    infix operator fun rem(b: Long) = rem(Vec2ul(), this, b, b)
    infix operator fun rem(b: Vec2ul) = rem(Vec2ul(), this, b.x, b.y)

    @JvmOverloads
    fun rem(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = rem(res, this, bX, bY)

    @JvmOverloads
    fun rem(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = rem(res, this, bX, bY)

    fun rem(b: Ulong, res: Vec2ul) = rem(res, this, b, b)
    fun rem(b: Long, res: Vec2ul) = rem(res, this, b, b)
    fun rem(b: Vec2ul, res: Vec2ul) = rem(res, this, b.x, b.y)

    fun remAssign(bX: Ulong, bY: Ulong) = rem(this, this, bX, bY)
    fun remAssign(bX: Long, bY: Long) = rem(this, this, bX, bY)
    infix operator fun remAssign(b: Ulong) {
        rem(this, this, b, b)
    }

    infix operator fun remAssign(b: Long) {
        rem(this, this, b, b)
    }

    infix operator fun remAssign(b: Vec2ul) {
        rem(this, this, b.x, b.y)
    }


    // -- Generic binary arithmetic operators --

    infix operator fun plus(b: Number) = plus(Vec2ul(), this, b.L, b.L)
    infix operator fun plus(b: Vec2t<out Number>) = plus(Vec2ul(), this, b._x.L, b._y.L)

    @JvmOverloads
    fun plus(bX: Number, bY: Number, res: Vec2ul = Vec2ul()) = plus(res, this, bX.L, bY.L)

    fun plus(b: Number, res: Vec2ul) = plus(res, this, b.L, b.L)
    fun plus(b: Vec2t<out Number>, res: Vec2ul) = plus(res, this, b._x.L, b._y.L)

    fun plusAssign(bX: Number, bY: Number) = plus(this, this, bX.L, bY.L)
    infix operator fun plusAssign(b: Number) {
        plus(this, this, b.L, b.L)
    }

    infix operator fun plusAssign(b: Vec2t<out Number>) {
        plus(this, this, b._x.L, b._y.L)
    }


    infix operator fun minus(b: Number) = minus(Vec2ul(), this, b.L, b.L)
    infix operator fun minus(b: Vec2t<out Number>) = minus(Vec2ul(), this, b._x.L, b._y.L)

    @JvmOverloads
    fun minus(bX: Number, bY: Number, res: Vec2ul = Vec2ul()) = minus(res, this, bX.L, bY.L)

    fun minus(b: Number, res: Vec2ul) = minus(res, this, b.L, b.L)
    fun minus(b: Vec2t<out Number>, res: Vec2ul) = minus(res, this, b._x.L, b._y.L)

    fun minusAssign(bX: Number, bY: Number) = minus(this, this, bX.L, bY.L)
    infix operator fun minusAssign(b: Number) {
        minus(this, this, b.L, b.L)
    }

    infix operator fun minusAssign(b: Vec2t<out Number>) {
        minus(this, this, b._x.L, b._y.L)
    }


    infix operator fun times(b: Number) = times(Vec2ul(), this, b.L, b.L)
    infix operator fun times(b: Vec2t<out Number>) = times(Vec2ul(), this, b._x.L, b._y.L)

    @JvmOverloads
    fun times(bX: Number, bY: Number, res: Vec2ul = Vec2ul()) = times(res, this, bX.L, bY.L)

    fun times(b: Number, res: Vec2ul) = times(res, this, b.L, b.L)
    fun times(b: Vec2t<out Number>, res: Vec2ul) = times(res, this, b._x.L, b._y.L)

    fun timesAssign(bX: Number, bY: Number) = times(this, this, bX.L, bY.L)
    infix operator fun timesAssign(b: Number) {
        times(this, this, b.L, b.L)
    }

    infix operator fun timesAssign(b: Vec2t<out Number>) {
        times(this, this, b._x.L, b._y.L)
    }


    infix operator fun div(b: Number) = div(Vec2ul(), this, b.L, b.L)
    infix operator fun div(b: Vec2t<out Number>) = div(Vec2ul(), this, b._x.L, b._y.L)

    @JvmOverloads
    fun div(bX: Number, bY: Number, res: Vec2ul = Vec2ul()) = div(res, this, bX.L, bY.L)

    fun div(b: Number, res: Vec2ul) = div(res, this, b.L, b.L)
    fun div(b: Vec2t<out Number>, res: Vec2ul) = div(res, this, b._x.L, b._y.L)

    fun divAssign(bX: Number, bY: Number) = div(this, this, bX.L, bY.L)
    infix operator fun divAssign(b: Number) {
        div(this, this, b.L, b.L)
    }

    infix operator fun divAssign(b: Vec2t<out Number>) {
        div(this, this, b._x.L, b._y.L)
    }


    infix operator fun rem(b: Number) = rem(Vec2ul(), this, b.L, b.L)
    infix operator fun rem(b: Vec2t<out Number>) = rem(Vec2ul(), this, b._x.L, b._y.L)

    @JvmOverloads
    fun rem(bX: Number, bY: Number, res: Vec2ul = Vec2ul()) = rem(res, this, bX.L, bY.L)

    fun rem(b: Number, res: Vec2ul) = rem(res, this, b.L, b.L)
    fun rem(b: Vec2t<out Number>, res: Vec2ul) = rem(res, this, b._x.L, b._y.L)

    fun remAssign(bX: Number, bY: Number) = rem(this, this, bX.L, bY.L)
    infix operator fun remAssign(b: Number) {
        rem(this, this, b.L, b.L)
    }

    infix operator fun remAssign(b: Vec2t<out Number>) {
        rem(this, this, b._x.L, b._y.L)
    }


    // -- Specific bitwise operators --

    infix fun and(b: Ulong) = and(Vec2ul(), this, b, b)
    infix fun and(b: Long) = and(Vec2ul(), this, b, b)
    infix fun and(b: Vec2ul) = and(Vec2ul(), this, b.x, b.y)

    fun and(b: Ulong, res: Vec2ul) = and(res, this, b, b)
    fun and(b: Long, res: Vec2ul) = and(res, this, b, b)
    fun and(b: Vec2ul, res: Vec2ul) = and(res, this, b.x, b.y)

    @JvmOverloads
    fun and(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = and(res, this, bX, bY)

    @JvmOverloads
    fun and(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = and(res, this, bX, bY)

    infix fun andAssign(b: Ulong) = and(this, this, b, b)
    infix fun andAssign(b: Long) = and(this, this, b, b)
    infix fun andAssign(b: Vec2ul) = and(this, this, b.x, b.y)
    fun andAssign(bX: Ulong, bY: Ulong) = and(this, this, bX, bY)
    fun andAssign(bX: Long, bY: Long) = and(this, this, bX, bY)


    infix fun or(b: Ulong) = or(Vec2ul(), this, b, b)
    infix fun or(b: Long) = or(Vec2ul(), this, b, b)
    infix fun or(b: Vec2ul) = or(Vec2ul(), this, b.x, b.y)

    fun or(b: Ulong, res: Vec2ul) = or(res, this, b, b)
    fun or(b: Long, res: Vec2ul) = or(res, this, b, b)
    fun or(b: Vec2ul, res: Vec2ul) = or(res, this, b.x, b.y)

    @JvmOverloads
    fun or(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = or(res, this, bX, bY)

    @JvmOverloads
    fun or(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = or(res, this, bX, bY)

    infix fun orAssign(b: Ulong) = or(this, this, b, b)
    infix fun orAssign(b: Long) = or(this, this, b, b)
    infix fun orAssign(b: Vec2ul) = or(this, this, b.x, b.y)
    fun orAssign(bX: Ulong, bY: Ulong) = or(this, this, bX, bY)
    fun orAssign(bX: Long, bY: Long) = or(this, this, bX, bY)


    infix fun xor(b: Ulong) = xor(Vec2ul(), this, b, b)
    infix fun xor(b: Long) = xor(Vec2ul(), this, b, b)
    infix fun xor(b: Vec2ul) = xor(Vec2ul(), this, b.x, b.y)

    fun xor(b: Ulong, res: Vec2ul) = xor(res, this, b, b)
    fun xor(b: Long, res: Vec2ul) = xor(res, this, b, b)
    fun xor(b: Vec2ul, res: Vec2ul) = xor(res, this, b.x, b.y)

    @JvmOverloads
    fun xor(bX: Ulong, bY: Ulong, res: Vec2ul = Vec2ul()) = xor(res, this, bX, bY)

    @JvmOverloads
    fun xor(bX: Long, bY: Long, res: Vec2ul = Vec2ul()) = xor(res, this, bX, bY)

    infix fun xorAssign(b: Ulong) = xor(this, this, b, b)
    infix fun xorAssign(b: Long) = xor(this, this, b, b)
    infix fun xorAssign(b: Vec2ul) = xor(this, this, b.x, b.y)
    fun xorAssign(bX: Ulong, bY: Ulong) = xor(this, this, bX, bY)
    fun xorAssign(bX: Long, bY: Long) = xor(this, this, bX, bY)


    infix fun shl(b: Int) = shl(Vec2ul(), this, b, b)

    fun shl(b: Int, res: Vec2ul) = shl(res, this, b, b)

    @JvmOverloads
    fun shl(bX: Int, bY: Int, res: Vec2ul = Vec2ul()) = shl(res, this, bX, bY)

    infix fun shlAssign(b: Int) = shl(this, this, b, b)
    fun shlAssign(bX: Int, bY: Int) = shl(this, this, bX, bY)


    infix fun shr(b: Int) = shr(Vec2ul(), this, b, b)

    fun shr(b: Int, res: Vec2ul) = shr(res, this, b, b)

    @JvmOverloads
    fun shr(bX: Int, bY: Int, res: Vec2ul = Vec2ul()) = shr(res, this, bX, bY)

    infix fun shrAssign(b: Int) = shr(this, this, b, b)
    fun shrAssign(bX: Int, bY: Int) = shr(this, this, bX, bY)


    @JvmOverloads
    fun inv(res: Vec2ul = Vec2ul()) = inv(res, this)

    fun invAssign() = inv(this, this)


    // -- Generic bitwise operators --

    infix fun and(b: Number) = and(Vec2ul(), this, b.L, b.L)
    fun and(bX: Number, bY: Number) = and(Vec2ul(), this, bX.L, bY.L)
    fun and(b: Vec2t<out Number>) = and(Vec2ul(), this, b._x.L, b._y.L)

    fun and(b: Number, res: Vec2ul) = and(res, this, b.L, b.L)
    fun and(bX: Number, bY: Number, res: Vec2ul) = and(res, this, bX.L, bY.L)
    fun and(b: Vec2t<out Number>, res: Vec2ul) = and(res, this, b._x.L, b._y.L)

    infix fun andAssign(b: Number) = and(this, this, b.L, b.L)
    fun andAssign(bX: Number, bY: Number) = and(this, this, bX.L, bY.L)
    infix fun andAssign(b: Vec2t<out Number>) = and(this, this, b._x.L, b._y.L)


    infix fun or(b: Number) = or(Vec2ul(), this, b.L, b.L)
    fun or(bX: Number, bY: Number) = or(Vec2ul(), this, bX.L, bY.L)
    fun or(b: Vec2t<out Number>) = or(Vec2ul(), this, b._x.L, b._y.L)

    fun or(b: Number, res: Vec2ul) = or(res, this, b.L, b.L)
    fun or(bX: Number, bY: Number, res: Vec2ul) = or(res, this, bX.L, bY.L)
    fun or(b: Vec2t<out Number>, res: Vec2ul) = or(res, this, b._x.L, b._y.L)

    infix fun orAssign(b: Number) = or(this, this, b.L, b.L)
    fun orAssign(bX: Number, bY: Number) = or(this, this, bX.L, bY.L)
    infix fun orAssign(b: Vec2t<out Number>) = or(this, this, b._x.L, b._y.L)


    infix fun xor(b: Number) = xor(Vec2ul(), this, b.L, b.L)
    fun xor(bX: Number, bY: Number) = xor(Vec2ul(), this, bX.L, bY.L)
    fun xor(b: Vec2t<out Number>) = xor(Vec2ul(), this, b._x.L, b._y.L)

    fun xor(b: Number, res: Vec2ul) = xor(res, this, b.L, b.L)
    fun xor(bX: Number, bY: Number, res: Vec2ul) = xor(res, this, bX.L, bY.L)
    fun xor(b: Vec2t<out Number>, res: Vec2ul) = xor(res, this, b._x.L, b._y.L)

    infix fun xorAssign(b: Number) = xor(this, this, b.L, b.L)
    fun xorAssign(bX: Number, bY: Number) = xor(this, this, bX.L, bY.L)
    infix fun xorAssign(b: Vec2t<out Number>) = xor(this, this, b._x.L, b._y.L)


    infix fun shl(b: Number) = shl(Vec2ul(), this, b.L, b.L)
    fun shl(bX: Number, bY: Number) = shl(Vec2ul(), this, bX.L, bY.L)

    fun shl(b: Number, res: Vec2ul) = shl(res, this, b.L, b.L)
    fun shl(bX: Number, bY: Number, res: Vec2ul) = shl(res, this, bX.L, bY.L)

    infix fun shlAssign(b: Number) = shl(this, this, b.L, b.L)
    fun shlAssign(bX: Number, bY: Number) = shl(this, this, bX.L, bY.L)


    infix fun shr(b: Number) = shr(Vec2ul(), this, b.L, b.L)
    fun shr(bX: Number, bY: Number) = shr(Vec2ul(), this, bX.L, bY.L)

    fun shr(b: Number, res: Vec2ul) = shr(res, this, b.L, b.L)
    fun shr(bX: Number, bY: Number, res: Vec2ul) = shr(res, this, bX.L, bY.L)

    infix fun shrAssign(b: Number) = shr(this, this, b.L, b.L)
    fun shrAssign(bX: Number, bY: Number) = shr(this, this, bX.L, bY.L)


    infix fun allLessThan(ul: Ulong): Boolean = x < ul && y < ul
    infix fun anyLessThan(ul: Ulong): Boolean = x < ul || y < ul
    infix fun lessThan(ul: Ulong): Vec2bool = Vec2bool { get(it) < ul }

    infix fun allLessThanEqual(ul: Ulong): Boolean = x <= ul && y <= ul
    infix fun anyLessThanEqual(ul: Ulong): Boolean = x <= ul || y <= ul
    infix fun lessThanEqual(ul: Ulong): Vec2bool = Vec2bool { get(it) <= ul }

    infix fun allEqual(ul: Ulong): Boolean = x == ul && y == ul
    infix fun anyEqual(ul: Ulong): Boolean = x == ul || y == ul
    infix fun equal(ul: Ulong): Vec2bool = Vec2bool { get(it) == ul }

    infix fun allNotEqual(ul: Ulong): Boolean = x != ul && y != ul
    infix fun anyNotEqual(ul: Ulong): Boolean = x != ul || y != ul
    infix fun notEqual(ul: Ulong): Vec2bool = Vec2bool { get(it) != ul }

    infix fun allGreaterThan(ul: Ulong): Boolean = x > ul && y > ul
    infix fun anyGreaterThan(ul: Ulong): Boolean = x > ul || y > ul
    infix fun greaterThan(ul: Ulong): Vec2bool = Vec2bool { get(it) > ul }

    infix fun allGreaterThanEqual(ul: Ulong): Boolean = x >= ul && y >= ul
    infix fun anyGreaterThanEqual(ul: Ulong): Boolean = x >= ul || y >= ul
    infix fun greaterThanEqual(ul: Ulong): Vec2bool = Vec2bool { get(it) >= ul }


    infix fun allLessThan(v: Vec2ul): Boolean = x < v.x && y < v.y
    infix fun anyLessThan(v: Vec2ul): Boolean = x < v.x || y < v.y
    infix fun lessThan(v: Vec2ul): Vec2bool = Vec2bool { get(it) < v[it] }

    infix fun allLessThanEqual(v: Vec2ul): Boolean = x <= v.x && y <= v.y
    infix fun anyLessThanEqual(v: Vec2ul): Boolean = x <= v.x || y <= v.y
    infix fun lessThanEqual(v: Vec2ul): Vec2bool = Vec2bool { get(it) <= v[it] }

    infix fun allEqual(v: Vec2ul): Boolean = x == v.x && y == v.y
    infix fun anyEqual(v: Vec2ul): Boolean = x == v.x || y == v.y
    infix fun equal(v: Vec2ul): Vec2bool = Vec2bool { get(it) == v[it] }

    infix fun allNotEqual(v: Vec2ul): Boolean = x != v.x && y != v.y
    infix fun anyNotEqual(v: Vec2ul): Boolean = x != v.x || y != v.y
    infix fun notEqual(v: Vec2ul): Vec2bool = Vec2bool { get(it) != v[it] }

    infix fun allGreaterThan(v: Vec2ul): Boolean = x > v.x && y > v.y
    infix fun anyGreaterThan(v: Vec2ul): Boolean = x > v.x || y > v.y
    infix fun greaterThan(v: Vec2ul): Vec2bool = Vec2bool { get(it) > v[it] }

    infix fun allGreaterThanEqual(v: Vec2ul): Boolean = x >= v.x && y >= v.y
    infix fun anyGreaterThanEqual(v: Vec2ul): Boolean = x >= v.x || y >= v.y
    infix fun greaterThanEqual(v: Vec2ul): Vec2bool = Vec2bool { get(it) >= v[it] }


    companion object : opVec2ul {
        const val length = Vec2t.LENGTH

        @JvmField
        val size = length * Ulong.BYTES

        @JvmStatic
        fun fromPointer(ptr: Ptr<Ulong>) = Vec2ul().apply {
            val p = ptr.toPtr<Long>()
            x.v = p[0]
            y.v = p[1]
        }
    }

    override fun size() = size

    override fun equals(other: Any?) = other is Vec2ul && this[0] == other[0] && this[1] == other[1]

    override fun hashCode() = 31 * x.v.hashCode() + y.v.hashCode()

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
    //@formatter:on

    override inline operator fun get(index: Int) = array[ofs + index]

    inline operator fun set(index: Int, value: Ulong) {
        array[ofs + index] = value
    }

    override inline operator fun component1() = x
    override inline operator fun component2() = y


    override fun toString(): String = "($x, $y)"
}
