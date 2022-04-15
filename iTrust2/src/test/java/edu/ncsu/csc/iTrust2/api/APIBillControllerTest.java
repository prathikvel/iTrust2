package edu.ncsu.csc.iTrust2.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Bill;
import edu.ncsu.csc.iTrust2.models.CPTCode;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.BillService;
import edu.ncsu.csc.iTrust2.services.CPTCodeService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.PersonnelService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIBillControllerTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BillService           billService;

    @Autowired
    private PatientService        patientService;

    @Autowired
    private PersonnelService      personnelService;

    @Autowired
    private CPTCodeService        codeService;

    /**
     * Sets up test
     */
    @Before
    @WithMockUser ( username = "admin", roles = { "USER", "ADMIN" } )
    public void setup () throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
        billService.deleteAll();
        patientService.deleteAll();
        personnelService.deleteAll();
        codeService.deleteAll();
    }

    @SuppressWarnings ( "unchecked" )
    @Test
    @Transactional
    @WithMockUser ( username = "billing staff member", roles = { "USER", "BSM" } )
    public void testRetrievingBills () throws Exception {

        // add in a patient first
        final Patient clyde = new Patient( new UserForm( "clyde", "123456", Role.ROLE_PATIENT, 1 ) );
        final Personnel billingStaff = new Personnel( new UserForm( "bsm", "654322", Role.ROLE_HCP, 1 ) );

        // patients and personnel get added to the database
        patientService.save( clyde );
        personnelService.save( billingStaff );

        // add in cpt codes
        final CPTCode surgeryCode = new CPTCode();
        surgeryCode.setCode( "21000" );
        surgeryCode.setDescription( "Musculoskeletal" );
        surgeryCode.setId( 1L );
        surgeryCode.setCost( 10 );

        final CPTCode therapyCode = new CPTCode();
        therapyCode.setCode( "90832" );
        therapyCode.setDescription( "30 minutes of psychotherapy" );
        therapyCode.setId( 2L );
        therapyCode.setCost( 10 );

        // codes going in database
        codeService.save( surgeryCode );
        codeService.save( therapyCode );

        // merely coalescing the cpt codes
        final List<CPTCode> codes = codeService.findAll();

        // make a bill, store it in the database
        final Bill billClyde = new Bill( clyde, billingStaff, codes );

        // for now
        billService.save( billClyde );

        // now retrieve all bills
        final String allBills = mvc.perform( get( "/api/v1/bills/" ).contentType( MediaType.APPLICATION_JSON ) )
                .andReturn().getResponse().getContentAsString();
        final String bill = mvc.perform( get( "/api/v1/bills/" ).contentType( MediaType.APPLICATION_JSON ) ).andReturn()
                .getResponse().getContentAsString();

        //
        assertNotNull( allBills );
        assertTrue( allBills.contains( "clyde" ) );

        // now make another patient
        final Patient avery = new Patient( new UserForm( "avery", "123456", Role.ROLE_PATIENT, 1 ) );

        // give them a bill
        final Bill billAvery = new Bill( avery, billingStaff, codes );

        // store said bill in the database

        // retrieve all bills, make sure bills from both patients are included

        // retrieve bill from one patient
    }

    @SuppressWarnings ( "unchecked" )
    @Test
    @Transactional
    @WithMockUser ( username = "billing staff member", roles = { "USER", "BSM" } )
    public void testUpdatingBills () throws Exception {

        // let's create patient and a billing staff member first
        final Patient clyde = new Patient( new UserForm( "clyde", "123456", Role.ROLE_PATIENT, 1 ) );
        final Patient avery = new Patient( new UserForm( "avery", "123456", Role.ROLE_PATIENT, 1 ) );
        final Personnel billingStaff = new Personnel( new UserForm( "bsm", "654322", Role.ROLE_HCP, 1 ) );

        // patients and personnel get added to the database
        patientService.save( clyde );
        personnelService.save( billingStaff );

        // add in cpt codes
        final CPTCode surgeryCode = new CPTCode();
        surgeryCode.setCode( "21000" );
        surgeryCode.setDescription( "Musculoskeletal" );
        surgeryCode.setId( 1L );
        surgeryCode.setCost( 10 );

        final CPTCode therapyCode = new CPTCode();
        therapyCode.setCode( "90832" );
        therapyCode.setDescription( "30 minutes of psychotherapy" );
        therapyCode.setId( 2L );
        therapyCode.setCost( 10 );

        final CPTCode bloodCount = new CPTCode();
        bloodCount.setCode( "85025" );
        bloodCount.setDescription( "Platelet Count" );
        bloodCount.setId( 3L );
        bloodCount.setCost( 10 );

        // codes going in database
        codeService.save( surgeryCode );
        codeService.save( therapyCode );

        // merely coalescing the cpt codes
        final List<CPTCode> codesClyde = codeService.findAll(); // clyde has the
                                                                // surgery and
                                                                // therapy codes

        codeService.save( bloodCount );
        final List<CPTCode> codesAvery = codeService.findAll(); // avery has
                                                                // those codes
                                                                // and the blood
                                                                // count code

        // make a bill, store it in the database
        final Bill billClyde = new Bill( clyde, billingStaff, codesClyde );
        billService.save( billClyde );

        // this bill will update the former bill
        final Bill billAvery = new Bill( avery, billingStaff, codesClyde );

        // avery's bill is never saved

        // the api will take care of the updates and return the bill ( with its
        // new lease on life )
        final String updatedBill = mvc
                .perform( put( "/api/v1/bills/" + billClyde.getId() ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( billAvery ) ) )
                .andReturn().getResponse().getContentAsString();

        // making sure the updated bill is avery
        assertNotNull( updatedBill );
        assertFalse( updatedBill.contains( "clyde" ) );
        assertTrue( updatedBill.contains( "avery" ) );

        // also clyde's bill shouldn't exist anymore, it was updated to avery's
        final String allBills = mvc.perform( get( "/api/v1/bills/" ).contentType( MediaType.APPLICATION_JSON ) )
                .andReturn().getResponse().getContentAsString();
        assertFalse( allBills.contains( "clyde" ) );
        assertTrue( allBills.contains( "avery" ) );
    }
}
