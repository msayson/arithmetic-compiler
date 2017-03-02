package translate.implementation;

import ast.Program;
import ir.frame.Frame;
import translate.Fragments;

public class TranslateImplementation {

    private Frame frameFactory;
    private Program program;

    public TranslateImplementation(Frame frameFactory, Program program) {
        this.frameFactory = frameFactory;
        this.program = program;
    }

    public Fragments translate() {
        TranslateVisitor vis = new TranslateVisitor(frameFactory);
        program.accept(vis);
        return vis.getResult();
    }

}
