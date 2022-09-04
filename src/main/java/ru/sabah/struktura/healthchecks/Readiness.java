package ru.sabah.struktura.healthchecks;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@org.eclipse.microprofile.health.Readiness
@ApplicationScoped
public class Readiness implements HealthCheck {
    private AtomicLong readyTime = new AtomicLong(0);

    public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
        readyTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("ReadinessCheck")
                .state(isReady())
                .withData("time", readyTime.get())
                .build();
    }

    /**
     * Become ready after 5 seconds
     *
     * @return true if application ready
     */
    private boolean isReady() {
        return Duration.ofMillis(System.currentTimeMillis() - readyTime.get()).getSeconds() >= 5;
    }
}
