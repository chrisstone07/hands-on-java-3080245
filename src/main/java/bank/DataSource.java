package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String _dbFile = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(_dbFile);
      System.out.println("Connected!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomerDetails(String username) {
    String sqlStmnt = "select * from Customers where username = ?";
    Customer customer = null;
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sqlStmnt)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery();) {
        customer = new Customer(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static Account getAccountDetails(int id) {
    String sqlStmnt = "select * from Accounts where id = ?";
    Account account = null;
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sqlStmnt)) {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery();) {
        account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getFloat("id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return account;
  }

  public static void main(String[] args) {
    Customer customer = getCustomerDetails("bpioch15@microsoft.com");
    String customerName = customer.getName();
    System.out.println(customerName);
    Account account = getAccountDetails(10557);
    Float accountBal = account.getBalance();
    System.out.println(accountBal);
  }
}
