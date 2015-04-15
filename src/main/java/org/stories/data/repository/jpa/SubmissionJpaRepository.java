package org.stories.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.stories.bean.jpa.SubmissionEntity;

/**
 * Repository : Submission.
 */
public interface SubmissionJpaRepository extends PagingAndSortingRepository<SubmissionEntity, Integer> {

}
