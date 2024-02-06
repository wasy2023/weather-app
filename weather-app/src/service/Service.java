package service;

import domain.weather;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public int hoursof(String prep){
        List<weather> reports = getAll().stream().filter(weather -> weather.getDescription().contains(prep)).collect(Collectors.toList());
        int result=0;
        for(weather wee : reports){
            result+=(wee.getEndtime()- wee.getStarttime());
        }
        return result;
    }

    public List<weather> filterBy(String description){
        List<weather> reports = getAll();
        return reports.stream().filter(weather -> Objects.equals(weather.getDescription().split(",")[0], description)).collect(Collectors.toList());
    }

    public List<String> descriptions(){
        List<String> descriptions = new ArrayList<>();
        try (Connection connection = repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM weather ");) {
            while (resultSet.next()) {
                String[] fullDescrp = resultSet.getString("description").split(",");
                if(!descriptions.contains(fullDescrp[0]))
                    descriptions.add(fullDescrp[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptions;
    }
    public List<weather> getAll() {
        List<weather> reports = new ArrayList<>();
        try (Connection connection = repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM weather ");) {
            while (resultSet.next()) {
                reports.add(new weather(resultSet.getInt("starthour"),resultSet.getInt("endhour"),resultSet.getInt("temperature"),resultSet.getInt("precipitation"),resultSet.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports.stream().sorted(Comparator.comparingInt(weather::getStarttime)).collect(Collectors.toList());
    }
    public weather getReport(String id) {
        try(Connection connection = repo.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM documents ");) {
            while (resultSet.next()) {
                //to be completed
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void saveObject(Object obj){
        String sql = "INSERT INTO Car (fuelType,activeStatus,model) VALUES (?,?,?)";
        try(Connection connection = repo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            preparedStatement.executeUpdate();

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void updateInDatabase(weather report){
        String sql = "UPDATE weather SET precipitation=?, description=? WHERE starthour=? AND endhour=?";
        List<weather> reports = getAll();         try(Connection connection = repo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){
                preparedStatement.setInt(1,report.getPrecipitation());
                preparedStatement.setString(2, report.getDescription());
                preparedStatement.setInt(3,report.getStarttime());
                preparedStatement.setInt(4,report.getEndtime());

                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
    }
}