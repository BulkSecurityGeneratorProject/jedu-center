package edu.netcracker.center.service;

import com.mysema.query.types.Predicate;
import edu.netcracker.center.domain.GroupOfStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GroupOfStudent.
 */
public interface GroupOfStudentService {

    /**
     * Save a groupOfStudent.
     * @return the persisted entity
     */
    public GroupOfStudent save(GroupOfStudent groupOfStudent);

    /**
     *  get all the groupOfStudents.
     *  @return the list of entities
     */
    public Page<GroupOfStudent> findAll(Predicate predicate, Pageable pageable);

    /**
     *  get the "id" groupOfStudent.
     *  @return the entity
     */
    public GroupOfStudent findOne(Long id);

    /**
     *  delete the "id" groupOfStudent.
     */
    public void delete(Long id);
}
