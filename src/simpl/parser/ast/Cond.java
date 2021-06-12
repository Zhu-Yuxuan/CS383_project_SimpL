package simpl.parser.ast;

// import java_cup.runtime.Symbol;
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

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public Cond replace (Symbol x, Expr e) {
        return new Cond(e1.replace(x, e), e2.replace(x, e), e3.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-COND
         * G|-e1:t1, q1     e2:t2, q2   e3:t3, q3
         * --------------------------------------
         * G|-if e1 then e2 else e3:a, q1 U q2 U q3 U {t1 = bool} U {t2 = t3} U {a = t3}
         */
        TypeResult t1 = e1.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = e2.typecheck(E2);
        TypeEnv E3 = t2.s.compose(E2);
        TypeResult t3 = e3.typecheck(E3);
        Substitution s_all = t1.s.compose(t2.s.compose(t3.s));
        s_all = s_all.compose(t3.s);
        Substitution s1 = s_all.apply(t1.t).unify(Type.BOOL);
        s_all = s_all.compose(s1);
        Substitution s2 = s_all.apply(t2.t).unify(t3.t);
        s_all = s_all.compose(s2);
        return TypeResult.of(s_all, t3.t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * true:
         * E,M,p;e1 => M',p';tt    E,M',p';e2 => M'',p'';v
         * -----------------------------------------------
         * E,M,p;if e1 then e2 else e3 => M'',p'';v
         * false:
         * E,M,p;e1 => M',p';ff    E,M',p';e3 => M'',p'';v
         * -----------------------------------------------
         * E,M,p;if e1 then e2 else e3 => M'',p'';v
         */
        BoolValue bo = (BoolValue) e1.eval(s);
        if (bo.b) {
            Value v = (Value) e2.eval(s);
            return v;
        }
        Value v = (Value) e3.eval(s);
        return v;
    }
}
