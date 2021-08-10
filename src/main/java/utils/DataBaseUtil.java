package utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;
import resources.Filepaths;

public class DataBaseUtil {
	public static Connection connection;
	public static Connection paymentConn;
	private static String envPropFile = Filepaths.envProp;
	public static PropertiesUtil propertiesUtil = new PropertiesUtil(envPropFile);
	private static String connectionUrl = propertiesUtil.getPropValue("databaseUrl");
	private static String dbUsername = propertiesUtil.getPropValue("dbUserName");
	private static String dbPassword = propertiesUtil.getPropValue("dbPassword");
	private static String dbURLCredentials = propertiesUtil.getPropValue("dbUrlCredentials");
	private static String dbURLCredentialsPaymentDatabase = propertiesUtil
			.getPropValue("dbUrlCredentialsPaymentDatabase");
	static String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public static void main(String[] args) {
		try {
			DataBaseUtil.callProcedureToDeleteUser(737);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection initSqlConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(dbURLCredentials);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection initSqlPaymentConn() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			paymentConn = DriverManager.getConnection(dbURLCredentialsPaymentDatabase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentConn;
	}

	/**
	 * This method is used to get the result set by executing the given
	 * query.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResultSet(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = connection.createStatement();
			Thread.sleep(500);
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * This Method is used to get the result from multiple result set
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 * @author Souradeep.Ghosh
	 */
	public static ResultSet getResultSetMulti(String query) {
		ResultSet resultSet2 = null;
		try {
			Statement statement = connection.createStatement();
			Thread.sleep(500);
			ResultSet resultSet1 = statement.executeQuery(query);
			resultSet1.close();
			boolean rs2 = statement.getMoreResults();
			if (rs2) {
				resultSet2 = statement.getResultSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet2;
	}

	/**
	 * This method is used to get the result set from the Payment Database by
	 * executing the given query.
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResultSetPaymentDb(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = paymentConn.createStatement();
			Thread.sleep(500);
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * This method is used to get the result set from the Payment Database by
	 * executing the given query from Chase Payment API DB
	 *
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResultSetPaymentDbChase(String query) {
		ResultSet resultSet = null;
		try {
			Statement statement = paymentConn.createStatement();
			Thread.sleep(500);
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public static String executeSQLQuery(String sqlQuery) {
		Connection connection = null;
		String resultValue = "";
		ResultSet rs;
		try {
			Class.forName(dbClass).newInstance();
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				resultValue = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return resultValue;
	}

	public static void updateSqlComm(String sqlQuery) {
		Connection connection = null;
		String resultValue = "";
		ResultSet rs;
		try {
			Class.forName(dbClass).newInstance();
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlQuery);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void executeUpdateDeleteSQLQuery(String sqlQuery) {
		Connection connection = null;
		ResultSet rs;
		try {
			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException sqlEx) {
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	public static int callStoredProcToActivateUser(int iUserID) {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call AutoActivation(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.execute();
			rs = stmt.getResultSet();
			try {
				rs.next();
				FinalResult = rs.getInt(1);
				//                System.out.println(FinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {
				err.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return FinalResult;
	}

	public static int callStoredProcToActivateUser(String userID) {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call AutoActivation(?)}";
			stmt = connection.prepareCall(insertUserID);
			int iUserID = Integer.parseInt(userID);
			stmt.setInt(1, iUserID);
			stmt.execute();
			rs = stmt.getResultSet();
			try {
				rs.next();
				FinalResult = rs.getInt(1);
				//                System.out.println(FinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {
				err.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return FinalResult;
	}

	/**
	 * Delete user by given user id by calling this method.
	 * @param userId
	 * @return
	 */
	public static int callProcedureToDeleteUser(int userId) {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call SetUnRegisterUser(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, userId);
			stmt.execute();
			rs = stmt.getResultSet();
			rs.next();
			String message = rs.getString(1);
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return FinalResult;
	}

	public static void callStoredProcUnblockAccIp() {
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			//
			String SQL = "{call AutoUnLockAll}";
			// String SQL = "call AutoUnLockAll";
			stmt = connection.prepareCall(SQL);
			stmt.execute();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	/**
	 * This method is used to activate a new account.
	 * @param utilityAccountNumber
	 * @return
	 * @throws Exception
	 */
	public static int activateNewUserStoredProc(String utilityAccountNumber) {
		String sUserIDQuery = SqlQuery.sUserIDQuery + utilityAccountNumber + "'";
		String sUserID = DataBaseUtil.executeSQLQuery(sUserIDQuery);
		int iUserID = Integer.parseInt(sUserID);
		Connection connection = null;
		ResultSet rs = null;
		int FinalResult = 0;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call AutoActivation(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.execute();
			rs = stmt.getResultSet();
			try {
				rs.next();
				FinalResult = rs.getInt(1);
				//                System.out.println(FinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException err) {
				err.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return FinalResult;
	}

	public static Map<String, String> tableResultInMapForm(ResultSet resultSet) {
		ResultSet rs = resultSet;
		List<String> columnNames = new LinkedList<>();
		Map<String, String> columnNameToValuesMap = new HashMap<String, String>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				columnNames.add(columnName);
				columnNameToValuesMap.put(columnName, new String());
			}
			while (rs.next()) {
				// Iterate the result set for each row
				for (String columnName : columnNames) {
					//Add the current row's column data to list
					String value1 = new String();
					value1 = rs.getString(columnName);
					//add the updated list of column data to the map now
					columnNameToValuesMap.put(columnName, value1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columnNameToValuesMap;
	}

	/**
	 * This method is used to get the min max length of all the registration fields.
	 */
	public static HashMap<String, String[]> getMinMaxOfRegFieldsDb() {
		String query = SqlQuery.getRegistrationTemplateConfig();
		HashMap<String, String[]> minMaxValueRegFields = new HashMap<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String[] minMaxLimit = new String[2];
				minMaxLimit[0] = rs.getString("Min Length");
				minMaxLimit[1] = rs.getString("Max Length");
				minMaxValueRegFields.put(rs.getString("ParentHead"), minMaxLimit);
			}
			return minMaxValueRegFields;
		} catch (Exception e) {
			e.printStackTrace();
			return minMaxValueRegFields;
		}
	}

	/**
	 * This method is used to get Notification module related data
	 */
	public static HashMap<String, Integer> getNotificationDataFromDb(String userName) {
		String query = SqlQuery.getNotificationQuery(userName);
		int rOutage = 0, uOutage = 0, rBilling = 0, uBilling = 0, rService = 0, uService = 0, rConnectMe = 0, uConnectMe = 0, rDR = 0, uDR = 0, rLeakAlert = 0, uLeakAlert = 0, rOthers = 0, uOthers = 0;
		int savedCount = 0, sentCount = 0, trashed = 0, receivedCount = 0, repliedCount = 0, allMailsCount = 0;
		HashMap<String, Integer> notificationMatrix = new HashMap<>();
		HashMap<String, String> msgIdMatrix = new HashMap<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String msgId = rs.getString("MessageId");
				if (msgIdMatrix.containsKey(msgId)) {
					String value = msgIdMatrix.get(msgId);
					if (value.equals("0") && rs.getString("IsReply").equals("1")) {
						msgIdMatrix.put(msgId, rs.getString("IsReply"));
						if (rs.getString("IsTrashed").equals("0")) {
							sentCount++;
							repliedCount++;
						}
					}
					if (value.equals("1") && rs.getString("IsReply").equals("1")) {
						if (rs.getString("IsTrashed").equals("0")) {
							repliedCount++;
						}
					}
				} else {
					msgIdMatrix.put(msgId, rs.getString("IsReply"));
					if (rs.getString("IsReply").equals("1")) {
						if (rs.getString("IsTrashed").equals("0"))
							sentCount++;
					}
					if (rs.getString("IsReply").equals("0")) {
						if (rs.getString("IsTrashed").equals("0"))
							receivedCount++;
					}
					if (rs.getString("IsTrashed").equals("0") && rs.getString("IsSaved").equals("1")) {
						savedCount++;
					}
					if (rs.getString("IsTrashed").equals("1")) {
						trashed++;
					} else {
						allMailsCount++;
					}
				}
				String category = rs.getString("PlaceHolderName");
				switch (category) {
					case "Outages":
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rOutage++;
								} else {
									uOutage++;
								}
							}
						}
						break;
					case "Billing":
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rBilling++;
								} else {
									uBilling++;
								}
							}
						}
						break;
					case "Service":
						if (rs.getString("IsReply").equalsIgnoreCase("0")) {
							if (rs.getString("IsTrashed").equalsIgnoreCase("0")) {
								if (rs.getString("IsRead").equalsIgnoreCase("1")) {
									rService++;
								} else {
									uService++;
								}
							}
						}
						break;
					case "Connect Me":
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rConnectMe++;
								} else {
									uConnectMe++;
								}
							}
						}
						break;
					case "Demand Response":
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rDR++;
								} else {
									uDR++;
								}
							}
						}
						break;
					case "Leak Alert":
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rLeakAlert++;
								} else {
									uLeakAlert++;
								}
							}
						}
						break;
					default:
						if (rs.getString("IsReply").equals("0")) {
							if (rs.getString("IsTrashed").equals("0")) {
								if (rs.getString("IsRead").equals("1")) {
									rOthers++;
								} else {
									uOthers++;
								}
							}
						}
				}
			}
			notificationMatrix.put("SavedCount", savedCount);
			notificationMatrix.put("SentCount", sentCount);
			notificationMatrix.put("TrashCount", trashed);
			notificationMatrix.put("ReceivedCount", receivedCount);
			notificationMatrix.put("RepliedCount", repliedCount);
			notificationMatrix.put("AllMailsCount", allMailsCount);
			notificationMatrix.put("ReadOutage", rOutage);
			notificationMatrix.put("UnReadOutage", uOutage);
			notificationMatrix.put("ReadBilling", rBilling);
			notificationMatrix.put("UnReadBilling", uBilling);
			notificationMatrix.put("ReadService", rService);
			notificationMatrix.put("UnReadService", uService);
			notificationMatrix.put("ReadConnectMe", rConnectMe);
			notificationMatrix.put("UnReadConnectMe", uConnectMe);
			notificationMatrix.put("ReadDR", rDR);
			notificationMatrix.put("UnReadDR", uDR);
			notificationMatrix.put("ReadLeakAlert", rLeakAlert);
			notificationMatrix.put("UnReadLeakAlert", uLeakAlert);
			notificationMatrix.put("ReadOthers", rOthers);
			notificationMatrix.put("UnReadOthers", uOthers);
			return notificationMatrix;
		} catch (Exception e) {
			e.printStackTrace();
			return notificationMatrix;
		}
	}

	/**
	 * This method is used to get Bill Statement.
	 * @param serviceAccountNumber
	 * @return billValue
	 */
	public static List<String> getBillStatementFromDb(String serviceAccountNumber) {
		String query = SqlQuery.getBillStatementQuery(serviceAccountNumber);
		List<String> billValue = new ArrayList<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String value = rs.getString("value");
				billValue.add(value);
			}
			return billValue;
		} catch (Exception e) {
			e.printStackTrace();
			return billValue;
		}
	}

	/**
	 * This method is used to get Payments.
	 * @param serviceAccountNumber
	 * @return billValue
	 */
	public static List<String> getPaymentsFromDb(String serviceAccountNumber) {
		String query = SqlQuery.getPaymentsQuery(serviceAccountNumber);
		List<String> paymentsValue = new ArrayList<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				String value = rs.getString("TransactionAmount");
				paymentsValue.add(value);
			}
			return paymentsValue;
		} catch (Exception e) {
			e.printStackTrace();
			return paymentsValue;
		}
	}

	public static HashMap<String, String> getInactiveAccInfo() {
		String query = SqlQuery.getInactiveAccDetails();
		HashMap<String, String> inactiveAccountInfo = new HashMap<>();
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			while (rs.next()) {
				inactiveAccountInfo.put("UtilityAccountNumber", rs
						.getString("utilityaccountnumber"));
				inactiveAccountInfo.put("ZipCode", rs.getString("zipcode"));
				inactiveAccountInfo.put("EmailID", rs
						.getString("emailid"));
				inactiveAccountInfo.put("PhoneNo", rs.getString("mobilephone"));
				break;
			}
			return inactiveAccountInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return inactiveAccountInfo;
		}
	}

	public static String getForgetPwdEmailLink(String userName) {
		String url = null;
		try {
			String emailBodyQuery = SqlQuery.getForgetPasswordEmailQuery(userName);
			ResultSet rs = DataBaseUtil.getResultSet(emailBodyQuery);
			rs.next();
			String str1 = rs.getString("Message");
			String s1 = "href='";
			String s2 = "'>Click here";
			url = str1.substring(str1.indexOf(s1) + s1.length(), str1.indexOf(s2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public static String getFirstSecQuestionOfUser(String userName) {
		String securityQue1Query = SqlQuery.getSecurityQue1Query(userName);
		String que1 = null;
		try {
			ResultSet rs = DataBaseUtil.getResultSet(securityQue1Query);
			rs.next();
			que1 = rs.getString("ControlText");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return que1;
	}

	public static String getResetPasswordHistoryDb() {
		String allUtilitySettingsQuery = SqlQuery.sAllUtilitySettingsQuery;
		String count = null;
		try {
			ResultSet rs = DataBaseUtil.getResultSet(allUtilitySettingsQuery);
			rs.next();
			count = rs.getString("ResetPasswordHistory");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static int getUserIdDb(String username) {
		String getUserIdQuery = SqlQuery.getUserId(username);
		String userId = null;
		try {
			ResultSet rs = DataBaseUtil.getResultSet(getUserIdQuery);
			rs.next();
			userId = rs.getString("UserId");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.parseInt(userId);
	}

	public static String getUserIdWithUserName(String username) {
		String getUserIdQuery = SqlQuery.getUserId(username);
		String userId = null;
		try {
			ResultSet rs = DataBaseUtil.getResultSet(getUserIdQuery);
			rs.next();
			userId = rs.getString("UserId");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}

	public static String getPrimaryContactType(String userName) {
		String query = "select Name from CommonMaster where MasterType = 'PhoneNumberType' " +
				"and MasterCode = (Select MobilePhoneType from [User] where UserName ='"
				+ userName + "')";
		ResultSet rs;
		String contactType = null;
		try {
			rs = DataBaseUtil.getResultSet(query);
			rs.next();
			contactType = rs.getString("Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactType;
	}

	public static String getAlternateContactType(String userName) {
		String query = "Select Name from CommonMaster where MasterType = 'PhoneNumberType' and " +
				"MasterCode = (Select HomePhoneType from [User] where UserName ='"
				+ userName + "')";
		String contactType = "";
		try {
			ResultSet rs = DataBaseUtil.getResultSet(query);
			if (rs.next())
				contactType = rs.getString("Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactType;
	}

	public static List<String> getAllTextsInNotificationPref(String sUsername) {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail " +
				"where AccountNotificationTypeID in (1, 5, 9, 13, 17, 21, 25, 29) " +
				"AND UserID = (Select UserID from [User] where UserName ='" + sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		try {
			while (rs.next()) {
				String value = rs.getString("EmailORPhone");
				lst.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<String> getAllEmailsInNotificationPref(String sUsername) {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail " +
				"where AccountNotificationTypeID in (2, 6, 10, 14, 18, 22, 26, 30) " +
				"AND UserID = (Select UserID from [User] where UserName ='" + sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		try {
			while (rs.next()) {
				String value = rs.getString("EmailORPhone");
				lst.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static List<String> getAllIVRsInNotificationPref(String sUsername) {
		String query = "select Distinct EmailORPhone from AccountNotificationDetail " +
				"where AccountNotificationTypeID in (4, 8, 12, 16, 20, 24, 28, 32) " +
				"AND UserID = (Select UserID from [User] where UserName ='" + sUsername + "')";
		ResultSet rs = DataBaseUtil.getResultSet(query);
		List<String> lst = new ArrayList<>();
		try {
			while (rs.next()) {
				String value = rs.getString("EmailORPhone");
				lst.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst;
	}

	public static String getAccountNumber(String utilityAccountNumber) {
		String query = SqlQuery.getAccountNumber(utilityAccountNumber);
		ResultSet rs = DataBaseUtil.getResultSet(query);
		String accountNo = "";
		try {
			while (rs.next()) {
				accountNo = rs.getString("AccountNumber");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountNo;
	}

	public static String getBillingDate(String utilityAccountNumber) {
		String sQuery = SqlQuery.getBillingDate(utilityAccountNumber);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		String Date = "";
		try {
			while (rs.next()) {
				Date = rs.getString("BillingDate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Date;
	}

	public static String getPaymentType(String utilityAccountNumber) {
		String sQuery = SqlQuery.getDefaultPaymentType(utilityAccountNumber);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		String pType = "";
		try {
			while (rs.next()) {
				pType = rs.getString("DefaultPaymentType");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pType;
	}

	public static List<String> getIsAmiStatus(String utilityAccountNumber) {
		String sQuery = SqlQuery.getIsAMIStatus(utilityAccountNumber);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("IsAMI");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getAccountStatus(String accountNum, String addressType, String status,
												String customerNum, String isPaperless, String linkedUsers,
												String zipCode, String cityName) {
		String sQuery = SqlQuery.getValueByFieldName(accountNum, addressType, status, customerNum,
				zipCode, cityName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("AccountStatus");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getServiceAccountNo(String accountNum, String AddType, String status,
												   String customerNum, String isPaperless,
												   String linkedUsers, String zipCode, String cityName) {
		String sQuery = SqlQuery.getValueByFieldName(accountNum, AddType, status, customerNum,
				zipCode, cityName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("UtilityAccountNumber");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getCountRowServiceAccount(String customerNum, String zipCode, String accountNum,
												   String Status, String addressType, String cityName,
												   String isPaperless, String linkedUser) {
		String value = "";
		if (isPaperless.equals("Yes")) {
			isPaperless = "0";
			String sQuery = SqlQuery.getCountOfAdvSearchResultServiceAccount(customerNum, zipCode, accountNum,
					Status, addressType, cityName, isPaperless, linkedUser);
			ResultSet rs = DataBaseUtil.getResultSet(sQuery);
			try {
				while (rs.next()) {
					value = rs.getString("TotalRecord");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (isPaperless.equals("No")) {
			isPaperless = "1";
			String sQuery = SqlQuery.getCountOfAdvSearchResultServiceAccount(customerNum, zipCode, accountNum,
					Status, addressType, cityName, isPaperless, linkedUser);
			ResultSet rs = DataBaseUtil.getResultSet(sQuery);
			try {
				while (rs.next()) {
					value = rs.getString("TotalRecord");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sQuery = SqlQuery.getCountOfAdvSearchResultServiceAccount(customerNum, zipCode, accountNum,
					Status, addressType, cityName, isPaperless, linkedUser);
			ResultSet rs = DataBaseUtil.getResultSet(sQuery);
			try {
				while (rs.next()) {
					value = rs.getString("TotalRecord");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String getCountRowCustomer(String customerNum, String zipCode, String accountNum,
											 String contactNo, String addressType, String cityName,
											 String firstName, String lastName, String email) {
		String value = "";
		String sQuery = SqlQuery.getCountOfAdvSearchResultCustomer(customerNum, zipCode, accountNum,
				contactNo, addressType, cityName, firstName, lastName, email);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("TotalRecord");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getCountRowUser(String accountNum, String addressType, String status, String userType,
										 String linkedAcc, String role, String isSocial, String customerNum,
										 String contactNo, String zipCode, String cityName, String firstName,
										 String lastName, String email) {
		String value = "";
		if (linkedAcc.equals("1")) {
			linkedAcc = "0-1";
			String sQuery = SqlQuery.getCountOfAdvSearchResultUser(accountNum, addressType, status, userType,
					linkedAcc, role, isSocial, customerNum, contactNo, zipCode, cityName, firstName, lastName,
					email);
			ResultSet rs = DataBaseUtil.getResultSet(sQuery);
			try {
				while (rs.next()) {
					value = rs.getString("TotalRecord");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sQuery = SqlQuery.getCountOfAdvSearchResultUser(accountNum, addressType, status, userType,
					linkedAcc, role, isSocial, customerNum, contactNo, zipCode, cityName, firstName, lastName,
					email);
			ResultSet rs = DataBaseUtil.getResultSet(sQuery);
			try {
				while (rs.next()) {
					value = rs.getString("TotalRecord");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String getDefaultAccWithUsername(String username) {
		String accNo = null;
		String query = SqlQuery.getDefaultAccQuery(username);
		try {
			ResultSet resultSet = DataBaseUtil.getResultSet(query);
			resultSet.next();
			accNo = resultSet.getString("utilityaccountnumber");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accNo;
	}

	public static String getEmailIdWithUserName(String username) {
		String email = null;
		String query = SqlQuery.getEmailIdWithUsername(username);
		try {
			ResultSet resultSet = DataBaseUtil.getResultSet(query);
			resultSet.next();
			email = resultSet.getString("emailid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}

	public static String getCountRowMode1(String itemToSearch) {
		String value = "";
		String sQuery = SqlQuery.getCountUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("Records");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getCountRowMode5(String itemToSearch) {
		String value = "";
		String sQuery = SqlQuery.getCountUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("Records");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getCountRowMode9(String itemToSearch) {
		String value = "";
		String sQuery = SqlQuery.getCountUniSearchResultMode9(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("Records");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static List<String> getUtilityAccNoUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("ServiceAccountnumber");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerNoUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("CustomerNumber");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerNameUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("CustomerName");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomersEmailUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("Customersemail");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getPrimaryPhoneUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("PrimaryPhone");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getAccountTypeUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("AccountType");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getAccountStatusUniSearch(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode1(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("AccountStatus");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerNoUniSearchMod5(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("CustomerNumber");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerNameUniSearchMode5(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("CustomerName");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomersEmailUniSearchMode5(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("Customersemail");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getPrimaryPhoneUniSearchMode5(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("PrimaryPhone");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getLinkedAccountUniSearchMode5(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode5(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("LinkedAccount");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getNameUniSearchMode9(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode9(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("Name");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getUserNameUniSearchMode9(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode9(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("UserName");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getEmailIDUniSearchMode9(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode9(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("EmailID");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getPrimaryPhoneUniSearchMode9(String itemToSearch) {
		String sQuery = SqlQuery.getUniSearchResultMode9(itemToSearch);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("PrimaryPhone");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getCountFieldValueUniSearch(String customerName, String zipCode, String accountNumber,
														   String emailId, String contactNo, String address) {
		String value = "";
		String sQuery = SqlQuery.getCountFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("TotalRecords");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getCountEmptyFieldSearchUniSearch() {
		String value = "";
		String sQuery = SqlQuery.getCountEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		try {
			while (rs.next()) {
				value = rs.getString("TotalRecords");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static List<String> getCustomerNameGlobal(String customerName, String zipCode, String accountNumber,
													 String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("CustomerName");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getServiceAccNoGlobal(String customerName, String zipCode, String accountNumber,
															 String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("AccountNumber");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getPrimaryContactNoGlobal(String customerName, String zipCode, String accountNumber,
														 String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("MobilePhone");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getEmailAddGlobal(String customerName, String zipCode, String accountNumber,
													 String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("EmailID");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerStatusGlobal(String customerName, String zipCode, String accountNumber,
													   String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("Status");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getAccountStatusGlobal(String customerName, String zipCode, String accountNumber,
													  String emailId, String contactNo, String address) {
		String sQuery = SqlQuery.getFieldValueUniSearch(customerName, zipCode, accountNumber, emailId,
				contactNo, address);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		try {
			while (rs.next()) {
				String value = rs.getString("AccountStatus");
				list.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> getCustomerNameGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("CustomerName");
			list.add(value);
		}
		return list;
	}

	public static List<String> getServiceAccNoGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("AccountNumber");
			list.add(value);
		}
		return list;
	}

	public static List<String> getPrimaryContactNoGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("MobilePhone");
			list.add(value);
		}
		return list;
	}

	public static List<String> getEmailAddGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("EmailID");
			list.add(value);
		}
		return list;
	}

	public static List<String> getCustomerStatusGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("Status");
			list.add(value);
		}
		return list;
	}

	public static List<String> getAccountStatusGlobalEmptySearch() throws Exception {
		String sQuery = SqlQuery.getEmptyFieldSearchUniSearch();
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("AccountStatus");
			list.add(value);
		}
		return list;
	}

	public static List<String> getDateTimeFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("ClickDate");
			list.add(value);
		}
		return list;
	}

	public static List<String> getBannerNameFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("BannerName");
			list.add(value);
		}
		return list;
	}

	public static List<String> getTitleFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("Title");
			list.add(value);
		}
		return list;
	}

	public static List<String> getCustomerNameFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("FullName");
			list.add(value);
		}
		return list;
	}

	public static List<String> getEmailIdFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("EmailId");
			list.add(value);
		}
		return list;
	}

	public static List<String> getAccountNumberFromBanners(String toDate, String fromDate, String moduleName) throws Exception {
		String sQuery = SqlQuery.getFilerDataForBanners(toDate, fromDate, moduleName);
		ResultSet rs = DataBaseUtil.getResultSet(sQuery);
		List<String> list = new ArrayList();
		while (rs.next()) {
			String value = rs.getString("UtilityAccountNumber");
			list.add(value);
		}
		return list;
	}

	/**
	 * This method is used to cancel delete profile request.
	 * @param username
	 * @param CancellationReason
	 * @throws Exception
	 */
	public static void cancelDeleteProfileStoredProcedure(String username, String CancellationReason) {
		String sUserIDQuery = SqlQuery.getUserId(username);
		String sUserID = DataBaseUtil.executeSQLQuery(sUserIDQuery);
		int iUserID = Integer.parseInt(sUserID);
		Connection connection = null;
		ResultSet rs = null;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call CancelDeletionRequest(?, ?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.setString(2, CancellationReason);
			stmt.execute();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is used to approve delete profile request.
	 * @param username
	 *
	 * @throws Exception
	 */
	public static void approveDeleteProfileStoredProcedure(String username) {
		String sUserIDQuery = SqlQuery.getUserId(username);
		String sUserID = DataBaseUtil.executeSQLQuery(sUserIDQuery);
		int iUserID = Integer.parseInt(sUserID);
		Connection connection = null;
		ResultSet rs = null;
		CallableStatement stmt = null;
		try {
			Class.forName(dbClass).newInstance();
			connection = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
			if (connection != null) {
			} else {
			}
			String insertUserID = "{call DeleteUserInfo(?)}";
			stmt = connection.prepareCall(insertUserID);
			stmt.setInt(1, iUserID);
			stmt.execute();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}