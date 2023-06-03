package glm_.vec1

import glm_.glm


/**
 * Created bY GBarbieri on 05.10.2016.
 */

// TODO fill vec1bool methods as others
data class Vec1bool(@JvmField var x: Boolean = false) {

    // -- Explicit basic, conversion other main.and conversion vector constructors --

    constructor(ba: BooleanArray) : this(ba[0])

    constructor(ba: Array<Boolean>) : this(ba[0])

    constructor(init: (Int) -> Boolean) : this(init(0))

    // -- Component accesses --

    inline operator fun get(i: Int): Boolean {
        if (i == 0) return x
        throw IndexOutOfBoundsException()
    }

    inline operator fun set(i: Int, b: Boolean) {
        if (i == 0) x = b
        else throw IndexOutOfBoundsException()
    }


    inline fun put(b: Boolean): Vec1bool {
        x = b
        return this
    }

    fun put(ba: BooleanArray): Vec1bool {
        x = ba[0]
        return this
    }

    fun put(ba: Array<Boolean>): Vec1bool {
        x = ba[0]
        return this
    }

    operator fun invoke(init: (Int) -> Boolean): Vec1bool {
        x = init(0)
        return this
    }

//    fun toBooleanArray(): BooleanArray = to(BooleanArray(length), 0)
//    infix fun to(floats: BooleanArray): BooleanArray = to(floats, 0)
//    fun to(floats: BooleanArray, index: Int): FloatArray {
//        floats[index] = x
//        return floats
//    }

    // -- Unary arithmetic vecOperators --

    operator fun not(): Vec1bool = Vec1bool(!x)

    fun notAssign(): Vec1bool {
        x = !x
        return this
    }

    infix fun not(res: Vec1bool): Vec1bool {
        res.x = !x
        return this
    }

    // -- relational --

    val all: Boolean
        get() = glm.all(this)

    // TODO
//    infix fun equal(b: Vec2bool) = glm.equal(this, b)
//    infix fun equal_(b: Vec2bool) = glm.equal(this, this, b)
//    fun equal(b: Vec2bool, res: Vec2bool) = glm.equal(res, this, b)
//
//    infix fun notEqual(b: Vec2bool) = glm.notEqual(this, b)
//    fun notEqual(b: Vec2bool, res: Vec2bool) = glm.notEqual(res, this, b)
//
//    fun any() = glm.any(this)
//
//    fun all() = glm.all(this)

    override fun toString(): String = "($x)"
}