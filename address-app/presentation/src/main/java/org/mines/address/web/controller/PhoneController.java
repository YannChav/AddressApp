package org.mines.address.web.controller;

import org.mines.address.domain.dto.PhoneSearchRequestDto;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.Phone;
import org.mines.address.port.driving.PhoneUseCase;
import org.mines.contact.api.controller.PhoneApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class PhoneController implements PhoneApi {

    private final PhoneUseCase phoneUseCase;

    @Autowired
    public PhoneController(PhoneUseCase phoneUseCase) {
        this.phoneUseCase = phoneUseCase;
    }

    @Override
    public ResponseEntity<List<org.mines.contact.api.model.Phone>> phonesSearch(
            Integer age,
            org.mines.contact.api.model.Gender gender,
            org.mines.contact.api.model.Location location,
            Integer radius,
            Integer limit
    ) {
        PhoneSearchRequestDto phoneSearchRequestDto = PhoneSearchRequestDto.PhoneSearchRequestDtoBuilder
                .aPhoneSearchRequestDto()
                .withAge(Optional.ofNullable(age))
                .withGender(Optional.ofNullable(gender == null ? null : Gender.valueOf(gender.getValue())))
                .withLocation(Optional.ofNullable(location == null ? null : this.map(location)))
                .withRadius(Optional.ofNullable(radius))
                .withLimit(Optional.ofNullable(limit))
                .build();

        return ResponseEntity.ok(phoneUseCase.searchPhones(phoneSearchRequestDto)
                .stream()
                .map(this::map)
                .collect(Collectors.toList()));
    }

    // Map from phone domain to phone API
    private org.mines.contact.api.model.Phone map(Phone phone) {
        org.mines.contact.api.model.Phone apiPhone = new org.mines.contact.api.model.Phone();
        apiPhone.setFirstname(phone.firstname());
        apiPhone.setLastname(phone.lastname());
        apiPhone.setGender(org.mines.contact.api.model.Gender.valueOf(phone.gender().name()));
        apiPhone.setPhone(phone.phone());
        apiPhone.setAge(phone.age());
        apiPhone.setLocation(this.map(phone.location()));

        return apiPhone;
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
