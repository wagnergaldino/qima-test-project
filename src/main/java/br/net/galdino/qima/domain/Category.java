package br.net.galdino.qima.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Category extends BaseEntity {
	
	private static final long serialVersionUID = 6526631499666730152L;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return super.toString() + " - Category [products=" + products + "]";
	}
	
}
