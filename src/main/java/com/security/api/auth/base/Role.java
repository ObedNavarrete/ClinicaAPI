package com.security.api.auth.base;

import javax.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role", schema = "security")
@SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1, schema = "security")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Integer id;
    private String name;
    private String description;
}
