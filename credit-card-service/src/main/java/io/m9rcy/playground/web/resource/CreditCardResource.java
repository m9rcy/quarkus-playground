package io.m9rcy.playground.web.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/credit-card-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreditCardResource {

  @POST
  @Path("/applications")
  public Response newCreditCardApplication() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @PUT
  @Path("/applications/{slug}")
  public Response updateCreditCardApplication() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @DELETE
  @Path("/applications/{slug}")
  public Response deleteApplication() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @GET
  @Path("/applications")
  public Response findAllApplications() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @POST
  @Path("/applications/{slug}/documents")
  public Response newApplicationDocument() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @POST
  public Response newCreditCardSubmission() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @POST
  @Path("/balance-transfer")
  public Response balanceTransfer() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }

  @POST
  @Path("/limit-increase")
  public Response limitIncrease() {
    return Response.ok("Not Implemented").status(Response.Status.NOT_IMPLEMENTED).build();
  }
}
