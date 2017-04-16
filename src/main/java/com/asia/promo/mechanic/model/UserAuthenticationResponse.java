package com.asia.promo.mechanic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationResponse {
	private boolean authenticated;
	private String userId;
}
