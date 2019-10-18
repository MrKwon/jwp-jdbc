package nextstep.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FunctionalPreparedStatement<R> {
    R apply(PreparedStatement t) throws SQLException;
}
