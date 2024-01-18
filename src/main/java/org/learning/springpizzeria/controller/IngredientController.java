package org.learning.springpizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.model.Ingredient;
import org.learning.springpizzeria.repository.IngredientRepository;
import org.learning.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping
    public String list(Model model) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        model.addAttribute("ingredientList", ingredientList);
        return "ingredients/list";
    }


    @GetMapping("/create")
    public String create(Model model) {
        Ingredient ingredient = new Ingredient();
        model.addAttribute("ingredient", ingredient);
        return "ingredients/create";
    }


    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient ingredientForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ingredients/create";
        }
        Ingredient savedIngredient = ingredientRepository.save(ingredientForm);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            Ingredient ingredientToEdit = result.get();
            model.addAttribute("ingredient", ingredientToEdit);
            return "ingredients/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + id + " not found");
        }
    }


    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Ingredient ingredientForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ingredients/edit";
        }
        Ingredient updatedIngredient = ingredientRepository.save(ingredientForm);
        return "redirect:/ingredients";

    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            Ingredient ingredientToDelete = result.get();
            ingredientRepository.delete(ingredientToDelete);
            redirectAttributes.addFlashAttribute("redirectMessage", "ingredient " + ingredientToDelete.getName() + " deleted");
            return "redirect:/ingredients";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id " + id + " not found");
        }

    }


}
