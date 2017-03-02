package test.util;

public class BackendTestUtils {
    /**
     * Assert that program output has expected value
     * */
    public static void check(String expected, String result) {
        check(expected, result, true);
    }

    public static void check(String expected, String result, boolean verbose) {
        boolean hasSameValue = expected.equals(result);
        if (verbose) {
            if (hasSameValue) {
                System.out.println("Output matched expected");
            } else {
                System.out.println("Output doesn't match expected.  Expected:");
                System.out.print(expected);
                System.out.println("Output:");
                System.out.print(result);
            }
        }
        assert (hasSameValue) : "Expected: " + expected + ", received: " + result;
    }
}
