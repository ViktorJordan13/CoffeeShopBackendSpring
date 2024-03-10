package com.codebrains.coffeeshopbackendspring.controllers;

import com.codebrains.coffeeshopbackendspring.entities.Coffee;
import com.codebrains.coffeeshopbackendspring.entities.CoffeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;

    public CoffeeController(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    @GetMapping
    public List<Coffee> getCoffees(){
        return coffeeRepository.findAll();
    }

    @GetMapping("/{coffeeId}")
    public Optional<Coffee> getCoffee(@PathVariable("coffeeId") Long coffeeId){
        var coffee = coffeeRepository.findById(coffeeId);
        return coffee;
    }

    @PostMapping
    public Coffee newCoffee(@RequestBody Coffee coffee){
        return this.coffeeRepository.save(coffee);
    }

    @PutMapping("/{coffeeId}")
    public Optional<Coffee> updateCoffee(@PathVariable("coffeeId") Long coffeeId, @RequestBody Coffee updatedCoffee){
        return this.coffeeRepository.findById(coffeeId)
                .map(oldCoffee -> this.coffeeRepository.save(updatedCoffee));
    }

    @DeleteMapping("/{coffeeId}")
    public void deleteCoffee(@PathVariable("coffeeId") Long coffeeId){
        this.coffeeRepository.deleteById(coffeeId);
    }
}
