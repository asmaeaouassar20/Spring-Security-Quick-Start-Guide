package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.webtoken;


// Déclaration d'un enregistrement (record) nommé LoginForm

public record LoginForm(
        String username,
        String password
        ) {
}

// Un enregistrement (record) est une classe immuable (constant, définitif, figé, fixe)
