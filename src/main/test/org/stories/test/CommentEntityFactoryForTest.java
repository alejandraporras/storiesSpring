package org.stories.test;

import org.stories.bean.jpa.CommentEntity;

public class CommentEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public CommentEntity newCommentEntity() {

		Integer id = mockValues.nextInteger();

		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setId(id);
		return commentEntity;
	}
	
}
