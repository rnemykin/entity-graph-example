package ru.rnemykin.demo.entitygraph.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.rnemykin.demo.entitygraph.model.Project;

import java.util.List;

@RepositoryRestResource
public interface ProjectRepository extends CrudRepository<Project, String> {

    @Query("from Project")
    @EntityGraph(value = "Project.members", type = EntityGraph.EntityGraphType.FETCH)
    List<Project> findAllFetchMembers();

    @Query("from Project")
    @EntityGraph(value = "Project.medias", type = EntityGraph.EntityGraphType.LOAD)
    List<Project> findAllFetchMedias();

    @Query("from Project")
    @EntityGraph(value = "Project.fetchAll", type = EntityGraph.EntityGraphType.FETCH)
    List<Project> findAllFetchAll();

}
