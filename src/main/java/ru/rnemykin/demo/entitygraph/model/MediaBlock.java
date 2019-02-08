package ru.rnemykin.demo.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
public class MediaBlock {
    public enum Type {
        HEADER, FOOTER, BODY
    }


    @Id
    String id = UUID.randomUUID().toString();
    String mediaId;
    String content;
}
