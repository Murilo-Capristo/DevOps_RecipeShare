package br.com.fiap.RecipeShare.feature.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Data
@Table(name = "rs_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String avatarUrl;

    private int score = 0; // soma de likes das receitas do usuário

    public User(OAuth2User principal){
        this.email = principal.getAttributes().get("login").toString();
        this.name = principal.getAttributes().get("name").toString();
        var avatarUrl = principal.getAttributes().get("picture") != null ?
                principal.getAttributes().get("picture") :
                principal.getAttributes().get("avatar_url");
        this.avatarUrl = avatarUrl.toString();
    }

    public User() {
    }
}
