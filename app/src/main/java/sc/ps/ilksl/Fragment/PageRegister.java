package sc.ps.ilksl.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import sc.ps.ilksl.Adapter.AdapterSpriner;
import sc.ps.ilksl.Manager.AllCommand;
import sc.ps.ilksl.Manager.DataArray;
import sc.ps.ilksl.R;

public class PageRegister extends Fragment {

    public static PageRegister newInstance(){
        PageRegister pageRegister = new PageRegister();
        return pageRegister;
    }

    private AllCommand allCommand;
    private DataArray dataArray;
    private Spinner edCountry;
    private EditText edNameRegister,edPhon,edEmail,edBirthday,edPass,edConfirm;
    private RadioButton rbMan,rbWoman;
    private TextView btnSummit;
    private String URL;

    private Spinner spin;

    private String fname,sex,phone,email,birthDate,pass,country;

    AdapterSpriner customAdapter;

    String[] SPINNER_DATA;

    //int flags[] = {R.drawable.ic_gavel, R.drawable.ic_gavel, R.drawable.ic_gavel, R.drawable.ic_gavel, R.drawable.ic_gavel, R.drawable.ic_gavel};

    String[] arrSaveNameFlag;
    int[] arrSaveImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCommand = new AllCommand();
        dataArray = new DataArray();

        URL = allCommand.GetStringShare(getContext(),allCommand.SHARE_URL,"");

        SPINNER_DATA = dataArray.GET_NAME_COUNTRY(getActivity(),allCommand.GetStringShare(getContext(),
                allCommand.Language,allCommand.STRKEY_TH));

        arrSaveNameFlag = new String[SPINNER_DATA.length];
        arrSaveImage = new int[SPINNER_DATA.length];

        for (int i = 0; i < SPINNER_DATA.length; i++) {
            String tempFlagimg = "";
            String strIDimgFlag = SPINNER_DATA[i];
            StringTokenizer st = new StringTokenizer(strIDimgFlag, ",");

            arrSaveNameFlag[i] = st.nextToken();
            tempFlagimg = st.nextToken();
            arrSaveImage[i] = dataArray.GET_IMG_COUNTRY(tempFlagimg);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_register, container, false);
        ItemView(view);
        return view;
    }
    private void ItemView(View view){

        edNameRegister = view.findViewById(R.id.edNameRegister);
        //edCountry = view.findViewById(R.id.edCountry);
        edPhon = view.findViewById(R.id.edPhon);
        edEmail = view.findViewById(R.id.edEmail);
        edBirthday = view.findViewById(R.id.edBirthday);
        edPass = view.findViewById(R.id.edPass);
        edConfirm = view.findViewById(R.id.edConfirm);
        rbMan = view.findViewById(R.id.rbMan);
        rbWoman = view.findViewById(R.id.rbWoman);
        btnSummit = view.findViewById(R.id.btnSummit);

        spin = view.findViewById(R.id.material_spinner1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AdapterSpriner customAdapter=new AdapterSpriner(getContext(),arrSaveImage,arrSaveNameFlag);
        spin.setAdapter(customAdapter);

        btnSummit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                fname = edNameRegister.getText().toString().trim();
                if (rbMan.isChecked()){
                    sex = "1";
                }else {
                    sex = "2";
                }

                phone = edPhon.getText().toString().trim();
                email = edEmail.getText().toString().trim();
                birthDate = edBirthday.getText().toString().trim();
                pass = edPass.getText().toString().trim();
                //country = edCountry.getText().toString().trim();

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... strings) {

                        return allCommand.GET_OK_HTTP_SendData(URL+"inc/register.php"
                                +"?fname="+fname
                                +"&sex="+sex
                                +"&phone="+phone
                                +"&email="+email
                                +"&birthDate="+birthDate
                                +"&pass="+pass
                                +"&country="+"66"
                                +"&lang="+"th_TH");
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        Log.e("PageRegister data : ", s.toString());
                    }
                }.execute();
            }
        });
    }
}
