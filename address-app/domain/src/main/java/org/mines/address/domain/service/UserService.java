package org.mines.address.domain.service;

import org.mines.address.domain.dto.CreatedUserDto;
import org.mines.address.domain.dto.UpdatedUserDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.LocationRepositoryPort;
import org.mines.address.port.driven.UserRepositoryPort;
import org.mines.address.port.driving.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserUseCase {
    @Autowired
    private UserRepositoryPort userRepository;

    @Autowired
    private LocationRepositoryPort locationRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private Clock clock;

    public UserService() { }

    @Override
    public User insert(CreatedUserDto createdUserDto) {
        Optional<Location> locationSearch = locationRepository.findByLatitudeAndLongitude(createdUserDto.location());

        Location location = locationSearch.orElse(locationRepository.insert(createdUserDto.location()));

        User user = User.UserBuilder
                .anUser()
                .withFirstname(createdUserDto.firstname())
                .withLastname(createdUserDto.lastname())
                .withGender(createdUserDto.gender())
                .withEmail(createdUserDto.email())
                .withPhone(createdUserDto.phone())
                .withBirthDate(createdUserDto.birthDate())
                .withLocation(location)
                .build();

        return userRepository.insert(user);
    }

    @Override
    public Optional<User> update(UpdatedUserDto updatedUserDto) {
        Optional<User> userSearch = userRepository.select(updatedUserDto.id());

        if (userSearch.isEmpty()) {
            return Optional.empty();
        }
        User currentUser = userSearch.get();

        User user = User.UserBuilder
                .anUser()
                .withFirstname(updatedUserDto.firstname().orElse(currentUser.firstname()))
                .withLastname(updatedUserDto.lastname().orElse(currentUser.lastname()))
                .withGender(updatedUserDto.gender().orElse(currentUser.gender()))
                .withEmail(updatedUserDto.email().orElse(currentUser.email()))
                .withPhone(updatedUserDto.phone().orElse(currentUser.phone()))
                .withBirthDate(updatedUserDto.birthDate().orElse(currentUser.birthDate()))
                .withLocation(updatedUserDto.location()
                        .map(loc -> locationRepository.findByLatitudeAndLongitude(loc)
                                .orElse(locationRepository.insert(loc)))
                        .orElse(currentUser.location()))
                .build();

        return Optional.of(userRepository.update(user));
    }

    @Override
    public Optional<User> get(UUID id) {
        return userRepository.select(id);
    }

    @Override
    public List<User> searchUsers(UserSearchRequestDto userSearchRequestDto) {
        List<User> users = userRepository.searchUsers(userSearchRequestDto);

        if (userSearchRequestDto.location().isEmpty() || userSearchRequestDto.radius().isEmpty()) {
            return users;
        } else {
            return users
                    .stream()
                    .filter(user -> locationService.filterUsersByLocation(user, userSearchRequestDto))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(id);
    }

    @Override
    public int getUserAge(User user) {
        if (user.birthDate() == null || user.birthDate().isEmpty()) {
            return -1; // Valeur par défaut si la date de naissance est absente
        }

        try {
            LocalDate birth = LocalDate.parse(user.birthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return Period.between(birth, LocalDate.now(clock)).getYears();
        } catch (Exception e) {
            return -1; // Gestion d'erreur si la date est mal formattée
        }
    }
}
