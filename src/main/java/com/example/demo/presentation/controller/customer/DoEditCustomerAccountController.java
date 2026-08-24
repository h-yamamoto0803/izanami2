package com.example.demo.presentation.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword;

@Controller
public class DoEditCustomerAccountController {
	
//	UpdateUser updateUser;
	
	@RequestMapping(value = TransitionTargetPageNameKeyword.DO_EDIT_CUSTOMER_ACCOUNT, method = RequestMethod.POST)
	public String doEditCustomerAccount() {
		
//		// 画面用の型からビジネスロジック用のモデルに変換し、Updateを実行
//		updateUser.updateUser(beforeEmployee, UpdateEmployeeInfoForm.convertTo(updateEmployeeInfoForm));
//
//        // Update後の情報を再取得
//        LoginUser updatedTargetEmployee = searchEmployee.searchLoginUser(updateEmployeeInfoForm.getMailAddress());
//
//        // ログイン者と更新した対象が同じアカウントだった場合、session情報を更新
//        if (Objects.equals(updatedTargetEmployee.getIdEmployee(), loginUserForm.getIdEmployee())) {
//            // ログイン者の情報をセッション情報に格納
//            httpSession.setAttribute(SessionKeyword.LOGIN_USER, LoginUserForm.convertFrom(updatedTargetEmployee));
//        }
//
//        // 更新結果のメッセージを画面にセットし一覧画面に遷移
//        model.addAttribute(PageReturnAttributeKeyword.MESSAGE_INFO, new MessageForm(UPDATE_COMPLETE));
//
//        // forward 先で不用意な動作をしないために UpdateEmployeeInfoForm をクリア
//        return "forward:" + TransitionTargetPageNameKeyword.SELECT_EMPLOYEE_INFO_FORM_EMPTY;
		System.out.println("アカウント編集登録処理");
		return TransitionTargetPageNameKeyword.MENU_HTML;
		
	}
}



