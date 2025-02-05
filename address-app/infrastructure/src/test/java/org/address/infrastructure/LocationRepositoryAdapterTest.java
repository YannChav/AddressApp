package org.address.infrastructure;


import org.address.infrastructure.config.PersistenceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mines.address.domain.model.Location;
import org.mines.address.port.driven.LocationRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestExecutionListeners({SqlScriptsTestExecutionListener.class, TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(classes = {PersistenceTestConfig.class})
@Sql(scripts = {"/data/clear.sql", "/data/data.sql"})
class LocationRepositoryAdapterTest {

    @Autowired
    private LocationRepositoryPort locationRepository;

    @Test
    public void shouldInsertLocation() {
        // GIVEN
        Location location = Location.LocationBuilder
                .aLocation()
                .withLatitude(47.0)
                .withLongitude(-1.0)
                .build();

        // WHEN
        Location persisted = locationRepository.insert(location);

        // THEN
        assertNotNull(persisted.id());
        assertEquals(47.0, persisted.latitude());
        assertEquals(-1.0, persisted.longitude());
    }

    @Test
    void shouldGetALocationByLatitudeAndLongitude() {
        // GIVEN
        Location location = Location.LocationBuilder
                .aLocation()
                .withLatitude(51.5074)
                .withLongitude(-0.1278)
                .build();

        // WHEN
        Optional<Location> selected = locationRepository.findByLatitudeAndLongitude(location);

        // THEN
        assertEquals(true, selected.isPresent());
        assertEquals(51.5074, selected.get().latitude());
        assertEquals(-0.1278, selected.get().longitude());
    }


}