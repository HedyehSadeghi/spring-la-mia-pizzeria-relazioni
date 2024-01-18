package org.learning.springpizzeria.controller;

import org.learning.springpizzeria.model.Ingredient;
import org.learning.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;


    @GetMapping
    public String list(Model model) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        model.addAttribute("ingredientList", ingredientList);
        return "ingredients/list";
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
