package com.security.api.entity;

import com.security.api.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "business", schema = "configuration")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "business_id_seq", sequenceName = "business_id_seq", allocationSize = 1, schema = "configuration")
public class Business extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_id_seq")
    private Integer id;

    private String name;
    private String address;
    private String phone;
}
