package com.example.dostava;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dostava.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemInfo extends AppCompatActivity {

    public static SimpleDateFormat dtf= new SimpleDateFormat("dd.MM.yyyy.");

    private int position=0;
    String ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno;
    int idI;
    private List<User> users=new ArrayList<>();
    User usersIspis=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_information);


        DatabaseHelper db=new DatabaseHelper(this);
        Cursor cursor=db.getListContents();
       /* if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt("id");
        }*/
         int id = getIntent().getIntExtra("id", 0);
        if(cursor.getCount()==0){
            Toast.makeText(this,"Nema svih podataka za korisnika",Toast.LENGTH_SHORT).show();
        }else{
            if(cursor.moveToNext()) {
                do {
                    idI=Integer.parseInt(cursor.getString(0));
                    ime = cursor.getString(1);
                    prezime = cursor.getString(2);
                    adresa = cursor.getString(3);
                    grad = cursor.getString(4);
                    datum = cursor.getString(5);
                    telefon = cursor.getString(6);
                    sokovi = cursor.getString(7);
                    cena = cursor.getString(8);
                    isporuceno = cursor.getString(9);
                    User user=new User(idI,ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno);
                    users.add(user);
                }while(cursor.moveToNext());
            }
            for(int i=0;i<users.size();i++){
                if(id==users.get(i).getId()){
                   usersIspis=new User(users.get(i));
                }
            }
            TextView tv1 = findViewById(R.id.textView2);
            tv1.setText("Ime: " + usersIspis.getIme());
            TextView tv2 = findViewById(R.id.textView3);
            tv2.setText("Prezime: " + usersIspis.getPrezime());
            TextView tv3 = findViewById(R.id.textView4);
            tv3.setText("Adresa: " + usersIspis.getAdresa());
            TextView tv4 = findViewById(R.id.textView5);
            tv4.setText("Grad: " + usersIspis.getGrad());
            TextView tv9 = findViewById(R.id.textView10);
            tv9.setText("Datum za isporuku: " + usersIspis.getDatum());
            TextView tv5 = findViewById(R.id.textView6);
            tv5.setText("Telefon: " + usersIspis.getTelefon());
            TextView tv6 = findViewById(R.id.textView7);
            tv6.setText("Broj sokova: " + usersIspis.getSokovi());
            TextView tv7 = findViewById(R.id.textView8);
            tv7.setText("Cena 1 soka: " + usersIspis.getCena());
            TextView tv10 = findViewById(R.id.tvZaPlacanje);
            if(usersIspis.getCena().isEmpty() || usersIspis.getSokovi().isEmpty()){
                tv10.setText("Za uplatu: Korisnik dobija besplatno, ili niste uneli sva polja");
            }else{
                tv10.setText("Za uplatu: " + Double.parseDouble(usersIspis.getCena())*Double.parseDouble(usersIspis.getSokovi()));
           }
            TextView tv8 = findViewById(R.id.textView9);
            tv8.setText("Isporuceno: " + usersIspis.getDobio());
        }



    }

}
