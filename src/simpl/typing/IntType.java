//done on 19 May morning
package simpl.typing;

// import simpl.interpreter.Int;

final class IntType extends Type {

    protected IntType() {
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
         * 1. t:int = tv -> [tv/t]
         * 2. t:int = t:int -> []
         * 3. t:int = others -> error
         */
        if(t instanceof TypeVar)
            return Substitution.of(((TypeVar) t), this);
        else if(t instanceof IntType)
            return Substitution.IDENTITY;
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        /**
         * ((\e:int. a) t ): int
         */
        return Type.INT;
    }

    public String toString() {
        return "int";
    }
}
