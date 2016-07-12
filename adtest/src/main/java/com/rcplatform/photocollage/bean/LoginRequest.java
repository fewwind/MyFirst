package com.rcplatform.photocollage.bean;

/**
 * Created by admin on 2016/7/5.
 */
public class LoginRequest {

    /**
     * timestamp : 1467691202435
     * password : abc123
     * sign : 13714deh610g147813gg13_12eg13df_5dg__d14g14d14106_77
     * username : 13175080425
     * clientInfo : {"appVersion":"1.4.1","channelId":"306255","deviceId":"00000000-3228-e0a5-b930-27ab0033c587","hardwareModel":"A0001","os":"android","systemVersion":"6.0.1","versionCode":"10"}
     */

    private String timestamp;
    private String password;
    private String sign;
    private String username;
    /**
     * appVersion : 1.4.1
     * channelId : 306255
     * deviceId : 00000000-3228-e0a5-b930-27ab0033c587
     * hardwareModel : A0001
     * os : android
     * systemVersion : 6.0.1
     * versionCode : 10
     */

    private ClientInfoBean clientInfo;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ClientInfoBean getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfoBean clientInfo) {
        this.clientInfo = clientInfo;
    }

    public static class ClientInfoBean {
        private String appVersion;
        private String channelId;
        private String deviceId;
        private String hardwareModel;
        private String os;
        private String systemVersion;
        private String versionCode;

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getHardwareModel() {
            return hardwareModel;
        }

        public void setHardwareModel(String hardwareModel) {
            this.hardwareModel = hardwareModel;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }
    }
}
