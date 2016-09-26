/*
 * Copyright 2016 CREATE-NET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.createnet.raptor.auth.service.controller;

import java.util.stream.Collectors;
import org.createnet.raptor.auth.entity.AuthorizationRequest;
import org.createnet.raptor.auth.entity.AuthorizationResponse;
import org.createnet.raptor.auth.entity.SyncRequest;
import org.createnet.raptor.auth.service.RaptorUserDetailsService;
import org.createnet.raptor.auth.service.acl.RaptorPermission;
import org.createnet.raptor.auth.service.entity.Device;
import org.createnet.raptor.auth.service.entity.User;
import org.createnet.raptor.auth.service.services.AclDeviceService;
import org.createnet.raptor.auth.service.services.DeviceService;
import org.createnet.raptor.auth.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Luca Capra <lcapra@create-net.org>
 */
@RestController
@PreAuthorize("isAuthenticated()")
public class DeviceController {

  private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

  @Autowired
  private DeviceService deviceService;

  @Autowired
  private UserService userService;

  @Autowired
  private AclDeviceService aclDeviceService;

  @RequestMapping(value = "/check", method = RequestMethod.POST)
  public ResponseEntity<?> checkPermission(
          @AuthenticationPrincipal RaptorUserDetailsService.RaptorUserDetails currentUser,
          @RequestBody AuthorizationRequest body
  ) {

    AuthorizationResponse response = new AuthorizationResponse();

    switch (body.getOperation()) {
      case User:

        response.result = true;
        response.userId = currentUser.getUuid();
        response.roles = currentUser.getRoles()
                .stream()
                .map((r) -> r.getName())
                .collect(Collectors.toList());

        break;
      case Permission:

        logger.debug("Check if user {} can `{}` on object {}", body.userId, body.permission, body.objectId);
        
        
        User user = (User) currentUser;
        if(body.userId != null) {
          user = userService.getByUuid(body.userId);
        }
        
        
        if(user == null) return ResponseEntity.notFound().build();

        Device device = deviceService.getByUuid(body.objectId);
        if(device == null) return ResponseEntity.notFound().build();

        Permission permission = RaptorPermission.getByName(body.permission.toLowerCase());
        if(permission == null) return ResponseEntity.notFound().build();
        
        response.result = aclDeviceService.check(device, user, permission);
        
        break;
      default:

        response.result = false;
        break;
    }

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @RequestMapping(value = "/sync", method = RequestMethod.POST)
  public ResponseEntity<?> syncObject(
          @RequestBody SyncRequest body
  ) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    deviceService.sync(auth, body);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
  }

}