package org.comeia.project.repository;

import java.util.Optional;

import org.comeia.project.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {

	Optional<File> findByIdAndDeletedIsFalse(long id);
}
