package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static com.epam.esm.ColumnName.*;

@Entity
@Table(name = ORDER_TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = ORDER_PRICE)
    private int price;

    @Column(name = ORDER_USER_ID)
    private Long userId;

    @Column(name = ORDER_ORDER_DATE)
    private LocalDateTime orderDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = ORDER_GIFTS,
            joinColumns = {@JoinColumn(name = ORDER_GIFTS_ORDER_ID)},
            inverseJoinColumns = {@JoinColumn(name = ORDER_GIFTS_GIFT_ID)})
    private Set<GiftCertificate> gifts;

}