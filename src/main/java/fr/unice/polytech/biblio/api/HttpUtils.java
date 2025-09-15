package fr.unice.polytech.biblio.api;

public class HttpUtils {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;

    public static final int BAD_REQUEST = 400;
    public static final int RESOURCE_NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final int INTERNAL_SERVER_ERROR = 500;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";

    private HttpUtils() {
        throw new IllegalStateException("Utility class");
    }
}
