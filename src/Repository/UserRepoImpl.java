package Repository;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepoImpl implements UserRepo{

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        PropertiesLoader.LoadPropertiesFile();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             )
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                userList.add(
                        new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("user_uuid"),
                                resultSet.getString("user_name"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getBoolean("is_deleted"),
                                resultSet.getBoolean("is_verified")
                        )
                );
            }
//            return userList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    private static final String   INSERT_SQL= """
             INSERT INTO users (user_name, user_email, is_deleted, user_password, is_verified, user_uuid)
                                                       VALUES (?,?,?,?,?,?) 
            """;
    @Override
    public void createUser(User user) {
        PropertiesLoader.LoadPropertiesFile();
        try(Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
                )
        {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setBoolean(3, user.getIsDeleted());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.getIsVerified());
            preparedStatement.setString(6, user.getUuid());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow > 0){
                System.out.println("Create user successfully!!!");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }
    private static final String DELETE_SQL = """
           DELETE FROM users WHERE user_id = ?
            """;
    @Override
    public void deleteUser(Integer id) {
        PropertiesLoader.LoadPropertiesFile();
        try(Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
                )
        {
            preparedStatement.setInt(1, id);
            int deletedRow = preparedStatement.executeUpdate();


        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }
    private  final String UPDATE_SQL= """
           UPDATE users SET user_name = ?, user_email = ?, is_deleted = ?, user_password = ?, is_verified = ?, user_uuid = ? WHERE user_id = ?
            """;

    @Override
    public User updateUser(User user) {
        PropertiesLoader.LoadPropertiesFile();
        try(
                Connection connection = DriverManager.getConnection(
                        PropertiesLoader.properties.getProperty("database_url"),
                        PropertiesLoader.properties.getProperty("database_username"),
                        PropertiesLoader.properties.getProperty("database_password"));
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
                ){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setBoolean(3, user.getIsDeleted());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.getIsVerified());
            preparedStatement.setString(6, user.getUuid());
            preparedStatement.setInt(7, user.getId());
            int updateRow = preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }
    private static final String   SEARCH_SQL= """
             SELECT * from users WHERE user_id = ?
            """;

    @Override
    public Optional<User> searchUser(Integer userId) {
        List<User> user = new ArrayList<User>();
        PropertiesLoader.LoadPropertiesFile();
        try(
                Connection connection = DriverManager.getConnection(
                        PropertiesLoader.properties.getProperty("database_url"),
                        PropertiesLoader.properties.getProperty("database_username"),
                        PropertiesLoader.properties.getProperty("database_password"));
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
                ){
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.add(
                        new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("user_uuid"),
                                resultSet.getString("user_name"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                resultSet.getBoolean("is_deleted"),
                                resultSet.getBoolean("is_verified")
                        )
                );
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsersSortedByName() {
        String sql = "SELECT * FROM users ORDER BY user_name";
        List<User> userList = new ArrayList<>();

        PropertiesLoader.LoadPropertiesFile();

        try (Connection connection = DriverManager.getConnection(
                PropertiesLoader.properties.getProperty("database_url"),
                PropertiesLoader.properties.getProperty("database_username"),
                PropertiesLoader.properties.getProperty("database_password"));
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_uuid"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getBoolean("is_deleted"),
                        resultSet.getBoolean("is_verified")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }


}
