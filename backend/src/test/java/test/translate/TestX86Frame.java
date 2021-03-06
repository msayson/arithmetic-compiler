package test.translate;

import ir.frame.Access;
import ir.frame.Frame;
import ir.frame.x86_64.X86_64Frame;
import ir.temp.Label;

import org.junit.jupiter.api.Test;
import util.List;

/**
 * This test is not very thorough. It basically only checks whether allocated locals
 * formals adresses are distinct from one another.
 * <p>
 * You probably want to craft some tests that are more specific and check whether
 * the *right* adresses get allocated for locals and formals (which depends on
 * understanding the specific calling conventions of the x86 architecture).
 *
 * @author kdvolder
 */
public class TestX86Frame {

    private Frame factory =
            //This should be the only reference to X86Frame. Everything else
            //should use Frame methods only
            X86_64Frame.factory;

    @Test
    public void noFormals() throws Exception {
        System.out.println("Allocating 0 formals and 4 locals");
        Label empty = Label.generate("empty");
        Frame frame = factory.newFrame(empty, 0);
        assert (0 == frame.getFormals().size());
        Access[] var = new Access[4];
        for (int i = 0; i < var.length; i++) {
            boolean escapes = i % 2 == 0;
            var[i] = frame.allocLocal(escapes);
            System.out.println("var[" + i + "] = " + var[i] + "\tescapes = " + escapes);
        }
        // This should have allocated 4 distinct locations. (Two of
        // them inFrame and two of them inRegisters).
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var.length; j++) {
                assert ((i == j) == var[i].equals(var[j]));
                assert ((i == j) == var[i].toString().equals(var[j].toString()));
            }
        }
    }

    @Test
    public void severalFormalsFrame() throws Exception {
        System.out.println("Allocating 4 formals and 4 locals");
        Label lab = Label.generate("four");
        List<Boolean> formalsEscape = List.list(true, false, true, false);
        Frame frame = factory.newFrame(lab, 4);
        List<Access> formals = frame.getFormals();

        assert(formalsEscape.size() == formals.size());

        Access[] var = new Access[8];
        for (int i = 0; i < formals.size(); i++) {
            var[i] = formals.get(i);
            Boolean escapes = formalsEscape.get(i);
            System.out.println("var[" + i + "] = " + var[i] + "\tescapes = " + escapes);
        }

        for (int i = formals.size(); i < var.length; i++) {
            //boolean escapes = i%2==0;
            boolean escapes = true; // force all locals to be inFrame for this test
            var[i] = frame.allocLocal(escapes);
            System.out.println("var[" + i + "] = " + var[i] + "\tescapes = " + escapes);
        }
        // This should have allocated 8 distinct locations (we are checking here
        // that formals and inframe locals don't accidentally get mapped to the same frame
        // adresses. for this test to work properly, it is assumed there is a reasonable
        // implementation of toString that will format two Access objects representing
        // the same inFrame address as the same string.
        for (int i = 0; i < var.length; i++) {
            for (int j = 0; j < var.length; j++) {
                assert ((i == j) == var[i].equals(var[j]));
                assert ((i == j) == var[i].toString().equals(var[j].toString()));
            }
        }
    }
}
