package com.example.demo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuadraticEquation {
    @JsonProperty("a")
    private double a;
    @JsonProperty("b")
    private double b;
    @JsonProperty("c")
    private double c;

    public QuadraticEquation() {
        a = 1;
        b = 0;
        c = 0;
    }

    public QuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("The coefficient 'a' must not be equal to zero.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @JsonProperty("discriminant")
    public double d() {
        return Math.pow(b, 2) - 4 * a * c;
    }

    @JsonProperty("x1")
    public String x1() {
        if (d() < 0) {
            return Double.toString(-b / ( 2 * a)) + " - " + Double.toString(Math.sqrt(Math.abs(d()))) + "i";
        }

        return Double.toString((-b - Math.sqrt(d())) / (2 * a));

    } 

    @JsonProperty("x2")
    public String x2() {
        if (d() < 0) {
            return Double.toString(-b / ( 2 * a)) + " + " + Double.toString(Math.sqrt(Math.abs(d()))) + "i";
        }
        else if (d() > 0) {
            return Double.toString((-b + Math.sqrt(d())) / (2 * a));
        }

        return null;
    } 
    
}
