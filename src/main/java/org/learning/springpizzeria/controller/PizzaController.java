package org.learning.springpizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.model.Pizza;
import org.learning.springpizzeria.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzeriaRepository pizzeriaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzeriaRepository.findAll();
        model.addAttribute("pizzaList", pizzaList);
        return "pizzas/list";
    }

    @GetMapping("/show/{name}")
    public String show(@PathVariable String name, Model model) {
        Optional<Pizza> result = pizzeriaRepository.findById(name);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            return "pizzas/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza " + name + " not found");
        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        Pizza pizza = new Pizza();
        model.addAttribute("pizza", pizza);
        return "pizzas/create";
    }


    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/pizzas/create";
        }
        Optional<Pizza> pizzaWithName = pizzeriaRepository.findByName(pizzaForm.getName());
        if (pizzaWithName.isPresent()) {
            bindingResult.addError(new FieldError("pizza", "name", pizzaForm.getName(), false, null, null, "name is unique"));
            return "pizzas/create";
        } else {
            Pizza savedPizza = pizzeriaRepository.save(pizzaForm);
            return "redirect:/pizzas/show/" + savedPizza.getName();
        }
    }


}
