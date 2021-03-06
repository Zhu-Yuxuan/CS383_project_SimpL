package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class IntegerLiteral extends Expr {

    public int n;

    public IntegerLiteral(int n) {
        this.n = n;
    }

    public String toString() {
        return "" + n;
    }

    @Override
    public IntegerLiteral replace (Symbol x, Expr e) {
        return this;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        return TypeResult.of(Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new IntValue(n);
    }
}
