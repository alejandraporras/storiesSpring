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
import org.stories.bean.Submission;
import org.stories.bean.Usertable;
import org.stories.test.SubmissionFactoryForTest;
import org.stories.test.UsertableFactoryForTest;

//--- Services 
import org.stories.business.service.SubmissionService;
import org.stories.business.service.UsertableService;

//--- List Items 
import org.stories.web.listitem.UsertableListItem;

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
public class SubmissionControllerTest {
	
	@InjectMocks
	private SubmissionController submissionController;
	@Mock
	private SubmissionService submissionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private UsertableService usertableService; // Injected by Spring

	private SubmissionFactoryForTest submissionFactoryForTest = new SubmissionFactoryForTest();
	private UsertableFactoryForTest usertableFactoryForTest = new UsertableFactoryForTest();

	List<Usertable> usertables = new ArrayList<Usertable>();

	private void givenPopulateModel() {
		Usertable usertable1 = usertableFactoryForTest.newUsertable();
		Usertable usertable2 = usertableFactoryForTest.newUsertable();
		List<Usertable> usertables = new ArrayList<Usertable>();
		usertables.add(usertable1);
		usertables.add(usertable2);
		when(usertableService.findAll()).thenReturn(usertables);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Submission> list = new ArrayList<Submission>();
		when(submissionService.findAll()).thenReturn(list);
		
		// When
		String viewName = submissionController.list(model);
		
		// Then
		assertEquals("submission/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = submissionController.formForCreate(model);
		
		// Then
		assertEquals("submission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Submission)modelMap.get("submission")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/submission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Submission submission = submissionFactoryForTest.newSubmission();
		Integer id = submission.getId();
		when(submissionService.findById(id)).thenReturn(submission);
		
		// When
		String viewName = submissionController.formForUpdate(model, id);
		
		// Then
		assertEquals("submission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submission, (Submission) modelMap.get("submission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/submission/update", modelMap.get("saveAction"));
		
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Submission submission = submissionFactoryForTest.newSubmission();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Submission submissionCreated = new Submission();
		when(submissionService.create(submission)).thenReturn(submissionCreated); 
		
		// When
		String viewName = submissionController.create(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/submission/form/"+submission.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submissionCreated, (Submission) modelMap.get("submission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Submission submission = submissionFactoryForTest.newSubmission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = submissionController.create(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("submission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submission, (Submission) modelMap.get("submission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/submission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
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

		Submission submission = submissionFactoryForTest.newSubmission();
		
		Exception exception = new RuntimeException("test exception");
		when(submissionService.create(submission)).thenThrow(exception);
		
		// When
		String viewName = submissionController.create(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("submission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submission, (Submission) modelMap.get("submission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/submission/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "submission.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Submission submission = submissionFactoryForTest.newSubmission();
		Integer id = submission.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Submission submissionSaved = new Submission();
		submissionSaved.setId(id);
		when(submissionService.update(submission)).thenReturn(submissionSaved); 
		
		// When
		String viewName = submissionController.update(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/submission/form/"+submission.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submissionSaved, (Submission) modelMap.get("submission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Submission submission = submissionFactoryForTest.newSubmission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = submissionController.update(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("submission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submission, (Submission) modelMap.get("submission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/submission/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
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

		Submission submission = submissionFactoryForTest.newSubmission();
		
		Exception exception = new RuntimeException("test exception");
		when(submissionService.update(submission)).thenThrow(exception);
		
		// When
		String viewName = submissionController.update(submission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("submission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(submission, (Submission) modelMap.get("submission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/submission/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "submission.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Submission submission = submissionFactoryForTest.newSubmission();
		Integer id = submission.getId();
		
		// When
		String viewName = submissionController.delete(redirectAttributes, id);
		
		// Then
		verify(submissionService).delete(id);
		assertEquals("redirect:/submission", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Submission submission = submissionFactoryForTest.newSubmission();
		Integer id = submission.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(submissionService).delete(id);
		
		// When
		String viewName = submissionController.delete(redirectAttributes, id);
		
		// Then
		verify(submissionService).delete(id);
		assertEquals("redirect:/submission", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "submission.error.delete", exception);
	}
	
	
}
