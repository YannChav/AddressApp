package org.mines.address.domain.service;

import org.mines.address.domain.dto.PhoneSearchRequestDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.Phone;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.UserRepositoryPort;
import org.mines.address.port.driving.PhoneUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneService implements PhoneUseCase {
    @Autowired
    private UserRepositoryPort userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    public PhoneService() { }

    private boolean filterPhonesByAge(Phone phone, Integer age) {
        return phone.age() <= age;
    }

    @Override
    public List<Phone> searchPhones(PhoneSearchRequestDto phoneSearchRequestDto) {
        UserSearchRequestDto userSearchRequestDto = UserSearchRequestDto.UserSearchRequestDtoBuilder
                .aUserSearchRequestDto()
                .withGender(phoneSearchRequestDto.gender())
                .withLimit(phoneSearchRequestDto.limit())
                .withLocation(phoneSearchRequestDto.location())
                .withRadius(phoneSearchRequestDto.radius())
                .build();

        List<User> users = userService.searchUsers(userSearchRequestDto);
        List<Phone> phones = users.stream().map(userPhone -> Phone.PhoneBuilder
                .aPhone()
                .withAge(userService.getUserAge(userPhone))
                .withFirstname(userPhone.firstname())
                .withLastname(userPhone.lastname())
                .withGender(userPhone.gender())
                .withLocation(userPhone.location())
                .withPhone(userPhone.phone())
                .build()
        ).collect(Collectors.toList());

        if (phoneSearchRequestDto.age().isPresent()) {
            phones = phones
                .stream()
                .filter(phone -> this.filterPhonesByAge(phone, phoneSearchRequestDto.age().get()))
                .toList();
        }
        return phones;
    }
}
