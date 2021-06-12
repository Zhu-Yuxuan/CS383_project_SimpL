package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        // TODO
        /**
         * pdf should be considered as types
         */
        // E = TypeEnv.empty;
        // E = TypeEnv.of(E, Symbol.symbol("iszero"), new ArrowType(Type.INT, Type.BOOL));
        // E = TypeEnv.of(E, Symbol.symbol("pred"), new ArrowType(Type.INT, Type.INT));
        // E = TypeEnv.of(E, Symbol.symbol("succ"), new ArrowType(Type.INT, Type.INT));
        // Type t1 = new TypeVar(true);
        // Type t2 = new TypeVar(true);
        // E = TypeEnv.of(E, Symbol.symbol("fst"), new ArrowType(new PairType(t1, t2), t1));
        // E = TypeEnv.of(E, Symbol.symbol("snd"), new ArrowType(new PairType(t1, t2), t2));
        // E = TypeEnv.of(E, Symbol.symbol("hd"), new ArrowType(new ListType(t1), t1));
        // E = TypeEnv.of(E, Symbol.symbol("tl"), new ArrowType(new ListType(t1), t1));
        E = new TypeEnv(){
            @Override
            public Type get(Symbol x) throws TypeError {
                if (x.toString().equals("iszero")) {
                    return new ArrowType(Type.INT, Type.BOOL);
                }
                else if (x.toString().equals("pred")) {
                    return new ArrowType(Type.INT, Type.INT);                    
                }
                else if (x.toString().equals("succ")) {
                    return new ArrowType(Type.INT, Type.INT);
                }
                else if (x.toString().equals("fst")) {
                    Type t1 = new TypeVar(true);
                    Type t2 = new TypeVar(true);
                    return new ArrowType(new PairType(t1, t2), t1);
                }
                else if (x.toString().equals("snd")) {
                    Type t1 = new TypeVar(true);
                    Type t2 = new TypeVar(true);
                    return new ArrowType(new PairType(t1, t2), t2);
                }
                else if (x.toString().equals("hd")) {
                    Type t = new TypeVar(true);
                    return new ArrowType(new ListType(t), t);
                }
                else if (x.toString().equals("tl")) {
                    Type t = new TypeVar(true);
                    return new ArrowType(new ListType(t), new ListType(t));
                }
                else throw new TypeError("Type not found!");
            }
        };

    }

    @Override
    public Type get(Symbol x) throws TypeError {
        try {
            return E.get(x);
        }catch (TypeError e){
            throw e;
        }
    }
}
