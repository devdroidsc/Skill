package sc.ps.ilksl.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import sc.ps.ilksl.Adapter.AdapterCountryPro;
import sc.ps.ilksl.Adapter.AdapterImageGallery;
import sc.ps.ilksl.Adapter.AdpaterSkill;
import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.Manager.DataArray;
import sc.ps.ilksl.Manager.RealPathUtil;
import sc.ps.ilksl.Model.ModelCountry;
import sc.ps.ilksl.Model.ModelImage;
import sc.ps.ilksl.Model.ModelSkill;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 22-03-2018.
 */

public class PageProfile extends Fragment{

    public static PageProfile newInstance(){
        PageProfile pageProfile = new PageProfile();
        return pageProfile;
    }

    private TextView tvIDUser,tvSkill,tvPhone,tvRecommend,tvCountry,tvRows,
                        tvName,tvEmail,tvReShowhit,tvSkillShow,tvEditTime,tvTimeSetting,
                        tvEditPass,tvEditRow,tvStatueOn_Off,tvEditProfile;
    private ImageView imProfile;
    private AllCommand allCommand;
    private String strURL;
    private LinearLayout LnRecommendHit;
    private boolean ShowHit;
    private String[] SPINNER_DATA;
    private DataArray dataArray;
    private String text;
    private String[] arrSaveNameFlag;
    private RecyclerView reSkill;
    private LinearLayoutManager horizontalLayoutManager,LayoutManager;
    private AdapterCountryPro adapter;
    private ArrayList<ModelCountry> listSkill;
    private ArrayList<ModelSkill> listSearch;
    private ArrayList<String> listNameSkill;
    private AdpaterSkill adpaterSkill;
    private Switch swOn_offline;
    private String[] listIdSkill;
     //ArrayList<String> listIdSkill;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount,pp;
    static final int LOCATION_REQUEST = 123;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allCommand = new AllCommand();
        dataArray = new DataArray();
        listSkill = new ArrayList<>();
        listSearch = new ArrayList<>();
        listNameSkill = new ArrayList<>();

        strURL = allCommand.GetStringShare(getContext(),allCommand.SHARE_URL,"");
        ShowHit = false;

        SPINNER_DATA = dataArray.GET_NAME_COUNTRY(getActivity(),allCommand.GetStringShare(getContext(),
                allCommand.Language,allCommand.STRKEY_TH));

        arrSaveNameFlag = new String[SPINNER_DATA.length];

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},LOCATION_REQUEST);
            return;
        }
        onPermissionMultiple();
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("MapsActivity", "PERMISSION_GRANTED");
                }
                break;
            case REQUEST_MUTIPLE:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    allowMultipleSuccess();
                } else {
                    allCommand.ShowLogCat("status","Some Permission is Denied");
                }
                break;
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_profile,container,false);
        ItemView(view);
        return view;
    }
    public void ItemView(View view){

        tvIDUser = view.findViewById(R.id.tvIDUser);
        //tvSkill = view.findViewById(R.id.tvSkill);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvRecommend = view.findViewById(R.id.tvRecommend);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvRows = view.findViewById(R.id.tvRows);
        tvName = view.findViewById(R.id.tvName);
        imProfile = view.findViewById(R.id.imProfile);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvEditProfile = view.findViewById(R.id.tvEditProfile);

        //LnRecommendHit = view.findViewById(R.id.LnRecommendHit);
        tvReShowhit = view.findViewById(R.id.tvReShowhit);
        reSkill = view.findViewById(R.id.reSkill);
        tvSkillShow = view.findViewById(R.id.tvSkillShow);
        tvEditTime = view.findViewById(R.id.tvEditTime);

        tvTimeSetting = view.findViewById(R.id.tvTimeSetting);
        tvEditPass = view.findViewById(R.id.tvEditPass);
        tvRows = view.findViewById(R.id.tvRows);
        tvEditRow = view.findViewById(R.id.tvEditRow);
        swOn_offline = view.findViewById(R.id.swOn_offline);
        tvStatueOn_Off = view.findViewById(R.id.tvStatueOn_Off);

        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterCountryPro(getContext(),listSkill);

        reSkill.setAdapter(adapter);
        reSkill.setLayoutManager(horizontalLayoutManager);
        reSkill.setHasFixedSize(true);

    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvIDUser.setText(allCommand.GetStringShare(getContext(),allCommand.DisName,""));
        tvPhone.setText(allCommand.GetStringShare(getContext(),allCommand.Phone,""));

        for (int i = 0; i < SPINNER_DATA.length; i++) {
            String tempFlagimg = "";
            String strIDimgFlag = SPINNER_DATA[i];
            StringTokenizer st = new StringTokenizer(strIDimgFlag, ",");
            arrSaveNameFlag[i] = st.nextToken();
            tempFlagimg = st.nextToken();
            if (tempFlagimg.equals(allCommand.GetStringShare(getContext(),allCommand.Country,""))){
                tvCountry.setText(arrSaveNameFlag[i]);
            }

        }

        tvName.setText(allCommand.GetStringShare(getContext(),allCommand.Name,""));
        tvEmail.setText(allCommand.GetStringShare(getContext(),allCommand.Email,""));
        setImageLogo(allCommand.GetStringShare(getContext(),allCommand.Profile,""));
        tvRecommend.setText(allCommand.GetStringShare(getContext(),allCommand.mStatus,""));
        tvTimeSetting.setText(allCommand.GetStringShare(getContext(),allCommand.TimeOpen1,"")+" - "
                +allCommand.GetStringShare(getContext(),allCommand.TimeOpen2,""));
        tvRows.setText(allCommand.GetStringShare(getContext(),allCommand.PassJob,""));
        ShowHit = false;

        tvReShowhit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickRecommend();
            }
        });
        tvSkillShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSearch.size()>0){
                    listSearch.clear();
                }
                setOnClickSkill();
            }
        });
        tvEditTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                setOnClickTime();
            }
        });
        tvEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnCLickPass();
            }
        });
        getDataProfile();
        tvEditRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickRow();
            }
        });
        setOnOffLine();

        swOn_offline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setOnClickOnOffLine("1");
                    tvStatueOn_Off.setText("เปิด");
                }else {setOnClickOnOffLine("2");
                    tvStatueOn_Off.setText("ปิด"); }
            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bm=null;
                setOnClickImgProfile(bm,"");
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void setOnClickOnOffLine(final String statue){

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/set_online.php?mid="+
                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+"&online="+statue);
            }
        }.execute();
    }
    private void setOnOffLine(){

        if (allCommand.GetStringShare(getContext(),allCommand.Online,"").equals("1")){
            swOn_offline.setChecked(true);
            tvStatueOn_Off.setText("เปิด");

        }else {
            swOn_offline.setChecked(false);
            tvStatueOn_Off.setText("ปิด");
        }
    }
    @SuppressLint("StaticFieldLeak")
    private void getDataProfile(){

        if (listSkill.size()>0){
            listSkill.clear();
        }
        if (listNameSkill.size()>0){
            listNameSkill.clear();
        }
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/get_mem.php?mid"+
                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+"=&mid2="
                        + allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+ "&lang=" + "th_TH");
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.e("getDataProfile : ", s);

                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(s);
                        setDataSkill(jsonObject.getString("JobPic"));
                        setNameSkill(jsonObject.getString("Job"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }.execute();

    }
    private void setDataSkill(String skill){
        //Log.e("PageProfile : Job ", skill);
        String[] strIDimgFlag = skill.split(",");
        for (String aStrIDimgFlag : strIDimgFlag) {

            ModelCountry modelCountry = new ModelCountry();
            modelCountry.setUrlImage(aStrIDimgFlag);
            listSkill.add(modelCountry);
            adapter.notifyDataSetChanged();
        }
    }
    private void setNameSkill(String skill){
        //Log.e("PageProfile : Job ", skill);
        String[] strIDimgFlag = skill.split(",");
        listNameSkill.addAll(Arrays.asList(strIDimgFlag));
    }
    private void setImageLogo(String img_url){

        Glide.with(getContext())
                .load(strURL +"img/profile/"+ img_url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imProfile.setImageBitmap(resource);
                        //saveImage(resource);
                    }
                });
    }
    private void setImageGallery(String img_rul){
        String[] strIDimgFlag = img_rul.split(",");

        for (int i = 0; i < strIDimgFlag.length; i++) {
            Log.e("PageProfile Image ", strIDimgFlag[i]);
        }

        //Log.e("PageProfile Image ", img_rul);
    }
    private String saveImage(Bitmap image) {
        File myDir =new File(android.os.Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getActivity().getPackageName(),"img");

        String savedImagePath = null;
        String imageFileName = "logo.png";

        boolean success = true;
        if (!myDir.exists()) {
            success = myDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(myDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return savedImagePath;
    }
    private void setOnClickSkill(){

        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("อาชีพถนัด")
                .setView(R.layout.dialog_custom2)
                .create();

        diag.show();

        TextView tvSaveSkill = diag.findViewById(R.id.tvSaveSkill);
        TextView tvCancelSkill = diag.findViewById(R.id.tvCancelSkill);
        RecyclerView reDialogSkill = diag.findViewById(R.id.reDialogSkill);
        LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adpaterSkill = new AdpaterSkill(listSearch,getActivity());

        reDialogSkill.setAdapter(adpaterSkill);
        reDialogSkill.setLayoutManager(LayoutManager);
        reDialogSkill.setHasFixedSize(true);

        if (listSearch.size()>0){
            listSearch.clear();
        }
        getDataProfile();

        setDataSearch("0");
        pp = 0;
        reDialogSkill.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = LayoutManager.getChildCount();
                    totalItemCount = LayoutManager.getItemCount();
                    pastVisiblesItems = LayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            pp = pp+1;
                            setDataSearch(String.valueOf(pp));

                        }

                    }
                }

            }
        });

        adpaterSkill.setOnClickSelectSkill(new AdpaterSkill.onClickSelectSkill() {
            @Override
            public void onClickSlSkill(int position, String Tag) {

                ModelSkill modelSkill = new ModelSkill();
                modelSkill.setName(listSearch.get(position).getName());
                modelSkill.setPic(listSearch.get(position).getPic());
                modelSkill.setId(listSearch.get(position).getId());
                modelSkill.setUpdate("new");
                if (listSearch.get(position).getStatus().equals("1")){
                    modelSkill.setStatus("0");
                }else {
                    modelSkill.setStatus("1");
                }
                listSearch.set(position,modelSkill);
                adpaterSkill.notifyDataSetChanged();

            }
        });

        tvSaveSkill.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listSearch.size(); i++) {

                    if (listSearch.get(i).getUpdate().equals("new")){
                        final int finalI = i;
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... strings) {
                                return allCommand.GET_OK_HTTP_SendData(strURL+ "inc/editProfile.php?mid="+
                                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                                        "&ptype=1_1&val="+listSearch.get(finalI).getId());
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                Log.e("PageProfile", s);
                            }
                        }.execute();
                    }

                }
                diag.dismiss();
                getDataProfile();
            }
        });
        tvCancelSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diag.dismiss();
                getDataProfile();
            }
        });

    }
    private void setOnClickRecommend(){

        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("แนะนำตัวเอง")
                .setView(R.layout.dialog_recommend_profile)
                .create();

        diag.show();

        final EditText edReconText = diag.findViewById(R.id.edReconText);
        TextView tvSaveRecon = diag.findViewById(R.id.tvSaveRecon);
        TextView tvCancelRecon = diag.findViewById(R.id.tvCancelRecon);

        if (edReconText != null) {
            edReconText.setText(allCommand.GetStringShare(getContext(),allCommand.mStatus,""));
        }

        int position = edReconText.length();
        edReconText.setSelection(position);

        final String[] Mess = {""};
        if (tvSaveRecon != null) {
            tvSaveRecon.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View view) {
                    if (edReconText != null) {
                        Mess[0] = edReconText.getText().toString().trim();
                    }
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... strings) {
                            return allCommand.GET_OK_HTTP_SendData(strURL+"inc/editProfile.php?mid="+
                                    allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                                    "&ptype="+"1_2"+"&val="+Mess[0]);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                if (jsonObject.getString("Status").equals("ok")){

                                    allCommand.SaveStringShare(getContext(),allCommand.mStatus,Mess[0]);
                                    tvRecommend.setText(allCommand.GetStringShare(getContext(),allCommand.mStatus,""));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            diag.dismiss();
                        }
                    }.execute();
                }
            });
        }

        if (tvCancelRecon != null) {
            tvCancelRecon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diag.dismiss();
                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setOnClickTime(){

        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("เวลาที่ต้องการรับงาน")
                .setView(R.layout.dialog_time_profile)
                .create();

        diag.show();

        TextView tvSaveRow = diag.findViewById(R.id.tvSaveRow);
        TextView tvCancelRow = diag.findViewById(R.id.tvCancelRow);
        final TextView tvStatueOpen_Close = diag.findViewById(R.id.tvStatueOpen_Close);
        final TimePicker tpStart = diag.findViewById(R.id.tpStart);
        final TimePicker tpEnd = diag.findViewById(R.id.tpEnd);
        Switch swOpen_Close = diag.findViewById(R.id.swOpen_Close);
        tpStart.setIs24HourView(true);
        tpEnd.setIs24HourView(true);


        final String[] TimeInH = new String[1];
        final String[] TimeInM = new String[1];
        final String[] TimeOutH = new String[1];
        final String[] TimeOutM = new String[1];

        final String[] TimeIn = new String[1];
        final String[] TimeOut = new String[1];

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        final Calendar cTimeIn = Calendar.getInstance();
        Calendar cTimeOut = Calendar.getInstance();

        Date timeIn = null;
        Date timeOut = null;
        try {
            timeIn = sdf.parse(allCommand.GetStringShare(getContext(),allCommand.TimeOpen1,"00:00"));
            timeOut = sdf.parse(allCommand.GetStringShare(getContext(),allCommand.TimeOpen2,"00:00"));
        } catch (ParseException e) {

            allCommand.ShowLogCat("PageProfile ",e.getMessage());
        }

        cTimeIn.setTime(timeIn);
        cTimeOut.setTime(timeOut);

        tpStart.setHour(cTimeIn.get(Calendar.HOUR_OF_DAY));
        tpStart.setMinute(cTimeIn.get(Calendar.MINUTE));

        tpEnd.setHour(cTimeOut.get(Calendar.HOUR_OF_DAY));
        tpEnd.setMinute(cTimeOut.get(Calendar.MINUTE));

        tvSaveRow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                if (tpStart.getHour()<10){
                    TimeInH[0] ="0"+String.valueOf(tpStart.getHour());
                }else {
                    TimeInH[0] = String.valueOf(tpStart.getHour());
                }

                if (tpStart.getMinute()<10){
                    TimeInM[0] ="0"+String.valueOf(tpStart.getMinute());
                }else {
                    TimeInM[0] = String.valueOf(tpStart.getMinute());
                }

                if (tpEnd.getHour()<10){
                    TimeOutH[0] ="0"+String.valueOf(tpEnd.getHour());
                }else {
                    TimeOutH[0] = String.valueOf(tpEnd.getHour());
                }

                if (tpEnd.getMinute()<10){
                    TimeOutM[0] ="0"+String.valueOf(tpEnd.getMinute());
                }else {
                    TimeOutM[0] = String.valueOf(tpEnd.getMinute());
                }

                TimeIn[0] = TimeInH[0]+":"+TimeInM[0];
                TimeOut[0] = TimeOutH[0]+":"+TimeOutM[0];

                //Log.e("Time in ", TimeIn[0]+" "+ TimeOut[0]);


                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {

                        return  allCommand.GET_OK_HTTP_SendData(strURL+"inc/set_timeopen.php?mid="+
                                allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                                "&to1="+TimeIn[0]+"&to2=" + TimeOut[0]);

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("Status").equals("ok")){

                                allCommand.SaveStringShare(getContext(),allCommand.TimeOpen1,TimeIn[0]);
                                allCommand.SaveStringShare(getContext(),allCommand.TimeOpen2,TimeOut[0]);

                                tvTimeSetting.setText(allCommand.GetStringShare(getContext(),allCommand.TimeOpen1,"")+" - "
                                        +allCommand.GetStringShare(getContext(),allCommand.TimeOpen2,""));

                            }
                            diag.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.execute();

            }
        });

        if (allCommand.GetStringShare(getContext(),allCommand.OpenJob,"").equals("1")){
            tvStatueOpen_Close.setText("เปิดรับงาน");
        }else {
            tvStatueOpen_Close.setText("ปิดรับงาน");
        }
        swOpen_Close.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tvStatueOpen_Close.setText("เปิดรับงาน");
                    setOnClickOpenCloseJob("1");
                }else {
                    tvStatueOpen_Close.setText("ปิดรับงาน");
                    setOnClickOpenCloseJob("2");
                }
            }
        });

        tvCancelRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diag.dismiss();
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void setOnClickOpenCloseJob(final String statue){

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/editProfile.php?mid="+
                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+"&open="+statue);
            }
        }.execute();
    }
    private void setOnClickRow(){

        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("รหัสเส้นทาง")
                .setView(R.layout.dialog_row_profile)
                .create();

        diag.show();

        final EditText edRowText = diag.findViewById(R.id.edRowText);
        TextView tvSaveRow = diag.findViewById(R.id.tvSaveRow);
        TextView tvCancelRow = diag.findViewById(R.id.tvCancelRow);


        if (edRowText != null) {
            edRowText.setText(allCommand.GetStringShare(getContext(),allCommand.PassJob,""));
        }
        int position = edRowText.length();
        edRowText.setSelection(position);

        final String[] Mess = {""};
        if (tvSaveRow != null) {
            tvSaveRow.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View view) {

                    if (edRowText != null) {
                        Mess[0] = edRowText.getText().toString().trim();
                    }

                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... strings) {
                            return allCommand.GET_OK_HTTP_SendData(strURL+"inc/editProfile.php?mid="+
                                   allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                                    "&ptype="+"1_3"+"&val="+Mess[0]);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                if (jsonObject.getString("Status").equals("ok")){

                                    allCommand.SaveStringShare(getContext(),allCommand.PassJob,Mess[0]);
                                    tvRows.setText(allCommand.GetStringShare(getContext(),allCommand.PassJob,""));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            diag.dismiss();
                        }
                    }.execute();
                }
            });
        }
        tvCancelRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diag.dismiss();
            }
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void setDataSearch(final String pp){

        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //sfSearch.setRefreshing(true);
            }

            @Override
            protected String doInBackground(String... strings) {

                return allCommand.GET_OK_HTTP_SendData(strURL+
                        "list.php?sjob&retina=1&lang=th_TH&pp="+pp);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //sfSearch.setRefreshing(false);
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        ModelSkill modelSkill = new ModelSkill();
                        modelSkill.setName(jsonObject.getString("name"));
                        modelSkill.setPic(jsonObject.getString("pic"));
                        modelSkill.setStatus("0");
                        modelSkill.setId(jsonObject.getString("id"));
                        modelSkill.setUpdate("old");
                        if (setStatueSkill(jsonObject.getString("name")).equals("1")){
                            modelSkill.setStatus("1");
                        }
                        listSearch.add(modelSkill);
                        adpaterSkill.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = !(s == null);

            }
        }.execute();
    }
    private String setStatueSkill(String Name){
        for (int i1 = 0; i1 < listNameSkill.size(); i1++) {
            if (listNameSkill.get(i1).equals(Name)){
                return "1";
            }
        }
        return "0";
    }
    private void setOnCLickPass(){
        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("เปลี่ยนรหัสผ่าน")
                .setView(R.layout.dialog_pass_profile)
                .create();
        diag.show();

        TextView tvSavePass = diag.findViewById(R.id.tvSavePass);
        TextView tvCancelPass = diag.findViewById(R.id.tvCancelPass);
        final TextView tvErrorPassOld = diag.findViewById(R.id.tvErrorPassOld);
        tvErrorPassOld.setVisibility(View.GONE);
        final TextView tvErrorPassAgain = diag.findViewById(R.id.tvErrorPassAgain);
        tvErrorPassAgain.setVisibility(View.GONE);

        final EditText edPassOld = diag.findViewById(R.id.edPassOld);
        final EditText edPassNew = diag.findViewById(R.id.edPassNew);
        final EditText edPassNewAgain = diag.findViewById(R.id.edPassNewAgain);

        final String[] PassNew = new String[1];

        tvSavePass.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                if (allCommand.GetStringShare(getContext(), allCommand.SHARE_PASSWORD, "").equals(edPassOld.getText().toString().trim())){

                    if (edPassNew.getText().toString().trim().equals(edPassNewAgain.getText().toString().trim())){
                        PassNew[0] = edPassNew.getText().toString().trim();
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... strings) {
                                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/editProfile.php?mid="+
                                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                                        "&ptype="+"2_4"+"&val="+PassNew[0]);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                diag.dismiss();
                            }
                        }.execute();
                    }else {
                        tvErrorPassAgain.setVisibility(View.VISIBLE);
                    }
                }else {
                    tvErrorPassOld.setVisibility(View.VISIBLE);
                }
            }
        });

        if (edPassNew != null) {
            edPassNew.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (allCommand.GetStringShare(getContext(), allCommand.SHARE_PASSWORD, "").equals(edPassOld.getText().toString().trim())){
                        tvErrorPassOld.setVisibility(View.GONE);
                    }else tvErrorPassOld.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
        if (edPassNewAgain != null) {
            edPassNewAgain.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (allCommand.GetStringShare(getContext(), allCommand.SHARE_PASSWORD, "").equals(edPassOld.getText().toString().trim())){
                        tvErrorPassOld.setVisibility(View.GONE);
                    }else tvErrorPassOld.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }

        if (tvCancelPass != null) {
            tvCancelPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    diag.dismiss();
                }
            });
        }
    }
    final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {

                String realPath;
                // SDK < API11
                if (Build.VERSION.SDK_INT < 11)
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), data.getData());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), data.getData());

                    // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());

                onSelectFromGalleryResult(data,realPath);

            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setOnClickImgProfile(thumbnail, String.valueOf(destination));
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data,String part) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setOnClickImgProfile(bm, part);
    }
    @SuppressLint("StaticFieldLeak")
    private void setOnClickImgProfile(final Bitmap bm, final String fName){
        final AlertDialog diag = new AlertDialog.Builder(getContext())
                .setTitle("เปลี่ยนชื่อและรูปภาพ")
                .setView(R.layout.dialog_img_profile)
                .create();
        diag.show();

        TextView tvTakePhoto = diag.findViewById(R.id.tvTakePhoto);
       // final ImageView imProfiles = diag.findViewById(R.id.imProfiles);
        TextView tvStorage = diag.findViewById(R.id.tvStorage);
        TextView tvSaveImg = diag.findViewById(R.id.tvSaveImg);

        RecyclerView reImageGallery = diag.findViewById(R.id.reImageGallery);

        final ArrayList<ModelImage> listModel = new ArrayList<>();
        final AdapterImageGallery adapterImageGallery;
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapterImageGallery = new AdapterImageGallery(getContext(),listModel,strURL);

        reImageGallery.setAdapter(adapterImageGallery);
        reImageGallery.setLayoutManager(horizontalLayoutManager);
        reImageGallery.setHasFixedSize(true);

        final String[] strIDimgFlag = allCommand.GetStringShare(getContext(),allCommand.Gallery,"").split(",");
        final String ImageProfile = allCommand.GetStringShare(getContext(),allCommand.Profile,"");

        for (int i = 0; i < strIDimgFlag.length; i++) {
            ModelImage modelImage = new ModelImage();
            modelImage.setName(strIDimgFlag[i]);
            modelImage.setNameProfile(ImageProfile);
            modelImage.setOnSelece(false);
            listModel.add(modelImage);
            adapterImageGallery.notifyDataSetChanged();
        }
        adapterImageGallery.setOnClickImageProfile(new AdapterImageGallery.onClickImageProfile() {
            @Override
            public void onClick(int position, boolean check) {
                for (int i = 0; i < listModel.size(); i++) {

                    ModelImage modelImage = new ModelImage();
                    modelImage.setName(listModel.get(i).getName());
                    modelImage.setNameProfile(listModel.get(i).getNameProfile());
                    if (position==i){
                        if (check){
                            modelImage.setOnSelece(true);
                        }else {
                            modelImage.setOnSelece(false);
                        }
                    }else {
                        modelImage.setOnSelece(false);
                    }
                    listModel.set(i,modelImage);
                    adapterImageGallery.notifyDataSetChanged();
                }
            }
        });
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
                diag.dismiss();
            }
        });

        tvStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

                diag.dismiss();
            }
        });
      //  imProfiles.setImageBitmap(bm);
        tvSaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*setImageProfile(bm);

                File f = new File(fName);
                String imageName = f.getName();
                Log.e("PageProfile ", imageName);

                diag.dismiss();*/

                //setImageGallery(allCommand.GetStringShare(getContext(),allCommand.Gallery,""));

            }
        });


    }
    public void setImageProfile(Bitmap bm){
        imProfile.setImageBitmap(bm);
    }

    final private int REQUEST_MUTIPLE = 124;

    public void onPermissionMultiple() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Phone");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale

                String msg = "";
                for (int i = 0; i < permissionsNeeded.size(); i++){
                    msg += "\n" + permissionsList.get(i);
                }
                String alert1 = "Access is required.";
                String alert2 = "For complete work";
                String message = alert1 +" " + msg + " " +alert2;
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MUTIPLE);

                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(getActivity(),permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MUTIPLE);
            return;
        }
        allowMultipleSuccess();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        //ตรวจเช็ค
        if (ActivityCompat.checkSelfPermission(getContext(),permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            //ขอ Permission
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener closeListener) {
        String ok = "Ok";
        String exitApp = "Exit the app";
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(ok, okListener)
                .setNegativeButton(exitApp, closeListener)
                .create()
                .show();
    }
    private void allowMultipleSuccess(){
        allCommand.ShowLogCat("Status","อนุญาตหลายอย่างเสร็จสิ้น");
        //Call Thread Login


    }
}
