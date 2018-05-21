package com.peru.hacknemia;

import android.content.Context;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Juan Luis on 06/01/2016.
 */
public class Config {

    public static final String URL_WEB      = "http://publicidadvr360.com/";
    public static final String URL_JSON     = "http://publicidadvr360.com/json";
    //http://localhost/www.hacknemia.com/app/save.php
    public static final String URL_SAVE     = "http://tabfacil.com/temporal/www.hacknemia.com/app/save.php";
    public static final String URL_FCM_TOKEN     = "http://publicidadvr360.com/fcm_token";

    public static final String SERVER_SUCCESS="server_success";

    public static String TAG_MENSAJE  ="mensaje";
    public static String TAG_SUCCESS  ="success";
    public static void mensaje(Context context,String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }


    public static String truncateCadena(String str, int maxWidth) {
        if (null == str) {
            return null;
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        return str.substring(0, maxWidth) + "...";
    }







    public static BigDecimal truncateDecimal(final double x, final int numberofDecimals) {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_DOWN);
    }


}
