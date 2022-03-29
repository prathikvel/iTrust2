package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Class that provided the REST endpoints for dealing with CPT Code creation,
 * deletion, updating, and viewing.
 *
 * @author Jonathan Buck
 *
 */
@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APICPTCodeController extends APIController {
    /** LoggerUtil */
    @Autowired
    private LoggerUtil     loggerUtil;

    /** CPTCode service */
    @Autowired
    private CPTCodeService service;

    /**
     * Adds a new CPT Code to the active CPT code list
     *
     * @param form
     *            the data for the new CPT code
     * @return the result of the API call
     */
    @PostMapping ( BASE_PATH + "/CPTCode/add" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public ResponseEntity createCode ( @RequestBody final CPTCodeForm form ) {

    }

    /**
     * Updates a CPT Code in the active CPT code list with a given ID and adds
     * the old version to the CPT code archive
     *
     * @param id
     *            the ID of the CPT code to edit
     * @param form
     *            the data for the new CPT code
     * @return the result of the API call
     */
    @PutMapping ( BASE_PATH + "/CPTCode/{id}" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public ResponseEntity editCode ( @PathVariable ( "id" ) final Long id, @RequestBody final CPTCodeForm form ) {

    }

    /**
     * Deletes a CPT Code from the active CPT code list with a given ID and adds
     * it to the CPT code archive
     *
     * @param id
     *            the ID of the CPT code to edit
     * @return the result of the API call
     */
    @DeleteMapping ( BASE_PATH + "/CPTCode/{id}" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public ResponseEntity deleteCode ( @PathVariable ( "id" ) final Long id ) {

    }

    /**
     * Returns the entire list of active CPT codes
     *
     * @return the result of the API call
     */
    @GetMapping ( BASE_PATH + "/CPTCode" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public ResponseEntity getCode () {

    }

    /**
     * Returns the entire list of archived CPT codes
     *
     * @return the result of the API call
     */
    @GetMapping ( BASE_PATH + "/CPTCode/history" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public ResponseEntity getCodeHistory () {

    }

}
