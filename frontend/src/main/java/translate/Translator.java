package translate;

import ast.Program;
import ir.frame.Frame;
import ir.temp.Label;
import parser.Parser;
import translate.implementation.TranslateImplementation;

import java.io.File;


/**
 * This is the interface for the translation to IR phase. To avoid making this
 * specific to how previous phases get called exactly, we expect this interface to
 * accept Strings or input files (rather than something more structured like an AST)
 * and call the preceding phases in an appropriate manner
 * to get the files parsed and typechecked before attempting IR generation.
 * <p>
 * As always, you are free to change this code in any way you like as long as
 * it doesn't break the provided unit tests.
 *
 * @author kdvolder
 */
public class Translator {

    public static Fragments translate(Frame frameFactory, String programStr) throws Exception {
        Program program = Parser.parse(programStr);
        return new TranslateImplementation(frameFactory, program).translate();
    }

    public static Fragments translate(Frame frameFactory, File programFile) throws Exception {
        Program program = Parser.parse(programFile);
        return new TranslateImplementation(frameFactory, program).translate();
    }

    /**
     * The translator should use this label for the Expression main computation.
     */
    public static final Label L_MAIN = Label.get("cs411main");

    /*
     * The labels below are supposed to point to "special" procedures
     * that are part of the expression runtime library.
     * <p>
     * The translator assumes that they are magically defined somehow.
     */
    public static final Label L_PRINT = Label.get("cs411println");
    public static final Label L_ERROR = Label.get("cs411error");
}
