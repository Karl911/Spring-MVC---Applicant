package com.kfa.demo;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class CreationForm {

	@NotEmpty
	private String libelle;
	
	@NotEmpty
	@Pattern(regexp="\\d")
	private String quantity;
	
	public String getLibelle()
	{
		return libelle;
	}
	
	public void setLibelle(final String libelle)
	{
		this.libelle = libelle;
	}
	
	public String getQuantity()
	{
		return quantity;
	}
	public void setQuantity(final String quantity)
	{
		this.quantity = quantity;
	}
	
}
