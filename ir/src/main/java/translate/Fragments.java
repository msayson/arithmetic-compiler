package translate;

import ir.frame.Frame;
import util.DefaultIndentable;
import util.IndentingWriter;
import util.List;

import java.util.Iterator;


/**
 * A collection of fragments of IR code.
 */
public class Fragments extends DefaultIndentable implements Iterable<Fragment> {

    /**
     * The translator should provide its FrameFactory in here. This object pretty much
     * centralises/isolates architecture specific code. Subsequent phases should use the
     * same target architecture as the translator, so it is convenient to have the
     * translator pass along this Frame factory object along with its generated IR code.
     */
    private Frame frameFactory;

    /**
     * Generate IR code (and possibly other Fragments):
     */
    private List<Fragment> frags = List.empty();

    public Fragments(Frame frameFactory) {
        super();
        this.frameFactory = frameFactory;
    }

    @Override
    public void dump(IndentingWriter out) {
        out.println("Fragments {");
        out.indent();

        for (Fragment frag : frags) {
            out.println(frag);
        }

        out.outdent();
        out.print("}");
    }

    public void add(Fragment frag) {
        frags.add(frag);
    }

    /**
     * To allow writing "foreach" loop on Fragments.
     */
    @Override
    public Iterator<Fragment> iterator() {
        return frags.iterator();
    }

    /**
     * Fetch the (target architecture specific) frame factory that was used to
     * produce this IR.
     */
    public Frame getFrameFactory() {
        return frameFactory;
    }

}
