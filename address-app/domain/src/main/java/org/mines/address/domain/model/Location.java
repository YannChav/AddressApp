package org.mines.address.domain.model;

public record Location(Integer id, Double latitude, Double longitude) {

    public static final class LocationBuilder {
        private Integer id;
        private Double latitude;
        private Double longitude;

        private LocationBuilder() {}

        public static LocationBuilder aLocation() {
            return new LocationBuilder();
        }

        public LocationBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public LocationBuilder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public LocationBuilder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Location build() {
            return new Location(id, latitude, longitude);
        }
    }
}
