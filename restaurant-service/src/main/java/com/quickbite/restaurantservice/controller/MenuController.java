package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{id}")
public class MenuController {

    // ====================== PUBLIC ====================

    @GetMapping("/menu")
    public Object getRestaurantMenu(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    // =============== RESTAURANT OWNER ================

    @PostMapping("/categories")
    public Object createCategory(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PostMapping("/items")
    public Object createItem(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PutMapping("/categories/{catId}")
    public Object updateCategory(
            @PathVariable Long id,
            @PathVariable Long catId
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PutMapping("/items/{itemId}")
    public Object updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        // TODO: Call MenuService
        return null;
    }

    @DeleteMapping("/categories/{catId}")
    public Object deleteCategory(
            @PathVariable Long id,
            @PathVariable Long catId
    ){
        // TODO: Call Menuservice
        return null;
    }

    @DeleteMapping("/items/{itemId}")
    public Object deleteItem(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        // TODO: Call MenuService
        return null;
    }

    @PatchMapping("/categories/{catId}/availability")
    public Object updateCategoryAvailability(
            @PathVariable Long id,
            @PathVariable Long catId
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PatchMapping("/items/{itemId}/availability")
    public Object updateItemAvailability(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        //TODO: Call MenuService
        return null;
    }

}
