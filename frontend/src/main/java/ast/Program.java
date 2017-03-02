package ast;

import visitor.Visitor;

import java.util.List;


public class Program extends AST {

    public final List<Statement> statements;
    public final Print printStatement;

    public Program(List<Statement> statements, Print printStatement) {
        this.statements = statements;
        this.printStatement = printStatement;
    }

    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

}
