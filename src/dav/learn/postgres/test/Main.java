package dav.learn.postgres.test;

public class Main {

  public static void main(String[] args) {
    ConsoleManager consoleManager = new ConsoleManager();
    SqlCommander commander = new PostgreSqlCommander();

    commander.createTable();
    commander.select();

    consoleManager.read();

    commander.insert();
    commander.select();

    consoleManager.read();

    commander.update();
    commander.select();

    consoleManager.read();

    commander.delete();
    commander.select();

    consoleManager.read();

    commander.dropTable();
    commander.select();

    commander.disconnect();
  }

}
