package org.address.infrastructure;

import org.address.infrastructure.config.PersistenceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mines.address.domain.model.Gender;
import org.mines.address.domain.model.Location;
import org.mines.address.domain.model.User;
import org.mines.address.port.driven.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestExecutionListeners({SqlScriptsTestExecutionListener.class, TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(classes = {PersistenceTestConfig.class})
@Sql(scripts = {"/data/clear.sql", "/data/data.sql"})
public class UserRepositoryAdapterTest {

    @Autowired
    private UserRepositoryPort userRepository;

    @Test
    public void shouldInsertAnUserWithValidEmail() {
        // GIVEN
        User user = User.UserBuilder
                .anUser()
                .withFirstname("John")
                .withLastname("Doe")
                .withGender(Gender.M)
                .withEmail("john@doe.com")
                .withPhone("123456789")
                .withLocation(Location.LocationBuilder
                        .aLocation()
                        .withId(1)
                        .withLatitude(48.8566)
                        .withLongitude(2.3522)
                        .build())
                .withBirthDate("2003-12-31")
                .build();

        // WHEN
        User persisted = userRepository.insert(user);

        // THEN
        assertNotNull(persisted.id());
        assertEquals("john@doe.com", persisted.email());
        assertEquals(48.8566, persisted.location().latitude());

        Collection<User> users = userRepository.selectAll();
        assertEquals(6, users.size());
        assertEquals("john@doe.com", users.stream()
                .filter(entry -> entry.id().equals(persisted.id()))
                .findFirst()
                .map(User::email).orElse(""));
    }

    @Test
    public void shouldNotInsertAnUserWithAnEmailAlreadyUse() {
        // GIVEN
        User user = User.UserBuilder
                .anUser()
                .withFirstname("John")
                .withLastname("Doe")
                .withGender(Gender.M)
                .withEmail("john.doe@example.com")
                .withPhone("123456789")
                .withLocation(Location.LocationBuilder
                        .aLocation()
                        .withLatitude(49.0)
                        .withLongitude(2.0)
                        .build())
                .withBirthDate("2003-12-31")
                .build();

        // WHEN

        // THEN
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.insert(user);
        });
    }

}
