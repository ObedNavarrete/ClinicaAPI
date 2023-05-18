package com.security.api.entity;

import com.security.api.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name = "resource", schema = "configuration")
@SequenceGenerator(name = "resource_id_seq", sequenceName = "resource_id_seq", allocationSize = 1, schema = "configuration")
public class Resource extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_id_seq")
    private Integer id;
    private String url;
    private String description;
}
