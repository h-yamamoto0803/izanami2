package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

public class TransitionTargetPageNameKeyword {

	// guest, utill
	
		// HTML
	
	public static final String MENU_HTML="menu";
		// Controller
	public static final String INDEX_BLANK = "";
	public static final String INDEX_SLASH = "/";
	public static final String LOGOUT_CONTROLLER = "/logout";
	public static final String RETURN_MENU = "/menu";
	public static final String REDIRECT = "redirect:";
	// artisan
		// HTML
	public static final String ARTISAN_LOGIN_HTML = "artisan-login";
	public static final String ARTISAN_MENU_HTML = "artisan-menu";

		// Controller
	public static final String LOGIN_ARTISAN_CONTROLLER = "/artisan/login";

	// customer
		// HTML
	public static final String CUSTOMER_LOGIN_HTML = "customer-login";
	public static final String CUSTOMER_MENU_HTML = "customer-menu";

		// Controller
	public static final String LOGIN_CUSTOMER_CONTROLLER = "/customer/login";

	// Post
		// Controller
	public final static String POST_DETAIL = "/post-detail";
	public final static String INSERT_POST = "/insertPost";
	public final static String INSERT_POST_CONFIRM = "/insertPostConfirm";
	public final static String POST = "/post";
	
		// HTML
	public final static String POST_DETAIL_HTML = "post-detail";
	public final static String POST_CREATE_HTML = "post-create";
	public final static String POST_CONFIRM_HTML = "post-confirm";


	// Form
	public final static String LOGIN_FORM = "loginForm";


	public static Set<String> getArtisanPageList() {
		return Set.of(
		);
	}

	public static Set<String> getCustomerPageList() {
		return Set.of(
		);
	}
}