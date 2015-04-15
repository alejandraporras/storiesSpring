package org.stories.test;

import org.stories.bean.Usertable;

public class UsertableFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Usertable newUsertable() {

		String id = mockValues.nextString(20);

		Usertable usertable = new Usertable();
		usertable.setId(id);
		return usertable;
	}
	
}
