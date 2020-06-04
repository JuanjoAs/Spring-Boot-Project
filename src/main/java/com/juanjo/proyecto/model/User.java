package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private boolean active;

	private String email;

	private String firstname;

	private String lastname;

	private String password;

	//bi-directional many-to-one association to Casa
	@OneToMany(mappedBy="user")
	private List<Casa> casas;
	


	@OneToMany(mappedBy="user")
	private List<Inquilino> inquilino;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;


	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	/**
	 * @return the inquilino
	 */
	public List<Inquilino> getInquilino() {
		return inquilino;
	}

	/**
	 * @param inquilino the inquilino to set
	 */
	public void setInquilino(List<Inquilino> inquilino) {
		this.inquilino = inquilino;
	}

	public User() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Casa> getCasas() {
		return this.casas;
	}

	public void setCasas(List<Casa> casas) {
		this.casas = casas;
	}

	public Casa addCasa(Casa casa) {
		getCasas().add(casa);
		casa.setUser(this);

		return casa;
	}

	public Casa removeCasa(Casa casa) {
		getCasas().remove(casa);
		casa.setUser(null);

		return casa;
	}

}