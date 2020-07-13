package io.m9rcy.playground.domain.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "APPLICATIONS")
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String slug;
  private String title;
  private String description;
  private String applicationName;
  private String customerId;

  @Column(name="payload", columnDefinition="LONGTEXT")
  private String payload;
  private boolean active;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Document> documents;
}
