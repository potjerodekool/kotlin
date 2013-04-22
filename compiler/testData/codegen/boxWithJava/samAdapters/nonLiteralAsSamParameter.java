import java.util.*;

class JavaClass {
    public static void sortIntList(List<Integer> list, Comparator<Integer> comparator, Runnable runnable) {
        Collections.sort(list, comparator);
        runnable.run();
    }
}
