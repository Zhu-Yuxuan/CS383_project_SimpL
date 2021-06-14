package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public App replace(Symbol x, Expr e) {
        return new App(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-app
         * G|-e1:t1, q1    e2:t2, q2
         * -------------------------
         * G|-e1 e2:a, q1 U q2 U {t1 = t2 -> a}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t2.s.compose(t1.s);
        ArrowType t1n = new ArrowType(s_all.apply(t2.t), new TypeVar(true)); 
        s_all = s_all.compose(s_all.apply(t1.t).unify(t1n));
        return TypeResult.of(s_all, s_all.apply(t1n.t2));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * e1 -> fun x e (v1)    e2 -> v2    e[v2/x] -> v1
         * -----------------------------------------------
         * e1 e2 -> v
         */
        FunValue v1 = (FunValue) l.eval(s);
        Value v2 = (Value) r.eval(s);
        return v1.e.eval(State.of(new Env(v1.E, v1.x, v2), s.M, s.p));
        }
}
