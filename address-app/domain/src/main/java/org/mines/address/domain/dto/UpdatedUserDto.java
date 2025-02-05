package org.mines.address.domain.dto;

import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;

import java.util.Optional;
import java.util.UUID;

public record UpdatedUserDto(UUID id, Optional<String> firstname, Optional<String> lastname, Optional<Location> location, Optional<String> birthDate, Optional<String> phone, Optional<String> email, Optional<Gender> gender) {
    public static final class UpdatedUserDtoBuilder {
        private UUID id;
        private Optional<String> firstname = Optional.empty();
        private Optional<String> lastname = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<String> birthDate = Optional.empty();
        private Optional<String> phone = Optional.empty();
        private Optional<String> email = Optional.empty();
        private Optional<Gender> gender = Optional.empty();

        private UpdatedUserDtoBuilder() {}

        public static UpdatedUserDtoBuilder anUpdatedUserDto() {
            return new UpdatedUserDtoBuilder();
        }

        public UpdatedUserDtoBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public UpdatedUserDtoBuilder withFirstname(Optional<String> firstname) {
            this.firstname = firstname;
            return this;
        }

        public UpdatedUserDtoBuilder withLastname(Optional<String> lastname) {
            this.lastname = lastname;
            return this;
        }

        public UpdatedUserDtoBuilder withLocation(Optional<Location> location) {
            this.location = location;
            return this;
        }

        public UpdatedUserDtoBuilder withBirthDate(Optional<String> birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UpdatedUserDtoBuilder withPhone(Optional<String> phone) {
            this.phone = phone;
            return this;
        }

        public UpdatedUserDtoBuilder withEmail(Optional<String> email) {
            this.email = email;
            return this;
        }

        public UpdatedUserDtoBuilder withGender(Optional<Gender> gender) {
            this.gender = gender;
            return this;
        }

        public UpdatedUserDto build() {
            return new UpdatedUserDto(id, firstname, lastname, location, birthDate, phone, email, gender);
        }
    }
}