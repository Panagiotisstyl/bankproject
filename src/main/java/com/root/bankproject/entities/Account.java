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
    private int id;

    @Column(name="type_account")
    private TypeAccount typeAccount;

    @Column(name="description")
    private String description;

    @Column(name="balance")
    private double balance;

    @OneToMany
    @JoinColumn(name="userId")
    private List<User> users;



}
