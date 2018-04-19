package sc.ps.ilksl.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import sc.ps.ilksl.Adapter.AdapterCountryPro;
import sc.ps.ilksl.Adapter.AdpaterSearch;
import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.Manager.GPSTracker;
import sc.ps.ilksl.Map.GoogleDirection;
import sc.ps.ilksl.Model.ModelCountry;
import sc.ps.ilksl.Model.ModelSear;
import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 06-03-2018.
 */

public class PageSearch extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{



    public static PageSearch newInstance() {
        PageSearch pageSearch = new PageSearch();
        return pageSearch;
    }

    private AllCommand allCommand;
    private ArrayList<ModelSear> listSearch;

    private LinearLayoutManager horizontalLayoutManager,LaoutSkill;
    private RecyclerView reSearch;
    private AdpaterSearch adapter;
    private AdapterCountryPro adapterSkill;
    private SearchView edSearch;
    private SwipeRefreshLayout sfSearch;
    private TextView tvAddress,tvRecommendMap,tvPhoneMap,tvDistanceMap,tvNavigate,tvExtra;
    private ImageView imProfileMap;
    private LinearLayout lnExtra;
    private RecyclerView reSkillSearch;
    private ArrayList<ModelCountry> listSkill;
    private LinearLayout lnDetail;
    private TextView tvCloseDetail;

    private MapView mMapView;
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int pp = 0;
    private GPSTracker gps;
    private String strURL;
    private MarkerOptions markers;
    private String Latitude,Longitude;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        listSearch = new ArrayList<>();
        listSkill = new ArrayList<>();

        strURL = allCommand.GetStringShare(getContext(),allCommand.SHARE_URL,"");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_search, container, false);
        itemView(view);
        mMapView = view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        return view;

    }
    private void itemView(View view){

        reSearch = view.findViewById(R.id.reSearch);
        edSearch = view.findViewById(R.id.edSearch);
        sfSearch = view.findViewById(R.id.sfSearch);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvRecommendMap = view.findViewById(R.id.tvRecommendMap);
        tvPhoneMap = view.findViewById(R.id.tvPhoneMap);
        tvDistanceMap = view.findViewById(R.id.tvDistanceMap);
        imProfileMap = view.findViewById(R.id.imProfileMap);
        tvNavigate = view.findViewById(R.id.tvNavigate);
       // tvExtra = view.findViewById(R.id.tvExtra);
        lnExtra = view.findViewById(R.id.lnExtra);
        //lnExtra.setVisibility(View.GONE);
        reSkillSearch = view.findViewById(R.id.reSkillSearch);
        lnDetail = view.findViewById(R.id.lnDetail);
        tvCloseDetail = view.findViewById(R.id.tvCloseDetail);

        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LaoutSkill = new  LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new AdpaterSearch(listSearch,getActivity());

        adapterSkill = new AdapterCountryPro(getActivity(),listSkill);

        reSearch.setAdapter(adapter);
        reSearch.setLayoutManager(horizontalLayoutManager);
        reSearch.setHasFixedSize(true);

        reSkillSearch.setAdapter(adapterSkill);
        reSkillSearch.setLayoutManager(LaoutSkill);
        reSkillSearch.setHasFixedSize(true);

        edSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        sfSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sfSearch.setRefreshing(true);
                if (listSearch.size()>0){
                    listSearch.clear();
                }
                setDataSearch("0");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listSearch.size()>=0){
            listSearch.clear();
            setDataSearch("0");
        }else {
            setDataSearch("0");
        }

       mMapView.getMapAsync(this);

       reSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);


               if(dx > 0) //check for scroll down
               {
                   visibleItemCount = horizontalLayoutManager.getChildCount();
                   totalItemCount = horizontalLayoutManager.getItemCount();
                   pastVisiblesItems = horizontalLayoutManager.findFirstVisibleItemPosition();

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

       adapter.setOnClickSearch(new AdpaterSearch.onClickSearch() {
           @SuppressLint("StaticFieldLeak")
           @Override
           public void onClickSearch(int position, String tage, final String id) {
               Log.e("PageSearch", "OnClick MakMap");
               new AsyncTask<String, Void, String>() {
                   @Override
                   protected String doInBackground(String... strings) {
                       return allCommand.GET_OK_HTTP_SendData(strURL+"inc/map.php?mid="+
                               allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                               "&s="+id+"&lang=th_TH");
                   }
                   @Override
                   protected void onPostExecute(String s) {
                       super.onPostExecute(s);

                       Latitude = "";
                       Longitude = "";

                       Log.e("map.php ", s);
                       try {
                           JSONArray jsonArray = new JSONArray(s);
                           for (int i = 0; i < jsonArray.length(); i++) {

                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               /*jsonObject.getString("Latitude");
                               jsonObject.getString("Longitude");*/

                               Latitude = jsonObject.getString("Latitude");
                               Longitude = jsonObject.getString("Longitude");

                               if (!jsonObject.getString("Latitude").equals("")&&!jsonObject.getString("Longitude").equals("")){
                                   markers = new MarkerOptions().position(
                                           new LatLng(Double.parseDouble(jsonObject.getString("Latitude")),
                                                   Double.parseDouble(jsonObject.getString("Longitude"))))
                                           .title(jsonObject.getString("ID"));

                                   markers.icon(BitmapDescriptorFactory
                                           .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                   mMap.addMarker(markers);

                                   CameraPosition cameraPosition = new CameraPosition.Builder()
                                           .target(new LatLng(Double.parseDouble(jsonObject.getString("Latitude")),
                                                   Double.parseDouble(jsonObject.getString("Longitude")))).zoom(9).build();

                                   mMap.animateCamera(CameraUpdateFactory
                                           .newCameraPosition(cameraPosition));
                               }else {

                                   Log.e("PageSearch", "No pepro online");
                               }

                           }


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }.execute();
           }
       });
       tvNavigate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


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
               final LatLng sydney = new LatLng(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
               openGoogleMap(myHome, sydney);

           }
       });


        tvCloseDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnDetail.setVisibility(View.GONE);
            }
        });
        /*tvExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("PageSearch", "OnClick");
                if (lnExtra.getVisibility()==View.GONE){
                    lnExtra.setVisibility(View.VISIBLE);

                }else {
                    lnExtra.setVisibility(View.GONE);
                }

            }
        });*/

    }

    @SuppressLint("StaticFieldLeak")
    private void onSetDataSkillSeach(final String pp){

        if (listSkill.size()>0){
            listSkill.clear();
        }
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/get_mem.php?mid"+
                        allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+"=&mid2="
                        + pp + "&lang=" + "th_TH");
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.e("getDataProfile : ", s);

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(s);
                    setDataSkill(jsonObject.getString("JobPic"));
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
            adapterSkill.notifyDataSetChanged();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void setDataSearch(final String pp){
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                sfSearch.setRefreshing(true);
            }

            @Override
            protected String doInBackground(String... strings) {

                return allCommand.GET_OK_HTTP_SendData(strURL+
                        "list.php?sjob&retina=1&lang=th_TH&pp="+pp);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                sfSearch.setRefreshing(false);
                Log.e("PageSearch ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        ModelSear modelSear = new ModelSear();
                        modelSear.setName(jsonObject.getString("name"));
                        modelSear.setPic(jsonObject.getString("pic"));
                        modelSear.setId(jsonObject.getString("id"));
                        listSearch.add(modelSear);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading = !(s == null);




            }
        }.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }

        mMap.setMyLocationEnabled(false);
        mMap.setOnMarkerClickListener(this);


        /*for (int i = 0; i < 2; i++) {

            // random latitude and logitude
            double[] randomLocation = createRandLocation(latitude[i],
                    longitude[i]);

            // Adding a marker

            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude[i], longitude[i]))
                    .title("Hello Maps " + i);

            // changing marker color
            if (i == 0)
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            if (i == 1)
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            if (i == 2)
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            if (i == 3)
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            if (i == 4)
                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            googleMap.addMarker(marker);

            // Move the camera to last position with a zoom level
            if (i == 1) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(randomLocation[0],
                                randomLocation[1])).zoom(15).build();

                googleMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }
        }*/


    }

    private double[] createRandLocation(double latitude, double longitude) {

        return new double[] {
                latitude + ((Math.random() - 0.5) / 500),longitude + ((Math.random() - 0.5) / 500),150 + ((Math.random() - 0.5) * 10) };
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(false);
                    Log.e("MapsActivity", "PERMISSION_GRANTED");
                }
                break;
        }
    }




    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onMarkerClick(final Marker marker) {

        lnDetail.setVisibility(View.VISIBLE);
        Log.e("PageSearch OnClick", marker.getTitle().toString().trim());

        //tvAddress.setVisibility(View.VISIBLE);
        final String mid2 = marker.getTitle().toString().trim();
        onSetDataSkillSeach(mid2);

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return allCommand.GET_OK_HTTP_SendData(strURL+"inc/get_mem.php?mid="+
                               allCommand.GetStringShare(getContext(),allCommand.MemberID,"")+
                               "&mid2="+mid2+"&lang=th_TH");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    jsonObject.getString("Name");
                    tvAddress.setText(jsonObject.getString("Name"));
                    tvRecommendMap.setText(jsonObject.getString("mStatus"));
                    tvPhoneMap.setText(jsonObject.getString("Phone"));
                    setImageLogo(jsonObject.getString("Profile"));
                    //tvDistanceMap.setText(jsonObject.getString(""));
                    //Log.e("onMarkerClick ", jsonObject.getString("Name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();

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

        setCalculateRow(longitude, Double.parseDouble(Longitude), latitude, Double.parseDouble(Latitude));


        return false;
    }

    public void setCalculateRow(double lon1,double lon2,double lat1,double lat2){

        LatLng start = new LatLng(lat1, lon1);
        LatLng end = new LatLng(lat2, lon2);
        GoogleDirection gd = new GoogleDirection(getContext());
        gd.request(start, end, GoogleDirection.MODE_DRIVING);
        gd.setOnDirectionResponseListener(new GoogleDirection.OnDirectionResponseListener() {
            @Override
            public void onResponse(String status, Document doc, GoogleDirection gd) {

                String distance = gd.getTotalDistanceText(doc);

                //Log.e("PageSearch: ", distance);
                tvDistanceMap.setText(distance);
            }
        });

    }
    private void setImageLogo(String img_url){

        Glide.with(getContext())
                .load(strURL +"img/profile/"+ img_url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imProfileMap.setImageBitmap(resource);
                    }
                });
    }

    private void openGoogleMap(LatLng src, LatLng dest) {
        String url = "http://maps.google.com/maps?saddr="+src.latitude+","+src.longitude+"&daddr="+dest.latitude+","+dest.longitude+"&mode=driving";
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
