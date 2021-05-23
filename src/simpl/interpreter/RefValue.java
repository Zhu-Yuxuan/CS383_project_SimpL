//done on 23 May morning
package simpl.interpreter;

import java.sql.Ref;

public class RefValue extends Value {

    public final int p;

    public RefValue(int p) {
        this.p = p;
    }

    public String toString() {
        return "ref@" + p;
    }

    @Override
    public boolean equals(Object other) {
        // TODO
        if (other instanceof RefValue) {
            return this.p == ((RefValue) other).p;
        }
        return false;
    }
}
