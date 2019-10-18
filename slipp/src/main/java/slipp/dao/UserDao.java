package slipp.dao;

import nextstep.jdbc.JdbcTemplate;
import slipp.domain.User;
import slipp.support.db.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate<Integer> jdbcTemplate = new JdbcTemplate<>(ConnectionManager.getDataSource());

        jdbcTemplate.execute("INSERT INTO USERS VALUES (?, ?, ?, ?)", pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            return pstmt.executeUpdate();
        });
    }

    public void update(User user) throws SQLException {
        JdbcTemplate<Integer> jdbcTemplate = new JdbcTemplate<>(ConnectionManager.getDataSource());

        jdbcTemplate.execute("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?", pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            return pstmt.executeUpdate();
        });
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate<List<User>> jdbcTemplate = new JdbcTemplate<>(ConnectionManager.getDataSource());
        return jdbcTemplate.execute("SELECT userId, password, name, email FROM USERS", pstmt -> {

            try (ResultSet rs = pstmt.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(new User(rs.getString("userId"), rs.getString("password"),
                            rs.getString("name"), rs.getString("email")));
                }
                return users;
            }
        });
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>(ConnectionManager.getDataSource());
        return jdbcTemplate.execute("SELECT userId, password, name, email FROM USERS WHERE userid=?", pstmt -> {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"),
                            rs.getString("name"), rs.getString("email"));
                }
                return user;
            }
        });

    }
}
