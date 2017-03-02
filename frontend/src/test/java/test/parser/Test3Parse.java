package test.parser;

import ast.Program;
import org.junit.jupiter.api.Test;
import parser.Parser;
import util.SampleCode;

import java.io.File;


/**
 * The tests in this class correspond more or less to the work in Chapter 3.
 * <p>
 * These tests try to call your parser to parse Expression programs, but they do
 * not check the AST produced by the parser. As such you should be able to get
 * these tests to pass without inserting any semantic actions into your
 * parser specification file.
 * <p>
 * The tests in this file are written in an order that can be followed if
 * you want to develop your parser incrementally, staring with the tests at the
 * top of the file, and working your way down.
 *
 * @author kdvolder
 */
public class Test3Parse {

    /**
     * All testing is supposed to go through calling this method to one of the
     * accept methods, to see whether some input is accepted by the parser. The subclass
     * Test4Parse refines these tests by overriding this accept method to also verify the
     * parse tree structure.
     */
    protected void accept(String input) throws Exception {
        Program p = Parser.parse(input);
        System.out.println(p.dump());
    }

    protected void accept(File file) throws Exception {
        System.out.println("parsing file: " + file);
        Program p = Parser.parse(file);
        System.out.println(p.dump());
    }

    //////////////////////////////////////////////////////////////////////////
    // First let's ensure we can parse the "simplest possible" program:

    @Test
    public void printNumber() throws Exception {
        accept("print 10;");
    }

    @Test
    public void printSum() throws Exception {
        accept("print 2 + 5;");
    }

    @Test
    public void printMult() throws Exception {
        accept("print 2 * 5;");
    }

    @Test
    public void simpleAssnSequence() throws Exception {
        accept("a = 15;\n" +
                "b = 20;\n" +
                "sum = a + b;\n" +
                "sumTimesTwo = sum * 2;\n" +
                "print sumTimesTwo;"
        );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // Finally, check whether the parser accepts all the sample code.
    @Test
    public void testParseSampleCode() throws Exception {
        File[] files = SampleCode.sampleFiles("java");
        for (File file : files) {
            accept(file);
        }
    }

}
