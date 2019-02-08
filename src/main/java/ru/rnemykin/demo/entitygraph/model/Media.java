package ru.rnemykin.demo.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
public class Media {
    public enum Type {
        PHOTO, VIDEO
    }


    @Id
    String id = UUID.randomUUID().toString();
    String projectId;
    String mediaUrl;

    @Enumerated(EnumType.STRING)
    Media.Type type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mediaId")
    Set<MediaBlock> blocks;
}
