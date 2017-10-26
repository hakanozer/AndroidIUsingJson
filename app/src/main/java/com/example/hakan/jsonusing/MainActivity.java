package com.example.hakan.jsonusing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    EditText tUser, tPass;
    static String adiSoyadi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tUser = (EditText) findViewById(R.id.txtUserName);
        tPass = (EditText) findViewById(R.id.txtUserPassword);

    }

    // butona tıklanıldığında yapılacak işlemler
    public void btnCalis(View v) {
        String userName = tUser.getText().toString();
        String userPass = tPass.getText().toString();

        if(userName.equals("")) {
            Toast.makeText(this, "Lütfen kullanıcı adını giriniz !", Toast.LENGTH_SHORT).show();
            tUser.requestFocus();
        }else if (userPass.equals("")) {
            Toast.makeText(this, "Lütfen şifrenizi yazınız !", Toast.LENGTH_SHORT).show();
            tPass.requestFocus();
        }else if (userPass.length() <= 4 ){
            Toast.makeText(this, "Şifre 4 karakterden fazla olmalı", Toast.LENGTH_SHORT).show();
        } else {
            // sunucuya data gönderilebilir.

            // http://jsonbulut.com/json/userLogin.php?ref=c539de7cf7ab8f5eeb54ea3aaf93f727&userEmail=b@b.com&userPass=123456&face=no
            String url = "http://jsonbulut.com/json/userLogin.php?ref=c539de7cf7ab8f5eeb54ea3aaf93f727&userEmail="+userName+"&userPass="+userPass+"&face=no";
            new kullaniciGiris(url).execute();
        }

        //Toast.makeText(this, "Uyarı Penceresi Açıldı " + userName, Toast.LENGTH_SHORT).show();
    }


    class kullaniciGiris extends AsyncTask<Void,Void, Void> {

        String url = "";
        String data = "";
        public kullaniciGiris(String url){
            this.url = url;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                JSONObject obj = new JSONObject(data);
                boolean durum = obj.getJSONArray("user").getJSONObject(0).getBoolean("durum");
                Log.d("Durum", ""+durum);
                String mesaj = obj.getJSONArray("user").getJSONObject(0).getString("mesaj");
                if (durum == true) {
                    JSONObject ob = obj.getJSONArray("user").getJSONObject(0).getJSONObject("bilgiler");
                    MainActivity.adiSoyadi = ob.getString("userName") + " " + ob.getString("userSurname");
                    Toast.makeText(MainActivity.this, "Başarılı Giriş Yaptınız ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Profil.class);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this, mesaj , Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                data = Jsoup.connect(url).ignoreContentType(true).execute().body();
            }catch (Exception ex) {
                System.err.println("Hata : " + ex);
            }
            return null;
        }
    }

}
