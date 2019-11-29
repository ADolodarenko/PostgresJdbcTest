package dav.learn.postgres.test;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class PostgreSqlCommander implements SqlCommander {

  private static final String DB_DRIVER = "org.postgresql.Driver";
  private static final String DB_URL = "jdbc:postgresql://localhost:5433/testdb";
  private static final String DB_USERNAME = "dolodarenko";
  private static final String DB_PASSWORD = "xxx";
  private static final String MESSAGE_OPEN_SUCCESS = "Opened connection.";
  private static final String MESSAGE_OPEN_EXCEPTION = "Couldn't open connection: ";
  private static final String MESSAGE_CLOSE_SUCCESS = "Closed connection.";
  private static final String MESSAGE_CLOSE_EXCEPTION = "Couldn't close connection: ";
  private static final String MESSAGE_CREATE_SUCCESS = "Table created.";
  private static final String MESSAGE_CREATE_EXCEPTION = "Couldn't create table: ";
  private static final String MESSAGE_DROP_SUCCESS = "Table dropped.";
  private static final String MESSAGE_DROP_EXCEPTION = "Couldn't drop table: ";
  private static final String MESSAGE_SELECT_SUCCESS = "Done selection.";
  private static final String MESSAGE_SELECT_EXCEPTION = "Couldn't select from table: ";
  private static final String MESSAGE_INSERT_SUCCESS = "Inserted into table.";
  private static final String MESSAGE_INSERT_EXCEPTION = "Couldn't insert into table: ";
  private static final String MESSAGE_UPDATE_SUCCESS = "Updated salary to Iisus.";
  private static final String MESSAGE_UPDATE_EXCEPTION = "Couldn't update salary to Iisus: ";
  private static final String MESSAGE_DELETE_SUCCESS = "Deleted Petya.";
  private static final String MESSAGE_DELETE_EXCEPTION = "Couldn't delete Petya: ";

  private PrintStream logStream;
  private Connection connection;
  private Statement statement;

  public PostgreSqlCommander() {
    this(null);
  }

  public PostgreSqlCommander(PrintStream logStream) {
    setLogStream(logStream);

    connect();
  }

  @Override
  public void setLogStream(PrintStream stream) {
    if (stream != null)
      this.logStream = stream;
    else
      this.logStream = System.out;
  }

  @Override
  public void connect() {
    disconnect();

    try {
      Class.forName(DB_DRIVER);

      connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
      connection.setAutoCommit(false);

      statement = connection.createStatement();

      logStream.println(MESSAGE_OPEN_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_OPEN_EXCEPTION + e.toString());
    }
  }

  @Override
  public void disconnect() {
    if (statement != null)
      try {
        statement.close();
      } catch (Exception e) {}

    if (connection != null)
      try {
        connection.close();

        logStream.println(MESSAGE_CLOSE_SUCCESS);
      } catch (Exception e) {
        logStream.println(MESSAGE_CLOSE_EXCEPTION + e.toString());
      }
  }

  @Override
  public void createTable() {

    String sql = "create table employee(" +
      "id serial primary key not null," +
      "name varchar(100) not null," +
      "joint_date date not null," +
      "salary real not null);";

    try {
      statement.executeUpdate(sql);
      connection.commit();

      logStream.println(MESSAGE_CREATE_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_CREATE_EXCEPTION + e.toString());
    }
  }

  @Override
  public void dropTable() {
    String sql = "drop table employee;";

    try {
      statement.executeUpdate(sql);
      connection.commit();

      logStream.println(MESSAGE_DROP_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_DROP_EXCEPTION + e.toString());
    }
  }

  @Override
  public void insert() {
    try {
      String sql = "insert into employee (name, joint_date, salary) " +
        "values ('Vasya', '2019-10-01', 25000.00)";
      statement.executeUpdate(sql);

      sql = "insert into employee (name, joint_date, salary) " +
        "values ('Petya', '2018-12-22', 225000.00)";
      statement.executeUpdate(sql);

      sql = "insert into employee (name, joint_date, salary) " +
        "values ('Iisus', '1933-06-01', 15.00)";
      statement.executeUpdate(sql);

      connection.commit();

      logStream.println(MESSAGE_INSERT_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_INSERT_EXCEPTION + e.toString());
    }
  }

  @Override
  public void update() {
    try {
      String sql = "update employee set salary = 1000000.111 where name = 'Iisus';";
      statement.executeUpdate(sql);

      connection.commit();

      logStream.println(MESSAGE_UPDATE_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_UPDATE_EXCEPTION + e.toString());
    }
  }

  @Override
  public void delete() {
    try {
      String sql = "delete from employee where name = 'Petya';";
      statement.executeUpdate(sql);

      connection.commit();

      logStream.println(MESSAGE_DELETE_SUCCESS);
    } catch (Exception e) {
      logStream.println(MESSAGE_DELETE_EXCEPTION + e.toString());
    }
  }

  @Override
  public void select() {
    String sql = "select * from employee;";

    try {

      ResultSet resultSet = statement.executeQuery(sql);

      logStream.println("employee");
      logStream.println("id;name;joint_date;salary");

      while (resultSet.next()) {
        StringBuilder builder = new StringBuilder();
        builder.append(resultSet.getString("id"));
        builder.append(';');
        builder.append(resultSet.getString("name"));
        builder.append(';');
        builder.append(resultSet.getString("joint_date"));
        builder.append(';');
        builder.append(resultSet.getString("salary"));

        logStream.println(builder.toString());
      }

      resultSet.close();
      connection.commit();

      logStream.println(MESSAGE_SELECT_SUCCESS);

    } catch (Exception e) {
      logStream.println(MESSAGE_SELECT_EXCEPTION + e.toString());
    }
  }
}
