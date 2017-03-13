package org.molw.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface BirdsRepository extends CrudRepository<BirdobsEntity, Long>, QueryByExampleExecutor<BirdobsEntity> {
	List<BirdobsEntity> findAll(Pageable pageable);

	List<BirdobsEntity> findByCommonName(String commonName);

	List<BirdobsEntity> findByObserverId(String id);
}
