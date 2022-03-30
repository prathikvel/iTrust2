package edu.ncsu.csc.iTrust2.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.repositories.CPTCodeRepository;

/**
 * Service class for interacting with CPTCode model, performing CRUD tasks with
 * database.
 *
 * @author Colin Scanlon
 *
 */
@Component
@Transactional
public class CPTCodeService extends Service<CPTCode, Long> {

    /** Repository for CRUD operations */
    @Autowired
    private CPTCodeRepository repository;

    /** Service for looking up Personnel */
    // @Autowired
    // private PersonnelService service;

    @Override
    protected JpaRepository<CPTCode, Long> getRepository () {
        return repository;
    }

    /**
     * Finds an ICDCode object for the given Code
     *
     * @param code
     *            Code of the ICDCode desired
     * @return ICDCode found, if any
     */
    public CPTCode findByCode ( final String code ) {
        return repository.findByCode( code );
    }

    @Override
    public List<CPTCode> findAll () {
        // final User user = service.findByName( LoggerUtil.currentUser() );
        // final Collection<Role> roles = user.getRoles();

        return repository.findAll();

        // if ( roles.contains( Role.ROLE_BSM ) ) {
        // return repository.findAll();
        // }
    }

    public List<CPTCode> findAllActive () {
        // final User user = service.findByName( LoggerUtil.currentUser() );
        // final Collection<Role> roles = user.getRoles();

        // if ( roles.contains( Role.ROLE_BSM ) ) {
        // return repository.findByIsArchiveFalse();
        // }

        return repository.findByArchiveIsFalse();

    }

    public List<CPTCode> findAllArchive () {
        // final User user = service.findByName( LoggerUtil.currentUser() );
        // final Collection<Role> roles = user.getRoles();

        // if ( roles.contains( Role.ROLE_BSM ) ) {
        // return repository.findByIsArchiveTrue();
        // }

        return repository.findByArchiveIsTrue();

    }

}
