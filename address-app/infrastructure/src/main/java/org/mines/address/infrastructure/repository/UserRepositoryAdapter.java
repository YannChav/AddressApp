package org.mines.address.infrastructure.repository;

import org.mines.address.domain.dto.UserSearchRequestDto;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> select(UUID uuid) {
        StringBuilder sqlGetUser = new StringBuilder();
        sqlGetUser.append("SELECT ");
        sqlGetUser.append("u.id, u.firstname, u.lastname, u.gender, u.phone, u.email, u.birth_date, u.location_id, " );
        sqlGetUser.append("l.latitude, l.longitude ");
        sqlGetUser.append("from users u ");
        sqlGetUser.append("inner join locations l ON u.location_id = l.id ");
        sqlGetUser.append("where u.id = ? ");

        return this.jdbcTemplate.query(sqlGetUser.toString(), new UserRowMapper(), uuid)
                .stream()
                .findFirst();
    }

    @Override
    public Collection<User> selectAll() {
        StringBuilder sqlSelectAllUsers = new StringBuilder();
        sqlSelectAllUsers.append("SELECT ");
        sqlSelectAllUsers.append("u.id, u.firstname, u.lastname, u.gender, u.phone, u.email, u.birth_date, u.location_id, " );
        sqlSelectAllUsers.append("l.latitude, l.longitude ");
        sqlSelectAllUsers.append("from users u ");
        sqlSelectAllUsers.append("inner join locations l ON u.location_id = l.id ");

        return jdbcTemplate.query(sqlSelectAllUsers.toString(), new UserRowMapper());
    }

    @Override
    public User insert(User user) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(Objects.requireNonNull(jdbcTemplate.getDataSource())).withTableName("users");
        final UUID uuid = UUID.randomUUID();

        final SqlParameterSource sqlInsertUser = new MapSqlParameterSource()
                .addValue("id", uuid)
                .addValue("firstname", user.firstname())
                .addValue("lastname", user.lastname())
                .addValue("location_id", user.location().id())
                .addValue("birth_date", user.birthDate())
                .addValue("phone", user.phone())
                .addValue("email", user.email())
                .addValue("gender", user.gender());

        simpleJdbcInsert.execute(sqlInsertUser);

        return User.UserBuilder.anUser()
                .withId(uuid)
                .withFirstname(user.firstname())
                .withLastname(user.lastname())
                .withLocation(user.location())
                .withBirthDate(user.birthDate())
                .withPhone(user.phone())
                .withEmail(user.email())
                .withGender(user.gender())
                .build();
    }

    @Override
    public User update(User user) {
        StringBuilder sqlUpdateUser = new StringBuilder();
        sqlUpdateUser.append("UPDATE users SET ");
        sqlUpdateUser.append("firstname = ?, ");
        sqlUpdateUser.append("lastname = ?, ");
        sqlUpdateUser.append("gender = ?, ");
        sqlUpdateUser.append("phone = ?, ");
        sqlUpdateUser.append("email = ?, ");
        sqlUpdateUser.append("birth_date = ?, ");
        sqlUpdateUser.append("location_id = ? " );

        jdbcTemplate.update(sqlUpdateUser.toString(),
                user.firstname(),
                user.lastname(),
                user.gender().toString(),
                user.phone(),
                user.email(),
                LocalDate.parse(user.birthDate()),
                user.location().id()
        );

        return user;
    }

    @Override
    public void delete(UUID uuid) {
        StringBuilder sqlDeleteUser = new StringBuilder();
        sqlDeleteUser.append("DELETE FROM users u ");
        sqlDeleteUser.append("WHERE ");
        sqlDeleteUser.append("u.id = ?");

        jdbcTemplate.update(sqlDeleteUser.toString(), uuid);
    }

    @Override
    public List<User> searchUsers(UserSearchRequestDto userSearchRequestDto) {
        StringBuilder sqlSearchUsers = new StringBuilder();
        sqlSearchUsers.append("SELECT ");
        sqlSearchUsers.append("u.id, u.firstname, u.lastname, u.gender, u.phone, u.email, u.birth_date, u.location_id, " );
        sqlSearchUsers.append("l.latitude, l.longitude ");
        sqlSearchUsers.append("from users u ");
        sqlSearchUsers.append("inner join locations l ON u.location_id = l.id ");

        if (userSearchRequestDto.gender().isPresent()) {
            sqlSearchUsers.append("WHERE u.gender = ? ");
            sqlSearchUsers.append("LIMIT ?");

            return this.jdbcTemplate.query(sqlSearchUsers.toString(),
                    new UserRowMapper(),
                    userSearchRequestDto.gender().get().toString(),
                    userSearchRequestDto.limit().get()
            );
        } else {
            sqlSearchUsers.append("LIMIT ?");

            return this.jdbcTemplate.query(sqlSearchUsers.toString(),
                    new UserRowMapper(),
                    userSearchRequestDto.limit().get()
            );
        }
    }
}
