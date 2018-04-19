package sc.ps.ilksl.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import sc.ps.ilksl.Model.ModelCountry;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 26-03-2018.
 */

public class AdapterCountryPro extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<ModelCountry> list;

    public AdapterCountryPro(Context context, ArrayList<ModelCountry> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_country,parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ModelCountry modelCountry = list.get(position);
        final ItemView itemView = (ItemView) holder;
        Glide.with(context)
                .load(modelCountry.getUrlImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        itemView.imCountry.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {

        private ImageView imCountry;

        public ItemView(View itemView) {
            super(itemView);
            imCountry = itemView.findViewById(R.id.imCountry);
        }
    }
}
