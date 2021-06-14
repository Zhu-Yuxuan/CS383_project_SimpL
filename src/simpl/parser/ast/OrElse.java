package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class OrElse extends BinaryExpr {

    public OrElse(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " orelse " + r + ")";
    }

    @Override
    public OrElse replace (Symbol x, Expr e) {
        return new OrElse(l.replace(x, e), r.replace(x, e));
    }


    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-ORELSE
         * G|-e1:t1, q1    e2:t2, q2
         * -------------------------
         * G|-e1 orelse e2: bool, q1 U q2 U {t1 = bool} U {t2 = bool}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        s_all.compose(s_all.apply(t1.t).unify(Type.BOOL));
        s_all.compose(s_all.apply(t2.t).unify(Type.BOOL));
        return TypeResult.of(s_all, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * true:
         * E,M,p;e1 ==> M',p';tt
         * ------------------------------
         * E,M,p:e1 orelse e2 => M',p';tt
         * false:
         * E,M,p;e1 ==> M',p';ff    E,M',p';e2 => M'',p'';v
         * ------------------------------------------------
         * E,M,p:e1 orelse e2 => M'',p'';v
         */
        BoolValue v1 = (BoolValue) l.eval(s);
        if (v1.b) {
            return new BoolValue(true);
        }
        Value v2 = (Value) r.eval(s);
        return v2;
    }
}
