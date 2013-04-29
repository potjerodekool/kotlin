package foo

import js.*

native
fun paramCount(vararg a : Int) : Int = js.noImpl

// test spread operator
fun count(vararg a : Int) = paramCount(*a)

native
class A(val size: Int) {
  fun test(dummy: Int, vararg args: Int): Boolean = js.noImpl
}

native
object b {
  fun test(size: Int, vararg args: Int): Boolean = js.noImpl
}

fun spreadInMethodCall(size: Int, vararg args: Int) = A(size).test(0, *args)

fun spreadInObjectMethodCall(size: Int, vararg args: Int) = b.test(size, *args)

native
fun testNativeVarargWithFunLit(vararg args: Int, f: (a: IntArray) -> Boolean): Boolean = js.noImpl

fun box() =
  paramCount(1, 2 ,3) == 3 &&
  paramCount() == 0 &&
  count() == 0 &&
  count(1, 1, 1, 1) == 4 &&
  A(5).test(0, 1, 2, 3, 4, 5) &&
  spreadInMethodCall(2, 1, 2) &&
  b.test(5, 1, 2, 3, 4, 5) &&
  spreadInObjectMethodCall(2, 1, 2) &&
  testNativeVarargWithFunLit(1, 2, 3) { args -> args.size == 3 }