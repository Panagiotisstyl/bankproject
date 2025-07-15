package com.root.bankproject.entities;


import com.root.bankproject.enums.TypeAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//TODO: table names ARE plural, with all lower cases
//TODO: class names should NOT be plural
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name="Accounts")
public class Accounts {

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
    private List<Users> users;

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", typeAccount=" + typeAccount +
                ", description='" + description  +
                ", balance=" + balance +
                ", userId/usersId" + idHelper()+
                '}';
    }

    private String idHelper(){

        String ids="";

        for(Users user : users){
            ids=ids+user.getId()+" ";
        }
        return ids;
    }


}
