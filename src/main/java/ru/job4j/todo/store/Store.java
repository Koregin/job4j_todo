package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {
    Item add(Item item);

    List<Item> findAll(User user);

    List<Item> findDoneItems(User user);

    List<Item> findNewItems(User user);

    boolean replace(Item item);

    boolean delete(int id);

    Item findById(int id);

    boolean setDone(int id);
}
