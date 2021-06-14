package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public Seq replace (Symbol x, Expr e) {
        return new Seq(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-Seq
         * G|-e1:t1, q1    e2:t2, q2
         * -------------------------
         * G|-e1;e2:t2, q1 U q2 U {t1 = unit}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        s_all.compose(s_all.apply(t1.t).unify(Type.UNIT));
        return TypeResult.of(s_all, s_all.apply(t2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2
         * ------------------------------------------------
         * E,M,p;e1;e2 => M'',p'';v2
         */
        l.eval(s);
        Value v2 = (Value) r.eval(s);
        return v2;
    }
}
