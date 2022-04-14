package edu.ncsu.csc.iTrust2.controllers.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.enums.TransactionType;
import edu.ncsu.csc.iTrust2.services.BillService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

/**
 * Class that provided the REST endpoints for dealing with Bills, deletion,
 * updating, and viewing.
 *
 * @author Jonathan Buck
 *
 */
@RestController
@SuppressWarnings ( { "unchecked", "rawtypes" } )
public class APIBillController extends APIController {
    /** LoggerUtil */
    @Autowired
    private LoggerUtil     loggerUtil;

    /** Bill service */
    @Autowired
    private BillService    billService;

    /** Patient service */
    @Autowired
    private PatientService patientService;

    /**
     * Returns the bills associated with
     *
     * @param username
     *            the name of the patient
     * @return the result of the API call
     */
    @GetMapping ( BASE_PATH + "/bills/{username}" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public List<Bill> getBillsbyPatient ( @PathVariable ( "username" ) final String username ) {

        // logging right away
        loggerUtil.log( TransactionType.LIST_BILLS_BY, LoggerUtil.currentUser(), "Patient bills viewed" );

        // the patient in question
        final Patient patient = (Patient) patientService.findByName( username );
        if ( patient == null ) {
            // return an empty array list
            return new ArrayList<Bill>();
        }

        // otherwise
        final List<Bill> patientBills = new ArrayList<Bill>();
        final List<Bill> allBills = billService.findAll(); // all the bills

        for ( int i = 0; i < allBills.size(); i++ ) {
            if ( patient.equals( allBills.get( i ).getPatient() ) ) {
                patientBills.add( allBills.get( i ) );
            }
        }

        // coallesced, so we can return
        return patientBills;

    }

    /**
     * Returns the entire repository of bills: paid, unpaid and delinquent alike
     *
     * @return the result of the API call
     */
    @GetMapping ( BASE_PATH + "/bills/" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public List<Bill> getBills () {
        loggerUtil.log( TransactionType.LIST_ALL_BILLS, LoggerUtil.currentUser(), "List all bills" );
        return billService.findAll();
    }
}
