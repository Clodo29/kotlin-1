/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:kotlin.jvm.JvmMultifileClass
@file:kotlin.jvm.JvmName("CharsKt")

package kotlin.text


/**
 * Creates a Char with the specified [code], or throws an exception if the [code] is out of `Char.MIN_VALUE.code..Char.MAX_VALUE.code`.
 *
 * @sample samples.text.Chars.charFromCode
 */
@ExperimentalStdlibApi
@SinceKotlin("1.4")
@kotlin.internal.LowPriorityInOverloadResolution // to not clash with Char constructor in js-ir
public fun Char(code: Int): Char {
    if (code < Char.MIN_VALUE.code || code > Char.MAX_VALUE.code) {
        throw IllegalArgumentException("Invalid Char code: $code")
    }
    return code.toChar()
}

/**
 * Returns the code of this Char.
 *
 * Code of a Char is the value it was constructed with, and the UTF-16 code unit corresponding to this Char.
 *
 * @sample samples.text.Chars.code
 */
@ExperimentalStdlibApi
@SinceKotlin("1.4")
@kotlin.internal.InlineOnly
public inline val Char.code: Int get() = this.toInt()

/**
 * Returns the numeric value of the digit that this Char represents in the specified [radix].
 * Throws an exception if the [radix] is not in the range `2..36` or if this Char is not a valid digit in the specified [radix].
 *
 * A Char is considered to represent a digit in the specified [radix] if at least one of the following is true:
 *  - The Char is one of the ASCII decimal digits '0' through '9' and its [code] is less than `radix + '0'.code`. In this case, `this.code - '0'.code` is returned.
 *  - The Char is one of the uppercase Latin letters 'A' through 'Z' and its [code] is less than `radix + 'A'.code - 10`. In this case, `this.code - 'A'.code + 10` is returned.
 *  - The Char is one of the lowercase Latin letters 'a' through 'z' and its [code] is less than `radix + 'a'.code - 10`. In this case, `this.code - 'a'.code + 10` is returned.
 *
 * @sample samples.text.Chars.digitToInt
 */
@ExperimentalStdlibApi
@SinceKotlin("1.4")
public fun Char.digitToInt(radix: Int = 10): Int {
    return digitToIntOrNull(radix) ?: throw IllegalArgumentException("Char $this is not a digit in the given radix=$radix")
}

/**
 * Returns the numeric value of the digit that this Char represents in the specified [radix], or `null` if this Char is not a valid digit in the specified [radix].
 * Throws an exception if the [radix] is not in the range `2..36`.
 *
 * A Char is considered to represent a digit in the specified [radix] if at least one of the following is true:
 *  - The Char is one of the ASCII decimal digits '0' through '9' and its [code] is less than `radix + '0'.code`. In this case, `this.code - '0'.code` is returned.
 *  - The Char is one of the uppercase Latin letters 'A' through 'Z' and its [code] is less than `radix + 'A'.code - 10`. In this case, `this.code - 'A'.code + 10` is returned.
 *  - The Char is one of the lowercase Latin letters 'a' through 'z' and its [code] is less than `radix + 'a'.code - 10`. In this case, `this.code - 'a'.code + 10` is returned.
 *
 * @sample samples.text.Chars.digitToIntOrNull
 */
@ExperimentalStdlibApi
@SinceKotlin("1.4")
public fun Char.digitToIntOrNull(radix: Int = 10): Int? {
    if (radix !in 2..36) {
        throw IllegalArgumentException("Invalid radix: $radix. Valid radix values are in range 2..36")
    }
    if (this in '0'..'9') {
        val digit = this - '0'
        return if (digit < radix) digit else null
    }
    val a = if (this <= 'Z') 'A' else 'a'
    val digit = 10 + (this - a)
    return if (digit in 10 until radix) digit else null
}

/**
 * Returns the Char that represents this numeric digit value in the specified [radix].
 * Throws an exception if the [radix] is not in the range `2..36` or if this value is not less than the specified [radix].
 *
 * If this value is less than `10`, the decimal digit Char with code `'0'.code + this` is returned.
 * Otherwise, the uppercase Latin letter with code `'A'.code + this - 10` is returned.
 *
 * @sample samples.text.Chars.digitToChar
 */
@ExperimentalStdlibApi
@SinceKotlin("1.4")
public fun Int.digitToChar(radix: Int = 10): Char {
    if (radix !in 2..36) {
        throw IllegalArgumentException("Invalid radix: $radix. Valid radix values are in range 2..36")
    }
    if (this < 0 || this >= radix) {
        throw IllegalArgumentException("Digit $this does not represent a valid digit in radix $radix")
    }
    return if (this < 10) {
        '0' + this
    } else {
        'A' + this - 10
    }
}

/**
 * Concatenates this Char and a String.
 *
 * @sample samples.text.Chars.plus
 */
@kotlin.internal.InlineOnly
public inline operator fun Char.plus(other: String): String = this.toString() + other

/**
 * Returns `true` if this character is equal to the [other] character, optionally ignoring character case.
 *
 * @param ignoreCase `true` to ignore character case when comparing characters. By default `false`.
 *
 * Two characters are considered the same ignoring case if at least one of the following is `true`:
 *   - The two characters are the same (as compared by the == operator)
 *   - Applying the method [toUpperCase] to each character produces the same result
 *   - Applying the method [toLowerCase] to each character produces the same result
 *
 * @sample samples.text.Chars.equals
 */
public fun Char.equals(other: Char, ignoreCase: Boolean = false): Boolean {
    if (this == other) return true
    if (!ignoreCase) return false

    if (this.toUpperCase() == other.toUpperCase()) return true
    if (this.toLowerCase() == other.toLowerCase()) return true
    return false
}

/**
 * Returns `true` if this character is a Unicode surrogate code unit.
 */
public fun Char.isSurrogate(): Boolean = this in Char.MIN_SURROGATE..Char.MAX_SURROGATE
