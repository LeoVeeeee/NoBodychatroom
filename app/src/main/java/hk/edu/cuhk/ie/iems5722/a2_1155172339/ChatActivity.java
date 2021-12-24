package hk.edu.cuhk.ie.iems5722.a2_1155172339;



import android.app.Activity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends Activity {
    //private ListView listView;//list
    private RecyclerView listView;
    private TextView chatroomTitle;
    private EditText content; //content
    private ImageButton send;      //  send button
    private Button back;
    private Button refresh;
    private String html;
    private int id;
    private int userid;
    private String sendContent;
    private String username = "Homelander";
    //private Boolean sendMsgResult;
    private int page;
    private List<JavaBean> list = new ArrayList<JavaBean>();
    private List<ChatroomBean> chatroomBeanList = new ArrayList<>();
    //private ChatAdapter adapter;
    private ChatRecycleAdapter chatRecycleAdapter;
    private JSONObject json;
    private JSONObject data;
    private JSONArray messagesArray;
    private List<JavaBean> getMessageList = new ArrayList<>();
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private static final int Service_GetSendMessage = 1;
    private static final int Service_UpdateMsg = 2;
    private static final int Service_UpdateUI = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
//                case Service_GetSendMessage:
//                    sendMsgResult = msg.getData().getBoolean("result");
//                    break;
                case Service_UpdateMsg:
                    //Collections.reverse(list);
                    sortData(list);
                    chatRecycleAdapter.setList(list);
                    chatRecycleAdapter.notifyDataSetChanged();
                    //listView.smoothScrollToPosition(list.size());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.chat_layout);
        super.onCreate(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String chatroomname = bundle.getString("name");
        id = bundle.getInt("id");
        page = bundle.getInt("page");

        listView = (RecyclerView) findViewById(R.id.rv_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        chatroomTitle = (TextView) findViewById(R.id.text_chatroomTitle);
        chatroomTitle.setText(chatroomname);
        //adapter = new ChatAdapter(list, this);
        chatRecycleAdapter = new ChatRecycleAdapter(list);
        //listView.setAdapter(adapter);
        listView.setAdapter(chatRecycleAdapter);
        content = (EditText) findViewById(R.id.content);
        send = (ImageButton) findViewById(R.id.send);
        back = findViewById(R.id.back);
        refresh = findViewById(R.id.bt_refresh);
        getMessage(id,page);
        listView.smoothScrollToPosition(list.size());
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JavaBean javaBean = new JavaBean();
                userid = 1155172339;
                sendContent = content.getText().toString();
                if(sendContent.equals(""))
                    return;
                SendMessageService.SendMessageService(id, userid, username, sendContent);
                javaBean.setContent(sendContent);
                javaBean.setName("User" + username);
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH: mm");
                String dateStr = dateFormat.format(System.currentTimeMillis());
                javaBean.setTime(dateStr);

                javaBean.setType(TYPE_SENT);
                javaBean.setId(id);
                list.add(javaBean);
                sortData(list);
                content.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = Service_UpdateMsg;
                        handler.sendMessage(message);
                    }
                }).start();
                listView.smoothScrollToPosition(list.size());
//                chatRecycleAdapter.setList(list);
//                chatRecycleAdapter.notifyDataSetChanged();
//                listView.smoothScrollToPosition(list.size());
//                content.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                list.clear();
                getMessage(id,1);
            }
        });


        listView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                if(page <= 5 && !recyclerView.canScrollVertically(-1)){
//                    page ++;
//                    getMessage(id, page);
//                }
            }
        });


    }

    public void getMessage(int roomID, int temp_page){
        new Thread(new Runnable() {
            int chatroomID = roomID;
            String url = getResources().getString(R.string.URL) + "get_messages?chatroom_id="
                    + chatroomID + "&page=" + temp_page;
            @Override
            public void run() {
                try{
                    html = ChatroomListService.getHtml(url);
                    json = new JSONObject(html);
                    String status = json.getString("status");
                    if(status.equals("OK")){
                        data = json.getJSONObject("data");
                        messagesArray = data.getJSONArray("messages");
                        for(int i = 0; i < messagesArray.length(); i++){
                            String name = messagesArray.getJSONObject(i).getString("name");
                            String message = messagesArray.getJSONObject(i).getString("message");
                            String message_time = messagesArray.getJSONObject(i).getString("message_time");
                            JavaBean getMessageBean = new JavaBean();
                            getMessageBean.setContent(message);
                            //SimpleDateFormat getdateFormat_long = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                            SimpleDateFormat getdateFormat_long = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                            SimpleDateFormat getdateFormat_short = new SimpleDateFormat("hh:mm");
                            Date date = getdateFormat_long.parse(message_time);
                            String time = getdateFormat_short.format(date);
                            getMessageBean.setName("User:" + name);
                            getMessageBean.setTime(time);
                            if(name.equals(username))
                                getMessageBean.setType(TYPE_SENT);
                            else
                                getMessageBean.setType(TYPE_RECEIVED);
                            getMessageBean.setId(roomID);
                            //getMessageList.add(getMessageBean);
                            list.add(getMessageBean);
                        }
//                        adapter.setList(getMessageList);
//                        adapter.notifyDataSetChanged();
//                        listView.setSelection(getMessageList.size() - 1);
                        Message message = new Message();
                        message.what = Service_UpdateMsg;
                        handler.sendMessage(message);
                        //listView.smoothScrollToPosition(list.size());
                        //content.setText("");

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.toString());
                    Looper.prepare();
                    Looper.loop();
                }
            }
        }).start();
    }

    public void sortData(List<JavaBean> messageList){
        Collections.sort(messageList, new Comparator<JavaBean>() {
            @Override
            public int compare(JavaBean t0, JavaBean t1) {
                int result = t0.getTime().compareTo(t1.getTime());
                if(result > 0){
                    return 1;
                }else if(result == 0)
                    return 0;
                return -1;
            }
        });
    }


}
