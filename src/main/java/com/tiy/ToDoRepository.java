package com.tiy;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jessicatracy on 9/15/16.
 */
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    ToDo findFirstByText(String text);
    Iterable<ToDo> findAllByUserId(Integer userId);
}
