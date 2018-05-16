package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.repository.ItemRepository;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Override
    public Boolean add(String name, String description, Double price, Long quantity){
        Optional<Theatre> theatreDB = theatreRepository.findById(Long.valueOf(132));
        if(theatreDB.isPresent()){
            this.itemRepository.save(new TheatreItem(name, description, price, quantity, theatreDB.get()));
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public  Boolean add(String name, String description, String duration){
        this.itemRepository.save(new UserItem(name, description, stringToDuration(duration)));
        return Boolean.TRUE;
    }

    private Duration stringToDuration(String duration){
        return Duration.ofDays(2);
    }


    @Override
    public List<Item> findAll(){
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> findAll(Specification<Item> spec){ return this.itemRepository.findAll(spec);}

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
