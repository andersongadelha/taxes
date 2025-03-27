package br.com.zup.taxes.external;

import br.com.zup.taxes.exceptions.StackSpotForbiddenException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.FORBIDDEN.value()) {
            return new StackSpotForbiddenException(response.reason());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}