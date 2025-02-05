package org.mines.address.domain.model;

public record Phone(Integer age, String firstname, String lastname, Gender gender, Location location, String phone) {

    public static final class PhoneBuilder {
        private Integer age;
        private String firstname;
        private String lastname;
        private Gender gender;
        private Location location;
        private String phone;

        private PhoneBuilder() {
        }

        public static PhoneBuilder aPhone() {
            return new PhoneBuilder();
        }

        public PhoneBuilder withAge(Integer age) {
            this.age = age;
            return this;
        }

        public PhoneBuilder withFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public PhoneBuilder withLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public PhoneBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public PhoneBuilder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public PhoneBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Phone build() {
            return new Phone(age, firstname, lastname, gender, location, phone);
        }
    }
}
