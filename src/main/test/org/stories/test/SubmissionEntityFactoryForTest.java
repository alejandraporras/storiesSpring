package org.stories.test;

import org.stories.bean.jpa.SubmissionEntity;

public class SubmissionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SubmissionEntity newSubmissionEntity() {

		Integer id = mockValues.nextInteger();

		SubmissionEntity submissionEntity = new SubmissionEntity();
		submissionEntity.setId(id);
		return submissionEntity;
	}
	
}
