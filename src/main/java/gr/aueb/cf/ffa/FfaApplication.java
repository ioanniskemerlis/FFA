package gr.aueb.cf.ffa;


import gr.aueb.cf.ffa.model.User;
import gr.aueb.cf.ffa.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FfaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FfaApplication.class, args);
    }

    @Component
    public class TestUserRepository {

        @Bean
        CommandLineRunner testUserRepo(UserRepository userRepository) {
            return args -> {
                // Create a sample user
                User newUser = new User(null, "freelancer123", "freelancer@example.com", "securepassword", "USER");

                // Save the user to the database
                User savedUser = userRepository.save(newUser);
                System.out.println("Saved User: " + savedUser);

                // Find the user by username
                userRepository.findByUsername("freelancer123")
                        .ifPresent(user -> System.out.println("Found User: " + user));
            };
        }
    }
}
