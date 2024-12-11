package com.socialtripper.restapi.exceptions;

import com.socialtripper.restapi.dto.messages.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.HttpURLConnection;

/**
 * Globalny kontroler obsługi wyjątków w aplikacji.
 * Klasa przechwytuje określone wyjątki, zwracając odpowiednie odpowiedzi HTTP
 * w spójnej strukturze JSON za pomocą obiektu {@link ErrorDTO}.
 * Klasa jest adnotowana poprzez ControllerAdvice, co sprawia, że obsługuje wyjątki
 * w całej aplikacji.
 */
@ControllerAdvice
public class FaultController {
    /**
     * Obsługa wyjąteku {@link EventNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link EventNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> eventNotFoundExceptionHandler(EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link PostNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link PostNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> postNotFoundExceptionHandler(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link AccountNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link AccountNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> accountNotFoundExceptionHandler(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link UserNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link UserNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> userNotFoundExceptionHandler(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link GroupNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link GroupNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> groupNotFoundExceptionHandler(GroupNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link CommentNotFoundException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 404 (Not Found).
     * @param e wyjątek - {@link CommentNotFoundException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDTO> commentNotFoundExceptionHandler(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(HttpURLConnection.HTTP_NOT_FOUND,HttpStatus.NOT_FOUND, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link UsernameAlreadyTakenException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 409 (Conflict).
     * @param e wyjątek - {@link UsernameAlreadyTakenException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> usernameTakenExceptionHandler(UsernameAlreadyTakenException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(HttpURLConnection.HTTP_CONFLICT,HttpStatus.CONFLICT, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link PhoneNumberAlreadyInUseException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 409 (Conflict).
     * @param e wyjątek - {@link PhoneNumberAlreadyInUseException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(PhoneNumberAlreadyInUseException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> phoneNumberTakenExceptionHandler(PhoneNumberAlreadyInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(HttpURLConnection.HTTP_CONFLICT,HttpStatus.CONFLICT, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link EmailAlreadyInUseException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 409 (Conflict).
     * @param e wyjątek - {@link EmailAlreadyInUseException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> emailAddressTakenExceptionHandler(EmailAlreadyInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(HttpURLConnection.HTTP_CONFLICT,HttpStatus.CONFLICT, e.getMessage()));
    }

    /**
     * Obsługa wyjąteku {@link EventMembersLimitException}.
     * Gdy wyjątek zostanie rzucony, metoda zwraca odpowiedź HTTP z kodem 409 (Conflict).
     * @param e wyjątek - {@link EventMembersLimitException}
     * @return informacja o błędzie - {@link ErrorDTO}
     */
    @ResponseBody
    @ExceptionHandler(EventMembersLimitException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ResponseEntity<ErrorDTO> eventMembersLimitReachedExceptionHandler(EventMembersLimitException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorDTO(HttpURLConnection.HTTP_CONFLICT,HttpStatus.CONFLICT, e.getMessage()));
    }

}
