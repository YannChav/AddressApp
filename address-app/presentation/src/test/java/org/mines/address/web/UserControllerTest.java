package org.mines.address.web;

import org.junit.jupiter.api.Test;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.User;
import org.mines.address.port.driving.UserUseCase;
import org.mines.address.web.config.WebTestConfig;
import org.mines.address.web.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@Import(WebTestConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserUseCase userUseCase;

    @Test
    void shouldGetAnUser() throws Exception {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        when(userUseCase.get(uuid)).thenReturn(Optional.of(
                User.UserBuilder
                        .anUser()
                        .withId(uuid)
                        .withFirstname("John")
                        .withLastname("Doe")
                        .withGender(Gender.M)
                        .withPhone("123456789")
                        .withEmail("john@doe.fr")
                        .withLocation(Location.LocationBuilder
                                .aLocation()
                                .withId(1)
                                .withLatitude(49.1)
                                .withLongitude(-2.12)
                                .build()
                        )
                        .withBirthDate("2003-08-08")
                        .build()
        ));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/%s".formatted(uuid.toString()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@doe.fr"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("M"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.latitude").value(49.1));

    }

    @Test
    void shouldNotGetInstanceData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/%s".formatted(UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void shouldCreateAnUser() throws Exception {
        UUID id = UUID.randomUUID();
        when(userUseCase.insert(any())).thenReturn(
                User.UserBuilder
                        .anUser()
                        .withId(id)
                        .withFirstname("John")
                        .withLastname("Doe")
                        .withGender(Gender.M)
                        .withPhone("123456789")
                        .withEmail("john@doe.fr")
                        .withLocation(Location.LocationBuilder
                                .aLocation()
                                .withId(1)
                                .withLatitude(49.1)
                                .withLongitude(-2.12)
                                .build()
                        )
                        .withBirthDate("2003-08-08")
                        .build()
        );

        String jsonContent = """
                {
                    "firstname": "John",
                    "lastname": "Doe",
                    "gender": "M",
                    "phone": "123456789",
                    "email": "john@doe.fr",
                    "birthdate": "2003-08-08",
                    "location": {
                        "id": "1"
                        "latitude": "49.1"
                        "longitude": "-2.12"
                    }
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user").content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));

        verify(userUseCase).insert(any());
    }
}
