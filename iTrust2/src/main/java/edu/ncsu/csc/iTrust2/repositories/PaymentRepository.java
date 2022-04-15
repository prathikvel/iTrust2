package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.models.Payment;
import edu.ncsu.csc.iTrust2.models.User;

/**
 *
 * @author colinscanlon
 *
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds an ICDCode by the provided code
     *
     * @param patient
     *            Code to search by
     * @return Matching code, if any
     */
    public CPTCode findByPatient ( User patient );

}
