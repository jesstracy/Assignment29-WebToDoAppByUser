package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessicatracy on 9/15/16.
 */
@Controller
public class ToDoWebAppController {
    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    User user;
    boolean signUpTrue = false;
    boolean loginTrue = false;
    boolean initialChoice = true;
    List<ToDo> listOfTodos;


    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Jessica";
            user.password = "pass";
            users.save(user);
        } else if (users.count() == 1) {
            User user2 = new User();
            user2.name = "Emily";
            user2.password = "12345";
            users.save(user2);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String username) {
        model.addAttribute("signUpTrue", signUpTrue);
        model.addAttribute("loginTrue", loginTrue);
        model.addAttribute("initialChoice", initialChoice);


        if (user != null) {
            listOfTodos = todos.findByUserId(user.id);

            model.addAttribute("toDoItems", listOfTodos);

            model.addAttribute("username", session.getAttribute("username"));
        }


        // Original way - works but shows all todos for all users, not just the user logged in
//        if (session.getAttribute("username") != null) {
//            Iterable<ToDo> listOfTodosIterable = todos.findAll();
//            ArrayList<ToDo> listOfTodos = new ArrayList<>();
//            for (ToDo todo : listOfTodosIterable) {
//                listOfTodos.add(todo);
//            }
//            model.addAttribute("toDoItems", listOfTodos);
//
//            model.addAttribute("username", session.getAttribute("username"));
//        }

        return "home";
    }

    @RequestMapping(path="/sign-up-button", method = RequestMethod.POST)
    public String signUpButton(Model model, HttpSession session) {
        if (!loginTrue) {
            signUpTrue = true;
//            session.setAttribute("signUpTrue", signUpTrue);
//            model.addAttribute("signUpTrue", signUpTrue);

            initialChoice = false;
//            session.setAttribute("initialChoice", initialChoice);
//            model.addAttribute("initialChoice", initialChoice);
        }
        return "redirect:/";
    }

    @RequestMapping(path="/login-button", method = RequestMethod.POST)
    public String loginButton(Model model, HttpSession session) {

        if (!signUpTrue) {
            loginTrue = true;
//            session.setAttribute("loginTrue", loginTrue);
//            model.addAttribute("loginTrue", loginTrue);

            initialChoice = false;
//            session.setAttribute("initialChoice", initialChoice);
//            model.addAttribute("initialChoice", initialChoice);
        }
        return "redirect:/";
    }

    @RequestMapping(path="/sign-up", method = RequestMethod.POST)
    public String signUp(HttpSession session, String username, String password) throws Exception {
        System.out.println("In sign up method");
        signUpTrue = false;

        if (username != "" && password != "") {
//            System.out.println("username and password okay");
            user = new User(username, password);
            users.save(user);
            session.setAttribute("username", username);
        } else {
            throw new Exception("Your username and password must not be blank!");
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        System.out.println("In login method");
        loginTrue = false;

        user = users.findFirstByName(username);

        if (user != null) {
//            System.out.println("IN LOGIN METHOD: User is not null! " + user.name);
            if (!password.equals(user.password)) {
                throw new Exception("Invalid password!");
            } else {
//                System.out.println("IN LOGIN METHOD: Setting username...");
                session.setAttribute("username", username);
            }
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(HttpSession session, String todoText) {
//        System.out.println("About to add: " + todoText);
        if (todoText != null) {
//            User user = users.findFirstByName(session.getAttribute("username").toString());
            ToDo todo = new ToDo(todoText, user);
            todos.save(todo);
//            System.out.println("ID of todo just added: " + todo.id);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/toggle", method = RequestMethod.GET)
    public String toggleToDo(Model model, Integer todoID) {
        if (todoID != null) {
            ToDo todo = todos.findOne(todoID);
            todo.isDone = !todo.isDone;
            todos.save(todo);
        }
        return "redirect:/";
    }

    @RequestMapping(path="/mark-all-done", method = RequestMethod.GET)
    public String markAllDone() {
        System.out.println("Marking all done...");
        listOfTodos = todos.findAllByUser(user);
        for (ToDo todo : listOfTodos) {
            todo.isDone = true;
            todos.save(todo);
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
        initialChoice = true;
        return "redirect:/";
    }
}
