package com.cinemas_theaters.cinemas_theaters.controller;


import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.entity.Purchase;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.enums.UserType;
import com.cinemas_theaters.cinemas_theaters.service.ItemService;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.PurchaseService;
import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @PostMapping
    public ResponseEntity makePurchase(@RequestParam(name = "item_id") Long item_id,
                                       @RequestHeader("Authorization") String token){

        String username = this.jwtService.getUser(token).getUsername();
        RegisteredUser user = this.registeredUserService.findByUsername(username);

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        if (user.getType() != UserType.RegisteredUser)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        Item item = itemService.findById(item_id);
        if(item != null){
            purchaseService.add(item, user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }
}
