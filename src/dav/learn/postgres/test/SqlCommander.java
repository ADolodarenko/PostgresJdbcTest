package dav.learn.postgres.test;

import java.io.PrintStream;

public interface SqlCommander {
  void setLogStream(PrintStream stream);
  void connect();
  void disconnect();
  void createTable();
  void dropTable();
  void insert();
  void update();
  void delete();
  void select();
}
