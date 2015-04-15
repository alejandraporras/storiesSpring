package org.stories.test;

import org.stories.bean.Submission;

public class SubmissionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Submission newSubmission() {

		Integer id = mockValues.nextInteger();

		Submission submission = new Submission();
		submission.setId(id);
		return submission;
	}
	
}
