package org.mines.address.infrastructure.repository;

import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.UserBuilder
                .anUser()
                .withId(UUID.fromString(rs.getString("id")))
                .withFirstname(rs.getString("firstname"))
                .withLastname(rs.getString("lastname"))
                .withGender(Gender.valueOf(rs.getString("gender")))
                .withPhone(rs.getString("phone"))
                .withEmail(rs.getString("email"))
                .withBirthDate(rs.getString("birth_date"))
                .withLocation(Location.LocationBuilder
                        .aLocation()
                        .withId(rs.getInt("location_id"))
                        .withLatitude(rs.getDouble("latitude"))
                        .withLongitude(rs.getDouble("longitude"))
                        .build())
                .build();
    }
}
