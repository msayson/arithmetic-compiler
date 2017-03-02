package test.translate;

import ir.frame.Frame;
import ir.frame.x86_64.X86_64Frame;
import ir.interp.Interp;
import ir.interp.InterpMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import translate.Fragment;
import translate.Fragments;
import translate.Translator;
import util.SampleCode;
import util.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

    //TODO
//    @Test
//    public void singleAssignmentAndPrintArith() throws Exception {
//        test("61\n",
//                "sum = 10 + 20;\n" +
//                        "print 1 + sum * 2;"
//        );
//    }

    //TODO: Support multiple assignments
//    @Test
//    public void multipleAssignments() throws Exception {
//        test("315\n",
//                "x = 5 * 3;\n" +
//                        "y = 100 * 3;\n" +
//                        "z = x + y;\n" +
//                        "print z;");
//    }

    //////////////// Sample code //////////////////////////////////

    @Test
    public void testSampleCode() throws Exception {
        File[] files = SampleCode.sampleFiles("java");
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (!optionalSample(f))
                test(f);
        }
    }

    protected Fragments test(File program) throws Exception {
        System.out.println("Translating: " + program);
        System.out.println(Utils.getContents(program));
        String expected = Utils.getExpected(program);
        String otherexpected = Utils.getOtherExpected(program);
        if (otherexpected != null) {
            return test(expected, otherexpected, program);
        } else {
            return test(expected, program);
        }
    }

    protected Fragments translate(File program) throws Exception {
        Fragments translated = Translator.translate(architecture, program);
        if (dumpIR()) {
            System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
            System.out.println(translated);
            System.out.println();
        }
        if (dumpIRToFile()) {
            String iroutname = Utils.changeSuffix(program, "ir");
            File irout = new File(iroutname);
            BufferedWriter irouts = new BufferedWriter(new FileWriter(irout));

            for (Fragment f : translated) {
                irouts.write(f.toString());
                irouts.write("\n");
            }
            irouts.close();
        }
        return translated;
    }

    protected String run(Fragments translated) {
        String result = null;
        if (getSimulationMode() != null) {
            System.out.println("Simulating IR code:");
            Interp interp = new Interp(translated, getSimulationMode());

            try {
                result = interp.run();
            } catch (Error e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                result = e.getMessage();
            }
        }
        return result;
    }

    protected Fragments test(String expected, File program)
            throws Exception {
        Fragments translated = translate(program);
        if (getSimulationMode() != null) {
            String result = run(translated);
            System.out.println(result);
            Assertions.assertEquals(expected, result);
        }
        System.out.println("=================================");
        return translated;
    }

    protected Fragments test(String expected, String otherexpected, File program)
            throws Exception {
        Fragments translated = translate(program);
        if (getSimulationMode() != null) {
            String result = run(translated);
            System.out.println(result);
            Assertions.assertTrue(expected.equals(result) ||
                    otherexpected.equals(result));
        }
        System.out.println("=================================");
        return translated;
    }

    private boolean optionalSample(File f) {
        return f.toString().endsWith("Visitor.java");
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
