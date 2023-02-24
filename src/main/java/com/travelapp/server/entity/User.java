package com.travelapp.server.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="user")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private LocalDate dateOfBirth;

    private boolean enabled = true;
    private boolean tokenExpired;

    @ManyToOne
    @JoinColumn(name="id")
    private Role role;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
//               fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Order> orders;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivilegesAndRolesAsStrings(role));
    }

    private List<String> getPrivilegesAndRolesAsStrings(Role role) {
        return List.of(role.getName().toString());
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
        return !tokenExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
