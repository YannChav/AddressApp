package org.mines.address.domain.dto;

import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;

import java.util.Optional;

public record PhoneSearchRequestDto(Optional<Integer> age, Optional<Gender> gender, Optional<Location> location, Optional<Integer> radius, Optional<Integer> limit) {
    public static final class PhoneSearchRequestDtoBuilder {
        private Optional<Integer> age = Optional.empty();
        private Optional<Gender> gender = Optional.empty();
        private Optional<Location> location = Optional.empty();
        private Optional<Integer> radius = Optional.empty();
        private Optional<Integer> limit = Optional.of(10);

        private PhoneSearchRequestDtoBuilder() {}

        public static PhoneSearchRequestDtoBuilder aPhoneSearchRequestDto() {
            return new PhoneSearchRequestDtoBuilder();
        }

        public PhoneSearchRequestDtoBuilder withAge(Optional<Integer> age) {
            this.age = age;
            return this;
        }

        public PhoneSearchRequestDtoBuilder withRadius(Optional<Integer> radius) {
            this.radius = radius;
            return this;
        }


        public PhoneSearchRequestDtoBuilder withGender(Optional<Gender> gender) {
            this.gender = gender;
            return this;
        }

        public PhoneSearchRequestDtoBuilder withLocation(Optional<Location> location) {
            this.location = location;
            return this;
        }

        public PhoneSearchRequestDtoBuilder withLimit(Optional<Integer> limit) {
            this.limit = limit;
            return this;
        }

        public PhoneSearchRequestDto build() {
            return new PhoneSearchRequestDto(age, gender, location, radius, limit);
        }
    }
}