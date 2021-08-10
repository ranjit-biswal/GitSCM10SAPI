package apiCore.Request.Registration;

public class RegistrationAPI {
	private String accountNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String dateOfBirth;
	private String mobileNumber;
	private String homePhone;
	private String address1;
	private String address2;
	private String isPasswordRequest;
	private String cityId;
	private String postalCode;
	private String userName;
	private String password;
	private String confirmPassword;
	private String securityQuestionId;
	private String hintAns;
	private String meterNumber;
	private String emailNotify;
	private String budgetNotify;
	private String billingAddress;
	private String serviceAccount;
	private String customerUtilityId;
	private String addressPowerPlanID;
	private String evPowerPlanId;
	private String defaultUsageView;
	private String defaultPaymentType;
	private String utilityId;
	private String latitude;
	private String longitude;
	private String securityQuestionId2;
	private String hintsAns2;
	private String ssnNumber;
	private String utilityAccountNumber;
	private String isVerification;
	private String sessionCode;
	private String alternateEmailID;
	private String bpNumber;
	private String streetNumber;
	private String drivingLicence;
	private String ipAddress;
	private String isCSRUser;
	private String languageCode;
	private String customerNo;
	private String customerType;
	private String externalLoginId;
	private String loginMode;
	private String contactType;
	private String notificationPreference;
	private String paperlessBill;
	private String osType;

	public RegistrationAPI(String accountNum, String firstName, String middleName,
						   String lastName, String emailId, String dateOfBirth,
						   String mobileNum, String homePhone, String address1,
						   String address2, String isPwdRequest, String cityId,
						   String postalCode, String userName, String password,
						   String confirmPassword, String securityQuestionId,
						   String hintAns, String meterNum, String emailNotify,
						   String budgetNotify, String billingAdd, String serviceAcc,
						   String customerUtilityId, String addPowerPlanId,
						   String evPowerPlanId, String defaultUsageView,
						   String defaultPaymentType, String utilityId, String latitude,
						   String longitude, String securityQuestionId2, String hintsAns2,
						   String ssnNumber, String utilityAccNum, String isVerification,
						   String sessionCode, String alternateEmailId, String bpNumber,
						   String streetNumber, String drivingLicence, String ipAddress,
						   String isCSRUser, String languageCode, String customerNo,
						   String customerType, String externalLoginId, String loginMode,
						   String contactType, String notificationPreference, String paperlessBill,
						   String osType) {
		this.accountNumber = accountNum;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
		this.mobileNumber = mobileNum;
		this.homePhone = homePhone;
		this.address1 = address1;
		this.address2 = address2;
		this.isPasswordRequest = isPwdRequest;
		this.cityId = cityId;
		this.postalCode = postalCode;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.securityQuestionId = securityQuestionId;
		this.hintAns = hintAns;
		this.meterNumber = meterNum;
		this.emailNotify = emailNotify;
		this.budgetNotify = budgetNotify;
		this.billingAddress = billingAdd;
		this.serviceAccount = serviceAcc;
		this.customerUtilityId = customerUtilityId;
		this.addressPowerPlanID = addPowerPlanId;
		this.evPowerPlanId = evPowerPlanId;
		this.defaultUsageView = defaultUsageView;
		this.defaultPaymentType = defaultPaymentType;
		this.utilityId = utilityId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.securityQuestionId2 = securityQuestionId2;
		this.hintsAns2 = hintsAns2;
		this.ssnNumber = ssnNumber;
		this.utilityAccountNumber = utilityAccNum;
		this.isVerification = isVerification;
		this.sessionCode = sessionCode;
		this.alternateEmailID = alternateEmailId;
		this.bpNumber = bpNumber;
		this.streetNumber = streetNumber;
		this.drivingLicence = drivingLicence;
		this.ipAddress = ipAddress;
		this.isCSRUser = isCSRUser;
		this.languageCode = languageCode;
		this.customerNo = customerNo;
		this.customerType = customerType;
		this.externalLoginId = externalLoginId;
		this.loginMode = loginMode;
		this.contactType = contactType;
		this.notificationPreference = notificationPreference;
		this.paperlessBill = paperlessBill;
		this.osType = osType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getIsPasswordRequest() {
		return isPasswordRequest;
	}

	public void setIsPasswordRequest(String isPasswordRequest) {
		this.isPasswordRequest = isPasswordRequest;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getSecurityQuestionId() {
		return securityQuestionId;
	}

	public void setSecurityQuestionId(String securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}

	public String getHintAns() {
		return hintAns;
	}

	public void setHintAns(String hintAns) {
		this.hintAns = hintAns;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getEmailNotify() {
		return emailNotify;
	}

	public void setEmailNotify(String emailNotify) {
		this.emailNotify = emailNotify;
	}

	public String getBudgetNotify() {
		return budgetNotify;
	}

	public void setBudgetNotify(String budgetNotify) {
		this.budgetNotify = budgetNotify;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}

	public String getCustomerUtilityId() {
		return customerUtilityId;
	}

	public void setCustomerUtilityId(String customerUtilityId) {
		this.customerUtilityId = customerUtilityId;
	}

	public String getAddressPowerPlanID() {
		return addressPowerPlanID;
	}

	public void setAddressPowerPlanID(String addressPowerPlanID) {
		this.addressPowerPlanID = addressPowerPlanID;
	}

	public String getEVPowerPlanID() {
		return evPowerPlanId;
	}

	public void setEVPowerPlanID(String eVPowerPlanID) {
		evPowerPlanId = eVPowerPlanID;
	}

	public String getDefaultUsageView() {
		return defaultUsageView;
	}

	public void setDefaultUsageView(String defaultUsageView) {
		this.defaultUsageView = defaultUsageView;
	}

	public String getDefaultPaymentType() {
		return defaultPaymentType;
	}

	public void setDefaultPaymentType(String defaultPaymentType) {
		this.defaultPaymentType = defaultPaymentType;
	}

	public String getUtilityId() {
		return utilityId;
	}

	public void setUtilityId(String utilityId) {
		this.utilityId = utilityId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSecurityQuestionId2() {
		return securityQuestionId2;
	}

	public void setSecurityQuestionId2(String securityQuestionId2) {
		this.securityQuestionId2 = securityQuestionId2;
	}

	public String getHintsAns2() {
		return hintsAns2;
	}

	public void setHintsAns2(String hintsAns2) {
		this.hintsAns2 = hintsAns2;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String sSNNumber) {
		ssnNumber = sSNNumber;
	}

	public String getUtilityAccountNumber() {
		return utilityAccountNumber;
	}

	public void setUtilityAccountNumber(String utilityAccountNumber) {
		this.utilityAccountNumber = utilityAccountNumber;
	}

	public String getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(String isVerification) {
		this.isVerification = isVerification;
	}

	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getAlternateEmailID() {
		return alternateEmailID;
	}

	public void setAlternateEmailID(String alternateEmailID) {
		this.alternateEmailID = alternateEmailID;
	}

	public String getBpNumber() {
		return bpNumber;
	}

	public void setBpNumber(String bPNumber) {
		bpNumber = bPNumber;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String iPAddress) {
		ipAddress = iPAddress;
	}

	public String getIsCSRUser() {
		return isCSRUser;
	}

	public void setIsCSRUser(String isCSRUser) {
		this.isCSRUser = isCSRUser;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getExternalLoginId() {
		return externalLoginId;
	}

	public void setExternalLoginId(String externalLoginId) {
		this.externalLoginId = externalLoginId;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getNotificationPreference() {
		return notificationPreference;
	}

	public void setNotificationPreference(String notificationPreference) {
		this.notificationPreference = notificationPreference;
	}

	public String getPaperlessBill() {
		return paperlessBill;
	}

	public void setPaperlessBill(String paperlessBill) {
		this.paperlessBill = paperlessBill;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String oSType) {
		osType = oSType;
	}

}
