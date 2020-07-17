package br.com.abinbev.controller.exception;

import br.com.abinbev.controller.response.ErrorResponse;
import br.com.abinbev.exceptions.DataNotFoundException;
import br.com.abinbev.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class CustumExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> processFeatureException(IllegalArgumentException e){
        log.warn("Ocurred illegal error: {}",e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> processException(Exception e){
        log.error("An internal error ocurred:{}",e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> processValidationException(MethodArgumentNotValidException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, ErrorResponse> mapErrors = new HashMap<>();
        List<ErrorResponse> errorsReponse = new ArrayList<>();
        List<Field> fields = Arrays.asList(ex.getParameter().getParameterType().getDeclaredFields());
        ex.getBindingResult().getFieldErrors().forEach((fieldError -> {
            Field fieldClazz = fields.stream().filter(field->field.getName().equals(fieldError.getField())).findFirst().get();
            List<Annotation> annotations = Arrays.asList(fieldClazz.getDeclaredAnnotations());
            Annotation annotation  = annotations.stream()
                    .filter(annotationClazz -> annotationClazz instanceof JsonProperty)
                    .findFirst().get();
            JsonProperty property = (JsonProperty) annotation;
            if(mapErrors.containsKey(property.value())){
                ErrorResponse errorResponse = mapErrors.get(property.value());
                List<String> defaultMessageErrors = errorResponse.getFieldErrors();
                defaultMessageErrors.add(fieldError.getDefaultMessage());
                errorResponse.setFieldErrors(defaultMessageErrors);
                mapErrors.put(property.value(),errorResponse);
            }else{
                List<String> defaultMessageErrors = new ArrayList<>();
                defaultMessageErrors.add(fieldError.getDefaultMessage());
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setFieldErrors(defaultMessageErrors);
                errorResponse.setField(property.value());
                mapErrors.put(property.value(),errorResponse);
            }
            errorsReponse.add(mapErrors.get(property.value()));
        }));
        body.put("errors", errorsReponse);
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> processViolationException(ConstraintViolationException e){
        log.error("An constraint violation error ocurred:{}",e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, ErrorResponse> mapErrors = new HashMap<>();
        List<ErrorResponse> errorsReponse = new ArrayList<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String nomeCampoCompleto = constraintViolation.getPropertyPath().toString();
            Class clazz = constraintViolation.getRootBeanClass();
            String nomeCampo = nomeCampoCompleto.split(".")[1];
            if(mapErrors.containsKey(nomeCampo)) {
                ErrorResponse errorResponse = mapErrors.get(nomeCampo);
                List<String> defaultMessageErrors = errorResponse.getFieldErrors();
                defaultMessageErrors.add(constraintViolation.getMessageTemplate());
                errorResponse.setFieldErrors(defaultMessageErrors);
                mapErrors.put(constraintViolation.getPropertyPath().toString(),errorResponse);
            }else {
                List<String> defaultMessageErrors = new ArrayList<>();
                defaultMessageErrors.add(constraintViolation.getMessageTemplate());
                ErrorResponse errorResponse =  new ErrorResponse();
                errorResponse.setFieldErrors(defaultMessageErrors);
                errorResponse.setField(nomeCampo);
                mapErrors.put(nomeCampo,errorResponse);
            }
            errorsReponse.add(mapErrors.get(nomeCampo));
        });
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", errorsReponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> processViolationException(NoHandlerFoundException e){
        log.error("An no found error ocurred:{}",e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> processViolationException(DataNotFoundException e){
        log.error("Ocurred DataNotFoundException error:{}",e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("message", Constants.MESSAGE_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

}
