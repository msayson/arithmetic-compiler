package test.codegen;

import analysis.RegAlloc;
import codegen.Assembly;
import codegen.CodeGenerator;
import ir.interp.InterpMode;
import org.junit.jupiter.api.AfterAll;
import test.translate.TestTranslate;
import test.util.BackendTestUtils;
import translate.Fragments;
import x86_64sim.Sim;
import x86_64sim.State;


/**
 * So what we will do is simply to compile again all the TestTranslate programs into
 * IR code, then convert these into assem instructions, and then simulate them.
 *
 * @author kdvolder and norm
 */
public class TestSimulateFinal extends TestTranslate {

    // Note that this class doesn't actually contain any @Test methods.
    // It inherits them from TestTranslate.
    // Each test will call one of the two test methods below, which we override
    // here.
    // This way we can easily reuse all the tests, but make them do something extra
    // (i.e. generate and print the assembly).

    @Override
    protected InterpMode getSimulationMode() {
        return null; // means don't simulate IR code.
    }

    @Override
    protected boolean dumpIR() {
        return false;
    }

    @Override
    protected Fragments test(String expected, String program) throws Exception {
        Fragments translated = super.test(expected, program);
        //		System.out.println("Generating assembly for "+program);
        String result = test(translated);
        BackendTestUtils.check(expected, result);
        return null; // doesn't matter right now.
    }

    protected String test(Fragments ir_fragments) {
        CodeGenerator cogen = new CodeGenerator();
        Assembly assembly = cogen.apply(ir_fragments);
        RegAlloc.doit(assembly);
        String program = assembly.toString();
        State state = null;
        String result;
        try {
            state = Sim.ulate(program, false);
            result = state.result;
        } catch (Error e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        System.out.println("Program:");
        System.out.print(program);
        System.out.println("Static: " + state.p.countInstructions() + " instructions generated");
        System.out.println("Dynamic: " + state.instructionsExecuted + " instructions executed");
        System.out.println("Output:");
        System.out.print(result);
        return result;
    }

    /**
     * This prints out a representation of the X86Muncher's pattern munching rules.
     * For debugging purposes.
     */
    @AfterAll
    public static void dumpRules() {
        // X86Muncher.dumpRules();
    }

}
