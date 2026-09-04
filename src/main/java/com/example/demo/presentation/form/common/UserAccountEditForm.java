package com.example.demo.presentation.form.common;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.demo.infra.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ユーザー情報更新画面のフォームクラスです。
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountEditForm {
private Integer userId;
private Byte userType;
@NotBlank
private String userName;
@NotBlank
@Email
private String email;
@NotBlank
@Size(min=6)
private String password;
@NotBlank
private String passwordConfirm;

private Byte isDeleted = 0;


public static UserEntity convertTo(UserAccountEditForm userAccountEditForm) {
     return new UserEntity(

    		 userAccountEditForm.getUserId(),
    		 userAccountEditForm.getUserType(),
    		 userAccountEditForm.getUserName(),
    		 userAccountEditForm.getEmail(),
    		 userAccountEditForm.getPassword(),
    		 null,
    		 null,
    		 userAccountEditForm.getIsDeleted()
    		 );
}

/**
 * パスワードの仕様チェック
 * パスワードが仕様を満たしてないか確認
 *
 * @param password        更新対象のパスワード
 * @param passwordConfirm 更新対象の確認用パスワード
 * @return 空文字: エラー無し　それ以外:エラー内容
 */
public static String validatePassword(String password, String passwordConfirm) {

    final String ERROR = "パスワードが一致していません";

    if (areBothBlank(password, passwordConfirm)) {
        // どちらもnullまたは空白の場合はOK
        return "";
    }

    // どちらかがnullまたは空白の場合のエラーハンドリング
    if (isEitherBlank(password, passwordConfirm)) {
        return ERROR;
    }

    // 値が異なった場合はエラーハンドリング
    if (!password.equals(passwordConfirm)) {
        return ERROR;
    }
    return "";

}

/**
 * 仮引数を null or 空 判定する
 *
 * @param str 判定対象の文字列
 * @return true:null or 空 false:null or 空 では無い
 */
private static boolean isNullOrBlank(String str) {
    if (null == str) {
        return true;
    }
    return str.isBlank();
}

/**
 * パスワードが今回更新対象に含まれているかチェック
 *
 * @param password        更新対象のパスワード
 * @param passwordConfirm 更新対象の確認用パスワード
 * @return true:どちらもnullまたは空白の場合 false:それ以外
 */
private static boolean areBothBlank(String password, String passwordConfirm) {
    return (password == null || password.isBlank()) && (passwordConfirm == null || passwordConfirm.isBlank());
}

/**
 * パスワードが今回更新対象に含まれているかチェック　その2
 *
 * @param password        更新対象のパスワード
 * @param passwordConfirm 更新対象の確認用パスワード
 * @return true: 問題なし false:どちらかがnullまたは空白の場合
 */
private static boolean isEitherBlank(String password, String passwordConfirm) {
    return password == null || password.isBlank() || passwordConfirm == null || passwordConfirm.isBlank();
}
}




