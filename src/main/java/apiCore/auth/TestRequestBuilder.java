package apiCore.auth;

import apiCore.Request.Login.LoginHelpPayload;
import apiCore.Request.Login.LoginPayload;
import apiCore.Request.Payments.OneTimePaymentBank;
import apiCore.Request.Payments.OneTimePaymentCreditCard;
import apiCore.Request.Payments.PaymentProfile;
import apiCore.Request.Registration.RegistrationAPI;
import resources.Filepaths;
import utils.JsonUtil;

public class TestRequestBuilder {
	String fileBank = "OTPBank.json";
	String fileLogin = "Login.json";
	String fileLoginHelp = "LoginHelp.json";
	String fileOneTimePaymentCC = "oneTimePaymentCC.json";
	String filePaymentProfileCC = "createProfileCC.json";
	String fileRegistration = "registrationPayload.json";
	public PaymentProfile cardPaymentProfile;

	JsonUtil jsonUtilBank = new JsonUtil(Filepaths.testDataSCPJsonFilepath, fileBank);
	JsonUtil jsonUtilLogin = new JsonUtil(Filepaths.testDataSCPJsonFilepath, fileLogin);
	JsonUtil jsonUtilLoginHelp = new JsonUtil(Filepaths.testDataSCPJsonFilepath,
			fileLoginHelp);
	JsonUtil jsonUtilOTPCC = new JsonUtil(Filepaths.testDataSCPJsonFilepath,
			fileOneTimePaymentCC);
	JsonUtil jsonUtilPaymentProfileCC = new JsonUtil(Filepaths.testDataSCPJsonFilepath,
			filePaymentProfileCC);
	JsonUtil jsonUtilRegistration = new JsonUtil(Filepaths.testDataSCPJsonFilepath,
			fileRegistration);

	public OneTimePaymentCreditCard oneTimePaymentCCPayload(String ccAccountNum, String ccExp, String cardCVV,
			String amount, String serviceAccountNumber) {
		OneTimePaymentCreditCard otpaymCC = new OneTimePaymentCreditCard(ccAccountNum, ccExp, cardCVV, "1", amount,
				serviceAccountNumber, jsonUtilOTPCC.getStringJsonValue("CustomerName"), "Dell", "Night",
				"4 @Northeastern", "Blvd Salem", "Salem", "NH", jsonUtilOTPCC.getStringJsonValue("zip"),
				jsonUtilOTPCC.getStringJsonValue("countryCode"), jsonUtilOTPCC.getStringJsonValue("email"),
				jsonUtilOTPCC.getStringJsonValue("phone"), "Residential", "1", "Mobile", "781");
		return otpaymCC;
	}

	public OneTimePaymentBank oneTimePaymentBankPayLoad(String amt) {
		OneTimePaymentBank otpayBank = new OneTimePaymentBank(jsonUtilBank.getStringJsonValue("accountNumber"),
				jsonUtilBank.getStringJsonValue("BankAccountNum"), jsonUtilBank.getStringJsonValue("RoutingNumber"), amt,
				jsonUtilBank.getStringJsonValue("paymentMethodType"),
				jsonUtilBank.getStringJsonValue("serviceAccountNumber"), jsonUtilBank.getStringJsonValue("customerName"),
				jsonUtilBank.getStringJsonValue("FirstName"), jsonUtilBank.getStringJsonValue("LastName"),
				jsonUtilBank.getStringJsonValue("AddressLine1"), jsonUtilBank.getStringJsonValue("AddressLine2"),
				jsonUtilBank.getStringJsonValue("City"), jsonUtilBank.getStringJsonValue("State"),
				jsonUtilBank.getStringJsonValue("Zip"), jsonUtilBank.getStringJsonValue("countryCode"),
				jsonUtilBank.getStringJsonValue("BankAccountType"), jsonUtilBank.getStringJsonValue("email"),
				jsonUtilBank.getStringJsonValue("phone"), jsonUtilBank.getStringJsonValue("AccounType"),
				jsonUtilBank.getStringJsonValue("AccountUtilityID"), jsonUtilBank.getStringJsonValue("ChannelType"),
				jsonUtilBank.getStringJsonValue("UserId"), jsonUtilBank.getStringJsonValue("BankName"));
		return otpayBank;
	}

	public LoginPayload getLoginPayLoad(String sUsername, String sPassword, String sAccountNumber) {
		LoginPayload login = new LoginPayload(sUsername, sPassword, sAccountNumber,
				jsonUtilLogin.getStringJsonValue("LanguageCode"), jsonUtilLogin.getStringJsonValue("ExternalLoginId"),
				Integer.parseInt(jsonUtilLogin.getStringJsonValue("LoginMode")),
				Integer.parseInt(jsonUtilLogin.getStringJsonValue("IsCSRUser")),
				jsonUtilLogin.getStringJsonValue("PushToken"), jsonUtilLogin.getStringJsonValue("Deviceid"),
				jsonUtilLogin.getStringJsonValue("UpdatedDate"), jsonUtilLogin.getStringJsonValue("LUpdHideShow"),
				Integer.parseInt(jsonUtilLogin.getStringJsonValue("TimeOffSet")),
				jsonUtilLogin.getStringJsonValue("deviceType"), jsonUtilLogin.getStringJsonValue("OperatingSystem"),
				jsonUtilLogin.getStringJsonValue("Browser"), jsonUtilLogin.getStringJsonValue("Country"),
				jsonUtilLogin.getStringJsonValue("IPAddress"));
		return login;
	}

	public LoginHelpPayload getLoginHelpPayLoad(String sEmailId, int IsForgotPassword, String sUtilityAccountNumber,
			int iType) {
		LoginHelpPayload loginHelpPayload = new LoginHelpPayload(sEmailId, IsForgotPassword,
				jsonUtilLoginHelp.getStringJsonValue("LanguageCode"), jsonUtilLoginHelp.getStringJsonValue("UtilityId"),
				sUtilityAccountNumber, iType, jsonUtilLoginHelp.getStringJsonValue("Reason"), true);
		return loginHelpPayload;
	}

	public PaymentProfile getPaymentProfile(String accNumber, String utilityNumber, String userID, String cardNumber,
			String cardType) {
		cardPaymentProfile = new PaymentProfile(Integer.parseInt(userID), Integer.parseInt(accNumber),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("PayTypeId")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("IsBankAccount")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("Mode")),
				jsonUtilPaymentProfileCC.getStringJsonValue("CardName"), cardType, cardNumber,
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ExpiryMonth")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ExpiryYear")),
				jsonUtilPaymentProfileCC.getStringJsonValue("SecurityCode"),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("PaymentMode")),
				jsonUtilPaymentProfileCC.getStringJsonValue("LanguageCode"), utilityNumber,
				jsonUtilPaymentProfileCC.getStringJsonValue("PaymentToken"),
				jsonUtilPaymentProfileCC.getStringJsonValue("CustomerRefNum"),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ChannelType")),
				jsonUtilPaymentProfileCC.getStringJsonValue("IP"));
		return cardPaymentProfile;
	}

	public PaymentProfile getPaymentProfile() {
		cardPaymentProfile = new PaymentProfile(
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("UserID")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("AccountNumber")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("PayTypeId")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("IsBankAccount")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("Mode")),
				jsonUtilPaymentProfileCC.getStringJsonValue("CardName"),
				jsonUtilPaymentProfileCC.getStringJsonValue("CardType"),
				jsonUtilPaymentProfileCC.getStringJsonValue("CardNumber"),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ExpiryMonth")),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ExpiryYear")),
				jsonUtilPaymentProfileCC.getStringJsonValue("SecurityCode"),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("PaymentMode")),
				jsonUtilPaymentProfileCC.getStringJsonValue("LanguageCode"),
				jsonUtilPaymentProfileCC.getStringJsonValue("UtilityAccountNumber"),
				jsonUtilPaymentProfileCC.getStringJsonValue("PaymentToken"),
				jsonUtilPaymentProfileCC.getStringJsonValue("CustomerRefNum"),
				Integer.parseInt(jsonUtilPaymentProfileCC.getStringJsonValue("ChannelType")),
				jsonUtilPaymentProfileCC.getStringJsonValue("IP"));
		return cardPaymentProfile;
	}

	public RegistrationAPI getRegistrationPayLoad(String firstName, String lastName, String emailId,
												  String mobileNum, String postalCode, String userName,
												  String pwd, String confirmPwd, String meterNum,
												  String ssnNum, String utilityAccNum, String streetNum,
												  String drivingLicence, String customerType, String contactType,
												  String notificationPref, String paperlessBill) {
		RegistrationAPI registration = new RegistrationAPI(
				jsonUtilRegistration.getStringJsonValue("AccountNumber"),
				firstName,
				jsonUtilRegistration.getStringJsonValue("MiddleName"),
				lastName,
				emailId,
				jsonUtilRegistration.getStringJsonValue("DateOfBirth"),
				mobileNum,
				jsonUtilRegistration.getStringJsonValue("HomePhone"),
				jsonUtilRegistration.getStringJsonValue("Address1"),
				jsonUtilRegistration.getStringJsonValue("Address2"),
				jsonUtilRegistration.getStringJsonValue("IsPasswordRequest"),
				jsonUtilRegistration.getStringJsonValue("CityId"),
				postalCode,
				userName,
				pwd,
				confirmPwd,
				jsonUtilRegistration.getStringJsonValue("SecurityQuestionId"),
				jsonUtilRegistration.getStringJsonValue("HintAns"),
				meterNum,
				jsonUtilRegistration.getStringJsonValue("EmailNotify"),
				jsonUtilRegistration.getStringJsonValue("BudgetNotify"),
				jsonUtilRegistration.getStringJsonValue("BillingAddress"),
				jsonUtilRegistration.getStringJsonValue("ServiceAccount"),
				jsonUtilRegistration.getStringJsonValue("CustomerUtilityId"),
				jsonUtilRegistration.getStringJsonValue("AddressPowerPlanID"),
				jsonUtilRegistration.getStringJsonValue("EVPowerPlanID"),
				jsonUtilRegistration.getStringJsonValue("DefaultUsageView"),
				jsonUtilRegistration.getStringJsonValue("DefaultpaymentType"),
				jsonUtilRegistration.getStringJsonValue("UtilityID"),
				jsonUtilRegistration.getStringJsonValue("Latitude"),
				jsonUtilRegistration.getStringJsonValue("Longitude"),
				jsonUtilRegistration.getStringJsonValue("SecurityQuestionId2"),
				jsonUtilRegistration.getStringJsonValue("HintsAns2"),
				ssnNum,
				utilityAccNum,
				jsonUtilRegistration.getStringJsonValue("IsVerfication"),
				jsonUtilRegistration.getStringJsonValue("SessionCode"),
				jsonUtilRegistration.getStringJsonValue("AlternateEmailID"),
				jsonUtilRegistration.getStringJsonValue("BPNumber"),
				streetNum,
				drivingLicence,
				jsonUtilRegistration.getStringJsonValue("IPAddress"),
				jsonUtilRegistration.getStringJsonValue("IsCSRUser"),
				jsonUtilRegistration.getStringJsonValue("LanguageCode"),
				jsonUtilRegistration.getStringJsonValue("CustomerNo"),
				customerType,
				jsonUtilRegistration.getStringJsonValue("ExternalLoginId"),
				jsonUtilRegistration.getStringJsonValue("LoginMode"),
				contactType,
				notificationPref,
				paperlessBill,
				jsonUtilRegistration.getStringJsonValue("OSType"));
		return registration;
	}
}
