package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

public class TransitionTargetPageNameKeyword {


//共通のHTML
	
	
	
	
//消費者アカウント登録・削除・編集関連のHTML
public static final String CUSTOMER_ACCOUNT_HTML = "customer-account";
public static final String CUSTOMER_ACCOUNT_EDIT_HTML = "customer-account-edit";
public static final String CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML = "customer-account-edit-confirm";
//消費者アカウント登録・削除・編集関連のController
public static final String CUSTOMER_ACCOUNT = "/customerAccount";
public static final String CUSTOMER_ACCOUNT_EDIT = "/customerAccountEdit";
public static final String CONFIRM_CUSTOMER_ACCOUNT_EDIT = "/confirmCustomerAccountEdit";
public static final String DO_EDIT_CUSTOMER_ACCOUNT = "/doEditCustomerAccount";
public static final String DELETE_CUSTOMER_ACCOUNT ="/DeleteCustomerAccount";

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
		// Controller
	public static final String LOGIN_ARTISAN_CONTROLLER = "/artisan/login";

	// customer
		// HTML
	public static final String CUSTOMER_LOGIN_HTML = "customer-login";
		// Controller
	public static final String LOGIN_CUSTOMER_CONTROLLER = "/customer/login";
		

	// Post
		// Controller
	public final static String POST_DETAIL = "/postDetail";
	public final static String INSERT_POST = "/insertPost";
	public final static String INSERT_POST_CONFIRM = "/insertPostConfirm";
	public final static String DELETE_POST = "/deletePost";
	public final static String POST = "/post";
		// HTML
	public final static String POST_DETAIL_HTML = "post-detail";
	public final static String POST_CREATE_HTML = "post-create";
	public final static String POST_CONFIRM_HTML = "post-confirm";

	// Form
	public final static String LOGIN_FORM = "loginForm";

	public static Set<String> getArtisanPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				RETURN_MENU,
				REDIRECT,
				LOGIN_ARTISAN_CONTROLLER
		);
	}
	
	public static Set<String> getCustomerPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				RETURN_MENU,
				LOGOUT_CONTROLLER,
				REDIRECT,
				CUSTOMER_ACCOUNT,
				CUSTOMER_ACCOUNT_EDIT,
				CONFIRM_CUSTOMER_ACCOUNT_EDIT,
				DO_EDIT_CUSTOMER_ACCOUNT
				);
	}
	
	public static Set<String> getGuestPageList(){
		return Set.of(

			CUSTOMER_LOGIN_HTML,
			
			CUSTOMER_ACCOUNT,
			CUSTOMER_ACCOUNT_EDIT,
			CONFIRM_CUSTOMER_ACCOUNT_EDIT,
			DO_EDIT_CUSTOMER_ACCOUNT,
			DELETE_CUSTOMER_ACCOUNT,
				INDEX_BLANK,
				INDEX_SLASH,
				RETURN_MENU,
				LOGOUT_CONTROLLER,
				REDIRECT
				);
	}
}