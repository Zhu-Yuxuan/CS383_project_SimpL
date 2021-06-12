package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
// import simpl.parser.Symbol;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-REL
         * G|-e1:t1, q1    e2:t2, q1    bop \in {<,<=,>,>=}
         * ------------------------------------------------
         * G|-e1 bop e2:bool, q1 U q2 U {t1 = int} U {t2 = int}
         */
        TypeResult t1 = l.typecheck(E);
        TypeEnv E2 = t1.s.compose(E);
        TypeResult t2 = r.typecheck(E2);
        Substitution s_all = t1.s.compose(t2.s);
        s_all.compose(s_all.apply(t1.t).unify(Type.INT));
        s_all.compose(s_all.apply(t2.t).unify(Type.INT));
        return TypeResult.of(s_all, Type.BOOL);
    }
}
