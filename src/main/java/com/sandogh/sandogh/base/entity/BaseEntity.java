package com.sandogh.sandogh.base.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @CreationTimestamp
    @Column(name = "creationdate")
    private LocalDateTime creationDate;

}
