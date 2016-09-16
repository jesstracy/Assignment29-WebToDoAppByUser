package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Jessica";
            user.password = "pass";
            users.save(user);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String username) {
//        if (session.getAttribute("username") != null) {
//            System.out.println("Getting here...");
//            User user = users.findFirstByName("username");
//            System.out.println("Found user in db- id is: " + user.id); /* not getting here */
//            Iterable<ToDo> listOfTodosIterable = todos.findAllByUserId(user.id);
//            ArrayList<ToDo> listOfTodos = new ArrayList<>();
//            for (ToDo todo : listOfTodosIterable) {
//                listOfTodos.add(todo);
//            }
//            model.addAttribute("toDoItems", listOfTodos);
//
//            model.addAttribute("username", session.getAttribute("username"));
//        }
        if (session.getAttribute("username") != null) {
            Iterable<ToDo> listOfTodosIterable = todos.findAll();
            ArrayList<ToDo> listOfTodos = new ArrayList<>();
            for (ToDo todo : listOfTodosIterable) {
                listOfTodos.add(todo);
            }
            model.addAttribute("toDoItems", listOfTodos);

            model.addAttribute("username", session.getAttribute("username"));
        }
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findFirstByName(username);

        if (user != null) {
//            System.out.println("User is not null! " + user.name);
            if (!password.equals(user.password)) {
                throw new Exception("Invalid password!");
            } else {
                session.setAttribute("username", username);
            }
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(HttpSession session, String todoText) {
//        System.out.println("About to add: " + todoText);
        if (todoText != null) {
            User user = users.findFirstByName(session.getAttribute("username").toString());
            ToDo todo = new ToDo(todoText, user);
            todos.save(todo);
//            System.out.println("ID of todo just added: " + todo.id);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteToDo(Model model, Integer todoID) {
//        System.out.println("About to delete: " + todoID);
        if (todoID != null) {
            todos.delete(todoID);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
