package ru.sabah.struktura.healthchecks;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Liveness
@ApplicationScoped
public class PostgresHealth implements HealthCheck {

    ExecutorService service = Executors.newFixedThreadPool(1);

    @PersistenceContext(unitName = "master")
    private EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        long start = System.currentTimeMillis();

        var hc = HealthCheckResponse.named("PostgresLiveness");

        var future = service.submit(() -> {
            entityManager.createNativeQuery("select 1").getSingleResult();
        });

        try {
            future.get(5, TimeUnit.SECONDS);
            hc.up();
        } catch (Exception e) {
            hc.down();
            future.cancel(true); //this method will stop the running underlying task
        }

        long elapsed = System.currentTimeMillis() - start;
        String elapsedStr = String.format("%f", (float) elapsed / 1000);
//        elapsedStr = StringUtils.stripBack(elapsedStr, '0');
//        hc.withData("check_request_time", elapsedStr + " s.");

        return hc.build();
    }
}
