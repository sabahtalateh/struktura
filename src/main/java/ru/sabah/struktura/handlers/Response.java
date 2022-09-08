package ru.sabah.struktura.handlers;

import lombok.Getter;
import lombok.extern.java.Log;
import ru.sabah.struktura.services.ServiceError;
import ru.sabah.struktura.services.security.CanNotLoginError;
import ru.sabah.struktura.services.security.UsernameInUseError;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static java.net.HttpURLConnection.*;

@Getter
//@Setter
//@Accessors(fluent = true)
@Log
public class Response<T> {
    private final int code;
    private boolean success;
    private List<String> errors;
    private T data;

    public Response() {
        this.code = HTTP_OK;
    }

    public Response(int code) {
        this.code = code;
        int firstDigit = Integer.parseInt(Integer.toString(code).substring(0, 1));
        this.success = firstDigit == 2;
    }


    public static <T> Response<T> ok() {
        return new Response<>(HTTP_OK);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<T>(HTTP_OK).data(data);
    }

    public static <T> Response<T> badRequest() {
        return new Response<>(HTTP_BAD_REQUEST);
    }

    public static <T> Response<T> forbidden() {
        return new Response<>(HTTP_FORBIDDEN);
    }

    public static <T> Response<T> unauthorized() {
        return new Response<>(HTTP_UNAUTHORIZED);
    }

    public static <T> Response<T> notFound() {
        return new Response<>(HTTP_NOT_FOUND);
    }

    public static <T> Response<T> serverError() {
        return new Response<>(HTTP_INTERNAL_ERROR);
    }


    public Response<T> data(T data) {
        this.data = data;

        return this;
    }

    public Response<T> errors(List<String> errors) {
        this.errors = errors;

        return this;
    }

    public Response<T> addError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        this.errors.add(error);

        return this;
    }

    public static <T> Response<T> fromException(Exception e) {
        if (e instanceof ServiceError) {
            Response<T> resp = mapException(e);
            return resp.addError(e.getMessage());
        } else {
            log.log(Level.WARNING, e.getMessage(), e);
            return Response.serverError();
        }
    }

    public static <T> Response<T> mapException(Exception e) {
        Response<T> r;
        if (e instanceof UsernameInUseError) {
            r = Response.forbidden();
        } else if (e instanceof CanNotLoginError) {
            r = Response.unauthorized();
        } else {
            r = Response.badRequest();
        }
        return r;
    }
}
