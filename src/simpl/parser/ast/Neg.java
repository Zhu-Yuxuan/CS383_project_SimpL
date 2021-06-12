package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public Neg replace (Symbol x, Expr e) {
        return new Neg(this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-Neg
         * G|-e:t, q
         * ---------
         * G|-~e:a, q U {a = t, a = int}
         */
        TypeResult tr = e.typecheck(E);
        Substitution s = tr.t.unify(Type.INT);
        s = tr.s.compose(s);
        return TypeResult.of(s, Type.INT);
        }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e => M',p';v1    v = -v1
         * ------------------------------
         * E,M,p;~e => M',p';v
         */
        IntValue v1 = (IntValue) e.eval(s);
        IntValue v = new IntValue(-v1.n);
        return v;
    }
}
