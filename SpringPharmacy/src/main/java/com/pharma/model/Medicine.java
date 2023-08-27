package com.pharma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Medicine {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 0, max = 50)
	private String name;
	
	@Min(1)
    private int price;
	
    public Medicine() {
		super();
	}
    public Medicine( @NotNull @Size(min = 0, max = 50) String name, @Min(1) int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public Medicine(Long id, @NotNull @Size(min = 0, max = 50) String name, @Min(1) int price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
    
}
