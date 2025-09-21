package com.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.springbootbackend.model.ItemModel;




@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Integer>{

}
