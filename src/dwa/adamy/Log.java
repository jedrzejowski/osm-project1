package dwa.adamy;

public class Log {
    public static final boolean isDEBUG = true;

    public static void m(Object x) {
        if (isDEBUG)
            System.out.println(x);
    }
}
