package resources;

//enum is special class in java which has collection of constants or  methods
public enum Routes {
	
	//Login Related API
	GetLoginID("/UserLogin/GetId"),
	ValidateUserLogin("/UserLogin/ValidateUserLogin"),
	GetLogOut("/UserLogin/Logout"),
	GetLoginHelp("/UserLogin/GetLoginHelp"),
	
	
	
	SetConnectMeRequest("/ContactUs/SetConnectMeRequest"),
	AddOneTimePaymentCC("/SCM_7_5_2-BaseChasePayment/api/Payment/Card"),
	AddOneTimePaymentBank("/SCM_7_5_2-BaseChasePayment/api/Payment/Bank"),
	LoginApi("/UserLogin/ValidateUserLogin"),
	RegistrationApi("/Registration/SetCustomerRegistration"),
	PaymentProfile("/Billing/SetInsertBillPayMode"),
	AddTokenizedPayment("/SCM_7_5_2-BaseChasePayment/api/Payment/TokenizedPayment"),
	AddUpdateOutage("/Outagecsp/InsertOutage");

	private String resource;
	
	Routes(String resource){
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}
}
