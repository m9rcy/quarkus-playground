package io.m9rcy.playground.web.filter;

import io.m9rcy.playground.domain.model.jwt.TokenState;
import io.m9rcy.playground.web.client.OauthService;
import io.m9rcy.playground.web.config.SystemConfigUtil;
import io.m9rcy.playground.web.model.response.AccessTokenResponse;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class ClientOauthRequestFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final String AUTH_RETRY_FLAG = "AUTH_RETRY_FLAG";
    private boolean configurationValid;

    private String ACCESS_TOKEN_URL;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String ACCESS_TOKEN_SCOPE;

    private AtomicReference<TokenState> currentTokenState = new AtomicReference<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOauthRequestFilter.class);

    public ClientOauthRequestFilter() {
        // Get the Config Statically, ConfigProperty will not work in a Provider
        ACCESS_TOKEN_URL = SystemConfigUtil.getStringConfig("access.token.url", null);
        CLIENT_ID = SystemConfigUtil.getStringConfig("client.id", null);
        CLIENT_SECRET = SystemConfigUtil.getStringConfig("client.secret", null);
        ACCESS_TOKEN_SCOPE = SystemConfigUtil.getStringConfig("access.token.scope", null);

        this.configurationValid = CLIENT_ID != null && CLIENT_SECRET != null && ACCESS_TOKEN_URL != null;
    }

    @SneakyThrows
    @Override
    public void filter(ClientRequestContext crc) throws IOException {
        if (crc.getUri().toString().equals(ACCESS_TOKEN_URL)) {
            return;
        }
        if (configurationValid) {
            OauthService oauthService = RestClientBuilder.newBuilder().baseUrl(new URI("https://m9rcy.auth0.com").toURL()).build(OauthService.class);

            TokenState tokenState = currentTokenState.get();
            if (tokenState == null) {
                tokenState = requestAccessTokens(oauthService);
                currentTokenState.set(tokenState);
            }
            long tokenIssueDate = tokenState.getIssueDate();
            long tokenTTL = tokenState.getTokenTTL();
            if (System.currentTimeMillis() > (tokenIssueDate + tokenTTL * 1000)) {
                tokenState = requestAccessTokens(oauthService);
                currentTokenState.set(tokenState);
            }
            Object currentToken = tokenState.getToken();
            crc.getHeaders().putSingle("Token", "Bearer " + currentToken);
            //crc.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, "Bearer " + currentToken);
            ((Closeable) oauthService).close();
        } else {
            crc.abortWith(javax.ws.rs.core.Response.status(Response.Status.PRECONDITION_FAILED).entity("Oauth client configuration Valid, but required configuration are not set").build());
        }
    }

    @SneakyThrows
    @Override
    public void filter(ClientRequestContext requestCtx, ClientResponseContext responseCtx) throws IOException {
        if (requestCtx.getUri().toString().equals(ACCESS_TOKEN_URL)) {
            return;
        }
        if (requestCtx.getProperty(AUTH_RETRY_FLAG) != null) {
            //we only retry once
            return;
        }
        if (responseCtx.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            boolean wasTokenSentBefore = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION).toLowerCase().contains("bearer");
            if (wasTokenSentBefore) { //attempt refresh
                OauthService oauthService = RestClientBuilder.newBuilder().baseUrl(new URI("https://m9rcy.auth0.com").toURL()).build(OauthService.class);
                requestCtx.setProperty(AUTH_RETRY_FLAG, true);
                TokenState newTokenState = requestAccessTokens(oauthService);
                repeatRequestWithAuth(requestCtx, responseCtx, newTokenState.getToken());
            }
        }
    }

    private TokenState requestAccessTokens(OauthService oauthService) {
        LOGGER.info("Requesting access token");
        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", CLIENT_ID)
                .param("client_secret", CLIENT_SECRET)
                .param("audience", ACCESS_TOKEN_SCOPE);
                //.param("scope", ACCESS_TOKEN_SCOPE);

        TokenState newState = null;
            newState = requestAccessTokenAndUpdateState(oauthService, form);
        LOGGER.info("Access token granted, ttl: {}", newState.getTokenTTL());
        return newState;
    }

    private TokenState requestAccessTokenAndUpdateState(OauthService oauthService, Form form){
        AccessTokenResponse tokenResp = oauthService.token(form);
        return updateClientTokenState(tokenResp);
    }

    /**
     * Repeat http call, just change the authorization header
     *
     * @param request  original req
     * @param response original response
     * @param token    newtoken
     * @return true if authorized
     */
    private boolean repeatRequestWithAuth(ClientRequestContext request, ClientResponseContext response, String token) {

        Client client = request.getClient();

        String method = request.getMethod();
        MediaType mediaType = request.getMediaType();
        URI lUri = request.getUri();

        WebTarget resourceTarget = client.target(lUri);

        Invocation.Builder builder = resourceTarget.request(mediaType);

        MultivaluedMap<String, Object> newHeaders = new MultivaluedHashMap<String, Object>();

        for (Map.Entry<String, List<Object>> entry : request.getHeaders().entrySet()) {
            if (HttpHeaders.AUTHORIZATION.equals(entry.getKey())) {
                continue;
            }
            newHeaders.put(entry.getKey(), entry.getValue());
        }

        newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        builder.headers(newHeaders);
        builder.property(AUTH_RETRY_FLAG, true);
        Invocation invocation;
        if (request.getEntity() == null) {
            invocation = builder.build(method);
        } else {
            invocation = builder.build(method, Entity.entity(request.getEntity(), request.getMediaType()));
        }
        Response nextResponse = invocation.invoke();

        if (nextResponse.hasEntity()) {
            response.setEntityStream(nextResponse.readEntity(InputStream.class));
        }
        MultivaluedMap<String, String> headers = response.getHeaders();
        headers.clear();
        headers.putAll(nextResponse.getStringHeaders());
        response.setStatus(nextResponse.getStatus());

        return response.getStatus() != Response.Status.UNAUTHORIZED.getStatusCode();
    }

    private TokenState updateClientTokenState(AccessTokenResponse tokenResp) {
        TokenState state = new TokenState();
        state.setIssueDate(System.currentTimeMillis());
        if (tokenResp.getRefreshToken() != null) {
            state.setRefreshToken(tokenResp.getRefreshToken());
        }
        state.setTokenTTL(tokenResp.getExpiresIn());
        state.setToken(tokenResp.getToken());

        return state;
    }
}
