package simpl.parser.ast;

// import java_cup.runtime.Symbol;s
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class ArithExpr extends BinaryExpr {

    public ArithExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        /**
         * CT-Arith
         * G|-e1:t1, q1   e2:t2,  q2
         * G|-e1 arith e2:a, q1 U q2 U {t1 = Int, t2 = Int, }
         */
        TypeResult tr1 = l.typecheck(E);
        TypeEnv E2 = tr1.s.compose(E);
        TypeResult tr2 = l.typecheck(E2);
        Substitution s_all = tr1.s.compose(tr2.s);
        Substitution s1 = s_all.apply(tr1.t).unify(Type.INT);
        s_all = s_all.compose(s1);
        Substitution s2 = s_all.apply(tr2.t).unify(Type.INT);
        s_all = s_all.compose(s2);
        return TypeResult.of(s_all, Type.INT);
    }
}