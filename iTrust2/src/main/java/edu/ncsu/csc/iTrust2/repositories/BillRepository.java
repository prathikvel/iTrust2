package edu.ncsu.csc.iTrust2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.User;

/**
 * Repository for interacting with Bill model. Method implementations generated
 * by Spring
 *
 * @author colinscanlon
 *
 */
public interface BillRepository extends JpaRepository<Bill, Long> {

    /**
     * Finds an Bill by the provided id
     *
     * @param code
     *            Code to search by
     * @return Matching code, if any
     */
    public List<Bill> findByPatient ( User patient );

}
