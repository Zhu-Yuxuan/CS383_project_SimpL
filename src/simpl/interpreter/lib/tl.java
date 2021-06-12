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

public class tl extends FunValue {

    public tl() {
        // TODO
        super(Env.empty, Symbol.symbol("tl"), new Expr() {
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
                 * E,M,p;e2 => M',p';v1
                 * ---------------------------------
                 * E,M,p;tl(cons,e1,e2) => M',p';v2
                 */
                ConsValue v1 = (ConsValue) s.E.get(Symbol.symbol("tl"));
                if (v1 == Value.UNIT) {
                    throw new RuntimeError("hd an empty list");
                }
                return v1.v2;
            }
        });
    }
}
