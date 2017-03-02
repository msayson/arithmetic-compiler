package visitor;

import ast.*;

/**
 * A modernized version of the Visitor interface, adapted from the textbook's
 * version.
 * <p>
 * Changes: this visitor allows you to return something as a result.
 * The "something" can be of any particular type, so the Visitor
 * uses Java generics to express this.
 *
 * @author kdvolder
 */
public interface Visitor<R> {

    //Declarations
    R visit(Program n);

    //Statements
    R visit(Print n);

    R visit(Assign n);

    //Expressions
    R visit(Plus n);

    R visit(Minus n);

    R visit(Times n);

    R visit(IntegerLiteral n);

    R visit(IdentifierExp n);
}
