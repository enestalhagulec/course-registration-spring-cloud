package net.etg.registerservice.exception;

import lombok.Data;

@Data
public class ErrorDetail {
    String message;
    String code;
    String path;
}
