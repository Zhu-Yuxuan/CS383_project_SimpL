package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;

public class Add extends ArithExpr {

    public Add(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " + " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        return new IntValue(((IntValue) l.eval(s)).n + ((IntValue) r.eval(s)).n);
    }

    @Override
    public Add replace (Symbol x, Expr e) {
        return new Add(l.replace(x, e), r.replace(x, e));
    }

    // @Override
}
