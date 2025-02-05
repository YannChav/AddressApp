package org.mines.address.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mines.address.domain.dto.PhoneSearchRequestDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Phone;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.UserRepositoryPort;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {
    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private UserService userService;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    void shouldGetPhones() {
        // GIVEN
        PhoneSearchRequestDto phoneSearchRequestDto = PhoneSearchRequestDto.PhoneSearchRequestDtoBuilder
                .aPhoneSearchRequestDto()
                .withAge(Optional.of(18))
                .withGender(Optional.of(Gender.M))
                .withLimit(Optional.of(25))
                .build();

        when(userService.searchUsers(any())).thenReturn(
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
        List<Phone> phones = phoneService.searchPhones(phoneSearchRequestDto);

        // THEN
        assertEquals(2, phones.size());
    }
}