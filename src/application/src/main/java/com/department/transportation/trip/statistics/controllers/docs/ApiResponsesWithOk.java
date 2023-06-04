package com.department.transportation.trip.statistics.controllers.docs;

import com.department.transportation.trip.statistics.core.exceptions.ExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author <a href="mailto:alexsros@gmail.com">Alex Rosa</a>
 * @since 04/06/2023 21:43
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "405", description = "Method Not Allowed",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server Error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class)))
})
public @interface ApiResponsesWithOk {
}
