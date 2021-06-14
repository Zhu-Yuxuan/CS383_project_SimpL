package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public Assign replace (Symbol x, Expr e) {
        return new Assign(l.replace(x, e), r.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-ASSIGN
         * G|-e1:t1, q1    e2:t2, q2
         * -------------------------
         * G|-e1 := e2:unit, q1 U q2 U {t1=t2 ref}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        RefType t2f = new RefType(t2.t);
        s_all = s_all.compose(s_all.apply(t1.t).unify(t2f));
        return TypeResult.of(s_all, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        /**
         * E,M,p;e1 => M',p';(ref,p1)    E,M'p';e2 => M''',p'';v    M''=M'''[p1->v]
         * ------------------------------------------------------------------------
         * E,M,p;e1:=e2 =>M'',p'';unit 
         */
        RefValue p1 = (RefValue) l.eval(s);
        Value v = (Value) r.eval(s);
        s.M.put(p1.p, v);
        return Value.UNIT;
    }
}
