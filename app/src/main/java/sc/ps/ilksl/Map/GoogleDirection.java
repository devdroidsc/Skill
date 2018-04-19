/*
 * Copyright (c) 2013 Akexorcist
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package sc.ps.ilksl.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SuppressLint("NewApi")
public class GoogleDirection {

	public final static String MODE_DRIVING = "driving";
	private OnDirectionResponseListener mDirectionListener = null;
	private boolean isLogging = false;
	private Context mContext = null;
    public GoogleDirection(Context context) {
    	mContext = context;
    }
 
    public String request(LatLng start, LatLng end, String mode) {
        final String url = "http://maps.googleapis.com/maps/api/directions/xml?"
                + "origin=" + start.latitude + "," + start.longitude  
                + "&destination=" + end.latitude + "," + end.longitude 
                + "&sensor=false&units=metric&mode=" + mode;

   		if(isLogging)
   			Log.i("GoogleDirection", "URL : " + url);
        new RequestTask().execute(new String[]{ url });
        return url;
    }
    
    private class RequestTask extends AsyncTask<String, Void, Document> {
		protected Document doInBackground(String... url) {
			try {

				OkHttpClient client = new OkHttpClient();
				Request request = new Request.Builder().url(url[0]).build();
				Response response = client.newCall(request).execute();

				InputStream in = response.body().byteStream();
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				return builder.parse(in);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} 
			return null;
		}
		
		protected void onPostExecute(Document doc) {
			super.onPostExecute(doc);
			if(mDirectionListener != null)
				mDirectionListener.onResponse(getStatus(doc), doc, GoogleDirection.this);
		}
	    
	   	private String getStatus(Document doc) {
	   		NodeList nl1 = doc.getElementsByTagName("status");
	   		Node node1 = nl1.item(0);
	   		if(isLogging)
	   			Log.i("GoogleDirection", "Status : " + node1.getTextContent());
	   		return node1.getTextContent();
	    }
    }

	public interface OnDirectionResponseListener {
	    public void onResponse(String status, Document doc, GoogleDirection gd);
	}

	public void setOnDirectionResponseListener(OnDirectionResponseListener listener) {
		mDirectionListener = listener;
	}

	public String getTotalDistanceText(Document doc) {
		NodeList nl1 = doc.getElementsByTagName("distance");
		Node node1 = nl1.item(nl1.getLength() - 1);
		NodeList nl2 = node1.getChildNodes();
		Node node2 = nl2.item(getNodeIndex(nl2, "text"));
		if(isLogging)
			Log.i("GoogleDirection", "TotalDuration : " + node2.getTextContent());
		return node2.getTextContent();
	}
	private int getNodeIndex(NodeList nl, String nodename) {
		for(int i = 0 ; i < nl.getLength() ; i++) {
			if(nl.item(i).getNodeName().equals(nodename))
				return i;
		}
		return -1;
	}
}
