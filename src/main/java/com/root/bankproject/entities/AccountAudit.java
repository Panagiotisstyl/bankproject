package com.root.bankproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="account_audit")
public class AccountAudit {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name="account_id")
    private Integer accountId;

    @Column(name="balance")
    private double balance;

    @Column(name="user_ids")
    private List<Integer> userIds;
}
