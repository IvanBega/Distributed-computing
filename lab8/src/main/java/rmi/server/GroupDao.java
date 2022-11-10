package rmi.server;

import rmi.common.IGroupDao;
import rmi.common.Group;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupDao extends UnicastRemoteObject implements IGroupDao {
    private final Connection connection;
    public GroupDao(Connection connection) throws RemoteException {
        super();
        this.connection = connection;
    }
    public void addGroup(Group group) throws RemoteException{
        String sql = "INSERT INTO DEPARTMENT" +
                " VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, group.getId());
            statement.setString(2, group.getName());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGroup(Group group) throws RemoteException{
        String sql = "UPDATE DEPARTMENT" +
                " SET NAME = ?" +
                " WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            if (statement.executeUpdate() == 0)
                throw new IllegalArgumentException("No group with ID " + group.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteGroup(int id) throws RemoteException{
        // ON DELETE CASCADE
        String sql = "DELETE FROM DEPARTMENT" +
                " WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() == 0)
                throw new IllegalArgumentException("No group with ID " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Group> getAllGroups() throws RemoteException{
        String sql = "SELECT * FROM DEPARTMENT";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            ArrayList<Group> resultList = new ArrayList<>();
            while (result.next()) {
                int id = result.getInt("ID");
                String name = result.getString("NAME");
                resultList.add(new Group(id, name));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
