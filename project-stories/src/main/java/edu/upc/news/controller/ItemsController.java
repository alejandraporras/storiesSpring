package edu.upc.news.controller;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.User;
import edu.upc.news.service.ItemService;
import edu.upc.news.service.UserService;



@Controller
public class ItemsController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
		
	private Integer idStoryGlobal;
	@RequestMapping("/")
	public String listStories(Model model) {
		
		
		model.addAttribute("stories",itemService.getAllStories());
	
		return "stories";	
		
	}
	
	@RequestMapping(value = "/stories", method=RequestMethod.GET)
	public String paginaPrincipal(Model model) {
		
		return listStories(model);
		
	}
	@RequestMapping(value = "/stories", method=RequestMethod.POST)
	public String paginaInicial(HttpServletRequest request, Model model) {
        // Handle a new guest (if any):
		User user = userService.findByName("Ale");
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        String text = request.getParameter("textin");
        
        if (url.isEmpty()) {
        	//ES UN ASK
        	itemService.newAsk(title, user, "", text);
        }
        else{
        	//STORY
        	 itemService.newStory(title, url, user);
   
        }
       
    
         return listStories(model);
    }
	
	
	@RequestMapping("/comments")
	public String comments(@RequestParam("idStory") Integer story, Model model) {
		idStoryGlobal = story;
		System.out.println("HISTORIA: " + idStoryGlobal);
		model.addAttribute("idStory", story);
		

		Item item = itemService.findItem(story.longValue());
		
		model.addAttribute("storyGlobal", item);
		
		List<Comment> comments = item.getComments();
		model.addAttribute("commentsOfC", comments);
		model.addAttribute("story", item);
		return	"comments";		
		
	}
	
	@RequestMapping(value = "/comments", method=RequestMethod.POST)
	public void allComments(@RequestParam("idStory") Integer story, HttpServletRequest request,Model model) {
		
		model.addAttribute("idStory", story);
		
		Item parentItem = itemService.findItem(story.longValue());
		
		User user = userService.findByName("Ale");

        String text = request.getParameter("textin");
        itemService.newComment(user, text, parentItem);
        comments(story,model);
	
	}
	
	
	
	@RequestMapping("/ask")
	public String allAsk(Model model) {


		model.addAttribute("asks",itemService.getAllAsk());
		return "ask";
		
	}

	
	@RequestMapping("/submit")
	public String submit(){
		return "submit";
	}

	@RequestMapping("/reply")
	public String showReplyComment(@RequestParam("idComment") Integer comment, Model model) {
		
		System.out.println("ID COMENT: " + comment);
		Comment c = (Comment) itemService.findItem(comment.longValue());
		model.addAttribute("comment", c);
		return	"reply";		
		
	}
	
	@RequestMapping(value = "/reply", method=RequestMethod.POST)
	public String reply(@RequestParam("idComment") Integer comment, HttpServletRequest request,Model model) {
		
		Item parentItem = itemService.findItem(comment.longValue());
		
		User user = userService.findByName("Ale");

        String text = request.getParameter("textin");
        itemService.newComment(user, text, parentItem);
        System.out.println("-----------" );
       
        
        //TODO MEJORAR ESTO
        model.addAttribute("idStory", idStoryGlobal);
		Item item = itemService.findItem(idStoryGlobal.longValue());
		List<Comment> comments = item.getComments();
		model.addAttribute("commentsOfC", comments);
		model.addAttribute("story", item);
		return	"comments";		
	
	}

}
