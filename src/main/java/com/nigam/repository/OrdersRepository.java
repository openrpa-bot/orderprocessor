package com.nigam.repository;

import com.nigam.entity.Orders;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false, path = "Repository", collectionResourceRel = "Repository")
//@RepositoryRestResource( path = "Repository", collectionResourceRel = "Repository")
@Tag(name = "Order Repository APIs", description = "Order Repository API")
public interface OrdersRepository extends JpaRepository<Orders, Long> {

}