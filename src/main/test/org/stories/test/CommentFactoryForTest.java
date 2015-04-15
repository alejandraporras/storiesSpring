package org.stories.test;

import org.stories.bean.Comment;

public class CommentFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Comment newComment() {

		Integer id = mockValues.nextInteger();

		Comment comment = new Comment();
		comment.setId(id);
		return comment;
	}
	
}
