package com.root.bankproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="user_audit")
public class UserAudit {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="username")
    private String username;

}
