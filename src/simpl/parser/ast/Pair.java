package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.PairType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override
    public Pair replace (Symbol x, Expr e) {
        return new Pair(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-PAIR
         * G|-e1:t1, q1    e2:t2, q2
         * -------------------------
         * G|-pair e1, e2:t1xt2, q1 U q2
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        return TypeResult.of(s_all, new PairType(s_all.apply(t1.t), s_all.apply(t2.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2
         * ------------------------------------------------
         * E,M,p;(e1, e2) => M'',p'';(v1, v2)
         */
        Value v1 = (Value) l.eval(s);
        Value v2 = (Value) r.eval(s);
        return new PairValue(v1, v2);
    }
}
