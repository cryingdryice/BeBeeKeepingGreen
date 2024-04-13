package com.mysite.BeBeeKeepingGreen.user;

import lombok.Getter;
import lombok.val;

// 관리자와 사용자 구분
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USESR");

    UserRole(String value){
        this.value = value;
    }

    private String value;
}
