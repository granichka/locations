package local.nix.locations.demo;

import local.nix.locations.database.connection.ConnectionUtil;
import local.nix.locations.demo.reader.DataBaseReader;
import local.nix.locations.demo.writer.SolutionWriter;
import local.nix.locations.util.data.Node;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        List<Node> graph = new DataBaseReader(ConnectionUtil.getConnection()).readGraph();
        Map<Node, List<Node>> problems = DataBaseReader.readProblems();
        new SolutionWriter(ConnectionUtil.getConnection()).writeSolution(graph, problems);
    }
}
