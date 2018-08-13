package nx.ESE.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import nx.ESE.documents.User;
import nx.ESE.repositories.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class DatabaseSeederServiceIT {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testUserSeedDatabase() {
        // this.databaseSeederService.deleteAllAndCreateAdmin();
        // this.databaseSeederService.seedDatabase("ESE-db-test.yml");
        User user = userRepository.findByUsername("u010");
        assertNotNull(user);
        assertEquals("u010", user.getUsername());
        assertEquals("e010@email.com", user.getEmail());
        assertEquals("13755572-7", user.getDni());
        assertTrue(user.getRoles().length >= 2);
    }
    
}
