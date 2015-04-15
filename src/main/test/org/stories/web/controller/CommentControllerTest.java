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
import org.stories.bean.Comment;
import org.stories.bean.Usertable;
import org.stories.bean.Submission;
import org.stories.test.CommentFactoryForTest;
import org.stories.test.UsertableFactoryForTest;
import org.stories.test.SubmissionFactoryForTest;

//--- Services 
import org.stories.business.service.CommentService;
import org.stories.business.service.UsertableService;
import org.stories.business.service.SubmissionService;

//--- List Items 
import org.stories.web.listitem.UsertableListItem;
import org.stories.web.listitem.SubmissionListItem;

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
public class CommentControllerTest {
	
	@InjectMocks
	private CommentController commentController;
	@Mock
	private CommentService commentService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private UsertableService usertableService; // Injected by Spring
	@Mock
	private SubmissionService submissionService; // Injected by Spring

	private CommentFactoryForTest commentFactoryForTest = new CommentFactoryForTest();
	private UsertableFactoryForTest usertableFactoryForTest = new UsertableFactoryForTest();
	private SubmissionFactoryForTest submissionFactoryForTest = new SubmissionFactoryForTest();

	List<Usertable> usertables = new ArrayList<Usertable>();
	List<Submission> submissions = new ArrayList<Submission>();

	private void givenPopulateModel() {
		Usertable usertable1 = usertableFactoryForTest.newUsertable();
		Usertable usertable2 = usertableFactoryForTest.newUsertable();
		List<Usertable> usertables = new ArrayList<Usertable>();
		usertables.add(usertable1);
		usertables.add(usertable2);
		when(usertableService.findAll()).thenReturn(usertables);

		Submission submission1 = submissionFactoryForTest.newSubmission();
		Submission submission2 = submissionFactoryForTest.newSubmission();
		List<Submission> submissions = new ArrayList<Submission>();
		submissions.add(submission1);
		submissions.add(submission2);
		when(submissionService.findAll()).thenReturn(submissions);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Comment> list = new ArrayList<Comment>();
		when(commentService.findAll()).thenReturn(list);
		
		// When
		String viewName = commentController.list(model);
		
		// Then
		assertEquals("comment/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = commentController.formForCreate(model);
		
		// Then
		assertEquals("comment/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Comment)modelMap.get("comment")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comment/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comment comment = commentFactoryForTest.newComment();
		Integer id = comment.getId();
		when(commentService.findById(id)).thenReturn(comment);
		
		// When
		String viewName = commentController.formForUpdate(model, id);
		
		// Then
		assertEquals("comment/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comment, (Comment) modelMap.get("comment"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comment/update", modelMap.get("saveAction"));
		
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Comment comment = commentFactoryForTest.newComment();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Comment commentCreated = new Comment();
		when(commentService.create(comment)).thenReturn(commentCreated); 
		
		// When
		String viewName = commentController.create(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/comment/form/"+comment.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(commentCreated, (Comment) modelMap.get("comment"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comment comment = commentFactoryForTest.newComment();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = commentController.create(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comment/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comment, (Comment) modelMap.get("comment"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comment/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
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

		Comment comment = commentFactoryForTest.newComment();
		
		Exception exception = new RuntimeException("test exception");
		when(commentService.create(comment)).thenThrow(exception);
		
		// When
		String viewName = commentController.create(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comment/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comment, (Comment) modelMap.get("comment"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comment/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "comment.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Comment comment = commentFactoryForTest.newComment();
		Integer id = comment.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Comment commentSaved = new Comment();
		commentSaved.setId(id);
		when(commentService.update(comment)).thenReturn(commentSaved); 
		
		// When
		String viewName = commentController.update(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/comment/form/"+comment.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(commentSaved, (Comment) modelMap.get("comment"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comment comment = commentFactoryForTest.newComment();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = commentController.update(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comment/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comment, (Comment) modelMap.get("comment"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comment/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
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

		Comment comment = commentFactoryForTest.newComment();
		
		Exception exception = new RuntimeException("test exception");
		when(commentService.update(comment)).thenThrow(exception);
		
		// When
		String viewName = commentController.update(comment, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comment/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comment, (Comment) modelMap.get("comment"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comment/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "comment.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<UsertableListItem> usertableListItems = (List<UsertableListItem>) modelMap.get("listOfUsertableItems");
		assertEquals(2, usertableListItems.size());
		
		@SuppressWarnings("unchecked")
		List<SubmissionListItem> submissionListItems = (List<SubmissionListItem>) modelMap.get("listOfSubmissionItems");
		assertEquals(2, submissionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Comment comment = commentFactoryForTest.newComment();
		Integer id = comment.getId();
		
		// When
		String viewName = commentController.delete(redirectAttributes, id);
		
		// Then
		verify(commentService).delete(id);
		assertEquals("redirect:/comment", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Comment comment = commentFactoryForTest.newComment();
		Integer id = comment.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(commentService).delete(id);
		
		// When
		String viewName = commentController.delete(redirectAttributes, id);
		
		// Then
		verify(commentService).delete(id);
		assertEquals("redirect:/comment", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "comment.error.delete", exception);
	}
	
	
}
