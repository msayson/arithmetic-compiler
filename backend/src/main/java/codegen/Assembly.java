package codegen;

import util.DefaultIndentable;
import util.IndentingWriter;
import util.List;
import util.Utils;

import java.util.Iterator;


/**
 * Represents the result of applying a code generator to a list of IR fragments.
 */
public class Assembly extends DefaultIndentable implements Iterable<AssemFragment> {

    private List<AssemFragment> fragments = List.empty();

    @Override
    public void dump(IndentingWriter out) {
        for (AssemFragment f : fragments) {
            out.println(f);
        }

        out.indent();

        out.println(".ident	\"arithlangc: simple arithmetic language compiler\"");
        if (!Utils.macOS())
            out.println(".section	.note.GNU-stack,\"\",@progbits");
        out.outdent();
    }

    public void add(AssemFragment code) {
        fragments.add(code);
    }

    @Override
    public Iterator<AssemFragment> iterator() {
        return fragments.iterator();
    }

}
