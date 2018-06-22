package com.cinemas_theaters.cinemas_theaters.tests;

import com.cinemas_theaters.cinemas_theaters.domain.dto.RegUserProfileUpdateDTO;
import com.cinemas_theaters.cinemas_theaters.domain.entity.JwtUser;
import com.cinemas_theaters.cinemas_theaters.repository.FriendshipRepository;
import com.cinemas_theaters.cinemas_theaters.repository.RegisteredUserRepository;
import com.cinemas_theaters.cinemas_theaters.service.JwtService;
import com.cinemas_theaters.cinemas_theaters.service.RegisteredUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RegisteredUserTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    private MockMvc mock;
    private ObjectMapper mapper;
    private String token1;
    private String token2;
    private String token3;

    @Before
    public void setUp()
    {
        this.mock = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.mapper = new ObjectMapper();

        JwtUser registeredUser = new JwtUser("pera");
        token1 = this.jwtService.getToken(registeredUser);

        JwtUser registeredUser2 = new JwtUser("balenko");
        token2 = this.jwtService.getToken(registeredUser2);

        JwtUser imaginaryUser = new JwtUser("mysterious");
        token3 = this.jwtService.getToken(imaginaryUser);
    }

    @Test
    @Transactional
    @Rollback
    public void updateDataAndPasswordTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        RegUserProfileUpdateDTO registeredUserProfileUpdateDTO = new RegUserProfileUpdateDTO("petromir", "periccccc", "random@gmail.com",
                true, "123", "321", "321", "142412341234", "Adresa");
        String jsonDTO = mapper.writeValueAsString(registeredUserProfileUpdateDTO);

        mock.perform(put("/registeredUser/updateRegisteredUserData")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDTO))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("pera")));
    }

    @Test
    @Transactional
    @Rollback
    public void updateDataAndPasswordBadParametersTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token3);

        RegUserProfileUpdateDTO regUserProfileUpdateDTO = new RegUserProfileUpdateDTO("afsadf", "asdfasd", "asdfasf@gmail.com",
                true, "123", "321", "321", "asdfsadfasdf", "asfdasfd");
        String jsonDTO = mapper.writeValueAsString(regUserProfileUpdateDTO);

        mock.perform(put("/registeredUser/updateRegisteredUserData")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDTO))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @Rollback
    public void searchUserTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        mock.perform(get("/registeredUser/searchUsers/pera")
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.users", hasSize(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void searchUserInvalid() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        mock.perform(get("/registeredUser/searchUsers/zdravko")
                .headers(headers))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.users", hasSize(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void activateUserTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token3);

        mock.perform(get("/registeredUser/activateUser")
                .contentType(MediaType.TEXT_PLAIN)
                .content(token3)
                .headers(headers))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @Transactional
    @Rollback
    public void getFriendsTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        mock.perform(get("/registeredUser/getFriends")
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    @Rollback
    public void addFriendTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "vlada1";

        mock.perform(put("/registeredUser/addFriend")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.friendships", hasSize(2)))
                .andExpect(jsonPath("$.friendships.[0].username", is("balenko")))
                .andExpect(jsonPath("$.friendships.[0].status", is("Accepted")));
    }

    @Test
    @Rollback
    @Transactional
    public void alreadyFriendsTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "balenko";

        mock.perform(put("/registeredUser/addFriend")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @Transactional
    public void removeFriendTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "balenko";

        mock.perform(put("/registeredUser/deleteFriend")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.friendships", hasSize(1)));
    }

    @Test
    @Transactional
    @Rollback
    public void addNotExistingFriendTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "zdravko";

        mock.perform(put("/registeredUser/addFriend")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    public void acceptNotExistingFriendRequestTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "zdravko";

        mock.perform(put("/registeredUser/acceptRequest")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteNonExistingFriendTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "zdravko";

        mock.perform(put("/registeredUser/deleteFriend")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @Rollback
    public void deletingNotExistingFriendRequestTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token1);

        String friendUsername = "zdravko";

        mock.perform(put("/registeredUser/deleteRequest")
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(friendUsername))
                .andExpect(status().isForbidden());
    }
}
