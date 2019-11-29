package dav.learn.postgres.test;

import java.sql.ResultSet;

public interface RowMapper<T> {
  T map(ResultSet resultSet, int rowNumber);
}
