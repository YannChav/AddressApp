package org.mines.address.domain.dto;

import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;

public record CreatedUserDto(String firstname, String lastname, Location location, String birthDate, String phone, String email, Gender gender) {
    public static final class CreatedUserDtoBuilder {
        private String firstname;
        private String lastname;
        private Location location;
        private String birthDate;
        private String phone;
        private String email;
        private Gender gender;

        private CreatedUserDtoBuilder() {}

        public static CreatedUserDto.CreatedUserDtoBuilder aCreatedUserDto() {
            return new CreatedUserDto.CreatedUserDtoBuilder();
        }

        public CreatedUserDto.CreatedUserDtoBuilder withFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CreatedUserDto.CreatedUserDtoBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public CreatedUserDto build() {
            return new CreatedUserDto(firstname, lastname, location, birthDate, phone, email, gender);
        }
    }
}