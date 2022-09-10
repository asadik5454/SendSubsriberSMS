package org.example;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Methods {

    public static void send_sms(String subs_key, String text_sms) throws Exception {
        JSONObject jo = new JSONObject();
        try {
            System.out.println("Start sms");
            jo.put("msisdn", subs_key);
            jo.put("source_addr","ZET-MOBILE");//"ZET-MOBILE"
            jo.put("text", text_sms);

            String ip_address = "http://172.28.193.139:8070/data";
            byte[] data = jo.toString().getBytes( StandardCharsets.UTF_8 );
            int dataLength = jo.toString().length();

            URL obj = new URL(ip_address);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput( true );
            con.setInstanceFollowRedirects( false );
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla5/0");
            con.setRequestProperty("Accept-Charset", "");
            con.setRequestProperty("Content-Length", Integer.toString(dataLength));
            try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
                wr.write( data );
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            System.out.println("response = " + response);

        } catch (Exception e) {
        }
    }





}