package com.example.onlinelibrary.model;


import com.example.onlinelibrary.constraint.FieldMatch;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Data
@FieldMatch.List({
        @FieldMatch(first = "username", second = "confirmUsername", message = "The username fields must match"),
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "national_code")
    private Integer nationalCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_books", joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private List<Book> books;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_favorite", joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id"))
    private List<Book> favorite;

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<Role>();
        }
        roles.add(role);
    }
}
