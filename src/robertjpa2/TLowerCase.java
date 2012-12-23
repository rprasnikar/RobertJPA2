/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robertjpa2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author robert
 */
@Entity
@Table(name = "t_lower_case")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TLowerCase.findAll", query = "SELECT t FROM TLowerCase t"),
    @NamedQuery(name = "TLowerCase.findByNameValue", query = "SELECT t FROM TLowerCase t WHERE t.nameValue = :nameValue"),
    @NamedQuery(name = "TLowerCase.findById", query = "SELECT t FROM TLowerCase t WHERE t.id = :id")})
public class TLowerCase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "name_value")
    private String nameValue;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public TLowerCase() {
    }

    public TLowerCase(Integer id) {
        this.id = id;
    }

    public String getNameValue() {
        return nameValue;
    }

    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TLowerCase)) {
            return false;
        }
        TLowerCase other = (TLowerCase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "robertjpa2.TLowerCase[ id=" + id + " ]";
    }
    
}
