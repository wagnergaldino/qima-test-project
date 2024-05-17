package br.net.galdino.qima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 2202954794164757020L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull(message = "Name is mandatory")
	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
	
	@NotNull(message = "Description is mandatory")
	@NotBlank(message = "Description is mandatory")
	@Size(min = 2, max = 110, message = "Description must be between 2 and 110 characters")
	private String description;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
