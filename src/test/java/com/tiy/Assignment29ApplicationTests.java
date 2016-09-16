package com.tiy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Assignment29ApplicationTests {

	@Autowired
	ToDoRepository todos;

	@Autowired
	UserRepository users;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsertToDo() {
		System.out.println("testInsertToDo()");
		System.out.println("my todos Repository = " + todos.toString());


		String testUserName = "Test_1";
		String testPassword = "pass";


		User testUser = new User(testUserName, testPassword);
		users.save(testUser);


		String testToDoText = "Test ToDo";

		ToDo testToDo = new ToDo(testToDoText, testUser);
		todos.save(testToDo);

		ToDo retrievedToDo = todos.findFirstByText(testToDoText);
		assertNotNull(retrievedToDo);

		todos.delete(testToDo);
		retrievedToDo = todos.findFirstByText(testToDoText);
		assertNull(retrievedToDo);

		users.delete(testUser);
	}

	@Test
	public void testInsertUser() {
		System.out.println("testInsertUser()");
		System.out.println("my users Repository = " + users.toString());

		String testUserName = "Test_1";
		String testPassword = "pass";


		User testUser = new User(testUserName, testPassword);
		users.save(testUser);

		User retrievedUser = users.findFirstByName(testUserName);
		assertNotNull(retrievedUser);

		users.delete(testUser);
		retrievedUser = users.findFirstByName(testUserName);
		assertNull(retrievedUser);
	}

//	@Test
//	public void insertHarcodedUser() {
//
//		String userName = "Jessica";
//		String password = "pass";
//
//
//		User myUser = new User(userName, password);
//		users.save(myUser);
//	}
}
