package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;
import simpl.parser.Symbol;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public Deref replace (Symbol x, Expr e) {
        return new Deref(this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-Deref
         * G|-e:t, q
         * ------------
         * G|-!e:a, q U {t = ref a}
         */
        TypeResult tr = e.typecheck(E);
        RefType rt = new RefType(new TypeVar(true));
        Substitution s = tr.t.unify(rt);
        s = tr.s.compose(s);
        return TypeResult.of(s, s.apply(rt.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e => M',p';rep p1    v=M'(p1)
         * -----------------------------------
         * E,M,p;!e => M',p';v
         */
        RefValue p1 = (RefValue) e.eval(s);
        Value v = s.M.get(p1.p);
        return v;
    }
}
