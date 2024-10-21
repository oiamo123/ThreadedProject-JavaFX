package org.example.demo.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.demo.models.additionals.Classes;
import org.example.demo.models.additionals.Fees;
import org.example.demo.models.additionals.Products_Suppliers;
import org.example.demo.models.additionals.Regions;
import org.example.demo.util.annotations.FieldInfo;

import java.util.Date;

@FieldInfo(name="Booking Detail")
public class BookingDetails {
    @FieldInfo(id = true, name = "First Name", isString = true, maxLength = 25)
    private SimpleIntegerProperty BookingDetailId;

    @FieldInfo(name = "Itinerary No", isDouble = true)
    private SimpleIntegerProperty ItineraryNo;

    @FieldInfo(name = "Trip Start", isDate = true)
    private SimpleObjectProperty<Date> TripStart;

    @FieldInfo(name = "Trip End", isDate = true)
    private SimpleObjectProperty<Date> TripEnd;

    @FieldInfo(name = "Description", isString = true, maxLength = 100)
    private SimpleStringProperty Description;

    @FieldInfo(name = "Destination", isString = true, maxLength = 100)
    private SimpleStringProperty Destination;

    @FieldInfo(name = "Price", isDouble = true, maxLength = 25)
    private SimpleDoubleProperty BasePrice;

    @FieldInfo(name = "Commission", isDouble = true, maxLength = 25)
    private SimpleDoubleProperty AgencyCommission;

    @FieldInfo(name = "Booking ID", isInt = true, maxLength = 11)
    private SimpleIntegerProperty BookingId;

    @FieldInfo(name = "Region ID", isString = true, maxLength = 5, className = Regions.class)
    private SimpleStringProperty RegionId;

    @FieldInfo(name = "Class ID", isString = true, maxLength = 5, className = Classes.class)
    private SimpleStringProperty ClassId;

    @FieldInfo(name = "Fee ID", isString = true, maxLength = 10, className = Fees.class)
    private SimpleStringProperty FeeId;

    @FieldInfo(name = "Product Supplier", isInt = true, className = Products_Suppliers.class)
    private SimpleIntegerProperty ProductSupplierId;

    // Constructors
    public BookingDetails() {
        this.BookingDetailId = new SimpleIntegerProperty();
        this.ItineraryNo = new SimpleIntegerProperty();
        this.TripStart = new SimpleObjectProperty<>();
        this.TripEnd = new SimpleObjectProperty<>();
        this.Description = new SimpleStringProperty();
        this.Destination = new SimpleStringProperty();
        this.BasePrice = new SimpleDoubleProperty();
        this.AgencyCommission = new SimpleDoubleProperty();
        this.BookingId = new SimpleIntegerProperty();
        this.RegionId = new SimpleStringProperty();
        this.ClassId = new SimpleStringProperty();
        this.FeeId = new SimpleStringProperty();
        this.ProductSupplierId = new SimpleIntegerProperty();
    }

    public BookingDetails(
            SimpleIntegerProperty itineraryNo,
            SimpleStringProperty description,
            SimpleStringProperty destination,
            SimpleDoubleProperty basePrice,
            SimpleDoubleProperty agencyCommission)
    {
        this.ItineraryNo = itineraryNo;
        this.Description = description;
        this.Destination = destination;
        this.BasePrice = basePrice;
        this.AgencyCommission = agencyCommission;
    }

    public int getBookingDetailId() {
        return BookingDetailId.get();
    }

    public SimpleIntegerProperty bookingDetailIdProperty() {
        return BookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.BookingDetailId.set(bookingDetailId);
    }

    public int getItineraryNo() {
        return ItineraryNo.get();
    }

    public SimpleIntegerProperty itineraryNoProperty() {
        return ItineraryNo;
    }

    public void setItineraryNo(int itineraryNo) {
        this.ItineraryNo.set(itineraryNo);
    }

    public Date getTripStart() {
        return TripStart.get();
    }

    public SimpleObjectProperty<Date> tripStartProperty() {
        return TripStart;
    }

    public void setTripStart(Date tripStart) {
        this.TripStart.set(tripStart);
    }

    public Date getTripEnd() {
        return TripEnd.get();
    }

    public SimpleObjectProperty<Date> tripEndProperty() {
        return TripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        this.TripEnd.set(tripEnd);
    }

    public String getDescription() {
        return Description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }

    public String getDestination() {
        return Destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return Destination;
    }

    public void setDestination(String destination) {
        this.Destination.set(destination);
    }

    public double getBasePrice() {
        return BasePrice.get();
    }

    public SimpleDoubleProperty basePriceProperty() {
        return BasePrice;
    }

    public void setBasePrice(double basePrice) {
        this.BasePrice.set(basePrice);
    }

    public double getAgencyCommission() {
        return AgencyCommission.get();
    }

    public SimpleDoubleProperty agencyCommissionProperty() {
        return AgencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.AgencyCommission.set(agencyCommission);
    }

    public int getBookingId() {
        return BookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        this.BookingId.set(bookingId);
    }

    public String getRegionId() {
        return RegionId.get();
    }

    public SimpleStringProperty regionIdProperty() {
        return RegionId;
    }

    public void setRegionId(String regionId) {
        this.RegionId.set(regionId);
    }

    public String getClassId() {
        return ClassId.get();
    }

    public SimpleStringProperty classIdProperty() {
        return ClassId;
    }

    public void setClassId(String classId) {
        this.ClassId.set(classId);
    }

    public String getFeeId() {
        return FeeId.get();
    }

    public SimpleStringProperty feeIdProperty() {
        return FeeId;
    }

    public void setFeeId(String feeId) {
        this.FeeId.set(feeId);
    }

    public int getProductSupplierId() {
        return ProductSupplierId.get();
    }

    public SimpleIntegerProperty productSupplierIdProperty() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.ProductSupplierId.set(productSupplierId);
    }
}
