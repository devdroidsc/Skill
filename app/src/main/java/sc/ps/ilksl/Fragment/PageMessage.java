package sc.ps.ilksl.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sc.ps.ilksl.Adapter.AdapterMessage;
import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.Manager.GPSTracker;
import sc.ps.ilksl.Model.ModelMessage;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 10-04-2018.
 */

public class PageMessage extends Fragment{

    public static PageMessage newInstance(){

        PageMessage pageMessage = new PageMessage();
        return pageMessage;
    }

    private RecyclerView reMessage;
    private ArrayList<ModelMessage> listMessage;
    private AdapterMessage adapterMessage;
    private AllCommand allCommand;
    private String strURL;
    private LinearLayoutManager LaoutMessage;
    private AdapterMessage adapter;
    private GPSTracker gps;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        listMessage = new ArrayList<>();
        strURL = allCommand.GetStringShare(getContext(),allCommand.SHARE_URL,"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_message,container,false);
        itemView(view);
        return view;
    }
    public void itemView(View view){
        reMessage = view.findViewById(R.id.reMessage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LaoutMessage = new  LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterMessage(listMessage,getContext(),strURL);

        reMessage.setAdapter(adapter);
        reMessage.setLayoutManager(LaoutMessage);
        reMessage.setHasFixedSize(true);

        adapter.setOnclick(new AdapterMessage.setOnClick() {
            @Override
            public void setOnClick(int position, String lot, String lat) {

                //Log.e("PageMessage", position + " " + lat + " " + lot);

                gps = new GPSTracker(getContext());

                double latitude = 0;
                double longitude = 0;

                if (gps.canGetLocation()) {

                    latitude =gps.getLatitude();
                    longitude =gps.getLongitude();

                    Log.e("Position", "ตำแหน่งของคุณคือ - \nLat: " + latitude+
                            "\nLong: " +longitude);

                } else {
                    Log.e("Position", "อุปกรณ์์ของคุณ ปิด GPS");
                }
                final LatLng myHome = new LatLng(latitude, longitude);
                final LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lot));
                openGoogleMap(myHome, sydney);
            }
        });
        setDataMessage();
    }
    @SuppressLint("StaticFieldLeak")
    private void setDataMessage(){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/get_dir.php?mid="+
                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+"&lang=" + "th_TH"+"&pp="+0);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        ModelMessage modelMessage = new ModelMessage();
                        modelMessage.setStrID(jsonObject.getString("ID"));
                        modelMessage.setStrName(jsonObject.getString("Name"));
                        modelMessage.setStrTime(jsonObject.getString("mTime"));
                        modelMessage.setLatH(jsonObject.getString("Latitude"));
                        modelMessage.setLongH(jsonObject.getString("Longitude"));
                        modelMessage.setLatiG(jsonObject.getString("Latitudes"));
                        modelMessage.setLongG(jsonObject.getString("Longitudes"));
                        modelMessage.setStrImg(jsonObject.getString("Img"));

                        listMessage.add(modelMessage);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void openGoogleMap(LatLng src, LatLng dest) {
        String url = "http://maps.google.com/maps?saddr="+src.latitude+","+
                src.longitude+"&daddr="+dest.latitude+ "(" + "pp" + ")"+","+dest.longitude+"&mode=driving";
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
