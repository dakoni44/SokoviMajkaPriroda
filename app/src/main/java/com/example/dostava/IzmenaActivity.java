package com.example.dostava;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dostava.model.User;

import java.util.ArrayList;

public class IzmenaActivity extends AppCompatActivity {

    private Button btnIzmeni;
    private EditText editId,editIme,editPrezime,editAdresa,editGrad,editDatum,editTelefon,editSokovi,editCena,editIsporuceno;
    DatabaseHelper mDatabaseHelper;
    ArrayList<User> users=new ArrayList<>();
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena);

        mDatabaseHelper=new DatabaseHelper(this);
        Cursor data=mDatabaseHelper.getListContents();
        if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt("position");
        }
        final int position = getIntent().getIntExtra("position", 0);
        if(data.moveToNext()) {
            do {
                User user = new User(data.getString(1), data.getString(2), data.getString(3),
                        data.getString(4), data.getString(5), data.getString(6),
                        data.getString(7), data.getString(8), data.getString(9),
                        data.getString(10));
                users.add(user);
            } while (data.moveToNext());
        }
        editId=findViewById(R.id.edit_id2);
        editId.setHint("Unesite id korisinika: ");
        editId.setText(users.get(position).getId());
        editIme= findViewById(R.id.edit_ime2);
        editIme.setHint("Unesite ime korisinika: ");
        editIme.setText(users.get(position).getIme());
        editPrezime=findViewById(R.id.edit_prezime2);
        editPrezime.setHint("Unesite prezime korisinika: ");
        editPrezime.setText(users.get(position).getPrezime());
        editAdresa= findViewById(R.id.edit_adresa2);
        editAdresa.setHint("Unesite adresu korisinika: ");
        editAdresa.setText(users.get(position).getAdresa());
        editGrad= findViewById(R.id.edit_grad2);
        editGrad.setHint("Unesite grad korisinika: ");
        editGrad.setText(users.get(position).getGrad());
        editDatum= findViewById(R.id.edit_datum2);
        editDatum.setHint("Unesite datum za dostavu: ");
        editDatum.setText(users.get(position).getDatum());
        editTelefon= findViewById(R.id.edit_telefon2);
        editTelefon.setHint("Unesite telefon korisinika: ");
        editTelefon.setText(users.get(position).getTelefon());
        editSokovi= findViewById(R.id.edit_sokovi2);
        editSokovi.setHint("Unesite broj sokova: ");
        editSokovi.setText(users.get(position).getSokovi());
        editCena= findViewById(R.id.edit_cena2);
        editCena.setHint("Unesite cenu jedne flase: ");
        editCena.setText(users.get(position).getCena());
        editIsporuceno= findViewById(R.id.edit_isporuceno2);
        editIsporuceno.setHint("Isporuceno? : ");
        editIsporuceno.setText(users.get(position).getDobio());
        btnIzmeni=findViewById(R.id.button2);
        mDatabaseHelper=new DatabaseHelper(this);
        btnIzmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idS=editId.getText().toString();
                String imeS=editIme.getText().toString();
                String prezimeS=editPrezime.getText().toString();
                String adresaS=editAdresa.getText().toString();
                String gradS=editGrad.getText().toString();
                String datumS=editDatum.getText().toString();
                String telefonS=editTelefon.getText().toString();
                String sokoviS=editSokovi.getText().toString();
                String cenaS=editCena.getText().toString();
                String isporucenoS=editIsporuceno.getText().toString();
                UpdateData(idS,imeS,prezimeS,adresaS,gradS,datumS,telefonS,sokoviS,cenaS,isporucenoS);
            }
        });
    }
    private void UpdateData(String id, String ime, String prezime, String adresa, String grad, String datum, String telefon,
                            String sokovi, String cena, String isporuceno){
        boolean updateData=mDatabaseHelper.updateData(id,ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno);
        if(updateData==true){
            Toast.makeText(this,"Uspesno imenjen korisnik",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Nije uspela izmena korisnika",Toast.LENGTH_SHORT).show();
        }
    }
}
