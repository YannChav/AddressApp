package org.mines.address.port.driving;

import org.mines.address.domain.dto.CreatedUserDto;
import org.mines.address.domain.dto.UpdatedUserDto;
import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.User;

import java.util.*;
import java.util.UUID;

public interface UserUseCase {

    User insert(CreatedUserDto createdUserDto);

    Optional<User> update(UpdatedUserDto user);

    Optional<User> get(UUID uuid);

    List<User> searchUsers(UserSearchRequestDto userSearchRequestDto);

    void delete(UUID uuid);

    int getUserAge(User user);
}
