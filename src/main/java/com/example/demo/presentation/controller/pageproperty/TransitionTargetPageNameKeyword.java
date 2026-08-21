package com.example.demo.presentation.controller.pageproperty;

import java.util.Set;

/**
 * システムで利用しているJSPのリスト
 */
public class TransitionTargetPageNameKeyword {

    // 共通
	public static final String MENU_HTML = "menu";
	public static final String POST_CREATE_HTML = "post-create";
	public static final String POST_CONFIRM_HTML = "post-confirm";

    // 消費者向け
	
    // 管職人向け

    // 共通(Controller)
	public static final String INDEX_BLANK = "";
    public static final String INDEX_SLASH = "/";
	public static final String RETURN_MENU = "/menu";
    public static final String INSERT_POST = "/insertPost";
    public static final String INSERT_POST_CONFIRM = "/insertPostConfirm";

    // 一般向け(Controller)

    // 管理者向け(Controller)

    /**
     * 管理者向けのページリストを返す
     *
     * @return 職人向けのページリスト
     */
    public static Set<String> getArtisanPageList() {

        return Set.of(
        		INDEX_BLANK,
        		INDEX_SLASH,
        		RETURN_MENU,
        		INSERT_POST,
        		INSERT_POST_CONFIRM
        );
    }

    /**
     * 消費者向けのページリストを返す
     *
     * @return 消費者向けのページリスト
     */
    public static Set<String> getCustomerPageList() {
        return Set.of(
        		INDEX_BLANK,
        		INDEX_SLASH,
        		RETURN_MENU,
        		INSERT_POST,
        		INSERT_POST_CONFIRM
        );
    }

}
