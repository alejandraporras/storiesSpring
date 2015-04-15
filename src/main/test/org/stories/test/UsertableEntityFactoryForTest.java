package org.stories.test;

import org.stories.bean.jpa.UsertableEntity;

public class UsertableEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UsertableEntity newUsertableEntity() {

		String id = mockValues.nextString(20);

		UsertableEntity usertableEntity = new UsertableEntity();
		usertableEntity.setId(id);
		return usertableEntity;
	}
	
}
