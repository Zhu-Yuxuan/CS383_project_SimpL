//done on 19 May morning
package simpl.typing;

public final class ListType extends Type {

    public Type t;

    public ListType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        return this.t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
        /**
         * 1. list t = tv => [tv/list t]
         * 2. list t = list tv => [tv/t]
         * 3. list t = others => error
         */
        if(t instanceof TypeVar)
            return Substitution.of(((TypeVar) t), this);
        else if(t instanceof ListType)
            return Substitution.IDENTITY;
        throw new TypeMismatchError();
        // return null;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return new ListType(this.t.replace(a, t));
    }

    public String toString() {
        return t + " list";
    }
}
