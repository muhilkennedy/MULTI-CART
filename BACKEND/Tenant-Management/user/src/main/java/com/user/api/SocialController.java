package com.user.api;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.ConfigType;
import com.base.service.ConfigurationService;
import com.base.util.Log;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.platform.messages.ConfigurationType;
import com.platform.messages.GenericResponse;
import com.platform.messages.GoogleOauthConstants;
import com.platform.messages.Response;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformOauthUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.User;
import com.user.exception.UserNotFoundException;
import com.user.messages.SocialUser;
import com.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("social")
public class SocialController {
	
	@Autowired
	private ConfigurationService configService;
	
	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;

	@PostMapping(value = "/login/google")
	public GenericResponse<User> loginGoogleUser(@RequestBody SocialUser body, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException, GeneralSecurityException, UserNotFoundException {
		GenericResponse<User> response = new GenericResponse<User>();
		// validate google token here
		ConfigType clientIdConfig = configService.getConfigIfPresent(GoogleOauthConstants.CLIENTID.name(),
				ConfigurationType.OAUTH);
		Assert.notNull(clientIdConfig, "Invalid Oath Configuration");
		Payload payload = PlatformOauthUtil.verifyAndParseGoogleToken(clientIdConfig.getVal(), body.getIdToken());
		Log.base.debug("Google token information : {}", payload);
		User user = empService.findBySecondaryEmailId((String) payload.get("email"));
		if (user == null) {
			throw new UserNotFoundException(
					"User Not Found. Please make sure to update your secondary mail for google login!");
		}
		httpResponse.addHeader(PlatformUtil.TOKEN_HEADER, JWTUtil.generateToken(user.getUniquename(),
				String.valueOf(user.getObjectId()), JWTUtil.USER_TYPE_EMPLOYEE, httpRequest.getRemoteAddr(), false));
		return response.setStatus(Response.Status.OK).setData(user).build();
	}
	
}
