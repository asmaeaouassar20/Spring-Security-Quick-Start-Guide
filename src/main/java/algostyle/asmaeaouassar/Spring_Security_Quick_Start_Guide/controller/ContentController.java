package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.controller;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.model.MyUser;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.repository.MyUserRepository;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.security.MyUserDetailService;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.service.MyUserService;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken.JwtService;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ContentController : est responsable de la gestion des requetes liées à l'authentification
 * et à l'accès aux différentes pages de l'application.
 * Il s'appuie sur Spring Security pour gérer l'authentification des
 * utilisateurs et utilise JWT pour la génération de tokens de l'authentification
 */

@Controller
public class ContentController {
    @Autowired
    private MyUserService myUserService;


    // Gérer le processus d'authentification des utilisateurs
    @Autowired
    private AuthenticationManager authenticationManager;


    // Service responsable de la création et de la validation des tokens JWT
    @Autowired
    private JwtService jwtService;


    // Service pour charger les détails de l'utilisateur à partir de la BD
    @Autowired
    private MyUserDetailService myUserDetailService;


    /**
     * Gère la requete GET pour afficher la page d'acceuil de l'application
     * @return le nom de la vue pour la page d'acceuil "home"
     */
    @GetMapping("/home")
    public String handleHomePage(){
        return "home";
    }


    /**
     * Gère la requete GET pour afficher la page d'acceuil des utilisateurs authentifiés
     * @return le nom de la vue pour la page d'accueil utilisateur "user_home"
     */
    @GetMapping("/user/home")
    public String handleUserHomePage(){
        return "user_home";
    }




    /**
     * Gère la requete GET pour afficher la page d'acceuil des administrateurs
     * @return le nom de la vue pour la pge d'acceuil admin "admin_home"
     */
    @GetMapping("/admin/home")
    public String handlAdminHomePage(){
        return "admin_home";
    }





    /**
     * Gère la requete GET pour afficher la page de connexion
     * il y en a une page par défaut pour spring boot, mais on l'a personnalisé
     * @return le nom de la vue pour la page de connexion "custom_login_page"
     */
    @GetMapping("/login")
    public String handleCustomLoginPage(){
        return "custom_login_page";
    }





    /**
     * Gérer la requete POST pour authentifier un utilisateur et retourner un token JWT
     *
     * @param loginForm un objet contenant le nom d'utilisateur et le mot de passe de l'utilisateur
     * @return un token JWT si l'authentification réussit
     * @throws UsernameNotFoundException si les informations d'identification fournies sont anvalides
     */
    @PostMapping("/authenticate")
    @ResponseBody
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm){

        // Créer un objet d'authentification avec le nom d'utilisateur et le mot de passe
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(),loginForm.password()
        ));


        // Vérifier si l'authentification a réussi
        if(authentication.isAuthenticated()){
            // Charge les détails de l'utilisateur et génère un token JWT
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        }else{
            // Lève une exception si les informations d'identification sont invalides
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }




    @GetMapping("/admin/users")
    @ResponseBody
    public List<MyUser> getAllUsers(){
        return myUserService.getAllUsers();
    }
}
