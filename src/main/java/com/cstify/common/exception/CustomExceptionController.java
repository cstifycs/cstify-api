package com.cstify.common.exception;

import com.cstify.common.response.ResUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionController {

	/* ======================
	 * 1. Validation 관련 예외
	 * ====================== */

	// @Valid DTO binding validation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		List<Map<String, String>> errors = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> {
                    assert fieldError.getDefaultMessage() != null;
                    return Map.of(
                            "errorField", fieldError.getField(),
                            "errorMsg", fieldError.getDefaultMessage()
                    );
                })
				.toList();

		log.debug("Validation errors: {}", errors);
		return ResUtils.data(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR", errors);
	}

	// @Validated + @RequestParam, @PathVariable 실패
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<Object> handleHandlerMethodValidation(HandlerMethodValidationException e) {
		log.error("HandlerMethodValidationException: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR");
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
		log.error("Missing parameter: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.BAD_REQUEST.value(), "MISSING_PARAMETER");
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
		log.error("Missing parameter: {}", e.getMessage(), e);
		String message = e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage() : "MISSING_PARAMETER";
		return ResUtils.simple(HttpStatus.BAD_REQUEST.value(), message);
	}

	/* ======================
	 * 2. Authentication / Authorization
	 * ====================== */
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<Object> handleTokenExpired(TokenExpiredException e) {
		log.error("Access token expired: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 801,"ACCESS_TOKEN_EXPIRED");
	}

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<Object> handleTokenInvalid(TokenInvalidException e) {
		log.error("Invalid access token: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 802,"ACCESS_TOKEN_INVALID");
	}

	@ExceptionHandler(TokenBlacklistException.class)
	public ResponseEntity<Object> handleTokenBlacklisted(TokenBlacklistException e) {
		log.error("Blacklisted token: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 803,"TOKEN_BLACKLISTED");
	}

	@ExceptionHandler(RefreshTokenExpiredException.class)
	public ResponseEntity<Object> handleRefreshTokenExpired(RefreshTokenExpiredException e) {
		log.error("Refresh token expired: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 804,"REFRESH_TOKEN_EXPIRED");
	}

	@ExceptionHandler(CookieExpiredException.class)
	public ResponseEntity<Object> handleCookieExpired(CookieExpiredException e) {
		log.error("Cookie expired: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 805,"COOKIE_EXPIRED");
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e) {
		log.error("Bad credentials: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 806,"BAD_CREDENTIALS");
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException e) {
		log.error("Username not found: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 807,"USERNAME_NOT_FOUND");
	}

	@ExceptionHandler(NotLoginException.class)
	public ResponseEntity<Object> handleNotLogin(NotLoginException e) {
		log.error("Not logged in: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.UNAUTHORIZED.value(), 808,"UNAUTHORIZED");
	}

	@ExceptionHandler(PermissionException.class)
	public ResponseEntity<Object> handlePermissionDenied(PermissionException e) {
		log.error("Permission denied: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.FORBIDDEN.value(), "FORBIDDEN");
	}

	/* ======================
	 * 3. Database Exceptions
	 * ====================== */
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Object> handleDataAccess(DataAccessException e) {
		log.error("Data access error: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(), "DB_ERROR");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException e) {
		log.error("Duplicate key: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.CONFLICT.value(), "DUPLICATE_KEY");
	}

	/* ======================
	 * 4. Business / Custom Exceptions
	 * ====================== */

	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<Object> handleDuplicateException(DuplicateException e) {
		log.error("Duplicate: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.CONFLICT.value(), e.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFound(NotFoundException e) {
		log.error("Not found: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<Object> handleNoContent(NoContentException e) {
		log.error("No content: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.NO_CONTENT.value(), "NO_CONTENT");
	}

	@ExceptionHandler(UploadFailException.class)
	public ResponseEntity<Object> handleUploadFail(UploadFailException e) {
		log.error("Upload fail: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(), 820,"UPLOAD_FAIL");
	}

	@ExceptionHandler(DownloadFailException.class)
	public ResponseEntity<Object> handleDownloadFail(DownloadFailException e) {
		log.error("Upload fail: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(), 830,"DOWNLOAD_FAIL");
	}

	@ExceptionHandler(BizException.class)
	public ResponseEntity<Object> handleBizException(BizException e) {
		log.error("Business error: {}", e.getMessage(), e);

		String[] message = e.getMessage().split("\\|");
		int code = (message.length > 1) ? Integer.parseInt(message[0]) : 500;
		String msg = (message.length > 1) ? message[1] : message[0];

		return ResUtils.simple(code, msg);
	}


	/* ======================
	 * 5. 404 / Not Found Handler
	 * ====================== */

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException e) {
		log.error("No handler found: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.NOT_FOUND.value(), "NOT_FOUND");
	}


	/* ======================
	 * 6. 최종 예외 (항상 마지막)
	 * ====================== */

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e) {
		log.error("Unhandled exception: {}", e.getMessage(), e);
		return ResUtils.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
	}
}