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
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        
        String text = request.getParameter("textin");
        
        User user = userService.findByName("Ale");
        itemService.newStory(title, url, user);
        System.out.println("  -- " + text );
        /*
        if (name != null)
            guestDao.persist(new Guest(name));
 
        // Prepare the result view (guest.jsp):
         
        return new ModelAndView("guest.jsp", "guestDao", guestDao);
         */
         return listStories(model);
    }
	
	
	@RequestMapping("/comments")
	public String comments(@RequestParam("idStory") Integer story, Model model) {
		
		model.addAttribute("idStory", story);
		

		Item item = itemService.findItem(story.longValue());
		
		model.addAttribute("node", item);
		
		List<Comment> comments = item.getComments();
		model.addAttribute("commentsOfC", comments);
		model.addAttribute("story", item);
		return	"comments";		
		
	}
	
	@RequestMapping("/ask")
	public String allAsk(Model model) {


		model.addAttribute("asks",itemService.getAllAsk());
		return "ask";
		
	}

	@RequestMapping("/submit")
	public String submit(){
		//TODO 
		return "submit";
	}

	@RequestMapping("/reply")
	public String replyComment(@RequestParam("idComment") Integer comment, Model model) {
		
	
		return	"reply";		
		
	}

}
