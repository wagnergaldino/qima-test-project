package br.net.galdino.qima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.galdino.qima.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
