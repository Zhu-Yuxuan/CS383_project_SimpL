package simpl.parser.ast;

// import simpl.typing.ListType;
// import simpl.typing.PairType;
// import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
// import simpl.typing.TypeVar;
// import simpl.parser.Symbol;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-EQ
         * G|-e1:t1, q1     w2:t2, q2
         * --------------------------
         * G|-e1 eq e2:bool, q1 U q2 U {t1 = t2}
         */
        TypeResult t1 = l.typecheck(E);
        if (!t1.t.isEqualityType()) {
            throw new TypeMismatchError();
        }
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        if (!t2.t.isEqualityType()) {
            throw new TypeMismatchError();
        }
        Substitution s_all = t1.s.compose(t1.s);
        s_all = s_all.compose(s_all.apply(t1.t).unify(s_all.apply(t2.t)));
        return TypeResult.of(s_all, Type.BOOL);
    }
}
