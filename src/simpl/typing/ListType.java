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
        return null;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return null;
    }

    public String toString() {
        return t + " list";
    }
}
