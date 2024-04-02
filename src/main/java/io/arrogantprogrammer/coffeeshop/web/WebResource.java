package io.arrogantprogrammer.coffeeshop.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

@Path("/")
public class WebResource {

    static final Logger LOGGER = LoggerFactory.getLogger(WebResource.class);

    @Inject
    Template coffeeshopTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return coffeeshopTemplate
                .data("streamUrl", "http://localhost:8080/dashboard/stream")
                .data("storeId", "XtremeJ");
    }
}
