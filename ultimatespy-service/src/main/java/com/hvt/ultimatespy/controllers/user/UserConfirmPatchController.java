package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Encryptor;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_CONFIRM_ID)
public class UserConfirmPatchController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> patch(@PathVariable("userId") String userId, @PathVariable("id") String id) throws Exception {
        if (userId == null || userId.trim().isEmpty()
            || id == null || id.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        String decodedUserId = new String(Base64.getDecoder().decode(id));
        if (!userId.equals(decodedUserId)) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = userService.get(userId).get();
        if (user != null) {
            if (user.getStatus().equals(StatusEnum.CREATED.value())) {
                // Update user status
                userService.updateStatus(userId, StatusEnum.ACTIVE.value()).get();
            }
        } else {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        return ResponseEntity.ok(null);
    }

}
