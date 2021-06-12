package simpl.interpreter.pcf;

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

public class succ extends FunValue {

    public succ() {
        // TODO
        super(Env.empty, Symbol.symbol("succ"), new Expr() {
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
                 * E,M,p;e => M',p';v    v' = v+1
                 * ------------------------------
                 * E,M,p;succ e => M',p';v'
                 */
                IntValue v = (IntValue) s.E.get(Symbol.symbol("succ"));
                return new IntValue(v.n + 1);
            }
        });
    }
}
