package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
// import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public Rec replace (Symbol x, Expr e) {
        if (this.x.toString().equals(x.toString())) {
            return this;
        }
        return new Rec(this.x, this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-REC
         * G[x:t1]|-e:t2, q1
         * -----------------
         * G|-rec x => e:t2, q1 U {t1 = t2}
         */
        TypeVar t1 = new TypeVar(true);
        TypeResult t2 = e.typecheck(TypeEnv.of(E, x, t1));
        Substitution s_all = t2.s;
        s_all.compose(s_all.apply(t2.t).unify(t1));
        return TypeResult.of(s_all, s_all.apply(t2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E[x->(rec,E,x,e)],M,p;e => M',p';v
         * ----------------------------------
         * E,M,p;rec x=>e => M',p';v
         */
        RecValue rec = new RecValue(s.E, x, e);
        Value v = e.eval(State.of(new Env(s.E, x, rec), s.M, s.p));
        return v;
    }
}
