package com.ziroom.zrp.service.trading.pojo;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangxm113
 * @version 1.0
 * @Date Created in 2017年07月31日 13:54
 * @since 1.0
 */
public class CustomerInfoOfCRM {
    private String error_code;
    private String error_message;
    private String status;
    private DataBean data;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private RentContractBean rentContract;
        private ContractCustomerBean contractCustomer;

        public RentContractBean getRentContract() {
            return rentContract;
        }

        public void setRentContract(RentContractBean rentContract) {
            this.rentContract = rentContract;
        }

        public ContractCustomerBean getContractCustomer() {
            return contractCustomer;
        }

        public void setContractCustomer(ContractCustomerBean contractCustomer) {
            this.contractCustomer = contractCustomer;
        }

        public static class RentContractBean {
            private String id;
            private String houseId;
            private String houseCode;
            private String houseSourceCode;
            private String roomId;
            private String uid;
            private String contractCode;
            private int houseType;
            private String signDate;
            private String startDate;
            private String stopDate;
            private String contractState;
            private String propertyState;
            private String monthRentPrice;
            private String dayRentPrice;
            private String deposit;
            private String originCommission;
            private String commission;
            private int paymentCycle;
            private String tenancyType;
            private int cycle;
            private String cityCode;
            private int contractStrategy;
            private boolean isShort;
            private boolean isRentback;
            private boolean isChangeSign;
            private boolean isBlank;
            private boolean isZwhite;
            private int reserveDeposit;
            private String createTime;
            private String lastModifyTime;
            private int isDel;
            private boolean isResign;
            private int isKeyAccount;
            private int isMortgaged;
            private int oldIsDeposit;
            private int isJointRent;
            private String countMoney;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHouseId() {
                return houseId;
            }

            public void setHouseId(String houseId) {
                this.houseId = houseId;
            }

            public String getHouseCode() {
                return houseCode;
            }

            public void setHouseCode(String houseCode) {
                this.houseCode = houseCode;
            }

            public String getHouseSourceCode() {
                return houseSourceCode;
            }

            public void setHouseSourceCode(String houseSourceCode) {
                this.houseSourceCode = houseSourceCode;
            }

            public String getRoomId() {
                return roomId;
            }

            public void setRoomId(String roomId) {
                this.roomId = roomId;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getContractCode() {
                return contractCode;
            }

            public void setContractCode(String contractCode) {
                this.contractCode = contractCode;
            }

            public int getHouseType() {
                return houseType;
            }

            public void setHouseType(int houseType) {
                this.houseType = houseType;
            }

            public String getSignDate() {
                return signDate;
            }

            public void setSignDate(String signDate) {
                this.signDate = signDate;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getStopDate() {
                return stopDate;
            }

            public void setStopDate(String stopDate) {
                this.stopDate = stopDate;
            }

            public String getContractState() {
                return contractState;
            }

            public void setContractState(String contractState) {
                this.contractState = contractState;
            }

            public String getPropertyState() {
                return propertyState;
            }

            public void setPropertyState(String propertyState) {
                this.propertyState = propertyState;
            }

            public String getMonthRentPrice() {
                return monthRentPrice;
            }

            public void setMonthRentPrice(String monthRentPrice) {
                this.monthRentPrice = monthRentPrice;
            }

            public String getDayRentPrice() {
                return dayRentPrice;
            }

            public void setDayRentPrice(String dayRentPrice) {
                this.dayRentPrice = dayRentPrice;
            }

            public String getDeposit() {
                return deposit;
            }

            public void setDeposit(String deposit) {
                this.deposit = deposit;
            }

            public String getOriginCommission() {
                return originCommission;
            }

            public void setOriginCommission(String originCommission) {
                this.originCommission = originCommission;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public int getPaymentCycle() {
                return paymentCycle;
            }

            public void setPaymentCycle(int paymentCycle) {
                this.paymentCycle = paymentCycle;
            }

            public String getTenancyType() {
                return tenancyType;
            }

            public void setTenancyType(String tenancyType) {
                this.tenancyType = tenancyType;
            }

            public int getCycle() {
                return cycle;
            }

            public void setCycle(int cycle) {
                this.cycle = cycle;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public int getContractStrategy() {
                return contractStrategy;
            }

            public void setContractStrategy(int contractStrategy) {
                this.contractStrategy = contractStrategy;
            }

            public boolean isIsShort() {
                return isShort;
            }

            public void setIsShort(boolean isShort) {
                this.isShort = isShort;
            }

            public boolean isIsRentback() {
                return isRentback;
            }

            public void setIsRentback(boolean isRentback) {
                this.isRentback = isRentback;
            }

            public boolean isIsChangeSign() {
                return isChangeSign;
            }

            public void setIsChangeSign(boolean isChangeSign) {
                this.isChangeSign = isChangeSign;
            }

            public boolean isIsBlank() {
                return isBlank;
            }

            public void setIsBlank(boolean isBlank) {
                this.isBlank = isBlank;
            }

            public boolean isIsZwhite() {
                return isZwhite;
            }

            public void setIsZwhite(boolean isZwhite) {
                this.isZwhite = isZwhite;
            }

            public int getReserveDeposit() {
                return reserveDeposit;
            }

            public void setReserveDeposit(int reserveDeposit) {
                this.reserveDeposit = reserveDeposit;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getLastModifyTime() {
                return lastModifyTime;
            }

            public void setLastModifyTime(String lastModifyTime) {
                this.lastModifyTime = lastModifyTime;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }

            public boolean isIsResign() {
                return isResign;
            }

            public void setIsResign(boolean isResign) {
                this.isResign = isResign;
            }

            public int getIsKeyAccount() {
                return isKeyAccount;
            }

            public void setIsKeyAccount(int isKeyAccount) {
                this.isKeyAccount = isKeyAccount;
            }

            public int getIsMortgaged() {
                return isMortgaged;
            }

            public void setIsMortgaged(int isMortgaged) {
                this.isMortgaged = isMortgaged;
            }

            public int getOldIsDeposit() {
                return oldIsDeposit;
            }

            public void setOldIsDeposit(int oldIsDeposit) {
                this.oldIsDeposit = oldIsDeposit;
            }

            public int getIsJointRent() {
                return isJointRent;
            }

            public void setIsJointRent(int isJointRent) {
                this.isJointRent = isJointRent;
            }

            public String getCountMoney() {
                return countMoney;
            }

            public void setCountMoney(String countMoney) {
                this.countMoney = countMoney;
            }
        }

        public static class ContractCustomerBean {
            private String id;
            private String contractCode;
            private String uid;
            private String userName;
            private int userSex;
            private String userSexName;
            private String userPhone;
            private int certType;
            private String certTypeName;
            private String certNum;
            private String userCert1;
            private String userCert2;
            private String userCert3;
            private String workName;
            private String workAddress;
            private String urgencyName;
            private String urgencyPhone;
            private String urgencyRelation;
            private int userIsLock;
            private int certIsLock;
            private String addTime;
            private String modifiedTime;
            private JointrentBean jointrent;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContractCode() {
                return contractCode;
            }

            public void setContractCode(String contractCode) {
                this.contractCode = contractCode;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserSex() {
                return userSex;
            }

            public void setUserSex(int userSex) {
                this.userSex = userSex;
            }

            public String getUserSexName() {
                return userSexName;
            }

            public void setUserSexName(String userSexName) {
                this.userSexName = userSexName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getCertType() {
                return certType;
            }

            public void setCertType(int certType) {
                this.certType = certType;
            }

            public String getCertTypeName() {
                return certTypeName;
            }

            public void setCertTypeName(String certTypeName) {
                this.certTypeName = certTypeName;
            }

            public String getCertNum() {
                return certNum;
            }

            public void setCertNum(String certNum) {
                this.certNum = certNum;
            }

            public String getUserCert1() {
                return userCert1;
            }

            public void setUserCert1(String userCert1) {
                this.userCert1 = userCert1;
            }

            public String getUserCert2() {
                return userCert2;
            }

            public void setUserCert2(String userCert2) {
                this.userCert2 = userCert2;
            }

            public String getUserCert3() {
                return userCert3;
            }

            public void setUserCert3(String userCert3) {
                this.userCert3 = userCert3;
            }

            public String getWorkName() {
                return workName;
            }

            public void setWorkName(String workName) {
                this.workName = workName;
            }

            public String getWorkAddress() {
                return workAddress;
            }

            public void setWorkAddress(String workAddress) {
                this.workAddress = workAddress;
            }

            public String getUrgencyName() {
                return urgencyName;
            }

            public void setUrgencyName(String urgencyName) {
                this.urgencyName = urgencyName;
            }

            public String getUrgencyPhone() {
                return urgencyPhone;
            }

            public void setUrgencyPhone(String urgencyPhone) {
                this.urgencyPhone = urgencyPhone;
            }

            public String getUrgencyRelation() {
                return urgencyRelation;
            }

            public void setUrgencyRelation(String urgencyRelation) {
                this.urgencyRelation = urgencyRelation;
            }

            public int getUserIsLock() {
                return userIsLock;
            }

            public void setUserIsLock(int userIsLock) {
                this.userIsLock = userIsLock;
            }

            public int getCertIsLock() {
                return certIsLock;
            }

            public void setCertIsLock(int certIsLock) {
                this.certIsLock = certIsLock;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getModifiedTime() {
                return modifiedTime;
            }

            public void setModifiedTime(String modifiedTime) {
                this.modifiedTime = modifiedTime;
            }

            public JointrentBean getJointrent() {
                return jointrent;
            }

            public void setJointrent(JointrentBean jointrent) {
                this.jointrent = jointrent;
            }

            public static class JointrentBean {
                private long id;
                private String contractCode;
                private String jointRentName;
                private int jointRentSex;
                private String jointRentPhone;
                private int jointRentCertType;
                private String jointRentCertNum;
                private String jointRentCert1;
                private String jointRentCert2;
                private boolean userIsLock;
                private boolean certIsLock;
                private String addTime;
                private String modifiedTime;
                private boolean isToStorage;
                private String jointRentSexName;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getContractCode() {
                    return contractCode;
                }

                public void setContractCode(String contractCode) {
                    this.contractCode = contractCode;
                }

                public String getJointRentName() {
                    return jointRentName;
                }

                public void setJointRentName(String jointRentName) {
                    this.jointRentName = jointRentName;
                }

                public int getJointRentSex() {
                    return jointRentSex;
                }

                public void setJointRentSex(int jointRentSex) {
                    this.jointRentSex = jointRentSex;
                }

                public String getJointRentPhone() {
                    return jointRentPhone;
                }

                public void setJointRentPhone(String jointRentPhone) {
                    this.jointRentPhone = jointRentPhone;
                }

                public int getJointRentCertType() {
                    return jointRentCertType;
                }

                public void setJointRentCertType(int jointRentCertType) {
                    this.jointRentCertType = jointRentCertType;
                }

                public String getJointRentCertNum() {
                    return jointRentCertNum;
                }

                public void setJointRentCertNum(String jointRentCertNum) {
                    this.jointRentCertNum = jointRentCertNum;
                }

                public String getJointRentCert1() {
                    return jointRentCert1;
                }

                public void setJointRentCert1(String jointRentCert1) {
                    this.jointRentCert1 = jointRentCert1;
                }

                public String getJointRentCert2() {
                    return jointRentCert2;
                }

                public void setJointRentCert2(String jointRentCert2) {
                    this.jointRentCert2 = jointRentCert2;
                }

                public boolean isUserIsLock() {
                    return userIsLock;
                }

                public void setUserIsLock(boolean userIsLock) {
                    this.userIsLock = userIsLock;
                }

                public boolean isCertIsLock() {
                    return certIsLock;
                }

                public void setCertIsLock(boolean certIsLock) {
                    this.certIsLock = certIsLock;
                }

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }

                public String getModifiedTime() {
                    return modifiedTime;
                }

                public void setModifiedTime(String modifiedTime) {
                    this.modifiedTime = modifiedTime;
                }

                public boolean isIsToStorage() {
                    return isToStorage;
                }

                public void setIsToStorage(boolean isToStorage) {
                    this.isToStorage = isToStorage;
                }

                public String getJointRentSexName() {
                    return jointRentSexName;
                }

                public void setJointRentSexName(String jointRentSexName) {
                    this.jointRentSexName = jointRentSexName;
                }
            }
        }
    }
}
