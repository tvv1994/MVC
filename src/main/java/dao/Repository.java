package dao;

import models.Model;

import java.sql.*;
import java.util.Collections;
import java.util.Optional;

public class Repository {
    private Connection connection;
    public static final String DB_URL = "jdbc:h2:" + Repository.class.getClassLoader().getResource("stockExchange").getPath();
    public static final String CREATE_TABLE = "create table IF NOT EXISTS SOMETHING (Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), number INT, tags VARCHAR(150), date DATE);";
    private static final String INSERT_SQL = "INSERT INTO SOMETHING (name, number, tags, date) values (?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM SOMETHING WHERE id like ?";

    public Repository() {
        try{
            connection = DriverManager.getConnection(DB_URL);
            try(Statement stH2 = connection.createStatement()){
                stH2.execute(CREATE_TABLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Model model){
        try(PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            stmt.setString(1, model.getName());
            stmt.setInt(2, model.getNumber());
            stmt.setString(3, String.join(", ", model.getListTag()));
            stmt.setDate(4, new Date(model.getDate().getTime()));
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Model> getByDate(Date date1, Date date2){


        String SELECT_BY_DATE_SQL = String.format("SELECT * FROM SOMETHING WHERE date >= %s AND date <= %s", date1, date2);
        try(PreparedStatement stmt = connection.prepareStatement(SELECT_BY_DATE_SQL)){

            try(ResultSet resultSet = stmt.executeQuery()){
                resultSet.next();
                return Optional.of(new Model(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        Collections.singletonList(resultSet.getString(4)),
                        resultSet.getDate(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void delete(int id){
        try(PreparedStatement stmt = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("REMOVE-No data is available.");
        }
    }
}
