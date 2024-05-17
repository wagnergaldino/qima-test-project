package br.net.galdino.qima.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.galdino.qima.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
