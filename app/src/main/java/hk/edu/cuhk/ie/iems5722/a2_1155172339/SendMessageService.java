package hk.edu.cuhk.ie.iems5722.a2_1155172339;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SendMessageService {
    public static void SendMessageService(int chatroomid, int userid, String name, String message) {
        boolean result = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://47.254.253.138/api/a3/send_message");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(15000);
                    connection.setConnectTimeout(15000);
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    OutputStream outputstrem = connection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputstrem, "UTF-8"));
                    Uri.Builder builder = new Uri.Builder();

                    builder.appendQueryParameter("chatroom_id", String.valueOf(chatroomid));
                    builder.appendQueryParameter("user_id", String.valueOf(userid));
                    builder.appendQueryParameter("name", name);
                    builder.appendQueryParameter("message", message);

                    String query = builder.build().getEncodedQuery();

                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputstrem.close();

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    line = bufferedReader.readLine();
                    System.out.println(line);

                    // get the response number
//                    int responseCode = connection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        System.out.println("Success");
//                    } else {
//                        System.out.println("Fail");
//                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}

//    public static void showdialog(View view){
//        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//        builder.setTitle("ToT");
//        builder.setMessage("Failed to send message... 0.0");
//        builder.setPositiveButton("You can only click here", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(view.getContext(), "You can only click here", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.show();
//    }
