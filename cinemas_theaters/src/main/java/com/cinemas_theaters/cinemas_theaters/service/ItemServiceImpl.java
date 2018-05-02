package com.cinemas_theaters.cinemas_theaters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.repository.ItemRepository;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public Boolean add(String name, String description, Double price){
        this.itemRepository.save(new Item(name, description, price));
        return Boolean.TRUE;
    }

    @Override
    public List<Item> findAll(){
        return this.itemRepository.findAll();
    }

    @Override
    public Item findById(Long id){
        Optional<Item> dbItem = this.itemRepository.findById(id);
        if(dbItem.isPresent()){
            return dbItem.get();
        }
        else{
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id){
        if (this.itemRepository.findById(id).isPresent()){
            this.itemRepository.deleteById(id);
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

    @Override
    public void modify(Item item){
        this.itemRepository.save(item);
    }
}
