package com.example.demo.presentation.controller.customer;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.customer.DeleteCustomerAccount;
import com.example.demo.infra.entity.UserEntity;
import com.example.demo.presentation.controller.pageproperty.PageReturnAttributeKeyword;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.customer.CustomerAccountEditForm;

@Controller
public class DeleteCustomerAccountController {
	HttpSession httpSession;
	LoginUserForm loginUserForm;
	DeleteCustomerAccount deleteCustomerAccount;

	public DeleteCustomerAccountController(HttpSession httpSession, DeleteCustomerAccount deleteCustomerAccount) {
		this.httpSession = httpSession;
		this.deleteCustomerAccount = deleteCustomerAccount;
	}
	
	@PermissionCheck
	@GetMapping(DELETE_ACCOUNT)
	public String deleteCustomerAccount(Model model,
			@ModelAttribute CustomerAccountEditForm customerAccountEditForm,
    		RedirectAttributes redirect,
			HttpSession session) {

		try {
			LoginUserForm loginUser = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);
			CustomerAccountEditForm beforecustomerAccountEditForm = deleteCustomerAccount.searchIdCustomer(loginUser.getUserId());
			UserEntity deleteUserEntity = CustomerAccountEditForm.convertTo(beforecustomerAccountEditForm);

			deleteUserEntity.setIsDeleted((byte) 1);
			deleteCustomerAccount.deleteCustomer(beforecustomerAccountEditForm,deleteUserEntity);
			String deleteMessage = "ユーザーの削除が完了しました";
			httpSession.invalidate();
	        redirect.addFlashAttribute(PageReturnAttributeKeyword.DELETE_MESSAGE, deleteMessage);

			return REDIRECT_MENU;

		} catch (Exception e) {
			// ログイン情報の破棄
			httpSession.invalidate();
			e.printStackTrace();
			model.addAttribute(PageReturnAttributeKeyword.MESSAGE_ERROR, "予期せぬエラーが発生しました。");
			return REDIRECT_MENU;
		}
	}
}
