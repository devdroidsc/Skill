package sc.ps.ilksl.Manager;

import android.app.Activity;
import android.content.res.TypedArray;

import java.util.Arrays;

import sc.ps.ilksl.R;

/**
 * Created by Lenovo on 20-03-2018.
 */

public class DataArray {

    public static String STRKEY_TH = "th_TH",STRKEY_EN="en_EN",STRKEY_JA="ja_JA",
            STRKEY_KO="ko_KO",STRKEY_LA="la_LA",STRKEY_RS="rs_RS",STRKEY_ZH="zh_ZH";

    public String[] GET_NAME_COUNTRY(Activity activity , String strKeyLang){
        String[] arrDataNameFage = null;
        if (strKeyLang.toString().equals(STRKEY_EN)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_en);
        }else if (strKeyLang.toString().equals(STRKEY_JA)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_ja);
        }else if (strKeyLang.toString().equals(STRKEY_ZH)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_zh);
        }else if (strKeyLang.toString().equals(STRKEY_KO)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_ko);
        }else if (strKeyLang.toString().equals(STRKEY_TH)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_th);
        }else if (strKeyLang.toString().equals(STRKEY_LA)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_la);
        }else if (strKeyLang.toString().equals(STRKEY_RS)) {
            arrDataNameFage = getStringArray(activity, R.array.my_flag_rs);
        }
        Arrays.sort(arrDataNameFage);
        return arrDataNameFage;
    }

    public int GET_IMG_COUNTRY(String strPath) {
        int iIDImg = 0;
        String strPathImg = strPath;
        switch (strPathImg){
            case "01":
                iIDImg = R.drawable.united_states;
                break;
            case "02":
                iIDImg = R.drawable.guam;
                break;
            case "1670":
                iIDImg = R.drawable.nm_islands;
                break;
            case "1684":
                iIDImg = R.drawable.american_samoa;
                break;
            case "1":
                iIDImg = R.drawable.canada;
                break;
            case "1441":
                iIDImg = R.drawable.bermuda;
                break;
            case "1264":
                iIDImg = R.drawable.anguilla;
                break;
            case "1268":
                iIDImg = R.drawable.antigua_barbuda;
                break;
            case "1242":
                iIDImg = R.drawable.bahamas;
                break;
            case "1246":
                iIDImg = R.drawable.barbados;
                break;
            case "1284":
                iIDImg = R.drawable.bv_islands;
                break;
            case "1345":
                iIDImg = R.drawable.c_islands;
                break;
            case "1767":
                iIDImg = R.drawable.dominica;
                break;
            case "1809":
                iIDImg = R.drawable.domi_republic;
                break;
            case "1473":
                iIDImg = R.drawable.grenada;
                break;
            case "1876":
                iIDImg = R.drawable.jamaica;
                break;
            case "1664":
                iIDImg = R.drawable.montserrat;
                break;
            case "1787":
                iIDImg = R.drawable.puerto_rico;
                break;
            case "1869":
                iIDImg = R.drawable.saint_kitts_nevis;
                break;
            case "1758":
                iIDImg = R.drawable.saint_lucia;
                break;
            case "1784":
                iIDImg = R.drawable.saint_vincent_grenadines;
                break;
            case "1868":
                iIDImg = R.drawable.dominican_republic;
                break;
            case "1649":
                iIDImg = R.drawable.turks_caicos_islands;
                break;
            case "1340":
                iIDImg = R.drawable.united_states_virgin_islands;
                break;
            case "20":
                iIDImg = R.drawable.egypt;
                break;
            case "212":
                iIDImg = R.drawable.morocco;
                break;
            case "213":
                iIDImg = R.drawable.algeria;
                break;
            case "216":
                iIDImg = R.drawable.tunisia;
                break;
            case "218":
                iIDImg = R.drawable.libya;
                break;
            case "220":
                iIDImg = R.drawable.gambia;
                break;
            case "221":
                iIDImg = R.drawable.senegal;
                break;
            case "222":
                iIDImg = R.drawable.mauritania;
                break;
            case "223":
                iIDImg = R.drawable.mali;
                break;
            case "224":
                iIDImg = R.drawable.guinea;
                break;
            case "225":
                iIDImg = R.drawable.ivoire;
                break;
            case "226":
                iIDImg = R.drawable.burkina_faso;
                break;
            case "227":
                iIDImg = R.drawable.niger;
                break;
            case "228":
                iIDImg = R.drawable.togo;
                break;
            case "229":
                iIDImg = R.drawable.benin;
                break;
            case "230":
                iIDImg = R.drawable.mauritius;
                break;
            case "231":
                iIDImg = R.drawable.liberia;
                break;
            case "232":
                iIDImg = R.drawable.sierra_leone;
                break;
            case "233":
                iIDImg = R.drawable.ghana;
                break;
            case "234":
                iIDImg = R.drawable.nigeria;
                break;
            case "235":
                iIDImg = R.drawable.chad;
                break;
            case "236":
                iIDImg = R.drawable.central_african_republic;
                break;
            case "237":
                iIDImg = R.drawable.cameroon;
                break;
            case "238":
                iIDImg = R.drawable.cape_verde;
                break;
            case "239":
                iIDImg = R.drawable.sao_tome_principe;
                break;
            case "240":
                iIDImg = R.drawable.equatorial_guinea;
                break;
            case "241":
                iIDImg = R.drawable.gabon;
                break;
            case "242":
                iIDImg = R.drawable.republic_congo;
                break;
            case "243":
                iIDImg = R.drawable.democratic_republic_congo;
                break;
            case "244":
                iIDImg = R.drawable.angola;
                break;
            case "245":
                iIDImg = R.drawable.guinea_bissau;
                break;
            case "246":
                iIDImg = R.drawable.british_indian_ocean_territory;
                break;
            case "247":
                iIDImg = R.drawable.ascension_island;
                break;
            case "248":
                iIDImg = R.drawable.seychelles;
                break;
            case "249":
                iIDImg = R.drawable.sudan;
                break;
            case "250":
                iIDImg = R.drawable.rwanda;
                break;
            case "251":
                iIDImg = R.drawable.ethiopia;
                break;
            case "252":
                iIDImg = R.drawable.somalia;
                break;
            case "253":
                iIDImg = R.drawable.djibouti;
                break;
            case "254":
                iIDImg = R.drawable.kenya;
                break;
            case "255":
                iIDImg = R.drawable.tanzania;
                break;
            case "256":
                iIDImg = R.drawable.uganda;
                break;
            case "257":
                iIDImg = R.drawable.burundi;
                break;
            case "258":
                iIDImg = R.drawable.mozambique;
                break;
            case "260":
                iIDImg = R.drawable.zambia;
                break;
            case "261":
                iIDImg = R.drawable.madagascar;
                break;
            case "262":
                iIDImg = R.drawable.reunion;
                break;
            case "263":
                iIDImg = R.drawable.zimbabwe;
                break;
            case "264":
                iIDImg = R.drawable.namibia;
                break;
            case "265":
                iIDImg = R.drawable.malawi;
                break;
            case "266":
                iIDImg = R.drawable.lesotho;
                break;
            case "267":
                iIDImg = R.drawable.botswana;
                break;
            case "268":
                iIDImg = R.drawable.swaziland;
                break;
            case "269":
                iIDImg = R.drawable.comoros;
                break;
            case "27":
                iIDImg = R.drawable.southafrica;
                break;
            case "290":
                iIDImg = R.drawable.saint;
                break;
            case "291":
                iIDImg = R.drawable.eritrea;
                break;
            case "297":
                iIDImg = R.drawable.aruba;
                break;
            case "298":
                iIDImg = R.drawable.faroe_islands;
                break;
            case "299":
                iIDImg = R.drawable.greenland;
                break;
            case "30":
                iIDImg = R.drawable.greece;
                break;
            case "31":
                iIDImg = R.drawable.netherlands;
                break;
            case "32":
                iIDImg = R.drawable.belgium;
                break;
            case "33":
                iIDImg = R.drawable.france;
                break;
            case "34":
                iIDImg = R.drawable.spain;
                break;
            case "350":
                iIDImg = R.drawable.gibraltar;
                break;
            case "351":
                iIDImg = R.drawable.portugal;
                break;
            case "352":
                iIDImg = R.drawable.luxembourg;
                break;
            case "353":
                iIDImg = R.drawable.ireland;
                break;
            case "354":
                iIDImg = R.drawable.iceland;
                break;
            case "355":
                iIDImg = R.drawable.albania;
                break;
            case "356":
                iIDImg = R.drawable.malta;
                break;
            case "357":
                iIDImg = R.drawable.cyprus;
                break;
            case "358":
                iIDImg = R.drawable.finland;
                break;
            case "359":
                iIDImg = R.drawable.bulgaria;
                break;
            case "36":
                iIDImg = R.drawable.hungary;
                break;
            case "370":
                iIDImg = R.drawable.lithuania;
                break;
            case "371":
                iIDImg = R.drawable.latvia;
                break;
            case "372":
                iIDImg = R.drawable.estonia;
                break;
            case "373":
                iIDImg = R.drawable.moldova;
                break;
            case "373533":
                iIDImg = R.drawable.transnistria;
                break;
            case "374":
                iIDImg = R.drawable.armenia;
                break;
            case "375":
                iIDImg = R.drawable.belarus;
                break;
            case "376":
                iIDImg = R.drawable.andorra;
                break;
            case "377":
                iIDImg = R.drawable.monaco;
                break;
            case "378":
                iIDImg = R.drawable.san_marino;
                break;
            case "379":
                iIDImg = R.drawable.vatican_city;
                break;
            case "380":
                iIDImg = R.drawable.ukraine;
                break;
            case "381":
                iIDImg = R.drawable.serbia;
                break;
            case "382":
                iIDImg = R.drawable.montenegro;
                break;
            case "385":
                iIDImg = R.drawable.croatia;
                break;
            case "386":
                iIDImg = R.drawable.slovenia;
                break;
            case "387":
                iIDImg = R.drawable.bosnia_herzegovina;
                break;
            case "389":
                iIDImg = R.drawable.macedonia;
                break;
            case "39":
                iIDImg = R.drawable.italy;
                break;
            case "40":
                iIDImg = R.drawable.romania;
                break;
            case "41":
                iIDImg = R.drawable.switzerland;
                break;
            case "420":
                iIDImg = R.drawable.czech_republic;
                break;
            case "421":
                iIDImg = R.drawable.slovakia;
                break;
            case "423":
                iIDImg = R.drawable.liechtenstein;
                break;
            case "43":
                iIDImg = R.drawable.austria;
                break;
            case "44":
                iIDImg = R.drawable.united_kingdom;
                break;
            case "45":
                iIDImg = R.drawable.denmark;
                break;
            case "46":
                iIDImg = R.drawable.sweden;
                break;
            case "47":
                iIDImg = R.drawable.norway;
                break;
            case "48":
                iIDImg = R.drawable.poland;
                break;
            case "49":
                iIDImg = R.drawable.germany;
                break;
            case "500":
                iIDImg = R.drawable.falkland_islands;
                break;
            case "501":
                iIDImg = R.drawable.belize;
                break;
            case "502":
                iIDImg = R.drawable.guatemala;
                break;
            case "503":
                iIDImg = R.drawable.el_salvador;
                break;
            case "504":
                iIDImg = R.drawable.honduras;
                break;
            case "505":
                iIDImg = R.drawable.nicaragua;
                break;
            case "506":
                iIDImg = R.drawable.costa_rica;
                break;
            case "507":
                iIDImg = R.drawable.panama;
                break;
            case "508":
                iIDImg = R.drawable.saint_pierre_miquelon;
                break;
            case "509":
                iIDImg = R.drawable.haiti;
                break;
            case "51":
                iIDImg = R.drawable.peru;
                break;
            case "52":
                iIDImg = R.drawable.mexico;
                break;
            case "53":
                iIDImg = R.drawable.buba;
                break;
            case "54":
                iIDImg = R.drawable.argentina;
                break;
            case "55":
                iIDImg = R.drawable.brazil;
                break;
            case "56":
                iIDImg = R.drawable.chile;
                break;
            case "57":
                iIDImg = R.drawable.colombia;
                break;
            case "58":
                iIDImg = R.drawable.venezuela;
                break;
            case "590":
                iIDImg = R.drawable.guadeloupe;
                break;
            case "591":
                iIDImg = R.drawable.bolivia;
                break;
            case "592":
                iIDImg = R.drawable.guyana;
                break;
            case "593":
                iIDImg = R.drawable.ecuador;
                break;
            case "594":
                iIDImg = R.drawable.french_guiana;
                break;
            case "595":
                iIDImg = R.drawable.paraguay;
                break;
            case "596":
                iIDImg = R.drawable.martinique;
                break;
            case "597":
                iIDImg = R.drawable.suriname;
                break;
            case "598":
                iIDImg = R.drawable.uruguay;
                break;
            case "599":
                iIDImg = R.drawable.netherlands_antilles;
                break;
            case "60":
                iIDImg = R.drawable.malaysia;
                break;
            case "61":
                iIDImg = R.drawable.australia;
                break;
            case "62":
                iIDImg = R.drawable.indonesia;
                break;
            case "63":
                iIDImg = R.drawable.philippines;
                break;
            case "64":
                iIDImg = R.drawable.new_zealand;
                break;
            case "65":
                iIDImg = R.drawable.singapore;
                break;
            case "66":
                iIDImg = R.drawable.thailand;
                break;
            case "670":
                iIDImg = R.drawable.east_timor;
                break;
            case "673":
                iIDImg = R.drawable.brunei;
                break;
            case "674":
                iIDImg = R.drawable.nauru;
                break;
            case "675":
                iIDImg = R.drawable.papua_new_guinea;
                break;
            case "676":
                iIDImg = R.drawable.tonga;
                break;
            case "677":
                iIDImg = R.drawable.solomon_islands;
                break;
            case "678":
                iIDImg = R.drawable.vanuatu;
                break;
            case "679":
                iIDImg = R.drawable.fiji;
                break;
            case "680":
                iIDImg = R.drawable.palau;
                break;
            case "681":
                iIDImg = R.drawable.wallis_futuna;
                break;
            case "682":
                iIDImg = R.drawable.cook_islands;
                break;
            case "683":
                iIDImg = R.drawable.niue;
                break;
            case "685":
                iIDImg = R.drawable.samoa;
                break;
            case "686":
                iIDImg = R.drawable.kiribati;
                break;
            case "687":
                iIDImg = R.drawable.new_caledonia;
                break;
            case "688":
                iIDImg = R.drawable.tuvalu;
                break;
            case "689":
                iIDImg = R.drawable.french_polynesia;
                break;
            case "690":
                iIDImg = R.drawable.tokelau;
                break;
            case "691":
                iIDImg = R.drawable.federated_states_micronesia;
                break;
            case "692":
                iIDImg = R.drawable.marshall_islands;
                break;
            case "7":
                iIDImg = R.drawable.russia;
                break;
            case "81":
                iIDImg = R.drawable.japan;
                break;
            case "82":
                iIDImg = R.drawable.south_korea;
                break;
            case "84":
                iIDImg = R.drawable.vietnam;
                break;
            case "850":
                iIDImg = R.drawable.north_korea;
                break;
            case "852":
                iIDImg = R.drawable.hong_kong;
                break;
            case "853":
                iIDImg = R.drawable.macau;
                break;
            case "855":
                iIDImg = R.drawable.cambodia;
                break;
            case "856":
                iIDImg = R.drawable.laos;
                break;
            case "86":
                iIDImg = R.drawable.china;
                break;
            case "880":
                iIDImg = R.drawable.bangladesh;
                break;
            case "886":
                iIDImg = R.drawable.taiwan;
                break;
            case "90":
                iIDImg = R.drawable.turkey;
                break;
            case "90392":
                iIDImg = R.drawable.northern_cyprus;
                break;
            case "91":
                iIDImg = R.drawable.india;
                break;
            case "92":
                iIDImg = R.drawable.pakistan;
                break;
            case "93":
                iIDImg = R.drawable.afghanistan;
                break;
            case "94":
                iIDImg = R.drawable.sri_lanka;
                break;
            case "95":
                iIDImg = R.drawable.burma;
                break;
            case "960":
                iIDImg = R.drawable.maldives;
                break;
            case "961":
                iIDImg = R.drawable.lebanon;
                break;
            case "962":
                iIDImg = R.drawable.jordan;
                break;
            case "963":
                iIDImg = R.drawable.syria;
                break;
            case "964":
                iIDImg = R.drawable.iraq;
                break;
            case "965":
                iIDImg = R.drawable.kuwait;
                break;
            case "966":
                iIDImg = R.drawable.saudi_arabia;
                break;
            case "967":
                iIDImg = R.drawable.yemen;
                break;
            case "968":
                iIDImg = R.drawable.oman;
                break;
            case "970":
                iIDImg = R.drawable.palestinian_territories;
                break;
            case "971":
                iIDImg = R.drawable.united_arab_emirates;
                break;
            case "972":
                iIDImg = R.drawable.israel;
                break;
            case "973":
                iIDImg = R.drawable.bahrain;
                break;
            case "974":
                iIDImg = R.drawable.qatar;
                break;
            case "975":
                iIDImg = R.drawable.bhutan;
                break;
            case "976":
                iIDImg = R.drawable.mongolia;
                break;
            case "977":
                iIDImg = R.drawable.nepal;
                break;
            case "98":
                iIDImg = R.drawable.iran;
                break;
            case "992":
                iIDImg = R.drawable.tajikistan;
                break;
            case "993":
                iIDImg = R.drawable.turkmenistan;
                break;
            case "994":
                iIDImg = R.drawable.azerbaijan;
                break;
            case "995":
                iIDImg = R.drawable.georgia;
                break;
            case "996":
                iIDImg = R.drawable.kyrgyzstan;
                break;
            case "998":
                iIDImg = R.drawable.uzbekistan;
                break;
            case "211":
                iIDImg = R.drawable.south_sudan;
                break;
        }
        return iIDImg;
    }



    public String[] getStringArray(Activity activity,int resId) {
        TypedArray my_string_array = activity.getResources().obtainTypedArray(resId);
        String[] array_string = new String[my_string_array.length()];
        for(int i = 0 ; i < array_string.length ; i++) {
            array_string[i] = my_string_array.getString(i);
        }
        my_string_array.recycle();
        return array_string;
    }

}
