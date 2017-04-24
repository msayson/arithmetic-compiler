package analysis.implementation;

import analysis.FlowGraph;
import analysis.Liveness;
import analysis.util.ActiveSet;
import analysis.util.graph.Node;
import ir.temp.Temp;
import util.List;

import java.util.Collections;


public class LivenessImplementation<N> extends Liveness<N> {

    List<Node<N>> visited = List.theEmpty();

    public LivenessImplementation(FlowGraph<N> graph) {
        super(graph);
    }

    @Override
    public List<Temp> liveOut(Node<N> node) {
        visited = List.theEmpty();
        return liveOutInternal(node);
    }

    private List<Temp> liveOutInternal(Node<N> node) {
        //Collect all temps used in successors
        ActiveSet<Temp> liveOutTemps = new ActiveSet<>();

        List<Node<N>> successors = node.succ();
        for (Node<N> successor : successors) {
            if (!visited.contains(successor)) {
                visited = List.cons(successor, visited);
                liveOutTemps.addAll(liveInInternal(successor));
            }
        }
        return liveOutTemps.getElements();
    }

    private List<Temp> liveIn(Node<N> node) {
        visited = List.theEmpty();
        return liveInInternal(node);
    }

    // Return use(node) UNION (out(node) - def(node))
    private List<Temp> liveInInternal(Node<N> node) {
        ActiveSet<Temp> liveInTemps = new ActiveSet<>();
        liveInTemps.addAll(liveOutInternal(node));
        liveInTemps = liveInTemps.remove(g.def(node)); // out(node) - def(node)

        liveInTemps.addAll(g.use(node)); // use(node) UNION (out(node) - def(node))
        return liveInTemps.getElements();
    }

    private String shortList(List<Temp> l) {
        java.util.List<String> reall = new java.util.ArrayList<String>();
        for (Temp t : l) {
            reall.add(t.toString());
        }
        Collections.sort(reall);
        StringBuffer sb = new StringBuffer();
        sb.append(reall);
        return sb.toString();
    }

    private String dotLabel(Node<N> n) {
        StringBuffer sb = new StringBuffer();
        sb.append(shortList(liveIn(n)));
        sb.append("\\n");
        sb.append(n);
        sb.append(": ");
        sb.append(n.wrappee());
        sb.append("\\n");
        sb.append(shortList(liveOut(n)));
        return sb.toString();
    }

    private double fontSize() {
        return (Math.max(30, Math.sqrt(Math.sqrt(g.nodes().size() + 1)) * g.nodes().size() * 1.2));
    }

    private double lineWidth() {
        return (Math.max(3.0, Math.sqrt(g.nodes().size() + 1) * 1.4));
    }

    private double arrowSize() {
        return Math.max(2.0, Math.sqrt(Math.sqrt(g.nodes().size() + 1)));
    }

    @Override
    public String dotString(String name) {
        StringBuffer out = new StringBuffer();
        out.append("digraph \"Flow graph\" {\n");
        out.append("labelloc=\"t\";\n");
        out.append("fontsize=" + fontSize() + ";\n");
        out.append("label=\"" + name + "\";\n");

        out.append("  graph [size=\"6.5, 9\", ratio=fill];\n");
        for (Node<N> n : g.nodes()) {
            out.append("  \"" + dotLabel(n) + "\" [fontsize=" + fontSize());
            out.append(", style=\"setlinewidth(" + lineWidth() + ")\", color=" + (g.isMove(n) ? "green" : "blue"));
            out.append("]\n");
        }
        for (Node<N> n : g.nodes()) {
            for (Node<N> o : n.succ()) {
                out.append("  \"" + dotLabel(n) + "\" -> \"" + dotLabel(o) + "\" [arrowhead = normal, arrowsize=" + arrowSize() + ", style=\"setlinewidth(" + lineWidth() + ")\"];\n");
            }
        }

        out.append("}\n");
        return out.toString();
    }

}
