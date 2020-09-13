package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.FuncUtils;
import com.hvt.ultimatespy.utils.enums.StatusEnum;
import com.hvt.ultimatespy.utils.mail.MailUtils;
import com.hvt.ultimatespy.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_ID)
public class UserConfirmPutController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable("id") String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        User user = userService.get(id).get();
        if (user != null) {
            if (user.getStatus().equals(StatusEnum.CREATED.value())) {
                // Update user status
                userService.updateStatus(id, StatusEnum.ACTIVE.value());
            }
        } else {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        return ResponseEntity.ok(null);
    }

}
