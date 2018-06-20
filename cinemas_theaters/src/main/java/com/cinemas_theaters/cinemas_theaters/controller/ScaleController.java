package com.cinemas_theaters.cinemas_theaters.controller;

import com.cinemas_theaters.cinemas_theaters.domain.dto.ScaleDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.ScaleService;
import com.cinemas_theaters.cinemas_theaters.service.UserService;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scale")
public class ScaleController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScaleService scaleService;

    public ResponseEntity upadateScale(@RequestHeader("Authorization") String token, @RequestBody ScaleDTO scale){

        if(token.split("\\.").length != 3)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        String username = this.jwtService.getUser(token).getUsername();
        User user = this.userService.findByUsername(username);

        if(user.getType() != com.cinemas_theaters.cinemas_theaters.domain.enums.UserType.SystemAdmin)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        this.scaleService.updateScale(scale.getMilestoneSilver(), scale.getMilestoneGold(), scale.getBronzeDiscount(),
                scale.getSilverDiscount(), scale.getGoldDiscount());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}
