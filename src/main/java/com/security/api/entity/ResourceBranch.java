package com.security.api.entity;

import com.security.api.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "resource_branch", schema = "configuration")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "resource_branch_id_seq", sequenceName = "resource_branch_id_seq", allocationSize = 1, schema = "configuration")
public class ResourceBranch extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_branch_id_seq")
    private Integer id;

    @Column(name = "id_resource", insertable = false, updatable = false)
    private Integer idResource;
    @ManyToOne
    @JoinColumn(name = "id_resource", nullable = false)
    private Resource resource;

    @Column(name = "id_branch")
    private Integer idBranch;
    @ManyToOne
    @JoinColumn(name = "id_branch", insertable = false, updatable = false)
    private Branch branch;

    @Column(name = "id_profile", insertable = false, updatable = false)
    private Integer idProfile;
    @ManyToOne
    @JoinColumn(name = "id_profile", nullable = false)
    private Profile profile;
}
