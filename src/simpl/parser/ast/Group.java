package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.parser.Symbol;

public class Group extends UnaryExpr {

    public Group(Expr e) {
        super(e);
    }

    public String toString() {
        return "" + e;
    }

    @Override
    public Group replace (Symbol x, Expr e) {
        return new Group(this.e.replace(x, e));
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        return e.typecheck(E);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return e.eval(s);
    }
}
