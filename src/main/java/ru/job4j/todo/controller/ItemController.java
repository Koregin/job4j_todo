package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String allItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("typeItems", "all");
        return "index";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", itemService.findNew());
        model.addAttribute("typeItems", "new");
        return "index";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model) {
        model.addAttribute("items", itemService.findDone());
        model.addAttribute("typeItems", "done");
        return "index";
    }

    @GetMapping("/formAddItem")
    public String addItem() {
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@RequestParam("description") String description) {
        Item item = new Item(description);
        itemService.create(item);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateCandidate(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/index";
    }

    @GetMapping("/fullInfoItem/{itemId}")
    public String fullInfoItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "infoItem";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/itemDone/{itemId}")
    public String itemDone(Model model, @PathVariable("itemId") int id) {
        itemService.setDone(id);
        model.addAttribute("item", itemService.findById(id));
        return "infoItem";
    }
}
