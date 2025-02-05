package org.mines.address.domain.service;

import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.User;
import org.mines.address.port.driving.LocationUseCase;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements LocationUseCase {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public boolean isWithinRadius(Location locationRef, Location locationUser, double radiusKm) {
        double dLat = Math.toRadians(locationUser.latitude() - locationRef.latitude());
        double dLon = Math.toRadians(locationUser.longitude() - locationRef.longitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(locationRef.latitude())) * Math.cos(Math.toRadians(locationUser.latitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c; // Distance en km

        return distance <= radiusKm;
    }

    public boolean filterUsersByLocation(User user, UserSearchRequestDto userSearchRequestDto) {
        // Vérifie si le paramètre rayon a été passé
        Location locationRef = userSearchRequestDto.location().get();
        Integer radius = userSearchRequestDto.radius().get();

        return this.isWithinRadius(locationRef, user.location(), radius);
    }
}
