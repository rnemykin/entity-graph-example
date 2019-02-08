package ru.rnemykin.demo.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Project.fetchAll",
                attributeNodes = {@NamedAttributeNode("members"), @NamedAttributeNode(value = "medias", subgraph = "Media.blocks")},
                subgraphs = {@NamedSubgraph(name = "Media.blocks", attributeNodes = @NamedAttributeNode("blocks"))}
        ),

        @NamedEntityGraph(
                name = "Project.members",
                attributeNodes = {@NamedAttributeNode("members")}
        ),

        @NamedEntityGraph(
                name = "Project.medias",
                attributeNodes = {@NamedAttributeNode(value = "medias", subgraph = "Media.blocks")},
                subgraphs = {@NamedSubgraph(name = "Media.blocks", attributeNodes = @NamedAttributeNode("blocks"))}
        )
})
public class Project {
    public enum Status {
        ACTIVE, INACTIVE, ARCHIVED
    }


    @Id
    String id = UUID.randomUUID().toString();
    String name;
    String description;

    @Enumerated(EnumType.STRING)
    Project.Status status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "projectId")
    Set<ProjectMember> members;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    Set<Media> medias;

    LocalDate createDate;
    LocalDate activateDate;
}
