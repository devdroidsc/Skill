package sc.ps.ilksl.Adapter;

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
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 06-03-2018.
 */

public class AdpaterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable{

    private ArrayList<ModelSear> list;
    private Context context;
    private ArrayList<ModelSear> mFilteredList;
    private onClickSearch onClickSearch;

    public AdpaterSearch(ArrayList<ModelSear> list, Context context) {
        this.list = list;
        this.mFilteredList = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_page_search,parent,false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ModelSear modelSear = mFilteredList.get(position);
        final ItemView itemView = (ItemView) holder;
        itemView.tvName.setText(modelSear.getName());
        itemView.lnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearch.onClickSearch(position,modelSear.getName(),modelSear.getId());
            }
        });

        Glide.with(context)
                .load(modelSear.getPic())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        itemView.imSearBG.setImageBitmap(resource);
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

                    ArrayList<ModelSear> filteredList = new ArrayList<>();

                    for (ModelSear androidVersion : list) {

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
                mFilteredList = (ArrayList<ModelSear>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setOnClickSearch(onClickSearch onClickSearchs){
        this.onClickSearch = onClickSearchs;
    }
    public interface onClickSearch{
        public void onClickSearch(int position,String tage,String Id);
    }
    public class ItemView extends RecyclerView.ViewHolder{

        private TextView tvName;
        private ImageView imSear,imSearBG;
        private RelativeLayout rltest;
        private LinearLayout lnMenu1;
        public ItemView(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            rltest = itemView.findViewById(R.id.rltest);
            imSearBG = itemView.findViewById(R.id.imSearBG);
            lnMenu1 = itemView.findViewById(R.id.lnMenu1);

        }
    }
}
