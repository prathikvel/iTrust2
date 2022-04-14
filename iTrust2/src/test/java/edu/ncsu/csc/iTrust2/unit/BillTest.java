package edu.ncsu.csc.iTrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.iTrust2.TestConfig;
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

/**
 * tests the bill model
 *
 * @author Jon
 *
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class BillTest {
    @Autowired
    private BillService             service;

    @Autowired
    private PatientService<Patient> patService;

    @Autowired
    private PersonnelService        perService;

    @Autowired
    CPTCodeService                  codeService;

    @Before
    public void setup () {
        service.deleteAll();
        patService.deleteAll();
        perService.deleteAll();
        codeService.deleteAll();
    }

    @Transactional
    @Test
    public void testService () {

        patService.save( new Patient( new UserForm( "patient", "123456", Role.ROLE_PATIENT, 1 ) ) );
        perService.save( new Personnel( new UserForm( "hcp", "654322", Role.ROLE_HCP, 1 ) ) );
        final Patient pat1 = patService.findAll().get( 0 );
        final Personnel per2 = perService.findAll().get( 0 );

        final CPTCode cost1 = new CPTCode();
        cost1.setCode( "00363" );
        cost1.setDescription( "Bandaid" );
        cost1.setId( 1L );
        cost1.setCost( 10 );

        final CPTCode cost2 = new CPTCode();
        cost2.setCode( "00364" );
        cost2.setDescription( "neosporin" );
        cost2.setId( 2L );
        cost2.setCost( 15 );

        final CPTCode cost3 = new CPTCode();
        cost3.setCode( "00365" );
        cost3.setDescription( "Application" );
        cost3.setId( 3L );
        cost3.setCost( 10 );

        codeService.save( cost1 );
        codeService.save( cost2 );
        codeService.save( cost3 );

        final List<CPTCode> codes = codeService.findAll();

        assertEquals( 3, codes.size() );

        final Bill bill = new Bill( pat1, per2, codes );
        service.save( bill );

        final Bill out = service.findByPatient( pat1 ).get( 0 );

        assertEquals( per2, out.getHcp() );
        assertEquals( pat1, out.getPatient() );
        assertEquals( codes, out.getCptCodes() );
        assertEquals( 3, out.getCptCodes().size() );
        assertEquals( (Integer) 35, out.getCost() );

        assertTrue( out.pay( 35 ) );

        assertEquals( (Integer) 0, out.getCost() );
    }

    @Transactional
    @Test
    public void testPayFullBill () {

        final Patient pat1 = new Patient( new UserForm( "patient", "123456", Role.ROLE_PATIENT, 1 ) );
        final Personnel per2 = new Personnel( new UserForm( "hcp", "654322", Role.ROLE_HCP, 1 ) );

        final CPTCode cost1 = new CPTCode();
        cost1.setCode( "00363" );
        cost1.setDescription( "Bandaid" );
        cost1.setId( 1L );
        cost1.setCost( 10 );

        final CPTCode cost2 = new CPTCode();
        cost2.setCode( "00364" );
        cost2.setDescription( "neosporin" );
        cost2.setId( 2L );
        cost2.setCost( 15 );

        final CPTCode cost3 = new CPTCode();
        cost3.setCode( "00364" );
        cost3.setDescription( "Application" );
        cost3.setId( 3L );
        cost3.setCost( 10 );

        final List<CPTCode> codes = new ArrayList<CPTCode>();

        codes.add( cost3 );
        codes.add( cost2 );
        codes.add( cost1 );

        final Bill bill = new Bill( pat1, per2, codes );

        final Bill out = bill;

        assertEquals( per2, out.getHcp() );
        assertEquals( pat1, out.getPatient() );
        assertEquals( (Integer) 35, out.getCost() );
        assertEquals( codes, out.getCptCodes() );

        assertTrue( out.pay( 35 ) );

        assertEquals( (Integer) 0, out.getCost() );
    }

    @Transactional
    @Test
    public void testPayPartBill () {
        final Patient pat1 = new Patient( new UserForm( "patient", "123456", Role.ROLE_PATIENT, 1 ) );
        final Personnel per2 = new Personnel( new UserForm( "bsm", "654322", Role.ROLE_HCP, 1 ) );

        final CPTCode cost1 = new CPTCode();
        cost1.setCode( "00363" );
        cost1.setDescription( "Bandaid" );
        cost1.setId( 1L );
        cost1.setCost( 10 );

        final CPTCode cost2 = new CPTCode();
        cost2.setCode( "00364" );
        cost2.setDescription( "neosporin" );
        cost2.setId( 2L );
        cost2.setCost( 15 );

        final CPTCode cost3 = new CPTCode();
        cost3.setCode( "00364" );
        cost3.setDescription( "Application" );
        cost3.setId( 3L );
        cost3.setCost( 10 );

        final List<CPTCode> codes = new ArrayList<CPTCode>();

        codes.add( cost3 );
        codes.add( cost2 );
        codes.add( cost1 );

        final Bill bill = new Bill( pat1, per2, codes );

        final Bill out = bill;

        assertEquals( per2, out.getHcp() );
        assertEquals( pat1, out.getPatient() );
        assertEquals( (Integer) 35, out.getCost() );
        assertEquals( codes, out.getCptCodes() );

        assertTrue( out.pay( 15 ) );

        assertEquals( (Integer) 20, out.getCost() );

        assertFalse( out.pay( 21 ) );

        assertTrue( out.pay( 20 ) );

        assertEquals( (Integer) 0, out.getCost() );
    }
}
