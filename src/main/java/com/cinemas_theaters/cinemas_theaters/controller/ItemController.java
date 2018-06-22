package com.cinemas_theaters.cinemas_theaters.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.cinemas_theaters.cinemas_theaters.domain.dto.AllItemsDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreItemDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserItemDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.*;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.repository.ItemSpecificationsBuilder;
import com.cinemas_theaters.cinemas_theaters.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import com.cinemas_theaters.cinemas_theaters.domain.dto.ItemDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private BidService bidService;

    @Autowired
    private EmailService emailService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/user"
    )
    public ResponseEntity addUserItem(@RequestHeader("Authorization") String token, @RequestBody UserItemDTO item){
        Boolean success;

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);


        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        Date date;
        try {
            date = format.parse(item.getDuration());
            Date now = new Date();

            if (!now.before(date))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        success = this.itemService.addUserItem(item.getName(), item.getDescription(),
                date, user, item.getImagePath());


        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/user/{id}"
    )
    public ResponseEntity modifyUserItem(@PathVariable("id") final Long id, @RequestBody UserItemDTO item,
                                         @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        UserItem userItem = this.itemService.findUserItemById(id);

        if (userItem == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (userItem.getUser() != user)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        Date date;
        try{
            date = format.parse(item.getDuration());

            Date now = new Date();

            if (!now.before(date))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        userItem.setName(item.getName());
        userItem.setDescription(item.getDescription());
        userItem.setImagePath(item.getImagePath());
        this.itemService.modifyUserItem(userItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }


    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            value = "/theatre/{id}"
    )
    public ResponseEntity modifyTheatreItem(@PathVariable("id") final Long id, @RequestBody TheatreItemDTO item,
                                            @RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        TheatreItem theatreItem = this.itemService.findTheatreItemById(id);

        theatreItem.setName(item.getName());
        theatreItem.setDescription(item.getDescription());
        theatreItem.setImagePath(item.getImagePath());
        theatreItem.setPrice(item.getPrice());
        theatreItem.setQuantity(item.getQuantity());

        this.itemService.modifyTheatreItem(theatreItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }


    @PostMapping(
            value = "/theatre",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity addTheatreItem(@RequestBody TheatreItemDTO item,
                                         @RequestHeader("Authorization") String token){

        Boolean success;

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if(user == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        if(user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);


        success = this.itemService.addTheatreItem(item.getName(), item.getDescription(),
                item.getTheatreId(), item.getPrice(), item.getQuantity());


        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AllItemsDTO findAll(){
        AllItemsDTO items = new AllItemsDTO();
        items.setUserItems( this.itemService.findAllUserItems() );
        items.setTheatreItems( this.itemService.findAllTheatreItems() );
        return items;
    }

    @GetMapping(
        value = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity findById(@PathVariable final Long id){
        Item item = this.itemService.findById(id);
        if (item != null){
            return ResponseEntity.ok(item);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity deleteById(@PathVariable final Long id,
                                     @RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Item item = this.itemService.findById(id);

        if (item instanceof UserItem){
            if(user.getType() != UserType.RegisteredUser)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            if (user != ((UserItem)item).getUser())
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            if(this.itemService.deleteById(id))
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else if (item instanceof TheatreItem){
            if (user.getType() != UserType.FanzoneAdmin)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            if(this.itemService.deleteById(id))
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity modify(@PathVariable final Long id, @RequestBody ItemDTO itemDTO){
        Item item = this.itemService.findById(id);
        if(item != null){
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            if (item instanceof TheatreItem) {
                ((TheatreItem)item).setPrice(itemDTO.getPrice());
            }
            this.itemService.modify(item);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping( value = "/search")
    public ResponseEntity searchItems(@RequestParam(value = "search") String search){
        ItemSpecificationsBuilder builder = new ItemSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|::)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()){
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Item> spec = builder.build();


        return ResponseEntity.ok(itemService.findAll(spec));
    }

    @GetMapping(
            value = "/evaluate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUserItemsToEvaluate(@RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);


        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);


        List<UserItem> items = this.itemService.findAllPendingUserItems();

        return ResponseEntity.ok(items);
    }


    @PostMapping(
            value = "/evaluate/accept/{id}"
    )
    public ResponseEntity acceptUserItem(@PathVariable("id") final Long id,
                                         @RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.itemService.acceptUserItem(id);

        Item item = this.itemService.findById(id);

        this.emailService.sendOfferAccepted((RegisteredUser)((UserItem)item).getUser(), item);

        return ResponseEntity.ok(null);
    }

    @PostMapping(
            value = "/evaluate/reject/{id}"
    )
    public ResponseEntity rejectUserItem(@PathVariable("id") final Long id,
                                         @RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.itemService.rejectUserItem(id);

        Item item = this.itemService.findById(id);

        this.emailService.sendOfferRejected((RegisteredUser) ((UserItem)item).getUser(), item);

        return ResponseEntity.ok(null);
    }

    @GetMapping(
            value = "/myoffers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getAllItemsByUser(@RequestHeader("Authorization") String token){

        if (token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        List<UserItem> items = this.itemService.findAllItemsByUser(user);

        return ResponseEntity.ok(items);
    }


    @GetMapping(
        value = "/myoffers/bids/{id}"
    )
    public ResponseEntity getAllBidsForItem(@PathVariable("id") final Long id,
                                            @RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        Item item = this.itemService.findById(id);

        if (item == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        List<Bid> bids = this.bidService.findByItem(item);

        return ResponseEntity.ok(bids);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/theatre"
    )
    public ResponseEntity getAllTheatreItems(@RequestHeader("Authorization") String token){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        if (user.getType() != UserType.FanzoneAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        List<TheatreItem> items = this.itemService.findAllTheatreItems();

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
