package dwa.adamy;

public class Log {
    public static final boolean isDEBUG = true;

    /**
     * Podaje na standardowe wyjście parametr x
     * @param x
     */
    public static void m(Object x) {
        if (isDEBUG)
            System.out.println(x);
    }
}
