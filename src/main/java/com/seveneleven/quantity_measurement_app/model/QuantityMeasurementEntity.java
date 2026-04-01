package com.seveneleven.quantity_measurement_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity", indexes = {
        @Index(name = "idx_operation", columnList = "operation"),
        @Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value", nullable = false)
    private double thisValue;

    @Column(name = "this_unit", nullable = false)
    private String thisUnit;

    @Column(name = "this_measurement_type", nullable = false)
    private String thisMeasurementType;

    @Column(name = "that_value")
    private double thatValue;

    @Column(name = "that_unit")
    private String thatUnit;

    @Column(name = "that_measurement_type")
    private String thatMeasurementType;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "result_value")
    private Double resultValue;

    @Column(name = "result_unit")
    private String resultUnit;

    @Column(name = "result_measurement_type")
    private String resultMeasurementType;

    @Column(name = "result_string")
    private String resultString;

    @Column(name = "is_error")
    private boolean isError;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- JPA Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- Constructors ---

    public QuantityMeasurementEntity() {}

    // Constructor for Compare (Result is a String like "Equal")
    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty, String op, String resultStr) {
        mapQuantities(thisQty, thatQty);
        this.operation = op;
        this.resultString = resultStr;
    }

    // Constructor for Convert (Result is a double)
    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty, String op, double resultVal) {
        mapQuantities(thisQty, thatQty);
        this.operation = op;
        this.resultValue = resultVal;
    }

    // Constructor for Arithmetic (Result is a DTO)
    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty, String op, QuantityDTO resultDTO) {
        mapQuantities(thisQty, thatQty);
        this.operation = op;
        if (resultDTO != null) {
            this.resultValue = resultDTO.getValue();
            this.resultUnit = resultDTO.getUnit();
            this.resultMeasurementType = resultDTO.getMeasurementType();
        }
    }

    // Constructor for Errors
    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty, String op, String errorMsg, boolean isError) {
        mapQuantities(thisQty, thatQty);
        this.operation = op;
        this.errorMessage = errorMsg;
        this.isError = isError;
    }

    private void mapQuantities(QuantityDTO thisQty, QuantityDTO thatQty) {
        if (thisQty != null) {
            this.thisValue = thisQty.getValue();
            this.thisUnit = thisQty.getUnit();
            this.thisMeasurementType = thisQty.getMeasurementType();
        }
        if (thatQty != null) {
            this.thatValue = thatQty.getValue();
            this.thatUnit = thatQty.getUnit();
            this.thatMeasurementType = thatQty.getMeasurementType();
        }
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getThisValue() { return thisValue; }
    public void setThisValue(double val) { this.thisValue = val; }

    public String getThisUnit() { return thisUnit; }
    public void setThisUnit(String unit) { this.thisUnit = unit; }

    public String getThisMeasurementType() { return thisMeasurementType; }
    public void setThisMeasurementType(String type) { this.thisMeasurementType = type; }

    public String getOperation() { return operation; }
    public void setOperation(String op) { this.operation = op; }

    public void setResultUnit(String unit) { this.resultUnit = unit; }
    public String getResultUnit() { return resultUnit; }

    public void setResultMeasurementType(String type) { this.resultMeasurementType = type; }
    public String getResultMeasurementType() { return resultMeasurementType; }

    public boolean isError() { return isError; }
    public void setError(boolean error) { isError = error; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String msg) { this.errorMessage = msg; }
    
    public double getThatValue() {
        return thatValue;
    }

    public void setThatValue(double thatValue) {
        this.thatValue = thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public void setThatUnit(String thatUnit) {
        this.thatUnit = thatUnit;
    }
 // --- That Measurement Type ---
    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public void setThatMeasurementType(String thatMeasurementType) {
        this.thatMeasurementType = thatMeasurementType;
    }

    // --- Result String ---
    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    // --- Result Value ---
    public Double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    // Add other missing getters/setters as needed for your DTO mapping...
}