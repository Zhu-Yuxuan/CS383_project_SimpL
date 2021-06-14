//done on 19 May morning
package simpl.typing;

public final class ArrowType extends Type {

    public Type t1, t2;

    public ArrowType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        // consider arrow type as two other types
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
        /**
         * 1. t1->t2 = tv => [tv/t1->t2]
         * 2. t1->t2 = tv1->tv2 => t1=tv1, t2=tv2
         * 3. t1->t2 = others => error
         */
        if(t instanceof TypeVar)
            return Substitution.of(((TypeVar) t), this);
        else if(t instanceof ArrowType){
            // Substitution s1 = this.t1.unify(((ArrowType) t).t1);
            // Substitution s2 = this.t2.unify(((ArrowType) t).t2);
            // return s1.compose(s2);
            Substitution s1 = ((ArrowType) t).t1.unify(this.t1);
            Substitution s2 = s1.apply(((ArrowType) t).t2).unify(s1.apply(this.t2));
            return s1.compose(s2);
        }
        else throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        // consider arrow type as two other types
        return this.t1.contains(tv) || this.t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return new ArrowType(this.t1.replace(a, t), this.t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " -> " + t2 + ")";
    }
}
