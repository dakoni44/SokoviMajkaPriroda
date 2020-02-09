package com.example.dostava.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.MyAdapter;
import com.example.dostava.R;
import com.example.dostava.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btnOk,btnReset;
    private EditText editIme,editPrezime,editAdresa,editGrad,editDatum,editTelefon,editSokovi,editCena,editIsporuceno;
    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editIme= view.findViewById(R.id.edit_ime);
        editPrezime=view.findViewById(R.id.edit_prezime);
        editAdresa= view.findViewById(R.id.edit_adresa);
        editGrad= view.findViewById(R.id.edit_grad);
        editDatum= view.findViewById(R.id.edit_datum);
        editTelefon= view.findViewById(R.id.edit_telefon);
        editSokovi= view.findViewById(R.id.edit_sokovi);
        editCena= view.findViewById(R.id.edit_cena);
        editIsporuceno= view.findViewById(R.id.edit_isporuceno);
        btnOk=view.findViewById(R.id.button);
        btnReset=view.findViewById(R.id.buttonReset);
        mDatabaseHelper=new DatabaseHelper(getContext());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imeS=editIme.getText().toString();
                String prezimeS=editPrezime.getText().toString();
                String adresaS=editAdresa.getText().toString();
                String gradS=editGrad.getText().toString();
                String datumS=editDatum.getText().toString();
                String telefonS=editTelefon.getText().toString();
                String sokoviS=editSokovi.getText().toString();
                String cenaS=editCena.getText().toString();
                String isporucenoS=editIsporuceno.getText().toString();
                AddData(imeS,prezimeS,adresaS,gradS,datumS,telefonS,sokoviS,cenaS,isporucenoS);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editIme.setText("");
                editPrezime.setText("");
                editAdresa.setText("");
                editGrad.setText("");
                editDatum.setText("");
                editTelefon.setText("");
                editSokovi.setText("");
                editCena.setText("");
                editIsporuceno.setText("");
            }
        });
    }

    public void AddData(String ime, String prezime, String adresa, String grad, String datum, String telefon,
                        String sokovi, String cena, String isporuceno){
        boolean insertData=mDatabaseHelper.addData(ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno);
        if(insertData==true){
            Toast.makeText(getContext(),"Uspesno unet korisnik",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Nije uspelo unosenje korisnika",Toast.LENGTH_SHORT).show();
        }
    }


}