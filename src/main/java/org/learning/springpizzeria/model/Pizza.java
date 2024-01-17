package org.learning.springpizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must not be blank")
    @Column(nullable = false)
    private String name;
    private String description;

    @Lob
    private String photo;

    @NotNull(message = "price must not be null")
    @DecimalMin(value = "1.0", message = "price must be higher than 1")
    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza", orphanRemoval = true)
    private List<Discount> discounts;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public Boolean activeDiscounts() {
        int counter = 0;
        for (Discount discount : discounts) {
            if (discount.isDiscountActive()) {
                counter++;
            }
        }
        return counter > 0;
    }


}
