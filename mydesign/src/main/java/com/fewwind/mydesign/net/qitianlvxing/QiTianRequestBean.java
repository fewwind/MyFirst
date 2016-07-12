package com.fewwind.mydesign.net.qitianlvxing;

/**
 * Created by admin on 2016/7/5.
 */
public class QiTianRequestBean {

    /**
     * token : fhNaUowi91bB/rRBVS1NB9FkqEm6TbfbN77zINM3gcuOFyF3UzlsFp3cLKq2W4oq
     */

    private AuthorityBean authority;
    /**
     * authority : {"token":"fhNaUowi91bB/rRBVS1NB9FkqEm6TbfbN77zINM3gcuOFyF3UzlsFp3cLKq2W4oq"}
     * type : 0
     */

    private int type;

    public AuthorityBean getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityBean authority) {
        this.authority = authority;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class AuthorityBean {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
