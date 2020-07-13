package io.m9rcy.playground.web.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.m9rcy.playground.domain.model.constants.ValidationMessages;
import io.m9rcy.playground.domain.model.service.ApplicationService;
import io.m9rcy.playground.web.model.request.NewApplication;
import io.m9rcy.playground.web.model.response.ApplicationResponse;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/credit-card-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreditCardResource {

  private static final Logger LOG = Logger.getLogger(CreditCardResource.class);

  @Inject
  ApplicationService applicationService;

  @Inject
  ObjectMapper objectMapper;


  @Operation(summary = "Creates new Credit Card Application")
  @APIResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "422", description = "Entity Errors")
  @Counted(name = "countApplications", description = "Counts how newCreditCardApplication method has been invoked")
  @Timed(name = "timeApplications", description = "Times how long it takes to invoke the newCreditCardApplication method", unit = MetricUnits.MILLISECONDS)
  @POST
  @Path("/applications")
  public Response newCreditCardApplication(@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
                                           NewApplication newApplication) throws JsonProcessingException {
    LOG.info(newApplication);
    String title = String.join(newApplication.getFirstName(), "-" ,newApplication.getLastName(), "-", "CC");
    String customerName = String.join(newApplication.getFirstName(), newApplication.getLastName());
    String stringyfy = objectMapper.writeValueAsString(newApplication);
    return Response.ok(applicationService.create(title, "Mastercard Silver", customerName, newApplication.getCustomerId(), stringyfy)).status(Response.Status.CREATED).build();
  }

  @Operation(summary = "Update Credit Card Application")
  @APIResponse(responseCode = "204", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @PUT
  @Path("/applications/{slug}")
  public Response updateCreditCardApplication() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Delete Credit Card Application")
  @APIResponse(responseCode = "204", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @DELETE
  @Path("/applications/{slug}")
  public Response deleteApplication() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Find all Credit Card Application")
  @APIResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @GET
  @Path("/applications")
  public Response findAllApplications() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Add Supporting document to Credit Card Application")
  @APIResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "422", description = "Entity Errors")
  @POST
  @Path("/applications/{slug}/documents")
  public Response newApplicationDocument() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Submits new Credit Card Application")
  @APIResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "422", description = "Entity Errors")
  @Counted(name = "countNewCreditCardSubmission", description = "Counts how newCreditCardSubmission method has been invoked")
  @Timed(name = "timeNewCreditCardSubmission", description = "Times how long it takes to invoke the newCreditCardSubmission method", unit = MetricUnits.MILLISECONDS)
  @POST
  public Response newCreditCardSubmission() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Balance Transfer")
  @APIResponse(responseCode = "201", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "422", description = "Entity Errors")
  @Counted(name = "countBalanceTransfer", description = "Counts how balanceTransfer method has been invoked")
  @Timed(name = "timeBalanceTransfer", description = "Times how long it takes to invoke the balanceTransfer method", unit = MetricUnits.MILLISECONDS)
  @POST
  @Path("/balance-transfer")
  public Response balanceTransfer() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @Operation(summary = "Submits Credit Card Limit increase")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ApplicationResponse.class)))
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "401", description = "Unauthorized")
  @APIResponse(responseCode = "422", description = "Entity Errors")
  @Counted(name = "countLimitIncrease", description = "Counts how limitIncrease method has been invoked")
  @Timed(name = "timeLimitIncrease", description = "Times how long it takes to invoke the limitIncrease method", unit = MetricUnits.MILLISECONDS)
  @POST
  @Path("/limit-increase")
  public Response limitIncrease() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }
}
