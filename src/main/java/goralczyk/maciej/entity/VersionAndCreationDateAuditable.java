package goralczyk.maciej.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class VersionAndCreationDateAuditable {

    /**
     * Edit version fore optimistic locking.
     */
    @Version
    private Long version;

    /**
     * Creation date.
     */
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    /**
     * Modification date.
     */
    @Column(name = "modification_date_time")
    private LocalDateTime modificationDateTime;

    /**
     * Update creation datetime.
     */
    @PrePersist
    public void onCreation() {
        creationDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onModification() {
        modificationDateTime = LocalDateTime.now();
    }

}
