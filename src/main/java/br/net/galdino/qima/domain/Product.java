package br.net.galdino.qima.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product extends BaseEntity {
	
	private static final long serialVersionUID = 6365198303285135617L;

	@ManyToOne
	@NotNull(message = "Category is mandatory")
	private Category category;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.0")
	@NotNull(message = "Price is mandatory")
	private BigDecimal price;

	private Boolean available;
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return super.toString() + " - Product [category=" + category + ", price=" + price + ", available=" + available + "]";
	}
	
}
