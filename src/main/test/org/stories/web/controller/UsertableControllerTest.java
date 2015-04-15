package org.stories.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.stories.bean.Usertable;
import org.stories.test.UsertableFactoryForTest;

//--- Services 
import org.stories.business.service.UsertableService;


import org.stories.web.common.Message;
import org.stories.web.common.MessageHelper;
import org.stories.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UsertableControllerTest {
	
	@InjectMocks
	private UsertableController usertableController;
	@Mock
	private UsertableService usertableService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private UsertableFactoryForTest usertableFactoryForTest = new UsertableFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Usertable> list = new ArrayList<Usertable>();
		when(usertableService.findAll()).thenReturn(list);
		
		// When
		String viewName = usertableController.list(model);
		
		// Then
		assertEquals("usertable/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = usertableController.formForCreate(model);
		
		// Then
		assertEquals("usertable/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Usertable)modelMap.get("usertable")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/usertable/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		String id = usertable.getId();
		when(usertableService.findById(id)).thenReturn(usertable);
		
		// When
		String viewName = usertableController.formForUpdate(model, id);
		
		// Then
		assertEquals("usertable/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertable, (Usertable) modelMap.get("usertable"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/usertable/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Usertable usertableCreated = new Usertable();
		when(usertableService.create(usertable)).thenReturn(usertableCreated); 
		
		// When
		String viewName = usertableController.create(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/usertable/form/"+usertable.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertableCreated, (Usertable) modelMap.get("usertable"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = usertableController.create(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("usertable/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertable, (Usertable) modelMap.get("usertable"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/usertable/create", modelMap.get("saveAction"));
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Usertable usertable = usertableFactoryForTest.newUsertable();
		
		Exception exception = new RuntimeException("test exception");
		when(usertableService.create(usertable)).thenThrow(exception);
		
		// When
		String viewName = usertableController.create(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("usertable/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertable, (Usertable) modelMap.get("usertable"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/usertable/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "usertable.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		String id = usertable.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Usertable usertableSaved = new Usertable();
		usertableSaved.setId(id);
		when(usertableService.update(usertable)).thenReturn(usertableSaved); 
		
		// When
		String viewName = usertableController.update(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/usertable/form/"+usertable.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertableSaved, (Usertable) modelMap.get("usertable"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = usertableController.update(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("usertable/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertable, (Usertable) modelMap.get("usertable"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/usertable/update", modelMap.get("saveAction"));
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Usertable usertable = usertableFactoryForTest.newUsertable();
		
		Exception exception = new RuntimeException("test exception");
		when(usertableService.update(usertable)).thenThrow(exception);
		
		// When
		String viewName = usertableController.update(usertable, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("usertable/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(usertable, (Usertable) modelMap.get("usertable"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/usertable/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "usertable.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		String id = usertable.getId();
		
		// When
		String viewName = usertableController.delete(redirectAttributes, id);
		
		// Then
		verify(usertableService).delete(id);
		assertEquals("redirect:/usertable", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Usertable usertable = usertableFactoryForTest.newUsertable();
		String id = usertable.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(usertableService).delete(id);
		
		// When
		String viewName = usertableController.delete(redirectAttributes, id);
		
		// Then
		verify(usertableService).delete(id);
		assertEquals("redirect:/usertable", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "usertable.error.delete", exception);
	}
	
	
}
