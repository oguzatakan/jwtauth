package com.atakanoguz.jwtauth.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.CascadeType.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotEmpty
    @Size(max = 64)
    @Column(name = "user_name",unique = true,nullable = false,length = 64)
    private String userName;

    @NotEmpty
    @Size(max = 64)
    @Column(name = "user_name", unique = true, nullable = false,length = 64)
    private String fullName;

    @NotEmpty
    @Size(max = 255)
    private String email;

    @NotEmpty
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreatedDate
    @CreationTimestamp
    private LocalDate createdDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {MERGE, PERSIST, REFRESH, DETACH})
    private Collection<Role> roles = new ArrayList<>();

}
