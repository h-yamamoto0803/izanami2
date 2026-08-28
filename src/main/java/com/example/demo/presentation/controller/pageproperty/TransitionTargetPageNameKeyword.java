package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

public class TransitionTargetPageNameKeyword {

	// guest, utill
	public static final String MENU_HTML = "menu";
	// Controller
	public static final String INDEX_BLANK = "";
	public static final String INDEX_SLASH = "/";
	public static final String LOGOUT_CONTROLLER = "/logout";
	public static final String MENU = "/menu";
	public static final String REDIRECT = "redirect:";
	public static final String REDIRECT_MENU = "redirect:/menu";

	// artisan
	// HTML
	public static final String ARTISAN_LOGIN_HTML = "artisan-login";
	// Controller
	public static final String LOGIN_ARTISAN_CONTROLLER = "/artisan/login";

	// customer
	// HTML
	public static final String CUSTOMER_LOGIN_HTML = "customer-login";
	//消費者アカウント登録・削除・編集関連のHTML
	public static final String CUSTOMER_ACCOUNT_HTML = "customer-account";
	public static final String CUSTOMER_ACCOUNT_EDIT_HTML = "customer-account-edit";
	public static final String CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML = "customer-account-edit-confirm";
	// Controller
	public static final String LOGIN_CUSTOMER_CONTROLLER = "/customer/login";
	//消費者アカウント登録・削除・編集関連のController
	public static final String INSERT_CUSTOMER = "/insertCustomer";
	public static final String INSERT_CUSTOMER_INPUT = "/insertCustomerInput";
	public static final String INSERT_CUSTOMER_CONFIRM = "/insertCustomerConfirm";
	public static final String CUSTOMER_ACCOUNT = "/customerAccount";
	public static final String CUSTOMER_ACCOUNT_EDIT = "/customerAccountEdit";
	public static final String CONFIRM_CUSTOMER_ACCOUNT_EDIT = "/confirmCustomerAccountEdit";
	public static final String DO_EDIT_CUSTOMER_ACCOUNT = "/doEditCustomerAccount";
	public static final String DELETE_CUSTOMER_ACCOUNT = "/DeleteCustomerAccount";

	// Post
	// Controller
	public final static String POST_DETAIL = "/postDetail";
	public final static String INSERT_POST = "/insertPost";
	public final static String INSERT_POST_RET = "/insertPostRet";
	public final static String INSERT_POST_CONFIRM = "/insertPostConfirm";
	public final static String DELETE_POST = "/deletePost";
	public final static String DO_INSERT_POST = "/post";
	public final static String FAVORITE = "/favorite";
	public final static String CANDIDATE = "/candidate";
	// HTML
	public final static String POST_DETAIL_HTML = "post-detail";
	public final static String POST_CREATE_HTML = "post-create";
	public final static String POST_CONFIRM_HTML = "post-confirm";
	public final static String INSERT_CUSTOMER_INPUT_HTML = "customer-register";
	public static final String INSERT_CUSTOMER_CONFIRM_HTML = "customer-register-confirm";

	// Form
	public final static String LOGIN_FORM = "loginForm";

	public static Set<String> getArtisanPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				MENU,
				REDIRECT,
				REDIRECT_MENU,
				
				POST_DETAIL,
				INSERT_POST,
				INSERT_POST_RET,
				INSERT_POST_CONFIRM,
				DO_INSERT_POST,
				DELETE_POST,
				
				CANDIDATE,
				LOGOUT_CONTROLLER
				);
	}

	public static Set<String> getCustomerPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				MENU,
				REDIRECT,
				REDIRECT_MENU,
				
				CUSTOMER_ACCOUNT,
				CUSTOMER_ACCOUNT_EDIT,
				CONFIRM_CUSTOMER_ACCOUNT_EDIT,
				DO_EDIT_CUSTOMER_ACCOUNT,
				DELETE_CUSTOMER_ACCOUNT,
				
				INSERT_CUSTOMER,
				INSERT_CUSTOMER_INPUT,
				INSERT_CUSTOMER_CONFIRM,
				
				POST_DETAIL,
				INSERT_POST,
				INSERT_POST_RET,
				INSERT_POST_CONFIRM,
				DO_INSERT_POST,
				DELETE_POST,

				FAVORITE,
				LOGOUT_CONTROLLER
				);
	}

	public static Set<String> getGuestPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				MENU,
				REDIRECT,
				REDIRECT_MENU,
				
				INSERT_POST,
				POST_DETAIL,
				
				LOGIN_CUSTOMER_CONTROLLER,
				LOGIN_ARTISAN_CONTROLLER,
				INSERT_CUSTOMER,
				INSERT_CUSTOMER_INPUT,
				INSERT_CUSTOMER_CONFIRM
				);
	}
}