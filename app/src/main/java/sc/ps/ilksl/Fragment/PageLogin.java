package sc.ps.ilksl.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.R;
import sc.ps.ilksl.bus.BusProvider;
import sc.ps.ilksl.bus.ModelBus;
import sc.ps.ilksl.bus.ModelClick;
import sc.ps.ilksl.utils.Utils;

/**
 * Created by Lenovo on 21-03-2018.
 */

public class PageLogin extends Fragment{

    public static PageLogin newInstance(){
        PageLogin pageLogin = new PageLogin();
        return pageLogin;
    }

    private TextView tvSubmit;
    private EditText edEmailLogin,edPassLogin;
    private AllCommand allCommand;
    private String strChackEmail,strChackPass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusProvider.getInstance().register(this);
        allCommand = new AllCommand();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_login,container,false);
        ItemView(view);
        return view;
    }

    public void ItemView(View v){

        tvSubmit = v.findViewById(R.id.tvSubmit);
        edEmailLogin = v.findViewById(R.id.edEmailLogin);
        edPassLogin = v.findViewById(R.id.edPassLogin);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setLogin();
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    public void setLogin(){

        strChackEmail = "";
        strChackPass = "";
        strChackEmail = edEmailLogin.getText().toString().trim();
        strChackPass = edPassLogin.getText().toString().trim();

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String strParam =allCommand.GetStringShare(getContext(),allCommand.SHARE_URL,"")+
                        "inc/checkLogin.php?sUsername=" + strChackEmail + "&sPassword=" + strChackPass;
                //Log.e("strResultLogin",  " ::::: "+ strResultLogin);
                return allCommand.GET_OK_HTTP_SendData(strParam);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("PageLogi  ", s);

                if (!(s.equals(""))) {
                    try {
                        JSONObject JObject;
                        String strStatus = "";
                        JObject = new JSONObject(s);
                        strStatus = JObject.getString("Status");
                        if (strStatus.toString().equals("1")) {
                            allCommand.SaveStringShare(getContext(),allCommand.SHARE_EMAIL, edEmailLogin.getText().toString().trim());
                            allCommand.SaveStringShare(getContext(),allCommand.SHARE_PASSWORD, edPassLogin.getText().toString().trim());

                            allCommand.SaveStringShare(getContext(),allCommand.STATUS_LOGIN,"1");
                            allCommand.SaveStringShare(getContext(),allCommand.Status ,JObject.getString("Status"));
                            allCommand.SaveStringShare(getContext(),allCommand.MemberID, JObject.getString("MemberID"));
                            allCommand.SaveStringShare(getContext(),allCommand.Name , JObject.getString("Name"));
                            allCommand.SaveStringShare(getContext(),allCommand.DisName,JObject.getString("DisName"));
                            allCommand.SaveStringShare(getContext(),allCommand.Sex,JObject.getString("Sex"));
                            allCommand.SaveStringShare(getContext(),allCommand.Phone,JObject.getString("Phone"));
                            allCommand.SaveStringShare(getContext(),allCommand.Email,JObject.getString("Email"));
                            allCommand.SaveStringShare(getContext(),allCommand.BDay,JObject.getString("BDay"));
                            allCommand.SaveStringShare(getContext(),allCommand.BMount,JObject.getString("BMount"));
                            allCommand.SaveStringShare(getContext(),allCommand.BYear,JObject.getString("BYear"));
                            allCommand.SaveStringShare(getContext(),allCommand.Profile,JObject.getString("Profile"));
                            allCommand.SaveStringShare(getContext(),allCommand.Gallery,JObject.getString("Gallery"));
                            allCommand.SaveStringShare(getContext(),allCommand.mStatus,JObject.getString("mStatus"));
                            allCommand.SaveStringShare(getContext(),allCommand.Online,JObject.getString("Online"));
                            allCommand.SaveStringShare(getContext(),allCommand.OpenJob,JObject.getString("OpenJob"));
                            allCommand.SaveStringShare(getContext(),allCommand.PassJob,JObject.getString("PassJob"));
                            allCommand.SaveStringShare(getContext(),allCommand.Country,JObject.getString("Country"));
                            allCommand.SaveStringShare(getContext(),allCommand.ConStatus,JObject.getString("ConStatus"));
                            allCommand.SaveStringShare(getContext(),allCommand.TimeOpen1,JObject.getString("TimeOpen1"));
                            allCommand.SaveStringShare(getContext(),allCommand.TimeOpen2,JObject.getString("TimeOpen2"));
                            allCommand.SaveStringShare(getContext(),allCommand.Job,JObject.getString("Job"));
                            allCommand.SaveStringShare(getContext(),allCommand.Nnum,JObject.getString("Nnum"));
                            allCommand.SaveStringShare(getContext(),allCommand.Nnum2,JObject.getString("Nnum2"));
                            allCommand.SaveStringShare(getContext(),allCommand.Message, JObject.getString("Message"));
                            allCommand.SaveStringShare(getContext(),allCommand.Token,JObject.getString("Token"));
                           // all_Variable.iSaveNew = Integer.parseInt(all_Command.getStringShare(all_Command.SHARE_NumMSG, "0"));

                            ModelBus modelBus = new ModelBus();
                            modelBus.setPage(Utils.KEY_ADD_PAGE_SEARCH);
                            modelBus.setMsg(Utils.NAME_ADD_PAGE_SEARCH);
                            BusProvider.getInstance().post(modelBus);

                            ModelClick modelClick = new ModelClick();
                            modelClick.setKey("1");
                            modelClick.setMess("Login");
                            BusProvider.getInstance().post(modelClick);
                        }else {
                            //all_Command.ShowCustomAlertDialog_OK(all_Variable.STR_warn + " : E-14", all_Variable.STR_finddate,ZOKLoginActivity.this,strCheckLang);
                        }
                    } catch (Exception e) {
                        Log.e("PageLogin", e.getMessage());
                    }

                }


            }
        }.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }
}
