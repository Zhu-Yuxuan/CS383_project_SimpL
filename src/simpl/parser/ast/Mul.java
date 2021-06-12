package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;

public class Mul extends ArithExpr {

    public Mul(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " * " + r + ")";
    }

    @Override
    public Mul replace (Symbol x, Expr e) {
        return new Mul(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2    v = v1*v2
         * -------------------------------------------------------------
         * E,M,p;e1*e2 => M'',p'';v1*v2
         */
        IntValue v1 = (IntValue) l.eval(s);
        IntValue v2 = (IntValue) r.eval(s);
        IntValue v = new IntValue(v1.n * v2.n);
        return v;
    }
}
