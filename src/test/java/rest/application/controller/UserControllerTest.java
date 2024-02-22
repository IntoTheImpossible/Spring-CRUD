package rest.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rest.application.model.User;
import rest.application.repository.UserRepository;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;
    @Autowired
    private ObjectMapper objectMapper;
    private ResultActions resultActions;
    private ResultActions resultActions2;


    public User user = User.builder().username("username1").name("name1").email("mail1").build();
    public User user2 = User.builder().username("username2").name("name2").email("mail2").build();

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        user.setId(1L);
        user2.setId(2L);
    }

    @Test
    void newUser() throws Exception {
        when(repository.save(user)).thenReturn(user);

        resultActions = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void getAllUsers() throws Exception {
        repository.save(user);
        repository.save(user2);

        when(repository.findAll()).thenReturn(List.of(user, user2));

        resultActions = mockMvc.perform(get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        user.setId(1L);
        repository.save(user);

        when(repository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        resultActions = mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    void updateUser() throws Exception {
        repository.save(user);

        when(repository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        user.setUsername("newUser");
        when(repository.save(user)).thenReturn(user);

        resultActions = mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newUser\",\"name\":\"user\",\"email\":\"mail\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("newUser"));
    }

    @Test
    void deleteUser() throws Exception {
        repository.save(user);

        when(repository.existsById(user.getId())).thenReturn(true);

        resultActions = mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user with " + user.getId()
                        + "has been deleted successfully"));
    }

    @Test
    void doesUserExistByUsername() throws Exception {
        repository.save(user);

        when(repository.findByUsername(user.getUsername())).thenReturn(user);

        resultActions = mockMvc.perform(get("/users/check/username/")
                        .param("username", user.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));


        resultActions2 = mockMvc.perform(get("/users/check/username/")
                        .param("username", "user3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));


    }

    @Test
    void doesUserExistByEmail() throws Exception {
        repository.save(user);

        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        resultActions = mockMvc.perform(get("/users/check/email/")
                        .param("email", user.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        resultActions2 = mockMvc.perform(get("/users/check/email/")
                        .param("email", "mail3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }
}