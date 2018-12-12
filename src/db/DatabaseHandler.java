package db;

import db.Base.Mysql;
import config.Const;
import model.ObjMessage;
import objects.Doctor;
import objects.OrderTicket;
import objects.Service;
import objects.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHandler extends Mysql {

    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static ResultSet getUser(User user) {
        try {
            String sql = "SELECT * FROM " + Const.USERS_TABLE + " WHERE email = ? AND password = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static User registerUser(User user) {
        try {
            String sql = "SELECT * FROM " + Const.USERS_TABLE + " WHERE email = ? AND password = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setError("Такой пользователь уже есть!");
            } else {
                sql = "INSERT INTO " + Const.USERS_TABLE + "( " + Const.USERS_ROLE_ID + ", " + Const.USERS_NAME + ", " + Const.USERS_SURNAME + ", " + Const.USERS_EMAIL + ", " + Const.USERS_PASSWORD + ") " +
                        " VALUES ( ? , ? , ? , ? , ? ) ";
                preparedStatement = connect().prepareStatement(sql);
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getPassword());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void addDoctor(Doctor doctor) {
        try {
            String sql = "SELECT * FROM " + Const.DOCTORS_TABLE + " WHERE email = ? AND password = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, doctor.getEmail());
            preparedStatement.setString(2, doctor.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doctor.setError("Такой доктор уже есть!");
            } else {
                insertIntoDoctorTable(doctor);
                insertIntoUsersTable(doctor);
                insertIntoScheduleTableDoctor(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoScheduleTableDoctor(Doctor doctor) throws SQLException {
        String sql = "SELECT * FROM " + Const.DOCTORS_TABLE + " WHERE email = ? AND password = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setString(1, doctor.getEmail());
        preparedStatement.setString(2, doctor.getPassword());
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int doctor_id = resultSet.getInt(Const.USERS_ID);
            List<String> week = new ArrayList<String>(Arrays.asList(
                    "понедельник",
                    "вторник",
                    "среда",
                    "четверг",
                    "пятница"
            ));
            for(String day : week){

                sql = "INSERT INTO " + Const.SCHEDULE_TABLE + "( " + Const.SCHEDULE_NAME_DAY + ", " + Const.SCHEDULE_COUNT_TICKET + ", " + Const.ORDER_DOCTOR_ID+") " +
                        " VALUES ( ? , ? , ? ) ";
                preparedStatement = connect().prepareStatement(sql);
                preparedStatement.setString(1, day);
                preparedStatement.setInt(2, 24);
                preparedStatement.setInt(3, doctor_id);

                preparedStatement.executeUpdate();
            }
        }
    }

    private static void insertIntoUsersTable(Doctor doctor) throws SQLException {
        String sql;
        sql = "INSERT INTO " + Const.USERS_TABLE + "( " + Const.USERS_ROLE_ID + ", " + Const.USERS_NAME + ", " + Const.USERS_SURNAME + ", " + Const.USERS_EMAIL + ", " + Const.USERS_PASSWORD + ") " +
                " VALUES ( ? , ? , ? , ? , ? ) ";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, doctor.getNameDoctor());
        preparedStatement.setString(3, doctor.getSurnameDoctor());
        preparedStatement.setString(4, doctor.getEmail());
        preparedStatement.setString(5, doctor.getPassword());

        preparedStatement.executeUpdate();
    }

    public static void insertIntoDoctorTable(Doctor doctor) throws SQLException {
        String sql;
        sql = "INSERT INTO " + Const.DOCTORS_TABLE + "( " + Const.USERS_ROLE_ID + ", " + Const.USERS_NAME + ", " + Const.USERS_SURNAME + ", " + Const.USERS_SPECIALTY + ", " + Const.USERS_EMAIL + ", " + Const.USERS_PASSWORD + ") " +
                " VALUES ( ? , ? , ? , ? , ?, ? ) ";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, doctor.getNameDoctor());
        preparedStatement.setString(3, doctor.getSurnameDoctor());
        preparedStatement.setString(4, doctor.getSpecialty());
        preparedStatement.setString(5, doctor.getEmail());
        preparedStatement.setString(6, doctor.getPassword());

        preparedStatement.executeUpdate();
    }

    public static void editDoctor(Doctor doctor) {
        try {
            editDoctorTable(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editDoctorTable(Doctor doctor) throws SQLException {
        String sql = "UPDATE " + Const.DOCTORS_TABLE + " SET " + Const.USERS_NAME + " = ?, " + Const.USERS_SURNAME + " = ?, " + Const.USERS_SPECIALTY + " = ?, " + Const.USERS_EMAIL + " = ?, " + Const.USERS_PASSWORD + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setString(1, doctor.getNameDoctor());
        preparedStatement.setString(2, doctor.getSurnameDoctor());
        preparedStatement.setString(3, doctor.getSpecialty());
        preparedStatement.setString(4, doctor.getEmail());
        preparedStatement.setString(5, doctor.getPassword());
        preparedStatement.setInt(6, doctor.getId());

        preparedStatement.executeUpdate();
    }

    public static void deleteDoctor(Doctor doctor) {
        try {
            String sql= "DELETE FROM "+ Const.SCHEDULE_TABLE + " WHERE  doctor_id = ?";

            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, doctor.getId());

            preparedStatement.executeUpdate();

            sql = "DELETE FROM " + Const.DOCTORS_TABLE + " WHERE id = ? ";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, doctor.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Doctor> viewDoctor() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        Doctor doctor;
        try {
            String sql = "SELECT * FROM " + Const.DOCTORS_TABLE;

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctor = new Doctor();
                doctor.setNameDoctor(resultSet.getString(Const.USERS_NAME));
                doctor.setSurnameDoctor(resultSet.getString(Const.USERS_SURNAME));
                doctor.setRole(resultSet.getInt(Const.USERS_ROLE_ID));
                doctor.setSpecialty(resultSet.getString(Const.USERS_SPECIALTY));
                doctor.setEmail(resultSet.getString(Const.USERS_EMAIL));
                doctor.setPassword(resultSet.getString(Const.USERS_PASSWORD));
                doctor.setId(resultSet.getInt(Const.USERS_ID));

                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public static ArrayList<Service> viewServices() {
        ArrayList<Service> services = new ArrayList<>();
        Service service;
        try {
            String sql = "SELECT " + Const.SERVICES_TABLE + ".id, " + Const.SERVICES_TABLE + ".name, " + Const.SERVICES_TABLE + ".doctor_id,  " + Const.SERVICES_TABLE + ".price," + Const.DOCTORS_TABLE+ ".name FROM " + Const.SERVICES_TABLE + " JOIN " + Const.DOCTORS_TABLE + " ON " + Const.SERVICES_TABLE + ".doctor_id = " + Const.DOCTORS_TABLE + ".id";

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                service = new Service();
                service.setNameServices(resultSet.getString(Const.USERS_NAME));
                service.setPriceServices(resultSet.getString(Const.SERVICES_PRICE));
                service.setNameDoctor(resultSet.getString(Const.DOCTORS_TABLE+"."+Const.USERS_NAME));
                service.setId(resultSet.getInt(Const.USERS_ID));
                service.setDoctorID(resultSet.getInt(Const.ORDER_DOCTOR_ID));

                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return services;
    }

    public static ArrayList<User> viewUser() {
        ArrayList<User> users = new ArrayList<>();
        User user;
        try {
            String sql = "SELECT * FROM " + Const.USERS_TABLE;

            preparedStatement = connect().prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setFirstName(resultSet.getString(Const.USERS_NAME));
                user.setLastName(resultSet.getString(Const.USERS_SURNAME));
                user.setRole(resultSet.getInt(Const.USERS_ROLE_ID));
                user.setEmail(resultSet.getString(Const.USERS_EMAIL));
                user.setPassword(resultSet.getString(Const.USERS_PASSWORD));
                user.setId(resultSet.getInt(Const.USERS_ID));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ObjMessage checkout(ObjMessage objMessage) {
        try {
            String sql = "SELECT * FROM " + Const.SCHEDULE_TABLE + " WHERE " + Const.SCHEDULE_NAME_DAY + " = ? AND " + Const.SCHEDULE_DOCTOR_ID + " = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, objMessage.getDoctorObject().getDay());
            preparedStatement.setInt(2, objMessage.getDoctorObject().getId());

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if (resultSet.getInt(Const.SCHEDULE_COUNT_TICKET) == 0) {
                    objMessage.getOrderTicket().setFlag(false);
                    return objMessage;
                } else {
                    int countTicket = resultSet.getInt(Const.SCHEDULE_COUNT_TICKET);
                    int idSelectedDay = resultSet.getInt(Const.USERS_ID);

                    String update = "UPDATE " + Const.SCHEDULE_TABLE + " SET " + Const.SCHEDULE_COUNT_TICKET + " = ? WHERE id = ?";
                    preparedStatement = connect().prepareStatement(update);
                    preparedStatement.setInt(1, (countTicket-1));
                    preparedStatement.setInt(2, idSelectedDay);

                    preparedStatement.executeUpdate();
                    OrderTicket orderTicket = new OrderTicket();
                    System.out.println(Const.SCHEDULE_COUNT_TICKET);

                    int countSession = 24 - countTicket;
                    System.out.println(countSession);
                    int timeAll = countSession * 20;
                    int startTime = 8;
                    int hours = timeAll/60;
                    int partOne = startTime + hours;
                    int partTwo = timeAll - hours*60;

                    sql = "INSERT INTO " + Const.ORDER_COUPONE + "( " + Const.ORDER_DAY + ", " + Const.ORDER_TIME + ", " + Const.ORDER_USER_ID + ", " + Const.ORDER_DOCTOR_ID +", " + Const.ORDER_NUMBER_TICKET + ") " +
                            " VALUES ( ? , ? , ? , ? , ?) ";
                    preparedStatement = connect().prepareStatement(sql);
                    preparedStatement.setString(1, objMessage.getDoctorObject().getDay());
                    preparedStatement.setString(2, partOne+" : "+partTwo);
                    preparedStatement.setInt(3, objMessage.getUserObject().getId());
                    preparedStatement.setInt(4, objMessage.getDoctorObject().getId());
                    preparedStatement.setInt(5, (countSession+1));

                    preparedStatement.executeUpdate();

                    saveInformationInFile(objMessage.getDoctorObject().getDay(), partOne+" : "+partTwo, objMessage.getUserObject().getFirstName(), objMessage.getDoctorObject().getNameDoctor(), objMessage.getDoctorObject().getSurnameDoctor(), objMessage.getDoctorObject().getSpecialty());

                    String selectedTicket = "SELECT " + Const.ORDER_COUPONE + ".day, " + Const.ORDER_COUPONE + ".time, " + Const.ORDER_COUPONE + ".user_id, " + Const.USERS_TABLE + ".name, " + Const.DOCTORS_TABLE + ".name, " + Const.DOCTORS_TABLE + ".surname, " + Const.DOCTORS_TABLE + ".specialty, " + Const.ORDER_COUPONE + ".numberTicket FROM " + Const.ORDER_COUPONE + " JOIN " + Const.USERS_TABLE + " ON " + Const.ORDER_COUPONE + ".user_id = " + Const.USERS_TABLE + ".id JOIN " + Const.DOCTORS_TABLE + " ON " + Const.ORDER_COUPONE + ".doctor_id = " + Const.DOCTORS_TABLE + ".id  WHERE " + Const.ORDER_COUPONE + ".user_id = ?";

                    preparedStatement = connect().prepareStatement(selectedTicket);
                    preparedStatement.setInt(1, objMessage.getUserObject().getId());
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        orderTicket.setDay(resultSet.getString(Const.ORDER_DAY));
                        orderTicket.setTime(resultSet.getString(Const.ORDER_TIME));
                        orderTicket.setUser(resultSet.getString(Const.USERS_TABLE+"."+Const.USERS_NAME));
                        orderTicket.setDoctorName(resultSet.getString(Const.DOCTORS_TABLE+"."+Const.USERS_NAME));
                        orderTicket.setDoctorSurname(resultSet.getString(Const.USERS_SURNAME));
                        orderTicket.setDoctorSpecialty(resultSet.getString(Const.USERS_SPECIALTY));
                        orderTicket.setNumberTicket(resultSet.getInt(Const.ORDER_NUMBER_TICKET));
                        orderTicket.setUserID(resultSet.getInt(Const.ORDER_USER_ID));
                    }
                    orderTicket.setFlag(true);
                    objMessage.setTicket(orderTicket);

                    return objMessage;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objMessage;
    }

    private static void saveInformationInFile(String day, String time, String firstName, String nameDoctor, String surnameDoctor, String specialty) {
        int count = 0;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("informationOrder.txt")))
        {
            count++;
            String out = count+" день:" + day + ",\nвремя: " + time + ",\nдоктор: " + nameDoctor + " " + surnameDoctor + " (" + specialty + ")\n";
            writer.append(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<OrderTicket> getTicket(ObjMessage objMessage) {
        User user = objMessage.getUserObject();
        ArrayList<OrderTicket> orderTickets = new ArrayList<OrderTicket>();
        OrderTicket orderTicket;
        try {

            String sql = "SELECT " + Const.ORDER_COUPONE + ".day, " + Const.ORDER_COUPONE + ".time, " + Const.USERS_TABLE + ".name, " + Const.DOCTORS_TABLE + ".name, " + Const.DOCTORS_TABLE + ".surname, " + Const.DOCTORS_TABLE + ".specialty, " + Const.ORDER_COUPONE + ".numberTicket FROM " + Const.ORDER_COUPONE + " JOIN " + Const.USERS_TABLE + " ON " + Const.ORDER_COUPONE + ".user_id = " + Const.USERS_TABLE + ".id JOIN " + Const.DOCTORS_TABLE + " ON " + Const.ORDER_COUPONE + ".doctor_id = " + Const.DOCTORS_TABLE + ".id  WHERE " + Const.ORDER_COUPONE + ".user_id = ?";

            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderTicket = new OrderTicket();
                orderTicket.setDay(resultSet.getString(Const.ORDER_DAY));
                orderTicket.setTime(resultSet.getString(Const.ORDER_TIME));
                orderTicket.setUser(resultSet.getString(Const.USERS_TABLE+"."+Const.USERS_NAME));
                orderTicket.setDoctorName(resultSet.getString(Const.DOCTORS_TABLE+"."+Const.USERS_NAME));
                orderTicket.setDoctorSurname(resultSet.getString(Const.USERS_SURNAME));
                orderTicket.setDoctorSpecialty(resultSet.getString(Const.USERS_SPECIALTY));
                orderTicket.setNumberTicket(resultSet.getInt(Const.ORDER_NUMBER_TICKET));

                orderTickets.add(orderTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderTickets;
    }

    public static void updateAll() {

        try {
            String sql = "DELETE FROM " + Const.ORDER_COUPONE;
            preparedStatement = connect().prepareStatement(sql);

            preparedStatement.executeUpdate();

            sql = "UPDATE " + Const.SCHEDULE_TABLE + " SET " + Const.SCHEDULE_COUNT_TICKET + " = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, 24);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<OrderTicket> viewTicket(ObjMessage objMessage) {
        Doctor doctor = new Doctor();
        ArrayList<OrderTicket> orderTickets = new ArrayList<OrderTicket>();
        OrderTicket orderTicket = null;

        try {
            String sql = "SELECT * FROM " + Const.DOCTORS_TABLE + " WHERE email = ? AND password = ?";

            preparedStatement = connect().prepareStatement(sql);

            preparedStatement.setString(1, objMessage.getUserObject().getEmail());
            preparedStatement.setString(2, objMessage.getUserObject().getPassword());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                doctor.setId(resultSet.getInt(Const.USERS_ID));
            }

            sql = "SELECT " + Const.ORDER_COUPONE + ".day, " + Const.ORDER_COUPONE + ".time, " + Const.USERS_TABLE + ".name FROM " + Const.ORDER_COUPONE + " JOIN " + Const.USERS_TABLE + " ON " + Const.ORDER_COUPONE + ".user_id = " + Const.USERS_TABLE + ".id WHERE " + Const.ORDER_COUPONE + ".doctor_id = ?";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, doctor.getId());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderTicket = new OrderTicket();
                orderTicket.setDay(resultSet.getString(Const.ORDER_DAY));
                orderTicket.setTime(resultSet.getString(Const.ORDER_TIME));
                orderTicket.setUser(resultSet.getString(Const.USERS_TABLE+"."+Const.USERS_NAME));
                orderTickets.add(orderTicket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderTickets;
    }

    public static void blockUser(User user) throws SQLException {
        String sql;
        sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_ROLE_ID + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, Const.BLOCK_USERS);
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
    }

    public static void unlockUser(User user) throws SQLException {
        String sql;
        sql = "UPDATE " + Const.USERS_TABLE + " SET " + Const.USERS_ROLE_ID + " = ? WHERE id = ?";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setInt(1, Const.UNLOCK_USERS);
        preparedStatement.setInt(2, user.getId());

        preparedStatement.executeUpdate();
    }

    public static void addServices(ObjMessage objMessage) throws SQLException {
        String sql;
        sql = "INSERT INTO " + Const.SERVICES_TABLE + "( " + Const.USERS_NAME + ", " + Const.SERVICES_PRICE+ ", " + Const.ORDER_DOCTOR_ID + ") " +
                " VALUES ( ? , ? , ? ) ";
        preparedStatement = connect().prepareStatement(sql);
        preparedStatement.setString(1, objMessage.getServiceObject().getNameServices());
        preparedStatement.setString(2, objMessage.getServiceObject().getPriceServices());
        preparedStatement.setInt(3, objMessage.getDoctorObject().getId());

        preparedStatement.executeUpdate();
    }

    public static void deleteServices(ObjMessage objMessage) throws SQLException {
        try {
            String sql = "DELETE FROM " + Const.PAID_TABLE +" WHERE services_id = ?";

            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, objMessage.getServiceObject().getId());

            preparedStatement.executeUpdate();

            sql= "DELETE FROM "+ Const.SERVICES_TABLE + " WHERE  doctor_id = ?";

            preparedStatement = connect().prepareStatement(sql);
            System.out.println(objMessage.getDoctorObject().getId());
            preparedStatement.setInt(1, objMessage.getDoctorObject().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void orderService(ObjMessage objMessage) {
        try {
            System.out.println("я тут");
            String sql;
            sql = "INSERT INTO " + Const.PAID_SERVICES + "( " + Const.SERVICES_ID+ ", " + Const.ORDER_DAY + ", " + Const.ORDER_TIME + ", " + Const.ORDER_USER_ID + ") " +
                    " VALUES ( ? , ? , ? , ? ) ";
            preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setInt(1, objMessage.getServiceObject().getId());
            preparedStatement.setString(2, objMessage.getServiceObject().getDay());
            preparedStatement.setString(3, objMessage.getServiceObject().getTime());
            preparedStatement.setInt(4, objMessage.getUserObject().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

