package simpl.interpreter.lib;

// import org.graalvm.compiler.core.common.type.SymbolicJVMCIReference;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class fst extends FunValue {

    public fst() {
        // TODO
        super(Env.empty, Symbol.symbol("fst"), new Expr() {
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
                 * ------------------------
                 * E,M,p;fst(e1,e2) => M',p';v1
                 */
                PairValue v1 = (PairValue) s.E.get(Symbol.symbol("fst"));
                return v1.v1;
            }
        });
    }
}
