package local.nix.locations.demo.reader;


import local.nix.locations.util.data.Edge;
import local.nix.locations.util.data.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseReader {

    private static Connection connection;
    private static List<Node> graph;

    public DataBaseReader(Connection connection) {
        this.connection = connection;
    }

    public  List<Node> readGraph() {

            graph = readLocations();
            setAdjacencies();
            return graph;


    }

    private static List<Node> readLocations() {
        List<Node> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();){
            ResultSet rs = statement.executeQuery("SELECT id, name FROM location");
            while(rs.next()) {
                String name = rs.getString("name");
                int index = rs.getInt("id");
                result.add(new Node(name, index));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    private static void setAdjacencies() {

        try (Statement statement = connection.createStatement();){
            ResultSet rs = statement.executeQuery("SELECT from_id, to_id, cost FROM route");
            while(rs.next()) {
                    Node from = getNode(rs.getInt("from_id"));
                    Node to = getNode(rs.getInt("to_id"));
                    int cost = rs.getInt("cost");
                    setEdges(from, to, cost);
                    setEdges(to, from, cost);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private static Node getNode(int index) {
        Node result = null;
        for(Node node: graph) {
            if(index == node.index) {
                result = node;
            }
        }

        return result;
    }

    private static void setEdges(Node from, Node to, int cost) {

        List<Edge> edges = from.adjacencies;

        edges.add(new Edge(to.index, cost));

    }

    public static Map<Node, List<Node>> readProblems() {
        Map<Node, List<Node>> result = new HashMap<>();

        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery("SELECT from_id, to_id FROM problem");
            while(rs.next()) {
                Node from = getNode(rs.getInt("from_id"));
                Node to = getNode(rs.getInt("to_id"));
                if(result.containsKey(from)) {
                    result.get(from).add(to);
                } else {
                    List<Node> list = new ArrayList<>();
                    list.add(to);
                    result.put(from, list);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
