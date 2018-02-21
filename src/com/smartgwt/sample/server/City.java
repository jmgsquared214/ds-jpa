package com.smartgwt.sample.server;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="City")
public class City
    implements Serializable
{

    @Id
    @Column (nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column (nullable = false)
    private String cityName;

    @Column (nullable = false)
    private Long countryId;

    @ManyToOne
    @JoinColumn(name="countryId", referencedColumnName="countryId", nullable=false, insertable=false, updatable=false)
    private Country country;

    public City ()
    {
    }

    public Long getCityId ()
    {
        return cityId;
    }

    public void setCityId (Long cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName ()
    {
        return cityName;
    }

    public void setCityName (String cityName)
    {
        this.cityName = cityName;
    }

    public Long getCountryId ()
    {
        return countryId;
    }

    public void setCountryId (Long countryId)
    {
        this.countryId = countryId;
    }

    public Country getCountry () {
        return country;
    }

    public void setCountry (Country country) {
        this.country = country;
    }

    /**
     * Returns a string representation of the object. Resulting string contains
     * full name of the class and list of its properties and their values.
     *
     * @return <code>String</code> representation of this object.
     */
    @Override
    public String toString ()
    {
        return getClass().getName()
               + "["
               + "cityId=" + ((getCityId() == null) ? "null" : getCityId().toString())
               + ", "
               + "cityName=" + ((getCityName() == null) ? "null" : getCityName().toString())
               + "]";
    }

}
