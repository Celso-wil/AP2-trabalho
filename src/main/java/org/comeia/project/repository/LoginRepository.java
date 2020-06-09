package org.comeia.project.repository;

import java.util.Optional;

import org.comeia.project.domain.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoginRepository extends JpaRepository<Login, Long>, JpaSpecificationExecutor<Login> {

	Optional<Login> findByIdAndDeletedIsFalse(long id);
}
