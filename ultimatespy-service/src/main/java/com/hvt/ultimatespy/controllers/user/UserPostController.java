package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.FuncUtils;
import com.hvt.ultimatespy.utils.enums.RoleEnum;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.services.user.UserService;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_USER)
public class UserPostController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> post(@RequestBody User user) throws Exception {
        if (user.getFullName() == null || user.getFullName().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setRole(RoleEnum.FREE.getRole());

        // Password was base64 encoded on the client side before send to server
        // Server continue encrypt with salt, then store on database
        String decodedPassword = new String(Base64.getDecoder().decode(user.getPassword().getBytes()));
        String salt = FuncUtils.randomString(8, true, true, true);
        String encryptedPassword = UserUtils.encryptPassword(decodedPassword, salt);
        user.setPassword(encryptedPassword);

        User result = userService.insert(user).get();
        return ResponseEntity.ok(result);
    }

}
