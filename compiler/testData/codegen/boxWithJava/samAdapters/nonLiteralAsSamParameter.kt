import java.util.*

fun box(): String {
    var res = "FAIL"
    val runnableFun = { res = "OK" }
    val comparatorFun = { (a: Int, b: Int) -> b - a }

    val list = ArrayList(Arrays.asList(3, 2, 4, 8, 1, 5))
    val expected = ArrayList(Arrays.asList(8, 5, 4, 3, 2, 1))
    JavaClass.sortIntList(list, comparatorFun, runnableFun)
    return if (list == expected) res else list.toString()
}
