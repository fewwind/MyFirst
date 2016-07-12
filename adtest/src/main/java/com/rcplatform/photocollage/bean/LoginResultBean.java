package com.rcplatform.photocollage.bean;

/**
 * Created by admin on 2016/7/5.
 */
public class LoginResultBean {

    /**
     * msgCode : 100
     * message : 请求成功
     * result : {"virtual":0,"isNewMember":0,"isFirst":0,"token":"pZM7TmIl4E0F+rcul4/87CW6CWzs0QTYs+ltTMG+pgjuMsklw2MyZ0cMc0UfBisB","member":{"memberId":187068480,"memberName":"注册会员","email":null,"mobile":"13175080425","isMale":null,"birthday":1467691295000,"memberType":1,"propertyID":661111152,"propertyType":40,"payTrain":false,"payTrainProID":0,"memberTypeName":"普卡会员","idCardRegister":false,"validateMobile":true,"goldLevel":0,"discountStr":"房费优惠8.5折起","bindWeixin":false,"bindQQ":false,"hasOpenTrustCheckIn":false,"bankName":null,"cardLastFourNumber":null,"auditing":11,"nickname":null,"headimgurl":null,"idCard":null,"cardNo":"13175080425","updateIDCard":2,"updateMemberName":2,"registMebChainId":"","lastCheckInChainId":""}}
     */

    private int msgCode;
    private String message;
    /**
     * virtual : 0
     * isNewMember : 0
     * isFirst : 0
     * token : pZM7TmIl4E0F+rcul4/87CW6CWzs0QTYs+ltTMG+pgjuMsklw2MyZ0cMc0UfBisB
     * member : {"memberId":187068480,"memberName":"注册会员","email":null,"mobile":"13175080425","isMale":null,"birthday":1467691295000,"memberType":1,"propertyID":661111152,"propertyType":40,"payTrain":false,"payTrainProID":0,"memberTypeName":"普卡会员","idCardRegister":false,"validateMobile":true,"goldLevel":0,"discountStr":"房费优惠8.5折起","bindWeixin":false,"bindQQ":false,"hasOpenTrustCheckIn":false,"bankName":null,"cardLastFourNumber":null,"auditing":11,"nickname":null,"headimgurl":null,"idCard":null,"cardNo":"13175080425","updateIDCard":2,"updateMemberName":2,"registMebChainId":"","lastCheckInChainId":""}
     */

    private ResultBean result;

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private int virtual;
        private int isNewMember;
        private int isFirst;
        private String token;
        /**
         * memberId : 187068480
         * memberName : 注册会员
         * email : null
         * mobile : 13175080425
         * isMale : null
         * birthday : 1467691295000
         * memberType : 1
         * propertyID : 661111152
         * propertyType : 40
         * payTrain : false
         * payTrainProID : 0
         * memberTypeName : 普卡会员
         * idCardRegister : false
         * validateMobile : true
         * goldLevel : 0
         * discountStr : 房费优惠8.5折起
         * bindWeixin : false
         * bindQQ : false
         * hasOpenTrustCheckIn : false
         * bankName : null
         * cardLastFourNumber : null
         * auditing : 11
         * nickname : null
         * headimgurl : null
         * idCard : null
         * cardNo : 13175080425
         * updateIDCard : 2
         * updateMemberName : 2
         * registMebChainId :
         * lastCheckInChainId :
         */

        private MemberBean member;

        public int getVirtual() {
            return virtual;
        }

        public void setVirtual(int virtual) {
            this.virtual = virtual;
        }

        public int getIsNewMember() {
            return isNewMember;
        }

        public void setIsNewMember(int isNewMember) {
            this.isNewMember = isNewMember;
        }

        public int getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(int isFirst) {
            this.isFirst = isFirst;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class MemberBean {
            private int memberId;
            private String memberName;
            private Object email;
            private String mobile;
            private Object isMale;
            private long birthday;
            private int memberType;
            private int propertyID;
            private int propertyType;
            private boolean payTrain;
            private int payTrainProID;
            private String memberTypeName;
            private boolean idCardRegister;
            private boolean validateMobile;
            private int goldLevel;
            private String discountStr;
            private boolean bindWeixin;
            private boolean bindQQ;
            private boolean hasOpenTrustCheckIn;
            private Object bankName;
            private Object cardLastFourNumber;
            private int auditing;
            private Object nickname;
            private Object headimgurl;
            private Object idCard;
            private String cardNo;
            private int updateIDCard;
            private int updateMemberName;
            private String registMebChainId;
            private String lastCheckInChainId;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getIsMale() {
                return isMale;
            }

            public void setIsMale(Object isMale) {
                this.isMale = isMale;
            }

            public long getBirthday() {
                return birthday;
            }

            public void setBirthday(long birthday) {
                this.birthday = birthday;
            }

            public int getMemberType() {
                return memberType;
            }

            public void setMemberType(int memberType) {
                this.memberType = memberType;
            }

            public int getPropertyID() {
                return propertyID;
            }

            public void setPropertyID(int propertyID) {
                this.propertyID = propertyID;
            }

            public int getPropertyType() {
                return propertyType;
            }

            public void setPropertyType(int propertyType) {
                this.propertyType = propertyType;
            }

            public boolean isPayTrain() {
                return payTrain;
            }

            public void setPayTrain(boolean payTrain) {
                this.payTrain = payTrain;
            }

            public int getPayTrainProID() {
                return payTrainProID;
            }

            public void setPayTrainProID(int payTrainProID) {
                this.payTrainProID = payTrainProID;
            }

            public String getMemberTypeName() {
                return memberTypeName;
            }

            public void setMemberTypeName(String memberTypeName) {
                this.memberTypeName = memberTypeName;
            }

            public boolean isIdCardRegister() {
                return idCardRegister;
            }

            public void setIdCardRegister(boolean idCardRegister) {
                this.idCardRegister = idCardRegister;
            }

            public boolean isValidateMobile() {
                return validateMobile;
            }

            public void setValidateMobile(boolean validateMobile) {
                this.validateMobile = validateMobile;
            }

            public int getGoldLevel() {
                return goldLevel;
            }

            public void setGoldLevel(int goldLevel) {
                this.goldLevel = goldLevel;
            }

            public String getDiscountStr() {
                return discountStr;
            }

            public void setDiscountStr(String discountStr) {
                this.discountStr = discountStr;
            }

            public boolean isBindWeixin() {
                return bindWeixin;
            }

            public void setBindWeixin(boolean bindWeixin) {
                this.bindWeixin = bindWeixin;
            }

            public boolean isBindQQ() {
                return bindQQ;
            }

            public void setBindQQ(boolean bindQQ) {
                this.bindQQ = bindQQ;
            }

            public boolean isHasOpenTrustCheckIn() {
                return hasOpenTrustCheckIn;
            }

            public void setHasOpenTrustCheckIn(boolean hasOpenTrustCheckIn) {
                this.hasOpenTrustCheckIn = hasOpenTrustCheckIn;
            }

            public Object getBankName() {
                return bankName;
            }

            public void setBankName(Object bankName) {
                this.bankName = bankName;
            }

            public Object getCardLastFourNumber() {
                return cardLastFourNumber;
            }

            public void setCardLastFourNumber(Object cardLastFourNumber) {
                this.cardLastFourNumber = cardLastFourNumber;
            }

            public int getAuditing() {
                return auditing;
            }

            public void setAuditing(int auditing) {
                this.auditing = auditing;
            }

            public Object getNickname() {
                return nickname;
            }

            public void setNickname(Object nickname) {
                this.nickname = nickname;
            }

            public Object getHeadimgurl() {
                return headimgurl;
            }

            public void setHeadimgurl(Object headimgurl) {
                this.headimgurl = headimgurl;
            }

            public Object getIdCard() {
                return idCard;
            }

            public void setIdCard(Object idCard) {
                this.idCard = idCard;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public int getUpdateIDCard() {
                return updateIDCard;
            }

            public void setUpdateIDCard(int updateIDCard) {
                this.updateIDCard = updateIDCard;
            }

            public int getUpdateMemberName() {
                return updateMemberName;
            }

            public void setUpdateMemberName(int updateMemberName) {
                this.updateMemberName = updateMemberName;
            }

            public String getRegistMebChainId() {
                return registMebChainId;
            }

            public void setRegistMebChainId(String registMebChainId) {
                this.registMebChainId = registMebChainId;
            }

            public String getLastCheckInChainId() {
                return lastCheckInChainId;
            }

            public void setLastCheckInChainId(String lastCheckInChainId) {
                this.lastCheckInChainId = lastCheckInChainId;
            }
        }
    }
}
