package edu.ncsu.csc.iTrust2.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Public class for the CPTCodes
 *
 * @author colinscanlon
 *
 */
public class CPTCode extends DomainObject {

    /**
     * ID of this CPTCode
     */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long    id;

    /**
     * The code of the CPTCode
     */
    private Integer code;

    /**
     * The cost of the CPTCode
     */
    private Integer cost;

    /**
     * Description of the diagnosis
     */
    private String  description;

    /**
     * Flag for if the code is archived or not
     */
    private boolean archive;

    /**
     * Empty constructor for Hibernate
     */
    public CPTCode () {

    }

    /**
     * Construct from a form
     *
     * @param form
     *            The form that validates and sanitizes input
     */
    public CPTCode ( final CPTCodeForm form ) {
        setCode( form.getCode() );
        setDescription( form.getDescription() );
        setId( form.getId() );
        setCost( form.getCost() );

        // validate
        if ( description.length() > 250 ) {
            throw new IllegalArgumentException( "Description too long (250 characters max): " + description );
        }
        if ( cost != 3 ) {
            throw new IllegalArgumentException( "Code not 3 digits: " + code );
        }
    }

    /**
     * Gets the id of the CPTCode
     *
     * @return The id of the CPTCode
     *
     */
    @Override
    public Serializable getId () {
        return id;
    }

    /**
     * Sets the ID of the Code
     *
     * @param id
     *            The new ID of the Code. For Hibernate.
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Gets the code of the CPTCode
     *
     * @return the code
     */
    public Integer getCode () {
        return code;
    }

    /**
     * Sets the code
     *
     * @param code
     *            the code to set
     */
    public void setCode ( final Integer code ) {
        this.code = code;
    }

    /**
     * Gets the CPTCode cost
     *
     * @return the cost
     */
    public Integer getCost () {
        return cost;
    }

    /**
     * Sets the CPTCode cost
     *
     * @param cost
     *            the cost to set
     */
    public void setCost ( final Integer cost ) {
        this.cost = cost;
    }

    /**
     * Gets the CPTCode description
     *
     * @return the description
     */
    public String getDescription () {
        return description;
    }

    /**
     * Sets the CPTCode description
     *
     * @param description
     *            the description to set
     */
    public void setDescription ( final String description ) {
        this.description = description;
    }

    /**
     * Returns if the CPTCode was archived or not
     *
     * @return the archive
     */
    public boolean isArchive () {
        return archive;
    }

    /**
     * Sets if the CPTCode is an archive or not
     *
     * @param archive
     *            the archive to set
     */
    public void setArchive ( final boolean archive ) {
        this.archive = archive;
    }

    @Override
    public int hashCode () {
        return Objects.hash( code, cost, description, id );
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final CPTCode other = (CPTCode) obj;
        return Objects.equals( code, other.code ) && Objects.equals( cost, other.cost )
                && Objects.equals( description, other.description ) && Objects.equals( id, other.id );
    }

    @Override
    public String toString () {
        return "CPTCode [id=" + id + ", code=" + code + ", cost=" + cost + ", description=" + description + "]";
    }

}
