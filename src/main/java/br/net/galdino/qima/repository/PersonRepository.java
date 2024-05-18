package br.net.galdino.qima.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.galdino.qima.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Person findByEmailAndPwd(String email, String pwd);
}
