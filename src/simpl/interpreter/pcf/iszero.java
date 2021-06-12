package simpl.interpreter.pcf;

import simpl.interpreter.BoolValue;
import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class iszero extends FunValue {

    public iszero() {
        // TODO
        super(Env.empty, Symbol.symbol("iszero"), new Expr() {
            @Override
            public Expr replace (Symbol x, Expr e) {
                return this;
            }

            @Override
            public TypeResult typecheck (TypeEnv E) throws TypeError {
                return TypeResult.of(new TypeVar(true));
            }

            @Override
            public Value eval(State s) throws RuntimeError{
                /**
                 * E,M,p;e => M',p';v    v == 0
                 * -----------------------------
                 * E,M,p;iszero e => M',p';tt
                 */
                IntValue v = (IntValue) s.E.get(Symbol.symbol("iszero"));
                if (v.n == 0) {
                    return new BoolValue(true);
                }
                return new BoolValue(false);
            }
        });
    }
}
