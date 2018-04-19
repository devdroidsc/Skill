package sc.ps.ilksl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import sc.ps.ilksl.Model.ModelCountry;
import sc.ps.ilksl.Model.ModelMessage;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 10-04-2018.
 */

public class AdapterMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<ModelMessage> list;
    private Context context;
    private String strURL;
    private setOnClick setOnClicks;

    public AdapterMessage(ArrayList<ModelMessage> list, Context context,String strURL) {
        this.list = list;
        this.context = context;
        this.strURL = strURL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_message,parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ModelMessage modelMessage = list.get(position);
        final ItemView itemView = (ItemView) holder;
        itemView.tvMessageTime.setText(modelMessage.getStrTime());

        Glide.with(context)
                .load(strURL +"img/profile/small_"+modelMessage.getStrImg())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        itemView.imImageM.setImageBitmap(resource);
                    }
                });
        itemView.lnListMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClicks.setOnClick(position,modelMessage.getLongH(),modelMessage.getLatH());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {

        private ImageView imImageM;
        private TextView tvAgeMessage;
        private TextView tvMessageTime;
        private LinearLayout lnListMessage;

        public ItemView(View itemView) {
            super(itemView);
            imImageM = itemView.findViewById(R.id.imImageM);
            tvAgeMessage = itemView.findViewById(R.id.tvAgeMessage);
            tvMessageTime = itemView.findViewById(R.id.tvMessageTime);
            lnListMessage = itemView.findViewById(R.id.lnListMessage);
        }
    }

    public void setOnclick(setOnClick setOnClick){
        this.setOnClicks = setOnClick;
    }
    public interface setOnClick{
        public void setOnClick(int position,String lot,String lat);
    }
}
