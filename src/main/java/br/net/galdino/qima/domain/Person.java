package br.net.galdino.qima.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Person extends BaseEntity {
	
	private static final long serialVersionUID = 8870778726908710726L;

	@NotNull(message = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
	@Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
	@Email(message = "Email should be valid")
	private String email;
	
	@NotNull(message = "Password is mandatory")
	@NotBlank(message = "Password is mandatory")
	@Size(min = 2, max = 50, message = "Password must be between 2 and 20 characters")
	private String pwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return super.toString() + "Person [email=" + email + ", pwd=" + pwd + "]";
	}
	
	
}
