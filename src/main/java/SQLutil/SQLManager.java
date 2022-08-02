package SQLutil;

import data.City;
import exception.AuthorizerException;
import exception.CommandException;
import exception.UsernameIsTakenException;
import util.User;

import java.sql.*;
import java.util.ArrayList;

public class SQLManager {
    private final static SQLConnect SQL_CONNECT = new SQLConnect();
    private final static Connection CONNECTION = SQL_CONNECT.connect();

    public static void init() {
        try {
            new SQLInitiator(CONNECTION).init();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void close() {
        try {
            CONNECTION.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean newUser(User user) throws SQLException, UsernameIsTakenException{
        PreparedStatement ps = CONNECTION.prepareStatement(StatementsEnum.GET_EXIST_USERS.getStatement());
        ps.setString(1, user.getUsername());
        ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                throw new UsernameIsTakenException("Пользователь с таким именем уже существует(");
            }
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(StatementsEnum.CREATE_AN_ACCOUNT.getStatement());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setBytes(2, User.strToByte(user.getPassword()));
            preparedStatement.executeUpdate();

            return true;
    }
    public static boolean singIn(User user) throws SQLException, AuthorizerException{
        PreparedStatement ps = CONNECTION.prepareStatement(StatementsEnum.SING_IN.getStatement());
        ps.setString(1, user.getUsername());
        ps.setBytes(2, User.strToByte(user.getPassword()));
        ResultSet resultSet = ps.executeQuery();
        if(!resultSet.next()) {
            throw new AuthorizerException("Неправильное имя пользователя или пароль!");
        }
        return true;
    }
    public static boolean add(City city, User user) throws SQLException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(StatementsEnum.ADD.getStatement());
        preparedStatement.setInt(1, (int) city.getId());
        preparedStatement.setString(2, city.getName());
        preparedStatement.setFloat(3, city.getCoordinates().getX());
        preparedStatement.setDouble(4, city.getCoordinates().getY());
        preparedStatement.setDate(5, Date.valueOf(city.getCreationDate()));
        preparedStatement.setDouble(6 , city.getArea());
        preparedStatement.setInt(7, city.getPopulation());
        preparedStatement.setDouble(8, city.getMetersAboveSeaLevel());
        preparedStatement.setFloat(9, city.getTimezone());
        preparedStatement.setBoolean(10, city.isCapital());
        preparedStatement.setString(11, city.getGovernment().getUpReg());
        preparedStatement.setString(12, city.getGovernor().getName());
        preparedStatement.setString(13, user.getUsername());
        preparedStatement.executeUpdate();
        return true;
    }
    public static boolean clear(User user) throws SQLException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(StatementsEnum.CLEAR.getStatement());
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.executeUpdate();
        return false;
    }

    public static boolean removeById(User user, long id) throws SQLException, CommandException {
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(StatementsEnum.FIND_CITY_BY_ID.getStatement());
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setInt(2, (int) id);
        ResultSet rs = preparedStatement.executeQuery();
        if (!rs.next()) return false;
        PreparedStatement ps = CONNECTION.prepareStatement(StatementsEnum.REMOVE_BY_ID.getStatement());
        ps.setString(1, user.getUsername());
        ps.setInt(2, (int) id);
        ps.executeUpdate();
        return true;
    }
    public  static boolean removeGreater(User user, City city) throws SQLException {
        PreparedStatement ps = CONNECTION.prepareStatement(StatementsEnum.REMOVE_GREATER.getStatement());
        ps.setFloat(1, city.getCoordinates().getX());
        ps.setDouble(2, city.getCoordinates().getY());
        ps.setString(3, user.getUsername());
        ps.executeUpdate();
        return true;
    }
    public static ArrayList<ArrayList<String>> parseSQL() throws SQLException {
        ArrayList<ArrayList<String>> arrayListArrayList = new ArrayList<>();
        Statement st = CONNECTION.createStatement();
        ResultSet rs = st.executeQuery(StatementsEnum.ALL_CITY.getStatement());
        while (rs.next()) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(rs.getString("id"));
            arrayList.add(rs.getString("name"));
            arrayList.add(rs.getString("x"));
            arrayList.add(rs.getString("y"));
            arrayList.add(rs.getString("creationDate"));
            arrayList.add(rs.getString("area"));
            arrayList.add(rs.getString("population"));
            arrayList.add(rs.getString("metersAboveSeaLevel"));
            arrayList.add(rs.getString("timezone"));
            arrayList.add(rs.getString("capital"));
            arrayList.add(rs.getString("government"));
            arrayList.add(rs.getString("governor"));
            arrayListArrayList.add(arrayList);
        }
        return  arrayListArrayList;
    }
    public static int getNextId() {
        try {
            Statement st = CONNECTION.createStatement();
            ResultSet rs = st.executeQuery(StatementsEnum.GET_NEXT_ID.getStatement());
            if (rs.next()) {
                return rs.getInt("nextval");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}

