package hk.edu.cuhk.ie.iems5722.a2_1155172339;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatroomListService {
    public static String getHtml(String path) throws Exception{
        InputStream inputStream = null;

        try{
            Log.i("path", path);
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.connect();
            //get data from html
            inputStream = connection.getInputStream();
            //convert the InputStream into a string
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        }finally {
            if (inputStream != null){
                inputStream.close();
            }

        }
    }
}
