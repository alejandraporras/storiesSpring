package edu.upc.news.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FacebookController {
	
	public static final String STATE = "state";
	
	private static final String APP_FACEBOOK_ID = "862153387189829"; 
	private static final String APP_FACEBOOK_SECRET = "fe92d873f8a8a58cd87c01617a824caf";
	private static final String APP_HOST = "http://localhost:8080/project-stories";
	
	private final FacebookConnectionFactory facebookConnectionFactory;
	
	private String currentUser;
	
	
	
	// Constructor: se crea el connection factory de Facebook
	// En teoria Spring llama una sola vez este contructor cuando intancia esta clase
	public FacebookController() {
		facebookConnectionFactory = new FacebookConnectionFactory(APP_FACEBOOK_ID,APP_FACEBOOK_SECRET);
	}
	

	
	// El metodo que trata la opcion 'Facebook'
	// Prepara la conexion a Facebook y luego redirige el flujo a esa pagina
	@RequestMapping("/login")
	public RedirectView startLogin(HttpSession session)  throws Exception {
		
		if (currentUser != null) {
			
	
			String userName = (String) session.getAttribute("userName");
			System.out.println("Ya existe login user..."+ currentUser + " userName:"+userName);
			return new RedirectView("stories");
		}
		
		
		String state = UUID.randomUUID().toString();
		session.setAttribute(STATE, state);
		
		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(APP_HOST + "/callback");
		params.setState(state);
		params.setScope("email");
		 
	
		 
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
		return new RedirectView(authorizeUrl);
		
	
	}
	
	// Esto se llama despues que se ha hecho login en facebook
	@RequestMapping("/callback")
	public RedirectView callBack(@RequestParam("code") String code,
	                             @RequestParam("state") String state,
	                             HttpSession session) {
	
		String stateFromSession = (String) session.getAttribute(STATE);
		session.removeAttribute(STATE);
		if (!state.equals(stateFromSession)) {
			System.out.println("algun problema");
			return new RedirectView("stories");
		}
	 
		AccessGrant accessGrant = getAccessGrant(code);

		// Recuperamos los datos del user Facebook
		Connection<Facebook> connection =  facebookConnectionFactory.createConnection(accessGrant);
		ConnectionKey connectionKey = connection.getKey();
		currentUser = connectionKey.getProviderUserId();  // Un ID unico del user Facebook
		UserProfile userProfile = connection.fetchUserProfile();

		System.out.println("EMAIL: "+ userProfile.getEmail());
		
		String userName = userProfile.getName();
		
		session.setAttribute("userName", userName);

		System.out.println("facebookUserId:"+currentUser + " userName:"+userName);
		return new RedirectView("stories");
	}
	
	private AccessGrant getAccessGrant(String authorizationCode) {		
		
		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
		
		return oauthOperations.exchangeForAccess(authorizationCode,
				APP_HOST + "/callback", null);
		
	}
	

}
