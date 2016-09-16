package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jessicatracy on 9/15/16.
 */
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    ToDo findFirstByText(String text);
    List<ToDo> findByUserId(Integer userId);
    List<ToDo> findAllByUser(User user);
}
