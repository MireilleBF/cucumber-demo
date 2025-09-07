package fr.unice.polytech.biblio.api;

public class HttpUtils {
    public static final int OK_CODE = 200;
    public static final int CREATED_CODE = 201;
    public static final int NO_CONTENT_CODE = 204;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND_RESOURCE = 404;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";

    private HttpUtils() {
        throw new IllegalStateException("Utility class");
    }
}
