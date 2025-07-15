package com.root.bankproject.entities;


import com.root.bankproject.enums.TypeAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="accounts")
public class Account {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="type_account")
    private TypeAccount typeAccount;

    @Column(name="description")
    private String description;

    @Column(name="balance")
    private double balance;

    @ManyToMany
    @JoinTable(
            name = "account_users",  // join table name
            joinColumns = @JoinColumn(name = "account_id"), // this entity FK
            inverseJoinColumns = @JoinColumn(name = "user_id") // other entity FK
    )
    private List<User> users;




}
