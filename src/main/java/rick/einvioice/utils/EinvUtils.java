package rick.einvioice.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class EinvUtils {
    private static final Logger LOGGER = LogManager.getLogger(EinvUtils.class);

    public EinvUtils() {
    }

    public String send(Map<String,Object> params,String url) {

        String json = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("CONTENT-TYPE", "application/x-www-form-urlencoded");
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(list, StandardCharsets.UTF_8.name()));
            StringBuilder sb = new StringBuilder();
            list.forEach(x -> sb.append(x.getName()).append("=").append(x.getValue()).append("&"));
            LOGGER.debug(sb.deleteCharAt(sb.length() - 1));
            try (CloseableHttpResponse response = client.execute(httpPost)) {
                json = EntityUtils.toString(response.getEntity());
                LOGGER.debug(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Map<String,Object> getDetailsPairs(String api_id,String cardNo, String cardEncrypt, String invDate, String invNum){
        long currentTime = System.currentTimeMillis() / 1000L + 10;
        long endTime = currentTime + 3000;
        Map<String, Object> params = new HashMap<>();
        params.put("version", EinvDetail.載具發票查詢VER);
        params.put("cardType", EinvDetail.卡別_手機條碼);
        params.put("cardNo", cardNo);
        params.put("action", EinvDetail.TYPE);
        params.put("timeStamp", String.valueOf(currentTime));
        params.put("expTimeStamp", String.valueOf(endTime));
        params.put("invNum", invNum);
        params.put("invDate", invDate);
        params.put("uuid", UUID.randomUUID().toString());
        params.put("appID", api_id);
        params.put("cardEncrypt", cardEncrypt);
        return params;
    }

    public Map<String,Object> getTitlePairs(String api_id,String cardNo, String cardEncrypt, String startDate, String endDate){
        long currentTime = System.currentTimeMillis() / 1000L + 10;
        long endTime = currentTime + 3000;
        Map<String, Object> params = new HashMap<>();
        params.put("version", EinvTitle.載具發票查詢VER);
        params.put("cardType", EinvTitle.卡別_手機條碼);
        params.put("cardNo", cardNo);
        params.put("action", EinvTitle.TYPE);
        params.put("timeStamp", String.valueOf(currentTime));
        params.put("expTimeStamp", String.valueOf(endTime));
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("onlyWinningInv", "N");
        params.put("uuid", UUID.randomUUID().toString());
        params.put("appID", api_id);
        params.put("cardEncrypt", cardEncrypt);
        //StringBuilder sb = new StringBuilder();
        //params.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(x -> {
        //    sb.append(x.getKey()).append("=").append(x.getValue()).append("&");
        //});
        //return sb.deleteCharAt(sb.length() - 1).toString();
        //params.put(new BasicNameValuePair("signature", signature(params)));
        return params;
    }

    /*
    private String signature(List<NameValuePair> params) {
        //params.sort(Comparator.comparing(NameValuePair::getName));
        StringBuilder sb = new StringBuilder();
        params.forEach(x -> {
            sb.append(x.getName()).append("=").append(x.getValue()).append("&");
        });
        String pp = sb.deleteCharAt(sb.length() - 1).toString();
        return hmacSHA256(pp, API_KEY);
    }*/

    private String hmacSHA256(String message, String key) {
        String HMAC_SHA256 = "HmacSHA256";
        Mac sha256Hmac;
        String result = null;

        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha256Hmac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
            sha256Hmac.init(keySpec);
            byte[] macData = sha256Hmac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
            result = Base64.getEncoder().encodeToString(macData);
            //result = bytesToHex(macData);
        } catch (InvalidKeyException e) {
            //logger.error("InvalidKeyException: ", e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //logger.error("NoSuchAlgorithmException: ", e);
            e.printStackTrace();
        } finally {
            // Put any cleanup here
        }
        return result;
    }
}
