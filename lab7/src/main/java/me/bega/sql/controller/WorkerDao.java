package me.bega.sql.controller;

import me.bega.sql.model.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class WorkerDao {
    private final Connection connection;

    public WorkerDao(Connection connection) {
        this.connection = connection;
    }
    public void addWorker(Worker worker) {
        String sql = "INSERT INTO WORKER" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, worker.getId());
            statement.setInt(2, worker.getGroupId());
            statement.setString(3, worker.getName());
            statement.setString(4, worker.getBirthDate().toString());
            statement.setFloat(5, worker.getSalary());
            statement.setBoolean(6, worker.hasScholarship());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteWorker(int id) {
        String sql = "DELETE FROM WORKER" +
                " WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() == 0)
                throw new IllegalArgumentException("No worker with ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWorker(Worker worker) {
        String sql = "UPDATE WORKER" +
                " SET ID_DEP = ?" +
                ", NAME = ?" +
                ", BIRTHDATE = ?" +
                ", SALARY = ?" +
                ", HASBONUS = ?" +
                " WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, worker.getGroupId());
            statement.setString(2, worker.getName());
            statement.setString(3, worker.getBirthDate().toString());
            statement.setFloat(4, worker.getSalary());
            statement.setBoolean(5, worker.hasScholarship());
            statement.setInt(6, worker.getId());
            if (statement.executeUpdate() == 0)
                throw new IllegalArgumentException("No worker with ID " + worker.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Worker> getAllWorkersFromGroup(int groupId) {
        String sql = "SELECT * FROM WORKER" +
                " WHERE ID_DEP = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupId);

            ResultSet result = statement.executeQuery();
            ArrayList<Worker> resultList = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("NAME");
                LocalDate birthDate = LocalDate.parse(result.getString("BIRTHDATE"));
                float salary = result.getFloat("SALARY");
                boolean hasBonus = result.getBoolean("HASBONUS");

                Worker student = new Worker(id, groupId, name, birthDate, hasBonus);
                student.setSalary(salary);
                resultList.add(student);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
