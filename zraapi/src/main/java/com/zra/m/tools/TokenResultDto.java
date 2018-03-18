package com.zra.m.tools;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/14.
 */
public class TokenResultDto {

    /**
     * code : 20000
     * message : success
     * resp : {"id":1,"uid":"uiduid","phone":"13800000000","email":"dp@ziroom.com","username":"ziroom","lastOnlineTime":1460369234,"status":1,"phoneChecked":1,"emailChecked":0,"createTime":1460369234,"hasPassword":true,"threeBindings":[{"id":1,"openId":"openIdopenId","type":1,"createTime":1460369234}]}
     */

    private String code;
    private String message;
    private RespBean resp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RespBean getResp() {
        return resp;
    }

    public void setResp(RespBean resp) {
        this.resp = resp;
    }

    public static class RespBean {
        /**
         * id : 1
         * uid : uiduid
         * phone : 13800000000
         * email : dp@ziroom.com
         * username : ziroom
         * lastOnlineTime : 1460369234
         * status : 1
         * phoneChecked : 1
         * emailChecked : 0
         * createTime : 1460369234
         * hasPassword : true
         * threeBindings : [{"id":1,"openId":"openIdopenId","type":1,"createTime":1460369234}]
         */

        private long id;
        private String uid;
        private String phone;
        private String email;
        private String username;
        private long lastOnlineTime;
        private long status;
        private long phoneChecked;
        private long emailChecked;
        private long createTime;
        private boolean hasPassword;
        private List<ThreeBindingsBean> threeBindings;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public long getLastOnlineTime() {
            return lastOnlineTime;
        }

        public void setLastOnlineTime(long lastOnlineTime) {
            this.lastOnlineTime = lastOnlineTime;
        }

        public long getStatus() {
            return status;
        }

        public void setStatus(long status) {
            this.status = status;
        }

        public long getPhoneChecked() {
            return phoneChecked;
        }

        public void setPhoneChecked(long phoneChecked) {
            this.phoneChecked = phoneChecked;
        }

        public long getEmailChecked() {
            return emailChecked;
        }

        public void setEmailChecked(long emailChecked) {
            this.emailChecked = emailChecked;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isHasPassword() {
            return hasPassword;
        }

        public void setHasPassword(boolean hasPassword) {
            this.hasPassword = hasPassword;
        }

        public List<ThreeBindingsBean> getThreeBindings() {
            return threeBindings;
        }

        public void setThreeBindings(List<ThreeBindingsBean> threeBindings) {
            this.threeBindings = threeBindings;
        }

        public static class ThreeBindingsBean {
            /**
             * id : 1
             * openId : openIdopenId
             * type : 1
             * createTime : 1460369234
             */

            private long id;
            private String openId;
            private long type;
            private long createTime;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public long getType() {
                return type;
            }

            public void setType(long type) {
                this.type = type;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
