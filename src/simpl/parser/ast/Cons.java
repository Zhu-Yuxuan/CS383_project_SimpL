package simpl.parser.ast;

// import java_cup.runtime.Symbol;
import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;
import simpl.parser.Symbol;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public Cons replace (Symbol x, Expr e) {
        return new Cons(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-CONS
         * G|-e1:t1, q1     e2:t2, q2
         * --------------------------
         * G|-e1::e2:a, q1 U q2 U {t2 = t1 list} U {a = t2}
         */
        TypeResult tr1 = l.typecheck(E);
        TypeEnv E2 = tr1.s.compose(E);
        TypeResult tr2 = r.typecheck(E2);
        // Substitution s_all = t1.s.compose(t2.s);
        // ListType t2l = new ListType(t1.t);
        // s_all = s_all.compose(s_all.apply(t2.t).unify(s_all.apply(t2l)));
        // return TypeResult.of(s_all, s_all.apply(t2.t));
        Substitution comp = tr2.s.compose(tr1.s);
        TypeVar a = new TypeVar(true);
        ListType lt = new ListType(a);
        Substitution s = comp.apply(tr2.t).unify(comp.apply(lt));
        comp = comp.compose(s);
        s = comp.apply(tr1.t).unify(comp.apply(a));
        comp = comp.compose(s);

        return TypeResult.of(comp, comp.apply(tr2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';v1    E,M',p';e2 => M'',p'';v2
         * ------------------------------------------------
         * E,M,p;e1::e2 => M'',p'';v1::v2
         */
        Value v1 = (Value) l.eval(s);
        Value v2 = (Value) r.eval(s);
        return new ConsValue(v1, v2);
    }
}
