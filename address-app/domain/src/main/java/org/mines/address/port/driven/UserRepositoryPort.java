package org.mines.address.port.driven;

import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    Optional<User> select(UUID uuid);

    Collection<User> selectAll();

    User insert(User user);

    User update(User user);

    void delete(UUID uuid);

    List<User> searchUsers(UserSearchRequestDto userSearchRequestDto);
}