package com.example.demo.jira.items.group.infrastructure.persistence;

import com.example.demo.jira.items.group.domain.model.Subgroup;
import com.example.demo.jira.items.group.enums.Direction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="enrolled_year")
    private Long enrolled_year;

    @Column(name="direction")
    private Direction direction;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="subgroups",
            joinColumns = @JoinColumn(name = "group_id")
    )
    private List<Subgroup> subgroups;
}
