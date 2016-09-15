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

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInsertToDo() {
		System.out.println("testInsertToDo()");
		System.out.println("my todos Repository = " + todos.toString());

		String testToDoText = "Test ToDo";

		ToDo testToDo = new ToDo(testToDoText);
		todos.save(testToDo);

		ToDo retrievedToDo = todos.findFirstByText(testToDoText);
		assertNotNull(retrievedToDo);

		todos.delete(testToDo);
		retrievedToDo = todos.findFirstByText(testToDoText);
		assertNull(retrievedToDo);
	}

}
