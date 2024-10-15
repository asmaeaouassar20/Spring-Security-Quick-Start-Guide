package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.controller;


import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.model.MyUser;
import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe RegistrationController
 *
 * Ce contrôleur gère les opérations d'inscription des utillisateurs.
 * Il recçoit les demandes d'inscription, encode les mots de passe
 * et enregistre les utilisateurs dans la BD
 *
 * -----------------------------------------------
 *  Controller pour la gestion de l'inscription des utilisateurs
 * -----------------------------------------------
 *
 */
@RestController
public class RegistrationController {
    /**
     * Référentiel pour interagir avec la BD des utilisateurs
     *
     * Cette instance est automatiquement injectée par Spring
     * Elle permet d'effectuer des opérations CRUD
     * sur les objets "MyUser"
     */
    @Autowired
    private MyUserRepository myUserRepository; // dépôt pour gérer les utilisateurs dans la BD


    /**
     * Service pour encoder les mots de passe des utilisateurs
     *
     * Cet attribut est utilisé pour assurer que les mots de passe
     * des utilisateurs sont stockés de manière sécurisée, en
     * les transformant en une forme hachée
     *
     * L'injection de ce service est également gérée par Spring
     */
    @Autowired
    private PasswordEncoder passwordEncoder; // Service pour encoder les mots de passe


    /**
     * Crée un nouvel utilisateur
     * @param myUser L'objet MyUser contenant les détails de l'utilisatur àà créer,
     *               reçu dans le corps de la requête HTTP
     * @return L'objet MyUser enregistré dans la BD, avec un mot de passe crypté.
     */
    @PostMapping("/register/user")
    public MyUser createUser(@RequestBody MyUser myUser){
        // Encode le mot de passe de l'utilisateur avant de le sauvegarder
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        // Enregistre l'utilisateur dans la BD et retourne l'objet sauvegardé
        return myUserRepository.save(myUser);
    }
}
