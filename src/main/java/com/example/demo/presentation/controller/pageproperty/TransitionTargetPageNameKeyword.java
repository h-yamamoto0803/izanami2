package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

public class TransitionTargetPageNameKeyword {
	
    //共通のHTML
    public static final String MENU_HTML ="menu";
	//消費者アカウント登録・削除・編集関連のHTML
	public static final String CUSTOMER_ACCOUNT_HTML = "customer-account";
	public static final String CUSTOMER_ACCOUNT_EDIT_HTML = "customer-account-edit";
	public static final String CUSTOMER_ACCOUNT_EDIT_CONFIRM_HTML = "customer-account-edit-confirm";

	//消費者アカウント登録・削除・編集関連のController
	public static final String CUSTOMER_ACCOUNT = "/CustomerAccount";

	public static final String CUSTOMER_ACCOUNT_EDIT = "/CustomerAccountEdit";
	public static final String CONFIRM_CUSTOMER_ACCOUNT_EDIT = "/ConfirmCustomerAccountEdit";
	public static final String DO_EDIT_CUSTOMER_ACCOUNT = "/DoEditCustomerAccount";

	public static String getArtisanPageList() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public static Set<String> getCustomerPageList() {
		// TODO 自動生成されたメソッド・スタブ
		return Set.of(
				CUSTOMER_ACCOUNT,
				CUSTOMER_ACCOUNT_EDIT,
				CONFIRM_CUSTOMER_ACCOUNT_EDIT,
				DO_EDIT_CUSTOMER_ACCOUNT
				
				
				);
	}

}
