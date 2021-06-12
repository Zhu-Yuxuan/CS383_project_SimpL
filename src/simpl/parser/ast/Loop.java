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

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public Loop replace (Symbol x, Expr e) {
        return new Loop(e1.replace(x, e), e2.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-LOOP
         * G|-e1:t1, q1     e2:t2, q2
         * --------------------------
         * G|-while e1 do e2:unit, q1 U q2 U {t1 = bool}
         */
        TypeResult t1 = e1.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = e2.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        s_all =s_all.compose(s_all.apply(t1.t).unify(Type.BOOL));
        return TypeResult.of(s_all, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * true:
         * E,M,p;e1 => M',p';tt    M',p';e2;while e1 do e2; => M'',p'';v
         * -------------------------------------------------------------
         * E,M,p;while e1 do e2 => M'',p'';v
         * false:
         * E,M,p:e1 => M',p';ff
         * --------------------
         * E,M,p;while e1 do e2 => M'',p'';unit
         */
        BoolValue bol = (BoolValue) e1.eval(s);
        if (bol.b) {
            e2.eval(s);
            Loop loop = new Loop(e1, e2);
            Value v = loop.eval(s);
            return v;
        }
        return Value.UNIT;
    }
}
