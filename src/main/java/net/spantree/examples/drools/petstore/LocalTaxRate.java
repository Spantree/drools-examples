package net.spantree.examples.drools.petstore;

import java.time.LocalDateTime;

public class LocalTaxRate extends TaxRate {
    private String state;
    private String city;

    public LocalTaxRate(String state, String city, Double rate) {
        super(rate);
        this.state = state;
        this.city = city;
    }

    public LocalTaxRate(String state, String city, Double rate, LocalDateTime dateActive) {
        super(rate, dateActive);
        this.state = state;
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
}
