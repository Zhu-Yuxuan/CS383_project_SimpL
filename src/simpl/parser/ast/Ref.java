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
import simpl.parser.Symbol;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public Ref replace (Symbol x, Expr e) {
        return new Ref(this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-REF
         * G|-e:t, q
         * -----------------
         * G|-ref e:t ref, q
         */
        TypeResult t = e.typecheck(E);
        Substitution s_all = t.s;
        return TypeResult.of(s_all, new RefType(s_all.apply(t.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p+1;e => M',p';v    M'=M''[p->v]
         * ------------------------------------
         * E,M,p,ref e => M',p';p
         */
        s.p.set(s.p.get() + 1);
        Value v = e.eval(s);
        s.M.put(s.p.get(), v);
        return new RefValue(s.p.get());
    }
}
