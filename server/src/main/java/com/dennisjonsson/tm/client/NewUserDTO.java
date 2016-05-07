package com.dennisjonsson.tm.client;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.dennisjonsson.tm.application.TMAppConstants;

public class NewUserDTO {
	
	@NotNull
	@Pattern(regexp = TMAppConstants.ANDROID_APP_KEY)
	public String appKey;
}
