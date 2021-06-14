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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public AndAlso replace (Symbol x, Expr e) {
        return new AndAlso(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-ANDALSO
         * G|-e1:t2, q1    e2:t2, q2
         * -------------------------
         * G|-e1 andalso e2:bool, q1 U q2 U {t1 = bool} U {t2 = bool}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        s_all = s_all.compose(s_all.apply(t1.t).unify(Type.BOOL));
        s_all = s_all.compose(s_all.apply(t2.t).unify(Type.BOOL));
        return TypeResult.of(s_all, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * tt andalso v -> v
         * ff andalso e -> ff
         */
        if (((BoolValue) l.eval(s)).b) {
            return r.eval(s);
        }
        return (BoolValue) l.eval(s);
    }

    
}
