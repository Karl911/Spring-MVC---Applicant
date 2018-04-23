package com.kfa.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LISTECOURSES")
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="IDOBJET")
	private Integer id;
	
	@Column(name="LIBELLE")
	private String libelle;
	
	@Column(name="QUANTITE")
	private Integer quantity;
	
	public Course()
	{
		
	}
	public Course (int id, String libelle, int quantity)
	{
		this.id=id;
		this.libelle = libelle;
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return String.format("[id=%s, libelle=%s, quantity=%d]", id, libelle, quantity);
		//return String.format("[prenom=%s, nom=%s,  age=%d]", prenom, nom, age);
	}
	
}
