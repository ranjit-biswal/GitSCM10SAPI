package apiCore.Request.Login;

public class LoginHelpPayload {
	private String Email;
	private int IsForgotPassword;
	private String LanguageCode;
	private String UtilityId;
	private String UtilityAccountNumber;
	private int Type;
	private String Reason;
	private boolean IsShow;

	public LoginHelpPayload(String Email, int IsForgotPassword, String LanguageCode, String UtilityId,
			String UtilityAccountNumber, int Type, String Reason, boolean IsShow) {
		this.Email = Email;
		this.IsForgotPassword = IsForgotPassword;
		this.LanguageCode = LanguageCode;
		this.UtilityId = UtilityId;
		this.UtilityAccountNumber = UtilityAccountNumber;
		this.Type = Type;
		this.Reason = Reason;
		this.IsShow = IsShow;

	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getIsForgotPassword() {
		return IsForgotPassword;
	}

	public void setIsForgotPassword(int isForgotPassword) {
		IsForgotPassword = isForgotPassword;
	}

	public String getLanguageCode() {
		return LanguageCode;
	}

	public void setLanguageCode(String languageCode) {
		LanguageCode = languageCode;
	}

	public String getUtilityId() {
		return UtilityId;
	}

	public void setUtilityId(String utilityId) {
		UtilityId = utilityId;
	}

	public String getUtilityAccountNumber() {
		return UtilityAccountNumber;
	}

	public void setUtilityAccountNumber(String utilityAccountNumber) {
		UtilityAccountNumber = utilityAccountNumber;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public boolean isIsShow() {
		return IsShow;
	}

	public void setIsShow(boolean isShow) {
		IsShow = isShow;
	}

}
