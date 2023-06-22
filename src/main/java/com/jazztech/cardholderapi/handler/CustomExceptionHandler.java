package com.jazztech.cardholderapi.handler;

import com.jazztech.cardholderapi.handler.exceptions.CardHolderAlreadyRegisteredException;
import com.jazztech.cardholderapi.handler.exceptions.ClientDoesNotCorrespondToCreditAnalysisException;
import com.jazztech.cardholderapi.handler.exceptions.CreditAnalysisNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final URI CARD_HOLDERS_URI = URI.create("/card-holders");

    @ExceptionHandler(CreditAnalysisNotFoundException.class)
    public ProblemDetail creditAnalysisNotFoundExceptionHandler(CreditAnalysisNotFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("http://jazztech.com/credit-analysis-not-found"));
        problemDetail.setTitle("Credit Analysis Not Found");
        problemDetail.setStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(CARD_HOLDERS_URI);
        return problemDetail;
    }

    @ExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException.class)
    public ProblemDetail clientDoesNotCorrespondToCreditAnalysisExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("http://jazztech.com/client-does-not-correspond-credit-analysis"));
        problemDetail.setTitle("Client Doesn't Correspond To Credit Analysis");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(CARD_HOLDERS_URI);
        return problemDetail;
    }

    @ExceptionHandler(CardHolderAlreadyRegisteredException.class)
    public ProblemDetail cardHolderAlreadyRegistredExceptionHandler(CardHolderAlreadyRegisteredException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create("http://jazztech.com/already-exists"));
        problemDetail.setTitle("Card Holder Already Exists");
        problemDetail.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(CARD_HOLDERS_URI);
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationExceptionHandler(ConstraintViolationException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("http://jazztech.com/invalid-argument"));
        problemDetail.setTitle("Invalid Arguments");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getConstraintViolations().toString());
        problemDetail.setInstance(CARD_HOLDERS_URI);
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("http://jazztech.com/invalid-argument"));
        problemDetail.setTitle("Invalid Arguments");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(CARD_HOLDERS_URI);
        return problemDetail;
    }

}
