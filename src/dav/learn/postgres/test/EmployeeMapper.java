package dav.learn.postgres.test;

import java.sql.ResultSet;

public class EmployeeMapper implements RowMapper<Employee> {

  @Override
  public Employee map(ResultSet resultSet, int rowNumber) {
    Employee employee = new Employee();

    return employee;
  }

}
