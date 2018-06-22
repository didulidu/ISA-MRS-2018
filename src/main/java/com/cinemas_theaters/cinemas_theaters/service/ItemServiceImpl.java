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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Boolean addUserItem(String name, String description, Date date, User user, String imagePath){
        this.userItemRepository.save(new UserItem(name, description, date, user, imagePath));
        return Boolean.TRUE;
    }

    @Override
    @Transactional
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
    @Transactional
    public List<Item> findAllItems(){
        return this.itemRepository.findAll();
    }

    @Override
    @Transactional
    public List<UserItem> findAllUserItems(){
        return this.userItemRepository.findAllByStatusAndActive(UserItemStatus.ACCEPTED, true);
    }

    @Override
    @Transactional
    public List<UserItem> findAllPendingUserItems(){return this.userItemRepository.findAllByStatusAndActive(UserItemStatus.PENDING, true);}

    @Override
    @Transactional
    public List<UserItem> findAllItemsByUser(User user){
        return this.userItemRepository.findByUserAndStatusAndActive(user, UserItemStatus.ACCEPTED, true);
    }

    @Override
    @Transactional
    public List<TheatreItem> findAllTheatreItems(){
        return this.theatreItemRepository.findAllByActive(true);
    }


    @Override
    @Transactional
    public List<Item> findAll(Specification<Item> spec){ return this.itemRepository.findAll(spec);}

    @Override
    @Transactional
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
    @Transactional
    public void acceptUserItem(Long id){
        UserItem item = this.userItemRepository.findByIdAndStatus(id, UserItemStatus.PENDING);

        item.setStatus(UserItemStatus.ACCEPTED);
        this.userItemRepository.save(item);

    }

    @Override
    @Transactional
    public void rejectUserItem(Long id){
        UserItem item = this.userItemRepository.findByIdAndStatus(id, UserItemStatus.PENDING);

        item.setStatus(UserItemStatus.REJECTED);
        this.userItemRepository.save(item);

    }


    @Override
    @Transactional
    public Boolean deleteById(Long id){
        Optional<Item> itemDAO = this.itemRepository.findById(id);
        if (itemDAO.isPresent()){
            Item item = itemDAO.get();
            if(item.getActive())
                item.setActive(false);
            else
                return false;
            this.itemRepository.save(item);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void modify(Item item){
        this.itemRepository.save(item);
    }

    @Override
    @Transactional
    public void modifyUserItem(UserItem userItem) {this.userItemRepository.save(userItem);}

    @Override
    @Transactional
    public void modifyTheatreItem(TheatreItem theatreItem){this.theatreItemRepository.save(theatreItem);}

    @Override
    @Transactional
    public UserItem findUserItemById(Long id){
        Optional<UserItem> itemDAO = this.userItemRepository.findById(id);
        if (itemDAO.isPresent())
            return itemDAO.get();

        return null;
    }

    @Override
    @Transactional
    public TheatreItem findTheatreItemById(Long id){
        Optional<TheatreItem> itemDAO = this.theatreItemRepository.findById(id);
        if(itemDAO.isPresent())
            return itemDAO.get();

        return null;
    }
}
