package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Set;

import static com.epam.esm.ColumnName.*;

@Entity
@Table(name = USER_TABLE_NAME)
//@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = USER_NAME)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = ORDER_USER_ID)
    Set<Order> orders;
}
