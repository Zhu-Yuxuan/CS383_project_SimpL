//done on 19 May morning

package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        // TODO
        /**
         * three cases
         * 1. tv = tv -> []
         * 2. tv = f(tv) -> error
         * 3. tv = other -> Substitution
         */
        if(t instanceof TypeVar){
            if(((TypeVar) t).name.equals(this.name))
                return Substitution.IDENTITY;
        }
        else if(t.contains(this))
            throw new TypeCircularityError();
        return Substitution.of(this, t);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // TODO
        return tv.name.equals(this.name);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // TODO
        if(this.name.equals(a.name))
            return t;
        return this;
    }
}
