package edu.ncsu.csc.iTrust2.controllers.routing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ncsu.csc.iTrust2.models.enums.Role;

/**
 * Controller to manage basic abilities for BSM roles
 *
 * @author Kai Presler-Marshall
 *
 */

@Controller
public class BSMController {

    /**
     * Returns the BSM for the given model
     *
     * @param model
     *            model to check
     * @return role
     */
    @RequestMapping ( value = "bsm/index" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String index ( final Model model ) {
        return Role.ROLE_BSM.getLanding();
    }

    /**
     * Add code
     *
     * @param model
     *            data for front end
     * @return mapping
     */
    @RequestMapping ( value = "bsm/cptcode" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String addCode ( final Model model ) {
        return "/bsm/manageCPTCodes";
    }

    /**
     * Add code
     *
     * @param model
     *            data for front end
     * @return mapping
     */
    @RequestMapping ( value = "bsm/cptcode/history" )
    @PreAuthorize ( "hasRole('ROLE_BSM')" )
    public String view ( final Model model ) {
        return "/bsm/viewArchivedCPTCodes";
    }
}
