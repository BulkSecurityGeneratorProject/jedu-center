package edu.netcracker.center.repository;

import edu.netcracker.center.domain.Curator;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Spring Data JPA repository for the Curator entity.
 */
public interface CuratorRepository extends JpaRepository<Curator,Long>, QueryDslPredicateExecutor<Curator> {

}
