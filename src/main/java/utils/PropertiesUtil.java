package utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    Properties props = null;
    OutputStream outputStream = null;
    InputStream inputStream = null;
    String configPropFP;

    public PropertiesUtil(String configPropFP) {
        try {
            props = new Properties();
            this.configPropFP = configPropFP;
            inputStream = new FileInputStream(configPropFP);
            props.load(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getPropValue(String key) {
        String value = null;
        value = props.getProperty(key).trim();
        return value;
    }

    /**
     * This method is to set the property value of the given key.
     *
     * @param customerType
     * @param username
     * @param password
     * @return
     */
    public void setPropValue(String customerType, String username, String password) {
        try {
            inputStream = new FileInputStream(configPropFP);
            //Properties props = new Properties();
            props.load(inputStream);
            inputStream.close();

            outputStream = new FileOutputStream(configPropFP);

            if (customerType == "residential") {
                props.setProperty("rUsername", username);
                props.setProperty("rPassword", password);
            } else if (customerType == "business") {
                props.setProperty("bUsername", username);
                props.setProperty("bPassword", password);
            }

            props.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is to set the property value of the given key.
     *
     * @param customerType
     * @param username
     * @param password
     * @param email
     * @return
     */
    public void setPropValue(String customerType, String username, String password, String email) {
        try {
            inputStream = new FileInputStream(configPropFP);
            //Properties props = new Properties();
            props.load(inputStream);
            inputStream.close();

            outputStream = new FileOutputStream(configPropFP);

            if (customerType == "residential") {
                props.setProperty("rUsername", username);
                props.setProperty("rPassword", password);
                props.setProperty("rEmail", email);
            } else if (customerType == "business") {
                props.setProperty("bUsername", username);
                props.setProperty("bPassword", password);
                props.setProperty("bEmail", email);
            }

            props.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is to set the property value of the given key.
     *
     * @param customerType
     * @param username
     * @param password
     * @param email
     * @param accNum
     * @param fName
     * @param lName
     * @return
     */
    public void setPropValue(String customerType, String username, String password, String email, String accNum, String fName, String lName) {
        try {
            inputStream = new FileInputStream(configPropFP);
            //Properties props = new Properties();
            props.load(inputStream);
            inputStream.close();

            outputStream = new FileOutputStream(configPropFP);

            if (customerType == "residential") {
                props.setProperty("rUsername", username);
                props.setProperty("rPassword", password);
                props.setProperty("rEmail", email);
                props.setProperty("rAccountNumber", accNum);
                props.setProperty("rFirstName", fName);
                props.setProperty("rLastName", lName);
            } else if (customerType == "business") {
                props.setProperty("bUsername", username);
                props.setProperty("bPassword", password);
                props.setProperty("bEmail", email);
                props.setProperty("bAccountNumber", accNum);
                props.setProperty("bFirstName", fName);
                props.setProperty("bLastName", lName);
            }

            props.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
