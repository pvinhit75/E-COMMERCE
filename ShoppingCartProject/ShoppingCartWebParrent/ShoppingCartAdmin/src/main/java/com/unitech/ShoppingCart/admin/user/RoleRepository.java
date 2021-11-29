package com.unitech.ShoppingCart.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unitech.commom.entities.Role;



@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
