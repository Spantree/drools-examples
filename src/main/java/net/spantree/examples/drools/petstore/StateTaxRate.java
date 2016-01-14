package net.spantree.examples.drools.petstore;

import java.time.LocalDateTime;

public class StateTaxRate extends TaxRate {
    private String state;

    public StateTaxRate(String state, Double rate) {
        super(rate);
        this.state = state;
    }

    public StateTaxRate(String state, Double rate, LocalDateTime dateActive) {
        super(rate, dateActive);
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
