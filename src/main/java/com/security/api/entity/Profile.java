package com.security.api.entity;

import com.security.api.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
@Builder
@Table(name = "profile", schema = "configuration")
@AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "profile_id_seq", sequenceName = "profile_id_seq", allocationSize = 1, schema = "configuration")
public class Profile extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_seq")
    private Integer id;
    private String name;
    private String code;

    @Column(name = "id_business", nullable = false)
    private Integer idBusiness;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_business", insertable = false, updatable = false)
    private Business business;

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<ResourceBranch> resourceBranches;

    @Transient
    private Set<Map<String,Object>> detailResourceBranches;

    public Set<Map<String,Object>> getDetailResourceBranches() {
        List<Branch> branches = this.resourceBranches.stream().map(ResourceBranch::getBranch).distinct().toList();
        Set<Map<String, Object>> set = new HashSet<>();

        for (Branch x : branches) {
            if (!x.getIdBusiness().equals(this.idBusiness))
                continue;

            Map<String, Object> firstMap = new HashMap<>();
            firstMap.put("idBranch", x.getId());
            firstMap.put("nameBranch", x.getName());
            List<Map<String, Object>> list = new ArrayList<>();
            for (ResourceBranch y : this.resourceBranches) {
                if (y.getPasive()) continue;
                if (!y.getBranch().getId().equals(x.getId())) continue;
                Map<String, Object> secondMap = new HashMap<>();
                secondMap.put("idResource", y.getResource().getId());
                secondMap.put("urlResource", y.getResource().getUrl());
                list.add(secondMap);
            }

            firstMap.put("resources", list);
            set.add(firstMap);
        }

        return set;
    }
}
