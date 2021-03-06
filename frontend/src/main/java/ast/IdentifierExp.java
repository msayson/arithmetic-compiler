package ast;

import visitor.Visitor;

public class IdentifierExp extends Expression {

    public final String name;

    public IdentifierExp(String name) {
        super();
        this.name = name;
    }

    @Override
    public <R> R accept(Visitor<R> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
