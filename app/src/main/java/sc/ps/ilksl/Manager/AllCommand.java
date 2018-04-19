package sc.ps.ilksl.Manager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;


import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sc.ps.ilksl.BuildConfig;
import sc.ps.ilksl.FormatHttpPostOkHttp.FromHttpPostOkHttp;


public class AllCommand {

	public static String SHARE_NAME = "SHARE_NAME";
	public static String SHARE_URL = "SHARE_URL";
	public static String Language = "Language";

	public static String SHARE_EMAIL = "SHARE_EMAIL";
	public static String SHARE_PASSWORD = "SHARE_PASSWORD";
	public static String MemberID = "MemberID";
	public static String STATUS_LOGIN = "STATUS_LOGIN";

	//profile
	public static String Status = "Status";

	public static String Name = "Name",DisName = "DisName",Sex = "Sex",Phone = "Phone",Email = "Email",BDay = "BDay",BMount = "BMount",BYear = "BYear"
						,Profile = "Profile",Gallery = "Gallery",mStatus = "mStatus",Online = "Online",OpenJob = "OpenJob"
						,PassJob = "PassJob",Country = "Country",ConStatus = "ConStatus",TimeOpen1 = "TimeOpen1",TimeOpen2 = "TimeOpen2"
			,Job = "Job",Nnum = "Nnum",Nnum2 = "Nnum2",Message = "Message",Token = "Token";

	public static String STRKEY_TH = "th_TH",STRKEY_EN="en_EN",STRKEY_JA="ja_JA",
			STRKEY_KO="ko_KO",STRKEY_LA="la_LA",STRKEY_RS="rs_RS",STRKEY_ZH="zh_ZH";

	public boolean isConnectingToInternet(Context _context) {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}
	public String GET_OK_HTTP_SendData(String url) {
		ShowLogCat("GET_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		}catch (Exception e){
			ShowLogCat("Err","GET_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
	}
	public String POST_OK_HTTP_SendData(String url, ArrayList<FromHttpPostOkHttp> params) {
		ShowLogCat("POST_OK_HTTP_SendData",url);
		try{
			OkHttpClient client = new OkHttpClient();
			MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
			for (int i = 0;i<params.size();i++){
				Log.e("Check Data",params.get(i).getKEY_POST().toString()+ " : " +params.get(i).getVALUS_POST().toString());
				multipartBuilder.addFormDataPart(params.get(i).getKEY_POST().toString(),params.get(i).getVALUS_POST().toString());
			}
			RequestBody requestBody = multipartBuilder.build();
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();

			Response response = client.newCall(request).execute();
			if (response.isSuccessful()){
				return response.body().string().toString();
			}
		}catch (Exception e){
			Log.e("*** Err ***","Err POST_OK_HTTP_SendData " + e.getMessage());
			return "";
		}
		return "";
	}


	@TargetApi(Build.VERSION_CODES.KITKAT)
	public String setEncodeBase64(final String input) {
		try {
			byte[] message = input.getBytes(StandardCharsets.UTF_8);
			return  Base64.encodeToString(message, Base64.DEFAULT);
		}catch (Exception e){
			ShowLogCat("Err","setEncodeBase64 " + e.getMessage());
		}
		return "";
	}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public String getEncodeBase64(final String encodeBase64) {
		try {
			byte[] decoded = Base64.decode(encodeBase64, Base64.DEFAULT);
			return new String(decoded, StandardCharsets.UTF_8);
		}catch (Exception e){
			ShowLogCat("Err","getEncodeBase64 " + e.getMessage());

		}
		return "";
	}
	public String GetStringShare(Context _context, String strKey, String strDe) {
		SharedPreferences shLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		if (shLang != null) {
			String strShare = shLang.getString(strKey, strDe);
			return strShare;
		}
		return "";
	}
	public void SaveStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
		edShLang.putString(strKey, strDe);
		edShLang.commit();
	}

	public void RemvoeStringShare(Context _context, String strKey, String strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
	}
	public void ShowLogCat(String tag, String msg){
		if (BuildConfig.DEBUG){
			Log.e("***AllCommand***",tag + " : " + msg);
		}
	}
	public String CoverStringFromServer_One(String strData){
		try {
			return  strData.substring(strData.indexOf("{"), strData.lastIndexOf("}") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	public int getIntShare(Context _context, String strKey, int strDe) {
		SharedPreferences shLang;
		shLang = _context
				.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		if (shLang != null) {
			int strShare = shLang.getInt(strKey, strDe);
			return strShare;
		}
		return 0;
	}
	public void saveIntShare(Context _context, String strKey, int strDe){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
		edShLang.putInt(strKey, strDe);
		edShLang.commit();
	}
	public void deleteShareData(Context _context, String strKey){
		SharedPreferences shLang;
		SharedPreferences.Editor edShLang;
		shLang = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
		edShLang = shLang.edit();
		edShLang.remove(strKey);
		edShLang.commit();
	}

	public String SetDatestamp(String Stamp){

		long timestamp = Long.parseLong(Stamp) * 1000L;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date netDate = (new Date(timestamp));
			return sdf.format(netDate);
		}
		catch(Exception ex){
			return "xx";
		}
	}
	public String SetDateFoment(Date date){

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(date);
		}
		catch(Exception ex){
			return "xx";
		}
	}

	public String DeleteFormatNumber(String strData){
		String strDataNum = new String(strData);
		String strNum = strDataNum.replace(",", "");
		return strNum;
	}


}
