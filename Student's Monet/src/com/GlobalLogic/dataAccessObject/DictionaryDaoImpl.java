package com.GlobalLogic.dataAccessObject;


import com.GlobalLogic.config.Config;
import com.GlobalLogic.domain.CountryArea;
import com.GlobalLogic.domain.PassportOffice;
import com.GlobalLogic.domain.RegisterOffice;
import com.GlobalLogic.domain.Street;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao{
    private static final String GET_STREET = "SELECT street_code, street_name FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_PASSPORT_OFFICE = "SELECT * FROM jc_passport_office WHERE passport_office_area_id = ?";
    private static final String GET_REGISTER_OFFICE = "SELECT * FROM jc_register_office WHERE register_office_area_id = ?";
    private static final String GET_AREA = "SELECT * FROM jc_country_structure WHERE area_id like ? and area_id <> ?";

    // TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }
    @Override
    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(GET_STREET)){
            statement.setString(1, "%" + pattern + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Street str = new Street(resultSet.getLong("street_code"), resultSet.getString("street_name"));
            result.add(str);
        }
        }catch(SQLException ex){
            throw new DaoException(ex);
            }
        return result;
    }
    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(GET_PASSPORT_OFFICE)){
            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PassportOffice str = new PassportOffice(
                        resultSet.getLong("passport_office_id"),
                        resultSet.getString("passport_office_area_id"),
                        resultSet.getString("passport_office_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException{
        List<RegisterOffice> result = new LinkedList<>();

        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(GET_REGISTER_OFFICE)){
            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RegisterOffice str = new RegisterOffice(
                        resultSet.getLong("register_office_area_id"),
                        resultSet.getString("register_office_area_id"),
                        resultSet.getString("register_office_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    public List<CountryArea> findAreas(String areaId) throws DaoException{
        List<CountryArea> result = new LinkedList<>();

        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(GET_AREA)){

            String param1 = buildParams(areaId);
            String param2 = areaId;


            statement.setString(1, param1);
            statement.setString(2, param2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CountryArea str = new CountryArea(
                        resultSet.getString("area_id"),
                        resultSet.getString("area_name"));
                result.add(str);
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    private String buildParams(String areaId) throws SQLException{
        if(areaId == null || areaId.trim().isEmpty()){
            return "__000000";
        }else if(areaId.endsWith("000000")){
         return areaId.substring(0,2)+"___000";
        }else if(areaId.endsWith("000")){
            return areaId.substring(0,5)+"___";
        }
        throw new SQLException("Invalid parameter 'areaId': " + areaId);
    }
}

