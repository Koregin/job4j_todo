package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;

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
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/items")
    public String allItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAll(user));
        model.addAttribute("typeItems", "all");
        return "items";
    }

    @GetMapping("/newItems")
    public String newItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findNew(user));
        model.addAttribute("typeItems", "new");
        return "items";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findDone(user));
        model.addAttribute("typeItems", "done");
        return "items";
    }

    @GetMapping("/formAddItem")
    public String addItem() {
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@RequestParam("description") String description, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Item item = new Item(description);
        item.setUser(user);
        itemService.create(item);
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateCandidate(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpSession session) {
        User user = (User) session.getAttribute("user");
        item.setUser(user);
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/fullInfoItem/{itemId}")
    public String fullInfoItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "infoItem";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/itemDone/{itemId}")
    public String itemDone(Model model, @PathVariable("itemId") int id) {
        itemService.setDone(id);
        model.addAttribute("item", itemService.findById(id));
        return "infoItem";
    }
}
