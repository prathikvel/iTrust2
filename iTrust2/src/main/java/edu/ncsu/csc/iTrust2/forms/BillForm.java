package edu.ncsu.csc.iTrust2.forms;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.CPTCode;

public class BillForm implements Serializable {
    /**
     * Serial Version of the Form. For the Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * Name of the Patient involved in the OfficeVisit
     */
    @NotEmpty
    private String            patient;

    /**
     * Name of the HCP involved in the OfficeVisit
     */
    @NotEmpty
    private String            hcp;

    /**
     * Date at which the OfficeVisit occurred
     */
    @NotEmpty
    private String            date;

    /**
     * ID of the OfficeVisit
     */
    private String            id;

    /**
     * Type of the OfficeVisit.
     */
    @NotEmpty
    private String            status;

    /**
     * List of all CPTCodes for an office visit
     */
    private List<CPTCode>     cptCodes;

    /**
     * The remaining cost of the bill
     */
    private String            cost;

    public BillForm () {

    }

    public BillForm ( final Bill bill ) {
        setPatient( bill.getPatient().getUsername() );
        setHcp( bill.getHcp().getUsername() );
        setDate( bill.getDate().toString() );
        setId( bill.getId().toString() );
        setCptCodes( bill.getCptCodes() );
        setCost( bill.getCost().toString() );

    }

    /**
     * @return the patient
     */
    public String getPatient () {
        return patient;
    }

    /**
     * @param patient
     *            the patient to set
     */
    public void setPatient ( final String patient ) {
        this.patient = patient;
    }

    /**
     * @return the hcp
     */
    public String getHcp () {
        return hcp;
    }

    /**
     * @param hcp
     *            the hcp to set
     */
    public void setHcp ( final String hcp ) {
        this.hcp = hcp;
    }

    /**
     * @return the date
     */
    public String getDate () {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate ( final String date ) {
        this.date = date;
    }

    /**
     * @return the id
     */
    public String getId () {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId ( final String id ) {
        this.id = id;
    }

    /**
     * @return the status
     */
    public String getStatus () {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus ( final String status ) {
        this.status = status;
    }

    /**
     * @return the cptCodes
     */
    public List<CPTCode> getCptCodes () {
        return cptCodes;
    }

    /**
     * @param cptCodes
     *            the cptCodes to set
     */
    public void setCptCodes ( final List<CPTCode> cptCodes ) {
        this.cptCodes = cptCodes;
    }

    /**
     * @return the cost
     */
    public String getCost () {
        return cost;
    }

    /**
     * @param cost
     *            the cost to set
     */
    public void setCost ( final String cost ) {
        this.cost = cost;
    }

}
