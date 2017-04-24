package test.translate;

import ir.frame.Frame;
import ir.frame.x86_64.X86_64Frame;
import ir.interp.Interp;
import ir.interp.InterpMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import translate.Fragments;
import translate.Translator;

/**
 * Test the arithlang translation phase that takes a program and turns
 * the bodies of all the methods in the program into IRtrees.
 * <p>
 * This test suite uses the IR interpreter to simulate the execution of the
 * resulting IR. This gives us some confidence that our translation works correctly :-)
 *
 * @author kdvolder
 */
public class TestTranslate {

    public static final Frame architecture = X86_64Frame.factory;

    /**
     * To make it easy to run all of these tests with the either
     * linearized ir code, basic blocks or trace scheduled code
     * We determine the simulation mode via this method.
     * <p>
     * Simply creating a subclass and overriding this method will create
     * a test suite that runs all the same tests in a different simulation
     * mode.
     *
     * @return
     */
    protected InterpMode getSimulationMode() {
        return InterpMode.LINEARIZED_IR;
    }

    /**
     * Print out all the generated IR?
     * <p>
     * If false, only the result of simulating the IR execution
     * will be printed.
     */
    protected boolean dumpIR() {
        return true;
    }

    /**
     * Print out all the generated IR to a file
     */
    protected boolean dumpIRToFile() {
        return true;
    }

    @Test
    public void number() throws Exception {
        test("10\n",
                "print 10;"
        );
    }

    @Test
    public void add() throws Exception {
        test("30\n",
                "print 10 + 20;"
        );
    }

    @Test
    public void multiply() throws Exception {
        test("100\n",
                "print 5 * 5 * 4;"
        );
    }

    @Test
    public void divide() throws Exception {
        test("5\n",
                "print 20 / 4;"
        );
    }

    @Test
    public void addTerms() throws Exception {
        test("407\n",
                "print 1 + 2 * 3 + 100 * 4;"
        );
    }

    @Test
    public void comments() throws Exception {
        test("30\n",
                "/*\nPrints the sum of 10 and 20.\n*/" +
                        "print 10 + 20; // Outputs \"30\"."
        );
    }

    @Test
    public void singleAssignment() throws Exception {
        test("30\n",
                "sum = 10 + 20;\n" +
                        "print sum;"
        );
    }

    @Test
    public void singleAssignmentAndPrintArith() throws Exception {
        test("61\n",
                "sum = 10 + 20;\n" +
                        "print 1 + sum * 2;"
        );
    }

    @Test
    public void multipleAssignments() throws Exception {
        test("315\n",
                "x = 5 * 3;\n" +
                        "y = 100 * 3;\n" +
                        "z = x + y;\n" +
                        "print z;");
    }

    protected Fragments test(String expected, String program) throws Exception {
        System.out.println("Translating program: ");
        System.out.println(program);
        Fragments translated = Translator.translate(architecture, program);
        if (dumpIR()) {
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            System.out.println(translated);
            System.out.println();
        }
        if (getSimulationMode() != null) {
            System.out.println("Simulating IR code:");
            Interp interp = new Interp(translated, getSimulationMode());
            String result = interp.run();
            System.out.print(result);
            Assertions.assertEquals(expected, result);
        }
        System.out.println("=================================");
        return translated;
    }

}
