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

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public Not replace (Symbol x, Expr e) {
        return new Not(this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-Not
         * G|-e:t, q
         * ---------
         * G|-not e:a, q U {a = t, a = bool}
         */
        TypeResult tr = e.typecheck(E);
        Substitution s = tr.t.unify(Type.BOOL);
        s = tr.s.compose(s);
        return TypeResult.of(s, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * true:
         * E,M,p;e => M',p';tt
         * -------------------
         * E,M,p;not e => M',p';ff
         * false:
         * E,M,p;e => M',p';ff
         * -------------------
         * E,M,p;not e => M',p';tt
         */
        BoolValue v = (BoolValue) e.eval(s);
        return new BoolValue(!(v.b));
    }
}
