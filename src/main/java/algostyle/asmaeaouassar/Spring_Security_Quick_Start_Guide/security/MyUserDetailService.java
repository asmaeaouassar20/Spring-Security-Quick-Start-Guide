package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.security;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.model.MyUser;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom implementation of UserDetailsService to load user-specific data from database
 *
 * This service interacts with the MyUserRepository to retrieve user information from DB based on the username provided during authentication
 * It maps MyUser entity form DB o Spring Security's UserDetails object for authentication and authorization
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;


    /**
     * Loads the user details from the database using the provided username
     *
     * If the user is found in the database, a UserDetails object is returned
     * If the user is not found, a UsernameNotFoundException is thrown
     * @param username the username of the user trying to log in
     * @return UserDetails containing the user's credentials and roles
     * @throws UsernameNotFoundException if the user is not found in the DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> optionalMyUserFromDB=myUserRepository.findByUsername(username);
        if(optionalMyUserFromDB.isPresent()){
            var myUserObjectFromDB=optionalMyUserFromDB.get();
            return User.builder()
                    .username(myUserObjectFromDB.getUsername())
                    .password(myUserObjectFromDB.getPassword())
                    .roles(getRoles(myUserObjectFromDB))
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    /**
     * Retrieves the roles of the user
     *
     * @param myUser
     * @return an array of roles assigned to the user
     */
    private String[] getRoles(MyUser myUser){
        if(myUser.getRole()==null){
            return new String[]{"USER"};
        }else{
            return myUser.getRole().split(",");
        }
    }
}
