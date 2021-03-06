package ast;

import visitor.Visitor;

public class IntegerLiteral extends Expression {

    public final int value;

    public IntegerLiteral(int value) {
        super();
        this.value = value;
    }

    public IntegerLiteral(String image) {
        this(Integer.parseInt(image));
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
