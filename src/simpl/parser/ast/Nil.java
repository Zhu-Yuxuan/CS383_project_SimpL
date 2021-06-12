package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;
import simpl.parser.Symbol;

public class Nil extends Expr {

    public String toString() {
        return "nil";
    }

    @Override
    public Nil replace (Symbol x, Expr e) {
        return this;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-NIL
         * 
         * -----
         * G|-nil[t]:t list, {}
         */
        TypeVar tn = new TypeVar(true); // or false?
        return TypeResult.of(new ListType(tn));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * 
         * -----------
         * E,M,p;nil => M,p;nil
         */
        return Value.NIL;
    }
}
