package utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class contains all the SCM and SCP related SQL Queries.
 *
 * @author Ranjit.Biswal
 * @since 09-04-19
 *
 */
public class SqlQuery {
	/*******************************************************
	 * >>>>>>>>>>>>> SCP RELATED SQL QUERIES <<<<<<<<<<<<<<*
	 *******************************************************/

	/*******************************************************
	 * >>>>>>>>>>>>>>>> LOGIN PAGE QUERIES <<<<<<<<<<<<<<< *
	 *******************************************************/
	public static String getDeActiveAccount() {
		String deActiveAccount = "select Top 1 Username from [user] where status='2' order by 1 desc";
		return deActiveAccount;
	}

	public static String getPassword(String username) {
		String password = "select password from [user] where username='" + username + "'";
		return password;
	}

	public static String updatePassword(String username, String password) {
		String updatePassword = "update [user] set Password='" + password + "' where username='" + username + "'";
		return updatePassword;
	}

	public static String updateUsernameQuery(String sUserId, String sChangeUsername) {
		String sQuery = "UPDATE [User] SET UserName = '" + sChangeUsername + "'\n" + "WHERE UserID = '" + sUserId + "'";
		return sQuery;
	}

	/*********************************************************
	 * >>>>>>>>>>>>> LOGIN SUPPORT PAGE QUERIES <<<<<<<<<<<<<<*
	 *********************************************************/
	public static String sRegisteredInactiveAccount = "SELECT UtilityAccountNumber, EmailID "
			+ "FROM CustomerInfo(NOLOCK) " + "WHERE UtilityAccountNumber IN (SELECT UtilityAccountNumber "
			+ "FROM CustomerInfo(NOLOCK) " + "WHERE AccountStatusID=2 " + "AND AccountNumber NOT IN (-1,2))";

	public static String getForgetPasswordEmailQuery(String username) {
		String query = "SELECT TOP 1 Message FROM ContractAccountNotifyEmail "
				+ "WHERE EmailID = (SELECT EmailID FROM [User] " + "WHERE UserName = '" + username + "') "
				+ "AND Subject = 'Smart Energy Water Password Reset Link' ORDER BY CreateDate DESC";
		return query;
	}

	public static String getSecurityQue1Query(String username) {
		String query = "SELECT ControlText FROM multilingualmaster WHERE LanguageCode = 'EN' "
				+ "AND ControlId=(SELECT ControlId FROM SecurityQuestion WHERE QuestionId = "
				+ "(SELECT SecurityQuestionId FROM [user] WHERE UserName ='" + username + "'))";
		return query;
	}

	public static String getSecurityQue2Query(String username) {
		String query = "SELECT ControlText FROM multilingualmaster WHERE LanguageCode = 'EN' "
				+ "AND ControlId=(Select ControlId from SecurityQuestion "
				+ "WHERE QuestionId = (SELECT SecurityQuestionId2 FROM [user] WHERE UserName ='" + username + "'))";
		return query;
	}

	public static String getRegNotActivatedUserQuery() {
		String query = "Select  CI.UtilityAccountNumber , U.EmailID, U.UserName "
				+ "from [User] U Join useraccount UA on U.Userid=UA.Userid \r\n"
				+ "join CustomerInfo CI on UA.UtilityAccountNumber = CI.UtilityAccountNumber "
				+ "where U.status=0 and CI.AddressType in  ('Residential', 'Commercial') "
				+ "and convert(date,U.LinkSentDate)>=convert(date,getdate()-3)\r\n" + "";
		return query;
	}

	public static String sSecurityLeveValue = "Select [Value] from TemplateDetail"
			+ " where ControlName = 'Security Level'";

	public static String getPasswordResetLinkEmailMsg(String sEmailId) {
		String sQuery = "SELECT TOP 1 ID, AccountNumber, EmailID, Subject, Message\n"
				+ "FROM ContractAccountNotifyEmail\n" + "WHERE EmailID = '" + sEmailId + "'\n"
				+ "AND Subject = 'SCM Password Reset Link'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	public static String getUsernameAssistanceEmailMsg(String sSubjectLine, String sEmailAddress) {
		String sQuery = "SELECT TOP 1 Message, IsNotify\n" + "FROM ContractAccountNotifyEmail\n" + "WHERE EmailID = '"
				+ sEmailAddress + "' " + "AND Subject = '" + sSubjectLine + "'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	public static String getInactiveAccDetails() {
		String query = "select distinct(ci.utilityaccountnumber), ci.accountstatusid, ci.zipcode, ci.emailid,\n" +
				"ci.addresstype, ci.address1, ci.firstname, ci.lastname, ci.mobilephone,\n" +
				"ci.drivinglicence, ci.accountstatus, ci.addresstype, amm.meternumber\n" +
				"from customerinfo as ci\n" +
				"left join accountmetermapping as amm on ci.accountnumber = amm.accountnumber\n" +
				"left join useraccount as ua on ua.utilityaccountnumber != ci.utilityaccountnumber\n" +
				"where ci.accountstatusid = '2'";
		return query;
	}

	/*****************************************************
	 * >>>>>>> CUSTOMER REGISTRATION PAGE QUERIES <<<<<<< *
	 *****************************************************/
	public static String sResidentialInactiveUserIDQuery = "DECLARE @ExpirationDays SMALLINT, @ExpirationMinutes INT "
			+ "SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') " + "FROM TemplateDetail T(NOLOCK) "
			+ "JOIN CommonMaster CM(NOLOCK) ON (CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
			+ "WHERE TempDetailID = 51 SELECT U.UserID FROM [User] U (NOLOCK) "
			+ "JOIN UserAccount UA(NOLOCK) ON U.UserID=UA.UserID "
			+ "JOIN Account IA(NOLOCK) ON IA.AccountNumber=UA.AccountNumber "
			+ "JOIN CustomerAddress CA(NOLOCK) ON CA.AddressId=IA.AddressId " + "WHERE U.Status=0  "
			+ "AND CA.AddressType=2 "
			+ "AND DATEDIFF(DAY,ISNULL(ReminderDate,U.CreatedDate),GETDATE())<=@ExpirationDays  ";

	public static String sInactiveDetailsQuery = "SELECT EmailID, UtilityAccountNumber, ZipCode, MeterNumber "
			+ "FROM VCustomer WHERE UserID= ";

	public static String sUserNameFromQuery = "Select Top 1 UserName From [User] where UserName is NOT NULL "
			+ "AND UserId NOT IN (1,-1) AND UserName <> ''";

	public static String sResidentialAccountResistered = "" + "SELECT Top 1 A.UtilityAccountNumber,A.AccountNumber "
			+ ",(CASE CA.AddressType WHEN 1 THEN 'Residential' ELSE 'Commercial' END) AS AddressType " + " "
			+ "FROM Account A(NOLOCK) " + "JOIN CustomerAddress CA(NOLOCK) ON A.AddressID=CA.AddressID "
			+ "WHERE A.Status=1 AND CA.AddressType=1";

	public static String sCommercialAccountResistered = "" + "SELECT Top 1 A.UtilityAccountNumber,A.AccountNumber "
			+ ",(CASE CA.AddressType WHEN 1 THEN 'Residential' ELSE 'Commercial' END) AS AddressType " + " "
			+ "FROM Account A(NOLOCK) " + "JOIN CustomerAddress CA(NOLOCK) ON A.AddressID=CA.AddressID "
			+ "WHERE A.Status=1 AND CA.AddressType=2";

	/**
	 * This getRegistrationTemplateConfig query fetches the Min,Max, Mandatory
	 * Status, Validation against CIS related information form the Database
	 * Primary Contact Number 10 10 true true
	 *
	 * @return Field Name-Primary Contact Number, MinLegth-10, MaxLegth-10,
	 *         Mandatory -True, Validation Against CIS-true
	 */
	public static String getRegistrationTemplateConfig() {
		String sRegistrationTemplateConfig = "" + " SELECT * from( "
				+ "select a.ControlName AS ParentHead,b.ControlName,b.value, b.ScpStatus from TemplateDetail a "
				+ "left join TemplateDetail b on a.TempDetailID=b.Parentid "
				+ "where b.ControlName IN ('Min Length','Max Length','Mandatory','Validation Against CIS', 'Type') "
				+ " ) AS s " + " PIVOT " + "(max(value) "
				+ "	for ControlName IN ([Min Length],[Max Length],[Mandatory],[Validation Against CIS], [Type]) )as pvt";
		return sRegistrationTemplateConfig;
	}

	/**
	 * This getRegistrationData query fetches the information required to do a
	 * successful registration
	 *
	 * @param addType
	 *            - 1 for Residential User, 2 For Business User
	 * @return CustomerID MobilePhone DrivingLicence customerNo
	 *         UtilityAccountNumber ZipCode Address1 SSNNumber CustomerType
	 *         MeterNumber
	 */
	public static String getRegistrationData(String addType, String accStatus) {
		String sRegistrationData = "SELECT Top 1 c.CustomerID, c.MobilePhone, c.DrivingLicence, C.customerNo, "
				+ "CA.UtilityAccountNumber,CA.ZipCode,CA.Address1,1234 AS SSNNumber, "
				+ "(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) AS CustomerType, "
				+ "MAX(AMM.MeterNumber) AS MeterNumber " + "FROM Customer c(NOLOCK) "
				+ "JOIN CustomerAddress CA(NOLOCK) ON c.CustomerID=ca.CustomerID "
				+ "JOIN Account a(NOLOCK) ON ca.AddressID = a.AddressID "
				+ "JOIN AccountMeterMapping AMM(NOLOCK) ON A.AccountNumber=AMM.AccountNumber "
				+ "LEFT JOIN (SELECT DISTINCT IA.AccountNumber "
				+ "FROM Customer IC(NOLOCK) JOIN CustomerAddress ICA(NOLOCK) ON IC.CustomerID = ICA.CustomerID "
				+ "JOIN Account IA(NOLOCK) ON IA.AddressID = ICA.AddressID "
				+ "JOIN UserAccount IUA(NOLOCK) ON IUA.AccountNumber=IA.AccountNumber) R ON A.AccountNumber = "
				+ "R.AccountNumber " + "WHERE R.AccountNumber IS NULL "
				+ "AND c.CustomerID NOT IN (1,-1, 10417, 10615, 10931) " + "AND CA.AddressType=" + addType + " "
				+ "AND a.Status='" + accStatus + "' " + "AND CA.PortalAccessType = 0 "
				+ "GROUP BY c.CustomerID, c.MobilePhone, C.customerNo, "
				+ "(CASE WHEN CA.AddressType = 1 THEN 'Residential' ELSE 'Commercial' END), "
				+ "c.DrivingLicence, CA.ZipCode, CA.Address1,C.SSNNumber,CA.UtilityAccountNumber";
		return sRegistrationData;
	}

	/*
	 * Get Residential account Registration data of a GIVEN ACCOUNT for
	 * registration.
	 *
	 */
	public static String getRegData(String addType, String accStatus) {
		String regDataQuery = "select top 1 ci.accountnumber, ci.utilityaccountnumber, ci.zipcode, ci.address1,\n"
				+ "    ci.accountstatusid, ci.addresstype, ci.firstname, ci.lastname, ci.mobilephone,\n"
				+ "    ci.drivinglicence, ci.accountstatus, ci.addresstype, ci.addressid, ci.customerid, \n"
				+ "    amm.meternumber, ca.homeinfostatus\n" + "from customerinfo as ci\n"
				+ "    left join accountmetermapping as amm on ci.accountnumber = amm.accountnumber\n"
				+ "    left join customeraddress as ca on ci.addressid = ca.addressid\n"
				+ "where ci.accountstatusid = '" + accStatus + "'\n" + "    and ci.addresstype = '" + addType + "'\n"
				+ "    and amm.meternumber != 'NULL'\n" + "    and ci.drivinglicence != 'NULL'\n"
				+ "    and ci.portalaccesstype = 'Standard'\n" + "    and ca.homeinfostatus = '0'\n"
				+ "    order by ci.createddate desc";
		return regDataQuery;
	}

	public static String getRegisteredAccount() {
		String query = "select top 1 ua.utilityaccountnumber, ua.roleid, ua.userid, u.status, u.emailid \n" +
				"from useraccount as ua\n" +
				"left join [user] as u on u.userid = ua.userid\n" +
				"where ua.roleid = '3' and u.status = '1'";
		return query;
	}

	public static String getCustomerDetails(String utilityAccNo) {
		String query = "select top 1 ci.accountnumber, ci.utilityaccountnumber, ci.zipcode, ci.address1,\n" +
				"ci.accountstatusid, ci.addresstype, ci.firstname, ci.lastname, ci.mobilephone, ci.emailid,\n" +
				"ci.drivinglicence, ci.accountstatus, ci.addresstype, ci.addressid, ci.customerid,\n" +
				"amm.meternumber, ca.homeinfostatus \n" +
				"from customerinfo as ci\n" +
				"left join accountmetermapping as amm on ci.accountnumber = amm.accountnumber \n" +
				"left join customeraddress as ca on ci.addressid = ca.addressid\n" +
				"where ci.utilityaccountnumber = '"+utilityAccNo+"'";
		return query;
	}

	/**
	 * This sUserIDQuery is to get the user id for an utilityAccountNumber
	 */
	public static String sUserIDQuery = "SELECT UserID FROM UserAccount where RoleID = 3 AND UtilityAccountNumber = '";

	/**
	 * This getUserId is to get the user id for a username select
	 * PaymentAccount, CCExpMonth, CCExpYear, FirstName, BankName from
	 * PaymentProfiles where ExternalId= '431' and ProfileStatus=1 order by 1
	 * desc.
	 */
	public static String getUserId(String username) {
		String sUserIDQuery = "select UserId from [User] where UserName='" + username + "'";
		return sUserIDQuery;
	}

	public static String sCommercialInactiveUserIDQuery = "DECLARE @ExpirationDays SMALLINT, @ExpirationMinutes "
			+ "INT SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T(NOLOCK) "
			+ "JOIN CommonMaster CM(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
			+ "WHERE TempDetailID = 51 SELECT U.UserID FROM [User] U (NOLOCK) "
			+ "JOIN UserAccount UA(NOLOCK) ON U.UserID = UA.UserID "
			+ "JOIN Account IA(NOLOCK) ON IA.AccountNumber = UA.AccountNumber "
			+ "JOIN CustomerAddress CA(NOLOCK) ON CA.AddressId = IA.AddressId "
			+ "WHERE U.Status = 0 AND CA.AddressType = 2 "
			+ "AND DATEDIFF(DAY, ISNULL(ReminderDate, U.CreatedDate), GETDATE())<=@ExpirationDays";

	public static String getContactTypeQuery = "select Name from commonmaster where mastertype='PhoneNumberType'";

	/**
	 * This method is to return the query for getting user id of the given user
	 * name.
	 *
	 * @param userName
	 * @return
	 */
	public static String getUserIdOfGivenUsernameQuery(String userName) {
		String sQuery = "SELECT UserID FROM [User] WHERE UserName = '" + userName + "'";
		return sQuery;
	}

	/**
	 * This method is to return stored procedure for deleting user with user id.
	 *
	 * @param userId
	 * @return
	 */
	public static String getStoredProcedureForDeletingUserWithUserId(String userId) {
		String sQuery = "EXEC SetUnRegisterUser @UserID=" + userId;
		return sQuery;
	}

	/**
	 * This method is to get the registered user data query.
	 *
	 * @param userName
	 * @return
	 */
	public static String getRegisteredUserDataQuery(String userName) {
		String sQuery = "SELECT UserID, UserName, EmailID, [Status], SecurityQuestionId, SecurityQuestionId2, "
				+ "Zipcode, MobilePhone, FirstName, LastName FROM [User] WHERE UserName = '" + userName + "'";
		return sQuery;
	}

	/**
	 * This method returns query for fetching the account activation mail
	 * content.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getRegistrationEmailContent(String sUsername) {
		String sQuery = "SELECT CustomerName, EmailId, Subject, EmailMsg, IsEmailSent\r\n"
				+ "FROM ContractAccountNotify\r\n" + "WHERE CustomerName='" + sUsername + "'";
		return sQuery;
	}

	public static String getAccountStatus(String sUsername) {
		String sQuery = "SELECT Status, UserName \n" + "FROM [User] \n" + "WHERE UserName ='" + sUsername + "'";
		return sQuery;
	}

	public static String getCISDataFromCustomerInfo(String sUtilityAccountNumber) {
		String sQuery = "SELECT MobilePhone, EmailID FROM Customer \n"
				+ "WHERE CustomerId = (SELECT CustomerID FROM UserAccount " + "WHERE UtilityAccountNumber = '"
				+ sUtilityAccountNumber + "')";
		return sQuery;
	}

	public static String getCISDataFromAccountTable(String sUtilityAccountNumber) {
		String sQuery = "SELECT Paperless FROM Account\n" + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber
				+ "'";
		return sQuery;
	}

	public static String getAlreadyTakenUsername() {
		String sQuery = "select top 1 username from [user] order by userid desc";
		return sQuery;
	}

	public static String getDefaultAccQuery(String username) {
		String query = "select utilityaccountnumber \n" + "from useraccount \n"
				+ "where userid = (select userid from [user] where username = '" + username + "')\n"
				+ "and isdefaultaccount = '1'";
		return query;
	}

	public static String getEmailIdWithUsername(String username) {
		String query = "select emailid from [user]\n" + "where username = '" + username + "'";
		return query;
	}

	/***********************************************************
	 * >>>>>>>>>>> ONE TIME PAYMENT PAGE QUERIES <<<<<<<<<<<<< *
	 ***********************************************************/
	// This query is for accounts with -ve balance
	public static String sResidentialAccountRegisteredBalanceAmountNegative = "";

	public static String sResidentialAccountResisteredBalanceAmountNegative = ";" + "DECLARE @AddressType TINYINT=1 "
			+ "IF OBJECT_ID('TempDB..#LatestBill','U') IS NOT NULL " + "DROP TABLE #LatestBill "
			+ "IF OBJECT_ID('TempDB..#LatestBill1','U') IS NOT NULL " + "DROP TABLE #LatestBill1 "
			+ "SELECT AccountNumber,MAX(BillingDate) AS BillingDate " + "INTO #LatestBill " + "FROM Billing(NOLOCK) "
			+ "GROUP BY AccountNumber " + "SELECT LB.AccountNumber,B.BillingID " + "INTO #LatestBill1 "
			+ "FROM #LatestBill LB "
			+ "JOIN Billing B(NOLOCK) ON LB.AccountNumber=B.AccountNumber AND LB.BillingDate=B.BillingDate "
			+ "SELECT TOP 1 CI.FullName,CI.Address1,CI.AddressType,CI.CityName,CI.StateName,CI.ZipCode, "
			+ "CI.MobilePhone,CI.EmailID,CI.UtilityAccountNumber,LB.AccountNumber,CONVERT(NUMERIC(18,2),Value) AS RemainingBalance "
			+ "FROM #LatestBill1 LB " + "JOIN BillingDetail BD(NOLOCK) ON LB.BillingID=BD.BillingID "
			+ "JOIN CustomerInfo CI(NOLOCK) ON LB.AccountNumber=CI.AccountNumber "
			+ "WHERE BD.HeadID=24 AND CONVERT(NUMERIC(18,2),Value)<=0 AND CI.AddressTypeID=@AddressType";

	public static String sUserBillNotGenerated = ""
			+ "SELECT TOP 1 CI.FullName,CI.Address1,CI.AddressType,CI.CityName,CI.StateName,CI.ZipCode,CI.MobilePhone,CI.EmailID,CI.UtilityAccountNumber,CI.AccountNumber"
			+ " FROM CustomerInfo CI(NOLOCK)" + " LEFT JOIN" + "( " + "SELECT DISTINCT AccountNumber "
			+ "FROM Billing(NOLOCK) " + ")B ON CI.AccountNumber=B.AccountNumber "
			+ "WHERE CI.AccountStatusID=1 AND B.AccountNumber IS NULL";

	/**
	 * This method returns payment received mail query.
	 *
	 * @param sUserID
	 * @param sMailSub
	 * @return
	 */
	public static String getPaymentMailsQuery(String sUserID, String sMailSub) {
		String sQuery = "SELECT TOP 1 Message, IsNotify, CreateDate FROM ContractAccountNotifyEmail\n"
				+ "WHERE UserID = '" + sUserID + "'\n" + "AND Subject = '" + sMailSub + "'\n" + "ORDER BY ID DESC";
		return sQuery;
	}

	/***********************************************************
	 * >>>>>>>>>>>>>>>> SIGN OUT PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ***********************************************************/

	/***********************************************************
	 * >>>>>>>>>>>>>>>> DASHBOARD PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ***********************************************************/
	public static String getCompareDataForDashboardWidget(String sUtilityAccountNumber) {
		String sQuery = "SELECT UtilityAccountNumber, Usagedate, [Value], Consumed, FromDate, ToDate "
				+ "FROM CompareDataLanding\n" + "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "'\n"
				+ "ORDER BY UsageDate DESC";
		return sQuery;
	}

	public static String getExistingEncryptedPasswordQuery(String sUserName) {
		String sQuery = "SELECT Password\n" + "FROM [User]\n" + "WHERE UserName = '" + sUserName + "'";
		return sQuery;
	}

	public static String updateExistingPassGivenPass(String sPassword, String sUserName) {
		String sQuery = "UPDATE [User]\n" + "SET Password = '" + sPassword + "'\n" + "WHERE UserName = '" + sUserName
				+ "'";
		return sQuery;
	}

	public static String getUserFullName(String sUserName) {
		String sQuery = "SELECT FirstName, LastName  FROM [user] WHERE username='" + sUserName + "'";
		return sQuery;
	}

	public static String getAutoPayEnrollStatus(String accountNumber) {
		String sStatus = "SELECT PayTypeId  FROM AccountRecurringPayment "
				+ "WHERE AccountNumber=(SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='" + accountNumber
				+ "')";
		return sStatus;
	}

	public static String getProjectedUsageForAMI(String accountNumber) {
		String sQuery = "SELECT PowerExpectedUsage,WaterExpectedUsage,GasExpectedUsage,SolarExpectedUsage FROM usagegenerationsummary WHERE AccountNumber='"
				+ accountNumber + "'";
		return sQuery;
	}

	public static String getAccountNumber(String UtilityAccountNumber) {
		String sQuery = "SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='" + UtilityAccountNumber + "'";
		return sQuery;
	}

	public static String getBillingDate(String UtilityAccountNumber) {
		String sQuery = "SELECT BillingDate FROM BIlling WHERE AccountNumber= (SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ UtilityAccountNumber + "' )";
		return sQuery;
	}

	public static String getPaymentMethod(String UtilityAccountNumber, String UserName) {
		String sQuery = "SELECT DefaultPaymentType FROM  UserAccount WHERE UtilityAccountNumber='"
				+ UtilityAccountNumber + "' AND UserID=(Select UserID from [User] where UserName ='" + UserName + "')";
		return sQuery;
	}

	public static String getIsAMIStatus(String UtilityAccountNumber) {
		String sQuery = "SELECT	IsAMI FROM AccountMeterMapping WHERE AccountNumber = (SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ UtilityAccountNumber + "')";
		return sQuery;
	}

	/***********************************************************
	 * >>>>>>>>>>> ACCOUNTS PROFILE PAGE QUERIES <<<<<<<<<<<<< *
	 ***********************************************************/
	/**
	 * Getting the profile information for a user
	 *
	 * @param Username
	 * @return sProfileInfo query which brings Username, email id, Mobile number
	 *         etc.
	 */
	public static String getMyAccountProfileInfo(String Username) {
		String sProfileInfo = "Select * from [User] where UserName ='" + Username + "'";
		return sProfileInfo;
	}

	/**
	 * This query brings the default account type whether commercial(2) or
	 * residential(1).
	 */
	public static String sDefaultUserAccountType = ""
			+ "SELECT distinct AddressType FROM VCustomer WHERE IsDefaultAccount=1 and UserName='";

	/**
	 * This query brings the default account and address in the Adress list in
	 * the header in the below format. 3456, NorthDakota Ave (C002002003)
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccountAddressHeader(String sUserName) {
		String sDefaultAccountAddressHeaderQuery = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='"
				+ sUserName + "')"
				+ " SELECT C.Address1+' ('+ UA.UtilityAccountNumber+')' as PropertyAddress FROM  [User] U (NOLOCK)"
				+ " JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID"
				+ " JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber WHERE U.userid=@AccountNo"
				+ " AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga WHERE AccessExpiryDate < getdate()"
				+ " AND ua.RequestID=ga.RequestID) and UA.ISDefaultAccount=1 ORDER BY UA.IsDefaultAccount DESC";
		return sDefaultAccountAddressHeaderQuery;
	}

	/**
	 * This query brings the default account and address in the Account Widget
	 * in Dashboard Page the header in the below format. 3456, NorthDakota
	 * Ave,Chino Hills,CA,-92602 (C002002003)
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAddressAccountWidget(String sUserName) {
		String sDefaultAccountAddressHeaderQuery = "SELECT DISTINCT (Address1 +', '+ Address2 +', '+CityName+', '+(Select  StateCode from statemaster where StateName=(select Top 1 StateName from VCustomer WHERE IsDefaultAccount=1 and UserName='"
				+ sUserName
				+ "'))+' - '+ZipCode+' ('+ UtilityAccountNumber+')') AS AddressAccountNumber FROM VCustomer WHERE IsDefaultAccount=1 and UserName='"
				+ sUserName + "'";

		return sDefaultAccountAddressHeaderQuery;
	}

	/**
	 * This method is to get the query to get all the accounts having owner
	 * access.
	 *
	 * @param userName
	 * @return
	 */
	public static String getAccountsWithOwnerAccess(String userName) {
		String sAccountsHavingOwnerAccessQuery = "SELECT [UserAccount].UtilityAccountNumber FROM [UserAccount] "
				+ "Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [User].username='" + userName
				+ "' " + "and [UserAccount].RoleId='3'";
		return sAccountsHavingOwnerAccessQuery;
	}

	/**
	 * This query brings the all account and address in the Address list in the
	 * header in the below format. 3456, NorthDakota Ave (C002002003)
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getAllAccountAddressHeader(String sUserName) {
		String sAllAccountAddressHeader = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='"
				+ sUserName + "') "
				+ "SELECT C.Address1+' ('+ UA.UtilityAccountNumber+')' as PropertyAddress FROM  [User] U (NOLOCK) "
				+ "JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID "

				+ "JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber "
				+ "JOIN Account ac ON UA.Accountnumber = ac.AccountNumber "
				+ " WHERE ac.status <> 3 and U.userid=@AccountNo "
				+ "AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga "

				+ "WHERE AccessExpiryDate < getdate() AND ua.RequestID=ga.RequestID)  ";
		return sAllAccountAddressHeader;
	}

	/**
	 * This query brings the default account in the header in the below format
	 * C002002003
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccount(String sUserName) {
		String sDefaultAccountQuery = "SELECT DISTINCT UtilityAccountNumber " + "FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "') "
				+ "AND IsDefaultAccount = '1'";
		return sDefaultAccountQuery;
	}

	/**
	 * This query brings the default UtilityAccountNumber and RoleID of the user
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccountAndRoleId(String sUserName) {
		String sDefaultAccountQuery = "SELECT DISTINCT UtilityAccountNumber, RoleID  " + "FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "') "
				+ "AND IsDefaultAccount = '1'";
		return sDefaultAccountQuery;
	}

	public static String getAllAccountsLinkedToTheUserQuery(String sUserName) {
		String sQuery = "SELECT DISTINCT UtilityAccountNumber\r\n" + "FROM UserAccount\r\n"
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "')";
//		System.out.println(sQuery);
		return sQuery;
	}

	public static String getAddressTypeQuery(String sUtilityAccountNumber) {
		String sQuery = "SELECT AddressType " + "FROM CustomerInfo " + "WHERE UtilityAccountNumber = '"
				+ sUtilityAccountNumber + "'";
		return sQuery;
	}

	/**
	 * This query brings the default
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAddressAccountNumber(String sUserName) {
		String sDefaultAddressAccount = "SELECT DISTINCT (Address1 +' ('+ UtilityAccountNumber+')') AS AddressAccountNumber FROM VCustomer \n"
				+ "WHERE IsDefaultAccount=1 AND UserName='" + sUserName + "'";
		return sDefaultAddressAccount;
	}

	/**
	 * This query brings all the accounts associated with a user login.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getAllUtilityAccountByUsername(String sUsername) {
		String sAllUtilityAccountByUsername = "SELECT UA.UtilityAccountNumber FROM UserAccount UA JOIN [User] U ON UA.UserID=U.UserID WHERE U.UserName='"
				+ sUsername + "'";
		return sAllUtilityAccountByUsername;
	}

	/**
	 *
	 * @param userName
	 * @param accountNumber
	 * @return
	 */
	public static String setDefaultAccount(String userName, String accountNumber) {
		String sDefaultAccount = "DECLARE @UserName NVARCHAR(100) DECLARE @UtilityAccountNumber VARCHAR(100)\n" +
				"SET @UserName='"+userName+"' SET @UtilityAccountNumber='"+accountNumber+"'\n" +
				"BEGIN TRANSACTION \n" +
				"UPDATE UA\n" +
				"SET IsDefaultAccount = 0 FROM UserAccount UA JOIN [User] U ON UA.UserID = U.UserID\n" +
				"WHERE U.UserName = @UserName \n" +
				"UPDATE UA SET IsDefaultAccount = 1\n" +
				"FROM UserAccount UA JOIN [User] U ON UA.UserID=U.UserID\n" +
				"WHERE U.UserName = @UserName AND UA.UtilityAccountNumber = @UtilityAccountNumber\n" +
				"IF (SELECT COUNT(1) FROM UserAccount UA(NOLOCK) JOIN [User] U(NOLOCK) ON UA.UserID = U.UserID \n" +
				"WHERE U.UserName = @UserName AND UA.IsDefaultAccount = 1) = 1\n" +
				"BEGIN  COMMIT TRANSACTION \n" +
				"SELECT 'Update was successful.' AS [Message]\n" +
				"END\n" +
				"ELSE\n" +
				"BEGIN ROLLBACK TRANSACTION\n" +
				"SELECT 'Update was not successful due to some mismatch in data.' AS [Message] END";
		return sDefaultAccount;
	}

	/**
	 * Getting all plan associated with a utility account
	 *
	 * @param utilityAccountNumber
	 * @return sAllAssociatedPlan query which brings all associated plans value
	 *         with utility account
	 */
	public static String getAllAssociatedPlans(String utilityAccountNumber) {
		String sAllAssociatedPlan = "SELECT AddressPowerPlanId, WaterPlanId, GasPlanId, PVPowerPlanId FROM Account WHERE UtilityAccountNumber='"
				+ utilityAccountNumber + "'";
		return sAllAssociatedPlan;
	}

	/**
	 * This minMaxLengthSecurityAns will give the result of Min and Max length
	 * of Security Answers
	 */
	public static String minMaxLengthSecurityAns = "SELECT ParentHead, [Min Length],[Max Length] from( select a.ControlName AS ParentHead,b.ControlName,b.value from TemplateDetail a left join TemplateDetail b on a.TempDetailID=b.Parentid where b.ControlName IN ('Min Length','Max Length')  ) AS s  PIVOT (max(value)	for ControlName IN ([Min Length],[Max Length]) )as pvt where ParentHead in ('Security Question 1','Security Question 2')";

	/**
	 * This minMaxLenZipCode will give the result of Min and Max length of
	 * Security Answers
	 */
	public static String minMaxLenZipCode = "SELECT ParentHead, [Min Length],[Max Length] from( select a.ControlName AS ParentHead,b.ControlName,b.value from TemplateDetail a left join TemplateDetail b on a.TempDetailID=b.Parentid where b.ControlName IN ('Min Length','Max Length')  ) AS s  PIVOT (max(value)	for ControlName IN ([Min Length],[Max Length]) )as pvt where ParentHead = 'Zip Code'";

	/**
	 * This mailingAddress will give the query for mailing Address of a user
	 */
	public static String getMailingAddress(String userName) {
		String mailingAddrQuery = "Select convert(varchar(30),ExpiryDate,101) as ExpDate, * from UserCommunicationAddress where MailAddressType = '1' and UserID= (select UserId from [User] where UserName='"
				+ userName + "')";
		return mailingAddrQuery;
	}

	/**
	 * This temporaryMailingAddress will give the query for temporary mailing
	 * Address of a user
	 */
	public static String getTemporaryMailingAddress(String userName) {
		String tempMailingAddrQuery = "Select convert(varchar(30),ExpiryDate,101) as ExpDate, * from UserCommunicationAddress where MailAddressType = '2' and UserID= (select UserId from [User] where UserName='"
				+ userName + "')";
		return tempMailingAddrQuery;
	}

	/**
	 * This method will give the query for current set mailing Address of a user
	 */
	public static String getCurrentMailingAddress(String userName) {
		String currentMailingAddrQuery = "Select top 1 convert(varchar(30),ExpiryDate,101) as ExpDate, * from UserCommunicationAddress "
				+ "where UserID= (select UserId from [User] where UserName='" + userName
				+ "') and (cast(ExpiryDate as date)>getdate() or ExpiryDate is null) order By UpdatedDate Desc";
		return currentMailingAddrQuery;
	}

	/**
	 * This method is to get all Accounts(Owner/Guest) of a User, takes Username
	 * as input and give all Accounts of this user
	 */
	public static String getAllLinkedAccountAccounts(String username) {
		String sQuery = "SELECT UtilityAccountNumber, RoleID FROM UserAccount where UserID = (Select UserID FROM [User] where UserName='"
				+ username + "')";
		return sQuery;
	}

	// Accounts

	/*
	 * Get Security Question by Question ID
	 */
	public static String getSecurityQueByItsId(String questionID) {
		String sQuery = "select ControlText from multilingualmaster where LanguageCode = 'EN' and ControlId=(Select ControlId from SecurityQuestion WHERE QuestionId = '"
				+ questionID + "')";
		return sQuery;
	}

	/*
	 * Query to get AddressID of Given Utility Account
	 */
	public static String getAddressIdOfUtilityAccount(String uAccount) {
		String sQuery = "Select AddressID from Account where UtilityAccountNumber = '" + uAccount + "'";
		return sQuery;
	}

	/*******************************************
	 *>>>>> ACCOUNTS SETTING PAGE QUERIES <<<<<*
	 *******************************************/
	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param UtilityAccountNumber
	 * @param Username
	 * @return
	 */
	public static String getMyAccountSettingConfig(String UtilityAccountNumber, String Username) {
		String sAccountSetting = "SELECT * FROM [UserAccount]  WHERE UtilityAccountNumber = '" + UtilityAccountNumber
				+ "'" + " AND UserID=(Select UserID from [User] where UserName ='" + Username + "'" + ")";
//		System.out.println("query : " + sAccountSetting);
		return sAccountSetting;
	}

	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param Username
	 * @return
	 */
	public static String getMyAccountSettingLanguageConfig(String Username) {
		String sUserLanguageSetting = "Select * from LanguageMaster where LanguageGuId = (Select LanguageGuId from UserLanguage WHERE UserID =(Select UserID from [User] where UserName ='"
				+ Username + "'" + ")" + ")";
		return sUserLanguageSetting;
	}

	/**
	 * This method is to get the query to update the default payment method.
	 *
	 * @param sUsername
	 * @param sAccountNum
	 * @param sPaymentType
	 *            |=> For 'Pay as you Go' = '0' and 'Monthly' = '1'
	 * @return
	 */
	public static String getQueryToUpdatePaymentMethodForAcc(String sAccountNum, String sUsername,
															 String sPaymentType) {
		String sQuery = "UPDATE [UserAccount] SET DefaultPaymentType = '" + sPaymentType
				+ "' WHERE  UtilityAccountNumber = '" + sAccountNum
				+ "' AND UserID=(Select UserID from [User] where UserName ='" + sUsername + "');";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the default payment method.
	 *
	 * @param sUsername
	 * @param sLanguageCode
	 *            + Englist = EN, French = FR, Spanish = ES
	 * @return
	 */
	public static String getQueryToUpdateLanguageForUser(String sLanguageCode, String sUsername) {
		String sQuery = "UPDATE UserLanguage set LanguageGuId = (Select LanguageGuId from LanguageMaster where LanguageCode = '"
				+ sLanguageCode + "') WHERE UserID =(Select UserID from [User] where UserName ='" + sUsername + "')";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the default payment method
	 * Monthly for all accounts associated with user.
	 *
	 * @param sUsername
	 *            |=> For 'Pay as you Go' = '0' and 'Monthly' = '1'
	 * @return
	 */
	public static String getQueryToUpdateMonthlyPaymentMethodForAllOwnerAccounts(String sUsername) {
		String sQuery = "UPDATE [UserAccount] SET DefaultPaymentType = '1' WHERE DefaultPaymentType = '0' And RoleID ='3' And UserID=(Select UserID from [User] where UserName ='"
				+ sUsername + "');";
		return sQuery;
	}

	/************************************************************
	 * >>>>>>>> NOTIFICATION PREFERENCE PAGE QUERIES <<<<<<<<<< *
	 ************************************************************/

	/************************************************************
	 * >>>>>>>>>>>>> PAYMENT INFO PAGE QUERIES <<<<<<<<<<<<<<<< *
	 ************************************************************/
	/**
	 * This query brings the my account setting info's saved by the account.
	 *
	 * @param UtilityAccountNumber
	 * @param Username
	 * @return
	 */
	/*
	 * public static String getDefaultPayId(String UserId, String
	 * PaymentMethodType) { String sPaymentInfo =
	 * "select Top 1 PaymentAccount, CCExpMonth, CCExpYear, BankRTE, FirstName, BankName from PaymentProfiles where ExternalId= '"
	 * + UserId + "' and ProfileStatus=1 and PaymentMethodType=" +
	 * PaymentMethodType + " order by CreatedAt desc";
	 *
	 * return sPaymentInfo; }
	 */

	/**
	 * This query brings all the Payment info for a user id.
	 *
	 * @param UserId
	 * @return It comes from the Payment Database
	 */
	public static String getAllPaymentInfo(String UserId) {
		String sPaymentInfo = "Select FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, CCExpYear "
				+ "from PaymentProfiles where ProfileStatus =1 and ( (CCExpMonth>=month(getdate()) and CCExpYear=RIGHT(year(getdate()),2))OR "
				+ "( CCExpMonth>=1 and CCExpYear>RIGHT(year(getdate()),2))OR ACHType = 1 AND ISNULL(FirstName,'')!='') AND ExternalId= '"
				+ UserId + "' order by CreatedAt desc";

		return sPaymentInfo;
	}

	/**
	 * This method returns the latest payment method added for the user
	 *
	 * @param UserId
	 * @return
	 */
	public static String getLatestAddedPaymentInfo(String UserId) {
		String sPaymentInfo = "Select Top 1 FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, CCExpYear, ProfileStatus "
				+ "from PaymentProfiles where ProfileStatus =1 and ( (CCExpMonth>=month(getdate()) and CCExpYear=RIGHT(year(getdate()),2))OR "
				+ "( CCExpMonth>=1 and CCExpYear>RIGHT(year(getdate()),2))OR ACHType = 1 AND ISNULL(FirstName,'')!='') AND ExternalId= '"
				+ UserId + "' order by CreatedAt desc";
		return sPaymentInfo;
	}

	/**
	 * This method returns the latest payment method added for the user in Chase
	 * Database
	 *
	 * @param sAccountNo
	 * @return
	 */
	public static String getLatestAddedPaymentInfoChase(String sAccountNo) {
		String sPaymentInfo = "SELECT TOP 1 FirstName, BankName, BankRouting, BankAccountNumber, "
				+ "CCAccountNumber, CcExp, Cvv, PaymentType, ProfileStatus, IsDeleted, ACHType \n"
				+ "FROM PaymentProfiles\n" + "WHERE ServiceAccountNumber = '" + sAccountNo + "'\n"
				+ "ORDER BY CreatedDate DESC";
		return sPaymentInfo;
	}

	/**
	 * This query brings all the Payment info for a user id.
	 *
	 * @param UserId
	 * @return It comes from the Payment Database
	 */
	public static String getAllPaymentInfoChase(String UserId) {
		String sPaymentInfo = " Select FirstName, BankName, BankRouting, BankAccountNumber, "
				+ " CCAccountNumber, CcExp, Cvv, PaymentType, ProfileStatus, IsDeleted, ACHType "
				+ " FROM PaymentProfiles  where UserId in(select UserId from [SCM10_S].[dbo].[User] where UserName='"
				+ UserId + "') and IsDeleted=0 and ProfileStatus=3";

		return sPaymentInfo;
	}

	/**
	 * This query brings all the Payment info for a user id.
	 *
	 * @param UserId
	 * @return It comes from the Payment Database
	 */
	public static String getCountAllPaymentInfoChase(String UserId) {
		String sPaymentInfo = " Select count(*) as PaymentMethods"
				+ " FROM PaymentProfiles  where UserId in(select UserId from [SCM10_S].[dbo].[User] where UserName='"
				+ UserId + "') and IsDeleted=0 and ProfileStatus=3";
		return sPaymentInfo;
	}

	/**
	 * This method returns the latest payment method added for the user in API
	 * Payment Database
	 *
	 * @param sAccountNo
	 * @return
	 */
	public static String getStatusPaymentInfo(String sAccountNo) {
		String sPaymentInfo = "select top 1 PaymentProfileId, FirstName, BankName, BankRouting, BankAccountNumber, "
				+ "CCAccountNumber, CcExp, Cvv, PaymentType, ProfileStatus, IsDeleted, ACHType \n"
				+ "from paymentprofiles\n" + "where ServiceAccountNumber = '" + sAccountNo + "'\n"
				+ "order by CreatedDate desc";
		return sPaymentInfo;
	}

	public static String getDeletedCountPaymentInfo(String accountNo, String paymentProfileId) {
		String query = "select count(paymentprofileid) as count\n" + "from paymentprofiles\n"
				+ "where serviceaccountnumber = '" + accountNo + "'\n" + "and paymentprofileid = '" + paymentProfileId
				+ "'";
		return query;
	}

	/**
	 * This method returns the Defualt Payid or Token which is by the
	 * getDefaultPaymentInfo method for getting the Default Payment Info
	 *
	 * @param Username
	 * @return
	 */
	public static String getDefaultPayToken(String Username) {
		String sPaymentToken = "SELECT DefaultPayId FROM defaultpayment acc "
				+ "WHERE UserId = (Select UserId from [USER] WHERE UserName='" + Username + "')";
		return sPaymentToken;
	}

	/**
	 * This method returns the external id which is by the getDefaultPaymentInfo
	 * method for getting the Default Payment Info
	 *
	 * @param Username
	 * @return
	 */
	public static String getExternalId(String Username) {
		String sExternalId = "Select UserId from [USER] WHERE UserName='" + Username + "'";
		return sExternalId;
	}

	/**
	 * This method returns the Default Payment Info for a User
	 *
	 * @param PaymentToken
	 * @return
	 */
	public static String getDefaultPaymentInfo(String PaymentToken, String ExternalId) {
		String sPaymentInfo = "select Top 1 FirstName,BankRTE, BankName , PaymentAccount, CCExpMonth, "
				+ "CCExpYear from PaymentProfiles where PaymentToken='" + PaymentToken + "'" + "  AND ExternalId='"
				+ ExternalId + "'" + "and FirstName IS NOT NULL order by CreatedAt desc";

		return sPaymentInfo;
	}

	/**
	 * This method deletes all the payment info for the user
	 *
	 * @return
	 */
	public static String getDeletePaymentInfo(String Username) {
		String sPaymentInfoDelete = "update [SCM10_S_API_Payment].[dbo].PaymentProfiles set profilestatus=2 where UserId in(select UserId from [SCM10_S].[dbo].[User] where UserName='"
				+ Username + "')";

		return sPaymentInfoDelete;
	}

	/*****************************************************
	 *>>>> ACCOUNT MARKETING PREFERENCE PAGE QUERIES <<<<*
	 *****************************************************/
	public static String getMarketingPrefQuery(String username) {
		String marketingPrefQuery = "Select PreferenceId from UserMarketingPreferenceSetting where UserID = (Select UserID from [User] where UserName = '"
				+ username + "')";
		return marketingPrefQuery;
	}

	public static String getMarketingPrefEmailQuery(String email, String subject) {
		String marketingPrefEmailQuery = "SELECT TOP 1 * FROM dbo.ContractAccountNotifyEmail WHERE EmailId = '" + email
				+ "' and subject like '" + subject + "%' ORDER BY 1 desc";
		return marketingPrefEmailQuery;
	}

	/*************************************
	 *>>>>> GUEST USER PAGE QUERIES <<<<<*
	 *************************************/
	/**
	 * This method is to get the query to get the guest user invite mail content
	 * and sent status.
	 *
	 * @param emailId
	 * @param subject
	 * @return
	 */
	public static String getGuestUserInviteMailContentQuery(String emailId, String subject) {
		String inviteMailContentQuery = "SELECT TOP 1 Message, IsNotify  FROM ContractAccountNotifyEmail\r\n"
				+ "WHERE EmailId='" + emailId + "'\r\n" + "AND Subject LIKE '" + subject + "%'\r\n"
				+ "ORDER BY ID DESC";
		return inviteMailContentQuery;
	}

	/**
	 * This method is to get all the linked accounts to the user with there role
	 * id, address and address type.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getAllLinkedAccountsWithRoleId(String sUsername) {
		String sQuery = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='" + sUsername + "') "
				+ "SELECT UA.UtilityAccountNumber, UA.RoleID, C.Address1, C.AddressType  FROM  [User] U (NOLOCK) "
				+ "JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID "
				+ "JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber WHERE U.userid=@AccountNo "
				+ "AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga WHERE AccessExpiryDate < getdate() "
				+ "AND ua.RequestID=ga.RequestID) ORDER BY UA.IsDefaultAccount DESC";
		return sQuery;
	}

	/**
	 * This method is to get RoleID, Address and AddressType of a UtilityAccount
	 * Number with the User
	 *
	 * @param sUsername
	 * @param utilityAccountNum
	 * @return
	 */
	public static String getAcountNumberDetilsWithUser(String sUsername, String utilityAccountNum) {
		String sQuery = "DECLARE @AccountNo INT=(SELECT UserId FROM [User] WHERE UserName='" + sUsername + "') "
				+ "SELECT UA.UtilityAccountNumber, UA.RoleID, C.Address1, C.AddressType  FROM  [User] U (NOLOCK) "
				+ "JOIN  UserAccount UA (NOLOCK) ON UA.UserID=U.UserID "
				+ "JOIN  CustomerInfo C (NOLOCK) ON UA.Accountnumber = C.AccountNumber WHERE U.userid=@AccountNo "
				+ "AND NOT EXISTS (SELECT 1 from GuestAccessRequest ga WHERE AccessExpiryDate < getdate() "
				+ "AND ua.RequestID=ga.RequestID) and UA.UtilityAccountNumber ='" + utilityAccountNum
				+ "' ORDER BY UA.IsDefaultAccount DESC";
		return sQuery;
	}

	/**
	 * This method is to get the user to whom email id is linked.
	 *
	 * @param sEmail
	 * @param sUsername
	 * @return
	 */
	public static String getUserToWhomEmailIdIsLinked(String sEmail, String sUsername) {
		String sQuery = "SELECT TOP 1 UserName, Password FROM [User] WHERE EmailID = '" + sEmail + "' AND Status = '1'"
				+ " AND UserName!='" + sUsername + "'";
//		System.out.println(sQuery);
		return sQuery;
	}

	/**
	 * This method is to get the encrypted password.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getEncryptedPasswordOfUser(String sUsername) {
		String sQuery = "SELECT UserName, Password FROM [User] WHERE UserName = '" + sUsername + "'";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the password for user.
	 *
	 * @param sUsername
	 * @param sPassword
	 * @return
	 */
	public static String getQueryToUpdatePasswordForUser(String sUsername, String sPassword) {
		String sQuery = "UPDATE [User]" + "SET Password = '" + sPassword + "' " + "WHERE UserName = '" + sUsername
				+ "'";
		return sQuery;
	}

	/**
	 * This method is to get the query to update the Primary Email Address for
	 * user.
	 *
	 * @param sUsername
	 * @param sEmailID
	 * @return
	 */
	public static String getQueryToUpdatePrimaryEmailAddrForUser(String sUsername, String sEmailID) {
		String sQuery = "UPDATE [User]" + "SET EmailID = '" + sEmailID + "' " + "WHERE UserName = '" + sUsername + "'";
		return sQuery;
	}

	/**
	 * This method used to update the access expiry date of the guest user
	 * invite.
	 *
	 * @param sAccountNumber
	 * @param sDate
	 * @return
	 */
	public static String getQueryToUpdateAccessExpireDate(String sAccountNumber, String sDate) {
		String sDateFormat = DateUtil.changeStringToDateInFormat(sDate, "yyyy-MM-dd", "yyyy-MM-d");
		String sQuery = "UPDATE GuestAccessRequest " + "SET AccessExpiryDate = '" + sDateFormat + " 00:00:00.000' "
				+ "WHERE UtilityAccountNumber = '" + sAccountNumber + "' AND "
				+ "RequestID = (SELECT Top 1 RequestID FROM GuestAccessRequest " + "WHERE UtilityAccountNumber = '"
				+ sAccountNumber + "' ORDER BY LastUpdated DESC)";
		return sQuery;
	}

	/**
	 * This method is to get the csp guest user configuration.
	 *
	 * @return
	 */
	public static String getCspGuestUserConfig() {
		String sQuery = "SELECT ConfigOption, ConfigValue FROM UtilityConfig WHERE "
				+ "ModuleName= 'GuestUserConfiguration'";
		return sQuery;
	}

	public static String getLoggedInUsersEmail(String sUsername) {
		String sQuery = "SELECT EmailID\n" + "FROM [User]\n" + "WHERE UserName='" + sUsername + "'";
		return sQuery;
	}

	/****************************************
	 *>>>>> ABOUT MY HOME PAGE QUERIES <<<<<*
	 ****************************************/

	/**
	 * This method is to get the Residential Accounts of a User, takes Username
	 * as input and gives all Residential Owner Accounts of this user
	 */
	public static String getResidentialOwnerAccounts(String username) {
		String sQuery = "Select CustomerAddress.UtilityAccountNumber FROM CustomerAddress join (Select [User].UserID, [UserAccount].UtilityAccountNumber FROM [UserAccount] join [User] on [UserAccount].UserID = [User].UserID  WHERE RoleID = '3' and UserName='"
				+ username
				+ "') as A on CustomerAddress.UtilityAccountNumber = A.UtilityAccountNumber where AddressType= '1'";
		return sQuery;
	}

	/**
	 * This method is to get the Business/Commercial Accounts of a User, takes
	 * Username as input and gives all Business/Commercial Owner Accounts of
	 * this user
	 */
	public static String getCommercialOwnerAccounts(String username) {
		String sQuery = "Select CustomerAddress.UtilityAccountNumber FROM CustomerAddress join (Select [User].UserID, [UserAccount].UtilityAccountNumber FROM [UserAccount] join [User] on [UserAccount].UserID = [User].UserID  WHERE RoleID = '3' and UserName='"
				+ username
				+ "') as A on CustomerAddress.UtilityAccountNumber = A.UtilityAccountNumber where AddressType= '2'";
		return sQuery;
	}

	/*********************************************
	 *>>>>> BILLING DASHBOARD PAGE QUERIES <<<<<<*
	 *********************************************/
	/**
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getOneTimePaymentDetails(String sUtilityAccountNumber) {
		String sGetOneTimePaymentDetails = "SELECT  Top 1 [User].FirstName, [User].LastName, [User].EmailID, [User].MobilePhone, "
				+ "CI.Address1, CI.CityName, CI.StateName, CI.ZipCode , BD.Value AS [RemainingBalance] FROM [User], CustomerInfo AS CI "
				+ "Inner Join Account ON CI.AccountNumber = Account.AccountNumber "
				+ "Inner Join (SELECT max(billingid) AS billingid,Accountnumber FROM Billing GROUP BY Accountnumber) AS [Extent1] "
				+ "ON Account.Accountnumber=[Extent1].Accountnumber "
				+ "INNER JOIN [dbo].[billingdetail] AS BD ON [Extent1].billingId=BD.billingId "
				+ "WHERE BD.Headid=24 And CI.UtilityAccountNumber = '" + sUtilityAccountNumber + "' "
				+ "AND [User].UserID=(SELECT UserID FROM [UserAccount]  WHERE UtilityAccountNumber = '"
				+ sUtilityAccountNumber + "'" + " and RoleID=" + "'" + "3" + "'" + ")";
		return sGetOneTimePaymentDetails;
	}

	/**
	 * This method used to get the query to get the details on utility bill
	 * page.
	 *
	 * @param UtilityAccountNumber
	 * @param sLanguage
	 * @return
	 */
	public static String getDetailsUtilityBillPage(String UtilityAccountNumber, String sLanguage) {
		String sDetailsUtilityBillPage = "Declare @utilityaccountnumber VARCHAR(100) = '" + UtilityAccountNumber + "', "
				+ "@LanguageCode VARCHAR(10) = '" + sLanguage + "', \n"
				+ "@BillingDate DATETIME = NULL DECLARE @BillingId INT, @FromDate DATE, @ToDate DATE, \n"
				+ "@DefaultPaymentType BIT, @LastRechargeAmount NUMERIC(12, 2), @CustomerId INT, @AccountNumber INT \n"
				+ "SELECT TOP 1 @CustomerId = CA.CustomerId, @DefaultPaymentType = DefaultPaymentType                 \n"
				+ "FROM CustomerAddress CA WITH(NOLOCK) JOIN Account A WITH(NOLOCK) ON(A.AddressId = CA.AddressId)\n"
				+ "WHERE A.AccountNumber = @AccountNumber \n" + "SELECT @AccountNumber = AccountNumber \n"
				+ "FROM account \n" + "WHERE utilityaccountnumber = @utilityaccountnumber\n"
				+ "IF @BillingDate IS NULL \n"
				+ "SELECT TOP 1 @BillingId = BillingId, @FromDate = PeriodFrom, @ToDate = PeriodTo \n"
				+ "FROM Billing WITH (NOLOCK) \n" + "WHERE AccountNumber = @AccountNumber ORDER BY BillingDate DESC \n"
				+ "ELSE \n" + "SELECT TOP 1 @BillingId = BillingId, @FromDate = PeriodFrom, @ToDate = PeriodTo \n"
				+ "FROM Billing WITH (NOLOCK) \n"
				+ "WHERE AccountNumber = @AccountNumber AND BillingDate = @BillingDate \n"
				+ "BEGIN SELECT TOP 1 @LastRechargeAmount = TransactionAmount \n"
				+ "FROM BillingTransaction WITH(NOLOCK) \n"
				+ "WHERE AccountNumber = @AccountNumber AND IsPrepay = 1 ORDER BY TransactionDate DESC \n"
				+ "SELECT dbo.getMultilingualMessage(BHM.HeadControlId, @LanguageCode, 'N', DEFAULT) AS [HeaderName], \n"
				+ "CASE WHEN BHM.HeadId = 24 AND ISNUMERIC(BD.Value) = 1 AND CAST(BD.Value AS NUMERIC(13,2)) < 0 \n"
				+ "AND BHM.HeadId = 24 AND @DefaultPaymentType = 1 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(13,2))) AS VARCHAR(12)) + ' CR' \n"
				+ "WHEN BHM.HeadId = 24 AND ISNUMERIC(BD.Value) = 1 AND CAST(BD.Value AS NUMERIC(13,2)) > 0 \n"
				+ "AND BHM.HeadId = 24 AND @DefaultPaymentType = 1 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(13,2))) AS VARCHAR(12)) \n"
				+ "WHEN BHM.HeadId = 5 THEN CAST(ABS(CAST((CASE WHEN CHARINDEX('E',BD.Value) > 0 THEN CAST(BD.Value AS FLOAT) \n"
				+ "ELSE BD.Value END) AS NUMERIC(15,2))) AS VARCHAR(12)) + ' kWh' \n"
				+ "WHEN BHM.HeadId = 8 THEN cast(cast(BD.Value as numeric(10,2)) as varchar) + ' HCF' \n"
				+ "WHEN BHM.HeadId = 11 THEN CAST(ABS(CAST(BD.Value AS NUMERIC(15,2))) AS VARCHAR(12)) + ' CCF' \n"
				+ "WHEN BHM.HeadId in (9, 19, 20, 22, 23, 24, 6) THEN cast(cast(BD.Value as decimal(10,2)) as varchar) \n"
				+ "ELSE BD.Value END Value, BHM.ColorCode \n" + "FROM BillingHeadMaster BHM WITH (NOLOCK) \n"
				+ "LEFT OUTER JOIN BillingDetail BD WITH (NOLOCK) ON (BD.HeadId = BHM.HeadId AND BD.BillingId = @BillingId) \n"
				+ "WHERE BHM.IsActive = 1 AND (BD.Value IS NOT NULL OR BHM.HeaderType = 1) AND BHM.IsPDFHead = 0 \n"
				+ "AND (BHM.Section IN (SELECT FeatureName \n" + "FROM FeatureSettings FS WITH (NOLOCK) \n"
				+ "WHERE FS.[Status] = 1) or BHM.Section = 'BillDetails') ORDER BY BHM.SortOrder END\n";

		return sDetailsUtilityBillPage;
	}

	/**
	 * This method is to get the payments details query.
	 *
	 * @param sAccountNo
	 * @return
	 */
	public static String getPaymentsDetailsQuery(String sAccountNo) {
		String sQuery = null;
		sQuery = "SELECT TOP 1 * FROM Payments\n" + "WHERE ServiceAccountNumber = '" + sAccountNo + "'\n"
				+ "ORDER BY 1 DESC";
//		System.out.println(sQuery);
		return sQuery;
	}

	/**
	 * This method is to get the payment extension eligibility of an account  query.
	 *
	 * @return
	 */
	public static String getPaymentExtension(String sUtilityAccountNumber) {
		String sQuery = "declare @utilityaccountnumber varchar(40) ='" + sUtilityAccountNumber + "'"

				+ " select distinct a.AccountNumber, b.BillingDate, bd.value ,a.utilityaccountnumber from billing b "
				+ " inner join billingdetail bd on b.billingid=bd.billingid "
				+ "inner join account a on a.accountnumber=b.accountnumber "
				+ "inner join accountaveragebilling ab on ab.accountnumber=a.accountnumber "
				+ "where bd.BillingId in (Select Top 1 BillingId from Billing where AccountNumber in "
				+ "(Select distinct accountNumber from account where UtilityAccountNumber =  @utilityaccountnumber) order By BillingDate Desc) and "
				+ "bd.headid=24 and convert(varchar,getdate(),1)<(select bd.value from billingdetail bd where bd.billingid in (Select Top 1 BillingId from Billing where AccountNumber in "
				+ "(Select distinct accountNumber from account where UtilityAccountNumber =  @utilityaccountnumber) order by billingdate desc)and bd.headid=25)";
		return sQuery;
	}

	/*****************************************************************************************
	 * BILLING PAYMENT PAGE QUERIES *
	 *****************************************************************************************/
	public static String getLatestPaymentProfile(String sAccountNumber) {
		String sQuery = "SELECT TOP 1 CustomerRefNum, PaymentType, CCAccountNumber, BankAccountNumber, "
				+ "CcExp, PaymentType, FirstName, ProfileStatus, BankRouting, BankName \n" + "FROM PaymentProfiles\n"
				+ "WHERE ServiceAccountNumber = '" + sAccountNumber + "'\n" + "ORDER BY PaymentProfileId DESC";
		return sQuery;
	}

	/************************************
	 *>>>>>> PAYMENT PAGE QUERIES <<<<<<*
	 ************************************/

	/******************************************
	 *>>>> RECURRING PAYMENT PAGE QUERIES <<<<*
	 ******************************************/
	/**
	 * This method is to get the query to get the non ami meters count linked to
	 * the given utility account number.
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getQueryCheckAccEnrollForAutoPay(String sUtilityAccountNumber) {
		String sAccEnrollForAutoPay = "Select COUNT(*) AS AutoPayEnrolledCount FROM AccountRecurringPayment WHERE "
				+ "AccountNumber=(SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ sUtilityAccountNumber + "')";
		return sAccEnrollForAutoPay;
	}

	/**
	 * This method is to get the query to get the accounts enrolled for Auto
	 * Pay.
	 *
	 * @param sUsername
	 * @return
	 */
	public static String getQueryAccEnrollForAutoPay(String sUsername) {
		String sAccountsEnrollForAutoPay = "SELECT UtilityAccountNumber FROM Account " + "WHERE AccountNumber "
				+ "IN (SELECT AccountNumber FROM AccountRecurringPayment " + "WHERE AccountNumber "
				+ "IN (SELECT AccountNumber FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUsername + "') "
				+ "AND RoleID in (3)))";
		return sAccountsEnrollForAutoPay;
	}

	public static String getQueryCountAccEnrolledForAutopay(String sUsername) {
		String sQuery = "SELECT COUNT (UtilityAccountNumber) AS UtilityAccountNumber FROM Account "
				+ "WHERE AccountNumber " + "IN (SELECT AccountNumber FROM AccountRecurringPayment "
				+ "WHERE AccountNumber " + "IN (SELECT AccountNumber FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] " + "WHERE UserName = '" + sUsername + "') "
				+ "AND RoleID IN (3)))";
		return sQuery;
	}

	/**
	 * This method is to get the auto pay enrollment confirmation email.
	 *
	 * @param sEmail
	 * @param sSubject
	 * @return
	 */
	public static String getAutoPayEnrollConfirmationEmail(String sEmail, String sSubject) {
		String sQuery = "SELECT TOP 1 CustomerName, EmailId, [subject], EmailMsg, CreatedDate\n"
				+ "FROM ContractAccountNotify\n" + "WHERE EmailId = '" + sEmail + "'\n" + "AND subject = '" + sSubject
				+ "'\n" + "ORDER BY ContractAccountId DESC";
		return sQuery;
	}

	/******************************************
	 *>>>>> BILLING HISTORY PAGE QUERIES <<<<<*
	 ******************************************/
	/**
	 * This method will return query to get Bill Statement of a given Utility
	 * Account
	 *
	 * @param sUtilityAccountNumber
	 * @return sQueryBillingStatements
	 */
	public static String getBillStatementQuery(String sUtilityAccountNumber) {
		String sQueryBillingStatements = "SELECT Billing.BillingDate, BillingDetail.value from BillingDetail join Billing on BillingDetail.BillingID=Billing.BillingID where BillingDetail.HeadId='22' and Billing.AccountNumber=(select AccountNumber from account where utilityAccountNumber='"
				+ sUtilityAccountNumber + "')  order by 1 desc";
		return sQueryBillingStatements;
	}

	/**
	 * This method will return query to get Payments of a given Utility Account
	 *
	 * @param sUtilityAccountNumber
	 * @return sQueryBillingStatements
	 */
	public static String getPaymentsQuery(String sUtilityAccountNumber) {
		String sQueryPayments = "SELECT TransactionDate, TransactionAmount FROM BillingTransaction where accountnumber=(select AccountNumber from account where utilityAccountNumber='"
				+ sUtilityAccountNumber + "' and TransactionStatus ='1')  order by 1 desc";
		return sQueryPayments;
	}

	/*****************************************************************************************
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>> BUDGET BILL PAGE QUERIES
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< *
	 *****************************************************************************************/
	/**
	 * This method is to get the query to get the non ami meters count linked to
	 * the given utility account number.
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getNonAMIMetersCountLinkedToAccount(String sUtilityAccountNumber) {
		String sNonAMIMeterCounts = "SELECT COUNT(*) AS NonAMIMeterCount FROM AccountMeterMapping WHERE "
				+ "AccountNumber=(SELECT AccountNumber FROM Account WHERE UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') AND ISAMI=0";
		return sNonAMIMeterCounts;
	}

	/**
	 * This method is to get the maximum monthly budget limit for residential
	 * and commercial accounts.
	 *
	 * @return
	 */
	public static String getMaxMonthlyBudgetLimit() {
		String sQuery = "SELECT MonthlyBudgetMaxLimit, IMonthlyBudgetMaxLimit FROM UtilitySettings";
		return sQuery;
	}

	/**
	 * This method is to get the Budget Bill for a service account
	 *
	 * @return
	 */
	public static String getMyBudgetForAccount(String serviceAccount) {
		String sQuery = "select top 12 * from budgetbill where accountnumber=(Select distinct accountNumber from account where UtilityAccountNumber = '"+serviceAccount+"') order by 1 desc";
		return sQuery;
	}

	/*****************************************************************************************
	 * RATE ANALYSIS PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * LEVEL PAY PAGE QUERIES *
	 *****************************************************************************************/
	public static String getLevelPayStatus(String utilityAccountNumber) {
		String sQuery = "select RecurringPayStatus from Account where UtilityAccountNumber='" + utilityAccountNumber
				+ "'";
		return sQuery;
	}

	public static String getTwelveMonthBillHistoryForLevelPay(String utilityAccountNumber) {
		String sQuery = "Select value from BillingDetail where BillingId in (Select top 12 BillingId from Billing where AccountNumber = (select distinct a.AccountNumber from billing b\r\n"
				+ "inner join billingdetail bd on b.billingid=bd.billingid\r\n"
				+ "inner join account a on a.accountnumber=b.accountnumber\r\n"
				//+ "inner join accountaveragebilling ab on ab.accountnumber=a.accountnumber\r\n"
				+ "where bd.BillingId in (Select Top 1 BillingId from Billing where AccountNumber in \r\n"
				+ "(Select distinct accountNumber from account where UtilityAccountNumber = '" + utilityAccountNumber
				+ "') order By BillingDate Desc) and\r\n" + " bd.headid=24) order By 1 Desc) And HeadId = 19";
		return sQuery;
	}

	public static String getCurrentDueAmountForLevelPay(String utilityAccountNumber) {
		String sQuery = "select distinct a.AccountNumber, b.BillingDate, bd.value ,a.utilityaccountnumber from billing b\r\n"
				+ "inner join billingdetail bd on b.billingid=bd.billingid\r\n"
				+ "inner join account a on a.accountnumber=b.accountnumber\r\n"
				+ "inner join accountaveragebilling ab on ab.accountnumber=a.accountnumber\r\n"
				+ "where bd.BillingId in (Select Top 1 BillingId from Billing where AccountNumber in \r\n"
				+ "(Select distinct accountNumber from account where UtilityAccountNumber = '" + utilityAccountNumber
				+ "') order By BillingDate Desc) and\r\n" + " bd.headid=24 ";
		return sQuery;
	}

	public static String getLevelPayEnrolmentStatus(String utilityAccountNumber) {
		String sQuery = "Select * from accountaveragebilling where AccountNumber in \r\n"
				+ "(Select distinct accountNumber from account where UtilityAccountNumber = '" + utilityAccountNumber
				+ "')";
		return sQuery;
	}

	/**
	 * If this query returns Result set and Value column give Zero or less
	 * amount then account is Eligible for level pay
	 */
	public static String getAccountEligibleLevelPayOrNot(String utilityAccountNumber) {
		String sQuery = "select distinct a.AccountNumber, b.BillingDate, bd.value ,a.utilityaccountnumber from billing b\r\n"
				+ "inner join billingdetail bd on b.billingid=bd.billingid\r\n"
				+ "inner join account a on a.accountnumber=b.accountnumber\r\n"
				+ "inner join accountaveragebilling ab on ab.accountnumber=a.accountnumber\r\n"
				+ "where bd.BillingId in (Select Top 1 BillingId from Billing where AccountNumber in \r\n"
				+ "(Select distinct accountNumber from account where UtilityAccountNumber = '" + utilityAccountNumber
				+ "') order By BillingDate Desc) and\r\n" + " bd.headid=24";
		return sQuery;
	}

	/*****************************************************************************************
	 * PAYMENT LOCATION QUERIES *
	 *****************************************************************************************/
	public static String getPaymentLocationListQuery() {
		String sQueryPaymentLocationList = "SELECT LocationName, Address1, CityName, PaymentLocWebsite, PaymentToDay, PayTimeFrom, PayTimeTo, ContactNo, Emailid FROM PaymentLocation LEFT JOIN CityMaster ON PaymentLocation.CityID = CityMaster.CityID where IsDeleted='0'and CityName != 'null'";
		return sQueryPaymentLocationList;
	}

	/*****************************************************************************************
	 * USAGE PAY PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * This query method is to get all meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003
	 *
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getAllMeterTypesCount(String sUtilityAccountNumber, String Username) {
		String sAllMeterTypeCount = "SELECT Metertype, count(metertype) AS MeterCount " + "FROM accountmetermapping "
				+ "WHERE accountnumber = (SELECT accountnumber " + "FROM UserAccount " + "WHERE UtilityAccountNumber ='"
				+ sUtilityAccountNumber + "' " + "AND UserID = (SELECT UserID FROM [User] WHERE UserName='" + Username
				+ "')) and Status !=0 " + "GROUP BY metertype";
		return sAllMeterTypeCount;
	}

	/**
	 * This query method is to get specific meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @param iMeterStatus
	 * @return
	 */
	public static String getCountSpecificMeterForAccountQuery(String sUtilityAccountNumber, String sMeterType,
															  int iMeterStatus) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "' and IsAMI=" + iMeterStatus
				+ " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter counts for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @return
	 */
	public static String getCountAllMetersForAccountQuery(String sUtilityAccountNumber, String sMeterType) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "'" + ") and MeterType='" + sMeterType + "'" + " and Status !=0";
		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter names for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1)
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @return
	 */
	public static String getUtilityAllMeterNameQuery(String sUtilityAccountNumber, String sMeterType) {
		String sQuerySpecificMeters = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "'" + " and Status='1'";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get all the meter names for a utillity account
	 * sUtilityAccountNumber= C002002003 sMeterType = P, W, G, PV(Power, Water,
	 * Gas, Solar) iMeterStatus = 0,1(Non AMI-0, AMI-1).
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterType
	 * @param iMeterStatus
	 * @return
	 */
	public static String getUtilitySpecificMeterNameQuery(String sUtilityAccountNumber, String sMeterType,
														  int iMeterStatus) {

		String sQuerySpecificMeters = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='" + sMeterType + "' and IsAMI=" + iMeterStatus
				+ " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 * This query method is to get the 1st Solar meter name for a utillity
	 * account sUtilityAccountNumber= C002002003.
	 *
	 * @param sUtilityAccountNumber
	 */
	public static String getUtilitySolarMeterNameQuery(String sUtilityAccountNumber) {
		String sQuerySpecificMeters = ";"
				+ "SELECT Top 1 MeterNumber FROM AccountMeterMapping WHERE AccountNumber=(Select AccountNumber from Account where UtilityAccountNumber='"
				+ sUtilityAccountNumber + "') and MeterType='PV'" + " and Status !=0";

		return sQuerySpecificMeters;
	}

	/**
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param sTableName
	 * @param iCspConfiguredMonths
	 * @return
	 */
	public static String getQueryCountDataAvailableUsageMonths(String sUtilityAccountNumber, String sMeterNumer,
															   String sTableName, int iCspConfiguredMonths) {

		String sCountDataAvailableUsageMonths = ";" + "declare @utilityaccountnumber varchar(100) ='"
				+ sUtilityAccountNumber + "' " + ",@MeterNumber varchar(100)='" + sMeterNumer + "' "
				+ ",@AccountNumber bigint " + ",@Meterid bigint "

				+ "select @AccountNumber=AccountNumber from account where utilityaccountnumber=@utilityaccountnumber "
				+ "select @Meterid=Meterid from AccountMeterMapping "
				+ "where AccountNumber = (select AccountNumber from account where utilityaccountnumber=@utilityaccountnumber) "
				+ "and MeterNumber=@MeterNumber "

				+ "select count(1) from " + sTableName
				+ " where AccountNumber=@AccountNumber and Meterid=@Meterid and usagedate>=dateadd(mm,-"
				+ iCspConfiguredMonths + ",getdate()) and rateplandetailid=1";

		return sCountDataAvailableUsageMonths;
	}

	/**
	 * This method is to get the monthly usage data
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iMonthConfigured
	 * @return
	 */
	/*
	 * public static String getMonthlyUsageForUtillityMeters(String sTableName,
	 * String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
	 * String sMeter = null; if (sMeterNumer == null) { sMeter = null; } else {
	 * sMeter = "'" + sMeterNumer + "'"; } String sMonthlyUsageQuery = ";" +
	 * "DECLARE @UtilityAccountNumber VARCHAR(100) = '" + sUtilityAccountNumber
	 * + "'" + ",@MeterNumber VARCHAR(100) = " + sMeter +
	 * " DECLARE @FromDate DATE " + ",@ToDate DATE " + ",@IsAMI TINYINT " +
	 * "IF @MeterNumber IS NULL " + "BEGIN " +
	 * "SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0)) "
	 * + "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "IF ISNULL(@MeterNumber, '') <> '' " + "BEGIN " +
	 * "SELECT @IsAMI = ISAMI " + "FROM account a " +
	 * "JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber " +
	 * "WHERE a.UtilityAccountNumber = @UtilityAccountNumber " +
	 * "	AND amm.meternumber = @MeterNumber " + "IF @IsAMI = 0 " + "BEGIN " +
	 * "SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0)) "
	 * + "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "IF @IsAMI = 1 " + "BEGIN " +
	 * "SET @ToDate = DATEADD(month, DATEDIFF(month, 0, getdate()), 0) " +
	 * "SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+1, @ToDate) " +
	 * "END " + "END " +
	 * "SELECT DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [UsageMonth] "
	 * + ", sum(P.Value) AS kWhValue " + "	,SUM(p.Consumed) AS DollarValue " +
	 * "FROM account a " +
	 * "JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber " +
	 * "JOIN " + sTableName + " p ON a.AccountNumber = p.AccountNumber" +
	 * "	AND amm.meterid = p.meterid " +
	 * "JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID " +
	 * "WHERE a.UtilityAccountNumber = @UtilityAccountNumber " +
	 * "AND RPD.UsageType='Usage' " +
	 * "AND amm.meternumber = isnull(@MeterNumber, amm.meternumber) " +
	 * "AND amm.STATUS = 1 " + "AND ( " + "		p.UsageDate >= @FromDate " +
	 * "AND p.UsageDate <= @ToDate ) " + "GROUP BY UtilityAccountNumber " +
	 * "	,p.UsageDate ORDER BY 1;"; return sMonthlyUsageQuery; }
	 */

	public static String getMonthlyUsageForUtillityMeters(String sTableName, String sMeterType,
														  String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sMonthlyUsageQuery = ";" +

				"; DECLARE @UtilityAccountNumber VARCHAR(100) = '" + sUtilityAccountNumber
				+ "', @MeterNumber VARCHAR(100) = " + sMeter
				+ " DECLARE @FromDate DATE ,@ToDate DATE, @IsAMI TINYINT, @month TinyInt"
				+ " IF @MeterNumber IS NULL BEGIN"
				+ " if Exists (Select 1 from Account A Inner join AccountMeterMapping AMM on A.AccountNumber=AMM.AccountNumber where"
				+ " A.UtilityAccountNumber=@UtilityAccountNumber and isami=0 and MeterType='" + sMeterType + "')"
				+ " Begin set @month=1 End Else  Set @month=0"
				+ " SET @ToDate = DATEADD(MM,  -@month, DATEADD(month, DATEDIFF(month, 0, getdate()), 0))"
				+ " SET @FromDate = DATEADD(MM, -" + iMonthConfigured + "+ 1, @ToDate) END"
				+ " IF ISNULL(@MeterNumber, '') <> ''" + " BEGIN SELECT @IsAMI = ISAMI FROM account a "
				+ " JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber"
				+ " WHERE a.UtilityAccountNumber = @UtilityAccountNumber AND amm.meternumber = @MeterNumber"
				+ " IF @IsAMI = 0  BEGIN"
				+ " SET @ToDate = DATEADD(MM, - 1, DATEADD(month, DATEDIFF(month, 0, getdate()), 0))"
				+ " SET @FromDate = DATEADD(MM, - " + iMonthConfigured + "+ 1, @ToDate) END" + " IF @IsAMI = 1  BEGIN"
				+ " SET @ToDate = DATEADD(month, DATEDIFF(month, 0, getdate()), 0)" + " SET @FromDate = DATEADD(MM, -"
				+ iMonthConfigured + " + 1, @ToDate) END END"
				+ " SELECT DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [UsageMonth]"
				+ " ,sum(P.Value) AS kWhValue ,SUM(p.Consumed) AS DollarValue "
				+ " FROM account a JOIN AccountMeterMapping amm ON a.AccountNumber = amm.AccountNumber" + " JOIN "
				+ sTableName + " p ON a.AccountNumber = p.AccountNumber"
				+ " AND amm.meterid = p.meterid JOIN RatePlanDetail RPD ON P.RatePlanDetailID = RPD.RatePlanDetailID"
				+ " WHERE a.UtilityAccountNumber = @UtilityAccountNumber "
				+ " AND RPD.UsageType = 'Usage' AND amm.meternumber = isnull(@MeterNumber, amm.meternumber) "
				+ " AND amm.STATUS = 1  AND (p.UsageDate >= @FromDate AND p.UsageDate <= @ToDate)"
				+ " GROUP BY UtilityAccountNumber, p.UsageDate ORDER BY 1;";
		return sMonthlyUsageQuery;
	}

	/**
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iMonthConfigured
	 * @return
	 */
	public static String getSeasonalUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
														   String sMeterNumer, int iMonthConfigured) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSeasonUsageQuery = ";" + "DECLARE   @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ " ,@MeterNumber varchar(100)= " + sMeter + " ,@FromDate        DATETIME "
				+ " ,@ToDate            DATETIME "

				+ " IF  (Month(getdate()) in (12,1,2)) " + " BEGIN "
				+ " SET @ToDate = CONVERT(VARCHAR(4),YEAR(GETDATE())) + '-' + '03' + '-' + '01' "
				+ " SET @FromDate= DATEADD(YEAR,-1,@ToDate); " + " END      " + "IF  (Month(getdate()) in (3,4,5)) "
				+ " BEGIN" + "   SET @ToDate = CONVERT(VARCHAR(4),YEAR(GETDATE())) + '-' + '06' + '-' + '01'  "
				+ "  SET @FromDate= DATEADD(YEAR,-1,@ToDate); " + " END   " + " IF  (Month(getdate()) in (6,7,8)) "
				+ " BEGIN " + "  SET @ToDate = CONVERT(VARCHAR(4),YEAR(GETDATE())) + '-' + '09' + '-' + '01'   "
				+ "  SET @FromDate= DATEADD(YEAR,-1,@ToDate); " + " END " + " else " + " BEGIN "
				+ "  SET @ToDate = CONVERT(VARCHAR(4),YEAR(GETDATE())) + '-' + '12' + '-' + '01'  "
				+ " SET @FromDate= DATEADD(YEAR,-1,@ToDate); " + " END "

				+ " SELECT SeasonName, sum(P.Value) "
				+ " kWh, sum(p.Consumed) DollarValue from account a join AccountMeterMapping "
				+ " amm on a.AccountNumber=amm.AccountNumber join " + sTableName + " p on "
				+ " a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid join "
				+ " SeasonDuration sd on sd.seasonmonth=datepart(MM,p.Usagedate) "
				+ " JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ " join SeasonMaster sm on sm.seasonid=sd.seasonid where a.UtilityAccountNumber=@UtilityAccountNumber AND RPD.UsageType= "
				+ " 'Usage' and amm.meternumber=isnull(@MeterNumber,amm.meternumber) and amm.Status=1 and p.UsageDate>=@FromDate "
				+ " and p.UsageDate< @ToDate group by UtilityAccountNumber, SeasonName order by 1";
//		System.out.println(sSeasonUsageQuery);
		return sSeasonUsageQuery;
	}

	/*
	 * public static String getSeasonalUsageForUtillityMeters(String sTableName,
	 * String sUtilityAccountNumber, String sMeterNumer, int iMonthConfigured) {
	 * String sMeter = null; if (sMeterNumer == null) { sMeter = null; } else {
	 * sMeter = "'" + sMeterNumer + "'"; } String sSeasonUsageQuery = ";" +
	 * "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber +
	 * "'" + ",@MeterNumber varchar(100)=" + sMeter +
	 * " SELECT SeasonName, sum(P.Value) " +
	 * " kWh, sum(p.Consumed) DollarValue from account a join AccountMeterMapping "
	 * + " amm on a.AccountNumber=amm.AccountNumber join " + sTableName +
	 * " p on " +
	 * " a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid join " +
	 * "  SeasonDuration sd on sd.seasonmonth=datepart(MM,p.Usagedate) " +
	 * "   JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
	 * +
	 * "   join SeasonMaster sm on sm.seasonid=sd.seasonid where a.UtilityAccountNumber=@UtilityAccountNumber AND RPD.UsageType= "
	 * +
	 * " 'Usage' and amm.meternumber=isnull(@MeterNumber,amm.meternumber) and amm.Status=1 and p.UsageDate>=dateadd(mm,-"
	 * + iMonthConfigured +
	 * " ,getdate()) and p.UsageDate< getdate() group by UtilityAccountNumber, SeasonName order by 1"
	 * ; // System.out.println(sSeasonUsageQuery); return sSeasonUsageQuery; }
	 */

	/**
	 * This query is for getting the daily usage for any meter/Utility/Account.
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getDailyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
														String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sDailyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ "  ,@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber"
				+ ", p.UsageDate AS UsageDate" + ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ " on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid"
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ " and amm.meternumber=isnull(@MeterNumber,amm.meternumber)" + " and amm.Status=1"
				+ " and p.UsageDate>=dateadd(day,-31,getdate())"
				+ " group by UtilityAccountNumber, p.UsageDate order by 1,2";

		return sDailyUsageQuery;
	}

	/**
	 * This query is for getting the Hourly usage for any meter/Utility/Account
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getHourlyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
														 String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sHourlyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber" + ", p.UsageDate AS UsageDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ " on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2 ";

		return sHourlyUsageQuery;
	}

	/**
	 * This query is for getting the 15 Min usage for any meter/Utility/Account.
	 *
	 * @param sTableName
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getQuarterlyUsageForUtillityMeters(String sTableName, String sUtilityAccountNumber,
															String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sQuarterlyUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber + "'"
				+ ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber" + ", p.UsageDate AS UsageDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber " + "join " + sTableName
				+ " p  JOIN RatePlanDetail RPD ON P.RatePlanDetailID=RPD.RatePlanDetailID "
				+ "on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "AND RPD.UsageType='Usage' "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2 ";

		return sQuarterlyUsageQuery;
	}

	/**
	 * This query is to fetch the monthly solar generation for the account
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getMonthlySolarGeneration(String sUtilityAccountNumber, String sMeterNumer,
												   int iConfigMonths) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSolarMonthlyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + ",@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber "
				+ ", DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [GenerationDate]"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarMonthlyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and p.UsageDate>=dateadd(mm,-" + (iConfigMonths + 1) + ",getdate()) "
				+ " and p.UsageDate<=dateadd(mm,-1,getdate()) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1 ";

		return sSolarMonthlyUsageQuery;
	}

	/**
	 * This method gives all the daily solar generation result
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @param iDays
	 * @return
	 */
	public static String getDailySolarGeneration(String sUtilityAccountNumber, String sMeterNumer, int iDays) {

		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}
		String sSolarDailyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + " ,@MeterNumber varchar(100)=" + sMeter

				+ " select UtilityAccountNumber" + " , p.UsageDate AS GenerationDate"
				+ ", sum(P.Value) kWh, sum(p.Consumed) DollarValue " + "from account a "
				+ "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarDailyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid and amm.IsAMI=1 "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and p.UsageDate>CONVERT(DATE,dateadd(day,-" + iDays + ",getdate())) "
				+ "and p.UsageDate<CONVERT(DATE,dateadd(day,0,getdate())) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1  ";

		return sSolarDailyUsageQuery;
	}

	/**
	 * This method gives all the Hourly solar generation result
	 *
	 * @param sUtilityAccountNumber
	 * @param sMeterNumer
	 * @return
	 */
	public static String getHourlySolarGeneration(String sUtilityAccountNumber, String sMeterNumer) {
		String sMeter = null;
		if (sMeterNumer == null) {
			sMeter = null;
		} else {
			sMeter = "'" + sMeterNumer + "'";
		}

		String sSolarHourlyUsageQuery = ";" + "  declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + "   ,@MeterNumber varchar(100)=" + sMeter + " select UtilityAccountNumber "
				+ ", p.UsageDate AS GenerationDate " + ", sum(P.Value) kWh, sum(p.Consumed) DollarValue "
				+ "from account a " + "join AccountMeterMapping amm on a.AccountNumber=amm.AccountNumber "
				+ "join SolarHourlyUsage p on a.AccountNumber=p.AccountNumber and amm.meterid=p.meterid "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and amm.meternumber=isnull(@MeterNumber,amm.meternumber) " + "and amm.Status=1 "
				+ "and CONVERT(DATE,p.UsageDate)=CONVERT(DATE,GETDATE()-1) "
				+ "group by UtilityAccountNumber, p.UsageDate order by 1,2  ";

		return sSolarHourlyUsageQuery;
	}

	/*****************************************************************************************
	 * COMPARE SPENDING PAGE QUERIES *
	 *****************************************************************************************/
	/**
	 * @param sUtilityAccountNumber
	 * @return
	 */
	public static String getCompareSofarAndProjectedUsage(String sUtilityAccountNumber) {
		String sSolarHourlyUsageQuery = "SELECT * FROM "
				+ "UsageGenerationSummary where AccountNumber=(select AccountNumber from account where UtilityAccountNumber ='"
				+ sUtilityAccountNumber + "')";
		return sSolarHourlyUsageQuery;
	}

	/**
	 *
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareMePreviousCurrentUsage(String sUtilityAccountNumber, String sUsageTypeKwh,
														  String sUsageTypeDollar, int iConfiguredCspMonth) {
		String sCompareMePreviousCurrentUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='"
				+ sUtilityAccountNumber + "'"
				+ " select curr.UtilityAccountNumber,[CurrentYearMonth],[CurrentKwhValue],[CurrentDollarValue],[PreviousYearMonth],[PreviousKwhValue],[PreviousDollarValue] from "
				+ "(select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] , sum(P."
				+ sUsageTypeKwh + ") AS [CurrentKwhValue] , sum(p." + sUsageTypeDollar
				+ ") AS [CurrentDollarValue] from account a "
				+ "join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "group by UtilityAccountNumber, p.UsageDate ) curr join "
				+ "(select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [PreviousKwhValue], sum(p." + sUsageTypeDollar
				+ ") AS [PreviousDollarValue] from account a "
				+ "join AccountCompareSpending p on a.AccountNumber=p.AccountNumber where a.UtilityAccountNumber=@UtilityAccountNumber "
				+ "and p.UsageDate>=dateadd(mm,-13-" + iConfiguredCspMonth
				+ ",getdate()) and  p.UsageDate<=dateadd(mm,-1-" + iConfiguredCspMonth + ",getdate()) "
				+ " group by UtilityAccountNumber, p.UsageDate "
				+ ") pre on curr.UtilityAccountNumber=pre.UtilityAccountNumber  and month(CurrentYearMonth) =month(PreviousYearMonth)";
		return sCompareMePreviousCurrentUsageQuery;
	}

	/**
	 * This query brings zipusage data for the account and all the users in the
	 * zip.
	 *
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareZipUsage(String sUtilityAccountNumber, String sUsageTypeKwh, String sUsageTypeDollar,
											int iConfiguredCspMonth) {

		String sCompareZipUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'" + " DECLARE @ZipCode VARCHAR(20),@AddressType TINYINT"
				+ " SELECT @ZipCode=CA.ZipCode,@AddressType=CA.AddressType FROM CustomerAddress CA "
				+ "WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "

				+ "select  case when [CurrentYearMonth] is not null then  [CurrentYearMonth]"
				+ " when [CurrentYearMonth] is null then [previousYearMonth]  end as [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[ZipUsageUnit],[ZipUsageValue] "
				+ "from  ( "
				+ "select    CA.ZipCode , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar + ") AS [MyUsageValue] "
				+ "FROM CustomerAddress CA " + "JOIN Account A ON CA.AddressID=A.AddressID "
				+ "JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "where a.UtilityAccountNumber=@UtilityAccountNumber " + "and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "group by CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0)) curr full outer join "
				+ "(	select p.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") AS [ZipUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [ZipUsageValue]  "
				+ "from ZipCompareSpending p where ZipCode=@ZipCode 	and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) "
				+ "AND P.AddressType=@AddressType group by p.ZipCode,DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on curr.ZipCode=pre.ZipCode and month(CurrentYearMonth) =month(PreviousYearMonth)    order by (CurrentYearMonth)";

		return sCompareZipUsageQuery;
	}

	/**
	 * This query brings Utility Usage data for the account and all the users in
	 * the zip.
	 *
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareUtilityUsage(String sUtilityAccountNumber, String sUsageTypeKwh,
												String sUsageTypeDollar, int iConfiguredCspMonth) {

		String sCompareUtilityUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "' DECLARE @AddressType TINYINT "
				+ "SELECT @AddressType=CA.AddressType FROM CustomerAddress CA WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "

				+ "select  case when [CurrentYearMonth] is not null then  [CurrentYearMonth]"
				+ "when [CurrentYearMonth] is null then [previousYearMonth]  end as [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[UtilityUsageUnit],[UtilityUsageValue] "
				+ "from ( select     DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ ", sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar + ") AS [MyUsageValue] "
				+ " FROM CustomerAddress CA JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber  and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())" + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "      group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) " + ") curr full outer join "
				+ "(select    DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth]  " + ", sum(P."
				+ sUsageTypeKwh + ") AS [UtilityUsageUnit] , sum(p." + sUsageTypeDollar + ") AS [UtilityUsageValue]  "
				+ "     from UtilityCompareSpending  p   where p.UsageDate>=dateadd(mm,-13,getdate())  "
				+ "    AND P.AddressType=@AddressType   group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on  month(CurrentYearMonth) =month(PreviousYearMonth) order by (CurrentYearMonth)";

		return sCompareUtilityUsageQuery;
	}

	/**
	 * This query brings All Usage data for the account and all the users in the
	 * zip.
	 *
	 * @param sUtilityAccountNumber
	 * @param sUsageTypeKwh
	 * @param sUsageTypeDollar
	 * @param iConfiguredCspMonth
	 * @return
	 */
	public static String getCompareAllUsage(String sUtilityAccountNumber, String sUsageTypeKwh, String sUsageTypeDollar,
											int iConfiguredCspMonth) {

		String sCompareAllUsageQuery = ";" + "declare @UtilityAccountNumber varchar(100)='" + sUtilityAccountNumber
				+ "'"
				+ " DECLARE @ZipCode VARCHAR(20),@AddressType TINYINT SELECT @ZipCode=CA.ZipCode,@AddressType=CA.AddressType "
				+ " FROM CustomerAddress CA WHERE CA.UtilityAccountNumber=@UtilityAccountNumber "
				+ " select b.[PreviousYearMonth] as CurrentYearMonth, e.kWh as MyUsageUnit, e.DollarValue as MyUsageValue , "
				+ " dateadd(mm,-12,b.[PreviousYearMonth]) as PreviousYearMonth, e.prekWh as PreviousUsageUnit, e.preDollarValue as PreviousUsageValue, "
				+ " b.[ZipUsageUnit], b.[ZipUsageValue], c.[UtilityUsageUnit], c.[UtilityUsageValue] FROM (select [CurrentYearMonth], "
				+ " curr.kWh, curr.DollarValue, [PreviousYearMonth], pre.kWh as prekwh, pre.DollarValue AS preDollarValue from "
				+ " (select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") kWh, sum(p." + sUsageTypeDollar
				+ ") DollarValue from account a join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) group by UtilityAccountNumber, p.UsageDate "
				+ " ) curr full outer join ( select UtilityAccountNumber , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") kWh, sum(p." + sUsageTypeDollar
				+ ") DollarValue  from account a join AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ " where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-13-"
				+ iConfiguredCspMonth + ",getdate()) and p.UsageDate<=dateadd(mm,-1-" + iConfiguredCspMonth
				+ ",getdate()) " + " group by UtilityAccountNumber, p.UsageDate "
				+ " ) pre on curr.UtilityAccountNumber=pre.UtilityAccountNumber and month(CurrentYearMonth) =month(PreviousYearMonth)) e "
				+ "full outer JOIN(select [CurrentYearMonth],[MyUsageUnit],[MyUsageValue] ,[PreviousYearMonth],[ZipUsageUnit],[ZipUsageValue] "
				+ " from (select    CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ " , sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar
				+ ") AS [MyUsageValue] FROM CustomerAddress CA "
				+ "  JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber  "
				+ "  where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) "
				+ "  group by CA.ZipCode, DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) curr full outer join ( select   p.ZipCode , DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [ZipUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [ZipUsageValue]  "
				+ " from ZipCompareSpending p where ZipCode=@ZipCode and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())  "
				+ "  AND P.AddressType=@AddressType group by p.ZipCode,DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) pre on curr.ZipCode=pre.ZipCode and month(CurrentYearMonth) =month(PreviousYearMonth))b ON e.currentyearmonth = b.currentyearmonth "
				+ " full outer JOIN(select [CurrentYearMonth],[PreviousYearMonth],[MyUsageUnit],[MyUsageValue],[UtilityUsageUnit],[UtilityUsageValue] "
				+ " from ( select     DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [CurrentYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [MyUsageUnit], sum(p." + sUsageTypeDollar
				+ ") AS [MyUsageValue] FROM CustomerAddress CA "
				+ " JOIN Account A ON CA.AddressID=A.AddressID JOIN AccountCompareSpending p on a.AccountNumber=p.AccountNumber "
				+ "  where a.UtilityAccountNumber=@UtilityAccountNumber and p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate()) " + " AND p.UsageDate <= dateadd(mm, - 1, getdate()) "
				+ "  group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ " ) curr full outer join (select    DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) AS [PreviousYearMonth] "
				+ "  , sum(P." + sUsageTypeKwh + ") AS [UtilityUsageUnit] , sum(p." + sUsageTypeDollar
				+ ") AS [UtilityUsageValue]  " + " from UtilityCompareSpending  p  where p.UsageDate>=dateadd(mm,-1-"
				+ iConfiguredCspMonth + ",getdate())  AND P.AddressType=@AddressType "
				+ " group by DATEADD(MONTH, DATEDIFF(MONTH, 0, p.UsageDate), 0) "
				+ ") pre on  month(CurrentYearMonth) =month(PreviousYearMonth))c "
				+ "  ON b.previousyearmonth = c.previousyearmonth order by c.previousyearmonth;";

		return sCompareAllUsageQuery;
	}

	/*****************************************
	 *>>>>> OUTAGES PAGE QUERIES (Osp) <<<<<<*
	 *****************************************/
	/**
	 * This method is to get the outage id for given outage message.
	 *
	 * @param outageMsg
	 * @return
	 */
	public static String getOutageIdQuery(String outageMsg) {
		String sOutageIdQuery = ";" + "SELECT OutageId FROM outageNotification WHERE OutageMessage='"
				+ outageMsg + "'"
				+ " and IsResolved='0'";
		return sOutageIdQuery;
	}
	
	public static String getOutageDetails(String outageInfo) {
		String query = "select top 1 outageid, outagemessage, isplannedoutage, outagereportinfo, \n" +
				"cause, starttime, endtime \n" +
				"from outagenotification\n" +
				"where outagemessage = '"+outageInfo+"'\n" +
				"order by lastupdated desc";
		return query;
	}

	public static String getOutageDetailsToUpdate(String serviceType) {
		String query = "select top 1 outageid, outagemessage, isplannedoutage, outagereportinfo, \n" +
				"cause, starttime, endtime, servicetypeid\n" +
				"from outagenotification\n" +
				"where servicetypeid = '"+serviceType+"'\n" +
				"order by lastupdated desc";
		return query;
	}

	public static String getOutageDetailsWithOutageId(String outageId) {
		String query = "select top 1 outageid, outagemessage, isplannedoutage, outagereportinfo, \n" +
				"cause, starttime, endtime, servicetypeid\n" +
				"from outagenotification\n" +
				"where outageid = '"+outageId+"'\n" +
				"order by lastupdated desc";
		return query;
	}

	/**
	 * This method used to get the customer effected in given outage.
	 *
	 * @param outageId
	 * @return
	 */
	public static String getCustomerAffected(String outageId) {
		String sCustomerAffected = ";"
				+ "SELECT COUNT(AccountNumber) as AccountNumber from customerOutage where OutageID='"
				+ outageId + "'";
		return sCustomerAffected;
	}

	public static String getPrePopulatedOutageDetails(String userName) {
		String query = "select u.fullname, u.mobilephone, u.emailid, ua.isdefaultaccount, ua.utilityaccountnumber\n" +
				"from [user] as u\n" +
				"left join useraccount as ua on ua.userid = u.userid\n" +
				"where u.username = '"+userName+"'\n" +
				"and ua.isdefaultaccount = '1'";
		return query;
	}

	/*******************************************
	 *>>>>> CONNECT ME PAGE QUERIES (Cmp) <<<<<*
	 *******************************************/
	/**
	 * This method is to get the Service Account Number in Random
	 *
	 * @return String
	 */
	public static String getConnectMeFeatures() {
		String sServiceAccountNumber = ";"
				+ "SELECT FeatureName, Status FROM dbo.FeatureSettings WHERE FeatureName LIKE 'ConnectMe.%'";
		return sServiceAccountNumber;
	}

	/******************************************
	 *>>>>> SERVICE REQUEST PAGE QUERIES <<<<<*
	 ******************************************/
	/**
	 * This method is to get the query for fetching service request reason name
	 * from the DB.
	 *
	 * @return
	 */
	public static String getPreLoginReasonNameList() {
		String sServiceReasonName = "SELECT ReasonName FROM SRReasonMaster WHERE isActive=1 AND isPreLogin = 1";
		return sServiceReasonName;
	}

	/**
	 * This method is to get the query for fetching service request reason name
	 * from the DB.
	 *
	 * @return
	 */
	public static String getPostLoginReasonNameList() {
		String sServiceReasonName = ";" + "SELECT ReasonName FROM SRReasonMaster WHERE isActive=1";
		return sServiceReasonName;
	}

	/*******************************************
	 *>>>>> ELECTRIC VEHICLE PAGE QUERIES <<<<<*
	 *******************************************/
	/**
	 * This method is to get the EV assigned to user on given address.
	 *
	 * @param userAccount
	 * @param accountId
	 * @return
	 */
	public static String getEVAssignedToUserOnGivenAccount(String userAccount, String accountId) {
		String eVAssignedToUserOnGivenAddress = ";" + "SELECT evm.Name FROM  [dbo].[UserElectricVehicleDetail] AS UEVD"
				+ " INNER JOIN [dbo].[UserElectricVehicle] AS UEV ON UEVD.[UserEVId] = UEV.[UserEVId] AND UEV.status=1"
				+ " INNER JOIN [dbo].[ElectronicVehicleMaster] AS EVM ON UEV.[EVId] = EVM.[EVId]"
				+ " INNER JOIN [dbo].[CustomerInfo] AS CI ON UEVD.[AddressId] = CI.[AddressId]"
				+ " WHERE UEV.userid=(SELECT UserID FROM [User] WHERE UserName = '" + userAccount
				+ "') AND CI.UtilityAccountNumber='" + accountId + "' order by Name Asc";
		return eVAssignedToUserOnGivenAddress;
	}

	/**
	 * This method is to get the complete electric vehicle list.
	 *
	 * @return completeEV
	 */
	public static String getCompleteEVList() {
		String completeEV = "SELECT Name FROM dbo.ElectronicVehicleMaster where IsDeleted = 0";
		return completeEV;
	}

	/**
	 * This method is to get the complete electric vehicle list.
	 *
	 * @return completeEV
	 */
	public static String unlinkElectricVehicleForAccount(String AccountNumber, String UserName) {
		String unlinkQuery = "Update [dbo].[UserElectricVehicle] set Status=0 where AddressId=(select AddressId from [CustomerInfo] where UtilityAccountNumber='"
				+ AccountNumber + "') and UserId=(SELECT UserID FROM [User] WHERE UserName = '" + UserName + "') ";
		return unlinkQuery;
	}

	/*******************************************
	 *>>>>> CHARGING STATION PAGE QUERIES <<<<<*
	 *******************************************/
	/**
	 * This method is to get the query for fetching charging station info from
	 * the DB.
	 *
	 * @return
	 */
	public static String getChargingStationsInfo() {
		String chargingStationInfo = "SELECT LocationID, LocationName, Address1, Zipcode FROM ChargingStation "
				+ "where IsActive = 1";
		return chargingStationInfo;
	}

	/**
	 * This method is to get the query for fetching charging station info from
	 * the DB.
	 *
	 * @return
	 */
	public static String getChargingStationsNameAndRate() {
		String chargingStationInfo = "SELECT LocationName, Rate FROM ChargingStation WHERE IsActive = 1 "
				+ "ORDER BY Rate ASC";
		return chargingStationInfo;
	}

	/***************************************
	 *>>>>> NOTIFICATION PAGE QUERIES <<<<<*
	 ***************************************/
	public static String getNotificationQuery(String userId) {
		String notificationQuery = "SELECT [Filter1].[MessageId1] AS [MessageId], [Filter1].[PlaceHolderName], [Filter1].[CreatedDate1] AS [CreatedDate], [Filter1].[MessageBody] AS [MessageBody], [Filter1].[Reason] AS [Reason], [Filter1].[Subject] AS [Subject], [Filter1].[EmailId] AS [EmailId], [Filter1].[ControlText] AS [ControlText], [Filter1].[IsRead] AS [IsRead], [Filter1].[IsSaved] AS [IsSaved], [Filter1].[IsTrashed] AS [IsTrashed], [Filter1].[IsReply] AS [IsReply], CASE WHEN ([Filter1].[AttachmentId] IS NULL) THEN cast(0 as bigint) ELSE [Filter1].[AttachmentId] END AS [C1], [Filter1].[ServiceID] AS [ServiceID], [Extent7].[UtilityAccountNumber] AS [UtilityAccountNumber] FROM "
				+ "(SELECT [Extent1].[AccountNumber] AS [AccountNumber], [Extent1].[IsRead] AS [IsRead], [Extent1].[IsSaved] AS [IsSaved], [Extent1].[IsTrashed] AS [IsTrashed], [Extent1].[IsReply] AS [IsReply], [Extent1].[EmailId] AS [EmailId], [Extent1].[UserID] AS [UserID1], [Extent2].[MessageDetailId] AS [MessageDetailId1], [Extent2].[MessageId] AS [MessageId2], [Extent2].[MessageBody] AS [MessageBody], [Extent2].[CreatedDate] AS [CreatedDate1], [Extent3].[MessageId] AS [MessageId1], [Extent3].[Reason] AS [Reason], [Extent3].[Subject] AS [Subject], [Extent3].[ServiceID] AS [ServiceID], [Extent4].[AttachmentId] AS [AttachmentId], [Extent5].[PlaceHolderId] "
				+ "AS [PlaceHolderId1], [Extent6].[LanguageCode] AS [LanguageCode], [Extent6].[ControlText] AS [ControlText], [Extent5].[PlaceHolderName] FROM [dbo].[UserMessages] AS [Extent1] INNER JOIN [dbo].[MessageDetail] AS [Extent2] ON [Extent1].[MessageDetailId] = [Extent2].[MessageDetailId] INNER JOIN [dbo].[MessageMaster] AS [Extent3] ON [Extent2].[MessageId] = [Extent3].[MessageId] LEFT OUTER JOIN [dbo].[MessageAttachments] AS [Extent4] ON [Extent2].[MessageDetailId] = [Extent4].[MessageDetailID] INNER JOIN [dbo].[MessagePlaceHolders] AS [Extent5] ON [Extent3].[PlaceHolderId] = [Extent5].[PlaceHolderId] INNER JOIN [dbo]. [MultiLingualMasterLookUp] AS "
				+ "[Extent6] ON [Extent5].[LookUpID] = [Extent6].[LookUpID]) AS [Filter1] INNER JOIN [dbo].[UserAccount] AS [Extent7] ON ([Filter1].[UserID1] = [Extent7].[UserID]) AND (([Filter1].[AccountNumber] = [Extent7].[AccountNumber]) OR (([Filter1].[AccountNumber] IS NULL) AND ([Extent7].[AccountNumber] IS NULL))) WHERE ([Extent7].[UserID] =  (select UserID from [User] where username = '"
				+ userId + "')) AND ([Filter1].[LanguageCode] = 'EN')\r\n" + "";
		return notificationQuery;
	}

	/*public static String getNotificationPreferenceChangeDetails(String sUtilityAccountNumber,
																TestNotificationPreferencePage.NotificationType notificationType) {
		String sQuery = "SELECT AccountNumber, EmailORPhone, UserID FROM AccountNotificationDetail\n"
				+ "WHERE AccountNumber = (SELECT TOP 1 AccountNumber FROM UserAccount \n"
				+ "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "')\n"
				+ "AND AccountNotificationTypeID = (SELECT AccountNotificationTypeID FROM AccountNotificationType \n"
				+ "WHERE AccountNotificationType = '" + notificationType + "')\n";
		return sQuery;
	}

	public static String getCountPreferenceChangeDetails(String sUtilityAccountNumber,
														 TestNotificationPreferencePage.NotificationType notificationType) {
		String sQuery = "SELECT Count(AccountNumber) As NoRow FROM AccountNotificationDetail\n"
				+ "WHERE AccountNumber = (SELECT TOP 1 AccountNumber FROM UserAccount \n"
				+ "WHERE UtilityAccountNumber = '" + sUtilityAccountNumber + "')\n"
				+ "AND AccountNotificationTypeID = (SELECT AccountNotificationTypeID FROM AccountNotificationType \n"
				+ "WHERE AccountNotificationType = '" + notificationType + "')\n";
		return sQuery;
	}*/

	/*****************************************************************************************
	 * EFFICIENCY PAGE QUERIES *
	 *****************************************************************************************/

	/*****************************************************************************************
	 * FOOTPRINT PAGE QUERIES *
	 *****************************************************************************************/
	public static String getLocationId() {
		String category = "select Distinct LocationTypeId from Greenfootprintlocations";
		return category;
	}

	public static String getZipCodeForCategory(String category) {
		String ZipCode = "select ZipCode  from Greenfootprintlocations where LocationTypeId ='" + category + "'";
		return ZipCode;
	}

	public static String getNameOfFootPrintForZip(String zip) {
		String name = "select Name,Address1 from Greenfootprintlocations where ZipCode='" + zip + "'";
		return name;
	}

	public static String getAddressOfFootPrintForZip(String zip) {
		String address = "select Name,Address1 from Greenfootprintlocations where ZipCode='" + zip + "'";
		return address;
	}

	/*****************************************************************************************
	 * CSP RELATED SQL QUERIES *
	 *****************************************************************************************/
	// This query brings the number of Usage Months configured in CSP
	public static String sUsageMonthSettingsCspQuery = ""
			+ "select configOption, ConfigValue from utilityconfig  where configid=21";

	public static String sAllUtilitySettingsQuery = ""
			+ "SELECT * FROM UtilitySettings WITH(NOLOCK) WHERE UtilityId = 0";

	// This query brings the number of solar days to be displayed and is
	// configured in CSP
	public static String sConfiguredSolarDays = ""
			+ "SELECT SolarDays FROM UtilitySettings WITH(NOLOCK) WHERE UtilityId = 0";

	// This query brings the number of Usage Months configured in CSP
	public static String sDecimalPlaceAdminSettingsCspQuery = "" + "SELECT UptoDecimalPlaces FROM UtilitySettings";

	// This query brings the Date format settings in the CSP
	public static String sDateFormatConfigSettingsCspQuery = ""
			+ "select FormatName from MetricSystemMaster WHERE MetricSystemMaster.RID in(Select DateFormats from MetricSystemSettings)";

	// This query brings the Currency settings in the CSP
	public static String sCurrencyConfigSettingsCspQuery = ""
			+ "select DisplayName from MetricSystemMaster WHERE MetricSystemMaster.RID in(Select CurrencySymbols from MetricSystemSettings)";

	public static String sDefaultLanguageQuery = ""
			+ "SELECT LanguageDescription FROM LanguageMaster where IsDefault=1";

	public static String sConfiguredLanguageQuery = ""
			+ "SELECT LanguageDescription FROM LanguageMaster where Status=1";

	/**
	 * This method used to get query to fetch the date format as per csp config.
	 *
	 * @return
	 */
	public static String getDateFormatAsCspConfig() {
		String sDateFormatConfigSettingsCspQuery = ""
				+ "SELECT FormatName FROM MetricSystemMaster WHERE MetricSystemMaster.RID IN(SELECT DateFormats FROM MetricSystemSettings)";
		return sDateFormatConfigSettingsCspQuery;
	}
	/*	*//**
	 * This query is for getting the Payment Options Value in CSP(Like
	 * Maximum Payment Amount, Processing Fee etc.)
	 *//*
	 * public static String getCspPaymentOptionDetails(String
	 * sAccountNumber) { String sCspPaymentOption = ";" +
	 * " Select distinct case when po.AccountType=1 then '" +
	 * "Residential'" + " else 'Commercial" + "' end as AccountType," +
	 * " case when po.PaymentType=1 then '" + "Credit Card' else" +
	 * " 'Bank Account" +
	 * "' end as PaymentType, pf.FromValue, pf.ToValue," +
	 * " po.MaxAmount, pf.Charge" +
	 * " from [SCM10_S].[dbo].[Customerinfo]c (NOLOCK) join " +
	 * " [SCM10_S_API_Payment].[dbo].[PaymentConfigurationSettingOptions] po (NOLOCK) "
	 * +
	 * "on c.AddressTypeid=po.AccountType and  po.VendorConfigurationId = 1 "
	 * +
	 * "join  [SCM10_S_API_Payment].[dbo].[PaymentConfigurationConvenienceFees] pf (NOLOCK) "
	 * + "on po.id=pf.ConfigurationId and pf.isactive=1 " +
	 * "where c.utilityaccountnumber in ('" + sAccountNumber + "')";
	 * return sCspPaymentOption; }
	 */

	/**
	 * This query is for getting the Payment Options Value in CSP(Like Maximum
	 * Payment Amount, Processing Fee etc.)
	 */
	public static String getCspPaymentOptionDetails(String sPaymentProvider, String sAccountState,
													String sAccountNumber) {
		String sCspPaymentOption = ";" + " declare @AccountState varchar(20)='" + sAccountState + "'"
				+ " declare @PaymentProvider varchar(20)='" + sPaymentProvider + "'"
				+ " declare @utilityaccountnumber varchar(40)='" + sAccountNumber + "'"
				+ "      Select distinct case when po.AccountType=1 then '" + "Residential'"
				+ " else 'Commercial' end as AccountType, "
				+ " case when po.PaymentType=1 then 'Credit Card' else 'Bank Account' end as PaymentType, pf.FromValue, "
				+ " pf.ToValue, po.MaxAmount, pf.Charge from [SCM10_S].[dbo].[Customerinfo]c "
				+ " (NOLOCK)  join  [SCM10_S_API_Payment].[dbo].[PaymentConfigurationSettingOptions] po (NOLOCK) "
				+ " on c.AddressTypeid=po.AccountType "
				+ " left Join  [SCM10_S_API_Payment].[dbo].[paymentVendorUtilityConfigurations] pvu on po.vendorconfigurationid=pvu.id "
				+ " left join  [SCM10_S_API_Payment].[dbo].[vendors] v on pvu.vendorid=v.id "
				+ " left join  [SCM10_S_API_Payment].[dbo].[PaymentConfigurationConvenienceFees] pf (NOLOCK) "
				+ " on po.id=pf.ConfigurationId and pf.isactive=1 where c.utilityaccountnumber in (@utilityaccountnumber) "
				+ " and pvu.Accountstatename in(@AccountState) and v.paymentprovider in (@PaymentProvider)";
		return sCspPaymentOption;
	}

	/**
	 * This method brings the sql query for Date Format, Currency, Temperature,
	 * Time format and Time Zone settings
	 *
	 * @return
	 */
	public static String getDateFormatMetricSettings() {
		String sDateFormatMetricSettings = ""
				+ "select 'Time Zone' AS Name ,'('+UTCOffset+') '+TimeZoneName as DisplayName from [TimeZone] TZ "
				+ " JOIN MetricSystemSettings MSS ON TZ.TimeZoneID=MSS.TimeZoneID UNION "
				+ "select 'Date format' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.dateformats UNION "
				+ "select 'Time format' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.Timeformats UNION "
				+ "select 'Temprature' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.Tempratureormats UNION "
				+ "select 'Currency' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.CurrencySymbols "
				+ "UNION select 'Distance' AS Name ,DisplayName from MetricSystemMaster MS "
				+ "JOIN MetricSystemSettings MSS ON MS.RID=MSS.DistanceFormats ";
		return sDateFormatMetricSettings;
	}

	/**
	 * This query will fetch the configured value for an option
	 *
	 * @param sModuleName
	 *            = Compare
	 * @param sConfigOption
	 *            = Current Usage Colour in CSP
	 * @return #00f2ef
	 */
	public static String getConfigValue(String sModuleName, String sConfigOption) {
		String sConfiguredOption = ";" + "select ConfigOption, ConfigValue from utilityconfig where ModuleName = '"
				+ sModuleName + "'" + " and ConfigOption ='" + sConfigOption + "'";
		return sConfiguredOption;
	}

	/**
	 * This query will fetch the configured value for an option
	 *
	 * @param sModuleName
	 *            = Compare = Current Usage Colour in CSP
	 * @return #00f2ef
	 */
	public static String getConfigValue(String sModuleName) {
		String sConfiguredOption = ";" + "select ConfigOption, ConfigValue from utilityconfig where ModuleName = '"
				+ sModuleName + "'";
		return sConfiguredOption;
	}

	/**
	 * This query will validate the module configured or not in CSP
	 *
	 * @return 1 = Available, 0- Not available
	 */
	public static String getFeatureConfigured(String sFeatureName) {
		String sFeatureStatus = ";" + "SELECT Status FROM dbo.FeatureSettings WHERE FeatureName ='" + sFeatureName
				+ "'";
		return sFeatureStatus;
	}

	/**
	 * This query will validate the SCP module configured or not in CSP
	 *
	 * @return 1 = Available, 0 - Not available
	 */
	public static String getAllFeatureConfigured() {
		String sFeatureStatus = ";" + "SELECT FeatureName, Status FROM dbo.FeatureSettings";
		return sFeatureStatus;
	}

	/**
	 * This query will validate the CSP module configured or not in CSP
	 *
	 * @return 1 = Available, 0 - Not available
	 */
	public static String getAllCspModules() {
		String sCspModules = ";" + "SELECT RightName, IsActive FROM [right] WHERE ParentID=0";
		return sCspModules;
	}

	public static String getUserCount(String sOperator, int iStatus) {
		String sGetUserCount = ";"
				+ "select  count(AccountNumber) as AccountNumber from Account WHERE AccountNumber NOT IN (-1,2) AND Status"
				+ sOperator + "'" + iStatus + "'";
		return sGetUserCount;
	}

	public static String getUtilityAccountNo(String sOperator, int iStatus) {
		String sUtilityNumber = ";"
				+ "Select utilityAccountnumber from Account where AccountNumber not in (-1,2) and status" + sOperator
				+ "'" + iStatus + "'";
		return sUtilityNumber;
	}

	public static String getUserDetails(String sOperator, int iStatus, String sUtilityAccountNumber) {
		String sUserDetails = ";"
				+ "SELECT * FROM Customer FULL OUTER JOIN CustomerAddress ON Customer.customerid = CustomerAddress.customerid where CustomerAddress.AddressID=(select AddressId from Account Where AccountNumber Not in (-1,2) and Status"
				+ sOperator + "'" + iStatus + "'" + "and utilityAccountNumber='" + sUtilityAccountNumber + "')";
		return sUserDetails;
	}

	public static String getUserMeterNumber(String sOperator, int iStatus, String sUtilityAccountNumber) {
		String sUserDetails = ";" + "SELECT meternumber FROM AccountMeterMapping WHERE AccountNumber='"
				+ sUtilityAccountNumber + "'";
		return sUserDetails;
	}

	public static String getUserStatus(String sUtilityAccountNumber) {
		String sUserDetails = ";" + "select status from Account Where AccountNumber Not in (-1,2)"
				+ "and utilityAccountNumber='" + sUtilityAccountNumber + "'";
		return sUserDetails;
	}

	public static String getUserIsLockStatus(String sUtilityAccountNumber) {
		String sUserIsLockStatus = ";"
				+ "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].UtilityAccountNumber='"
				+ sUtilityAccountNumber + "'";
		return sUserIsLockStatus;
	}

	public static String getUserMessage(String reason) {
		String sUserIsLockStatus = ";" + "Select * from MessageMaster where Reason='" + reason
				+ "'order by messageID desc";
		return sUserIsLockStatus;
	}

	public static String getUserDetailsFromDB() {
		String sUserDetails = ";"
				+ "SELECT Top 1 c.FirstName,c.LastName,c.CustomerID,c.MobilePhone,c.DrivingLicence,C.customerNo,CA.UtilityAccountNumber AS UtilityAccountNumber ,MAX(AMM.MeterNumber) AS MeterNumber,CA.ZipCode,CA.Address1,C.SSNNumber "
				+ "  ,(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) AS CustomerType " + "  "
				+ "FROM Customer c(NOLOCK)   JOIN CustomerAddress CA(NOLOCK) ON c.CustomerID=ca.CustomerID "
				+ "JOIN Account a(NOLOCK) ON ca.AddressID=a.AddressID "
				+ "JOIN AccountMeterMapping AMM(NOLOCK) ON A.AccountNumber=AMM.AccountNumber " + "LEFT JOIN  " + "( "
				+ "SELECT DISTINCT IA.AccountNumber          FROM Customer IC(NOLOCK) "
				+ "JOIN CustomerAddress ICA(NOLOCK) ON IC.CustomerID=ICA.CustomerID "
				+ "JOIN Account IA(NOLOCK) ON IA.AddressID=ICA.AddressID "
				+ "JOIN UserAccount IUA(NOLOCK) ON IUA.AccountNumber=IA.AccountNumber "
				+ ")R ON A.AccountNumber=R.AccountNumber   WHERE R.AccountNumber IS NULL "
				+ "AND c.CustomerID NOT IN (1,-1)" + "AND CA.AddressType=1"
				+ "GROUP BY c.FirstName,c.LastName,c.CustomerID,c.MobilePhone,C.customerNo,(CASE WHEN CA.AddressType=1 THEN 'Residential' ELSE 'Commercial' END) "
				+ ",c.DrivingLicence,CA.ZipCode,CA.Address1,C.SSNNumber,CA.UtilityAccountNumber";
		return sUserDetails;
	}

	/**
	 * This method returns query which return user details for the given
	 * username.
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getUserDetailsQuery(String sUserName) {
		String sQuery = "SELECT * FROM [User] " + "WHERE UserName = '" + sUserName + "'";
		return sQuery;
	}

	public static String getTopLatestUpdatedActiveUser() {
		String query = "select top 1 userid, username, password, emailid, status from [user]\n" +
				"where status = 1\n" +
				"order by userid desc";
		return query;
	}

	public static String updatePasswordWithUserId(String userId, String password) {
		String query = "update [user]\n" +
				"set password = '"+password+"'\n" +
				"where userid = '"+userId+"'";
		return query;
	}

	public static String getEmailContentResetPwdCsp(String emailId) {
		String query = "select top 1 * from contractaccountnotifyemail\n" +
				"where emailid = '"+emailId+"' \n" +
				"and subject = 'SCM Password Reset Link'\n" +
				"and module = 'Admin'\n" +
				"order by id desc";
		return query;
	}

	public static String getUserIsArchiveStatus(String userIP) {
		String sUserIsLockStatus = ";"
				+ "SELECT top 1  IsArchive FROM UserActivityTrail WHERE ActivityStatus<>1 and IPAddress='" + userIP
				+ "' order by activitydatetime desc";
		return sUserIsLockStatus;
	}

	public static String getUserBillingAmount(String sBillingdate) {
		String sUserIsLockStatus = ";"
				+ "SELECT Value FROM BillingDetail WHERE BillingID=( SELECT BillingId from Billing WHERE AccountNumber=16 and billingDate= '"
				+ sBillingdate + "') and Headid='22'";
		return sUserIsLockStatus;
	}

	public static String getUserBillingAmount(String sBillingdate, String sUtilityNum) {
		String sUserIsLockStatus = ";"
				+ " SELECT Value FROM BillingDetail WHERE BillingID=( SELECT BillingId from Billing WHERE AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNum + "') and billingDate= '" + sBillingdate + "') and Headid='22'";
		return sUserIsLockStatus;
	}

	public static String getUserPaymentAmount(String sPaymentDate, String sUtilityNum) {
		String sUserIsLockStatus = ";"
				+ "  SELECT * FROM BillingTransaction  where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNum + "') and TransactionDate='" + sPaymentDate + "'";
		return sUserIsLockStatus;
	}

	public static String getUserMeterDetails(String sUtilityNumber) {
		String sUserMeterDetails = ";"
				+ "SELECT * FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNumber + "') and Status='1'";
		return sUserMeterDetails;
	}

	public static String getUserUtilityNumberByMeterType(String sMeterType, String sStatus, String sOperator) {
		String utilityNum = ";"
				+ "select UtilityAccountNumber from Account where AccountNumber=(SELECT Top 1 AccountNumber FROM AccountMeterMapping WHERE Status = '"
				+ sStatus + "' and MeterType = '" + sMeterType
				+ "' and accountnumber not in (-1,2) GROUP BY AccountNumber HAVING COUNT(MeterType)" + sOperator + "1)";
		return utilityNum;
	}

	public static String getMeterNumberWithStatus(String sMeterType, String sUtilityNumber, String sStatus) {
		String meterNumber = ";"
				+ "SELECT MeterNumber FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ sUtilityNumber + "') and Status='" + sStatus + "' and MeterType='" + sMeterType + "'";
		return meterNumber;
	}

	public static String getAllMeterCount(String sUtilityNumber, String sStatus, String sMeterType) {
		String meterCount = ";" + "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM vcustomer WHERE status='"
				+ sStatus + "' and metertype='" + sMeterType + "' AND UtilityAccountNumber='" + sUtilityNumber + "'";
		return meterCount;
	}

	public static String getAllAMIMeterCount(String sUtilityNumber, String iSAMI, String sStatus, String sMeterType) {
		String meterCount = ";" + "SELECT Count (DISTINCT MeterNumber) as MeterNumber FROM vcustomer WHERE IsAMI='"
				+ iSAMI + "' and status='" + sStatus + "' AND metertype='" + sMeterType + "' AND UtilityAccountNumber='"
				+ sUtilityNumber + "'";
		return meterCount;
	}

	public static String getUtilityNumberWithOneAMIMeter(String iSAMI, String sStatus, String sMeterType) {
		String utilityAccountNo = ";"
				+ "select UtilityAccountNumber from Account where AccountNumber=(SELECT Top 1 AccountNumber FROM AccountMeterMapping WHERE IsAMI='"
				+ iSAMI + "' and status='" + sStatus + "' AND metertype='" + sMeterType
				+ "'and accountnumber not in (-1,2) GROUP BY AccountNumber HAVING COUNT(IsAMI)=1)";

		return utilityAccountNo;
	}

	// This query brings the disclaimer text for a specific language.
	/**
	 * @param ControlId-ML_Compare_CompareZip_NoData
	 * @param sLangauge-En
	 * @return sControlTextQuery
	 */
	public static String getControlText(String ControlId, String sLangauge) {
		String sControlTextQuery = ";" + "select ControlText from multilingualmaster where ControlId ='" + ControlId
				+ "' and LanguageCode ='" + sLangauge + "'";

		return sControlTextQuery;
	}

	/**
	 * This query is for getting the Payment Options Value in CSP(Like Maximum
	 * Payment Amount, Processing Fee etc.)
	 */
	public static String getCspPaymentOption() {
		String sCspPaymentOption = ";" + "select ConfigType, ConfigValue from [Config].[ConfigurationSetting]";
		return sCspPaymentOption;
	}

	/**
	 * This method is to get the Service Account Number in Random
	 *
	 * @return String
	 */
	public static String getServiceAccountNumber(String op, int value) {
		String sServiceAccountNumber = ";"
				+ "select UtilityAccountNumber from Account WHERE AccountNumber NOT IN (-1,2) AND Status" + op + value;
		return sServiceAccountNumber;
	}

	public static String getAdvanceSearchCustomer(String serviceAccountNumber, String accountType, String status,
												  String customerNumber, String zipCode, String city, String firstName, String lastName, String email) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and ";
		boolean flag = false;

		if (!serviceAccountNumber.equalsIgnoreCase("")) {
			query = query + "A." + "UtilityAccountNumber='" + serviceAccountNumber + "'";
			flag = true;
		}
		if (!accountType.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "AddressType='" + accountType + "'";
			flag = true;
		}

		if (!status.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			// query = query + "A." + "AccountStatus='" + status + "'";
			if (status.equalsIgnoreCase("Active")) {
				query = query + "A." + "AccountStatusID IN (0,1,3) ";
			} else {
				query = query + "A." + "AccountStatusID NOT IN (0,1,3) ";
			}
			flag = true;

		}
		if (!customerNumber.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "customerNo='" + customerNumber + "'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "ZipCode='" + zipCode + "'";
			flag = true;
		}
		if (!city.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "CityName='" + city + "'";
			flag = true;
		}
		if (!firstName.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + " A." + "FirstName='" + firstName + "'";
			flag = true;
		}
		if (!lastName.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "LastName='" + lastName + "'";
			flag = true;
		}
		if (!email.equalsIgnoreCase("")) {
			if (flag) {
				query = query + " and ";
			}
			query = query + "A." + "EmailID='" + email + "'";
			flag = true;
		}

		query = query + " ORDER BY A.FullName ASC";
		return query;

	}

	public static String getAdvanceSearchServiceAccountNumber(String serviceAccountNumber, String accountType,
															  String status, String customerNumber, String zipCode, String city) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and ";

		String que = "SELECT cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ " ,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ " ,cust.AddressType AS 'AccountType',cust.AccountStatus,LinkedUser"
				+ " ,replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ " FROM customerinfo cust"
				+ " left JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ "   FROM useraccount (NOLOCK)" + "  GROUP BY AccountNumber"
				+ " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber" + " WHERE ";

		boolean flag = false;

		if (!serviceAccountNumber.equalsIgnoreCase("")) {
			que = que + "cust." + "UtilityAccountNumber='" + serviceAccountNumber + "'";
			flag = true;
		}
		if (!accountType.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + "cust." + "AddressType='" + accountType + "'" + " and cust.portalAccessType='standard' ";
			flag = true;
		}

		if (!status.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}

			que = que + "cust." + "AccountStatus='" + status + "'";
			flag = true;

		}
		if (!customerNumber.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "customerNo='" + customerNumber + "'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "ZipCode='" + zipCode + "'";
			flag = true;
		}
		if (!city.equalsIgnoreCase("")) {
			if (flag) {
				que = que + " and ";
			}
			que = que + " cust." + "CityName='" + city + "'";
			flag = true;
		}

//		System.out.println(que);

		que = que + " ORDER BY cust.UtilityAccountNumber ASC";
		return que;

	}

	public static String getLinkAccountByCustomerNumber(String utilityAccountNumber) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone,b.UtilityAccountNumber"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.UtilityAccountNumber='" + utilityAccountNumber + "'";
		return query;
	}

	public static String getCustomerCountByStatus(String status) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.AccountStatus='" + status + "'";
		return query;
	}

	public static String getCustomerDetailsByName(String customerName) {
		String query = ";"
				+ "SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ "," + "case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "FROM customerinfo (NOLOCK) A "
				+ "left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "WHERE A.CustomerID NOT IN (1,-1) and A.FullName='" + customerName + "'";
//		System.out.println(query);
		return query;
	}

	/**
	 * This Method is to get the link account number of the service account
	 * number
	 *
	 * @param utilityNumber
	 * @param roleType
	 * @return
	 */
	public static String getLinkAccountCount(String utilityNumber, String roleType) {
		String query = null;
		if (roleType.equalsIgnoreCase("Property Managers")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "' and [UserAccount].RoleID='" + 2 + "'";
		} else if (roleType.equalsIgnoreCase("Guest Users")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "' and [UserAccount].RoleID='" + 1 + "'";
		} else if (roleType.equalsIgnoreCase("Total")) {
			query = "SELECT * FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
					+ utilityNumber + "'";
		}
//		System.out.println(query);
		return query;
	}

	/**
	 * This method is to get info of Commercial/ Residential 'Active', 'Not
	 * Registered', 'Registered' and 'Inactive' for Link Account Popup
	 *
	 * @param accountType
	 *            = Residential | Commercial
	 * @param accountStatus
	 *            = 'Not Registered' | 'Registered' | 'Active' | 'Inactive'
	 *
	 * @return a query having value for UtilityAccountNumber, ZipCode,
	 *         DrivingLicence, MeterNumber, Address1, AddressType
	 */
	public static String getInfoForLinkingAccountFromDB(String accountType, String accountStatus) {
		String query = null;
		query = "select top 1 CustomerInfo.UtilityAccountNumber, CustomerInfo.ZipCode, CustomerInfo.DrivingLicence, CustomerInfo.Address1, CustomerInfo.AddressType, AccountMeterMapping.MeterNumber  from CustomerInfo Inner JOIN  AccountMeterMapping on CustomerInfo.AccountNumber= AccountMeterMapping.AccountNumber WHERE AccountStatus = '"
				+ accountStatus + "' AND CustomerInfo.AddressType = '" + accountType
				+ "' AND CustomerInfo.UtilityAccountNumber not like 'R%' AND CustomerInfo.UtilityAccountNumber not like 'C%' ";
//		System.out.println(query);
		return query;
	}

	public static String getInfoForLinkingAccountFromDB(String accountType, String accountStatus, String userId) {
		String query = null;
		query = "select Top 1 CustomerInfo.UtilityAccountNumber, CustomerInfo.ZipCode, CustomerInfo.DrivingLicence, CustomerInfo.Address1, CustomerInfo.AddressType, AccountMeterMapping.MeterNumber  from CustomerInfo Inner JOIN  AccountMeterMapping on CustomerInfo.AccountNumber= AccountMeterMapping.AccountNumber WHERE AccountStatus = '"
				+ accountStatus + "' AND CustomerInfo.AddressType = '" + accountType
				+ "' and CustomerInfo.accountnumber not in (SELECT AccountNumber FROM [UserAccount]  WHERE UserID=(Select UserID from [User]  where UserName ='"
				+ userId + "'))";
//		System.out.println(query);
		return query;
	}

	/**
	 * This method is to get info of Commercial/ Residential
	 * Registered-NotActivated and Activation Expired for Link Account Popup
	 *
	 * @param accountType
	 *            = Residential | Commercial
	 *
	 * @return a query having value for UtilityAccountNumber, ZipCode,
	 *         DrivingLicence, MeterNumber, Address1, AddressType
	 */
	public static String getInfoForLinkAccountRegisteredExpiredActivationFromDB(String accountType) {
		String query = null;
		query = "select top 1 CI.UtilityAccountNumber, CI.ZipCode, CI.DrivingLicence, CI.Address1, CI.AddressType, AMM.MeterNumber\r\n"
				+ "from [User] U\r\n" + "Join useraccount UA on U.Userid=UA.Userid\r\n"
				+ "join CustomerInfo CI on UA.UtilityAccountNumber = CI.UtilityAccountNumber\r\n"
				+ "join AccountMeterMapping AMM on CI.AccountNumber= AMM.AccountNumber\r\n"
				+ "Join Account A on UA.AccountNumber=A.AccountNumber\r\n"
				+ "where A.status=0 and U.status=0 and CI.AddressType= '" + accountType + "'\r\n"
				+ "and convert(date,U.LinkSentDate)<=convert(date,getdate()-3)";
//		System.out.println(query);
		return query;
	}

	public static String getOptedEfficiencyByAccountNumber(String accountNumber, String categoty) {
		String q = "SELECT * FROM EEPromotion LEFT JOIN EEPromotionUsers ON EEPromotion.PromotionId = EEPromotionUsers.PromotionId where EEPromotionUsers.AccountNumber=(select AccountNumber from customerinfo where UtilityAccountNumber="
				+ "'" + accountNumber + "') and EEPromotion.CategoryId=" + "'" + categoty
				+ "' and EEPromotion.IsActive='1' and EEPromotion.IsDeleted='0' ORDER BY EEPromotion.CreatedDate DESC";
//		System.out.println(q);
		return q;
	}

	public static String getEfficiencyDescription(String efficiencyName, String categoty) {
		String q = "Select Description from EEPromotion where Title='" + efficiencyName + "'"
				+ "and IsActive='1' and CategoryId='" + categoty + "' and AccountType in ('1','2')"
				+ " and  IsDeleted='0'";
//		System.out.println(q);
		return q;
	}

	public static String getEfficiencyAdded(String efficiencyName, String categoty) {
		String q = "SELECT count(*) as Added FROM EEPromotionUsers where promotionId=(Select promotionId from EEPromotion where Title='"
				+ efficiencyName + "'and IsActive='1' and CategoryId='" + categoty
				+ "'and AccountType in ('1','2') and  IsDeleted='0')";
		return q;
	}

	public static String getEfficiencyViews(String efficiencyName, String categoty) {
		String q = "Select views from EEPromotion where Title='" + efficiencyName + "'and IsActive='1' and CategoryId='"
				+ categoty + "'and IsActive='1' and AccountType in ('1','2') and  IsDeleted='0'";
		return q;
	}

	public static String getEfficiencyLikes(String efficiencyName, String categoty) {
		String q = "SELECT count(*) as LikeCount FROM EEPromotionLike where promotionId=(Select promotionId from EEPromotion where Title='"
				+ efficiencyName + "' and IsActive='1' and CategoryId='" + categoty
				+ "' and AccountType in ('1','2')  and  IsDeleted='0')";
		return q;
	}

	public static String getAccountNotificationDetail(String accountNumber) {
		String q = "SELECT AccountNotificationType,EmailOrPhone FROM AccountNotificationType Join AccountNotificationDetail on AccountNotificationType.AccountNotificationTypeID=AccountNotificationDetail.AccountNotificationTypeID where"
				+ " AccountNumber=(Select top 1 AccountNumber from userAccount where utilityAccountNumber='"
				+ accountNumber
				+ "') and UserID=(SELECT [UserAccount].UserID FROM [UserAccount] Full OUTER JOIN [User] on [UserAccount].UserID=[User].UserID where [UserAccount].utilityAccountNumber='"
				+ accountNumber + "'and [UserAccount].RoleID=3)";
		return q;
	}

	public static String getMeterNumberForAccount(String accountNumber, String meterType) {
		String q = null;
		q = "SELECT MeterNumber  FROM AccountMeterMapping where AccountNumber=(select Accountnumber from Account Full outer join CustomerAddress on Account.AddressId=customerAddress.AddressId where CustomerAddress.utilityAccountnumber='"
				+ accountNumber + "') and MeterType='" + meterType + "' and Status='1'";
		return q;
	}

	public static String getMarketPreference(String userName) {
		String q = null;
		q = "SELECT MarketingPreferenceSetting.PreferenceName FROM UserMarketingPreferenceSetting join MarketingPreferenceSetting on  UserMarketingPreferenceSetting.PreferenceId=MarketingPreferenceSetting.PreferenceId    where UserMarketingPreferenceSetting.UserId=(select UserID from [User] where UserName='"
				+ userName + "')";
		return q;
	}

	public static String getCustomerPropertyDetails(String accountNumber) {
		String q = null;
		q = "select UtilityAccountNumber,AddressType,CityName,Zipcode from customerinfo where UtilityAccountNumber='"
				+ accountNumber + "'";
		return q;
	}

	public static String getInteractionCount(String userName) {
		String q = null;
		q = "SELECT count(*) as EventCount from SCPModuleEventLog where userId=(select UserID from [User] where UserName='"
				+ userName + "') order by 1 desc";
		return q;
	}

	public static String getGuestCount(String accountNumber) {
		String q = null;
		q = "SELECT count(*) as GuestCount FROM GuestAccessRequest where UtilityAccountNumber='" + accountNumber + "'";
		return q;
	}

	public static String getGuestDetails(String accountNumber) {
		String q = null;
		q = "SELECT GuestName,GuestEmailID FROM GuestAccessRequest where UtilityAccountNumber='" + accountNumber + "'";
		return q;
	}

	public static String getBillingDetails(String accountNumber) {
		String q = null;
		q = "SELECT Top 10 Billing.BillingDate,BillingDetail.value from BillingDetail join Billing on  BillingDetail.BillingID=Billing.BillingID where BillingDetail.HeadId='22' and Billing.AccountNumber=(select AccountNumber from account where utilityAccountNumber='"
				+ accountNumber + "')  order by 1 desc ";
		return q;
	}

	public static String getPaymentDetails(String accountNumber) {
		String q = null;
		q = "SELECT top 10 * FROM BillingTransaction where accountnumber=(select AccountNumber from account where utilityAccountNumber='"
				+ accountNumber + "') order by 1 desc";
		return q;
	}

	// New query for CSRs
	public static String getServiceAccountNumberCount() {
		String sGetServiceAccountNumberCount = ";"
				+ "select count(AccountNumber) as TotalServiceAccountNumberCount from Account WHERE AccountNumber NOT IN (-1,2)";
		return sGetServiceAccountNumberCount;
	}

	public static String getServiceAccountNumberCountActive() {
		// String sGetServiceAccountNumberCountActive = ";"
		// + "select count(AccountNumber) as ActiveServiceAccountNumber from
		// Account WHERE AccountNumber NOT IN (-1,2) AND Status in (0,1,3);";
		// return sGetServiceAccountNumberCountActive;
		String sGetServiceAccountNumberCountActive = ";"
				+ "SELECT COUNT(AccountNumber) ActiveServiceAccountNumber FROM customerinfo   WHERE AccountStatusID IN (0,1,3) AND CustomerID NOT IN (1,-1)";
		return sGetServiceAccountNumberCountActive;

	}

	public static String getCustomerCount() {
		String sGetCustomerCount = ";"
				+ "SELECT COUNT(DISTINCT customerid) as TotalCustomerNumber FROM CustomerInfo(NOLOCK) WHERE CustomerID NOT IN (1,-1)";
		return sGetCustomerCount;
	}

	public static String getCustomerCountActive() {
		String sGetCustomerCountActive = ";"
				+ "SELECT COUNT(DISTINCT customerid) as TotalCustomerNumberActive FROM CustomerInfo(NOLOCK) WHERE CustomerID NOT IN (1,-1) AND AccountStatusID IN (0,1,3)";
		return sGetCustomerCountActive;
	}

	/**
	 * This Method is to get the Utility Number by the meter type and customer
	 * type
	 *
	 * @param meterType
	 *            E,W,G
	 * @param customerType
	 *            Enterprise,Mass-market
	 * @return
	 */
	public static String getUtilityNumberByMeterAndCustomerType(String meterType, String customerType) {
		String query = "Declare @MeterType Varchar(500)='" + meterType + "',@CustomerType Varchar(200)='" + customerType
				+ "'" + " If(@CustomerType='Enterprise')" + " BEGIN"
				+ " select  CI.AccountNumber,CI.UtilityAccountnUmber from AccountMeterMapping AMM"
				+ " Inner Join CustomerInfo CI On AMM.AccountNumber=CI.AccountNumber"
				+ " where CI.AddressTypeID=2 And CI.PortalAccessTypeID=1 and CI.AccountStatusID=0"
				+ " And AMM.MeterType in (Select Item from dbo.SplitString(@MeterType,','))" + " END" + " Else"
				+ " BEGIN" + " select  CI.AccountNumber,CI.UtilityAccountnUmber from AccountMeterMapping AMM"
				+ " Inner Join CustomerInfo CI On AMM.AccountNumber=CI.AccountNumber"
				+ " where CI.AddressTypeID=1 And CI.PortalAccessTypeID=0 and CI.AccountStatusID=0"
				+ " And AMM.MeterType in (Select Item from dbo.SplitString(@MeterType,','))" + " END";
		return query;
	}

	// sql query for connect Me Template
	public static String getConnectMetemplate() {
		String sConnectMeTemplate = ";" + "SELECT top 1 * FROM TemplateType where status=1 and moduleid=2";
		return sConnectMeTemplate;
	}

	// sql query for Service Template
	public static String getServicetemplate() {
		String sServiceTemplate = ";" + "SELECT top 1 * FROM TemplateType where status=1 and moduleid=1";
		return sServiceTemplate;
	}

	public static String getConnectMeTopics() {
		String sConnectMeTopic = ";" + "SELECT  * FROM ConnectTopicMaster where IsActive=1";
		return sConnectMeTopic;
	}

	public static String getServiceTopics() {
		String sServiveTopic = ";" + "SELECT * FROM SRReasonMaster where IsActive=1";
		return sServiveTopic;
	}

	public static String getBanners() {
		String sBanners = ";" + "select * from BannerMaster where IsActive=1";
		return sBanners;
	}

	public static String getRegistrationTemplates() {
		String stempReg = ";" + "select * from TemplateMaster";
		return stempReg;
	}

	public static String getRegistrationTemplatesActive() {
		String stempRegActive = ";" + "select * from TemplateMaster where isActive=1";
		return stempRegActive;
	}

	public static String getBannersDetails(String att) {
		String stempRegActive = ";" + "select * from BannerMaster where IsActive=1 and BannerName='" + att + "'";
		return stempRegActive;
	}

	public static String getAllEmailTemplates() {
		String sEmailTemplate = ";" + "select * from EMailTemplate";
		return sEmailTemplate;
	}

	public static String getEmailTemplateDetails(String tempName) {
		String stempEmail = ";" + "select * from EMailTemplate where TemplateName='" + tempName + "'";
		return stempEmail;
	}

	public static String getFieldValueUniSearch(String customerName, String zipCode, String accountNumber,
													  String emailId, String contactNo, String address) {
		String q = "Declare @ZipCode  VARCHAR(100) = '" + zipCode + "'" + ", @CustName  VARCHAR(122)= '" + customerName
				+ "'" + ", @AccountNumber VARCHAR(100) = '" + accountNumber + "'" + ", @TimeOffSet INT   = -360 "
				+ ", @SortColumn NVARCHAR(50) = 'FullName' " + ", @SortOrder  NVARCHAR(4) = 'ASC' "
				+ ", @PageIndex  INT   = 1 " + ", @PageSize  INT   = 10 " + ", @MobilePhone VARCHAR(100) = '"
				+ contactNo + "' " + ", @EmailID  VARCHAR(100)= '" + emailId + "'" + ", @address1      VARCHAR(255)= '"
				+ address + "'" + ", @SearchString VARCHAR(100)= NULL " + ", @FilterMode TINYINT  = 2 "

				+ "  DECLARE @StatusCount  TABLE([Status] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE @CustomerTypeCount TABLE([CustomerType] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE  @ExpirationDays INT=1,@TotalRecords int=0,@RecCount BIGINT=0 "

				+ " SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T WITH(NOLOCK) "
				+ " JOIN CommonMaster CM WITH(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
				+ " WHERE TempDetailID = 51 "

				+ "  SELECT CI.AccountNumber,CI.CustomerId,CI.FullName AS [CustomerName],CI.EmailID as EmailID,CI.ZipCode, "
				+ " CI.MobilePhone as  MobilePhone,Address1,CI.CityName,CI.AddressType  AS [CustomerType],CI.[AccountStatus] AS ActualCIStatus, "
				+ "  Case when CI.[AccountStatus]='Registered' OR CI.[AccountStatus]='Active' Then 'Registered' Else 'Not Registered' End AS [Status2], "
				+ " Case when IsNull(U.UserId,'')='' then 'Not Registered' else 'Registered' End AS [Status],IsNull(U.IsLocked,0) as IsLocked,  "
				+ " Case when CI.[AccountStatus]='Inactive' Then CI.[AccountStatus] when CI.[AccountStatus]='Closed' Then 'Inactive'"

				+ " Else 'Active' End as [AccountStatus], "
				+ "  CI.Latitude,CI.Longitude,CI.UtilityAccountNumber,TY.TemplateTypeId as TemplateTypeId_HomeBusiness,DM.ModuleID as ModuleId_HomeBusiness, "
				+ " U.UserId,CI.FirstName,CI.LastName,CI.AccountStatusId AS AccountStatusCode,ISNULL(u.[Status],3) AS CustomerStatusCode, "
				+ " (CASE WHEN CI.[AccountStatusID]=0 AND DATEDIFF(DAY,ISNULL(U.ReminderDate,U.CreatedDate),GETDATE())<=@ExpirationDays THEN 0 "
				+ "  WHEN CI.[AccountStatusId]=3 OR (CI.[AccountStatusId]=0  "
				+ "  AND DATEDIFF(DAY,ISNULL(U.ReminderDate,U.CreatedDate),GETDATE())>@ExpirationDays) THEN 3 ELSE CI.[AccountStatusID] END) "
				+ "  AS [AccountStatusID], "
				+ "  REPLACE(STUFF(('O'+IsNull((Select Top 1 ', P, ' From UserAccount Where AccountNumber=CI.AccountNumber and IsNull(RoleID,0)=2),'') + "
				+ "  IsNull((Select Top 1 ', G' From UserAccount Where AccountNumber=CI.AccountNumber and IsNull(RoleID,0)=1),'')),1,0,''),', ,',',') as Roles "
				+ " FROM CustomerInfo CI WITH(NOLOCK) "
				+ "   LEFT JOIN UserAccount ua  WITH(NOLOCK) ON ua.AccountNumber  = CI.AccountNumber AND ua.RoleID = 3  and Iscustomerowner=1"
				+ "  LEFT JOIN [User]  U  (Nolock)  ON u.UserId=ua.UserId "
				+ "  JOIN CommonMaster  CMNCS WITH(NOLOCK) ON CMNCS.MasterCode  = ISNULL(U.[Status],3)   AND CMNCS.MasterType= 'CustomerStatus' "
				+ "  JOIN UtilityConfig UC(NOLOCK) on UC.ModuleName=(case CI.AddressTypeId when  1 Then 'AboutMyHome' when 2 then 'AboutMyBusiness' end)"
				+ "	 AND UC.[ConfigOption]='TemplateTypeID' "
				+ "   JOIN TemplateType TY(NOLOCK) ON UC.ConfigValue=TY.TemplateTypeId "
				+ "   JOIN DynamicModules DM on DM.ModuleID=TY.ModuleID " + "  WHERE CI.ID IN(" + "  SELECT CI.ID "
				+ "  FROM CustomerInfo CI WITH(NOLOCK)  " + "  WHERE CI.CustomerId > 1 "
				+ "  AND IsNull(CI.UtilityAccountNumber,'')LIKE '%'+ IsNull(Cast(@AccountNumber as Varchar(32)),IsNull(CI.UtilityAccountNumber,'')) +'%'"
				+ "  AND IsNull(CI.FullName,'') LIKE '%'+ IsNull(@CustName,IsNull(CI.FullName,'')) +'%' "
				+ "  AND IsNull(CI.ZipCode,'')=IsNull(Cast(@ZipCode as Varchar(32)),IsNull(CI.ZipCode,'')) "
				+ "  AND IsNull(CI.MobilePhone,'') LIKE '%'+ IsNull(@MobilePhone,IsNull(CI.MobilePhone,'')) +'%' "
				+ "  AND ISNULL(CI.EmailID,'') LIKE '%'+ IsNull(@EmailID,ISNULL(CI.EmailID,'')) +'%' "
				+ "  AND (IsNull(CI.[address1],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address1],''))+'%'"
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address2],''))+'%') "
				+ "  AND (IsNull(CI.FirstName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FirstName,''))+'%'  "
				+ "  OR Isnull(CI.LastName,'') LIKE '%'+IsNull(@SearchString,Isnull(CI.LastName,''))+'%' "
				+ "  OR IsNull(CI.UtilityAccountNumber,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.UtilityAccountNumber,''))+'%' "
				+ "  OR IsNull(CI.EmailID,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.EmailID,''))+'%' "
				+ "  OR IsNull(CI.MobilePhone,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.MobilePhone,''))+'%'  "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FullName,''))+'%'  "
				+ "  OR IsNull(CI.[address1],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address1],''))+'%' "
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address2],''))+'%' "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+ IsNull(@SearchString,IsNull(CI.FullName,'')) +'%' "
				+ "  OR IsNull(Cast(CI.ZipCode as Varchar(32)),'') LIKE '%'+ IsNull(@SearchString,IsNull(Cast(CI.ZipCode as Varchar(32)),'')) +'%' "
				+ "     )" + " ) " + " ORDER BY [CustomerName] " + " print 'end'";
		return q;
	}

	public static String getCountFieldValueUniSearch(String customerName, String zipCode, String accountNumber,
														   String emailId, String contactNo, String address) {
		String q = "Declare @ZipCode  VARCHAR(100) = '" + zipCode + "'" + ", @CustName  VARCHAR(122)= '" + customerName
				+ "'" + ", @AccountNumber VARCHAR(100) = '" + accountNumber + "'" + ", @TimeOffSet INT   = -360 "
				+ ", @SortColumn NVARCHAR(50) = 'FullName' " + ", @SortOrder  NVARCHAR(4) = 'ASC' "
				+ ", @PageIndex  INT   = 1 " + ", @PageSize  INT   = 10 " + ", @MobilePhone VARCHAR(100) = '"
				+ contactNo + "' " + ", @EmailID  VARCHAR(100)= '" + emailId + "'" + ", @address1      VARCHAR(255)= '"
				+ address + "'" + ", @SearchString VARCHAR(100)= NULL " + ", @FilterMode TINYINT  = 2 "

				+ "  DECLARE @StatusCount  TABLE([Status] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE @CustomerTypeCount TABLE([CustomerType] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE  @ExpirationDays INT=1,@TotalRecords int=0,@RecCount BIGINT=0 "

				+ " SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T WITH(NOLOCK) "
				+ " JOIN CommonMaster CM WITH(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
				+ " WHERE TempDetailID = 51 "

				+ "  SELECT COUNT(CI.CustomerId) AS TotalRecords "

				+ " FROM CustomerInfo CI WITH(NOLOCK) "
				+ "   LEFT JOIN UserAccount ua  WITH(NOLOCK) ON ua.AccountNumber  = CI.AccountNumber AND ua.RoleID = 3  and Iscustomerowner=1"
				+ "  LEFT JOIN [User]  U  (Nolock)  ON u.UserId=ua.UserId "
				+ "  JOIN CommonMaster  CMNCS WITH(NOLOCK) ON CMNCS.MasterCode  = ISNULL(U.[Status],3)   AND CMNCS.MasterType= 'CustomerStatus' "
				+ "  JOIN UtilityConfig UC(NOLOCK) on UC.ModuleName=(case CI.AddressTypeId when  1 Then 'AboutMyHome' when 2 then 'AboutMyBusiness' end)"
				+ "	 AND UC.[ConfigOption]='TemplateTypeID' "
				+ "   JOIN TemplateType TY(NOLOCK) ON UC.ConfigValue=TY.TemplateTypeId "
				+ "   JOIN DynamicModules DM on DM.ModuleID=TY.ModuleID " + "  WHERE CI.ID IN(" + "  SELECT CI.ID "
				+ "  FROM CustomerInfo CI WITH(NOLOCK)  " + "  WHERE CI.CustomerId > 1 "
				+ "  AND IsNull(CI.UtilityAccountNumber,'')LIKE '%'+ IsNull(Cast(@AccountNumber as Varchar(32)),IsNull(CI.UtilityAccountNumber,'')) +'%'"
				+ "  AND IsNull(CI.FullName,'') LIKE '%'+ IsNull(@CustName,IsNull(CI.FullName,'')) +'%' "
				+ "  AND IsNull(CI.ZipCode,'')=IsNull(Cast(@ZipCode as Varchar(32)),IsNull(CI.ZipCode,'')) "
				+ "  AND IsNull(CI.MobilePhone,'') LIKE '%'+ IsNull(@MobilePhone,IsNull(CI.MobilePhone,'')) +'%' "
				+ "  AND ISNULL(CI.EmailID,'') LIKE '%'+ IsNull(@EmailID,ISNULL(CI.EmailID,'')) +'%' "
				+ "  AND (IsNull(CI.[address1],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address1],''))+'%'"
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address2],''))+'%') "
				+ "  AND (IsNull(CI.FirstName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FirstName,''))+'%'  "
				+ "  OR Isnull(CI.LastName,'') LIKE '%'+IsNull(@SearchString,Isnull(CI.LastName,''))+'%' "
				+ "  OR IsNull(CI.UtilityAccountNumber,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.UtilityAccountNumber,''))+'%' "
				+ "  OR IsNull(CI.EmailID,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.EmailID,''))+'%' "
				+ "  OR IsNull(CI.MobilePhone,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.MobilePhone,''))+'%'  "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FullName,''))+'%'  "
				+ "  OR IsNull(CI.[address1],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address1],''))+'%' "
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address2],''))+'%' "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+ IsNull(@SearchString,IsNull(CI.FullName,'')) +'%' "
				+ "  OR IsNull(Cast(CI.ZipCode as Varchar(32)),'') LIKE '%'+ IsNull(@SearchString,IsNull(Cast(CI.ZipCode as Varchar(32)),'')) +'%' "
				+ "     )" + " ) ";
		return q;
	}

	public static String getCountEmptyFieldSearchUniSearch() {
		String q = "Declare @ZipCode  VARCHAR(100) = null" + ", @CustName  VARCHAR(122)= null"
				+ ", @AccountNumber VARCHAR(100) = null" + ", @TimeOffSet INT   = -360 "
				+ ", @SortColumn NVARCHAR(50) = 'FullName' " + ", @SortOrder  NVARCHAR(4) = 'ASC' "
				+ ", @PageIndex  INT   = 1 " + ", @PageSize  INT   = 10 " + ", @MobilePhone VARCHAR(100) = null"
				+ ", @EmailID  VARCHAR(100)= null" + ", @address1      VARCHAR(255)= null"
				+ ", @SearchString VARCHAR(100)= NULL " + ", @FilterMode TINYINT  = 2 "

				+ "  DECLARE @StatusCount  TABLE([Status] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE @CustomerTypeCount TABLE([CustomerType] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE  @ExpirationDays INT=1,@TotalRecords int=0,@RecCount BIGINT=0 "

				+ " SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T WITH(NOLOCK) "
				+ " JOIN CommonMaster CM WITH(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
				+ " WHERE TempDetailID = 51 "

				+ "  SELECT COUNT(CI.CustomerId) AS TotalRecords "

				+ " FROM CustomerInfo CI WITH(NOLOCK) "
				+ "   LEFT JOIN UserAccount ua  WITH(NOLOCK) ON ua.AccountNumber  = CI.AccountNumber AND ua.RoleID = 3  and Iscustomerowner=1"
				+ "  LEFT JOIN [User]  U  (Nolock)  ON u.UserId=ua.UserId "
				+ "  JOIN CommonMaster  CMNCS WITH(NOLOCK) ON CMNCS.MasterCode  = ISNULL(U.[Status],3)   AND CMNCS.MasterType= 'CustomerStatus' "
				+ "  JOIN UtilityConfig UC(NOLOCK) on UC.ModuleName=(case CI.AddressTypeId when  1 Then 'AboutMyHome' when 2 then 'AboutMyBusiness' end)"
				+ "	 AND UC.[ConfigOption]='TemplateTypeID' "
				+ "   JOIN TemplateType TY(NOLOCK) ON UC.ConfigValue=TY.TemplateTypeId "
				+ "   JOIN DynamicModules DM on DM.ModuleID=TY.ModuleID " + "  WHERE CI.ID IN(" + "  SELECT CI.ID "
				+ "  FROM CustomerInfo CI WITH(NOLOCK)  " + "  WHERE CI.CustomerId > 1 "
				+ "  AND IsNull(CI.UtilityAccountNumber,'')LIKE '%'+ IsNull(Cast(@AccountNumber as Varchar(32)),IsNull(CI.UtilityAccountNumber,'')) +'%'"
				+ "  AND IsNull(CI.FullName,'') LIKE '%'+ IsNull(@CustName,IsNull(CI.FullName,'')) +'%' "
				+ "  AND IsNull(CI.ZipCode,'')=IsNull(Cast(@ZipCode as Varchar(32)),IsNull(CI.ZipCode,'')) "
				+ "  AND IsNull(CI.MobilePhone,'') LIKE '%'+ IsNull(@MobilePhone,IsNull(CI.MobilePhone,'')) +'%' "
				+ "  AND ISNULL(CI.EmailID,'') LIKE '%'+ IsNull(@EmailID,ISNULL(CI.EmailID,'')) +'%' "
				+ "  AND (IsNull(CI.[address1],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address1],''))+'%'"
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address2],''))+'%') "
				+ "  AND (IsNull(CI.FirstName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FirstName,''))+'%'  "
				+ "  OR Isnull(CI.LastName,'') LIKE '%'+IsNull(@SearchString,Isnull(CI.LastName,''))+'%' "
				+ "  OR IsNull(CI.UtilityAccountNumber,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.UtilityAccountNumber,''))+'%' "
				+ "  OR IsNull(CI.EmailID,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.EmailID,''))+'%' "
				+ "  OR IsNull(CI.MobilePhone,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.MobilePhone,''))+'%'  "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FullName,''))+'%'  "
				+ "  OR IsNull(CI.[address1],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address1],''))+'%' "
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address2],''))+'%' "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+ IsNull(@SearchString,IsNull(CI.FullName,'')) +'%' "
				+ "  OR IsNull(Cast(CI.ZipCode as Varchar(32)),'') LIKE '%'+ IsNull(@SearchString,IsNull(Cast(CI.ZipCode as Varchar(32)),'')) +'%' "
				+ "     )" + " ) ";
		return q;
	}

	public static String getEmptyFieldSearchUniSearch() {
		String q = "Declare @ZipCode  VARCHAR(100) = null" + ", @CustName  VARCHAR(122)= null"
				+ ", @AccountNumber VARCHAR(100) = null" + ", @TimeOffSet INT   = -360 "
				+ ", @SortColumn NVARCHAR(50) = 'FullName' " + ", @SortOrder  NVARCHAR(4) = 'ASC' "
				+ ", @PageIndex  INT   = 1 " + ", @PageSize  INT   = 10 " + ", @MobilePhone VARCHAR(100) = null"
				+ ", @EmailID  VARCHAR(100)= null" + ", @address1      VARCHAR(255)= null"
				+ ", @SearchString VARCHAR(100)= NULL " + ", @FilterMode TINYINT  = 2 "

				+ "  DECLARE @StatusCount  TABLE([Status] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE @CustomerTypeCount TABLE([CustomerType] VARCHAR(20),Cnt BIGINT DEFAULT 0) "
				+ " DECLARE  @ExpirationDays INT=1,@TotalRecords int=0,@RecCount BIGINT=0 "

				+ " SELECT @ExpirationDays = REPLACE(CM.Name,' day(s)','') FROM TemplateDetail T WITH(NOLOCK) "
				+ " JOIN CommonMaster CM WITH(NOLOCK) ON(CM.MasterCode = T.Value AND CM.MasterType = 'TempValidity') "
				+ " WHERE TempDetailID = 51 "

				+ "  SELECT CI.AccountNumber,CI.CustomerId,CI.FullName AS [CustomerName],CI.EmailID as EmailID,CI.ZipCode, "
				+ " CI.MobilePhone as  MobilePhone,Address1,CI.CityName,CI.AddressType  AS [CustomerType],CI.[AccountStatus] AS ActualCIStatus, "
				+ "  Case when CI.[AccountStatus]='Registered' OR CI.[AccountStatus]='Active' Then 'Registered' Else 'Not Registered' End AS [Status2], "
				+ " Case when IsNull(U.UserId,'')='' then 'Not Registered' else 'Registered' End AS [Status],IsNull(U.IsLocked,0) as IsLocked,  "
				+ " Case when CI.[AccountStatus]='Inactive' Then CI.[AccountStatus] when CI.[AccountStatus]='Closed' Then 'Inactive'"
				+ " Else 'Active' End as [AccountStatus], "
				+ "  CI.Latitude,CI.Longitude,CI.UtilityAccountNumber,TY.TemplateTypeId as TemplateTypeId_HomeBusiness,DM.ModuleID as ModuleId_HomeBusiness, "
				+ " U.UserId,CI.FirstName,CI.LastName,CI.AccountStatusId AS AccountStatusCode,ISNULL(u.[Status],3) AS CustomerStatusCode, "
				+ " (CASE WHEN CI.[AccountStatusID]=0 AND DATEDIFF(DAY,ISNULL(U.ReminderDate,U.CreatedDate),GETDATE())<=@ExpirationDays THEN 0 "
				+ "  WHEN CI.[AccountStatusId]=3 OR (CI.[AccountStatusId]=0  "
				+ "  AND DATEDIFF(DAY,ISNULL(U.ReminderDate,U.CreatedDate),GETDATE())>@ExpirationDays) THEN 3 ELSE CI.[AccountStatusID] END) "
				+ "  AS [AccountStatusID], "
				+ "  REPLACE(STUFF(('O'+IsNull((Select Top 1 ', P, ' From UserAccount Where AccountNumber=CI.AccountNumber and IsNull(RoleID,0)=2),'') + "
				+ "  IsNull((Select Top 1 ', G' From UserAccount Where AccountNumber=CI.AccountNumber and IsNull(RoleID,0)=1),'')),1,0,''),', ,',',') as Roles "

				+ " FROM CustomerInfo CI WITH(NOLOCK) "
				+ "   LEFT JOIN UserAccount ua  WITH(NOLOCK) ON ua.AccountNumber  = CI.AccountNumber AND ua.RoleID = 3  and Iscustomerowner=1"
				+ "  LEFT JOIN [User]  U  (Nolock)  ON u.UserId=ua.UserId "
				+ "  JOIN CommonMaster  CMNCS WITH(NOLOCK) ON CMNCS.MasterCode  = ISNULL(U.[Status],3)   AND CMNCS.MasterType= 'CustomerStatus' "
				+ "  JOIN UtilityConfig UC(NOLOCK) on UC.ModuleName=(case CI.AddressTypeId when  1 Then 'AboutMyHome' when 2 then 'AboutMyBusiness' end)"
				+ "	 AND UC.[ConfigOption]='TemplateTypeID' "
				+ "   JOIN TemplateType TY(NOLOCK) ON UC.ConfigValue=TY.TemplateTypeId "
				+ "   JOIN DynamicModules DM on DM.ModuleID=TY.ModuleID " + "  WHERE CI.ID IN(" + "  SELECT CI.ID "
				+ "  FROM CustomerInfo CI WITH(NOLOCK)  " + "  WHERE CI.CustomerId > 1 "
				+ "  AND IsNull(CI.UtilityAccountNumber,'')LIKE '%'+ IsNull(Cast(@AccountNumber as Varchar(32)),IsNull(CI.UtilityAccountNumber,'')) +'%'"
				+ "  AND IsNull(CI.FullName,'') LIKE '%'+ IsNull(@CustName,IsNull(CI.FullName,'')) +'%' "
				+ "  AND IsNull(CI.ZipCode,'')=IsNull(Cast(@ZipCode as Varchar(32)),IsNull(CI.ZipCode,'')) "
				+ "  AND IsNull(CI.MobilePhone,'') LIKE '%'+ IsNull(@MobilePhone,IsNull(CI.MobilePhone,'')) +'%' "
				+ "  AND ISNULL(CI.EmailID,'') LIKE '%'+ IsNull(@EmailID,ISNULL(CI.EmailID,'')) +'%' "
				+ "  AND (IsNull(CI.[address1],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address1],''))+'%'"
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+ IsNull(@address1,IsNull(CI.[address2],''))+'%') "
				+ "  AND (IsNull(CI.FirstName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FirstName,''))+'%'  "
				+ "  OR Isnull(CI.LastName,'') LIKE '%'+IsNull(@SearchString,Isnull(CI.LastName,''))+'%' "
				+ "  OR IsNull(CI.UtilityAccountNumber,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.UtilityAccountNumber,''))+'%' "
				+ "  OR IsNull(CI.EmailID,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.EmailID,''))+'%' "
				+ "  OR IsNull(CI.MobilePhone,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.MobilePhone,''))+'%'  "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+IsNull(@SearchString,IsNull(CI.FullName,''))+'%'  "
				+ "  OR IsNull(CI.[address1],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address1],''))+'%' "
				+ "  OR IsNull(CI.[address2],'') LIKE '%'+IsNull(@SearchString,IsNull(CI.[address2],''))+'%' "
				+ "  OR IsNull(CI.FullName,'') LIKE '%'+ IsNull(@SearchString,IsNull(CI.FullName,'')) +'%' "
				+ "  OR IsNull(Cast(CI.ZipCode as Varchar(32)),'') LIKE '%'+ IsNull(@SearchString,IsNull(Cast(CI.ZipCode as Varchar(32)),'')) +'%' "
				+ "     )" + " ) ";
		return q;
	}

	public static String getFieldValueUniSearch() {
		String q = "select top 1 * from Customerinfo  ORDER BY FullName ASC";
		return q;
	}

	public static String getValueByFieldName(String accountNumber, String AddressType, String AccountStatus,
											 String customerNumber, String zipCode, String cityName) {
		String q = "select c.UtilityAccountNumber,c.FullName,c.EmailID,c.Address1,c.ZipCode,c.CustomerNo,c.AddressType,c.AccountStatus,c.MobilePhone,c.CityName from customerinfo c Join UserAccount a on c.AccountNumber=a.AccountNumber where ";
		boolean flag = false;
		if (!customerNumber.equalsIgnoreCase("null")) {
			q = q + "CustomerNo like '%" + customerNumber + "%'";
			flag = true;
		}

		if (!accountNumber.equalsIgnoreCase("null")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "c.UtilityAccountNumber like '%" + accountNumber + "%'";
			flag = true;
		}
		if (!AccountStatus.equalsIgnoreCase("---Select---")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "AccountStatus like '%" + AccountStatus + "%'";
			flag = true;
		}
		if (!AddressType.equalsIgnoreCase("---Select---")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "AddressType like '%" + AddressType + "%'";
			flag = true;
		}
		if (!zipCode.equalsIgnoreCase("null")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "zipCode like '%" + zipCode + "%'";
			flag = true;
		}
		if (!cityName.equalsIgnoreCase("null")) {
			if (flag) {
				q = q + " and ";
			}
			q = q + "cityName like '%" + cityName + "%'";
			flag = true;
		}
		q = q + " ORDER BY FullName ASC";
		return q;

	}

	public static String getValueByFieldNameServiceAccount(String accountNumber, String AddressType, String status,
														   String customerNumber, String isPaperless, String linkedUsers, String zipCode, String cityName) {
		String q = "declare @utilityAccountNumber varchar(50)='" + accountNumber + "'"
				+ ", @CustomerNo	VARCHAR(100)  = '" + customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null"
				+ ", @AccountType   VARCHAR(100) = '" + AddressType + "'" + ", @Status		VARCHAR(100) = '" + status
				+ "'" + ", @IsPaperLess   BIT			 = '" + isPaperless + "'" + ", @ZipCode		VARCHAR(100) = '"
				+ zipCode + "'" + ", @City			VARCHAR(100) = '" + cityName + "'"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = '" + linkedUsers + "'"
				+ ", @UserType		VARCHAR(50)  = NULL"

				+ " If(@status='Active')" + " Begin"

				+ " SELECT RN,[ServiceAccountnumber],[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[AccountType]"
				+ ", [LinkedUsers],Premise,[AccountStatus],[AccountNumber],[AddressType],[LinkedUserGuest],[LinkedUserPM]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ " , cust.customerNo AS 'CustomerNumber'"
				+ " , cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone'"
				+ " , case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ " , isnull(linkeduser.LinkedUser,0) as 'LinkedUsers'"
				+ " , replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case when cust.AccountStatusID in(0,1,3) then 'Active'  else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest"
				+ ", isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM" + " FROM customerinfo cust (NOLOCK) "
				+ " left JOIN " + " (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK)" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 GROUP BY PA.accountNumber"
				+ " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=11 GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=6"
				+ " GROUP BY PA.accountNumber )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber WHERE CustomerID NOT IN (1,-1)"
				+ " and cust.[Accountstatusid] in (0,1,3)"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,'')) or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"

				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ "  AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,''))"
				+ " AND (isnull(@NoOfLinkedUser,'') ='' or  (case when isnull(linkeduser.LinkedUser,0)=0 or linkeduser.LinkedUser<=1 then '0-1' "
				+ " when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5'  when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10'"
				+ "  when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,'')) )  TBL"

				+ "	End "

				+ " If(@status='Inactive')" + " Begin"

				+ " SELECT RN,[ServiceAccountnumber],[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[AccountType]"
				+ ", [LinkedUsers],Premise,[AccountStatus],[AccountNumber],[AddressType],[LinkedUserGuest],[LinkedUserPM]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ " , cust.customerNo AS 'CustomerNumber'"
				+ " , cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ ", isnull(linkeduser.LinkedUser,0) as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case when cust.AccountStatusID in(0,1,3) then 'Active'  else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest"
				+ ", isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM" + " FROM customerinfo cust (NOLOCK) "
				+ " left JOIN " + " (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK)" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 GROUP BY PA.accountNumber"
				+ " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=11 GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ "  SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=6"
				+ " GROUP BY PA.accountNumber )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber WHERE CustomerID NOT IN (1,-1)"
				+ " and cust.[Accountstatusid] in (2,4)"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,'')) or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"

				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ "  AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,''))"
				+ " AND (isnull(@NoOfLinkedUser,'') ='' or  (case when isnull(linkeduser.LinkedUser,0)=0 or linkeduser.LinkedUser<=1 then '0-1' "
				+ "  when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5'  when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10'"
				+ "  when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,'')) )  TBL"

				+ " End"

				+ " Else"

				+ " SELECT RN,[ServiceAccountnumber],[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[AccountType]"
				+ ", [LinkedUsers],Premise,[AccountStatus],[AccountNumber],[AddressType],[LinkedUserGuest],[LinkedUserPM]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ ", cust.customerNo AS 'CustomerNumber'"
				+ ", cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ " , case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ ", isnull(linkeduser.LinkedUser,0) as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case when cust.AccountStatusID in(0,1,3) then 'Active'  else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest"
				+ ", isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM" + " FROM customerinfo cust (NOLOCK) "
				+ " left JOIN " + " (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK)" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 GROUP BY PA.accountNumber"
				+ " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) +"
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=11 GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 And PU.Roleid=6"
				+ " GROUP BY PA.accountNumber )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber WHERE CustomerID NOT IN (1,-1)"
				+ " and cust.[Accountstatusid] in (0,1,3,2,4)"
				+ " (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,'')) or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"

				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ " AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,''))"
				+ " AND (isnull(@NoOfLinkedUser,'') ='' or  (case when isnull(linkeduser.LinkedUser,0)=0 or linkeduser.LinkedUser<=1 then '0-1' "
				+ "  when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5'  when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10'"
				+ "  when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,'')) )  TBL";
		return q;

	}

	public static String getValueByFieldNameCustomer(String customerNumber, String zipCode, String accountNumber,
													 String contactNo, String AddressType, String cityName, String firstName, String lastName, String email) {
		String q = "declare @utilityAccountNumber varchar(50)='" + accountNumber + "'"
				+ ", @CustomerNo	VARCHAR(100)  = '" + customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null"
				+ ", @AccountType   VARCHAR(100) = '" + AddressType + "'" + ", @Status		VARCHAR(100) = Null"
				+ ", @IsPaperLess   BIT			 = Null" + ", @ZipCode		VARCHAR(100) = '" + zipCode + "'"
				+ ", @City			VARCHAR(100) = '" + cityName + "'" + ", @FName			VARCHAR(100) = '"
				+ firstName + "'" + ", @LName			VARCHAR(50)  = '" + lastName + "'"
				+ ", @PhoneNo		VARCHAR(20)  = '" + contactNo + "'" + ", @EmailID		VARCHAR(100) = '" + email
				+ "'" + ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = NULL"
				+ ", @UserType		VARCHAR(50)  = NULL" + " IF (@Status IS NULL OR @Status='Active')" + " begin"
				+ " SELECT distinct RN,CustomerID,[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[LinkedAccount],[MailingAddress],[CustomerStatus]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY FullName DESC) AS RN,cust.CustomerID,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", isnull(acclinked.LinkedAccount,0) as 'LinkedAccount'"
				+ ", replace(replace(isnull(CCA.address1,'')+','+isnull(CCA.address2,'')+','+isnull(CCA.cityname,'')+','+isnull(CCA.StateCode,'')+','+isnull(CCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ ", Cust.CustomerStatus	" + " From"
				+ " (SELECT DISTINCT A.customerid,A.customerNo,A.FirstName,A.LastName,A.FullName,A.EmailID,A.MobilePhone"
				+ ", case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ " FROM customerinfo (NOLOCK) A"
				+ " left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ " WHERE A.CustomerID NOT IN (1,-1)"
				+ " AND (isnull(@CustomerNo,'')  ='' or A.customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@FName,'')  ='' or A.FirstName like '%'+isnull(@FName,'')+'%')"
				+ " AND (isnull(@LName,'')  ='' or A.LastName like '%'+isnull(@LName,'')+'%')"
				+ " AND (isnull(@Status,'') ='' or (case when A.AccountStatusID in (0,1,3) then 'Active' end)=isnull(@Status,''))"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or A.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@AccountType,'')  ='' " + " or (case when A.addresstypeid=1 then 'Residential' "
				+ " when A.addresstypeid=2 and A.PortalAccessTypeID=1 Then 'Enterprise' Else 'Commercial' end)=isnull(@AccountType,''))"
				+ " AND (isnull(@PhoneNo,'')  ='' or A.MobilePhone  like '%'+isnull(@PhoneNo,'')+'%')"
				+ " AND (isnull(@EmailId,'')  ='' or A.EmailID like '%'+isnull(@EmailId,'')+'%')"
				+ " AND (isnull(@ZipCode,'') ='' or A.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'')  ='' or A.cityname=isnull(@City,'')) " + " ) cust"
				+ " left JOIN customercommunicationaddress  CCA (NOLOCK) ON cust.CustomerID=CCA.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAcc "
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1) "
				+ " AND (isnull(@utilityAccountNumber,'') ='' or UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " GROUP BY CustomerID" + " )AS linkedacc ON cust.CustomerID=linkedacc.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAccount "
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1) " + " GROUP BY CustomerID"
				+ " )AS acclinked ON cust.CustomerID=acclinked.CustomerID"

				+ " ) TBL"

				+ " END"

				+ " IF (@status='Inactive')" + " begin"
				+ " SELECT distinct RN,CustomerID,[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[LinkedAccount],[MailingAddress],[CustomerStatus]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY FullName DESC) AS RN,cust.CustomerID,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", isnull(acclinked.LinkedAccount,0) as 'LinkedAccount'"
				+ ", replace(replace(isnull(CCA.address1,'')+','+isnull(CCA.address2,'')+','+isnull(CCA.cityname,'')+','+isnull(CCA.StateCode,'')+','+isnull(CCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ ", 'Inactive' as 'CustomerStatus'" + " From"
				+ " (SELECT DISTINCT CI.customerid,CI.customerNo,CI.FirstName,CI.LastName,CI.FullName,CI.EmailID,CI.MobilePhone"
				+ " FROM customerinfo CI (NOLOCK)  " + " Left Join" + " (SELECT DISTINCT customerid "
				+ " FROM CustomerInfo(NOLOCK) " + " WHERE CustomerID NOT IN (1,-1) AND AccountStatusID IN (0,1,3)"
				+ " )CIA on CI.customerid=CIA.customerid"
				+ " WHERE CI.CustomerID NOT IN (1,-1) AND CIA.customerid is null"
				+ " AND (isnull(@CustomerNo,'')  ='' or customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@FName,'')  ='' or FirstName like '%'+isnull(@FName,'')+'%')"
				+ " AND (isnull(@LName,'')  ='' or LastName like '%'+isnull(@LName,'')+'%')"
				+ " AND (isnull(@Status,'') ='' or (case when AccountStatusID not in (0,1,3) then 'Inactive' end)=isnull(@Status,''))"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@AccountType,'')  ='' " + " or (case when CI.addresstypeid=1 then 'Residential' "
				+ " when CI.addresstypeid=2 and CI.PortalAccessTypeID=1 Then 'Enterprise' Else 'Commercial' end)=isnull(@AccountType,''))"
				+ "	AND (isnull(@PhoneNo,'')  ='' or MobilePhone like '%'+isnull(@PhoneNo,'')+'%')"
				+ "	AND (isnull(@EmailId,'')  ='' or EmailID like '%'+isnull(@EmailId,'')+'%')"
				+ "	AND (isnull(@ZipCode,'') ='' or ZipCode=isnull(@ZipCode,''))"
				+ "	AND (isnull(@City,'')  ='' or cityname=isnull(@City,'')) " + " ) cust "
				+ " LEFT JOIN customercommunicationaddress  CCA (NOLOCK) ON cust.CustomerID=CCA.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAcc"
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1) "
				+ " AND (isnull(@utilityAccountNumber,'') ='' or UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " GROUP BY CustomerID" + " )AS linkedacc ON cust.CustomerID=linkedacc.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAccount"
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1) " + " GROUP BY CustomerID"
				+ " )AS acclinked ON cust.CustomerID=acclinked.CustomerID"

				+ " ) TBL" + " End";
		return q;

	}

	public static String getValueByFieldNameUser(String accountNumber, String AddressType, String status,
												 String userType, String linkedAccount, String role, String isSocial, String customerNumber,
												 String contactNo, String zipCode, String cityName, String firstName, String lastName, String email) {
		String q = "declare @utilityAccountNumber varchar(50)='" + accountNumber + "'"
				+ ", @CustomerNo	VARCHAR(100)  = '" + customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null"
				+ ", @AccountType   VARCHAR(100) = '" + AddressType + "'" + ", @StatusID		VARCHAR(100) = '"
				+ status + "'" + ", @IsPaperLess   BIT			 = Null" + ", @ZipCode		VARCHAR(100) = '" + zipCode
				+ "'" + ", @City			VARCHAR(100) = '" + cityName + "'" + ", @FName			VARCHAR(100) = '"
				+ firstName + "'" + ", @LName			VARCHAR(50)  = '" + lastName + "'"
				+ ", @PhoneNo		VARCHAR(20)  = '" + contactNo + "'" + ", @EmailID		VARCHAR(100) = '" + email
				+ "'" + ", @LockedID		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = '" + isSocial + "'"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = '" + linkedAccount + "'" + ", @Role			VARCHAR(30)  = '" + role
				+ "'" + ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = NULL"
				+ ", @UserType		VARCHAR(50)  = '" + userType + "'"

				+ " SELECT distinct RN,Userid,Fullname as Name,UserName,EmailID,MobilePhone as [PrimaryPhone],"
				+ " isnull(LinkedAcc,0) as [LinkedAccounts],[MailingAddress],[UserStatus],[LockStatus],[UserType]"
				+ " from" + " (select row_Number() over(ORDER BY  U.FullName DESC) AS RN,U.userid,U.Fullname"
				+ ", U.UserName,U.EmailID,U.MobilePhone ,accLinked.LinkedAcc,linkedacc.LinkedAccount"
				+ ", replace(replace(isnull(UCA.address1,'')+','+isnull(UCA.address2,'')+','+isnull(UCA.cityname,'')+','+isnull(UCA.StateCode,'')+','+isnull(UCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ ", case when U.Status=0 then 'Pending Activation'"
				+ "  when U.[Status]=1 and De.Status is null then 'Active'"
				+ " when U.[Status]=2 or  De.[Status]=1 then 'Inactive'	" + "  when U.[Status]=4 then 'Locked' "
				+ "  when U.Status=5 then 'Temp Locked' end as 'UserStatus'"
				+ ", case when U.IsLocked=0 then 'Unlocked'" + " when U.IsLocked=1 then 'Locked'	"
				+ " when U.IsLocked=2 then 'Locked by Admin'	 end as 'LockStatus'"
				+ ", case when U.UserType =4 then 'Enterprise' else 'Mass Market' end as  'UserType'"
				+ " FROM [user] U (NOLOCK) " + " LEFT JOIN DeletionRequest De (NOLOCK)" + " On U.userid=De.Userid  "
				+ " and De.[Status]=1 and De.[CancelledDate] is null" + " JOIN "
				+ " (SELECT UA.userid,isnull(count(distinct UA.accountNumber),0) AS LinkedAccount " + " FROM "
				+ " (Select UserID,AccountNumber,RoleID from useraccount (NOLOCK)" + " Union all"
				+ "  Select PU.Userid,PA.Accountnumber,case when PU.RoleID=10 then 3 else PU.RoleID end as RoleID"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + " where PA.IsActive=1 "
				+ " )UA" + " JOIN Customerinfo CI ON UA.Accountnumber=CI.Accountnumber"
				+ " WHERE CI.CustomerID NOT IN (1,-1)" + " AND (isnull(@Role,'')='' or"
				+ " (case when UA.RoleID=1 then 'Guest' " + "	when UA.RoleID=2 then 'Property Manager'"
				+ "	when UA.RoleID=3 then 'Owner'" + "	when UA.RoleID=6 then 'Portfolio Manager'"
				+ "	when UA.RoleID=11 then 'Portfolio Guest' " + " end)=isnull(@Role,''))"
				+ " AND (isnull(@ZipCode,'') ='' or CI.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'') ='' or CI.cityname=isnull(@City,''))"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or CI.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ "  AND (isnull(@AccountType,'') ='' or (CI.PortalAccessTypeID=1 And CI.PortalAccessType=isnull(@AccountType,'')) or (CI.PortalAccessTypeID=0 And CI.AddressType=isnull(@AccountType,'')))"
				+ " GROUP BY UA.userid" + " )AS linkedacc ON U.userid=linkedacc.userid" + "	JOIN "
				+ " (SELECT userid,isnull(count(distinct accountNumber),0) AS LinkedAcc " + "  FROM "
				+ " (Select UserID,AccountNumber from useraccount (NOLOCK)" + " Union all"
				+ " Select PU.Userid,PA.Accountnumber" + " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID  " + " where PA.IsActive=1"
				+ " )UA" + " GROUP BY userid" + " )AS accLinked ON U.userid=accLinked.userid"
				+ " LEFT JOIN usercommunicationaddress UCA ON U.userid=UCA.userid AND UCA.MailAddressType=1 "
				+ " where U.userid NOT IN (-1,1) AND U.UserType Not In(3,5) AND U.[STATUS]<>3   "

				+ " AND (isnull(@FName,'') ='' or isnull(U.FirstName,'') like '%'+isnull(@FName,'')+'%')"
				+ " AND (isnull(@LName,'') ='' or isnull(U.LastName,'') like '%'+isnull(@LName,'')+'%')"
				+ " AND (isnull(@StatusID,'') ='' or U.Status=isnull(@StatusID,'')) "
				+ " AND (isnull(@LockedID,'') ='' or U.IsLocked=isnull(@LockedID,'')) "
				+ " AND (isnull(@NoOfLinkedAcc,'') ='' or "

				+ " (case when accLinked.LinkedAcc=0 or accLinked.LinkedAcc<=1 then '0-1' "
				+ " when accLinked.LinkedAcc>1 and accLinked.LinkedAcc<=5 then '2-5'"
				+ " when accLinked.LinkedAcc>5 and accLinked.LinkedAcc<=10 then '6-10'"
				+ " when accLinked.LinkedAcc>10 then '10+' end) =isnull(@NoOfLinkedAcc,'')) "
				+ " AND (isnull(@PhoneNo,'') ='' or U.MobilePhone like '%'+isnull(@PhoneNo,'')+'%')"
				+ " AND (isnull(@EmailId,'') ='' or U.EmailID like '%'+isnull(@EmailId,'')+'%')"
				+ " AND (cast(U.CreatedDate as date) between isnull(@FromRegDate,'2014-01-01') and isnull(@ToRegDate,cast(getdate() as date))) "
				+ " AND (isnull(@SocialLogin,'') ='' or (case when U.loginmode=2 then 'Yes' else 'No' end) =isnull(@SocialLogin,''))"
				+ " AND (isnull(@UserType,'') ='' or (Case when U.UserType=4 then 'Enterprise' else 'Mass Market' end) = isnull(@UserType,'')) "
				+ " ) TBL";
		return q;

	}

	public static String getCountOfAdvSearchResultServiceAccount(String customerNumber, String zipCode,
																 String accountNumber, String AccountStatus, String AddressType, String cityName, String isPaperless,
																 String linkedUsers) {

		String q = "declare @utilityAccountNumber varchar(50)='" + accountNumber + "'" + ", @PageIndex		INT	= 1"
				+ ", @PageSize		INT	= 200" + ", @timeoffset	INT	= 0" + ", @CustomerNo	VARCHAR(100)  = '"
				+ customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = '"
				+ AddressType + "'" + ", @Status		VARCHAR(100) = '" + AccountStatus + "'"
				+ ", @IsPaperLess   VARCHAR(100)			 = '" + isPaperless + "'"
				+ ", @ZipCode		VARCHAR(100) = '" + zipCode + "'" + ", @City			VARCHAR(100) = '" + cityName
				+ "'" + ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = '" + linkedUsers + "'"
				+ ", @UserType		VARCHAR(50)  = NULL"

				+ " If(@status='Active')" + " Begin" + " SELECT count(*) as 'TotalRecord'" + " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber' ,cust.customerNo AS 'CustomerNumber'"
				+ ", cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone'"
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ ", linkeduser.LinkedUser as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case 	when cust.AccountStatusID in(0,1,3) then 'Active' else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest ,isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM"
				+ " FROM customerinfo cust (NOLOCK)  left JOIN  (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK) GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  " + " AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1 where PA.IsActive=1"
				+ " GROUP BY PA.accountNumber" + " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber, isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK) where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0)  AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber, isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber"
				+ " WHERE CustomerID NOT IN (1,-1) AND (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " and cust.[Accountstatusid] in (0,1,3)"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,'')) "
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,''))"
				+ " or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"
				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ " AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode, '')) AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,'')) AND (isnull(@NoOfLinkedUser,'') ='' or (case when isnull(linkeduser.LinkedUser,0)=0 or "
				+ " linkeduser.LinkedUser<=1 then '0-1' when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5' when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10' when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,''))"
				+ " ) TBL" + " End" + " if(@status='Inactive')" + " Begin" + " SELECT count(*) as 'TotalRecord'"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber' ,cust.customerNo AS 'CustomerNumber'"
				+ ", cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ ", linkeduser.LinkedUser as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case 	when cust.AccountStatusID in(0,1,3) then 'Active' else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest ,isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM"
				+ " FROM customerinfo cust (NOLOCK)  left JOIN  (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK) GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  " + " AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1 where PA.IsActive=1"
				+ " GROUP BY PA.accountNumber" + " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber, isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK) where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0)  AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber, isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber"
				+ " WHERE CustomerID NOT IN (1,-1) AND (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " and cust.[Accountstatusid] in (2,4)"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,'')) "
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,''))"
				+ " or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"
				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ " AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode, '')) AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,'')) AND (isnull(@NoOfLinkedUser,'') ='' or (case when isnull(linkeduser.LinkedUser,0)=0 or "
				+ " linkeduser.LinkedUser<=1 then '0-1' when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5' when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10' when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,''))"
				+ " ) TBL" + " End" + " if(@status='')" + " Begin" + " SELECT count(*) as 'TotalRecord'" + " from"
				+ " (SELECT row_number() over(ORDER BY cust.FullName desc) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber' ,cust.customerNo AS 'CustomerNumber'"
				+ ", cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType End AS 'AccountType'"
				+ ", linkeduser.LinkedUser as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case 	when cust.AccountStatusID in(0,1,3) then 'Active' else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest ,isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM"
				+ " FROM customerinfo cust (NOLOCK)  left JOIN  (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ " FROM useraccount (NOLOCK) GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  " + " AS LinkedUser"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1 where PA.IsActive=1"
				+ " GROUP BY PA.accountNumber" + " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber, isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK) where Roleid=1 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0)  AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2 GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber, isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1  GROUP BY PA.accountNumber"
				+ " )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber"
				+ " JOIN Account acc ON cust.AccountNumber=acc.AccountNumber"
				+ " WHERE CustomerID NOT IN (1,-1) AND (isnull(@utilityAccountNumber,'') ='' or cust.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " and cust.[Accountstatusid] in (0,1,2,3,4)"
				+ " AND (isnull(@CustomerNo,'') ='' or cust.customerNo=isnull(@CustomerNo,'')) "
				+ " AND (isnull(@AccountType,'') ='' or (cust.PortalAccessTypeID=1 And cust.PortalAccessType=isnull(@AccountType,''))"
				+ " or (cust.PortalAccessTypeID=0 And cust.AddressType=isnull(@AccountType,'')))"
				+ " AND (isnull(CAST(@IsPaperLess AS VARCHAR),'') = '' or isnull(Acc.Paperless,0)=CAST(@IsPaperLess AS VARCHAR))"
				+ " AND (isnull(@ZipCode,'') ='' or cust.ZipCode=isnull(@ZipCode, '')) AND (isnull(@City,'') ='' or cust.cityname=isnull(@City,'')) AND (isnull(@NoOfLinkedUser,'') ='' or (case when isnull(linkeduser.LinkedUser,0)=0 or "
				+ " linkeduser.LinkedUser<=1 then '0-1' when linkeduser.LinkedUser>1 and linkeduser.LinkedUser<=5 then '2-5' when linkeduser.LinkedUser>5 and linkeduser.LinkedUser<=10 then '6-10' when linkeduser.LinkedUser>10 then '10+' end) =isnull(@NoOfLinkedUser,''))"
				+ " ) TBL" + " End";
		return q;

	}

	public static String getCountOfAdvSearchResultCustomer(String customerNumber, String zipCode, String accountNumber,
														   String contactNo, String AddressType, String cityName, String firstName, String lastName, String email) {

		String q = "declare @utilityAccountNumber varchar(50)='" + accountNumber + "'"
				+ ", @CustomerNo	VARCHAR(100)  = '" + customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null"
				+ ", @AccountType   VARCHAR(100) = '" + AddressType + "'" + ", @Status		VARCHAR(100) = Null"
				+ ", @IsPaperLess   BIT			 = Null" + ", @ZipCode		VARCHAR(100) = '" + zipCode + "'"
				+ ", @City			VARCHAR(100) = '" + cityName + "'" + ", @FName			VARCHAR(100) = '"
				+ firstName + "'" + ", @LName			VARCHAR(50)  = '" + lastName + "'"
				+ ", @PhoneNo		VARCHAR(20)  = '" + contactNo + "'" + ", @EmailID		VARCHAR(100) = '" + email
				+ "'" + ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = NULL"
				+ ", @UserType		VARCHAR(50)  = NULL" + " SELECT count(distinct CustomerID) as 'TotalRecord'"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY FullName desc) AS RN,cust.CustomerID,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", isnull(linkedacc.LinkedAccount,0) as 'LinkedAccount'"
				+ ", replace(replace(isnull(CCA.address1,'')+','+isnull(CCA.address2,'')+','+isnull(CCA.cityname,'')+','+isnull(CCA.StateCode,'')+','+isnull(CCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ " From"
				+ " (SELECT DISTINCT CI.customerid,CI.customerNo,CI.FirstName,CI.LastName,CI.FullName,CI.EmailID,CI.MobilePhone"
				+ " FROM customerinfo CI (NOLOCK) " + " Left Join" + " (SELECT DISTINCT customerid "
				+ "	FROM CustomerInfo(NOLOCK) " + "	WHERE CustomerID NOT IN (1,-1) AND AccountStatusID IN (0,1,3)"
				+ " )CIA on CI.customerid=CIA.customerid" + " WHERE CI.CustomerID NOT IN (1,-1) "
				+ " AND (isnull(@CustomerNo,'')  ='' or customerNo=isnull(@CustomerNo,''))"
				+ " AND (isnull(@FName,'')  ='' or FirstName like '%'+isnull(@FName,'')+'%')"
				+ " AND (isnull(@LName,'')  ='' or LastName like '%'+isnull(@LName,'')+'%')"
				+ " AND (isnull(@Status,'') ='' or (case when AccountStatusID not in (0,1,3) then 'Inactive' end)=isnull(@Status,''))"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@AccountType,'')  ='' " + " or (case when CI.addresstypeid=1 then 'Residential' "
				+ " when CI.addresstypeid=2 and CI.PortalAccessTypeID=1 Then 'Enterprise' Else 'Commercial' end)=isnull(@AccountType,''))"
				+ "	AND (isnull(@PhoneNo,'')  ='' or MobilePhone like '%'+isnull(@PhoneNo,'')+'%')"
				+ "	AND (isnull(@EmailId,'')  ='' or EmailID like '%'+isnull(@EmailId,'')+'%')"
				+ "	AND (isnull(@ZipCode,'') ='' or ZipCode=isnull(@ZipCode,''))"
				+ "	AND (isnull(@City,'')  ='' or cityname=isnull(@City,'')) " + " ) cust "
				+ " left JOIN customercommunicationaddress  CCA (NOLOCK) ON cust.CustomerID=CCA.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAccount "
				+ " FROM customerinfo (NOLOCK)"
				+ " WHERE CustomerID NOT IN (1,-1) AND (isnull(@utilityAccountNumber,'') ='' or UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " GROUP BY CustomerID" + " )AS linkedacc ON cust.CustomerID=linkedacc.CustomerID"

				+ " ) TBL";
		return q;

	}

	public static String getCountOfAdvSearchResultUser(String accountNumber, String AddressType, String status,
													   String userType, String linkedAccount, String role, String isSocial, String customerNumber,
													   String contactNo, String zipCode, String cityName, String firstName, String lastName, String email) {

		String q = " declare @utilityAccountNumber varchar(50)='" + accountNumber + "'" + ", @PageIndex		INT	= 1"
				+ ", @PageSize		INT	= 200" + ", @timeoffset	INT	= 0" + ", @CustomerNo	VARCHAR(100)  = '"
				+ customerNumber + "'" + ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = '"
				+ AddressType + "'" + ", @Status		VARCHAR(100)  = '" + status + "'"
				+ ", @IsPaperLess   BIT			 = Null" + ", @ZipCode		VARCHAR(100) = '" + zipCode + "'"
				+ ", @City			VARCHAR(100) = '" + cityName + "'" + ", @FName			VARCHAR(100) = '"
				+ firstName + "'" + ", @LName			VARCHAR(50)  = '" + lastName + "'"
				+ ", @PhoneNo		VARCHAR(20)  = '" + contactNo + "'" + ", @EmailID		VARCHAR(100) = '" + email
				+ "'" + ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = '" + isSocial + "'"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = '" + linkedAccount + "'" + ", @Role			VARCHAR(30)  = '" + role
				+ "'" + ", @IsAutoSuggest BIT			 = 0" + ", @SearchValue   VARCHAR(255) = NULL"
				+ ", @Customerid	VARCHAR(20)  = NULL" + ", @OrderColumn   VARCHAR(50)  = NULL"
				+ ", @SortOrder		VARCHAR(50)  = NULL" + ", @NoOfLinkedUser VARCHAR(20)  = Null"
				+ ", @UserType		VARCHAR(50)  = '" + userType + "'" + ", @LockedID     TINYINT       =NULL"
				+ ", @StatusID     TINYINT       =NULL"

				+ " SELECT @LockedID= masterCode FROM commonmaster WHERE mastertype='LockStatus' AND Name=@Locked"
				+ " SELECT @StatusID =masterCode FROM commonmaster WHERE mastertype='CustomerStatus' AND Name=@Status"

				+ " SELECT count(distinct Userid) as 'TotalRecord' " + " from"
				+ " (select row_Number() over(ORDER BY FullName desc) AS RN,U.userid,U.Fullname,U.UserName"
				+ " ,U.EmailID,U.MobilePhone ,linkedacc.LinkedAccount"
				+ ", replace(replace(isnull(UCA.address1,'')+','+isnull(UCA.address2,'')+','+isnull(UCA.cityname,'')+','+isnull(UCA.StateCode,'')+','+isnull(UCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ " FROM [user] U (NOLOCK) "
				+ " JOIN (SELECT UA.userid,isnull(count(distinct UA.accountNumber),0) AS LinkedAccount "
				+ " FROM (Select UserID,AccountNumber,RoleID from useraccount (NOLOCK)" + " Union all"
				+ " Select PU.Userid,PA.Accountnumber,case when PU.RoleID=10 then 3 else PU.RoleID end as RoleID"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + " where PA.IsActive=1 "
				+ " )UA" + " JOIN customerinfo CI ON UA.Accountnumber=CI.Accountnumber"
				+ " WHERE CI.CustomerID NOT IN (1,-1)" + " AND (isnull(@Role,'')  ='' or "
				+ " (case when UA.RoleID=1 then 'Guest'" + "	  when UA.RoleID=2 then 'Property Manager'"
				+ "	  when UA.RoleID=3 then 'Owner'" + "	  when UA.RoleID=6 then 'Portfolio Manager'"
				+ "	  when UA.RoleID=11 then 'Portfolio Guest' " + "	end)=isnull(@Role,''))"
				+ " AND (isnull(@ZipCode,'') ='' or CI.ZipCode=isnull(@ZipCode,''))"
				+ " AND (isnull(@City,'')  ='' or CI.cityname=isnull(@City,''))"
				+ " AND (isnull(@utilityAccountNumber,'') ='' or CI.UtilityAccountNumber=isnull(@utilityAccountNumber,''))"
				+ " AND (isnull(@AccountType,'') ='' or (CI.PortalAccessTypeID=1 And CI.PortalAccessType=isnull(@AccountType,'')) or (CI.PortalAccessTypeID=0 And CI.AddressType=isnull(@AccountType,'')))"
				+ " GROUP BY UA.userid" + " )AS linkedacc ON U.userid=linkedacc.userid" + " JOIN "
				+ " (SELECT userid,isnull(count(distinct accountNumber),0) AS LinkedAcc " + " FROM "
				+ " (Select UserID,AccountNumber from useraccount (NOLOCK)" + " Union all"
				+ " Select PU.Userid,PA.Accountnumber" + " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + " where PA.IsActive=1"
				+ " )UA" + " GROUP BY userid" + " )AS accLinked ON U.userid=accLinked.userid"
				+ " LEFT JOIN usercommunicationaddress UCA ON U.userid=UCA.userid AND UCA.MailAddressType=1"
				+ " WHERE U.userid NOT IN (-1,1) AND U.UserType Not In(3,5) AND U.[STATUS]<>3 "
				+ " AND (isnull(@FName,'')  ='' or isnull(U.FirstName,'') like '%'+isnull(@FName,'')+'%')"
				+ " AND (isnull(@LName,'')  ='' or isnull(U.LastName,'') like '%'+isnull(@LName,'')+'%')"
				+ " AND (isnull(@StatusID,'')  ='' or U.Status=isnull(@StatusID,'')) "
				+ " AND (isnull(@LockedID,'')  ='' or U.IsLocked=isnull(@LockedID,'')) "
				+ " AND (isnull(@NoOfLinkedAcc,'')  ='' or"
				+ "  (case when accLinked.LinkedAcc=0 or accLinked.LinkedAcc<=1 then '0-1' "
				+ "	  when accLinked.LinkedAcc>1 and accLinked.LinkedAcc<=5 then '2-5'"
				+ "	  when accLinked.LinkedAcc>5 and accLinked.LinkedAcc<=10 then '6-10'"
				+ "	  when accLinked.LinkedAcc>10 then '10+' end) =isnull(@NoOfLinkedAcc,'')) "
				+ " AND (isnull(@PhoneNo,'')  ='' or U.MobilePhone like '%'+isnull(@PhoneNo,'')+'%')"
				+ " AND (isnull(@EmailId,'')  ='' or U.EmailID like '%'+isnull(@EmailId,'')+'%')"
				+ " AND (cast(U.CreatedDate as date) between isnull(@FromRegDate,'2014-01-01') and isnull(@ToRegDate,cast(getdate() as date)))"
				+ " AND (isnull(@SocialLogin,'')  ='' or (case when U.loginmode=2 then 'Yes' else 'No' end) =isnull(@SocialLogin,'')) "
				+ " AND (isnull(@UserType,'')  ='' or (Case when U.UserType=4 then 'Enterprise' else 'Mass Market' end) = isnull(@UserType,''))"
				+ " ) TBL";
		return q;

	}

	public static String getUniSearchResultMode1(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"

				+ " SELECT [ServiceAccountnumber],[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[AccountType]"
				+ ", [LinkedUsers],Premise,[AccountStatus],[AccountNumber],[AddressType],[LinkedUserGuest],[LinkedUserPM]"
				+ " from"
				+ " (SELECT row_number() over(ORDER BY FULLNAME DESC) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ ", cust.customerNo AS 'CustomerNumber',cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail'"
				+ ", cust.MobilePhone AS 'PrimaryPhone' "
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType END AS 'AccountType'"
				+ ", ISNULL(linkeduser.LinkedUser,0) as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case "

				+ " when cust.AccountStatusID in(0,1,3) then 'Active' else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest"
				+ ", isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM" + ", cust.PortalAccessType"
				+ " FROM customerinfo cust (NOLOCK) "
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ "	FROM useraccount (NOLOCK)" + "	GROUP BY AccountNumber" + "	Union All"
				+ "	SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUser"
				+ "	FROM	 PortfolioAccount	PA	WITH(NOLOCK) "
				+ "	INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + "	where PA.IsActive=1"
				+ "	GROUP BY PA.accountNumber" + " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=1" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID "
				+ " where PA.IsActive=1 And PU.Roleid=11" + " GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM  PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID"
				+ " where PA.IsActive=1 And PU.Roleid=6" + " GROUP BY PA.accountNumber"
				+ " )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber" + " WHERE CustomerID NOT IN (1,-1) "

				+ " AND (ISNULL(@SearchValue,'')='' OR (cust.UtilityAccountNumber= isnull(@SearchValue,'')"
				+ " OR (cust.FullName = isnull(@SearchValue,'')"
				+ " OR (cust.EmailID = isnull(@SearchValue,'') OR (cust.MobilePhone = isnull(@SearchValue,'') "
				+ " OR (cust.cityname = isnull(@SearchValue,'') or cust.ZipCode=isnull(@SearchValue,'')))))))  "
				+ " )TBL";
		return q;
	}

	public static String getUniSearchResultMode5(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"

				+ " SELECT distinct RN,CustomerID,[CustomerNumber],[CustomerName],[Customersemail],[PrimaryPhone],[LinkedAccount]"
				+ ", [MailingAddress],[CustomerStatus]" + " from"
				+ " (SELECT row_number() over(ORDER BY FULLNAME DESC) AS RN,cust.CustomerID,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", isnull(linkedacc.LinkedAccount,0) as 'LinkedAccount'"
				+ ", replace(replace(isnull(CCA.address1,'')+','+isnull(CCA.address2,'')+','+isnull(CCA.cityname,'')+','+isnull(CCA.StateCode,'')+','+isnull(CCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ ", [CustomerStatus]" + " From"
				+ " (SELECT DISTINCT a.customerid,a.customerNo,a.FullName,a.EmailID,a.MobilePhone"
				+ ", case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "   FROM customerinfo (NOLOCK) A"
				+ "	left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "	WHERE a.CustomerID NOT IN (1,-1) And (ISNULL(@SearchValue,'')='' "
				+ "	OR (a.cityname like '%'+@SearchValue+'%') OR (a.ZipCode like '%'+@SearchValue+'%') "
				+ "	OR (a.FullName like '%'+@SearchValue+'%') "

				+ "	OR (a.customerNo=isnull(@SearchValue,'') "
				+ "	OR (a.EmailID =isnull(@SearchValue,'') OR (a.MobilePhone =isnull(@SearchValue,'')" + "	)"
				+ " ) )))cust "
				+ " left JOIN customercommunicationaddress  CCA (NOLOCK) ON cust.CustomerID=CCA.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAccount "
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1)" + " GROUP BY CustomerID"
				+ " )AS linkedacc ON cust.CustomerID=linkedacc.CustomerID" + " ) TBL";
		return q;
	}

	public static String getUniSearchResultMode9(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"
				+ " SELECT distinct RN,Userid,Fullname as Name,UserName,EmailID,MobilePhone as [PrimaryPhone]"
				+ ", isnull(LinkedAccount,0) as [LinkedAccounts],[MailingAddress],[UserStatus],[LockStatus],[UserType]"
				+ " from"
				+ " (select row_Number() over(ORDER BY U.UserName DESC) AS RN,U.userid,U.Fullname,U.UserName,U.EmailID,U.MobilePhone ,linkedacc.LinkedAccount"
				+ "	,replace(replace(isnull(UCA.address1,'')+','+isnull(UCA.address2,'')+','+isnull(UCA.cityname,'')+','+isnull(UCA.StateCode,'')+','+isnull(UCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ "	,case" + "  when U.Status=0 then 'Pending Activation'"
				+ "  when U.[Status]=1 and De.Status is null then 'Active'"
				+ "  when U.[Status]=2 or  De.[Status]=1 then 'Inactive'	" + "  when U.[Status]=4 then 'Locked' "
				+ "  when U.Status=5 then 'Temp Locked' end as 'UserStatus'"
				+ ", case when U.IsLocked=0 then 'Unlocked'" + "	  when U.IsLocked=1 then 'Locked'	"
				+ "	  when U.IsLocked=2 then 'Locked by Admin'	 end as 'LockStatus'" + ", U.UserType as 'UsertypeID'"
				+ ", case when U.UserType =4 then 'Enterprise' else 'Mass Market' end as  'UserType'"
				+ " FROM [user] U (NOLOCK) " + " LEFT JOIN DeletionRequest De (NOLOCK)" + " On U.userid=De.Userid "
				+ " and De.[Status]=1 and De.[CancelledDate] is null"
				+ " JOIN ( select distinct(tmp.userid), sum(tmp.linkedaccount) AS LinkedAccount from ( (SELECT UA.userid,isnull(count(distinct UA.accountNumber),0) AS LinkedAccount "
				+ " FROM useraccount UA (NOLOCK)" + " GROUP BY UA.userid)" + " union all"
				+ " (SELECT PU.Userid,isnull(count(distinct PA.accountNumber),0) AS LinkedAccount "
				+ " FROM	 PortfolioAccount	PA	WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + " where PA.IsActive=1 "
				+ " GROUP BY PU.userid" + " )) as tmp" + " group by tmp.userid"
				+ " )AS linkedacc ON U.userid=linkedacc.userid"

				+ " JOIN Commonmaster co ON U.UserType = co.mastercode And co.mastertype='UserType'"
				+ " LEFT JOIN usercommunicationaddress UCA ON U.userid=UCA.userid AND UCA.MailAddressType=1"
				+ " where (ISNULL(@SearchValue,'')='' OR  U.UserName=isnull(@SearchValue,'')" + " )" + " ) TBL";
		return q;
	}

	public static String getCountUniSearchResultMode1(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"

				+ " SELECT COUNT(ServiceAccountnumber) AS Records" + " from"
				+ " (SELECT row_number() over(ORDER BY FULLNAME DESC) AS RN,cust.UtilityAccountNumber AS 'ServiceAccountnumber'"
				+ ", cust.customerNo AS 'CustomerNumber',cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail'"
				+ ", cust.MobilePhone AS 'PrimaryPhone' "
				+ ", case when cust.PortalAccessTypeID=1 then cust.PortalAccessType else cust.AddressType END AS 'AccountType'"
				+ ", ISNULL(linkeduser.LinkedUser,0) as 'LinkedUsers'"
				+ ", replace(isnull(cust.address1,'')+','+isnull(cust.address2,'')+','+isnull(cust.cityname,'')+','+isnull(cust.StateCode,'')+','+isnull(cust.ZipCode,''),',,',',') as 'Premise'"
				+ ", case "

				+ " when cust.AccountStatusID in(0,1,3) then 'Active' else 'Inactive' end 'AccountStatus'"
				+ ", cust.AccountNumber,cust.AddressType,isnull(linkedGuest.LinkedUserGuest,0) as LinkedUserGuest"
				+ ", isnull(linkedPM.LinkedUserPM,0) as LinkedUserPM" + ", cust.PortalAccessType"
				+ " FROM customerinfo cust (NOLOCK) "
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUser"
				+ "	FROM useraccount (NOLOCK)" + "	GROUP BY AccountNumber" + "	Union All"
				+ "	SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUser"
				+ "	FROM	 PortfolioAccount	PA	WITH(NOLOCK) "
				+ "	INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + "	where PA.IsActive=1"
				+ "	GROUP BY PA.accountNumber" + " )AS linkeduser ON cust.AccountNumber=linkeduser.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserGuest"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=1" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserGuest"
				+ " FROM PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID "
				+ " where PA.IsActive=1 And PU.Roleid=11" + " GROUP BY PA.accountNumber"
				+ " )AS linkedGuest ON cust.AccountNumber=linkedGuest.AccountNumber"
				+ " LEFT JOIN (SELECT AccountNumber,isnull(count(distinct userid),0) AS LinkedUserPM"
				+ " FROM useraccount (NOLOCK)" + " where Roleid=2" + " GROUP BY AccountNumber" + " Union All"
				+ " SELECT PA.accountNumber,isnull(count(distinct PU.Userid),0)  AS LinkedUserPM"
				+ " FROM  PortfolioAccount PA WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser PU WITH(NOLOCK) ON PA.GroupID=PU.GroupID"
				+ " where PA.IsActive=1 And PU.Roleid=6" + " GROUP BY PA.accountNumber"
				+ " )AS linkedPM ON cust.AccountNumber=linkedPM.AccountNumber" + " WHERE CustomerID NOT IN (1,-1) "

				+ " AND (ISNULL(@SearchValue,'')='' OR (cust.UtilityAccountNumber= isnull(@SearchValue,'')"
				+ " OR (cust.FullName = isnull(@SearchValue,'')"
				+ " OR (cust.EmailID = isnull(@SearchValue,'') OR (cust.MobilePhone = isnull(@SearchValue,'') "
				+ " OR (cust.cityname = isnull(@SearchValue,'') or cust.ZipCode=isnull(@SearchValue,'')))))))  "
				+ " )TBL";
		return q;
	}

	public static String getCountUniSearchResultMode5(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"

				+ " SELECT COUNT(CustomerID) AS Records" + " from"
				+ " (SELECT row_number() over(ORDER BY FULLNAME DESC) AS RN,cust.CustomerID,cust.customerNo AS 'CustomerNumber',"
				+ " cust.FullName AS 'CustomerName',cust.EmailID AS 'Customersemail',cust.MobilePhone AS 'PrimaryPhone' "
				+ ", isnull(linkedacc.LinkedAccount,0) as 'LinkedAccount'"
				+ ", replace(replace(isnull(CCA.address1,'')+','+isnull(CCA.address2,'')+','+isnull(CCA.cityname,'')+','+isnull(CCA.StateCode,'')+','+isnull(CCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ ", [CustomerStatus]" + " From"
				+ " (SELECT DISTINCT a.customerid,a.customerNo,a.FullName,a.EmailID,a.MobilePhone"
				+ ", case when b.customerid IS NOT NULL THEN 'Active' ELSE 'Inactive' END AS 'CustomerStatus'"
				+ "   FROM customerinfo (NOLOCK) A"
				+ "	left join customerinfo (NOLOCK) b on A.customerid=b.customerid and b.AccountStatusID IN (0,1,3)"
				+ "	WHERE a.CustomerID NOT IN (1,-1) And (ISNULL(@SearchValue,'')='' "
				+ "	OR (a.cityname like '%'+@SearchValue+'%') OR (a.ZipCode like '%'+@SearchValue+'%') "
				+ "	OR (a.FullName like '%'+@SearchValue+'%') "

				+ "	OR (a.customerNo=isnull(@SearchValue,'') "
				+ "	OR (a.EmailID =isnull(@SearchValue,'') OR (a.MobilePhone =isnull(@SearchValue,'')" + "	)"
				+ " ) )))cust "
				+ " left JOIN customercommunicationaddress  CCA (NOLOCK) ON cust.CustomerID=CCA.CustomerID"
				+ " LEFT JOIN (SELECT CustomerID,isnull(count(distinct accountNumber),0) AS LinkedAccount "
				+ " FROM customerinfo (NOLOCK)" + " WHERE CustomerID NOT IN (1,-1)" + " GROUP BY CustomerID"
				+ " )AS linkedacc ON cust.CustomerID=linkedacc.CustomerID" + " ) TBL";
		return q;
	}

	public static String getCountUniSearchResultMode9(String itemToSearch) {
		String q = "declare @utilityAccountNumber varchar(50)=Null" + ", @CustomerNo	VARCHAR(100)  = Null"
				+ ", @UserID		VARCHAR(20)  = Null" + ", @AccountType   VARCHAR(100) = Null"
				+ ", @Status		VARCHAR(100) = Null" + ", @IsPaperLess   BIT			 = Null"
				+ ", @ZipCode		VARCHAR(100) = Null" + ", @City			VARCHAR(100) = Null"
				+ ", @FName			VARCHAR(100) = Null" + ", @LName			VARCHAR(50)  = NULL"
				+ ", @PhoneNo		VARCHAR(20)  = NULL" + ", @EmailID		VARCHAR(100) = NULL"
				+ ", @Locked		VARCHAR(100) = Null" + ", @FromRegDate	date		 = Null"
				+ ", @ToRegDate		date		 = NULL" + ", @SocialLogin	VARCHAR(10)  = NULL"
				+ ", @NoOfLinkedAcc VARCHAR(20)  = NULL" + ", @Role			VARCHAR(30)  = NULL"
				+ ", @SearchValue   VARCHAR(255) = '" + itemToSearch + "'" + ", @Customerid	VARCHAR(20)  = NULL"
				+ ", @OrderColumn   VARCHAR(50)  = NULL" + ", @SortOrder		VARCHAR(50)  = NULL"
				+ ", @NoOfLinkedUser VARCHAR(20)  = NULL" + ", @UserType		VARCHAR(50)  = NULL"
				+ " SELECT COUNT(UserName) AS Records" + " from"
				+ " (select row_Number() over(ORDER BY U.UserName DESC) AS RN,U.userid,U.Fullname,U.UserName,U.EmailID,U.MobilePhone ,linkedacc.LinkedAccount"
				+ "	,replace(replace(isnull(UCA.address1,'')+','+isnull(UCA.address2,'')+','+isnull(UCA.cityname,'')+','+isnull(UCA.StateCode,'')+','+isnull(UCA.ZipCode,''),',,',','),',,','') AS 'MailingAddress'"
				+ "	,case" + "  when U.Status=0 then 'Pending Activation'"
				+ "  when U.[Status]=1 and De.Status is null then 'Active'"
				+ "  when U.[Status]=2 or  De.[Status]=1 then 'Inactive'	" + "  when U.[Status]=4 then 'Locked' "
				+ "  when U.Status=5 then 'Temp Locked' end as 'UserStatus'"
				+ ", case when U.IsLocked=0 then 'Unlocked'" + "	  when U.IsLocked=1 then 'Locked'	"
				+ "	  when U.IsLocked=2 then 'Locked by Admin'	 end as 'LockStatus'" + ", U.UserType as 'UsertypeID'"
				+ ", case when U.UserType =4 then 'Enterprise' else 'Mass Market' end as  'UserType'"
				+ " FROM [user] U (NOLOCK) " + " LEFT JOIN DeletionRequest De (NOLOCK)" + " On U.userid=De.Userid "
				+ " and De.[Status]=1 and De.[CancelledDate] is null"
				+ " JOIN ( select distinct(tmp.userid), sum(tmp.linkedaccount) AS LinkedAccount from ( (SELECT UA.userid,isnull(count(distinct UA.accountNumber),0) AS LinkedAccount "
				+ " FROM useraccount UA (NOLOCK)" + " GROUP BY UA.userid)" + " union all"
				+ " (SELECT PU.Userid,isnull(count(distinct PA.accountNumber),0) AS LinkedAccount "
				+ " FROM	 PortfolioAccount	PA	WITH(NOLOCK) "
				+ " INNER JOIN  PortfolioUser	PU	WITH(NOLOCK) ON PA.GroupID=PU.GroupID " + " where PA.IsActive=1 "
				+ " GROUP BY PU.userid" + " )) as tmp" + " group by tmp.userid"
				+ " )AS linkedacc ON U.userid=linkedacc.userid"

				+ " JOIN Commonmaster co ON U.UserType = co.mastercode And co.mastertype='UserType'"
				+ " LEFT JOIN usercommunicationaddress UCA ON U.userid=UCA.userid AND UCA.MailAddressType=1"
				+ " where (ISNULL(@SearchValue,'')='' OR  U.UserName=isnull(@SearchValue,'')" + " )" + " ) TBL";
		return q;
	}
	/*
	 * Query for Fetching the reset password msg
	 */

	public static String getResetPasswordLinkAdmin(String emailID) {
		String q = "SELECT top 1 *  FROM ContractAccountNotifyEmail where emailId='" + emailID
				+ "' AND subject='SCM Password Reset Link' ORDER BY 1 DESC";
		return q;
	}

	/*
	 * Query to fetch the Emailid form admin user
	 */

	public static String getUsernameFromAdminUser() {
		String q = "select top 10 username from Adminuser order by username ASC";
		return q;

	}

	public static String getFootPrintData(int zipcode, int locationtype) {
		String q = "select Name from GreenFootPrintLocations where LocationTypeId =" + locationtype + "and ZipCode = "
				+ zipcode + "order by Name ASC";
		return q;

	}

	public static String getFootPrintName(String username, String zipcode) {
		String q = "select Name from GreenFootPrintLocations where username = " + username + " and ZipCode = "
				+ zipcode;
		return q;
	}

	public static String getPaymentLocationsData() {
		String q = "select LocationName from PaymentLocation where IsDeleted=0 and cityId = '12979' order by LocationName ASC";
		return q;

	}

	public static String getPaymentDetails_Chase_TransID(String transID) {
		String q = "select * from PaymentRecons where orderid='" + transID + "'";
		return q;
	}

	public static String getCustomerRefNum(String cardName, String userID, String utilityAccNum) {
		String q = "select top 1 * from PaymentProfiles where userid='" + userID + "' and CustomerName='" + cardName
				+ "' and serviceAccountNumber='" + utilityAccNum + "' and IsDeleted=0 order by createdDate desc";
		return q;
	}

	public static String getUserNameFromUserID(String userID) {
		String q = "select * from [User] where UserID='" + userID + "'";
		return q;
	}

	public static String getAlreadyUserUserNameFromUserID(String userID) {
		String q = "Select Top 1 UserName FROM [User] where UserName != '" + userID + "' Order By CreatedDate Desc";
		return q;
	}

	public static String getDefaultPaymentType(String sAccountNumber) {
		String q = "Select DefaultPaymentType from useraccount where UtilityAccountNumber = '" + sAccountNumber + "'";
		return q;
	}

	public static String getAutoPayCount(String utilityNum) {
		String q = "SELECT COUNT(*)  AS autopayCount from AccountRecurringPayment where utilityAccountNumber='"
				+ utilityNum + "' order by 1 desc ";
		return q;
	}

	public static String getAutoPayInfo(String utilityNum) {
		String q = "SELECT * from AccountRecurringPayment where utilityAccountNumber='" + utilityNum
				+ "' order by 1 desc";
		return q;
	}

	public static String getTop10AdminRoleName() {
		String q = "select Top 10 RoleName from role order by rolename ASC";
		return q;
	}

	public static String getAllAdminRoleName() {
		String q = "select RoleName from role order by rolename ASC";
		return q;
	}

	public static String getorderIDFromPayments(int postingStatus, String statusMessage) {
		String q = "select top 1 * from PaymentRecons where CisPostingStatus ='" + postingStatus
				+ "' and StatusMessage = '" + statusMessage + "' order by 1 desc";
		return q;
	}

	public static String getorderIDFromPayments(String orderId) {
		String q = "select * from paymentRecons where orderid = '" + orderId + "'";
		return q;
	}

	public static String getEmailSubject(String emailid) {
		String q = "SELECT top 1 * FROM ContractAccountNotify where emailid ='" + emailid + "' order by 1 desc";
		return q;
	}

	public static String getEmailId_Notification_Pre(String UtilityAccountNumber) {
		String q = "SELECT * FROM AccountNotificationDetail where AccountNumber=(select AccountNumber from Account where utilityAccountNumber='"
				+ UtilityAccountNumber + "') and AccountNotificationTypeID=6";
		return q;
	}

	public static String getEmailTemplate(String templateName) {
		String q = "select * from EMailTemplate where TemplateName = '" + templateName + "'";
		return q;
	}

	public static String getPaymentOrderIDByCreateDate(String date, String paymentStatus) {
		String q = "select top 1 * from PaymentRecons where CreatedDate like '" + date + "%' and StatusMessage='"
				+ paymentStatus + "'" + "and IsRefunded=0 and IsReversed=0";
		return q;
	}

	public static String getPaymentTransactionID() {
		String q = "SELECT top 1 * FROM PaymentRecons where StatusMessage='Successful'and IsRefunded=0 and IsReversed=0 order by PaymentDate desc";
		return q;
	}

	public static String getEmailExcelReports(String emailId) {
		String q = "select top 1 * from contractaccountnotify where EmailId = '" + emailId + " order by 1 desc ";
		return q;
	}

	public static String getEmailIDFromAdminUser(String username) {
		String q = "select EmailID from Adminuser where username ='" + username + "'";
		return q;

	}

	// query for Notification Module
	public static String getNotificationByPlaceHolder(String placeHolderNumber) {
		String q = " exec GetInboxMessagesAdmin @accountnumber=N'2',@pagesize=N'50',@timeoffset=N'-480',@placeholderid=N'"
				+ placeHolderNumber + "', @pageindex=N'1',@userid=N'1',@ispageload=N'0'";
		return q;
	}

	// query for validate the notification in db
	public static String getSubjectNotification(String subject) {
		String q = "SELECT * FROM ContractAccountNotifyEmail where Subject = '" + subject + "' order by 1 desc";
		return q;
	}

	public static String getEmailIdByUtilityAccNumber(String utilityAccNum) {
		String q = "select EmailID from customerInfo where UtilityAccountNumber = '" + utilityAccNum + "'";
		return q;
	}

	/*
	 * This method is used to get the count of the Customer ref no present for
	 * an userID
	 */
	public static String getCustRefNoCount(String userID) {
		String q = "select  count(CustomerRefNum) as custRefNumber  from paymentprofiles where userId=" + userID
				+ " and isDeleted=0";
		return q;
	}

	public static String getCustRefnumber(String userID) {
		String q = "select CustomerRefNum from paymentprofiles where userId=" + userID + " and isDeleted=0";
		return q;
	}

	/*
	 * //query for validate the notification in db public static String
	 * getSubjectNotification(String subject) { String q =
	 * "SELECT * FROM ContractAccountNotifyEmail where Subject = '"
	 * +subject+"' order by 1 desc"; return q; }
	 */
	// query for Efficiency Module
	public static String getEfficiencyCount(String categoryId) {
		String q = "SELECT COUNT(categoryId) as Count from EEPromotion where categoryId='" + categoryId
				+ "' and IsDeleted=0";
		return q;
	}

	public static String getEfficiencyCount(String categoryId, String title) {
		String q = "SELECT COUNT(categoryId) as Count from EEPromotion where categoryId='" + categoryId
				+ "' and IsDeleted=1 and Title = '" + title + "'";
		return q;
	}

	public static String getEfficiencyDetails(String categoryID, String efficiencyName) {
		String q = "SELECT * from EEPromotion where categoryId='" + categoryID + "'and title = '" + efficiencyName
				+ "'";
		return q;
	}

	public static String getEfficiencyDetails(String categoryId) {
		String q = "SELECT * from EEPromotion where categoryId='" + categoryId + "' and IsDeleted=0";
		return q;
	}

	// query for Reset Password
	public static String getEmailContent(String emailID) {
		String q = "SELECT top 1 *  FROM ContractAccountNotifyEmail where emailId='" + emailID
				+ "' AND subject='SCM Temporary Password' ORDER BY 1 DESC";
		return q;
	}

	public static String setUserPassword(String password, String utilityAccountNumber) {
		String q = "Update [user] Set Password ='" + password
				+ "' where userID = (Select userID from [userAccount] where utilityAccountNumber ='"
				+ utilityAccountNumber + "')";
		return q;

	}

	public static String getPasswordUpdatedEmailContent(String emailID) {
		String q = "SELECT top 1 * FROM ContractAccountNotifyEmail where emailId='" + emailID + "' ORDER BY 1 DESC";
		return q;
	}

	// Query for email count by module wise
	public static String getEmailByMessageType(String toDate, String fromDate, String msgType) {
		String q = "select count(*) as totalEmail from ContractAccountNotifyEmail where (CreateDate	between '" + toDate
				+ "' AND '" + fromDate + "') and Module = '" + msgType + "' order by 1 desc ";
		return q;
	}

	// Query for email count by module wise
	public static String getEmailByStatus(String toDate, String fromDate, String status) {
		String q = "select count(*) as totalEmail from emailnotificationstatus where (CreateDate	between '" + toDate
				+ "' AND '" + fromDate + "') and StatusDescription = '" + status + "' order by 1 desc ";
		return q;
	}

	// Query for getting the status of notification
	public static String getNotificationReceivedStatus(String emailID) {
		String q = "SELECT top 1 * FROM ContractAccountNotifyEmail where emailId='" + emailID + "' ORDER BY 1 DESC";
		return q;
	}

	// Query for getting the status of notification alert
	public static String getNotificationAlertStatus(String accountNum, String notiTypeID) {
		String q = "select * from accountnotificationdetail where AccountNumber = '" + accountNum
				+ "' and accountnotificationtypeid in (" + notiTypeID + ")";
		return q;
	}

	public static String getCountProgramRebatesAnalyticsEfficiency(String startDate, String endDate, int mode) {
		String q = "DECLARE @DateFrom   DATETIME ='" + startDate + "', @DateTo   DATETIME = '" + endDate + "'"
				+ ",@Mode    TINYINT  = " + mode + "    /* Mode=3 For Rebates and Mode=4 For Programs*/"
				+ ",@EECategoryType INT   = NULL, @TimeOffSet int=330 "
				+ "SELECT   sum(ISNULL([Approved Enrollment], 0))         AS [ApprovedEnrollment]   "
				+ ",sum(ISNULL([Pending Enrollment], 0))         AS [PendingEnrollment]   "
				+ ",Sum(ISNULL([Approved Enrollment], 0) + ISNULL([Pending Enrollment], 0)) AS [TotalEnrollment]   "
				+ "FROM (SELECT  epu.CreatedDate            AS Created_Date, "
				+ "CASE epu.UserPromotionStatus WHEN 1 THEN 'Approved Enrollment' ELSE 'Pending Enrollment' END AS EnrollmentType   "
				+ ",COUNT(epu.accountNumber)                  AS PeopleEnrolled   FROM  eepromotion   ep  WITH(NOLOCK)   "
				+ "INNER JOIN eepromotionusers epu  WITH(NOLOCK) ON ep.PromotionId = epu.PromotionId AND ep.IsDeleted = 0   "
				+ "INNER JOIN eepromotioncategory epc  WITH(NOLOCK) ON ep.categoryid = epc.CategoryId   "
				+ "INNER JOIN Account    a  WITH(NOLOCK) ON a.AccountNumber = epu.AccountNumber   "
				+ "INNER JOIN CustomerAddress  ca  WITH(NOLOCK) ON ca.AddressID = a.AddressID   "
				+ "INNER JOIN dbo.Customer  Cust WITH(NOLOCK) ON CA.CustomerID = Cust.CustomerID   "
				+ "WHERE epu.CreatedDate >= @DateFrom AND epu.CreatedDate < @DateTo  AND epc.CategoryId = @Mode  "
				+ "GROUP BY epu.CreatedDate  ,CASE epu.UserPromotionStatus WHEN 1 THEN 'Approved Enrollment' ELSE 'Pending Enrollment' END   "
				+ ") AS src_table   PIVOT(SUM(PeopleEnrolled) FOR EnrollmentType IN ([Approved Enrollment],[Pending Enrollment])) AS pv_table   "
				+ "where CAST(Created_Date As DATE) BETWEEN @DateFrom AND @DateTo";
		return q;
	}

	public static String getRecordDetailsAnalyticsProgramRebates(String createdDate, int Mode) {
		String q = "Declare @EECategoryType INT   = " + Mode
				+ ", @PromotionId  BIGINT = NULL ,@UtilityAccount VARCHAR(100) = NULL "
				+ ",@FullName   VARCHAR(100) = NULL "
				+ "SELECT CONVERT(VARCHAR(19),epu.CreatedDate,120) AS CreatedDate, A.UtilityAccountNumber,Cust.FullName,"
				+ "ep.Title, ep.PromotionId, Cust.EmailID, ep.SavingValue, ep.Description FROM  eepromotion   ep  WITH(NOLOCK) "
				+ "INNER JOIN eepromotionusers epu  WITH(NOLOCK) ON ep.PromotionId = epu.PromotionId AND ep.IsDeleted = 0 "
				+ "INNER JOIN eepromotioncategory epc  WITH(NOLOCK) ON ep.categoryid = epc.CategoryId "
				+ "INNER JOIN Account    a  WITH(NOLOCK) ON a.AccountNumber = epu.AccountNumber "
				+ "INNER JOIN CustomerAddress  ca  WITH(NOLOCK) ON ca.AddressID = a.AddressID "
				+ "INNER JOIN dbo.Customer  Cust WITH(NOLOCK) ON CA.CustomerID = Cust.CustomerID "
				+ "WHERE convert(nvarchar(50), epu.CreatedDate,121) LIKE '" + createdDate + "%'"
				+ "AND epc.CategoryId = ISNULL(CONVERT(VARCHAR(2), @EECategoryType), epc.CategoryId) "
				+ "/* AND EP.Title LIKE '%'+ISNULL(@Topic,EP.Title)+'%'*/ "
				+ "AND Cust.FullName LIKE '%'+ISNULL(@FullName,Cust.FullName)+'%' "
				+ "AND A.UtilityAccountNumber LIKE '%'+ISNULL(@UtilityAccount,A.UtilityAccountNumber)+'%' ORDER by createdDate Desc";
		return q;
	}

	public static String getTotalRecordsAnalyticsProgramRebates(int noOfRecords, String startDate, String endDate,
																int Mode) {

		String q = "Declare @DateFrom Datetime= '" + startDate + "',@DateTo Datetime ='" + endDate + "'"
				+ ",@EECategoryType INT   = " + Mode
				+ ", @PromotionId  BIGINT = NULL, @UtilityAccount VARCHAR(100) = NULL,"
				+ "@FullName   VARCHAR(100) = NULL " + "SELECT Top " + noOfRecords
				+ " CONVERT(VARCHAR(19),epu.CreatedDate,120)               AS CreatedDate,"
				+ "A.UtilityAccountNumber, Cust.FullName, ep.Title, ep.PromotionId, Cust.EmailID, "
				+ "ep.SavingValue,ep.Description " + "FROM  eepromotion   ep  WITH(NOLOCK) "
				+ "INNER JOIN eepromotionusers epu  WITH(NOLOCK) ON ep.PromotionId = epu.PromotionId AND ep.IsDeleted = 0 "
				+ "INNER JOIN eepromotioncategory epc  WITH(NOLOCK) ON ep.categoryid = epc.CategoryId "
				+ "INNER JOIN Account    a  WITH(NOLOCK) ON a.AccountNumber = epu.AccountNumber "
				+ "INNER JOIN CustomerAddress  ca  WITH(NOLOCK) ON ca.AddressID = a.AddressID "
				+ "INNER JOIN dbo.Customer  Cust WITH(NOLOCK) ON CA.CustomerID = Cust.CustomerID "
				+ "WHERE CAST(epu.CreatedDate As DATE) BETWEEN @DateFrom AND @DateTo "
				+ "AND epc.CategoryId = ISNULL(CONVERT(VARCHAR(2), @EECategoryType), epc.CategoryId) "
				+ "/* AND EP.Title LIKE '%'+ISNULL(@Topic,EP.Title)+'%'*/ "
				+ "AND Cust.FullName LIKE '%'+ISNULL(@FullName,Cust.FullName)+'%' "
				+ "AND A.UtilityAccountNumber LIKE '%'+ISNULL(@UtilityAccount,A.UtilityAccountNumber)+'%' ORDER by createdDate Desc";
		return q;
	}

	// Query for getting the Quiet hours status of notification alert
	public static String getQuietHoursStatus(String accountNumber, String userID) {
		String q = "select * from AccountNotification where AccountNumber = '" + accountNumber + "' and UserID = '"
				+ userID + "'";
		return q;
	}

	// Query for getting the Quiet hours status of notification alert
	public static String getACcountNumberByUtilityAccountNumber(String utilityAccNum, String userID) {
		String q = "select * from AccountNotification where AccountNumber = '" + utilityAccNum + "'";

		return q;
	}

	/**
	 * This query is used for fetching default account number
	 *
	 * @param sUserName
	 * @return
	 */
	public static String getDefaultAccountNumber(String sUserName) {
		String sDefaultAccountQuery = "SELECT DISTINCT AccountNumber " + "FROM UserAccount "
				+ "WHERE UserID = (SELECT UserID FROM [User] WHERE UserName = '" + sUserName + "') "
				+ "AND IsDefaultAccount = '1'";
		return sDefaultAccountQuery;
	}

	/*
	 * This query will used for getting the total service accounts
	 */
	public static String getTotalServiceAccounts() {
		String totalServiceAccuntsCount = "SELECT COUNT(DISTINCT AccountNumber) as TotalServiceAccountsCount "
				+ "FROM CustomerInfo(NOLOCK) WHERE CustomerID NOT IN (1,-1)";
		return totalServiceAccuntsCount;
	}

	/*
	 * This query will used for the getting the active accounts
	 */
	public static String getActiveServiceAccounts() {
		String activeAccountCount = "SELECT COUNT(DISTINCT AccountNumber) as ActiveAccounts FROM CustomerInfo(NOLOCK)"
				+ "WHERE AccountStatusID IN (0,1,3) AND CustomerID NOT IN (1,-1)";
		return activeAccountCount;
	}

	/*
	 * This query will get the registered user count
	 */
	public static String getRegisterUsersCount() {
		String registeredUserCount = "SELECT count(DISTINCT U.userid)  as TotalUser " + "FROM [user] U (NOLOCK)"
				+ " JOIN (select accountNumber,Userid from UserAccount (NOLOCK)" + " Union all"
				+ " SELECT PA.accountNumber,PU.Userid" + " FROM     PortfolioAccount    PA    WITH(NOLOCK)"
				+ " INNER JOIN  PortfolioUser    PU    WITH(NOLOCK) ON PA.GroupID=PU.GroupID" + " where PA.IsActive=1 "
				+ ") UA  ON U.userid=UA.userid"
				+ " WHERE U.userid NOT IN (-1,1) AND U.[STATUS] not in (3) AND U.UserType Not in (3) --AND UA.IsDefaultAccount=1";
		return registeredUserCount;
	}

	public static String getRegisterdActiveUserCount() {
		String registerdActiveUserCount = "SELECT count(DISTINCT U.userid)  As ActiveUser " + "FROM [user] U (NOLOCK)"
				+ " JOIN (select accountNumber,Userid from UserAccount (NOLOCK)" + " Union all"
				+ " SELECT PA.accountNumber,PU.Userid" + " FROM     PortfolioAccount    PA    WITH(NOLOCK)"
				+ " INNER JOIN  PortfolioUser    PU    WITH(NOLOCK) ON PA.GroupID=PU.GroupID AND PU.IsActive=1"
				+ " where PA.IsActive=1 )" + " UA ON U.userid=UA.userid"
				+ " WHERE U.STATUS =1 AND isnull(U.islocked,0)=0 AND U.userid NOT IN (-1,1) AND U.UserType Not in (3,5)";
		return registerdActiveUserCount;
	}

	/*
	 * This query will reset the primay email id
	 */
	public static String resetPrimaryEmailID(String username, String emailID) {
		String resetEmail = "UPDATE [User] SET EmailID = '" + username + "' WHERE UserName = '" + emailID + "'";
		return resetEmail;
	}

	/*
	 * This query will return the primary Email id
	 */
	public static String getPrimaryEmailID(String userID) {
		String resetEmail = "select EmailID from [User] where UserID = '" + userID + "'";
		return resetEmail;
	}

	/*
	 * This query returns count of linked account number
	 */
	public static String getCountLinkedAccount(String userID) {
		String NoOfLinkedAccount = "Select count (UserID) as LinkedAccountCount from UserAccount  where UserID = '"
				+ userID + "'";
		return NoOfLinkedAccount;
	}

	/*
	 * This query returns user status
	 */
	public static String getStatusUser(String userName) {
		String statusUser = "SELECT Status FROM [User] WHERE UserName = '" + userName + "'";
		return statusUser;
	}

	/**
	 * This query will return the total Billing Data
	 *
	 * @return
	 */
	public static String getBillingDataCount(String utilityAccNum) {
		String billingCount = "Select count(BillingId) as BillingRecordCount from Billing  where cast (BillingDate as date) > '2018-05-31' and AccountNumber=(select AccountNumber from account where utilityAccountNumber='"
				+ utilityAccNum + "')";
		return billingCount;
	}

	/*
	 * This query returns transaction data count
	 */
	public static String getTransactionDataCount(String utilityAccNum) {
		String transactionCount = "SELECT Distinct count(BillingTransactionId) as TotalTransaction FROM BillingTransaction where cast (TransactionDate as date) > '2018-03-29' and accountnumber=(select AccountNumber from account where utilityAccountNumber='"
				+ utilityAccNum + "') group by AccountNumber";
		return transactionCount;
	}
	public static String getFilerDataForBanners(String toDate, String fromDate, String moduleName){
		String q="	Declare @DateFrom DATETIME = '"+fromDate+"'"
				+" ,@DateTo DATETIME = '"+toDate+"'"
				+" ,@BannerTitle VARCHAR(100)= NULL"
				+" ,@OrderColumn VARCHAR(50) = N'ClickDate'"
				+" ,@SortOrder VARCHAR(50) = N'desc'"
				+" ,@BannerName VARCHAR(100) = '"+toDate+"'"
				+" ,@PageLength INT = 50"
				+" ,@PageStartFrom INT = 0"
				+" ,@TimeOffSet int=330"

				+"  Declare @PageTo INT "
				+"  set @PageTO= @PageStartFrom+@PageLength"

				+"  IF ISNULL(@OrderColumn, '') = ''"
				+"  SET @OrderColumn = 'ClickDate'"
				+"  ELSE"
				+"  SET @OrderColumn = @OrderColumn"
				+"  IF ISNULL(@OrderColumn, '') = 'BannerTitle'"
				+"   SET @OrderColumn = 'Title'"
				+"  ELSE"
				+"   SET @OrderColumn = @OrderColumn"
				+"  IF ISNULL(@SortOrder, '') = ''"
				+"   SET @SortOrder = 'asc'"
				+"  ELSE"
				+"   SET @SortOrder = @SortOrder"
				+"  IF ISNULL(@DateFrom, '') = ''"
				+"   SET @DateFrom = CAST(DATEADD(MM, - 1, GETDATE()) AS DATE)"
				+"  IF ISNULL(@DateTo, '') = ''"
				+"   SET @DateTo = CAST(GETDATE() AS DATE)"
				+"   ELSE"
				+"   SET @DateTo = DATEADD(DD, 0, @DateTo)"


				+" SELECT  CONVERT(VARCHAR(19),BTC.TraceTime,120) ClickDate,BS.BannerName, Alternatetext AS Title"
				+"  ,C.FullName,C.EmailId,CA.UtilityAccountNumber, BS.BannerContent"

				+"     FROM BannerMaster          BS WITH(NOLOCK)"
				+"    JOIN BannerTraceClicks     BTC WITH(NOLOCK) ON BTC.BannerID = BS.BannerID"
				+" JOIN CustomerAddress  CA WITH(NOLOCK) ON BTC.UtilityAccountNumber = CA.UtilityAccountNumber"
				+" JOIN Customer    C WITH(NOLOCK) ON CA.CustomerId = C.CustomerId where"
				+"        	CAST(DATEADD(MI,@TimeOffSet,BTC.TraceTime) As DATE) BETWEEN @DateFrom AND @DateTo "
				+"  AND BS.BannerName LIKE '%'+ISNULL(@BannerName,BS.BannerName)+'%'"
				+"  AND Alternatetext LIKE '%'+ISNULL(@BannerTitle,Alternatetext)+'%'";

		return q;


	}

	/*
	 * This query returns CSR Profile tab data
	 */
	public static String getCSRPofileDataFromUser(String utilityAccNum) {
		String userData = "select EmailID, AlternateEmailID, UserName,MobilePhone,FullName, FirstName from [User] where UserID = (select top 1 UserID from useraccount where UtilityAccountNumber = '" + utilityAccNum + "')";
		return userData;
	}

	/*
	 * This query returns CSR Profile tab data
	 */
	public static String getCSRPofileDataFromCustomerInfo(String utilityAccNum) {
		String customerData = "select AccountStatus,FullName,CreatedDate, ZipCode,CityName, AddressType from CustomerInfo where UtilityAccountNumber = '" + utilityAccNum + "'";
		return customerData;
	}

	/*
	 * This query returns CSR Profile tab meter data
	 */
	public static String getCSRPofileMeterMapping(String utilityAccNum) {
		String meterData = "SELECT * FROM AccountMeterMapping where AccountNumber= (select Top 1 AccountNumber from  useraccount where UtilityAccountNumber = '" + utilityAccNum + "')";
		return meterData;
	}

	/*
	 * This query returns CSR Profile tab marketing pref data
	 */
	public static String getCSRPofileUserMarketingPreferences(String utilityAccNum) {
		String marketigPrefData = "SELECT * FROM UserMarketingPreferenceSetting where AccountNumber= (select Top 1 AccountNumber from  useraccount where UtilityAccountNumber = '" + utilityAccNum + "')";
		return marketigPrefData;
	}

	/*
	 * This query returns CSR paperless status
	 */
	public static String getpaperLessStatus(String utilityAccNum, String userID) {
		String paperLessStatusDB = "select Paperless from useraccount where UtilityAccountNumber = '" + utilityAccNum + "' and  UserID = '" + userID + "'";
		return paperLessStatusDB;
	}


	/**
	 * List of Tables which are cleaned after Delete My Profile Action
	 */
	public static String query_001 = "SELECT UA.UserAccountID,UA.UserID,UA.AccountNumber,UA.RoleID,UA.CreatedDate,UA.LastUpdated,UA.IsDefaultAccount,UA.UtilityAccountNumber,UA.BPNumber,UA.CustomerID,\r\n" +
			"UA.Paperless,UA.EmailNotify,UA.BudgetNotify,UA.DefaultPaymentType,UA.IsPledge,UA.RequestID,UA.PaperlessUpdateDate,UA.IsCustomerOwner FROM useraccount UA INNER JOIN UserAccount(NOLOCK) UAA ON UA.AccountNumber = UAA.AccountNumber WHERE UAA.RoleId IN (1,2) AND UAA.IsCustomerOwner = 0 AND UAA.AccountNumber IN (SELECT AccountNumber\r\n" +
			"FROM UserAccount(NOLOCK) UA WHERE userid IN (SELECT DISTINCT userid FROM [user] WHERE username = '%s')";
	public static String query_002 = "select * from BellNotification where userid in (select distinct userid from [user] where username='%s')";
	public static String query_003 = "select * from PushNotificationMessage where userid in (select distinct userid from [user] where username='%s')";
	public static String query_004 = "select * from BannerTraceClicks where userid in (select distinct userid from [user] where username='%s')";
	public static String query_005 = "select * from EEPromotionUsers where userid in (select distinct userid from [user] where username='%s')";
	public static String query_006 = "select * from EEPromotionUsers where PromotionUserId in (select PromotionUserId from EEPromotionUsers where userid in (select distinct userid from [user] where username='%s'))";
	public static String query_007 = "select * from UserElectricVehicle where userid in (select distinct userid from [user] where username='%s')";
	public static String query_008 = "select * from UserServiceID where userid in (select distinct userid from [user] where username='%s')";
	public static String query_009 = "select * from DefaultPayment where userid in (select distinct userid from [user] where username='%s')";
	public static String query_010 = "select * from UserProfile where userid in (select distinct userid from [user] where username='%s')";
	public static String query_011 = "select TRS.TRValueGuId, TRS.SaveID, TRS.CollectionId, TRS.ResponseValue, TRS.LastUpdated, TRS.TemplateTypeId, TRS.CreatedDate "
			+ "FROM TemplateResponseValueSave TRS INNER JOIN TemplateResponseSave TR ON TRS.SaveID = TR.SaveID WHERE TR.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_012 = "select * from TemplateResponseSave where userid in (select distinct userid from [user] where username='%s')";
	public static String query_013 = "select UM.UserMessageId, UM.AccountNumber, UM.CreatedDate, UM.CreatedBy, UM.UpdatedDate, UM.UpdatedBy, UM.IsRead, UM.IsSaved, "
			+ "UM.IsTrashed, UM.IsReply, UM.MessageDetailId, UM.type, UM.EmailId, UM.UserID FROM UserMessages UM JOIN MessageDetail MD ON "
			+ "UM.MessageDetailID = MD.MessageDetailID JOIN MessageMaster MM ON MD.MessageID = MM.MessageID WHERE MM.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_014 = "select MA.AttachmentId, MA.MessageDetailID, MA.AttachmentPath, MA.AttachmentName, MA.AttachmentType, MA.CreateDate, "
			+ "MA.UpdatedDate, MA.Latitude, MA.Longitude, MA.ResponseGuId FROM MessageAttachments MA JOIN UserMessages UM ON MA.MessageDetailID = UM.MessageDetailID "
			+ "JOIN TemplateResponse TR ON UM.UserID = TR.UserID WHERE TR.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_015 = "select MA.AttachmentId, MA.MessageDetailID, MA.AttachmentPath, MA.AttachmentName, MA.AttachmentType, MA.CreateDate, "
			+ "MA.UpdatedDate, MA.Latitude, MA.Longitude, MA.ResponseGuId FROM MessageAttachments MA JOIN TemplateResponse TR ON MA.ResponseGuId = TR.ResponseGuId "
			+ "WHERE TR.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_016 = "select * FROM TemplateResponse WHERE MessageDetailId IN (SELECT MessageDetailId FROM MessageDetail WHERE userid IN "
			+ "(select distinct userid from [user] where username='%s'))";
	public static String query_017 = "select * from TemplateResponse where userid in (select distinct userid from [user] where username='%s')";
	public static String query_018 = "select MD.MessageDetailId, MD.MessageId, MD.MessageBody, MD.CreatedDate, MD.CreatedBy, MD.UpdatedDate, MD.UpdatedBy, "
			+ "MD.IsResolved, MD.UserID, MD.IsAutoReply FROM MessageDetail MD JOIN MessageMaster MM ON MD.MessageID = MM.MessageID WHERE MM.UserID IN "
			+ "(select distinct userid from [user] where username='%s')";
	public static String query_019 = "select UM.UserMessageId, UM.AccountNumber, UM.CreatedDate, UM.CreatedBy, UM.UpdatedDate, UM.UpdatedBy, UM.IsRead, "
			+ "UM.IsSaved, UM.IsTrashed, UM.IsReply, UM.MessageDetailId, UM.type, UM.EmailId, UM.UserID FROM UserMessages UM JOIN MessageDetail MD ON "
			+ "UM.MessageDetailID = MD.MessageDetailID WHERE MD.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_020 = "select * from MessageDetail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_021 = "select * from MessageMaster where userid in (select distinct userid from [user] where username='%s')";
	public static String query_022 = "select * from UserTextMsg where userid in (select distinct userid from [user] where username='%s')";
	public static String query_023 = "select * from UserDashBoardModuleMapping where userid in (select distinct userid from [user] where username='%s')";
	public static String query_024 = "select * from AccountNotificationDetail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_025 = "select * from ContractAccountNotify where userid in (select distinct userid from [user] where username='%s')";
	public static String query_026 = "select * from ContractAccountNotifyEmail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_027 = "select * from EEPromotionUsersAudit where userid in (select distinct userid from [user] where username='%s')";
	public static String query_028 = "select * from ContractAccountNotifyIVR where userid in (select distinct userid from [user] where username='%s')";
	public static String query_029 = "select * from UserCommunicationAddress where userid in (select distinct userid from [user] where username='%s')";
	public static String query_030 = "select * from ContractAccountNotifyText where userid in (select distinct userid from [user] where username='%s')";
	public static String query_031 = "select * from ContractAccountNotifyEmailArchive where userid in (select distinct userid from [user] where username='%s')";
	public static String query_032 = "select * from ContractAccountNotifyIVRArchive where userid in (select distinct userid from [user] where username='%s')";
	public static String query_033 = "select * from ContractAccountNotifyTextArchive where userid in (select distinct userid from [user] where username='%s')";
	public static String query_034 = "select * from EmailMessageArchive where userid in (select distinct userid from [user] where username='%s')";
	public static String query_035 = "select * from UserCommunicationAddressHist where userid in (select distinct userid from [user] where username='%s')";
	public static String query_036 = "select * from EEPromotionLike where userid in (select distinct userid from [user] where username='%s')";
	public static String query_037 = "select * from SCPModuleEventLog where userid in (select distinct userid from [user] where username='%s')";
	public static String query_038 = "select * from AccountNotification where userid in (select distinct userid from [user] where username='%s')";
	public static String query_039 = "select * from TrackingEvents where userid in (select distinct userid from [user] where username='%s')";
	public static String query_040 = "select * from EEPromotionAddDate where userid in (select distinct userid from [user] where username='%s')";
	public static String query_041 = "select * from ApplicationForm where userid in (select distinct userid from [user] where username='%s')";
	public static String query_042 = "select * from UserDetail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_043 = "select * from AccountLevelPay where userid in (select distinct userid from [user] where username='%s')";
	public static String query_044 = "select * from ContractAccountNotifyWhatsApp where userid in (select distinct userid from [user] where username='%s')";
	public static String query_045 = "select * from AccountTextMessage where userid in (select distinct userid from [user] where username='%s')";
	public static String query_046 = "select * from UserMarketingPreferenceSetting where userid in (select distinct userid from [user] where username='%s')";
	public static String query_047 = "select * FROM Audit_GuestAccessRequest  WHERE UserID_To IN (select distinct userid from [user] where username='%s')";
	public static String query_048 = "select * FROM AddAccount_Request WHERE UserID_From IN (select distinct userid from [user] where username='%s')";
	public static String query_049 = "select * from UtilityUser where userid in (select distinct userid from [user] where username='%s')";
	public static String query_050 = "select * from UserActivityTrail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_051 = "select * from UserActivityTrail where userid in (select distinct userid from [user] where username='%s')";
	public static String query_052 = "select * from UserDeviceHistory where userid in (select distinct userid from [user] where username='%s')";
	public static String query_053 = "select * from AgencyProgram where userid in (select distinct userid from [user] where username='%s')";
	public static String query_054 = "select * from UserLanguage where userid in (select distinct userid from [user] where username='%s')";
	public static String query_055 = "select * from UserMessages where userid in (select distinct userid from [user] where username='%s')";
	public static String query_056 = "select * from UserRatingHistory where userid in (select distinct userid from [user] where username='%s')";
	public static String query_057 = "select * from MyAccountUpdate where userid in (select distinct userid from [user] where username='%s')";
	public static String query_058 = "select * from CampaignUsers where userid in (select distinct userid from [user] where username='%s')";
	public static String query_059 = "select * from BankAccount where userid in (select distinct userid from [user] where username='%s')";
	public static String query_060 = "select * from BillingTransaction where userid in (select distinct userid from [user] where username='%s')";
	public static String query_061 = "select * from HoneyRefreshToken where userid in (select distinct userid from [user] where username='%s')";
	public static String query_062 = "select GA.RequestID, GA.UserID_From, GA.UserID_To, GA.UtilityAccountNumber, GA.BPNumber, GA.RoleID, GA.CreatedDate, "
			+ "GA.RequestStatus, GA.LastUpdated, GA.GuestName, GA.GuestEmailID, GA.AccessExpiryDate, GA.ActivationKey, GA.AccessLevel, GA.RequestBatch, GA.JobTitle, "
			+ "GA.PhoneNumber FROM GuestAccessRequest GA JOIN PortfolioUser PU  ON GA.requestid=PU.Requestid JOIN PortFolioMaster PM ON PU.GroupID=PM.GroupID "
			+ "and PU.RoleId<>10 and PU.isactive=1 and PU.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_063 = "select PA.GroupAccountID, PA.GroupID, PA.AccountNumber, PA.IsActive, PA.CreatedDate, PA.LastUpdated, PA.IsDefaultAccount "
			+ "FROM PortfolioAccount PA JOIN PortFolioMaster PM ON PA.GroupID=PM.GroupID WHERE PA.Isactive=1 and PM.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_064 = "select PU.UserGroupID, PU.UserID, PU.GroupID, PU.IsActive, PU.CreatedDate, PU.LastUpdated, PU.IsDefault, PU.RoleID, PU.RequestID FROM PortfolioUser PU "
			+ "JOIN PortFolioMaster PM ON PU.GroupID=PM.GroupID and PU.isactive=1 and PU.RoleId=10 and PU.UserID IN (select distinct userid from [user] where username='%s')";
	public static String query_065 = "select * from PortfolioMaster where Isactive=1 and userid in (select distinct userid from [user] where username='%s')";
	public static String query_066 = "select * from useraccount where userid in (select distinct userid from [user] where username='%s')";
	public static String query_067 = "select * FROM [user] WHERE userid IN (select distinct userid from [user] where username='%s')";

	public static ArrayList allCleanedTables(String username){
		ArrayList<String> list=new ArrayList<String>();
		list.add(String.format(query_001, username));
		list.add(String.format(query_002, username));
		list.add(String.format(query_003, username));
		list.add(String.format(query_004, username));
		list.add(String.format(query_005, username));
		list.add(String.format(query_006, username));
		list.add(String.format(query_007, username));
		list.add(String.format(query_008, username));
		list.add(String.format(query_009, username));
		list.add(String.format(query_010, username));

		list.add(String.format(query_011, username));
		list.add(String.format(query_012, username));
		list.add(String.format(query_013, username));
		list.add(String.format(query_014, username));
		list.add(String.format(query_015, username));
		list.add(String.format(query_016, username));
		list.add(String.format(query_017, username));
		list.add(String.format(query_018, username));
		list.add(String.format(query_019, username));
		list.add(String.format(query_020, username));

		list.add(String.format(query_021, username));
		list.add(String.format(query_022, username));
		list.add(String.format(query_023, username));
		list.add(String.format(query_024, username));
		list.add(String.format(query_025, username));
		list.add(String.format(query_026, username));
		list.add(String.format(query_027, username));
		list.add(String.format(query_028, username));
		list.add(String.format(query_029, username));
		list.add(String.format(query_030, username));

		list.add(String.format(query_031, username));
		list.add(String.format(query_032, username));
		list.add(String.format(query_033, username));
		list.add(String.format(query_034, username));
		list.add(String.format(query_035, username));
		list.add(String.format(query_036, username));
		list.add(String.format(query_037, username));
		list.add(String.format(query_038, username));
		list.add(String.format(query_039, username));
		list.add(String.format(query_040, username));

		list.add(String.format(query_041, username));
		list.add(String.format(query_042, username));
		list.add(String.format(query_043, username));
		list.add(String.format(query_044, username));
		list.add(String.format(query_045, username));
		list.add(String.format(query_046, username));
		list.add(String.format(query_047, username));
		list.add(String.format(query_048, username));
		list.add(String.format(query_049, username));
		list.add(String.format(query_050, username));

		list.add(String.format(query_051, username));
		list.add(String.format(query_052, username));
		list.add(String.format(query_053, username));
		list.add(String.format(query_054, username));
		list.add(String.format(query_055, username));
		list.add(String.format(query_056, username));
		list.add(String.format(query_057, username));
		list.add(String.format(query_058, username));
		list.add(String.format(query_059, username));
		list.add(String.format(query_060, username));

		list.add(String.format(query_061, username));
		list.add(String.format(query_062, username));
		list.add(String.format(query_063, username));
		list.add(String.format(query_064, username));
		list.add(String.format(query_065, username));
		list.add(String.format(query_066, username));
		list.add(String.format(query_067, username));
		return list;
	}
}
