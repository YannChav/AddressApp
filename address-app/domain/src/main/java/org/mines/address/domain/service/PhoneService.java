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
        List<Phone> phones = new ArrayList<>();

        for (User user : users) {
            Phone phone = Phone.PhoneBuilder
                    .aPhone()
                    .withAge(userService.getUserAge(user))
                    .withFirstname(user.firstname())
                    .withLastname(user.lastname())
                    .withGender(user.gender())
                    .withLocation(user.location())
                    .withPhone(user.phone())
                    .build();
            phones.add(phone);
        }
        return phones;
    }
}
