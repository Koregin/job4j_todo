package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {
    Item add(Item item);

    List<Item> findAll();

    List<Item> findDoneItems();

    List<Item> findNewItems();

    boolean replace(Item item);

    boolean delete(int id);

    Item findById(int id);

    boolean setDone(int id);
}
