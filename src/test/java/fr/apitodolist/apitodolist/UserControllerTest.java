package fr.apitodolist.apitodolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.apitodolist.apitodolist.config.error.FunctionalException;
import fr.apitodolist.apitodolist.dto.login.SuccessFullLoginDto;
import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import fr.apitodolist.apitodolist.service.impl.UtilisateurService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.profiles.active = test"})
class UserControllerTest {

    @Autowired
    IUtilisateurRepository iUtilisateurRepository;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void cleanDataBase() {
        this.iUtilisateurRepository.deleteAll();

    }

    private String authenticateAndGetToken(String username, String password) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String loginUrl = "/login";
        String requestBody = objectMapper.writeValueAsString(Map.of("username", username, "password", password));
        MvcResult result = mvc.perform(post(loginUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        SuccessFullLoginDto successFullLoginDto = objectMapper.readValue(responseContent, SuccessFullLoginDto.class);
        System.out.println(successFullLoginDto.token());
        return successFullLoginDto.token();
    }




    @Nested
    class Create {
        @Test
        @DisplayName("it should throw an exception when the user already exist")
        void createUserAlreadyInBase() throws Exception {

            CreateUtilisateurDto userInBase = new CreateUtilisateurDto("test", "test");
            CreateUtilisateurDto userTest = new CreateUtilisateurDto("test", "test");
            utilisateurService.create(userInBase);

            mvc.perform(post("/utilisateurs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userTest)))
                    .andExpect(status().isConflict())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof FunctionalException))
                    .andExpect(result -> assertEquals("Login already exist",  result.getResolvedException().getMessage()));
        }

        @Test
        @DisplayName("it should throw an exception when login is null in dto")
        void createUserWithNoLogin() throws Exception {

            CreateUtilisateurDto userTest = new CreateUtilisateurDto("", "test");

            mvc.perform(post("/utilisateurs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(userTest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof FunctionalException))
                    .andExpect(result -> assertEquals("Login is null",  result.getResolvedException().getMessage()));
        }
    }

    @Nested
    class fetch {
        @Test
        @DisplayName("it should throw an exception when the user already exist")
        void fetchUserByLogin() throws Exception {

            CreateUtilisateurDto userInBase = new CreateUtilisateurDto("test", "test");
            utilisateurService.create(userInBase);
            UtilisateurDto UserInBaseComplete = utilisateurService.fetchByLogin(userInBase.login());

            //recuperer un token
            String token = authenticateAndGetToken(UserInBaseComplete.login(),"test");

            mvc.perform(get("/utilisateurs/" + UserInBaseComplete.login())
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", token))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertEquals(new ObjectMapper().writeValueAsString(UserInBaseComplete), result.getResponse().getContentAsString()));
        }
    }

}
