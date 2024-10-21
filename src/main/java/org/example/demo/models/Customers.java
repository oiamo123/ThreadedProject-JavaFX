package org.example.demo.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.demo.models.additionals.Agents;
import org.example.demo.util.annotations.FieldInfo;

import java.sql.ResultSet;

@FieldInfo(name = "Customer")
public class Customers {
    @FieldInfo(name = "Customer ID", id = true)
    private SimpleIntegerProperty CustomerId;

    @FieldInfo(name = "First Name", isString = true, maxLength = 25)
    private SimpleStringProperty CustFirstName;

    @FieldInfo(name = "Last Name", isString = true, maxLength = 25)
    private SimpleStringProperty CustLastName;

    @FieldInfo(name = "Address", isString = true, maxLength = 75)
    private SimpleStringProperty CustAddress;

    @FieldInfo(name = "City", isString = true, maxLength = 50)
    private SimpleStringProperty CustCity;

    @FieldInfo(name = "Province", isString = true, maxLength = 2)
    private SimpleStringProperty CustProv;

    @FieldInfo(name = "Postal", isString = true, maxLength = 6)
    private SimpleStringProperty CustPostal;

    @FieldInfo(name = "Country", isString = true, maxLength = 25)
    private SimpleStringProperty CustCountry;

    @FieldInfo(name = "Home Phone", isString = true, maxLength = 20)
    private SimpleStringProperty CustHomePhone;

    @FieldInfo(name = "Business Phone", isString = true, maxLength = 20)
    private SimpleStringProperty CustBusPhone;

    @FieldInfo(name = "Email", isString = true, maxLength = 50)
    private SimpleStringProperty CustEmail;

    @FieldInfo(name = "Agent", isInt = true, className = Agents.class)
    private SimpleIntegerProperty AgentId;

    // Constructors
    public Customers() {
        this.CustomerId = new SimpleIntegerProperty();
        this.CustFirstName = new SimpleStringProperty();
        this.CustLastName = new SimpleStringProperty();
        this.CustAddress = new SimpleStringProperty();
        this.CustCity = new SimpleStringProperty();
        this.CustProv = new SimpleStringProperty();
        this.CustPostal = new SimpleStringProperty();
        this.CustCountry = new SimpleStringProperty();
        this.CustHomePhone = new SimpleStringProperty();
        this.CustBusPhone = new SimpleStringProperty();
        this.CustEmail = new SimpleStringProperty();
        this.AgentId = new SimpleIntegerProperty();
    }

    @Override
    public String toString() {
        return CustFirstName.get() + " " + CustLastName.get();
    }

    public int getCustomerId() {
        return CustomerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        this.CustomerId.set(customerId);
    }

    public String getCustFirstName() {
        return CustFirstName.get();
    }

    public SimpleStringProperty custFirstNameProperty() {
        return CustFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.CustFirstName.set(custFirstName);
    }

    public String getCustLastName() {
        return CustLastName.get();
    }

    public SimpleStringProperty custLastNameProperty() {
        return CustLastName;
    }

    public void setCustLastName(String custLastName) {
        this.CustLastName.set(custLastName);
    }

    public String getCustAddress() {
        return CustAddress.get();
    }

    public SimpleStringProperty custAddressProperty() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        this.CustAddress.set(custAddress);
    }

    public String getCustCity() {
        return CustCity.get();
    }

    public SimpleStringProperty custCityProperty() {
        return CustCity;
    }

    public void setCustCity(String custCity) {
        this.CustCity.set(custCity);
    }

    public String getCustProv() {
        return CustProv.get();
    }

    public SimpleStringProperty custProvProperty() {
        return CustProv;
    }

    public void setCustProv(String custProv) {
        this.CustProv.set(custProv);
    }

    public String getCustPostal() {
        return CustPostal.get();
    }

    public SimpleStringProperty custPostalProperty() {
        return CustPostal;
    }

    public void setCustPostal(String custPostal) {
        this.CustPostal.set(custPostal);
    }

    public String getCustCountry() {
        return CustCountry.get();
    }

    public SimpleStringProperty custCountryProperty() {
        return CustCountry;
    }

    public void setCustCountry(String custCountry) {
        this.CustCountry.set(custCountry);
    }

    public String getCustHomePhone() {
        return CustHomePhone.get();
    }

    public SimpleStringProperty custHomePhoneProperty() {
        return CustHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.CustHomePhone.set(custHomePhone);
    }

    public String getCustBusPhone() {
        return CustBusPhone.get();
    }

    public SimpleStringProperty custBusPhoneProperty() {
        return CustBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.CustBusPhone.set(custBusPhone);
    }

    public String getCustEmail() {
        return CustEmail.get();
    }

    public SimpleStringProperty custEmailProperty() {
        return CustEmail;
    }

    public void setCustEmail(String custEmail) {
        this.CustEmail.set(custEmail);
    }

    public int getAgentId() {
        return AgentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        this.AgentId.set(agentId);
    }
}
