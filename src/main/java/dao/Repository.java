package dao;

import models.Model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Repository {

    private static Repository instance;

    public static Repository getInstance(){
        if(instance == null)
            instance = new Repository();
        return instance;
    }

    private Connection connection;

    private static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:" + Repository.class.getClassLoader().getResource("stockExchange").getPath();
    public static final String CREATE_TABLE = "create table IF NOT EXISTS SOMETHING (Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), number INT, tags VARCHAR(150), date DATE);";
    private static final String INSERT_SQL = "INSERT INTO SOMETHING (name, number, tags, date) values (?,?,?,?)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM SOMETHING WHERE id like ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM SOMETHING";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM SOMETHING WHERE id LIKE ?";

    public Repository() {
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            try(Statement stH2 = connection.createStatement()){
                stH2.execute(CREATE_TABLE);
            }
        } catch (SQLException | ClassNotFoundException e) {
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

    public List<Model> getAll() {
        try(PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_SQL)){
            try(ResultSet resultSet = stmt.executeQuery()){
                List<Model> result = new ArrayList<>();
                while (resultSet.next()){
                    Model model = new Model(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            Arrays.asList(resultSet.getString(4).split(", ")),
                            resultSet.getDate(5)
                    );
                    result.add(model);
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

    public Optional<Model> getById(Integer id) {
        try(PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setInt(1, id);
            try(ResultSet resultSet = stmt.executeQuery()){
                Model model = null;
                while (resultSet.next()){
                    model = new Model(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            Arrays.asList(resultSet.getString(4).split(", ")),
                            resultSet.getDate(5)
                    );
                }
                if(model != null)return Optional.of(model);
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
