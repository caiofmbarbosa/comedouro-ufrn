package com.ufrn.projeto.webApp.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NAME",  nullable = false, length = 60)
    private String nome;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 60)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 150)
    private String password;

    @CreationTimestamp
    @Column(name = "REGISTRATION_DATE", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean ativo = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tutor", orphanRemoval = true)
    private List<Pet> pets;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_TUTOR"));
    }

    @Override
    public @NonNull String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isEnabled() {
        return getAtivo();
    }
}
