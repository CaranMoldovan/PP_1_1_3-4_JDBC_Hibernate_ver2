package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();


    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "age TINYINT);");

        } catch (SQLException e) {
            handleException("Error creating Users table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS User;");
        } catch (SQLException e) {
            handleException("Error dropping Users table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleException("Error saving user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM User WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleException("Error removing user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        User user;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User");
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                user = new User(id, name, lastName, age);
                userList.add(user);
            }

        } catch (SQLException e) {
            handleException("Error retrieving users", e);
        }

        return userList;
    }


    @Override
    public void cleanUsersTable() {
        try (
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE User");
        } catch (SQLException e) {
            handleException("Error cleaning Users table", e);
        }
    }

    private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}