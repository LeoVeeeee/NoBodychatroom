package hk.edu.cuhk.ie.iems5722.a2_1155172339;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CRListAdapter extends BaseAdapter {
    private Context context;
    private List<ChatroomBean> list;
    public CRListAdapter(List<ChatroomBean> list, Context context){
        this.list = list;
        this.context = context;
    }

    public void setList(List<ChatroomBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final myView myview;
        if(view == null){
            myview = new myView();
            view = LayoutInflater.from(context).inflate(R.layout.button_chatroom, null);
            myview.chatroomname = (Button) view.findViewById(R.id.StartButton);
            view.setTag(myview);
        }else{
            myview = (myView) view.getTag();
        }
        ChatroomBean chatroomBean = list.get(i);
        String chatroomname = chatroomBean.getRoomName();
        int id = chatroomBean.getId();
        myview.chatroomname.setText(chatroomname);
        myview.chatroomname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", chatroomname);
                bundle.putInt("id", id);
                bundle.putInt("page",1);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class myView{
        Button chatroomname;
    }
}
