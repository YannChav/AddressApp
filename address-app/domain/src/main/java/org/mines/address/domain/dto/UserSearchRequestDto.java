package org.mines.address.domain.dto;

import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;

import java.util.Optional;
import java.util.UUID;

public record UserSearchRequestDto(Optional<Gender> gender, Optional<Location> location, Optional<Integer> radius, Optional<Integer> limit) {
    public static final class UserSearchRequestDtoBuilder {
        private Optional<Gender> gender = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<Integer> radius = Optional.empty();
        private Optional<Integer> limit = Optional.of(10);
        private UserSearchRequestDtoBuilder() {}

        public static UserSearchRequestDtoBuilder aUserSearchRequestDto() {
            return new UserSearchRequestDtoBuilder();
        }

        public UserSearchRequestDtoBuilder withGender(Optional<Gender> gender) {
            this.gender = gender;
            return this;
        }

        public UserSearchRequestDtoBuilder withLocation(Optional<Location> location) {
            this.location = location;
            return this;
        }

        public UserSearchRequestDtoBuilder withRadius(Optional<Integer> radius) {
            this.radius = radius;
            return this;
        }

        public UserSearchRequestDtoBuilder withLimit(Optional<Integer> limit) {
            this.limit = limit.isEmpty() ? Optional.of(25) : limit;
            return this;
        }

        public UserSearchRequestDto build() {
            return new UserSearchRequestDto(gender, location, radius, limit);
        }
    }
}