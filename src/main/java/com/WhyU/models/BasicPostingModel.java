package com.WhyU.models;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@MappedSuperclass

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder(builderMethodName = "basicPostingModelBuilder")
public class BasicPostingModel<U> extends BasicModel {
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreatedBy
    private U createdBy;

    @LastModifiedBy
    private U updatedBy;
}
