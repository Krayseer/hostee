package ru.anykeyers.videoservice.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Пользователь
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    /**
     * Идентификатор пользователя
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль
     */
    @Column(name = "password")
    private String password;

    /**
     * Почтовый адрес
     */
    @Column(name = "email")
    private String email;

    @Column(name = "blocked")
    private boolean blocked;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSetting userSetting;

    /**
     * Роль
     */
    @ElementCollection(
            targetClass = Role.class,
            fetch = FetchType.EAGER
    )
    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !blocked;
    }

}
