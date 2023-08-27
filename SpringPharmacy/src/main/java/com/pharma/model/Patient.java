package com.pharma.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Patient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull
	@Size(min = 0, max = 30)
	private String firstName;
	private String lastName;
	
	@NotNull
	@Email(message = "Please provide valid email")
    private String email;
	
	@NotNull
	@Pattern(regexp = "^\\d{10}$", message="Please provide ten digits")
	private String contact;
	
	@Min(1)
    private int age;
	
	@Pattern(regexp = "^[M|F|O]$", message="Gender should be M,F and O")
    private String gender;
    
	public Patient() {
		super();
	}
	public Patient( @NotNull @Size(min = 0, max = 30) String firstName, String lastName,
			@NotNull @Email(message = "Please provide valid email") String email,
			@NotNull @Pattern(regexp = "^\\d{10}$", message = "Please provide ten digits") String contact,
			@Min(1) int age, @Pattern(regexp = "^[M|F|O]$", message="Gender should be M,F and O") String gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contact = contact;
		this.age = age;
		this.gender = gender;
	}
	
	public Patient(Long id, @NotNull @Size(min = 0, max = 30) String firstName, String lastName,
			@NotNull @Email(message = "Please provide valid email") String email,
			@NotNull @Pattern(regexp = "^\\d{10}$", message = "Please provide ten digits") String contact,
			@Min(1) int age, @Pattern(regexp = "^[M|F|O]$", message="Gender should be M,F and O") String gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contact = contact;
		this.age = age;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", contact=" + contact + ", age=" + age + ", gender=" + gender + "]";
	}
	
}
