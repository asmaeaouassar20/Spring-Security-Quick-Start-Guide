package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken;

import algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.security.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Service pour gérer les opérations liées aux tokens JWT
    @Autowired
    private MyUserDetailService myUserDetailService; // Service pour charger les détails de l'utilisateur


    /**
     * Méthode de filtrage interne qui vérifie et valide le token JWT
     *
     * @param request la requête HTTP à traiter
     * @param response la requête HTTP à envoyer
     * @param filterChain La chaîne de filtres pour continuer le traitement
     * @throws ServletException en cas d'erreur lors du traitement de la requête
     * @throws IOException en cas d'erreur d'entrée/sortie
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // récupérer l'en-tête d'autorisation
        String authHeader=request.getHeader("Authorization");

        // Vérifier si l'en-tête est null ou n commence pas par "Beare r "
        if(authHeader==null || authHeader.startsWith("Bearer ")){
            // si non, passe à la chaîne de filtres suivante
            filterChain.doFilter(request,response);
            return;
        }

        // Extraire le token JWT de l'en-tête
        String jwt=authHeader.substring(7);

        // Extraire le nom d'utilisateur à parir du token
        String username=jwtService.extractUsername(jwt);

        // Vérifier si l'utilisateur est présent et qu'aucune authentification n'est encore définie dans le contexte
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            // charger les détails de l'utilisateur à partir du service
            UserDetails userDetails=myUserDetailService.loadUserByUsername(username);

            // vérifier si les détails de l'utilisateur sont valides et si le token est valide
            if(userDetails!=null && jwtService.isTokenValid(jwt)){
                // Crée un token d'authentification avec les détails de l'utilisateur
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                username,
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        );

                // Ajoute les détails de la requête au token d'authentification
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Définit le token d'authentification dans le context de sécurité
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        // Poursuit le traitement de la requête
        filterChain.doFilter(request,response);
    }
}
