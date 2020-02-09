package com.example.dostava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dostava.model.User;
import com.example.dostava.ui.tools.ToolsFragment;

import java.util.ArrayList;
import java.util.List;

public class IzmenaActivity extends AppCompatActivity {

    private Button btnIzmeni;
    private EditText editId,editIme,editPrezime,editAdresa,editGrad,editDatum,editTelefon,editSokovi,editCena,editIsporuceno;
    DatabaseHelper mDatabaseHelper;
    ArrayList<User> users=new ArrayList<>();
    List<String> ids=new ArrayList<>();
    User usersIspis=new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena);

        mDatabaseHelper=new DatabaseHelper(this);
        Cursor data=mDatabaseHelper.getListContents();
        /*if (savedInstanceState != null) {
            this. = savedInstanceState.getInt("position");
        }*/
         int id = getIntent().getIntExtra("id", 0);
        if(data.moveToNext()) {
            do {
                User user = new User(Integer.parseInt(data.getString(0)),data.getString(1), data.getString(2), data.getString(3),
                        data.getString(4), data.getString(5), data.getString(6),
                        data.getString(7), data.getString(8), data.getString(9));
                users.add(user);
                ids.add(data.getString(0));
            } while (data.moveToNext());
        }
        for(int i=0;i<users.size();i++){
            if(id==users.get(i).getId()){
                usersIspis=new User(users.get(i));
            }
        }
        editId=findViewById(R.id.edit_id2);
        editId.setHint("Unesite id korisinika: ");
        editId.setText(String.valueOf(usersIspis.getId()));
        editIme= findViewById(R.id.edit_ime2);
        editIme.setHint("Unesite ime korisinika: ");
        editIme.setText(usersIspis.getIme());
        editPrezime=findViewById(R.id.edit_prezime2);
        editPrezime.setHint("Unesite prezime korisinika: ");
        editPrezime.setText(usersIspis.getPrezime());
        editAdresa= findViewById(R.id.edit_adresa2);
        editAdresa.setHint("Unesite adresu korisinika: ");
        editAdresa.setText(usersIspis.getAdresa());
        editGrad= findViewById(R.id.edit_grad2);
        editGrad.setHint("Unesite grad korisinika: ");
        editGrad.setText(usersIspis.getGrad());
        editDatum= findViewById(R.id.edit_datum2);
        editDatum.setHint("Unesite datum za dostavu: ");
        editDatum.setText(usersIspis.getDatum());
        editTelefon= findViewById(R.id.edit_telefon2);
        editTelefon.setHint("Unesite telefon korisinika: ");
        editTelefon.setText(usersIspis.getTelefon());
        editSokovi= findViewById(R.id.edit_sokovi2);
        editSokovi.setHint("Unesite broj sokova: ");
        editSokovi.setText(usersIspis.getSokovi());
        editCena= findViewById(R.id.edit_cena2);
        editCena.setHint("Unesite cenu jedne flase: ");
        editCena.setText(usersIspis.getCena());
        editIsporuceno= findViewById(R.id.edit_isporuceno2);
        editIsporuceno.setHint("Isporuceno? : ");
        editIsporuceno.setText(usersIspis.getDobio());
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
            Toast.makeText(this,"Proverite izmenu zbog id-a",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Nije uspela izmena korisnika",Toast.LENGTH_SHORT).show();
        }
    }
}
