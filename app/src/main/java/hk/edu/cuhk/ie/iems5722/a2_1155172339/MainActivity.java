package hk.edu.cuhk.ie.iems5722.a2_1155172339;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a2_1155172339.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<ChatroomBean> list = new ArrayList<>();
    private List<ChatroomBean> chatroomBeans = new ArrayList<>();
    private ChatroomBean chatroomBean;
    private Button button = null;
    private CRListAdapter crListAdapter;
    private String html;
    private JSONObject json;
    private JSONArray jsonArray;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    for (ChatroomBean item : chatroomBeans) {
                        list.add(item);
                        crListAdapter.setList(list);
                        crListAdapter.notifyDataSetChanged();
                        listView.setSelection(list.size() - 1);
                    }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());



        listView = (ListView) findViewById(R.id.lv_chatroomlist);

        crListAdapter = new CRListAdapter(list, this);
        listView.setAdapter(crListAdapter);
        button = (Button) findViewById(R.id.StartButton);

        //new a thread to get chatroom names from html
        new Thread() {
            String url = getResources().getString(R.string.URL) + "get_chatrooms";
            @Override
            public void run() {
                try{
                    html = ChatroomListService.getHtml(url);
                    json = new JSONObject(html);
                    String status = json.getString("status");
                    if (status.equals("OK")){
                        jsonArray = json.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            chatroomBean = new ChatroomBean();
                            String name = jsonArray.getJSONObject(i).getString("name");
                            chatroomBean.setRoomName(name);
                            int id = jsonArray.getJSONObject(i).getInt("id");
                            chatroomBean.setId(id);
                            chatroomBeans.add(chatroomBean);
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }else{
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "网络连接错误!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }.start();

    }

    public boolean isGPSAvailable(Activity activity){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS){
            if(googleApiAvailability.isUserResolvableError(status)){
                googleApiAvailability.getErrorDialog(activity, status, 9000).show();
            }
            return false;
        }
        return true;
    }


}