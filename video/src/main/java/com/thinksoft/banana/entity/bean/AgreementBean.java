package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class AgreementBean extends BaseBean {

    /**
     * userAgreement : {"title":"提现阶梯","content":""}
     */

    private UserAgreementBean userAgreement;

    public UserAgreementBean getUserAgreement() {
        return userAgreement;
    }

    public void setUserAgreement(UserAgreementBean userAgreement) {
        this.userAgreement = userAgreement;
    }

    public static class UserAgreementBean {
        /**
         * title : 提现阶梯
         * content :
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
