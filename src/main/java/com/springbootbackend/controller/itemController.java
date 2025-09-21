package com.springbootbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbackend.Service.Itemservice;
import com.springbootbackend.model.ItemModel;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Item", description = "Operations related to item management")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cafe")
@RestController
public class itemController {

	private final Itemservice itemservice;

    @Autowired
    public itemController(Itemservice itemservice) {
        this.itemservice = itemservice;
    }
	
	@Operation(summary = "Get item by ID", description = "Retrieve an item by its unique ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item found"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@GetMapping("/item/{id}")
    public ResponseEntity<ItemModel> getItemById(@PathVariable int id) {
        ItemModel item = itemservice.getItemById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all items", description = "Retrieve all items.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Items retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/item")
    public ResponseEntity<Iterable<ItemModel>> getAllItems() {
        Iterable<ItemModel> items = itemservice.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Operation(summary = "Add new item", description = "Add a new item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/item")
    public ResponseEntity<ItemModel> addItem(@RequestBody ItemModel newItem) {
        ItemModel addedItem = itemservice.addItem(newItem);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Update item", description = "Update an existing item by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item updated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/item/{id}")
    public ResponseEntity<ItemModel> updateItem(@PathVariable int id, @RequestBody ItemModel updatedItem) {
        ItemModel item = itemservice.updateItem(id, updatedItem);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete item", description = "Delete an item by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        itemservice.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @Operation(summary = "Enable item", description = "Enable an item by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item enabled successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/enable/{id}")
    public ItemModel enableItem(@PathVariable int id) {
        return itemservice.enableItem(id);
    }

    @Operation(summary = "Disable item", description = "Disable an item by its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item disabled successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/disable/{id}")
    public ItemModel disableItem(@PathVariable int id) {
        return itemservice.disableItem(id);
    }


}
