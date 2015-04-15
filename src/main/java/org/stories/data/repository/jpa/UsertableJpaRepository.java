package org.stories.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.stories.bean.jpa.UsertableEntity;

/**
 * Repository : Usertable.
 */
public interface UsertableJpaRepository extends PagingAndSortingRepository<UsertableEntity, String> {

}
