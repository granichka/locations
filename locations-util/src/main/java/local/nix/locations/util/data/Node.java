package local.nix.locations.util.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {

    public final String name;
    public final int index;
    public List<Edge> adjacencies = new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;
    public Node(String name, int index) { this.name = name; this.index = index; }
    public String toString() { return name; }

    public List<Edge> getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(List<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }

    public int compareTo(Node other)
    {
        return Double.compare(minDistance, other.minDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return index == node.index &&
                Objects.equals(name, node.name);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }
}

