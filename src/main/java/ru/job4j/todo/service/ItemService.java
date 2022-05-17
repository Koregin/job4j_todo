package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.ItemStore;

import java.util.List;

@Service
public class ItemService {
    private final ItemStore store;

    public ItemService(ItemStore store) {
        this.store = store;
    }

    public Item create(Item item) {
        return store.add(item);
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public List<Item> findNew() {
        return store.findNewItems();
    }

    public List<Item> findDone() {
        return store.findDoneItems();
    }

    public boolean update(Item item) {
        return store.replace(item);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public void setDone(int id) {
        Item item = store.findById(id);
        item.setDone(true);
        store.replace(item);
    }
}
