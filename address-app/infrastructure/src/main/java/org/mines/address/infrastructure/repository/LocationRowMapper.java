package org.mines.address.infrastructure.repository;

import org.mines.address.domain.model.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Location.LocationBuilder.aLocation()
                .withId(rs.getInt("id"))
                .withLatitude(rs.getDouble("latitude"))
                .withLongitude(rs.getDouble("longitude"))
                .build();
    }
}
