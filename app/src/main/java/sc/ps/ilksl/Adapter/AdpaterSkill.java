package sc.ps.ilksl.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import sc.ps.ilksl.Model.ModelSear;
import sc.ps.ilksl.Model.ModelSkill;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 06-03-2018.
 */

public class AdpaterSkill extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable{

    private ArrayList<ModelSkill> list;
    private Context context;
    private ArrayList<ModelSkill> mFilteredList;
    private onClickSelectSkill onClickSelectSkill;

    public AdpaterSkill(ArrayList<ModelSkill> list, Context context) {
        this.list = list;
        this.mFilteredList = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dialog_skill,parent,false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ModelSkill modelSkill = mFilteredList.get(position);
        final ItemView itemView = (ItemView) holder;
        itemView.tvNameSkill.setText(modelSkill.getName());
        //itemView.tvImStatus.setVisibility(View.GONE);
        Glide.with(context)
                .load(modelSkill.getPic())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        itemView.imPicSkill.setImageBitmap(resource);
                    }
                });
        //Log.e("AdpaterSkill", modelSkill.getStatus());
        if (modelSkill.getStatus().equals("1")){
            itemView.tvImStatus.setVisibility(View.VISIBLE);
        }
        if (modelSkill.getStatus().equals("0")){
            itemView.tvImStatus.setVisibility(View.GONE);
        }

        itemView.lnMenuSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickSelectSkill.onClickSlSkill(position,modelSkill.getId());

                if (itemView.tvImStatus.getVisibility()==View.VISIBLE){

                    itemView.tvImStatus.setVisibility(View.GONE);
                }else {
                    itemView.tvImStatus.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = list;
                } else {

                    ArrayList<ModelSkill> filteredList = new ArrayList<>();

                    for (ModelSkill androidVersion : list) {

                        if (androidVersion.getName().toLowerCase().contains(charString)){

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                mFilteredList = (ArrayList<ModelSkill>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemView extends RecyclerView.ViewHolder{

        private ImageView imPicSkill;
        private TextView tvNameSkill;
        private TextView tvImStatus;
        private LinearLayout lnMenuSkill;

        public ItemView(View itemView) {
            super(itemView);

            imPicSkill = itemView.findViewById(R.id.imPicSkill);
            tvNameSkill = itemView.findViewById(R.id.tvNameSkill);
            tvImStatus = itemView.findViewById(R.id.tvImStatus);
            lnMenuSkill = itemView.findViewById(R.id.lnMenuSkill);
        }
    }

    public void setOnClickSelectSkill(onClickSelectSkill onClickSelectSkill){
        this.onClickSelectSkill = onClickSelectSkill;
    }
    public interface onClickSelectSkill{
        public void onClickSlSkill(int position,String Tag);
    }
}
