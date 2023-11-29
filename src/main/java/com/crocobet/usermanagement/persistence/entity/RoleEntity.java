package com.crocobet.usermanagement.persistence.entity;

import com.crocobet.usermanagement.persistence.model.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
public class RoleEntity extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;
}
