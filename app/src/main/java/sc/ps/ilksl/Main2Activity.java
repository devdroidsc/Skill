package sc.ps.ilksl;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import sc.ps.ilksl.Fragment.PageLanguage;
import sc.ps.ilksl.Fragment.PageLogin;
import sc.ps.ilksl.Fragment.PageMessage;
import sc.ps.ilksl.Fragment.PageProfile;
import sc.ps.ilksl.Fragment.PageRegister;
import sc.ps.ilksl.Fragment.PageSearch;
import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.bus.BusProvider;
import sc.ps.ilksl.bus.ModelBus;
import sc.ps.ilksl.bus.ModelClick;
import sc.ps.ilksl.utils.Utils;

public class Main2Activity extends AppCompatActivity {

    private AllCommand allCommand;
    private String TAG_PAGESEARCH = "TAGPAGESEARCH",TAG_PAGEPOST = "TAGPAGEPOST",TAG_PAGELOGIN = "TAGPAGELOGIN",
            TAG_PAGEPROFILE = "TAGPAGEPROFILE",TAG_PAGELANGUAGE = "TAGPAGELANGUAGE",TAG_PAGEMESSAGE = "TAGPAGEMESSAGE";


    private ImageView fab;
    private LinearLayout lnMenu1,lnMenu2,lnMenu3,fabMain,lnMenu4,lnMenu5;
    private TextView tvMenu2;
    private boolean onClick = false;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BusProvider.getInstance().register(this);

        allCommand = new AllCommand();

        allCommand.SaveStringShare(Main2Activity.this,allCommand.SHARE_URL,"http://ios.sktrue.com/");

        fab = findViewById(R.id.fab);
        lnMenu1 = findViewById(R.id.lnMenu1);
        lnMenu2 = findViewById(R.id.lnMenu2);
        lnMenu3 = findViewById(R.id.lnMenu3);
        lnMenu4 = findViewById(R.id.lnMenu4);
        lnMenu5 = findViewById(R.id.lnMenu5);
        fabMain = findViewById(R.id.fabMain);

        tvMenu2 = findViewById(R.id.tvMenu2);

        lnMenu1.setVisibility(View.GONE);
        lnMenu2.setVisibility(View.GONE);
        lnMenu3.setVisibility(View.GONE);
        lnMenu4.setVisibility(View.GONE);
        lnMenu5.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onClick){
                    onClick = false;
                    lnMenu1.setVisibility(View.GONE);
                    lnMenu2.setVisibility(View.GONE);
                    lnMenu3.setVisibility(View.GONE);
                    lnMenu4.setVisibility(View.GONE);
                    lnMenu5.setVisibility(View.GONE);
                    fabMain.setBackgroundColor(0);
                }else {
                    onClick = true;
                    lnMenu1.setVisibility(View.VISIBLE);
                    lnMenu2.setVisibility(View.VISIBLE);
                    lnMenu3.setVisibility(View.VISIBLE);
                    lnMenu4.setVisibility(View.VISIBLE);
                    lnMenu5.setVisibility(View.VISIBLE);
                    fabMain.setBackgroundColor(Color.parseColor("#9c9e9e9e"));

                    if (!allCommand.GetStringShare(Main2Activity.this,allCommand.STATUS_LOGIN,"").equals("1")){
                        lnMenu4.setVisibility(View.GONE);
                        lnMenu5.setVisibility(View.GONE);
                        tvMenu2.setText("เข้าระบบ");
                        lnMenu1.setVisibility(View.VISIBLE);
                    }else {
                        lnMenu4.setVisibility(View.VISIBLE);
                        lnMenu5.setVisibility(View.VISIBLE);
                        tvMenu2.setText("ออกระบบ");
                        lnMenu1.setVisibility(View.GONE);
                    }
                }
            }
        });
        lnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentAttach = (PageRegister)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGEPOST);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }
            }
        });
        lnMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentAttach = (PageLogin)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGELOGIN);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }
                if (!allCommand.GetStringShare(Main2Activity.this,allCommand.STATUS_LOGIN,"").equals("1")){
                    allCommand.SaveStringShare(Main2Activity.this,allCommand.STATUS_LOGIN,"0");
                }else {
                    allCommand.SaveStringShare(Main2Activity.this,allCommand.STATUS_LOGIN,"0");
                }
            }
        });

        lnMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentAttach = (PageLanguage)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGELANGUAGE);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

            }
        });

        lnMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentAttach = (PageProfile)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGEPROFILE);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

            }
        });
        lnMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentAttach = (PageMessage)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGEMESSAGE);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

            }
        });

        addFragmentFirst();
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Fragment fragmentAttach = (PageSearch)
                getSupportFragmentManager().findFragmentByTag(TAG_PAGESEARCH);

        if (!onFragmentPage().equals(fragmentAttach)){
            getSupportFragmentManager().beginTransaction()
                    .attach(fragmentAttach)
                    .detach(onFragmentPage())
                    .commit();
        }else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "กดที่ 'กลับ' อีกครั้งเพื่อออก", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }


    }

    @Subscribe
    public void setOnClick(ModelBus modelBus){

        if (modelBus !=null){
            if (modelBus.getPage() == Utils.KEY_ADD_PAGE_SEARCH){

                Fragment fragmentAttach = (PageSearch)
                        getSupportFragmentManager().findFragmentByTag(TAG_PAGESEARCH);

                if (!onFragmentPage().equals(fragmentAttach)){
                    getSupportFragmentManager().beginTransaction()
                            .attach(fragmentAttach)
                            .detach(onFragmentPage())
                            .commit();
                }

            }
        }


    }
    private void addFragmentFirst() {

        PageRegister pageRegister = PageRegister.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, pageRegister,TAG_PAGEPOST)
                .detach(pageRegister)
                .commit();

        PageLogin pageLogin = PageLogin.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, pageLogin,TAG_PAGELOGIN)
                .detach(pageLogin)
                .commit();

        PageProfile pageProfile = PageProfile.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, pageProfile,TAG_PAGEPROFILE)
                .detach(pageProfile)
                .commit();

        PageLanguage pageLanguage = PageLanguage.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, pageLanguage,TAG_PAGELANGUAGE)
                .detach(pageLanguage)
                .commit();

        PageMessage pageMessage = PageMessage.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, pageMessage,TAG_PAGEMESSAGE)
                .detach(pageMessage)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flMain, PageSearch.newInstance(),TAG_PAGESEARCH)
                .commit();
    }

    private Fragment onFragmentPage() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.flMain);
        if (f != null) {
            return f;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
