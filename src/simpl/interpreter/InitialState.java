package simpl.interpreter;

import simpl.interpreter.lib.hd;
import simpl.interpreter.lib.tl;
import simpl.interpreter.lib.fst;
import simpl.interpreter.lib.snd;
import simpl.interpreter.pcf.iszero;
import simpl.interpreter.pcf.pred;
import simpl.interpreter.pcf.succ;
import simpl.parser.Symbol;

public class InitialState extends State {

    public InitialState() {
        super(initialEnv(Env.empty), new Mem(), new Int(0));
    }

    private static Env initialEnv(Env E) {
        // TODO
        // with pcfs & libs
        Env env = new Env(E, Symbol.symbol("fst"), new fst());
        env = new Env(env, Symbol.symbol("snd"), new snd());
        env = new Env(env, Symbol.symbol("hd"), new hd());
        env = new Env(env, Symbol.symbol("tl"), new tl());
        env = new Env(env, Symbol.symbol("iszero"), new iszero());
        env = new Env(env, Symbol.symbol("pred"), new pred());
        env = new Env(env, Symbol.symbol("succ"), new succ());
        return env;
    }
}
