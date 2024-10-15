package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service pour la gestion des JSON web tokens (JWT)
 * Fournit des méthodes pour générer, extraire et valider des tokens JWT
 */

@Service
public class JwtService {

    // Exemple de clé secrète utilisée pour signer les JWT (et qui doit être secrète)
    // Generate the key one then use it as a secure key in the code
    private static final String SECRET_KEY="1C52FB2730097E85404B1B1440D63204B95C557AFA2FD2BFCB93616E20E747AD6CD7ED8DDF30B6223A58ADA6A15CF6033BC69D97FB7B20DF4AE455D34CA4B94F";


    /**
     * Génère un JWT pour un utilisateur donné
     *
     * @param userDetails Détails de l'utilisateur pour lequel le token est généré
     * @return Le token JWT sous forme de chaîne
     */
    public String generateToken(UserDetails userDetails){
        // Création d'une carte pour stocker les revendications (claims) dutoken
        Map<String,String> claims=new HashMap<>();
        claims.put("test add extra claims","from JwtService class");
        return Jwts.builder()
                .claims(claims) // Ajout des revendications(claims) au token
                .subject(userDetails.getUsername()) // sujet du token c'est le nom d'utilisateur
                .issuedAt(new Date(System.currentTimeMillis())) // Date d'émission du token
                .expiration(new Date(System.currentTimeMillis() +60*60*30)) // Date d'expiration (30min)
                .signWith(generateKey()) // Signature du token avec la clé secrète
                .compact(); // Création du token JWT

        // compact() : créer et renvoyer le token JWT à partir des revendications et des paramètres spécifiés
    }




    /**
     * Génère une clé secrète à partir de la chaîne de la clé secrète encodée en Base64
     *
     * @return La clé secrète sous forme de SecretKey
     */
    private SecretKey generateKey(){
        byte[] decodedKey= Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey); // Création de la clé HMAC
    }


    /**
     * Extrait le nom d'utilisateur à partir du token JWT
     *
     * @param jwt Le token JWT à partir duquel extraire le nom d'utilisateur
     * @return Le nom d'utilisateur xtrait du token
     */
    public String extractUsername(String jwt) {
       Claims claims=getClaims(jwt);
        return claims.getSubject();
    }


    /**
     * Récupérer les revendications du token JWT
     *
     * @param jwt Le token JWT dont on veut les revendications
     * @return Les revendications du token
     */
    private Claims getClaims(String jwt){
        // Crée un nouvel objet parser pour traiter le token JWT
        return Jwts.parser()// initialisation d'un parser JWT
                // Vérifier la signature du token avec la clé secrète générée
                .verifyWith(generateKey())
                // Finalise la configuration du parser
                .build()
                // Analyse le token JWT et récupère les revendicaions(claims) signées
                .parseSignedClaims(jwt)
                // Retourne le corps des revendications extraites
                .getPayload();
    }



    /**
     * Vérifier si le token JWT est valide en vérifiant en vérifiant sa date d'expiration
     * @param jwt Le token JWT à valider
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String jwt) {
        Claims claims=getClaims(jwt);
        return claims.getExpiration().after(new Date());
    }
}
