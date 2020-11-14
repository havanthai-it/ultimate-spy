package com.hvt.ultimatespy.controllers.user;

import com.hvt.ultimatespy.models.BaseList;
import com.hvt.ultimatespy.models.post.FacebookPost;
import com.hvt.ultimatespy.services.user.UserPostService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Constants.ROUTE_USER_ID_POST)
public class UserPostListController {

    @Autowired
    private UserPostService userPostService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<String>> get(@PathVariable String id, @RequestParam String type) throws Exception {
        if (id == null || id.trim().isEmpty()
            || type == null || type.trim().isEmpty()) {
            throw Errors.BAD_REQUEST_EXCEPTION;
        }

        List<String> list = new ArrayList<>();

        if (type.toLowerCase().equals("saved")) {
            list = userPostService.listIds(id, "saved").get();
        } else if (type.toLowerCase().equals("tracked")) {
            list = userPostService.listIds(id, "tracked").get();
        }

        return ResponseEntity.ok(list);
    }



}
