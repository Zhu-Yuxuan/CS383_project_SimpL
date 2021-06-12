package simpl.interpreter.lib;

import simpl.interpreter.ConsValue;
import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class hd extends FunValue {

    public hd() {
        // TODO
        super(Env.empty, Symbol.symbol("hd"), new Expr() {
            @Override
            public Expr replace (Symbol x, Expr e) {
                return this;
            }

            @Override
            public TypeResult typecheck (TypeEnv E) throws TypeError {
                // return null;
                return TypeResult.of(new TypeVar(true));
            }

            @Override
            public Value eval (State s) throws RuntimeError {
                /**
                 * E,M,p;e1 => M',p';v1
                 * ---------------------------------
                 * E,M,p;fst(cons,e1,e2) => M',p';v1
                 */
                ConsValue v1 = (ConsValue) s.E.get(Symbol.symbol("hd"));
                if (v1 == Value.UNIT) {
                    throw new RuntimeError("hd an empty list");
                }
                return v1.v1;
            }
        });
    }
}
