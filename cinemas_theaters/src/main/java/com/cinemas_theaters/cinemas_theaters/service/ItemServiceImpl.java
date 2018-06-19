package com.cinemas_theaters.cinemas_theaters.service;

import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserItemStatus;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreItemRepository;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import com.cinemas_theaters.cinemas_theaters.repository.UserItemRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cinemas_theaters.cinemas_theaters.repository.ItemRepository;

import java.time.Duration;
import java.util.*;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    @Autowired
    private TheatreItemRepository theatreItemRepository;

    @Autowired
    private TheatreRepository theatreRepository;


    @Override
    public Boolean addUserItem(String name, String description, String duration, User user, String imagePath){
        this.userItemRepository.save(new UserItem(name, description, user, stringToDuration(duration), imagePath));
        return Boolean.TRUE;
    }

    @Override
    public Boolean addTheatreItem(String name, String description, Long theatreId, Double price, Long quantity){
        Optional<Theatre> theatre = this.theatreRepository.findById(theatreId);
        if (theatre.isPresent()){
            Set set = new LinkedHashSet();
            set.add(Category.ACTION);

            this.theatreItemRepository.save(new TheatreItem(name, description, theatre.get(),set, price, quantity));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Duration stringToDuration(String duration){
        return Duration.ofDays(2);
    }


    @Override
    public List<Item> findAllItems(){
        return this.itemRepository.findAll();
    }

    @Override
    public List<UserItem> findAllUserItems(){
        return this.userItemRepository.findByStatus(UserItemStatus.ACCEPTED);
    }

    @Override
    public List<UserItem> findAllPendingUserItems(){return this.userItemRepository.findByStatus(UserItemStatus.PENDING);}

    @Override
    public List<TheatreItem> findAllTheatreItems(){
        return this.theatreItemRepository.findAll();
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
    public void acceptUserItem(Long id){
        Optional<UserItem> item = this.userItemRepository.findById(id);
        if (item.isPresent()){
            UserItem userItem = item.get();
            userItem.setStatus(UserItemStatus.ACCEPTED);

            this.userItemRepository.save(userItem);
        }
    }

    @Override
    public void rejectUserItem(Long id){
        Optional<UserItem> item = this.userItemRepository.findById(id);
        if (item.isPresent()){
            UserItem userItem = item.get();
            userItem.setStatus(UserItemStatus.REJECTED);

            this.userItemRepository.save(userItem);
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
