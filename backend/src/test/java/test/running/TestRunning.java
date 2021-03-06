package test.running;

import driver.Compiler;
import driver.Runner;
import test.translate.TestTranslate;
import test.util.BackendTestUtils;
import translate.Fragments;

import java.io.File;

/**
 * This is the final test...
 * <p>
 * We reuse again all the tests from TestTranslate, but this time
 * we run them not by translating into IR, but by compiling to
 * an assembly file, calling the gnu assembler / linker and
 * running the resulting executable file.
 *
 * @author kdvolder
 */
public class TestRunning extends TestTranslate {

    Compiler compiler = new Compiler();
    Runner runner = new Runner();

    @Override
    protected Fragments test(String expected, String program) throws Exception {
        File assemFile = new File("temp.s");
        if (assemFile.exists())
            assemFile.delete();

        System.out.println("compiling: " + program);
        compiler.compile(program, assemFile);
        System.out.println(">>>>>  " + assemFile);

        String output = runner.run(assemFile);
        BackendTestUtils.check(expected, output, false);
        return null;
    }

}
