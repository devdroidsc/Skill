package sc.ps.ilksl.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 28-03-2018.
 */

public class PageLanguage extends Fragment {

    public static PageLanguage newInstance(){
        PageLanguage pageLanguage = new PageLanguage();
        return pageLanguage;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_language,container,false);
        return view;
    }
}
