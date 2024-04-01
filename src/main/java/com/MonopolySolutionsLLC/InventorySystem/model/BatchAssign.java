package com.MonopolySolutionsLLC.InventorySystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchCode;
    private Date uploadedOn;
    private String uploadedBy;
    private String assignedTo;
    private int totalRecords;
    private int uploadedSuccess;
    private int uploadedFailure;

//    @Builder.Default
    @OneToMany(mappedBy = "batchAssign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BatchAssignDetail> details = new ArrayList<>();
}
