package ru.sabah.struktura.metrics;

import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SomeMetric {
    @Gauge(unit = "zaza", absolute = true)
    public String appUpTimeSeconds() {
        return "Hello!";
    }
}
