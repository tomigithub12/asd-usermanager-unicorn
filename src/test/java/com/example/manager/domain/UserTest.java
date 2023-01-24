package com.example.manager.domain;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;

public class UserTest {
    @Test
    void TestWholeUserClassGetterAndSetter() {
        User user = new User("testFirstNameFirstInit","testLastNameFirstInit","usernameFirstInit", "1337FirstInit", 1, true, null);

        user.setFirstname("testFirstName");
        String gotFirstname = user.getFirstname();
        assert gotFirstname.equals("testFirstName") : "Firstname is Not valid";

        user.setLastname("testLastName");
        String gotLastName = user.getLastname();
        assert gotLastName.equals("testLastName") : "LastName is Not valid";

        user.setUsername("username");
        String gotUsername = user.getUsername();
        assert gotUsername.equals("username") : "Username is Not valid";

        user.setPassword("1337");
        String gotPassword = user.getPassword();
        assert gotPassword.equals("1337") : "Password is Not valid";

        user.setFailedAttempt(0);
        int gotFailedAttempt = user.getFailedAttempt();
        assert gotFailedAttempt == (0) : "FailedAttempt is Not valid";

        user.setAccountNonLocked(false);
        boolean gotAccountNonLocked = user.getAccountNonLocked();
        assert !gotAccountNonLocked : "AccountNonLocked is Not valid";

        Date testDate = new Date(2022, Calendar.JANUARY, 3, 2, 44, 26);
        user.setLockTime(testDate);
        Date gotLockTime = user.getLockTime();
        assert gotLockTime == testDate : "LockTime is Not valid";
    }
}
