package com.hvt.ultimatespy.services.subscriberEmail;

import com.hvt.ultimatespy.ds.Datasource;
import com.hvt.ultimatespy.models.subscriberEmail.SubscriberEmail;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SubscriberEmailService {

    private static final Logger logger = Logger.getLogger(SubscriberEmailService.class.getName());

    public CompletableFuture<SubscriberEmail> get(String email) {
        return CompletableFuture.supplyAsync(() -> {
            SubscriberEmail subscriberEmail = null;
            Connection conn = null;
            CallableStatement cs = null;
            ResultSet rs = null;
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall("INSERT INTO " +
                        " tb_subscriber_email(S_EMAIL) " +
                        " VALUES(?)");
                cs.setString(1, email);
                rs = cs.executeQuery();
                if (rs != null && rs.next()) {
                    subscriberEmail = new SubscriberEmail();
                    subscriberEmail.setEmail(rs.getString("S_EMAIL"));
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }
            return subscriberEmail;
        });
    }

    public CompletableFuture<Integer> insert(String email) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            Connection conn = null;
            CallableStatement cs = null;
            try {
                conn = Datasource.getConnection();
                cs = conn.prepareCall("INSERT INTO " +
                        " tb_subscriber_email(S_EMAIL) " +
                        " VALUES(?)");
                cs.setString(1, email);
                cs.execute();
                result = 1;
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            } finally {
                Datasource.close(conn, cs, null);
            }
            return result;
        });
    }

}
