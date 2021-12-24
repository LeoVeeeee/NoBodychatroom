package hk.edu.cuhk.ie.iems5722.a2_1155172339;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ChatRecycleAdapter extends RecyclerView.Adapter<ChatRecycleAdapter.MyViewHolder> {
    private List<JavaBean> list;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        ImageView adatars_left, adatars_right;
        TextView chatcontent_left, chattime_left, chatcontent_right, chattime_right;
        TextView user_name_left, user_name_right;

        public MyViewHolder(View itemView){
            super(itemView);
            leftLayout = (RelativeLayout) itemView.findViewById(R.id.leftLayout);
            rightLayout = (RelativeLayout) itemView.findViewById(R.id.rightLayout);
            adatars_left = (ImageView) itemView.findViewById(R.id.iv_chatavatars_left);
            adatars_right = (ImageView) itemView.findViewById(R.id.iv_chatavatars_right);
            chatcontent_left = (TextView) itemView.findViewById(R.id.tv_chatcontent_left);
            chattime_left = (TextView) itemView.findViewById(R.id.tv_chattime_left);
            user_name_left = (TextView) itemView.findViewById(R.id.tv_user_left);
            chatcontent_right = (TextView) itemView.findViewById(R.id.tv_chatcontent_right);
            chattime_right = (TextView) itemView.findViewById(R.id.tv_chattime_right);
            user_name_right = (TextView) itemView.findViewById(R.id.tv_user_right);
        }
    }
    public ChatRecycleAdapter(List<JavaBean> list){
        this.list = list;
    }

    public void setList(List<JavaBean> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialogbox_layout, null);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        JavaBean javaBean = list.get(position);
        if (javaBean.getType() == JavaBean.TYPE_SENT){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.chatcontent_right.setText(javaBean.getContent());
            holder.chattime_right.setText(javaBean.getTime());
            holder.user_name_right.setText(javaBean.getName());
        }else if(javaBean.getType() == JavaBean.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.chattime_left.setText(javaBean.getTime());
            holder.chatcontent_left.setText(javaBean.getContent());
            holder.user_name_left.setText(javaBean.getName());
        }
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
