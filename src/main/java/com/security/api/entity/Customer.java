package com.security.api.entity;

import com.security.api.audit.AuditableEntity;
import com.security.api.auth.base.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer", schema = "configuration")
@SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1, schema = "configuration")
public class Customer extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", unique = true)
    private User user;

    @Column(name = "id_user", insertable = false, updatable = false)
    private Integer idUser;

    @Column(name = "id_business")
    private Integer idBusiness;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", insertable = false, updatable = false)
    private Business business;
}
