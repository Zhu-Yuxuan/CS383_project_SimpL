package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;

public class Eq extends EqExpr {

    public Eq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " = " + r + ")";
    }

    @Override
    public Eq replace (Symbol x, Expr e) {
        return new Eq(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * true:
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2    v1 == v2
         * ------------------------------------------------------------
         * E,M,p;e1=e2 => M'',p'';tt
         * false:
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2    v1 != v2
         * ------------------------------------------------------------
         * E,M,p;e1=e2 => M'',p'';ff
         */
        return new BoolValue(l.eval(s).equals(r.eval(s)));
    }
}
