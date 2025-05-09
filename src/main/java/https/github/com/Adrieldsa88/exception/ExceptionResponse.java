package https.github.com.Adrieldsa88.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
