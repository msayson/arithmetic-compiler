package visitor;

import ast.*;
import util.IndentingWriter;

import java.io.PrintWriter;


/**
 * This prints the structure of an AST, showing its hierarchical relationships.
 * <p>
 * This version is also cleaned up to actually produce *properly* indented
 * output.
 *
 * @author norm
 */
public class StructurePrintVisitor implements Visitor<Void> {

    /**
     * Where to send out.print output.
     */
    private IndentingWriter out;

    public StructurePrintVisitor(PrintWriter out) {
        this.out = new IndentingWriter(out);
    }

    ///////////// Visitor methods /////////////////////////////////////////

    @Override
    public Void visit(Program n) {
        out.println("Program");
        out.indent();
        for (int i = 0; i < n.statements.size(); i++) {
            n.statements.get(i).accept(this);
        }
        n.printStatement.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(Print n) {
        out.println("Print");
        out.indent();
        n.exp.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(Assign n) {
        out.println("Assign");
        out.indent();
        n.name.accept(this);
        n.value.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(Plus n) {
        out.println("Plus");
        out.indent();
        n.e1.accept(this);
        n.e2.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(Minus n) {
        out.println("Minus");
        out.indent();
        n.e1.accept(this);
        n.e2.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(Times n) {
        out.println("Times");
        out.indent();
        n.e1.accept(this);
        n.e2.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(DividedBy n) {
        out.println("DividedBy");
        out.indent();
        n.e1.accept(this);
        n.e2.accept(this);
        out.outdent();
        return null;
    }

    @Override
    public Void visit(IntegerLiteral n) {
        out.println("IntegerLiteral " + n.value);
        return null;
    }

    @Override
    public Void visit(IdentifierExp n) {
        out.println("IdentifierExp " + n.name);
        return null;
    }
}
