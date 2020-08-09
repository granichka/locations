package local.nix.locations.util.djkstra;

import local.nix.locations.util.data.Edge;
import local.nix.locations.util.data.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class GraphHandler {

    public static void computePaths(List<Node> graph, Node source)
    {
        source.minDistance = 0.;
        PriorityQueue<Node> vertexQueue = new PriorityQueue<Node>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Node u = vertexQueue.poll();


            for (Edge e : u.adjacencies)
            {
                int index = e.target;
                Node node = getNodeByIndex(graph, index);
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < node.minDistance) {
                    vertexQueue.remove(node);

                    node.minDistance = distanceThroughU ;
                    node.previous = u;
                    vertexQueue.add(node);
                }
            }
        }
    }

    public static List<Node> getShortestPathTo(Node target)
    {
        List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.previous)
            path.add(node);

        Collections.reverse(path);
        return path;
    }

    public static Node getNodeByIndex(List<Node> graph, int index) {
        Node result = null;
        for(Node node: graph) {
            if(node.index == index) {
                result = node;
            }
        }

        if(result == null) {
            throw new IllegalArgumentException("узла с таким индексом нет в графе");
        }

        return result;
    }

    public static Node getNodeByName(List<Node> graph, String name) {
        Node result = null;
        for(Node node: graph) {
            if(node.name.equals(name)) {
                result = node;
            }
        }

        if(result == null) {
            throw new IllegalArgumentException("узла с таким индексом нет в графе");
        }

        return result;
    }
}
