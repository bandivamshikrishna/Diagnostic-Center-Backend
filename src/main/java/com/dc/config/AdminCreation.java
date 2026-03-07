package com.dc.config;

import com.dc.entity.UserAuthEntity;
import com.dc.exception.RoleNotFoundException;
import com.dc.repository.UserAuthRepository;
import com.dc.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AdminCreation implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthRepository userAuthRepository;

    public AdminCreation(UserRoleRepository userRoleRepository,PasswordEncoder passwordEncoder,
                         UserAuthRepository userAuthRepository){
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            if (!"admin-creation".equalsIgnoreCase(args[0]))
                return;
            if (userAuthRepository.existsByRole_RoleCode("AD")) {
                System.out.println("Admin User Already Exists");
                return;
            }

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Email : ");
            String email = scanner.nextLine();

            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            System.out.print("Enter Confirm Password : ");
            String confirmPassword = scanner.nextLine();


            if (!password.equalsIgnoreCase(confirmPassword)) {
                System.out.println("Password do not match");
                return;
            }

            UserAuthEntity userAuthEntity = new UserAuthEntity();
            userAuthEntity.setEmail(email);
            userAuthEntity.setPassword(passwordEncoder.encode(confirmPassword));
            userAuthEntity.setRole(userRoleRepository.findByRoleCode("AD").orElseThrow(
                    ()-> new RoleNotFoundException("Invalid Role")
            ));
            userAuthEntity.setActive(true);
            userAuthEntity.setLocked(false);
            userAuthEntity.setCreatedDate(LocalDate.now());
            userAuthRepository.save(userAuthEntity);
            System.out.println("Admin User Created Successfully...");
        }
    }
}
