package integration_tests;

import highload.lab1.Lab1Application;
import highload.lab1.configuration.AppConfig;
import highload.lab1.model.*;
import highload.lab1.model.dto.RoleDto;
import highload.lab1.model.dto.UserDto;
import highload.lab1.model.enums.Rarity;
import highload.lab1.security.jwt.WebSecurityConfig;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.hasSize;

@SpringBootTest(classes = {Lab1Application.class, AppConfig.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    public ObjectMapper objectMapper = new ObjectMapper();

    private final UserTestDto user = new UserTestDto("1", "1");

    @Test
    void testUserController_getAllUsers() throws Exception {
        String token = authorize();

        String responseJson = mockMvc.perform(get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("page_size", "10")
                        .param("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<User> users = objectMapper.readValue(responseJson, new TypeReference<List<User>>() {
        });

        assertThat(users, hasSize(5));
    }

    @Test
    @DirtiesContext
    void testAuthController_signUp() throws Exception {
        String token = authorize();
        UserDto userDto = UserDto.builder()
                .username("test")
                .password("test")
                .roles(List.of(new RoleDto("USER")))
                .build();

        String responseMessage = mockMvc.perform(post("/auth/sign_up")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String requestJson = objectMapper.writeValueAsString(new UserTestDto("test", "test"));

        String responseJwtToken = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("User created!", responseMessage);
        assertEquals(3, responseJwtToken.split("\\.").length);

    }

    @Test
    void testMarketController_getMarketById() throws Exception {
        String token = authorize();

        String responseJson = mockMvc.perform(get("/api/markets/14b91a2c-e3be-400d-8b6a-a891085770b2")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Market market = objectMapper.readValue(responseJson, Market.class);

    }

    @Test
    void testDriverController_getDriverById() throws Exception {
        String token = authorize();

        String responseJson = mockMvc.perform(get("/api/persons/fa271573-d16d-4d29-84f3-74cbefc578af")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Person person = objectMapper.readValue(responseJson, Person.class);

        assertEquals("1", person.getFirstname());
        assertEquals("1", person.getLastname());
        assertEquals(Rarity.LEGENDARY, person.getRarity());

    }

    @Test
    void testContentController_getAllDetails() throws Exception {
        String token = authorize();

        String responseJson = mockMvc.perform(get("/api/details")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("page_size", "10")
                        .param("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Content> details = objectMapper.readValue(responseJson, new TypeReference<List<Content>>() {
        });

        assertThat(details, hasSize(5));

    }

    @Test
    void testCardController_getAllCards() throws Exception {
        String token = authorize();

        String responseJson = mockMvc.perform(get("/api/cards")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .param("page_size", "10")
                        .param("page", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Card> cards = objectMapper.readValue(responseJson, new TypeReference<List<Card>>() {
        });

        assertThat(cards, hasSize(5));

    }


    private String authorize() throws Exception {
        String requestJson = objectMapper.writeValueAsString(user);

        return mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}

@AllArgsConstructor
class UserTestDto implements Serializable {
    public String username;
    public String password;
}