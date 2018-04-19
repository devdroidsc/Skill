package sc.ps.ilksl.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import sc.ps.ilksl.Model.ModelImage;
import sc.ps.ilksl.R;

public class AdapterImageGallery extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<ModelImage> list;
    private String url;
    private onClickImageProfile onClickImageProfile;

    public AdapterImageGallery(Context context, ArrayList<ModelImage> list, String url) {
        this.context = context;
        this.list = list;
        this.url = url;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_image_profile,parent,false);
        return new ItemView(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ModelImage modelImage = list.get(position);
        final ItemView itemView = (ItemView) holder;
        itemView.imSetImageProfile.setVisibility(View.GONE);
        itemView.imDeleteImage.setVisibility(View.GONE);
        final boolean[] setOnClick = {true};
        itemView.imDeleteImage.setBackgroundTintList(context.getResources().getColorStateList(R.color.color_red));
        itemView.imSetImageProfile.setBackgroundTintList(context.getResources().getColorStateList(R.color.color_white));



        if (modelImage.isOnSelece()){
            itemView.imSetImageProfile.setVisibility(View.VISIBLE);
            itemView.imDeleteImage.setVisibility(View.VISIBLE);
        }else {
            itemView.imSetImageProfile.setVisibility(View.GONE);
            itemView.imDeleteImage.setVisibility(View.GONE);
        }

        if (modelImage.getNameProfile().equals(modelImage.getName())){
            itemView.imSetImageProfile.setVisibility(View.VISIBLE);
            itemView.imDeleteImage.setVisibility(View.GONE);
            itemView.imSetImageProfile.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
        }

        itemView.imGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelImage.getNameProfile().equals(modelImage.getName())){

                    Log.e("AdapterImageGallery ", "Image Profile");

                }else {
                    if (modelImage.isOnSelece()){
                        onClickImageProfile.onClick(position,false);
                    }else {
                        onClickImageProfile.onClick(position,true);

                    }

                }

            }
        });

        Glide.with(context)
                .load(url +"img/profile/"+ modelImage.getName())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        final Bitmap[] selectedImage = {resource};
                        selectedImage[0] = getResizedBitmap(selectedImage[0], 400);//
                        itemView.imGallery.setImageBitmap(selectedImage[0]);
                        //saveImage(resource);
                    }
                });
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {

        private ImageView imGallery;
        private ImageView imSetImageProfile,imDeleteImage;
        public ItemView(View itemView) {
            super(itemView);

            imGallery = itemView.findViewById(R.id.imGallery);
            imSetImageProfile = itemView.findViewById(R.id.imSetImageProfile);
            imDeleteImage = itemView.findViewById(R.id.imDeleteImage);
        }
    }

    public void setOnClickImageProfile(onClickImageProfile onClickImageProfile){
        this.onClickImageProfile = onClickImageProfile;
    }
    public interface onClickImageProfile{
        public void onClick(int position,boolean check);
    }
}
