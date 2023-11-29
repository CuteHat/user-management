package com.crocobet.usermanagement.persistence.specification;

import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class UserSpecifications {
    public static Specification<UserEntity> isActive(Boolean active) {
        return (root, query, builder) -> builder.equal(root.get(UserEntity.Fields.active), active);
    }

    public static Specification<UserEntity> emailEquals(String email) {
        return ((root, query, builder) -> builder.equal(root.get(UserEntity.Fields.email), email));
    }

    public static Specification<UserEntity> optEmailEquals(String email) {
        return ((root, query, builder) -> {
            if (StringUtils.isEmpty(email)) {
                return builder.conjunction();
            }
            return builder.equal(root.get(UserEntity.Fields.email), email.toLowerCase());
        });
    }

    public static Specification<UserEntity> optUsernameEquals(String username) {
        return ((root, query, builder) -> {
            if (StringUtils.isEmpty(username)) {
                return builder.conjunction();
            }
            return builder.equal(root.get(UserEntity.Fields.username), username);
        });
    }

    public static Specification<UserEntity> optIsActive(Boolean isActive) {
        return ((root, query, builder) -> {
            if (isActive == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(UserEntity.Fields.active), isActive);
        });
    }

    public static Specification<UserEntity> optHasRoles(Collection<RoleName> roles) {
        return (root, query, builder) -> {
            if (roles == null || roles.isEmpty()) return builder.conjunction();

            Join<UserEntity, RoleEntity> join = root.join(UserEntity.Fields.roles);
            return join.get(RoleEntity.Fields.name).in(roles);
        };
    }
}
