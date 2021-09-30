package com.my.todos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import javax.validation.constraints.Size;


/**
 * @author JayendraB
 * Created on 29/08/21
 */

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    private String user;

    @Size(min=10, message="Enter at least 10 Characters...")
    @Column(name="description")
    @NonNull
    private String description;

    @NonNull
    private LocalDate targetDate;

    @NonNull
    @JsonProperty("isDone")
    private boolean isDone;
}
