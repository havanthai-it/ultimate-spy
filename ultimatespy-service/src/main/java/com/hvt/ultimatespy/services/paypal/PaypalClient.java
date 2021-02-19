package com.hvt.ultimatespy.services.paypal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hvt.ultimatespy.config.Config;
import com.hvt.ultimatespy.ds.Datasource;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaypalClient {

    private static final Logger logger = Logger.getLogger(PaypalClient.class.getName());

    public CompletableFuture<String> getAccessToken() {
        return CompletableFuture.supplyAsync(() -> {
            String accessToken = null;
            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(Config.prop.getProperty("paypal.api.baseUrl") + "/v1/oauth2/token");

                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                        Config.prop.getProperty("paypal.api.publicKey"),
                        Config.prop.getProperty("paypal.api.secretKey"));
                httpPost.addHeader(new BasicScheme().authenticate(credentials, httpPost, null));

                String body = "grant_type=client_credentials";

                StringEntity entity = new StringEntity(body);

                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Accept-Language", "en_US");
                httpPost.setHeader("Content-type", "x-www-form-urlencoded");

                CloseableHttpResponse response = client.execute(httpPost);
                String strResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
                JsonObject json = new Gson().fromJson(strResponse, JsonObject.class);
                accessToken = json.get("access_token").getAsString();
                client.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
            return accessToken;
        });
    }

    public CompletableFuture<Integer> cancelSubscription(String accessToken, String subscriptionId, String reason) {
        return CompletableFuture.supplyAsync(() -> {
            int result = 0;
            try {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(Config.prop.getProperty("paypal.api.baseUrl") + "/v1/billing/subscriptions/" + subscriptionId + "/cancel");

                String body = "{\"reason\":\"" + reason + "\"}";

                StringEntity entity = new StringEntity(body);

                httpPost.setEntity(entity);
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + accessToken);

                CloseableHttpResponse response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() < 300) {
                    result = 1;
                }

                client.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
            return result;
        });
    }

}
