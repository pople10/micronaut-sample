/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.consts;

public enum ExceptionCode {
    BAD_VALIDATION(422),
    ENTITY_NOT_FOUND(404),
    UNAUTHENTICATED(401),
    FORBIDDEN(403),
    ACTION_REQUIRED(402),
    INTERNAL_ERROR(500);

    private final int code;

    ExceptionCode(int i) {
        this.code = i;
    }

    public int getCode() {
        return this.code;
    }
}
