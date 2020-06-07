package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the inquilino database table.
 * 
 */
@Entity
@NamedQuery(name = "Inquilino.findAll", query = "SELECT i FROM Inquilino i")
public class Inquilino implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private boolean active;

	private String dni;
	
	private String email;

	private String pais;
	
	private String telefono;
	
	private String vivienda;

	private String firstname;

	private String lastname;
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;


	// bi-directional many-to-many association to Alquiler
	@OneToMany(mappedBy = "inquilino")
	private List<Alquiler> alquilers;

	
	public void setDatos(Inquilino i) {
		this.id = i.getId();
		this.active = i.getActive();
		this.dni = i.getDni();
		this.email = i.getEmail();
		this.pais = i.getPais();
		this.telefono = i.getTelefono();
		this.vivienda = i.getVivienda();
		this.firstname = i.getFirstname();
		this.lastname = i.getLastname();
		this.user = i.getUser();
		this.alquilers = i.getAlquilers();
	}

	public Inquilino() {
	}

	/**
	 * @return the vivienda
	 */
	public String getVivienda() {
		return vivienda;
	}

	/**
	 * @param vivienda the vivienda to set
	 */
	public void setVivienda(String vivienda) {
		this.vivienda = vivienda;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getNombre() {
		return this.firstname + " " + this.lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Alquiler> getAlquilers() {
		return this.alquilers;
	}

	public void setAlquilers(List<Alquiler> alquilers) {
		for(Alquiler a:alquilers) {
			a.setInquilino(this);
			System.out.println("nombre del inquilino en la reserva("+a.getId()+"): "+a.getInquilino().getNombre());
		}
		this.alquilers = alquilers;
	}

}