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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult, Model model) {
        //valido oggetto
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formDiscount.getPizza());
            return "discounts/create";
        }
        if ((formDiscount.getExpireDate() != null) && formDiscount.isExpireDateBeforeStartDate()) {
            formDiscount.setExpireDate(formDiscount.getStartDate().plusDays(5));
        }

        //no errori: salvo su db
        Discount storedDiscount = discountRepository.save(formDiscount);

        return "redirect:/pizzas/show/" + storedDiscount.getPizza().getId();

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Discount> result = discountRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("discount", result.get());
            return "discounts/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with id " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Discount discountForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "discounts/edit";
        }
        if ((discountForm.getExpireDate() != null) && discountForm.isExpireDateBeforeStartDate()) {
            discountForm.setExpireDate(discountForm.getStartDate().plusDays(5));
        }
        Discount updatedDiscount = discountRepository.save(discountForm);
        return "redirect:/pizzas/show/" + updatedDiscount.getPizza().getId();

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Discount> result = discountRepository.findById(id);
        if (result.isPresent()) {
            Discount discountToDelete = result.get();
            discountRepository.delete(discountToDelete);
            redirectAttributes.addFlashAttribute("redirectMessage", "discount" + result.get().getTitle() + " deleted");
            return "redirect:/pizzas/show/" + discountToDelete.getPizza().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with id " + id + " not found");
        }

    }


}
