package edu.ncsu.csc.iTrust2.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
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
    }

    @SuppressWarnings ( "unchecked" )
    @Test
    @Transactional
    @WithMockUser ( username = "billing staff member", roles = { "USER", "BSM" } )
    public void testRetrievingBills () throws Exception {

        // add in a patient first
        final Patient clyde = new Patient( new UserForm( "patient", "123456", Role.ROLE_PATIENT, 1 ) );
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
        surgeryCode.setCode( "90832" );
        surgeryCode.setDescription( "30 minutes of psychotherapy" );
        surgeryCode.setId( 2L );
        surgeryCode.setCost( 10 );

        // merely coalescing the cpt codes
        final List<CPTCode> codes = new ArrayList<CPTCode>();

        codes.add( surgeryCode );
        codes.add( therapyCode );

        // codes going in database
        codeService.save( surgeryCode );
        codeService.save( therapyCode );

        // make a bill, store it in the database
        final Bill billClyde = new Bill(); // missing "clyde, billingStaff,
                                           // codes"
        // for now
        billService.save( billClyde );

        // now retrieve all bills
        final String content = mvc.perform( get( "/api/v1/bills/" ).contentType( MediaType.APPLICATION_JSON ) )
                .andReturn().getResponse().getContentAsString();

        // now make another patient

        // give them a bill

        // store said bill in the database

        // retrieve all bills, make sure bills from both patients are included

        // retrieve bill from one patient
    }

}
