package next.dao;

import next.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johngrib on 2017. 5. 15..
 */
public class MockUserDao implements UserDao  {

    private Map<String, User> users = new HashMap<>();

    @Override
    public void insert(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String userId) {
        return users.get(userId);
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public void update(User user) {

    }
}
