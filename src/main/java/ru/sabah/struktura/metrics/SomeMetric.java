package ru.sabah.struktura.metrics;

import org.eclipse.microprofile.metrics.annotation.Gauge;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SomeMetric {
    @Gauge(unit = "zaza", absolute = true)
    public int appUpTimeSeconds() {
        return 22;
    }
}
