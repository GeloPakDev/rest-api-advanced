package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Set;

import static com.epam.esm.ColumnName.*;

@Entity@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = USER_TABLE_NAME)

@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = USER_NAME)
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = ORDER_USER_ID)
    private Set<Order> orders;
}
