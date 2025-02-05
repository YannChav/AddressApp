package org.mines.address.web.controller;

import org.mines.address.domain.dto.CreatedUserDto;
import org.mines.address.domain.dto.UpdatedUserDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.port.driving.UserUseCase;
import org.mines.contact.api.controller.UserApi;
import org.mines.address.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserController implements UserApi {

    private final UserUseCase userUseCase;

    @Autowired
    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        userUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<org.mines.contact.api.model.User> getUserById(UUID id) {
        Optional<org.mines.contact.api.model.User> userApi = userUseCase.get(id)
                .map(this::map);

        if (userApi.isPresent()) {
            return ResponseEntity.ok().body(userApi.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<org.mines.contact.api.model.User> userAdd(org.mines.contact.api.model.CreatedUserDto createdUserDto) {
        User user = userUseCase.insert(this.map(createdUserDto));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(user.id()).toUri();

        return ResponseEntity.created(uri).body(this.map(user));
    }

    @Override
    public ResponseEntity<List<org.mines.contact.api.model.User>> userSearch(
            org.mines.contact.api.model.Gender gender,
            org.mines.contact.api.model.Location location,
            Integer radius,
            Integer limit
    ) {
        UserSearchRequestDto userSearchRequestDto = UserSearchRequestDto.UserSearchRequestDtoBuilder
                .aUserSearchRequestDto()
                .withGender(Optional.ofNullable(gender == null ? null : Gender.valueOf(gender.getValue())))
                .withLocation(Optional.ofNullable(location == null ? null : this.map(location)))
                .withRadius(Optional.ofNullable(radius))
                .withLimit(Optional.ofNullable(limit))
                .build();

        List<User> users = userUseCase.searchUsers(userSearchRequestDto);

        return ResponseEntity.ok(
                users
                .stream()
                .map(this::map)
                .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<org.mines.contact.api.model.User> userUpdate(org.mines.contact.api.model.UpdatedUserDto updatedUserDto) {
        return userUseCase.update(this.map(updatedUserDto))
                .map(user -> ResponseEntity.ok(this.map(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Map from user API to user domain
    private User map(org.mines.contact.api.model.User user) {
        return User.UserBuilder.anUser()
                .withId(user.getId())
                .withFirstname(user.getFirstname())
                .withLastname(user.getLastname())
                .withGender(Gender.valueOf(user.getGender().getValue()))
                .withPhone(user.getPhone())
                .withEmail(user.getEmail())
                .withLocation(this.map(user.getLocation()))
                .withBirthDate(user.getBirthDate().toString())
                .build();
    }

    // Map from user domain to user API
    private org.mines.contact.api.model.User map(User user) {
        org.mines.contact.api.model.User apiUser = new org.mines.contact.api.model.User();
        apiUser.setId(user.id());
        apiUser.setFirstname(user.firstname());
        apiUser.setLastname(user.lastname());
        apiUser.setGender(org.mines.contact.api.model.Gender.valueOf(user.gender().toString()));
        apiUser.setPhone(user.phone());
        apiUser.setEmail(user.email());
        apiUser.setBirthDate(LocalDate.parse(user.birthDate()));
        apiUser.setLocation(this.map(user.location()));

        return apiUser;
    }

    // Map from Created User DTO API to Cretaed User DTO domain
    private CreatedUserDto map(org.mines.contact.api.model.CreatedUserDto createdUserDto) {
        return CreatedUserDto.CreatedUserDtoBuilder.aCreatedUserDto()
                .withFirstname(createdUserDto.getFirstname())
                .withLastname(createdUserDto.getLastname())
                .withGender(Gender.valueOf(createdUserDto.getGender().getValue()))
                .withPhone(createdUserDto.getPhone())
                .withEmail(createdUserDto.getEmail())
                .withLocation(this.map(createdUserDto.getLocation()))
                .withBirthDate(createdUserDto.getBirthDate().toString())
                .build();
    }

    // Map from Updated User DTO API to Uodated User DTO domain
    private UpdatedUserDto map(org.mines.contact.api.model.UpdatedUserDto updatedUserDto) {
        return UpdatedUserDto.UpdatedUserDtoBuilder.anUpdatedUserDto()
                .withId(updatedUserDto.getId())
                .withFirstname(Optional.ofNullable(updatedUserDto.getFirstname()))
                .withLastname(Optional.ofNullable(updatedUserDto.getLastname()))
                .withGender(Optional.ofNullable(updatedUserDto.getGender() == null ? null : Gender.valueOf(updatedUserDto.getGender().getValue())))
                .withPhone(Optional.ofNullable(updatedUserDto.getPhone()))
                .withEmail(Optional.ofNullable(updatedUserDto.getEmail()))
                .withLocation(Optional.ofNullable(updatedUserDto.getLocation() == null ? null : this.map(updatedUserDto.getLocation())))
                .withBirthDate(Optional.ofNullable(updatedUserDto.getBirthDate() == null ? null : updatedUserDto.getBirthDate().toString()))
                .build();
    }

    // Map from location API to location domain
    private Location map(org.mines.contact.api.model.Location location) {
        return Location.LocationBuilder.aLocation()
                .withId(location.getId())
                .withLatitude(location.getLatitude())
                .withLongitude(location.getLongitude())
                .build();
    }

    // Map from location domain to location API
    private org.mines.contact.api.model.Location map(Location location) {
        org.mines.contact.api.model.Location apiLocation = new org.mines.contact.api.model.Location();
        apiLocation.setId(location.id());
        apiLocation.setLatitude(location.latitude());
        apiLocation.setLongitude(location.longitude());

        return apiLocation;
    }
}
