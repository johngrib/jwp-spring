package next.controller.user;

import next.dao.UserDao;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by johngrib on 2017. 5. 15..
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserDao userDao;

    UserController uc;

    @Before
    public void setup() {
        uc = new UserController(userDao);
    }

    @Test
    public void profile() throws Exception {
        User dbUser = new User("userId", "password", "name", "email");

        when(userDao.findByUserId("javajigi"))
                .thenReturn(dbUser);

        ExtendedModelMap model = new ExtendedModelMap();

        uc.profile("javajigi", model);

        User user = (User) model.get("user");

        assertThat(user, is(dbUser));
    }

    @Test
    public void create() throws Exception {
        User dbUser = new User("userId", "password", "name", "email");
        uc.create(dbUser);
        verify(userDao).insert(dbUser);
    }
}
