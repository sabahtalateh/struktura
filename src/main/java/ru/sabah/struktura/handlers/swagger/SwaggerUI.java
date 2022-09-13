package ru.sabah.struktura.handlers.swagger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("swagger")
@NoArgsConstructor
public class SwaggerUI {

    private static final String respTmpl = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="utf-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1" />
              <meta
                name="description"
                content="SwaggerUI"
              />
              <title>SwaggerUI</title>
              <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@4.5.0/swagger-ui.css" />
            </head>
            <body>
            <div id="swagger-ui"></div>
            <script src="https://unpkg.com/swagger-ui-dist@4.5.0/swagger-ui-bundle.js" crossorigin></script>
            <script>
              window.onload = () => {
                window.ui = SwaggerUIBundle({
                  url: '%s:%s/openapi',
                  dom_id: '#swagger-ui',
                });
              };
            </script>
            </body>
            </html>""";

    private static String resp;

    @Inject
    public SwaggerUI(
            @ConfigProperty(name = "server.host") String serverHost,
            @ConfigProperty(name = "server.port") String serverPort
    ) {
        resp = String.format(respTmpl, String.format("%s://%s", "http", serverHost), serverPort);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response swagger() {
        return Response
                .status(Response.Status.OK)
                .entity(resp)
                .build();
    }
}
