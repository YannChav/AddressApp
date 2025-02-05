package org.mines.address.infrastructure.repository;

import org.mines.address.domain.model.Location;
import org.mines.address.port.driven.LocationRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class LocationRepositoryAdapter implements LocationRepositoryPort {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LocationRepositoryAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Location> findByLatitudeAndLongitude(Location location) {
        StringBuilder sqlFindLocationByLatAndLon = new StringBuilder();
        sqlFindLocationByLatAndLon.append("SELECT ");
        sqlFindLocationByLatAndLon.append("l.id, l.latitude, l.longitude ");
        sqlFindLocationByLatAndLon.append("FROM locations l ");
        sqlFindLocationByLatAndLon.append("WHERE ");
        sqlFindLocationByLatAndLon.append("l.latitude = ? ");
        sqlFindLocationByLatAndLon.append("AND l.longitude = ? ");

        return jdbcTemplate.query(sqlFindLocationByLatAndLon.toString(),
                new LocationRowMapper(),
                location.latitude(),
                location.longitude()).stream().findFirst();
    }

    @Override
    public Location insert(Location location) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(
                Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("locations")
                .usingGeneratedKeyColumns("id");

        final SqlParameterSource sqlInsertLocation = new MapSqlParameterSource()
                .addValue("latitude", location.latitude())
                .addValue("longitude", location.longitude());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(sqlInsertLocation);

        return Location.LocationBuilder.aLocation()
                .withId(generatedId.intValue())
                .withLatitude(location.latitude())
                .withLongitude(location.longitude())
                .build();
    }
}
