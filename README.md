# arithmetic-compiler
Compiler for a simple arithmetic language

#### ArithLang Grammar in BNF Form

```
Program ::= Assign* Print <EOF>

Assign ::= IDENTIFIER "=" Expression ";"

Print ::= PRINT Expression ";"

Expression ::= MultExpression ( ("+"|"-") MultExpression ) *

MultExpression ::= PrimaryExpression ( ("*"|"/") PrimaryExpression ) *

PrimaryExpression ::= NUMBER_LITERAL | IDENTIFIER
```

#### Example Programs

The end of every program is a print statement.
```
print 5 * 5 * 4; // Outputs "200"
```

Following arithmetic conventions, multiplication and division are evaluated before addition and subtraction.
```
print 1 + 2 * 3 + 100 * 4; // Outputs "407"
```

Single-line and multi-line comments are supported and follow C/Java conventions.
```
/*
This is a multi-line comment
*/
print 1 + 2; // This is a single-line comment
```

Variables can be assigned to break calculations into manageable parts.
```
sum = 10 + 20;
print 1 + sum * 2; // Outputs "61"
```

This source code extends the starter MiniJava project from UBC's undergraduate compiler course, "CPSC 411 Introduction to Compiler Construction".
[http://www.ugrad.cs.ubc.ca/~cs411/2016w2/](http://www.ugrad.cs.ubc.ca/~cs411/2016w2/)

The original MiniJava starter code accompanies "Modern Compiler Implementation in Java, Second Edition (Andrew W. Appel)".
[http://www.cambridge.org/resources/052182060X/](http://www.cambridge.org/resources/052182060X/)
