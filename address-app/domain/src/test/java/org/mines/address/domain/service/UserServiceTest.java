package org.mines.address.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mines.address.domain.dto.CreatedUserDto;
import org.mines.address.domain.dto.PhoneSearchRequestDto;
import org.mines.address.domain.dto.UpdatedUserDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.Phone;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.LocationRepositoryPort;
import org.mines.address.port.driven.UserRepositoryPort;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private LocationRepositoryPort locationRepositoryPort;

    @Mock
    private LocationService locationService;

    @Mock
    private Clock clock;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveAUser() {
        // GIVEN
        CreatedUserDto createdUserDto = CreatedUserDto.CreatedUserDtoBuilder
                .aCreatedUserDto()
                .withFirstname("John")
                .withLastname("Doe")
                .withGender(Gender.M)
                .withEmail("john@doe.com")
                .withPhone("123456789")
                .withBirthDate("2003-08-08")
                .withLocation(Location.LocationBuilder
                        .aLocation()
                        .withLatitude(4.12)
                        .withLongitude(5.31)
                        .build())
                .build();

        UUID id = UUID.randomUUID();

        when(userRepositoryPort.insert(any())).thenReturn(
            User.UserBuilder.anUser()
                .withId(id)
                .withFirstname("John")
                .withLastname("Doe")
                .withGender(Gender.M)
                .withEmail("john@doe.com")
                .withPhone("123456789")
                .withBirthDate("2003-08-08")
                .withLocation(Location.LocationBuilder
                        .aLocation()
                        .withLatitude(4.12)
                        .withLongitude(5.31)
                        .build())
                .build());

        LocalDate fixedDate = LocalDate.of(2025, 2, 3);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);

        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        // WHEN
        User saved = userService.insert(createdUserDto);

        // THEN
       assertEquals(id, saved.id());
       assertEquals(21, userService.getUserAge(saved));
    }

    @Test
    void shouldUpdateAnUser() {
        // GIVEN
        UUID id = UUID.randomUUID();
        UpdatedUserDto updatedUserDto = UpdatedUserDto.UpdatedUserDtoBuilder
                .anUpdatedUserDto()
                .withId(id)
                .withFirstname(Optional.of("Julia"))
                .withGender(Optional.of(Gender.F))
                .withEmail(Optional.of("julia@doe.com"))
                .build();

        when(userRepositoryPort.update(any())).thenReturn(
                User.UserBuilder.anUser()
                        .withId(id)
                        .withFirstname("Julia")
                        .withLastname("Doe")
                        .withGender(Gender.F)
                        .withEmail("julia@doe.com")
                        .withPhone("123456789")
                        .withBirthDate("2003-08-08")
                        .withLocation(Location.LocationBuilder
                                .aLocation()
                                .withLatitude(4.12)
                                .withLongitude(5.31)
                                .build())
                        .build());

        when(userRepositoryPort.select(any())).thenReturn(
                Optional.of(User.UserBuilder.anUser()
                        .withId(id)
                        .withFirstname("Julia")
                        .withLastname("Doe")
                        .withGender(Gender.F)
                        .withEmail("julia@doe.com")
                        .withPhone("123456789")
                        .withBirthDate("2003-08-08")
                        .withLocation(Location.LocationBuilder
                                .aLocation()
                                .withLatitude(4.12)
                                .withLongitude(5.31)
                                .build())
                        .build()));

        LocalDate fixedDate = LocalDate.of(2025, 2, 3);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);

        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        // WHEN
        Optional<User> updated = userService.update(updatedUserDto);

        // THEN
        assertEquals(id, updated.get().id());
        assertEquals(21, userService.getUserAge(updated.get()));
        assertEquals("Julia", updated.get().firstname());
        assertEquals("Doe", updated.get().lastname());
        assertEquals("julia@doe.com", updated.get().email());
    }


    @Test
    void shouldGetUsers() {
        // GIVEN
        UserSearchRequestDto userSearchRequestDto = UserSearchRequestDto.UserSearchRequestDtoBuilder
                .aUserSearchRequestDto()
                .withGender(Optional.of(Gender.M))
                .withLimit(Optional.of(25))
                .build();

        when(userRepositoryPort.searchUsers(any())).thenReturn(
                List.of(
                        User.UserBuilder.anUser()
                                .withFirstname("John")
                                .withLastname("Doe")
                                .withGender(Gender.M)
                                .withEmail("john@doe.com")
                                .withPhone("123456789")
                                .build(),
                        User.UserBuilder.anUser()
                                .withFirstname("Julia")
                                .withLastname("Doe")
                                .withGender(Gender.F)
                                .withEmail("julia@doe.com")
                                .withPhone("123456789")
                                .build()
                ));

        // WHEN
        List<User> users = userService.searchUsers(userSearchRequestDto);

        // THEN
        assertEquals(2, users.size());
    }

    @Test
    void shouldRemoveAUser() {
        // GIVEN
        UUID uuid = UUID.randomUUID();

        // WHEN
        userService.delete(uuid);

        // THEN
        verify(userRepositoryPort, times(1)).delete(uuid);
    }


}
