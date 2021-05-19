//done on 19 May morning
package simpl.typing;

// import jdk.internal.net.http.common.Pair;
// import simpl.parser.ast.Pair;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        // consider a pair as two units
        return t1.isEqualityType() && t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
        /**
         * three cases
         * 1. tv = (t1, t2) -> [(t1, t2) / tv]
         * 2. (tv1, tv2) = (t1, t2) -> tv1 = t1, tv2 = t2
         *    need to consider that t2 may contains tv1
         * 3. int / bool / ... = (t1, t2) -> error
         */
        if(t instanceof TypeVar)
            return Substitution.of((TypeVar) t, this);
        if(t instanceof PairType){
            Substitution s1 = ((PairType) t).t1.unify(this.t1);
            Substitution s2 = ((PairType) t).t2.unify(this.t2);
            return s1.compose(s2);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        //consider a pair as two units
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        //consider a pair as two units
        return new PairType(t1.replace(a, t), t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
