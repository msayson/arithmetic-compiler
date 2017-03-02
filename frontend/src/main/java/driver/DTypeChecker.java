package driver;

import parser.Parser;

import java.io.File;

public class DTypeChecker {

    /**
     * Given a source file, compile it and write the parse tree to System.out.
     *
     * @param program program to compile.
     */
    public static void compile(File program) throws Exception {
        Parser.parse(program);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String program = args[0];
        try {
            compile(new File(program));
        } catch (Exception e) {
            System.out.println("Compilation problem");
            e.printStackTrace();
        }
    }

}
