package simpl.parser.ast;

// import java.util.concurrent.Flow.Subscriber;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
// import simpl.typing.Substitution;
// import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    @Override
    public Fn replace (Symbol x, Expr e) {
        if (x.toString().equals(this.x.toString())){
            return this;
        }
        return new Fn(this.x, this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-FN
         * G, x:t1|-e:t2, q
         * --------------
         * G|-fn x.e:a = t1->t2, q
         */
        TypeVar t1 = new TypeVar(true);
        TypeResult t2 = e.typecheck(TypeEnv.of(E, x, t1));
        ArrowType a = new ArrowType(t2.s.apply(t1), t2.s.apply(t2.t));
        return TypeResult.of(t2.s, a);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * 
         * ---------------
         * E,M,p;fn x=> e => M,p;(fn,E,x,e)
         */
        return new FunValue(s.E, x, e);
    }
}
