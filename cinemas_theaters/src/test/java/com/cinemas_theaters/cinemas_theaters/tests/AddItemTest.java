package com.cinemas_theaters.cinemas_theaters.tests;


import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreItemDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.RegisteredUser;
import com.cinemas_theaters.cinemas_theaters.domain.entity.User;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddItemTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtService jwtService;

    private ObjectMapper mapper;
    private MockMvc mock;

    @Before
    public void setUp(){
        this.mock = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.mapper = new ObjectMapper();
    }

    @Test
    public void addOfficialItemTest1() throws Exception{
        User user = new RegisteredUser();
        JwtUser jwt = new JwtUser(user.getUsername());
        String token = this.jwtService.getToken(jwt);

        TheatreItemDTO theatreItemDTO = new TheatreItemDTO("Item1", "opis", null, new Long(132), new Long(10), null);

        String itemJsonCorrect = mapper.writeValueAsString(theatreItemDTO);

        mock.perform(post("/items/theatre", theatreItemDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJsonCorrect)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void addOfficialItemTest2() throws Exception{


        TheatreItemDTO theatreItemDTO = new TheatreItemDTO("Item1", "opis", null, new Long(132), new Long(10), null);

        String itemJsonCorrect = mapper.writeValueAsString(theatreItemDTO);

        mock.perform(post("/items/theatre", theatreItemDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemJsonCorrect)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
