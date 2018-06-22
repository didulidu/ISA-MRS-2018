package com.cinemas_theaters.cinemas_theaters.domain.dto;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Theatre;
import com.cinemas_theaters.cinemas_theaters.domain.entity.TheatreItem;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.domain.entity.UserItem;

import java.util.List;

public class AllItemsDTO {
    private List<UserItem> userItems;
    private List<TheatreItem> theatreItems;

    public AllItemsDTO(){}
    public AllItemsDTO(List<UserItem> userItems, List<TheatreItem> theatreItems){
        this.userItems = userItems;
        this.theatreItems = theatreItems;
    }

    public void setUserItems(List<UserItem> userItems){this.userItems = userItems;}
    public List<UserItem> getUserItems(){return this.userItems;}

    public void setTheatreItems(List<TheatreItem> theatreItems){this.theatreItems = theatreItems;}
    public List<TheatreItem> getTheatreItems(){return this.theatreItems;}
}
