package com.epam.esm;

import lombok.*;
//import org.hibernate.envers.Audited;

import javax.persistence.*;

import static com.epam.esm.ColumnName.*;

@Entity
@Table(name = TAG_TABLE_NAME)
//@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = TAG_NAME)
    private String name;
}