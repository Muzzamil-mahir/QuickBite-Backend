package com.quickbite.restaurantservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class MenuController {

    // ====================== PUBLIC ====================

    @GetMapping("/{id}/menu")
    public Object getRestaurantMenu(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    // =============== RESTAURANT OWNER ================

    @PostMapping("/{id}/categories")
    public Object createCategory(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PostMapping("/{id}/items")
    public Object createItem(
            @PathVariable Long id
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PutMapping("/{id}/categories/{catId}")
    public Object updateCategory(
            @PathVariable Long id,
            @PathVariable Long catId
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PutMapping("/{id}/items/{itemId}")
    public Object updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        // TODO: Call MenuService
        return null;
    }

    @DeleteMapping("/{id}/categories/{catId}")
    public Object deleteCategory(
            @PathVariable Long id,
            @PathVariable Long catId
    ){
        // TODO: Call Menuservice
        return null;
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public Object deleteItem(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        // TODO: Call MenuService
        return null;
    }

    @PatchMapping("/{id}/categories/{catId}/availability")
    public Object updateCategoryAvailability(
            @PathVariable Long id,
            @PathVariable Long catId
    ) {
        // TODO: Call MenuService
        return null;
    }

    @PatchMapping("/{id}/items/{itemId}/availability")
    public Object updateItemAvailability(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){
        //TODO: Call MenuService
        return null;
    }

}
