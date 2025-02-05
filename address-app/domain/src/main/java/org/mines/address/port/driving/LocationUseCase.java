package org.mines.address.port.driving;

import org.mines.address.domain.model.Location;

public interface LocationUseCase {

    boolean isWithinRadius(Location locationRef, Location locationUser, double radiusKm);

}
