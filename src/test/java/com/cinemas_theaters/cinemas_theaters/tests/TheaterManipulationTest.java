package com.cinemas_theaters.cinemas_theaters.tests;


import com.cinemas_theaters.cinemas_theaters.domain.dto.TheatreDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserLoginDTO;
import com.cinemas_theaters.cinemas_theaters.domain.dto.UserRegistrationDTO;
import com.cinemas_theaters.cinemas_theaters.domain.enums.StructureType;
import com.cinemas_theaters.cinemas_theaters.repository.TheatreRepository;
import com.cinemas_theaters.cinemas_theaters.service.TheatreService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TheaterManipulationTest {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private TheatreRepository theatreRepository;

    private ObjectMapper mapper;
    private MockMvc mock;

    @Before
    public void setUp(){
        this.mock = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.mapper = new ObjectMapper();
    }

    @Test
    public void addTheaterTest() throws Exception {
        TheatreDTO theatreDTO = new TheatreDTO("Pozoriste Mladih", "Neka ulica u centru neki broj", "Pozoriste mladih okuplja mlade ljude da gledaju pozorisne predstave", 3.5, StructureType.Theatre,"blabla","Novi Sad");
        String theaterJSON = mapper.writeValueAsString(theatreDTO);

        mock.perform(post("/theatre", theatreDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(theaterJSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getTheaterByIdNotOk() throws Exception{
        Long id = Long.valueOf(122);
        mock.perform(get("/theatre/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getTheaterByIdOk() throws Exception{
        Long id = Long.valueOf(132);
        mock.perform(get("/theatre/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
//    @Test
//    public void registrationIncorrectPasswordTest() throws Exception {
//        UserRegistrationDTO userCorrect = new UserRegistrationDTO("Petar", "Petrovic", "pera", "12345", "54321", "pera@gmail.com" , "12312312", "Bulevar oslobodjenja 12, Novi Sad");
//
//        String userJsonCorrect = mapper.writeValueAsString(userCorrect);
//
//        mock.perform(post("/registeredUser/registration", userCorrect)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJsonCorrect)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void registrationUsernameAlreadyExistTest() throws Exception {
//        UserRegistrationDTO userCorrect = new UserRegistrationDTO("marko", "markovic", "mare", "1234", "1234", "mare@gmail.com" , "12312312", "Bulevar oslobodjenja 12, Novi Sad");
//
//        String userJsonCorrect = mapper.writeValueAsString(userCorrect);
//
//        mock.perform(post("/registeredUser/registration", userCorrect)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJsonCorrect)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void loginIncorrectTest() throws Exception {
//        UserLoginDTO userIncorrect = new UserLoginDTO("steva", "123456789");
//
//        String userJsonIncorrect = mapper.writeValueAsString(userIncorrect);
//
//        mock.perform(post("/user/login", userIncorrect)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJsonIncorrect)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void loginCorrectTest() throws Exception {
//        UserLoginDTO userCorrect = new UserLoginDTO("mare", "1234");
//
//        String userJsonCorrect = mapper.writeValueAsString(userCorrect);
//
//        mock.perform(post("/user/login", userCorrect)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJsonCorrect))
//                .andExpect(status().isOk());
//    }
//



}

