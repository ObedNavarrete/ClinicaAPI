package com.security.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.security.api.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "branch", schema = "configuration")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "branch_id_seq", sequenceName = "branch_id_seq", allocationSize = 1, schema = "configuration")
public class Branch extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_seq")
    private Integer id;

    @Column(name = "id_business", nullable = false)
    private Integer idBusiness;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", insertable = false, updatable = false)
    @JsonBackReference
    private Business business;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String city;
}
