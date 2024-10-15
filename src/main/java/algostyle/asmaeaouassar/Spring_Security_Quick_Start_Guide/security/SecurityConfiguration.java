package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.security;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // To indicate that a class is a source of bean definitions for the application context in Spring, allowing for the configuration of various components and setting within the application
@EnableWebSecurity  // To activate web security in a Spring application, allowing the configuration of user authentication and authorization rules
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain for HTTP requests
     *
     * This method specifies acces rules for various URL patterns:
     * - Allows public access to "/home"
     * - Restricts access to "/user/**" to users with the "USER" role
     * - Restricts access to "/admin/**" to users with the "ADMIN" role
     * - Requires authentication for all other requests
     * - Enable form-based login, allowing access to the login page for all users
     * - Disable CSRF protection for the application
     *
     * @param httpSecurity the HttpSecutity object used to configure security setting
     * @return a SecurityFilterChain object that contains the defined security rules
     * @throws Exception if an error occurs during the configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpSecurityRegistry->{
            httpSecurityRegistry.requestMatchers("/home","/register/**","/authenticate").permitAll();
            httpSecurityRegistry.requestMatchers("/user/**").hasRole("USER");
            httpSecurityRegistry.requestMatchers("/admin/**").hasRole("ADMIN");
            httpSecurityRegistry.anyRequest().authenticated();
        }).formLogin( //Specify the custom login page URL
                httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .successHandler(new AuthenticationSuccessHandler())
                        .permitAll()
             ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /**
     * Configures an in-memory user details service with predefined users.
     *
     * This method defines two users:
     * - A normal user with username "achraf", a hashed password, and the "USER" role
     * - An admin user with username "asmae", a hashed password, and both "USER" and "ADMIN" roles
     * The user details are stored in memory and managed by Spring Security
     *
     * @return a UserDetailsService instance that manages the defined users in memory
     */
    /*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails inMemoryNormalUser= User.builder()
                .username("achraf")
                .password("$2a$12$iJr3Oc1BJaiGnLeAKRCl2.m7wKlvVKnzaZsSmj1jxCYLQBGtbyAf6")
                .roles("USER")
                .build();

        UserDetails inMemoryAdminUser=User.builder()
                .username("asmae")
                .password("$2a$12$WAdS93NBDM93hkCY34.88.S7DOFe71gDPihF8ekQgZ9mceV.OHMgu")
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(inMemoryNormalUser,inMemoryAdminUser);
    }
    */


    // If you want to retrieve user details from DB, instead of using in memory uers
    @Bean
    public UserDetailsService userDetailsService(){
        return myUserDetailService;
    }


    /**
     * Configures and returns an instance of DaoAuthenticationProvider
     *
     * Méthode qui retourne un fournisseur d'authentification
     *
     * Le provider est généralement une instance de AuthenticationProvider
     * qui contient la logique pourauthentifier
     * les utilisateurs
     * @return an instance of AuthenticationProvider that uses the configured UserDetailsService and PasswordEncoder for authentication
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }





    /**
     * Cofigures a password encoder using BCrypt hashing algorithm
     *
     * This method returns an instance of BCryptPasswordEncoder which s used to encode passwords
     *
     * @return a PasswordEncoder instance that uses BCrypt for encodeing passswords
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * Crée un bean de gestionnaire d'authentification
     *
     * Cette méthode est annotée avec @Bean, ce qui signifie que Spring
     * l'utilise pour générer un bean dans le contexte de l'application.
     *
     * Le gestionnaire d'authentification est responsable de la vérification
     * des infos d'identification des utilisateurs lors du processus
     * d'authentification
     *
     * @return Un objet AuthenticationManager qui utilise un ProviderManager
     *      pour gérer l'authentification des utilisateurs
     */

    @Bean
    public AuthenticationManager authenticationManager(){
        // Crée et retourne une instance de ProviderManager
        // qui est un type d'AuthenticationManager
        return new ProviderManager(authenticationProvider());
    }
}
