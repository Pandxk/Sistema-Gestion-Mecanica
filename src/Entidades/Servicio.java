/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Date
 */
@Entity
@Table(name = "servicio", catalog = "bdmecanica", schema = "")
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s"),
    @NamedQuery(name = "Servicio.findByIdServicio", query = "SELECT s FROM Servicio s WHERE s.idServicio = :idServicio"),
    @NamedQuery(name = "Servicio.findByMantenimiento", query = "SELECT s FROM Servicio s WHERE s.mantenimiento = :mantenimiento"),
    @NamedQuery(name = "Servicio.findByKilometraje", query = "SELECT s FROM Servicio s WHERE s.kilometraje = :kilometraje"),
    @NamedQuery(name = "Servicio.findByDescripcion", query = "SELECT s FROM Servicio s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Servicio.findByEstado", query = "SELECT s FROM Servicio s WHERE s.estado = :estado"),
    @NamedQuery(name = "Servicio.findByCosto", query = "SELECT s FROM Servicio s WHERE s.costo = :costo")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idServicio")
    private Integer idServicio;
    @Column(name = "mantenimiento")
    private String mantenimiento;
    @Column(name = "kilometraje")
    private String kilometraje;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private Double costo;
    @JoinColumn(name = "Vehiculo_idVehiculo", referencedColumnName = "idVehiculo")
    @ManyToOne(optional = false)
    private Vehiculo vehiculoidVehiculo;

    public Servicio() {
    }

    public Servicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Vehiculo getVehiculoidVehiculo() {
        return vehiculoidVehiculo;
    }

    public void setVehiculoidVehiculo(Vehiculo vehiculoidVehiculo) {
        this.vehiculoidVehiculo = vehiculoidVehiculo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Servicio[ idServicio=" + idServicio + " ]";
    }
    
}
