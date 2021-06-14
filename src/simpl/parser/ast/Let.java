package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public Let replace(Symbol x, Expr e) {
        if (this.x.toString().equals(x.toString())) {
            /**
             * (let x = e1 in e2)[e/x] => (let x = e1[e/x] e2)
             */
            return new Let(this.x, e1.replace(x, e), e2);
        }
        /**
         * (let x = e1 in e2)[e/x'] => (let x = e1[e/x'] in e2[e/x'])
         */
        return new Let(this.x, e1.replace(x, e), e2.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-LET
         * G|-e2[e1/x]:t2, q1   e1:t1
         * --------------------------
         * G|-let x=e1 in e2:t2, q1
         */
        TypeResult t1 = e1.typecheck(E);
        TypeResult t2 = e2.replace(x, e1).typecheck(E);
        return TypeResult.of(t2.s, t2.s.apply(t2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E[x->v1],M',p';e2 => M'',p'';v2
         * -------------------------------------------------------
         * E,M,p;let x=e1 in e2 end=> M'',p'';v2
         */
        Value v1 = (Value) e1.eval(s);
        Value v2 = (Value) e2.eval(State.of(new Env(s.E, x, v1), s.M, s.p));
        return v2;
    }
}
