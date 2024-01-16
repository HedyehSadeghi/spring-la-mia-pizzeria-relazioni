package org.learning.springpizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.model.Discount;
import org.learning.springpizzeria.model.Pizza;
import org.learning.springpizzeria.repository.DiscountRepository;
import org.learning.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private DiscountRepository discountRepository;


    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            Pizza pizzaOnSale = result.get();
            model.addAttribute("pizza", pizzaOnSale);

            Discount newDiscount = new Discount();
            newDiscount.setPizza(pizzaOnSale);
            newDiscount.setStartDate(LocalDate.now());
            newDiscount.setExpireDate(LocalDate.now().plusDays(10));
            model.addAttribute("discount", newDiscount);
            return "discounts/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + pizzaId + " not found");
        }

    }


    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult) {
        //valido oggetto
        if (bindingResult.hasErrors()) {
            return "discounts/create";
        }
        //no errori: salvo su db
        Discount storedDiscount = discountRepository.save(formDiscount);

        return "redirect:/pizzas/show/" + storedDiscount.getPizza().getId();

    }


}
