package com.springbootbackend.Service;

import com.springbootbackend.model.ItemModel;

public interface Itemservice {
	
	ItemModel getItemById(int id);
    Iterable<ItemModel> getAllItems();
    ItemModel addItem(ItemModel newItem);
    ItemModel updateItem(int id, ItemModel updatedItem);
    void deleteItem(int id);
	ItemModel enableItem(int id);
	ItemModel disableItem(int id);

}