package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/15/16.
 */
@Controller
public class ToDoWebAppController {
    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        Iterable<ToDo> listOfTodosIterable = todos.findAll();
        ArrayList<ToDo> listOfTodos = new ArrayList<>();
        for (ToDo todo : listOfTodosIterable) {
            listOfTodos.add(todo);
        }
        model.addAttribute("toDoItems", listOfTodos);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findFirstByName(username);
        if (user != null) {
            if (!password.equals(user.password)) {
                throw new Exception("Invalid password!");
            }
            session.setAttribute("username", username);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(String todoText) {
        System.out.println("About to add: " + todoText);
        if (todoText != null) {
            ToDo todo = new ToDo(todoText);
            todos.save(todo);
            System.out.println("ID of todo just added: " + todo.id);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteToDo(Model model, Integer todoID) {
        System.out.println("About to delete: " + todoID);
        if (todoID != null) {
            todos.delete(todoID);
        }
        return "redirect:/";
    }
}
