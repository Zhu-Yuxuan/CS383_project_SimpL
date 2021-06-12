package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
// import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public Expr replace (Symbol x, Expr e) {
        if (this.x.toString().equals(x.toString())) {
            return e;
        }
        else return this;

    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-NAME
         * G(x)=t
         * ----------
         * G|-x:t, {}
         */
        if (E.get(x) == null) {
            throw new TypeError("Type error: " + x + " not foumd");
        }
        return TypeResult.of(E.get(x));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * recursive:
         * E(x)=(rec,E1,x1,e1)    E1,M,p;rec x1 => e1 => M',p';v
         * -----------------------------------------------------
         * E,M,p;x => M',p';v
         * end:
         * E(x)=v
         * ----------------
         * E,M,p;x => M,p,v
         */
        Value Ex = s.E.get(x);
        if (Ex == null) {
            throw new RuntimeError("mismatch variable Name");
        }
        else if (Ex instanceof RecValue) {
            Rec rec = new Rec(((RecValue) Ex).x, ((RecValue) Ex).e);
            Value v = rec.eval(State.of(((RecValue) Ex).E, s.M, s.p));
            return v;
        }
        return Ex;
    }
}
