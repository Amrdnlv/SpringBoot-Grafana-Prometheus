package com.thecheckler.sburrestdemo.classs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffes")
public class ResstApiDemoController {
    private List<Coffee> coffees = new ArrayList<>();

    public ResstApiDemoController()
    {
        coffees.addAll(List.of(
          new Coffee("Cafe Cereza"),
          new Coffee("Cafe Ganadoz"),
          new Coffee("Cafe Lareno"),
          new Coffee("Cafe Tres Pontas")
        ));
    }

    @GetMapping
    Iterable<Coffee> getCofees()
    {
        return coffees;
    }

    @GetMapping("/{id}")
    ResponseEntity getCofeesById(@PathVariable String id)
    {
        for (Coffee c:coffees)
        {
            if(c.getId().equals(id)) return new ResponseEntity(Optional.of(c), HttpStatus.OK);
        }
        return new ResponseEntity(Optional.empty(),HttpStatus.FAILED_DEPENDENCY);
    }

//    для получения списка видов кофе
    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffees.add(coffee);
        return coffee;
    }
    @PutMapping("/{id}")
    Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;
        for (Coffee c: coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
    }
    @DeleteMapping("{id}")
    ResponseEntity deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
        return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
    }
}
