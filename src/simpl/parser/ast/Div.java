package simpl.parser.ast;

// import org.graalvm.compiler.core.common.util.TypeReader;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;

public class Div extends ArithExpr {

    public Div(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " / " + r + ")";
    }

    @Override
    public Div replace (Symbol x, Expr e) {
        return new Div(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2    v2 != 0    v = v1/v2
         * ------------------------------------------------------------------------
         * E,M,p;e1/e2 => M'',p'';v
         */
        IntValue v1 = (IntValue) l.eval(s);
        IntValue v2 = (IntValue) r.eval(s);
        if (v2.n == 0) {
            throw new RuntimeError("divided by 0");
        }
        IntValue v = new IntValue(v1.n/v2.n);
        return v;
    }
}
