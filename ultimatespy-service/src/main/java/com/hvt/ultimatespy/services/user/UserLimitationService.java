package com.hvt.ultimatespy.services.user;

import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.models.user.User;
import com.hvt.ultimatespy.utils.Errors;
import com.hvt.ultimatespy.utils.enums.ActionEnum;
import com.hvt.ultimatespy.utils.enums.PlanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserLimitationService {

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserService userService;

    public void checkLimitation(String userId, String action, int hours) throws Exception {
        if (userId == null || userId.isEmpty()) return;
        Integer done = userLogService.total(userId, action, hours).get();
        User user = userService.get(userId).get();
        boolean bool = false;
        if (user.getPlan().equals(PlanEnum.FREE.value())) {
            if (action.equals(ActionEnum.SEARCH.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.free.queries"))) {
                bool = true;
            } else if (action.equals(ActionEnum.SAVE_POST.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.free.save"))) {
                bool = true;
            } else if (action.equals(ActionEnum.TRACK_POST.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.free.track"))) {
                bool = true;
            }
        } else if (user.getPlan().equals(PlanEnum.BASIC.value())) {
            if (action.equals(ActionEnum.SEARCH.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.basic.queries"))) {
                bool = true;
            } else if (action.equals(ActionEnum.SAVE_POST.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.basic.save"))) {
                bool = true;
            } else if (action.equals(ActionEnum.TRACK_POST.value()) && done < Integer.parseInt(Config.prop.getProperty("limitation.basic.track"))) {
                bool = true;
            }
        } else if (user.getPlan().equals(PlanEnum.PREMIUM.value())) {
            bool = true;
        }

        if (!bool) {
            throw Errors.DAILY_LIMITATION_EXCEPTION;
        }
    }

}
