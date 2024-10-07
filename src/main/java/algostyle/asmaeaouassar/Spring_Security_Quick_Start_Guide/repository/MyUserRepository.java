package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.repository;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findByUsername(String username);
}
