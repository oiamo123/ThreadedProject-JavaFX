package org.example.demo.models.additionals;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.demo.util.annotations.FieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Agents {
    @FieldInfo(id = true, name = "ID")
    private SimpleIntegerProperty AgentId = new SimpleIntegerProperty();

    @FieldInfo(name = "First Name", isDisplayValue = true)
    private SimpleStringProperty AgtFirstName = new SimpleStringProperty();

    @FieldInfo(name = "Last Name", isDisplayValue = true)
    private SimpleStringProperty AgtLastName = new SimpleStringProperty();

    public Agents() {}

    @Override
    public String toString() {
        return AgtFirstName.get() + " " + AgtLastName.get();
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

    public String getAgtFirstName() {
        return AgtFirstName.get();
    }

    public SimpleStringProperty agtFirstNameProperty() {
        return AgtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.AgtFirstName.set(agtFirstName);
    }

    public String getAgtLastName() {
        return AgtLastName.get();
    }

    public SimpleStringProperty agtLastNameProperty() {
        return AgtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.AgtLastName.set(agtLastName);
    }
}
