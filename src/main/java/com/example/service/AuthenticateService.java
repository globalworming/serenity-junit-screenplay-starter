package com.example.service;

import com.example.model.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AuthenticateService {

  private static final AuthenticateService instance = new AuthenticateService();
  private final Map<String, List<Role>> accessTokenToRoles = new HashMap<>();

  {
    accessTokenToRoles.put("adminAccessToken", singletonList(Role.ADMIN));
    accessTokenToRoles.put("playerAccessToken", singletonList(Role.PLAYER));
  }

  public static AuthenticateService getInstance() {
    return instance;
  }

  public List<Role> getCurrentRoles(String accessToken) {
    return accessTokenToRoles.getOrDefault(accessToken, emptyList());
  }
}
