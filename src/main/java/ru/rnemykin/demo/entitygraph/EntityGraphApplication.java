package ru.rnemykin.demo.entitygraph;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import ru.rnemykin.demo.entitygraph.model.Project;
import ru.rnemykin.demo.entitygraph.repository.ProjectRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class EntityGraphApplication implements RepositoryRestConfigurer {
    private final ProjectRepository projectRepository;


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        config.useHalAsDefaultJsonMediaType(false);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void initData(ApplicationReadyEvent event) {
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .objectPoolSize(100)
                .randomizationDepth(3)
                .charset(StandardCharsets.UTF_8)
                .dateRange(LocalDate.now(), LocalDate.now().plusMonths(6))
                .stringLengthRange(5, 50)
                .collectionSizeRange(1, 10)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .build();

        projectRepository.saveAll(Stream.iterate(1, s -> s + 1)
                .map(s -> enhancedRandom.nextObject(Project.class))
                .limit(10)
                .peek(project -> {
                    project.getMedias().forEach(media -> {
                        media.setProjectId(project.getId());
                        media.getBlocks().forEach(block -> block.setMediaId(media.getId()));
                    });
                    project.getMembers().forEach(m -> m.setProjectId(project.getId()));
                })
                .collect(Collectors.toList())
        );
    }



    public static void main(String[] args) {
        SpringApplication.run(EntityGraphApplication.class, args);
    }

}

