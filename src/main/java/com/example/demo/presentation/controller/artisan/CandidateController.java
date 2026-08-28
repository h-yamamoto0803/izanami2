package com.example.demo.presentation.controller.artisan;

import static com.example.demo.presentation.controller.pageproperty.TransitionTargetPageNameKeyword.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.aspect.PermissionCheck;
import com.example.demo.domain.service.artisan.Candidate;
import com.example.demo.dto.CandidateResponseDto;
import com.example.demo.presentation.controller.pageproperty.SessionKeyword;
import com.example.demo.presentation.form.LoginUserForm;
import com.example.demo.presentation.form.artisan.CandidateForm;

@RestController
public class CandidateController {

	Candidate candidate;

	@Autowired
	CandidateController(Candidate candidate) {
		this.candidate = candidate;
	}

	/**
	 * 検討が押された際に呼び出され、処理を行うメソッドを呼び出し結果をjsonで返すコントローラ
	 * PostMapping等関連する範囲の完成以降修正予定
	 * @param form
	 * @param session
	 * @return 対象投稿のID、 追加処理だったか否か、変更後の検討数を持つDTO
	 */
	@PermissionCheck
	@PostMapping(CANDIDATE)
	public CandidateResponseDto candidate(
			CandidateForm form,
			HttpSession session) {
		LoginUserForm loginUserForm = (LoginUserForm) session.getAttribute(SessionKeyword.LOGIN_USER);

		CandidateResponseDto response = candidate.switchCandidate(loginUserForm, form);

		return response;

	}

}
