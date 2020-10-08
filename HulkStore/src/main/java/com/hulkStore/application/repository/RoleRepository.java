package com.hulkStore.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hulkStore.application.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
