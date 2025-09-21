package com.springbootbackend.Service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootbackend.model.ItemModel;
import com.springbootbackend.repository.ItemRepository;


@Service
public class ItemserviceImpl implements Itemservice {
	
	@Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemModel getItemById(int id) {
    	Optional<ItemModel> item = itemRepository.findById(id);
        return item.orElse(null);
    }


    @Override
    public Iterable<ItemModel> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public ItemModel addItem(ItemModel newItem) {
        return itemRepository.save(newItem);
    }

    @Override
    public ItemModel updateItem(int id, ItemModel updatedItem) {
        ItemModel existingItem = getItemById(id);
        if (existingItem != null) {
            existingItem.setItemname(updatedItem.getItemname());
            existingItem.setItemprice(updatedItem.getItemprice());;
            existingItem.setItemdesc(updatedItem.getItemdesc());
            existingItem.setStatus(updatedItem.getStatus());
            return itemRepository.save(existingItem);
        }
        return null;
    }

    @Override
    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }
    
    @Override
    public ItemModel enableItem(int id) {
        ItemModel item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setStatus("enabled");
            return itemRepository.save(item);
        }
        return null;
    }
    
    @Override
    public ItemModel disableItem(int id) {
        ItemModel item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            item.setStatus("disabled");
            return itemRepository.save(item);
        }
        return null;
    }
	
	
	
}