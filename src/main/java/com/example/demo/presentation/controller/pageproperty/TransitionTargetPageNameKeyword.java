package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

public class TransitionTargetPageNameKeyword {
	public static final String ARTISAN_LOGIN_HTML = "artisan-login";
	public static final String ARTISAN_MENU_HTML = "artisan-menu";
	public static final String CUSTOMER_LOGIN_HTML = "customer-login";
	public static final String CUSTOMER_MENU_HTML = "customer-menu";


	// guest, utill
		// HTML
	public static final String MENU_HTML= "menu";
		// Controller
	public static final String INDEX_BLANK="";
	public static final String INDEX_SLASH="/";
	// artisan
	
	// customer
	// Post
		//Controller
	public final static String POST_DETAIL = "/post-detail";
		//HTML
	public final static String POST_DETAIL_HTML = "post-detail";
	


	public static final String LOGIN_ARTISAN_CONTROLLER = "/artisan/login";
	public static final String LOGIN_CUSTOMER_CONTROLLER = "/customer/login";
	public static final String LOGOUT_CONTROLLER = "/logout";

	public static final String LOGIN_FORM = "loginForm";
	
	
	public static Set<String> getArtisanPageList() {
	    return Set.of(
	        ARTISAN_LOGIN_HTML,
	        ARTISAN_MENU_HTML
	    );

	}

	public static Set<String> getCustomerPageList() {
	    return Set.of(
	        CUSTOMER_LOGIN_HTML,
	        CUSTOMER_MENU_HTML
	    );
	}
}