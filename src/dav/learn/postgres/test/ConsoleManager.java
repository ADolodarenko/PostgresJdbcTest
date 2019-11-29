package dav.learn.postgres.test;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleManager {

  private static final String DEFAULT_PROMPT = "Press any key...";
  private static final String MESSAGE_READ_EXCEPTION = "Couldn't reade from console.";
  private static final String MESSAGE_CLOSE_EXCEPTION = "Couldn't close console reader.";

  private BufferedReader reader;

  public ConsoleManager() {
    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  public String read() {
    return read(DEFAULT_PROMPT);
  }

  public String read(String prompt) {
    System.out.println(prompt);

    String input = null;

    try {
      input = reader.readLine();
    } catch (IOException e) {
      System.out.println(MESSAGE_READ_EXCEPTION);
    }

    return input;
  }

  public void close() {
    try {
      reader.close();
    } catch (IOException e) {
      System.out.println(MESSAGE_CLOSE_EXCEPTION);
    }
  }
}
