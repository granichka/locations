package local.nix.locations.demo.writer;

import local.nix.locations.util.data.Node;
import local.nix.locations.util.djkstra.GraphHandler;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class SolutionWriter {

    private static Connection connection;

    public SolutionWriter(Connection connection) {
        this.connection = connection;
    }

    public  void writeSolution(List<Node> graph, Map<Node, List<Node>> problems) {
        for(Node location: problems.keySet()) {
            GraphHandler.computePaths(graph, location);
            List<Node> currentTargetList = problems.get(location);
                for(Node target: currentTargetList) {
                    try (PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM problem WHERE from_id = (?) AND to_id = (?)");
                         PreparedStatement statement2 = connection.prepareStatement("INSERT INTO solution (problem_id, cost) VALUES ((?), (?))");) {

                        statement1.setInt(1, location.index);
                        statement1.setInt(2, target.index);
                        ResultSet rs = statement1.executeQuery();
                        int problemId = -1;
                        while (rs.next()) {
                            problemId = rs.getInt("id");
                        }
                        if(problemId == -1) {
                            throw new IllegalArgumentException("некорректное id из таблицы problem");
                        }

                        statement2.setInt(1, problemId);
                        statement2.setInt(2, (int) target.minDistance);
                        statement2.execute();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }



                }

        }

    }
}
