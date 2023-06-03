package  glm_.mat4x4

import glm_.*
import glm_.glm.inverse
import glm_.glm.transpose
import glm_.mat2x2.Mat2
import glm_.mat2x2.Mat2d
import glm_.mat2x2.Mat2x2t
import glm_.mat2x3.Mat2x3t
import glm_.mat2x4.Mat2x4t
import glm_.mat3x2.Mat3x2t
import glm_.mat3x3.Mat3
import glm_.mat3x3.Mat3d
import glm_.mat3x4.Mat3x4t
import glm_.mat4x2.Mat4x2t
import glm_.mat4x3.Mat4x3t
import glm_.mat4x4.operators.mat4x4_operators
import glm_.quat.Quat
import glm_.vec2.Vec2
import glm_.vec2.Vec2t
import glm_.vec3.Vec3
import glm_.vec3.Vec3t
import glm_.vec4.Vec4
import glm_.vec4.Vec4bool
import glm_.vec4.Vec4t
import kool.*
import org.lwjgl.system.MemoryUtil.memGetFloat
import org.lwjgl.system.MemoryUtil.memPutFloat
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.FloatBuffer

/**
 * Created by GBarbieri on 10.11.2016.
 */
class Mat4 private constructor(@Suppress("UNUSED_PARAMETER") dummy: Int, @JvmField var array: FloatArray) : Mat4x4t<Float>(), ToFloatBuffer {

    // -- Constructors --

    /**
     * Creates a identity matrix
     */
    constructor() : this(1.0f)

    /**
     * Creates a matrix with the diagonal set to [s]
     */
    constructor(s: Float) : this(s, s, s, s)

    /**
     * Creates a matrix with the diagonal set to [s]
     */
    constructor(s: Number) : this(s, s, s, s)

    /**
     * Creates a matrix with the diagonal set to [x], [y], [z] and 1.0
     */
    constructor(x: Number, y: Number, z: Number) : this(x, y, z, 1f)    // TODO others

    /**
     * Creates a matrix with the diagonal set to [x], [y], [z] and [w]:
     */
    constructor(x: Float, y: Float, z: Float, w: Float) : this(
            x, 0, 0, 0,
            0, y, 0, 0,
            0, 0, z, 0,
            0, 0, 0, w)

    /**
     * Creates a matrix with the diagonal set to [x], [y], [z] and [w]:
     */
    constructor(x: Number, y: Number, z: Number, w: Number) : this(
            x, 0f, 0f, 0f,
            0f, y, 0f, 0f,
            0f, 0f, z, 0f,
            0f, 0f, 0f, w)

    // TODO others

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, 0 and 1:
     */
    constructor(v: Vec2t<*>) : this(v._x, v._y, 0, 1)

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, z and 1
     */
    constructor(v: Vec2t<*>, z: Number) : this(v._x, v._y, z, 1)

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, z and w
     */
    constructor(v: Vec2t<*>, z: Number, w: Number) : this(v._x, v._y, z, w)

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, [v].z and 1
     */
    constructor(v: Vec3t<*>) : this(v._x, v._y, v._z, 1)

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, [v].z and w
     */
    constructor(v: Vec3t<*>, w: Number) : this(v._x, v._y, v._z, w)

    /**
     * Creates a matrix with the diagonal set to [v].x, [v].y, [v].z and [v].w
     */
    constructor(v: Vec4t<*>) : this(v._x, v._y, v._z, v._w)

    /**
     * Creates a matrix with the
     * first column of [a] and [aW],
     * the second [b] and [bW],
     * the third [c] and [cW] and
     * the last [d] and [dW]
     */
    constructor(a: Vec3t<*>, aW: Number, b: Vec3t<*>, bW: Number, c: Vec3t<*>, cW: Number, d: Vec3t<*>, dW: Number) : this(a._x, a._y, a._z, aW, b._x, b._y, b._z, bW, c._x, c._y, c._z, cW, d._x, d._y, d._z, dW)


    // TODO(wasabi): check, either the variable names a chosen badly or the order passed to the array is wrong
    constructor(x0: Number, y0: Number, z0: Number, w0: Number,
                x1: Number, y1: Number, z1: Number, w1: Number,
                x2: Number, y2: Number, z2: Number, w2: Number,
                x3: Number, y3: Number, z3: Number, w3: Number) : this(0, floatArrayOf(
            x0.f, y0.f, z0.f, w0.f,
            x1.f, y1.f, z1.f, w1.f,
            x2.f, y2.f, z2.f, w2.f,
            x3.f, y3.f, z3.f, w3.f,
    ))

    // TODO(wasabi): check, either the variable names a chosen badly or the order passed to the array is wrong
    constructor(x0: Float, y0: Float, z0: Float, w0: Float,
                x1: Float, y1: Float, z1: Float, w1: Float,
                x2: Float, y2: Float, z2: Float, w2: Float,
                x3: Float, y3: Float, z3: Float, w3: Float) : this(0, floatArrayOf(
            x0, y0, z0, w0,
            x1, y1, z1, w1,
            x2, y2, z2, w2,
            x3, y3, z3, w3,
    ))

    constructor(v0: Vec4t<out Number>, v1: Vec4t<out Number>, v2: Vec4t<out Number>, v3: Vec4t<out Number>) : this(
            v0._x, v0._y, v0._z, v0._w,
            v1._x, v1._y, v1._z, v1._w,
            v2._x, v2._y, v2._z, v2._w,
            v3._x, v3._y, v3._z, v3._w)

    constructor(block: (Int) -> Number) : this(
        block(0).f, block(1).f, block(2).f, block(3).f,
        block(4).f, block(5).f, block(6).f, block(7).f,
        block(8).f, block(9).f, block(10).f, block(11).f,
        block(12).f, block(13).f, block(14).f, block(15).f)

    constructor(block: (Int, Int) -> Number) : this(
        block(0, 0).f, block(0, 1).f, block(0, 2).f, block(0, 3).f,
        block(1, 0).f, block(1, 1).f, block(1, 2).f, block(1, 3).f,
        block(2, 0).f, block(2, 1).f, block(2, 2).f, block(2, 3).f,
        block(3, 0).f, block(3, 1).f, block(3, 2).f, block(3, 3).f)

    constructor(list: Iterable<*>, index: Int = 0) : this(
        list.elementAt(index)!!.toFloat, list.elementAt(index + 1)!!.toFloat, list.elementAt(index + 2)!!.toFloat, list.elementAt(index + 3)!!.toFloat,
        list.elementAt(index + 4)!!.toFloat, list.elementAt(index + 5)!!.toFloat, list.elementAt(index + 6)!!.toFloat, list.elementAt(index + 7)!!.toFloat,
        list.elementAt(index + 8)!!.toFloat, list.elementAt(index + 9)!!.toFloat, list.elementAt(index + 10)!!.toFloat, list.elementAt(index + 11)!!.toFloat,
        list.elementAt(index + 12)!!.toFloat, list.elementAt(index + 13)!!.toFloat, list.elementAt(index + 14)!!.toFloat, list.elementAt(index + 15)!!.toFloat)

    constructor(buffer: ByteBuffer, offset: Int) : this(
        buffer.getFloat(offset),
        buffer.getFloat(offset + Float.BYTES),
        buffer.getFloat(offset + Float.BYTES * 2),
        buffer.getFloat(offset + Float.BYTES * 3),
        buffer.getFloat(offset + Float.BYTES * 4),
        buffer.getFloat(offset + Float.BYTES * 5),
        buffer.getFloat(offset + Float.BYTES * 6),
        buffer.getFloat(offset + Float.BYTES * 7),
        buffer.getFloat(offset + Float.BYTES * 8),
        buffer.getFloat(offset + Float.BYTES * 9),
        buffer.getFloat(offset + Float.BYTES * 10),
        buffer.getFloat(offset + Float.BYTES * 11),
        buffer.getFloat(offset + Float.BYTES * 12),
        buffer.getFloat(offset + Float.BYTES * 13),
        buffer.getFloat(offset + Float.BYTES * 14),
        buffer.getFloat(offset + Float.BYTES * 15))

    constructor(buffer: FloatBuffer, index: Int = buffer.pos) : this(
        buffer[index], buffer[index + 1], buffer[index + 2], buffer[index + 3],
        buffer[index + 4], buffer[index + 5], buffer[index + 6], buffer[index + 7],
        buffer[index + 8], buffer[index + 9], buffer[index + 10], buffer[index + 11],
        buffer[index + 12], buffer[index + 13], buffer[index + 14], buffer[index + 15])

    constructor(ptr: Ptr<Float>) : this(block = { i -> ptr[i] })

    // -- Matrix conversions --

    constructor(mat2: Mat2) : this(
        mat2[0, 0], mat2[0, 1], 0f, 0f,
        mat2[1, 0], mat2[1, 1], 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f)

    constructor(mat2: Mat2d) : this(
        mat2[0, 0], mat2[0, 1], 0.0, 0.0,
        mat2[1, 0], mat2[1, 1], 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0)

    constructor(mat3: Mat3) : this(
        mat3[0, 0], mat3[0, 1], mat3[0, 2], 0,
        mat3[1, 0], mat3[1, 1], mat3[1, 2], 0,
        mat3[2, 0], mat3[2, 1], mat3[2, 2], 0,
        0, 0, 0, 1)

    constructor(mat3: Mat3d) : this(
        mat3[0, 0], mat3[0, 1], mat3[0, 2], 0,
        mat3[1, 0], mat3[1, 1], mat3[1, 2], 0,
        mat3[2, 0], mat3[2, 1], mat3[2, 2], 0,
        0, 0, 0, 1)

    constructor(mat4: Mat4) : this(0, mat4.array.clone())
    constructor(mat4: Mat4d) : this(0, FloatArray(length) { mat4.array[it].f })

    constructor(mat2x3: Mat2x3t<*>) : this(
        mat2x3[0, 0], mat2x3[0, 1], mat2x3[0, 2], 0,
        mat2x3[1, 0], mat2x3[1, 1], mat2x3[1, 2], 0,
        0, 0, 1, 0,
        0, 0, 0, 1)

    constructor(mat3x2: Mat3x2t<*>) : this(
        mat3x2[0, 0], mat3x2[0, 1], 0, 0,
        mat3x2[1, 0], mat3x2[1, 1], 0, 0,
        mat3x2[2, 0], mat3x2[2, 1], 1, 0,
        0, 0, 0, 1)

    constructor(mat2x4: Mat2x4t<*>) : this(
        mat2x4[0, 0], mat2x4[0, 1], mat2x4[0, 2], mat2x4[0, 3],
        mat2x4[1, 0], mat2x4[1, 1], mat2x4[1, 2], mat2x4[1, 3],
        0, 0, 1, 0,
        0, 0, 0, 1)

    constructor(mat4x2: Mat4x2t<*>) : this(
        mat4x2[0, 0], mat4x2[0, 1], 0, 0,
        mat4x2[1, 0], mat4x2[1, 1], 0, 0,
        mat4x2[2, 0], mat4x2[2, 1], 1, 0,
        mat4x2[3, 0], mat4x2[3, 1], 0, 1)

    constructor(mat3x4: Mat3x4t<*>) : this(
        mat3x4[0, 0], mat3x4[0, 1], mat3x4[0, 2], mat3x4[0, 3],
        mat3x4[1, 0], mat3x4[1, 1], mat3x4[1, 2], mat3x4[1, 3],
        mat3x4[2, 0], mat3x4[2, 1], mat3x4[2, 2], mat3x4[2, 3],
        0, 0, 0, 1)

    constructor(mat4x3: Mat4x3t<*>) : this(
        mat4x3[0, 0], mat4x3[0, 1], mat4x3[0, 2], 0,
        mat4x3[1, 0], mat4x3[1, 1], mat4x3[1, 2], 0,
        mat4x3[2, 0], mat4x3[2, 1], mat4x3[2, 2], 0,
        mat4x3[3, 0], mat4x3[3, 1], mat4x3[3, 2], 1)

    // TODO others
    @JvmOverloads
    constructor(floats: FloatArray, transpose: Boolean = false) : this(0,
                                                                       if (transpose) floatArrayOf(
                                                                           floats[0], floats[4], floats[8], floats[12],
                                                                           floats[1], floats[5], floats[9], floats[13],
                                                                           floats[2], floats[6], floats[10], floats[14],
                                                                           floats[3], floats[7], floats[11], floats[15])
                                                                       else floats.clone())

    // TODO others
    constructor(inputStream: InputStream, bigEndian: Boolean = true) : this(
        inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian),
        inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian),
        inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian),
        inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian), inputStream.float(bigEndian))

    fun put(v0: Vec4, v1: Vec4, v2: Vec4, v3: Vec4) {
        v0.to(array, 0)
        v1.to(array, 4)
        v2.to(array, 8)
        v3.to(array, 12)
    }


    infix operator fun invoke(s: Float) = invoke(s, s, s, s)

    infix operator fun invoke(v: Vec2) = invoke(v.x, v.y, 1f, 1f)
    infix operator fun invoke(v: Vec3) = invoke(v.x, v.y, v.z, 1f)
    infix operator fun invoke(v: Vec4) = invoke(v.x, v.y, v.z, v.w)

    infix operator fun invoke(floats: FloatArray) = invoke(
        floats[0], floats[1], floats[2], floats[3],
        floats[4], floats[5], floats[6], floats[7],
        floats[8], floats[9], floats[10], floats[11],
        floats[12], floats[13], floats[14], floats[15])

    infix operator fun invoke(mat2: Mat2) = invoke(
        mat2[0, 0], mat2[0, 1], 0f, 0f,
        mat2[1, 0], mat2[1, 1], 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f)

    infix operator fun invoke(mat2: Mat2d) = invoke(
        mat2[0, 0].f, mat2[0, 1].f, 0f, 0f,
        mat2[1, 0].f, mat2[1, 1].f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f)

    infix operator fun invoke(mat3: Mat3) = invoke(
        mat3[0, 0], mat3[0, 1], mat3[0, 2], 0f,
        mat3[1, 0], mat3[1, 1], mat3[1, 2], 0f,
        mat3[2, 0], mat3[2, 1], mat3[2, 2], 0f,
        0f, 0f, 0f, 1f)

    infix operator fun invoke(mat3: Mat3d) = invoke(
        mat3[0, 0].f, mat3[0, 1].f, mat3[0, 2].f, 0f,
        mat3[1, 0].f, mat3[1, 1].f, mat3[1, 2].f, 0f,
        mat3[2, 0].f, mat3[2, 1].f, mat3[2, 2].f, 0f,
        0f, 0f, 0f, 1f)

    infix operator fun invoke(mat4: Mat4) = invoke(mat4.array.clone())
    infix operator fun invoke(mat4: Mat4d) = invoke(FloatArray(length) { mat4.array[it].f })

//    infix operator fun invoke(mat2x3: Mat2x3t<*>) = invoke(
//            mat2x3[0, 0], mat2x3[0, 1], mat2x3[0, 2], 0,
//            mat2x3[1, 0], mat2x3[1, 1], mat2x3[1, 2], 0,
//            0, 0, 1, 0,
//            0, 0, 0, 1)
//
//    infix operator fun invoke(mat3x2: Mat3x2t<*>) = invoke(
//            mat3x2[0, 0], mat3x2[0, 1], 0, 0,
//            mat3x2[1, 0], mat3x2[1, 1], 0, 0,
//            mat3x2[2, 0], mat3x2[2, 1], 1, 0,
//            0, 0, 0, 1)
//
//    infix operator fun invoke(mat2x4: Mat2x4t<*>) = invoke(
//            mat2x4[0, 0], mat2x4[0, 1], mat2x4[0, 2], mat2x4[0, 3],
//            mat2x4[1, 0], mat2x4[1, 1], mat2x4[1, 2], mat2x4[1, 3],
//            0, 0, 1, 0,
//            0, 0, 0, 1)
//
//    infix operator fun invoke(mat4x2: Mat4x2t<*>) = invoke(
//            mat4x2[0, 0], mat4x2[0, 1], 0, 0,
//            mat4x2[1, 0], mat4x2[1, 1], 0, 0,
//            mat4x2[2, 0], mat4x2[2, 1], 1, 0,
//            mat4x2[3, 0], mat4x2[3, 1], 0, 1)
//
//    infix operator fun invoke(mat3x4: Mat3x4t<*>) = invoke(
//            mat3x4[0, 0], mat3x4[0, 1], mat3x4[0, 2], mat3x4[0, 3],
//            mat3x4[1, 0], mat3x4[1, 1], mat3x4[1, 2], mat3x4[1, 3],
//            mat3x4[2, 0], mat3x4[2, 1], mat3x4[2, 2], mat3x4[2, 3],
//            0, 0, 0, 1)
//
//    infix operator fun invoke(mat4x3: Mat4x3t<*>) = invoke(
//            mat4x3[0, 0], mat4x3[0, 1], mat4x3[0, 2], 0,
//            mat4x3[1, 0], mat4x3[1, 1], mat4x3[1, 2], 0,
//            mat4x3[2, 0], mat4x3[2, 1], mat4x3[2, 2], 0,
//            mat4x3[3, 0], mat4x3[3, 1], mat4x3[3, 2], 1)

    operator fun invoke(x: Float, y: Float, z: Float, w: Float) = invoke(
        x, 0f, 0f, 0f,
        0f, y, 0f, 0f,
        0f, 0f, z, 0f,
        0f, 0f, 0f, w)

    operator fun invoke(x: Number, y: Number, z: Number, w: Number) = invoke(
        x.f, 0f, 0f, 0f,
        0f, y.f, 0f, 0f,
        0f, 0f, z.f, 0f,
        0f, 0f, 0f, w.f)

    operator fun invoke(a0: Float, a1: Float, a2: Float, a3: Float,
                        b0: Float, b1: Float, b2: Float, b3: Float,
                        c0: Float, c1: Float, c2: Float, c3: Float,
                        d0: Float, d1: Float, d2: Float, d3: Float): Mat4 {

        put(a0, a1, a2, a3, b0, b1, b2, b3, c0, c1, c2, c3, d0, d1, d2, d3)
        return this
    }

    operator fun invoke(a0: Number, a1: Number, a2: Number, a3: Number,
                        b0: Number, b1: Number, b2: Number, b3: Number,
                        c0: Number, c1: Number, c2: Number, c3: Number,
                        d0: Number, d1: Number, d2: Number, d3: Number): Mat4 {

        put(a0.f, a1.f, a2.f, a3.f, b0.f, b1.f, b2.f, b3.f, c0.f, c1.f, c2.f, c3.f, d0.f, d1.f, d2.f, d3.f)
        return this
    }

    infix fun put(mat4: Mat4) {
        if (this !== mat4)
            System.arraycopy(mat4.array, 0, array, 0, length)
    }

    fun identity() = invoke(1f)
    infix fun put(s: Float) = put(s, s, s, s)
    infix fun put(v: Vec2) = put(v.x, v.y, 1f, 1f)
    infix fun put(v: Vec3) = put(v.x, v.y, v.z, 1f)
    infix fun put(v: Vec4) = put(v.x, v.y, v.z, v.w)

    infix fun put(floats: FloatArray) = put(floats[0], floats[1], floats[2], floats[3], floats[4], floats[5], floats[6],
                                            floats[7], floats[8], floats[9], floats[10], floats[11], floats[12], floats[13], floats[14], floats[15])

    fun put(x: Float, y: Float, z: Float, w: Float) = put(
        x, 0f, 0f, 0f,
        0f, y, 0f, 0f,
        0f, 0f, z, 0f,
        0f, 0f, 0f, w)

    fun put(a0: Float, a1: Float, a2: Float, a3: Float,
            b0: Float, b1: Float, b2: Float, b3: Float,
            c0: Float, c1: Float, c2: Float, c3: Float,
            d0: Float, d1: Float, d2: Float, d3: Float) {

        array[0] = a0
        array[1] = a1
        array[2] = a2
        array[3] = a3

        array[4] = b0
        array[5] = b1
        array[6] = b2
        array[7] = b3

        array[8] = c0
        array[9] = c1
        array[10] = c2
        array[11] = c3

        array[12] = d0
        array[13] = d1
        array[14] = d2
        array[15] = d3
    }

    // TODO others
    fun toMat3() = to(Mat3())

    infix fun to(res: Mat3): Mat3 {

        res[0, 0] = this[0, 0]
        res[0, 1] = this[0, 1]
        res[0, 2] = this[0, 2]

        res[1, 0] = this[1, 0]
        res[1, 1] = this[1, 1]
        res[1, 2] = this[1, 2]

        res[2, 0] = this[2, 0]
        res[2, 1] = this[2, 1]
        res[2, 2] = this[2, 2]

        return res
    }

    fun toFloatArray(): FloatArray = to(FloatArray(length), 0)
    infix fun to(floats: FloatArray): FloatArray = to(floats, 0)
    fun to(floats: FloatArray, index: Int): FloatArray {
        System.arraycopy(array, 0, floats, index, length)
        return floats
    }

    override fun to(buf: ByteBuffer, offset: Int): ByteBuffer {
        return buf
            .putFloat(offset + 0 * Float.BYTES, array[0])
            .putFloat(offset + 1 * Float.BYTES, array[1])
            .putFloat(offset + 2 * Float.BYTES, array[2])
            .putFloat(offset + 3 * Float.BYTES, array[3])
            .putFloat(offset + 4 * Float.BYTES, array[4])
            .putFloat(offset + 5 * Float.BYTES, array[5])
            .putFloat(offset + 6 * Float.BYTES, array[6])
            .putFloat(offset + 7 * Float.BYTES, array[7])
            .putFloat(offset + 8 * Float.BYTES, array[8])
            .putFloat(offset + 9 * Float.BYTES, array[9])
            .putFloat(offset + 10 * Float.BYTES, array[10])
            .putFloat(offset + 11 * Float.BYTES, array[11])
            .putFloat(offset + 12 * Float.BYTES, array[12])
            .putFloat(offset + 13 * Float.BYTES, array[13])
            .putFloat(offset + 14 * Float.BYTES, array[14])
            .putFloat(offset + 15 * Float.BYTES, array[15])
    }

    override fun to(buf: FloatBuffer, offset: Int): FloatBuffer {
        buf[offset + 0] = array[0]
        buf[offset + 1] = array[1]
        buf[offset + 2] = array[2]
        buf[offset + 3] = array[3]
        buf[offset + 4] = array[4]
        buf[offset + 5] = array[5]
        buf[offset + 6] = array[6]
        buf[offset + 7] = array[7]
        buf[offset + 8] = array[8]
        buf[offset + 9] = array[9]
        buf[offset + 10] = array[10]
        buf[offset + 11] = array[11]
        buf[offset + 12] = array[12]
        buf[offset + 13] = array[13]
        buf[offset + 14] = array[14]
        buf[offset + 15] = array[15]
        return buf
    }

    infix fun to(res: Quat) = glm.quat_cast(this, res)
    fun toQuat() = glm.quat_cast(this, Quat())

    fun to(ptr: Ptr<Float>, transpose: Boolean = false) {
        when {
            transpose -> {
                ptr[0] = get(0, 0); ptr[1] = get(1, 0); ptr[2] = get(2, 0); ptr[3] = get(3, 0)
                ptr[4] = get(0, 1); ptr[5] = get(1, 1); ptr[6] = get(2, 1); ptr[7] = get(3, 1)
                ptr[8] = get(0, 2); ptr[9] = get(1, 2); ptr[10] = get(2, 2); ptr[11] = get(3, 2)
                ptr[12] = get(0, 3); ptr[13] = get(1, 3); ptr[14] = get(2, 3); ptr[15] = get(3, 3)
            }

            else -> {
                ptr[0] = get(0, 0); ptr[1] = get(0, 1); ptr[2] = get(0, 2); ptr[3] = get(0, 3)
                ptr[4] = get(1, 0); ptr[5] = get(1, 1); ptr[6] = get(1, 2); ptr[7] = get(1, 3)
                ptr[8] = get(2, 0); ptr[9] = get(2, 1); ptr[10] = get(2, 2); ptr[11] = get(2, 3)
                ptr[12] = get(3, 0); ptr[13] = get(3, 1); ptr[14] = get(3, 2); ptr[15] = get(3, 3)
            }
        }
    }

    // -- put --

    fun put(mat2x2: Mat2x2t<Number>) {
        array[0] = mat2x2[0, 0].f
        array[1] = mat2x2[0, 1].f
        array[2] = 0f
        array[3] = 0f

        array[4] = mat2x2[1, 0].f
        array[5] = mat2x2[1, 1].f
        array[6] = 0f
        array[7] = 0f

        array[8] = 0f
        array[9] = 0f
        array[10] = 0f
        array[11] = 0f

        array[12] = 0f
        array[13] = 0f
        array[14] = 0f
        array[15] = 0f
    }

//    fun to(scalar: Number) {
//        value = mutableListOf(
//                Vec4(scalar.main.getF, 0),
//                Vec4(0, scalar.main.getF))
//    }
//
//    fun to(x0: Number, x1: Number, y0: Number, y1: Number) {
//        value = mutableListOf(
//                Vec4(x0.main.getF, y0.main.getF),
//                Vec4(x1.main.getF, y1.main.getF))
//    }
//
//    fun to(v0: Vec4t<*>, v1: Vec4t<*>) {
//        value = mutableListOf(
//                Vec4(v0),
//                Vec4(v1))
//    }


    // -- Accesses --

    override operator fun get(index: Int) = Vec4(index * 4, array)
    override operator fun get(column: Int, row: Int) = array[column * 4 + row]

    override operator fun set(column: Int, row: Int, value: Float) = array.set(column * 4 + row, value)

    override operator fun set(index: Int, value: Vec4t<out Number>) {
        array[index * 4] = value._x.f
        array[index * 4 + 1] = value._y.f
        array[index * 4 + 2] = value._z.f
        array[index * 4 + 3] = value._w.f
    }

    operator fun set(i: Int, v: Vec4) {
        v.to(array, i * 4)
    }

    // TODO other cases
    fun set(i: Int, v: Vec3, s: Float) {
        v.to(array, i * 4)
        array[i * 4 + 3] = s
    }


    // -- Unary arithmetic operators --

    operator fun unaryPlus() = this

    operator fun unaryMinus() = Mat4(
        -array[0], -array[1], -array[2], -array[3],
        -array[4], -array[5], -array[6], -array[7],
        -array[8], -array[9], -array[10], -array[11],
        -array[12], -array[13], -array[14], -array[15])


    // -- Increment main.and decrement operators --

    operator fun inc(res: Mat4 = Mat4()): Mat4 = plus(res, this, 1f)
    fun incAssign() = plus(this, this, 1f)

    operator fun dec(res: Mat4 = Mat4()): Mat4 = minus(res, this, 1f)
    fun decAssign() = minus(this, this, 1f)


    // -- Specific binary arithmetic operators --

    infix operator fun plus(b: Float) = plus(Mat4(), this, b)
    infix operator fun plus(b: Mat4) = plus(Mat4(), this, b)

    fun plus(b: Float, res: Mat4) = plus(res, this, b)
    fun plus(b: Mat4, res: Mat4) = plus(res, this, b)

    infix operator fun plusAssign(b: Float) {
        plus(this, this, b)
    }

    infix operator fun plusAssign(b: Mat4) {
        plus(this, this, b)
    }


    infix operator fun minus(b: Float) = minus(Mat4(), this, b)
    infix operator fun minus(b: Mat4) = minus(Mat4(), this, b)

    fun minus(b: Float, res: Mat4) = minus(res, this, b)
    fun minus(b: Mat4, res: Mat4) = minus(res, this, b)

    infix operator fun minusAssign(b: Float) {
        minus(this, this, b)
    }

    infix operator fun minusAssign(b: Mat4) {
        minus(this, this, b)
    }


    /**
     * @return = [b] * @this
     */
    infix operator fun times(b: Float) = times(Mat4(), this, b)

    infix operator fun times(b: Vec4) = times(Vec4(), this, b)
    infix operator fun times(b: Mat4) = times(Mat4(), this, b)

    /**
     * [res] = [b] * @this
     */
    fun times(b: Float, res: Mat4) = times(res, this, b)

    fun times(b: Vec4, res: Vec4) = times(res, this, b)
    fun times(b: Mat4, res: Mat4) = times(res, this, b)

    infix operator fun timesAssign(b: Float) {
        times(this, this, b)
    }

    infix operator fun timesAssign(b: Vec4) {
        times(b, this, b)
    }

    infix operator fun timesAssign(b: Mat4) {
        times(this, this, b)
    }


    infix operator fun div(b: Float) = div(Mat4(), this, b)
    infix operator fun div(b: Mat4) = div(Mat4(), this, b)

    fun div(b: Float, res: Mat4) = div(res, this, b)
    fun div(b: Mat4, res: Mat4) = div(res, this, b)

    infix operator fun divAssign(b: Float) {
        div(this, this, b)
    }

    infix operator fun divAssign(b: Mat4) {
        div(this, this, b)
    }


    // -- Matrix functions --

    val det get() = glm.determinant(this)

    fun inverse() = inverse(Mat4())
    infix fun inverse(res: Mat4) = inverse(res, this)

    fun inverseAssign() = inverse(this, this)

    @JvmOverloads
    fun transpose(res: Mat4 = Mat4()) = transpose(res, this)

    fun transposeAssign() = transpose(this, this)

    @JvmOverloads
    fun inverseTranspose(res: Mat4 = Mat4()) = glm.inverseTranspose(res, this)

    fun inverseTransposeAssign() = glm.inverseTranspose(this, this)


    fun cleanTranslationAssign() = glm.cleanTranslation(this, this)

    @JvmOverloads
    fun cleanTranslation(res: Mat4 = Mat4()) = glm.cleanTranslation(res, this)


    // TODO others
    infix fun scale(scale: Vec3) = scale(scale.x, scale.y, scale.z, Mat4())

    fun scale(scale: Vec3, res: Mat4) = scale(scale.x, scale.y, scale.z, res)

    infix fun scale(scale: Float) = scale(scale, scale, scale, Mat4())
    fun scale(scale: Float, res: Mat4) = scale(scale, scale, scale, res)

    @JvmOverloads
    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float, res: Mat4 = Mat4()) = glm.scale(this, scaleX, scaleY, scaleZ, res)

    infix fun scaleAssign(scale: Vec3) = scaleAssign(scale.x, scale.y, scale.z)
    infix fun scaleAssign(scale: Float) = scaleAssign(scale, scale, scale)
    fun scaleAssign(scaleX: Float, scaleY: Float, scaleZ: Float) = glm.scale(this, scaleX, scaleY, scaleZ, this)


    infix fun translate(translate: Vec3) = translate(translate.x, translate.y, translate.z, Mat4())
    fun translate(translate: Vec3, res: Mat4) = translate(translate.x, translate.y, translate.z, res)

    infix fun translate(translate: Float) = translate(translate, translate, translate, Mat4())
    fun translate(translate: Float, res: Mat4) = translate(translate, translate, translate, res)

    @JvmOverloads
    fun translate(translateX: Float, translateY: Float, translateZ: Float, res: Mat4 = Mat4()) =
        glm.translate(this, translateX, translateY, translateZ, res)

    infix fun translateAssign(translate: Vec3) = translateAssign(translate.x, translate.y, translate.z)
    infix fun translateAssign(translate: Float) = translateAssign(translate, translate, translate)
    fun translateAssign(translateX: Float, translateY: Float, translateZ: Float) = glm.translate(this, translateX, translateY, translateZ, this)


//    infix fun isEqual(b: Mat4) = this[0].isEqual(b[0]) && this[1].isEqual(b[1]) && this[2].isEqual(b[2]) && this[3].isEqual(b[3])

    @JvmOverloads
    fun rotate(angle: Float, vX: Float, vY: Float, vZ: Float, res: Mat4 = Mat4()) = glm.rotate(this, angle, vX, vY, vZ, res)

    @JvmOverloads
    fun rotate(angle: Float, v: Vec3, res: Mat4 = Mat4()) = glm.rotate(this, angle, v, res)

    fun rotateAssign(angle: Float, vX: Float, vY: Float, vZ: Float) = glm.rotate(this, angle, vX, vY, vZ, this)
    fun rotateAssign(angle: Float, v: Vec3) = glm.rotate(this, angle, v, this)

    @JvmOverloads
    fun rotateX(angle: Float, res: Mat4 = Mat4()) = glm.rotateX(this, angle, res)

    @JvmOverloads
    fun rotateY(angle: Float, res: Mat4 = Mat4()) = glm.rotateY(this, angle, res)

    @JvmOverloads
    fun rotateZ(angle: Float, res: Mat4 = Mat4()) = glm.rotateZ(this, angle, res)

    @JvmOverloads
    fun rotateXYZ(angle: Vec3, res: Mat4 = Mat4()) = glm.rotateXYZ(this, angle.x, angle.y, angle.z, res)

    @JvmOverloads
    fun rotateXYZ(angleX: Float, angleY: Float, angleZ: Float, res: Mat4 = Mat4()) = glm.rotateXYZ(this, angleX, angleY, angleZ, res)

    fun rotateXassign(angle: Float) = glm.rotateX(this, angle, this)
    fun rotateYassign(angle: Float) = glm.rotateY(this, angle, this)
    fun rotateZassign(angle: Float) = glm.rotateZ(this, angle, this)
    fun rotateXYZassign(angle: Vec3) = glm.rotateXYZ(this, angle.x, angle.y, angle.z, this)
    fun rotateXYZassign(angleX: Float, angleY: Float, angleZ: Float) = glm.rotateXYZ(this, angleX, angleY, angleZ, this)


    override var a0: Float
        get() = array[0]
        set(v) = array.set(0, v)
    override var a1: Float
        get() = array[1]
        set(v) = array.set(1, v)
    override var a2: Float
        get() = array[2]
        set(v) = array.set(2, v)
    override var a3: Float
        get() = array[3]
        set(v) = array.set(3, v)

    override var b0: Float
        get() = array[4]
        set(v) = array.set(4, v)
    override var b1: Float
        get() = array[5]
        set(v) = array.set(5, v)
    override var b2: Float
        get() = array[6]
        set(v) = array.set(6, v)
    override var b3: Float
        get() = array[7]
        set(v) = array.set(7, v)

    override var c0: Float
        get() = array[8]
        set(v) = array.set(8, v)
    override var c1: Float
        get() = array[9]
        set(v) = array.set(9, v)
    override var c2: Float
        get() = array[10]
        set(v) = array.set(10, v)
    override var c3: Float
        get() = array[11]
        set(v) = array.set(11, v)

    override var d0: Float
        get() = array[12]
        set(v) = array.set(12, v)
    override var d1: Float
        get() = array[13]
        set(v) = array.set(13, v)
    override var d2: Float
        get() = array[14]
        set(v) = array.set(14, v)
    override var d3: Float
        get() = array[15]
        set(v) = array.set(15, v)


    override val isIdentity
        get() = this[0, 0] == 1f && this[1, 0] == 0f && this[2, 0] == 0f && this[3, 0] == 0f &&
                this[0, 1] == 0f && this[1, 1] == 1f && this[2, 1] == 0f && this[3, 1] == 0f &&
                this[0, 2] == 0f && this[1, 2] == 0f && this[2, 2] == 1f && this[3, 2] == 0f &&
                this[0, 3] == 0f && this[1, 3] == 0f && this[2, 3] == 0f && this[3, 3] == 1f

    companion object : mat4x4_operators {
        const val length = Mat4x4t.length

        @JvmField
        val size = length * Float.BYTES

        @JvmStatic
        fun fromPointer(ptr: Ptr<Float>, transpose: Boolean = false): Mat4 = when {
            transpose -> Mat4(ptr[0], ptr[4], ptr[8], ptr[12],
                              ptr[1], ptr[5], ptr[9], ptr[13],
                              ptr[2], ptr[6], ptr[10], ptr[14],
                              ptr[3], ptr[7], ptr[11], ptr[15])

            else -> Mat4(ptr)
        }

        val identity: Mat4
            get() = Mat4(1f)
    }

    override fun size() = Companion.size

    override fun elementCount() = length

    override fun equals(other: Any?) = other is Mat4 && array.contentEquals(other.array)
    override fun hashCode() = 31 * (31 * (31 * this[0].hashCode() + this[1].hashCode()) + this[2].hashCode()) + this[3].hashCode()

    fun equal(b: Mat4, epsilon: Float, res: Vec4bool = Vec4bool()): Vec4bool = glm.equal(this, b, epsilon, res)
    fun equal(b: Mat4, epsilon: Vec4, res: Vec4bool = Vec4bool()): Vec4bool = glm.equal(this, b, epsilon, res)
    fun notEqual(b: Mat4, epsilon: Float, res: Vec4bool = Vec4bool()): Vec4bool = glm.notEqual(this, b, epsilon, res)
    fun notEqual(b: Mat4, epsilon: Vec4, res: Vec4bool = Vec4bool()): Vec4bool = glm.notEqual(this, b, epsilon, res)
    fun allEqual(b: Mat4, epsilon: Float = glm.εf): Boolean = glm.allEqual(this, b, epsilon)
    fun anyNotEqual(b: Mat4, epsilon: Float = glm.εf): Boolean = glm.anyNotEqual(this, b, epsilon)
}
