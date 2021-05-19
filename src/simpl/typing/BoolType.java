package simpl.typing;

final class BoolType extends Type {

    protected BoolType() {
    }

    @Override
    public boolean isEqualityType() {
        // TODO
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // TODO
        /**
         * 1. t:bool = tv => [tv/t]
         * 2. t:bool = t:bool => []
         * 3. t:bool = others => error
         */
        if(t instanceof TypeVar)
            return Substitution.of(((TypeVar) t), this);
        else if(t instanceof BoolType)
            return Substitution.IDENTITY;
        throw new TypeMismatchError();
        // return null;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        return Type.BOOL;
    }

    public String toString() {
        return "bool";
    }
}
