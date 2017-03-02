package ast;

import visitor.Visitor;

public class DividedBy extends Expression {

    public final Expression e1;
    public final Expression e2;

    public DividedBy(Expression e1, Expression e2) {
        super();
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", e1.toString(), e2.toString());
    }
}
