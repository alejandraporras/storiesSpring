package org.stories.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.stories.bean.jpa.CommentEntity;

/**
 * Repository : Comment.
 */
public interface CommentJpaRepository extends PagingAndSortingRepository<CommentEntity, Integer> {

}
