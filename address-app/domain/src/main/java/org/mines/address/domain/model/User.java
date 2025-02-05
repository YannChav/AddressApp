package org.mines.address.domain.model;

import java.util.UUID;

public record User(UUID id, String firstname, String lastname, Location location, String birthDate, String phone, String email, Gender gender) {

  public static final class UserBuilder {
    private UUID id;
    private String firstname;
    private String lastname;
    private Location location;
    private String birthDate;
    private String phone;
    private String email;
    private Gender gender;

    private UserBuilder() {}

    public static UserBuilder anUser() {
      return new UserBuilder();
    }

    public UserBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public UserBuilder withFirstname(String firstname) {
      this.firstname = firstname;
      return this;
    }

    public UserBuilder withLastname(String lastname) {
      this.lastname = lastname;
      return this;
    }

    public UserBuilder withLocation(Location location) {
      this.location = location;
      return this;
    }

    public UserBuilder withBirthDate(String birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    public UserBuilder withPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public UserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder withGender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public User build() {
      return new User(id, firstname, lastname, location, birthDate, phone, email, gender);
    }
  }
}