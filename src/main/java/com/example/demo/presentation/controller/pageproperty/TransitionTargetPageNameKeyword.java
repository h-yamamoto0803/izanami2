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
	public static final String INSERT_CUSTOMER = "/insertCustomer";
	public static final String INSERT_CUSTOMER_INPUT = "/insertCustomerInput";
	public static final String INSERT_CUSTOMER_CONFIRM = "/insertCustomerConfirm";

	//アカウント登録・削除・編集関連のHTML
	public static final String ACCOUNT_HTML = "customer-account";
	public static final String ACCOUNT_EDIT_HTML = "customer-account-edit";
	public static final String ACCOUNT_EDIT_CONFIRM_HTML = "customer-account-edit-confirm";

	//ユーザーアカウント登録・削除・編集関連のController
	public static final String ACCOUNT = "/account";
	public static final String ACCOUNT_EDIT = "/accountEdit";
	public static final String CONFIRM_ACCOUNT_EDIT = "/confirmAccountEdit";
	public static final String DO_EDIT_ACCOUNT = "/doEditCustomerAccount";
	public static final String REDIRECT_ACCOUNT = "redirect:/account";
	public static final String DELETE_ACCOUNT = "/deleteAccount";
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
	public static final String POST_DETAIL = "/postDetail";
	public static final String INSERT_POST = "/insertPost";
	public static final String INSERT_POST_RET = "/insertPostRet";
	public static final String INSERT_POST_CONFIRM = "/insertPostConfirm";
	public static final String DELETE_POST = "/deletePost";
	public static final String DO_INSERT_POST = "/post";
	public static final String FAVORITE = "/favorite";
	public static final String CANDIDATE = "/candidate";
	// HTML
	public static final String POST_DETAIL_HTML = "post-detail";
	public static final String POST_CREATE_HTML = "post-create";
	public static final String POST_CONFIRM_HTML = "post-confirm";
	public static final String INSERT_CUSTOMER_INPUT_HTML = "customer-register";
	public static final String INSERT_CUSTOMER_CONFIRM_HTML = "customer-register-confirm";

	// Form
	public static final String LOGIN_FORM = "loginForm";

	// Portfolio
	// Controller
	public static final String DETAIL_PORTFOLIO = "/portfolio-detail";
	public static final String INSERT_PORTFOLIO = "/portfolio-create";
	public static final String INSERT_PORTFOLIO_RET = "/insertPortfolioRet";
	public static final String INSERT_PORTFOLIO_CONFIRM = "/portfolio-create-confirm";
	public static final String DO_INSERT_PORTFOLIO = "/portfolio";
	public static final String DELETE_PORTFOLIO = "/deletePortfolio";
	public static final String EDIT_PORTFOLIO = "/portfolio-edit";
	public static final String EDIT_PORTFOLIO_CONFIRM = "/portfolio-edit-confirm";
	public static final String DO_EDIT_PORTFOLIO = "/portfolio-update";
	// HTML
	public static final String INSERT_PORTFOLIO_HTML = "portfolio-create";
	public static final String INSERT_PORTFOLIO_CONFIRM_HTML = "portfolio-create-confirm";
	public static final String PORTFOLIO_DETAIL_HTML = "portfolio-detail";
	public static final String EDIT_PORTFOLIO_HTML = "portfolio-edit";
	public static final String EDIT_PORTFOLIO_CONFIRM_HTML = "portfolio-edit-confirm";

	public static Set<String> getArtisanPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				MENU,
				REDIRECT,
				REDIRECT_MENU,

				ACCOUNT,
				ACCOUNT_EDIT,
				CONFIRM_ACCOUNT_EDIT,
				DO_EDIT_ACCOUNT,
				DELETE_ACCOUNT,
				REDIRECT_ACCOUNT,

				POST_DETAIL,
				INSERT_POST,
				INSERT_POST_RET,
				INSERT_POST_CONFIRM,
				DO_INSERT_POST,
				DELETE_POST,

				CANDIDATE,
				LOGOUT_CONTROLLER,
				INSERT_PORTFOLIO,
				INSERT_PORTFOLIO_CONFIRM,
				DO_INSERT_PORTFOLIO,
				DELETE_PORTFOLIO,
				EDIT_PORTFOLIO,
				EDIT_PORTFOLIO_HTML,
				DETAIL_PORTFOLIO,
				PORTFOLIO_DETAIL_HTML,
				EDIT_PORTFOLIO_CONFIRM,
				EDIT_PORTFOLIO_CONFIRM_HTML,
				DO_EDIT_PORTFOLIO);
	}

	public static Set<String> getCustomerPageList() {
		return Set.of(
				INDEX_BLANK,
				INDEX_SLASH,
				MENU,
				REDIRECT,
				REDIRECT_MENU,

				ACCOUNT,
				ACCOUNT_EDIT,
				CONFIRM_ACCOUNT_EDIT,
				DO_EDIT_ACCOUNT,
				DELETE_ACCOUNT,
				REDIRECT_ACCOUNT,

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
				LOGOUT_CONTROLLER,
				DETAIL_PORTFOLIO,
				PORTFOLIO_DETAIL_HTML);
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
				INSERT_CUSTOMER_CONFIRM,
				DETAIL_PORTFOLIO,
				PORTFOLIO_DETAIL_HTML);
	}
}