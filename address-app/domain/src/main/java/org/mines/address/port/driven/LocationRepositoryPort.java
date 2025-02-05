package org.mines.address.port.driven;

import org.mines.address.domain.model.Location;

import java.util.Optional;

public interface LocationRepositoryPort {
    Optional<Location> findByLatitudeAndLongitude(Location location);

    Location insert(Location location);
}
