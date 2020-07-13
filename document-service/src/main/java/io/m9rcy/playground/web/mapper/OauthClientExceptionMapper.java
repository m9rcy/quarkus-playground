package io.m9rcy.playground.web.mapper;

import io.m9rcy.playground.domain.model.exception.BusinessException;
import io.m9rcy.playground.web.exception.ForbiddenException;
import io.m9rcy.playground.web.exception.UnauthorizedException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;

@Provider
@Priority(4000)
public class OauthClientExceptionMapper implements ResponseExceptionMapper<RuntimeException> {
    @Override
    public RuntimeException toThrowable(Response response) {
        int status = response.getStatus();
        String msg = getBody(response);

        BusinessException be ;
        switch (status) {
            case 401: be = new UnauthorizedException();         // (4)
                break;
            case 403: be = new ForbiddenException();         // (4)
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
        return be;
    }

    private String getBody(Response response) {
        ByteArrayInputStream is = (ByteArrayInputStream) response.getEntity();
        byte[] bytes = new byte[is.available()];
        is.read(bytes,0,is.available());
        String body = new String(bytes);
        return body;
    }
}
