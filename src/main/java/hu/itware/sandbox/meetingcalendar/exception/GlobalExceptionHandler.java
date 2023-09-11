package hu.itware.sandbox.meetingcalendar.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {MeetingCalendarBaseException.class})
    @ResponseBody
    protected ProblemDetail handleExceptions(MeetingCalendarBaseException ex, WebRequest request){
        return convertExceptionToProblemDetail(HttpStatus.BAD_REQUEST,ex);
    }

    private ProblemDetail convertExceptionToProblemDetail(HttpStatus status, MeetingCalendarBaseException baseException){
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setProperty("error",baseException.getError());
        detail.setProperty("errorCode",baseException.getCode());
        return detail;
    }

}
