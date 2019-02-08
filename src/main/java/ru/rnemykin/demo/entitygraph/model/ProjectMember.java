package ru.rnemykin.demo.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
public class ProjectMember {
    public enum Role {
        USER, MODERATOR, OWNER
    }


    @Id
    String id = UUID.randomUUID().toString();
    String projectId;
    String name;

    @Enumerated(EnumType.STRING)
    ProjectMember.Role role;
}
