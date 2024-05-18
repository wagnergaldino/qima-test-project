package br.net.galdino.qima.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Person {
	
	private static final long serialVersionUID = 8870778726908710726L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotNull(message = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
	@Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
	@Email(message = "Email should be valid")
	private String email;
	
	@NotNull(message = "Password is mandatory")
	@NotBlank(message = "Password is mandatory")
	@Size(min = 2, max = 50, message = "Password must be between 2 and 20 characters")
	private String pwd;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @PrePersist
    public void setCreationDate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    
    @PreUpdate
    public void setChangeDate() {
        this.updatedAt = new Date();
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		return "Person [id=" + id + ", email=" + email + ", pwd=" + pwd + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

}
