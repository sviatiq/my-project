package com.GlobalLogic.dataAccessObject;

import com.GlobalLogic.config.Config;
import com.GlobalLogic.domain.*;
import com.GlobalLogic.domain.student.StudentOrder;
import com.GlobalLogic.domain.student.StudentOrderStatus;
import java.sql.*;
import java.time.LocalDateTime;


public class StudentDaoImpl implements StudentOrderDao {

    public static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "            student_order_status, student_order_date, husband_sur_name, " +
            "            husband_given_name, husband_patronymic, husband_date_of_birth, " +
            "            husband_passport_seria, husband_passport_number, husband_passport_date, " +
            "            husband_passport_office_id, husband_post_index, husband_street_code, " +
            "            husband_building, husband_extension, husband_apartment, wife_sur_name, " +
            "            wife_given_name, wife_patronymic, wife_date_of_birth, wife_passport_seria, " +
            "            wife_passport_number, wife_passport_date, wife_passport_office_id, " +
            "            wife_post_index, wife_street_code, wife_building, wife_extension, " +
            "            wife_appartment, certificate_id, register_office_id, marriage_date)" +
            "    VALUES (?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, ?, " +
            "            ?, ?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, ?, " +
            "            ?, ?, ?, ?);";

    public static final String INSERT_CHILD = "INSERT INTO public.jc_student_child(" +
            "            student_child_id, student_order_id, child_sur_name, child_given_name, " +
            "            child_patronymic, child_date_of_birth, child_certificate_number, " +
            "            child_certificate_date, child_register_office_id, child_post_index, " +
            "            child_street_code, child_building, child_extension, child_apartment)" +
            "    VALUES (?, ?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, " +
            "            ?, ?, ?, ?);";

    // TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
        return connection;
    }

    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DaoException {
        Long result = -1L;
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})){

            connection.setAutoCommit(false);
            try {
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                setParametersForAdults(statement, 3, studentOrder.getHusband());
                setParametersForAdults(statement, 16, studentOrder.getWife());


                statement.setString(29, studentOrder.getMarriageCertificateId());
                statement.setLong(30, studentOrder.getMarriageOffice().getOfficeId());
                statement.setDate(31, java.sql.Date.valueOf(studentOrder.getMarriageDate()));

                statement.executeUpdate();

                ResultSet generateKeysResultSet = statement.getGeneratedKeys();
                if (generateKeysResultSet.next()) {
                    result = generateKeysResultSet.getLong(1);
                }
                generateKeysResultSet.close();

                saveChildren(connection, studentOrder, result);
                connection.commit();
            }catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }
        }catch(SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    private void saveChildren(Connection connection, StudentOrder studentOrder, Long studentOrderId) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : studentOrder.getChildren()) {
                System.out.println("Шаг 1");
                statement.setLong(1, studentOrderId);
                System.out.println("Шаг 2");
                setParametersForChild(statement, child);
                System.out.println("Шаг 3");
                statement.executeUpdate();
            }
        }
    }
    private void setParametersForAdults(PreparedStatement statement, int start, Adult adult) throws SQLException {
        System.out.println("Шаг 1");
        setParametersForPerson(statement, start, adult);
        System.out.println("Шаг 7");
        statement.setString(start+4, adult.getPassportSeria());
        System.out.println("Шаг 8");
        statement.setString(start+5, adult.getPassportNumber());
        System.out.println("Шаг 9");
        statement.setDate(start+6, Date.valueOf(adult.getIssueDate()));
        System.out.println("Шаг 10");
        statement.setLong(start+7, adult.getIssueDepartment().getOfficeId());
        System.out.println("Шаг 11");
        setParametersForAddress(statement, start+8, adult);
    }
    private void setParametersForChild(PreparedStatement statement, Child child) throws SQLException{
        System.out.println("Шаг 18");
        setParametersForPerson(statement, 2, child);
        System.out.println("Шаг 19");
        statement.setString(6, child.getCertification());
        System.out.println("Шаг 20");
        statement.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        System.out.println("Шаг 21");
        statement.setLong(8, child.getIssueDepartment().getOfficeId());
        System.out.println("Шаг 22");
        setParametersForAddress(statement, 9, child);
    }
    private void setParametersForPerson(PreparedStatement statement, int start, Person person ) throws SQLException {
        System.out.println("Шаг 2");
        statement.setString(start, person.getSurname());
        System.out.println("Шаг 3");
        statement.setString(start+1, person.getName());
        System.out.println("Шаг 4");
        statement.setString(start+2, person.getPatronymic());
        System.out.println("Шаг 5");
        statement.setDate(start+3, java.sql.Date.valueOf(person.getDateOfBirth()));
        System.out.println("Шаг 6");
    }
    private void setParametersForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        System.out.println("Шаг 12");
        Address adult_address = person.getAddress();
        System.out.println("Шаг 13");
        statement.setString(start, adult_address.getPostCode());
        System.out.println("Шаг 14");
        statement.setLong(start+1, adult_address.getStreet().getStreetCode());
        System.out.println("Шаг 15");
        statement.setString(start+2, adult_address.getBuilding());
        System.out.println("Шаг 16");
        statement.setString(start+3, adult_address.getExtension());
        System.out.println("Шаг 17");
        statement.setString(start+4, adult_address.getAppartment());
    }
}