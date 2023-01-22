package com.example.manager;

import com.example.manager.controller.UserController;
import com.example.manager.domain.User;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.Assert;
//import static org.springframework.util.Assert;



//@SpringBootTest
class AsdUserManagerApplicationTests {

	@Test
	void contextLoads() {
	};





	@Test
	void test_register() {
		User dummyUser = new User("benis","kebab","box", "asd123", 0, false, null);


		UserController test_user_controller = new UserController();
		//User test_user = test_user_controller.register(test_firstname,test_lastname,test_username,test_password);

		//assert

		//assertEquals



		//assertEquals(2, calculator.add(1, 1));

		//assertThat(test_user, instanceOf(User.class));
	};

}
