package com.example.dostava.ui.max;

import androidx.lifecycle.ViewModelProviders;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.R;
import com.example.dostava.model.User;

import java.util.ArrayList;
import java.util.List;

public class MaxFragment extends Fragment {

    private MaxViewModel mViewModel;
    DatabaseHelper mDatabaseHelper;
    Cursor cursor;
    String ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno;
    int idI;
    List<User> users=new ArrayList<>();
    List<Double> cene=new ArrayList<>();
    TextView tvMaxId,tvMaxIme,tvMaxPrezime,tvMaxAdresa,tvMaxGrad,tvMaxDatum,
            tvMaxTelefon,tvMaxSokovi,tvMaxCena,tvMaxIsporuceno,tvMaxIsplata;

    public static MaxFragment newInstance() {
        return new MaxFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.max_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MaxViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseHelper=new DatabaseHelper(getContext());
        cursor=mDatabaseHelper.getListContents();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(),"Trenutno nema korisnika",Toast.LENGTH_SHORT).show();
        }else {
            if (cursor.moveToNext()) {
                do {
                    idI = Integer.parseInt(cursor.getString(0));
                    ime = cursor.getString(1);
                    prezime = cursor.getString(2);
                    adresa = cursor.getString(3);
                    grad = cursor.getString(4);
                    datum = cursor.getString(5);
                    telefon = cursor.getString(6);
                    sokovi = cursor.getString(7);
                    cena = cursor.getString(8);
                    isporuceno = cursor.getString(9);
                    User user = new User(idI, ime, prezime, adresa, grad, datum, telefon, sokovi, cena, isporuceno);
                    users.add(user);
                } while (cursor.moveToNext());
            }
            // }

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getCena().isEmpty() || users.get(i).getSokovi().isEmpty()) {
                    continue;
                } else {
                    cene.add(Double.parseDouble(users.get(i).getSokovi()) * Double.parseDouble(users.get(i).getCena()));
                }
            }
            Double maxCena = 0.0;
            if (cene.size() > 0) {
                maxCena = cene.get(0);
            }
            for (int i = 0; i < cene.size(); i++) {
                if (maxCena < cene.get(i)) {
                    maxCena = cene.get(i);
                }
            }
            User user2 = new User();
            if (cene.size() > 0) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getCena().isEmpty() || users.get(i).getSokovi().isEmpty()) {
                        continue;
                    }
                    if (Double.parseDouble(users.get(i).getSokovi()) * Double.parseDouble(users.get(i).getCena()) == maxCena) {
                        user2 = new User(users.get(i));
                    }
                }

                    tvMaxId = view.findViewById(R.id.tvMaxId);
                tvMaxIme = view.findViewById(R.id.tvMaxIme);
                tvMaxPrezime = view.findViewById(R.id.tvMaxPrezime);
                tvMaxAdresa = view.findViewById(R.id.tvMaxAdresa);
                tvMaxGrad = view.findViewById(R.id.tvMaxGrad);
                tvMaxDatum = view.findViewById(R.id.tvMaxDatum);
                tvMaxTelefon = view.findViewById(R.id.tvMaxTelefon);
                tvMaxSokovi = view.findViewById(R.id.tvMaxSokovi);
                tvMaxCena = view.findViewById(R.id.tvMaxCena);
                tvMaxIsplata = view.findViewById(R.id.tvMaxIsplata);
                tvMaxIsporuceno = view.findViewById(R.id.tvMaxIsporuceno);
                tvMaxId.setText("Id: " + user2.getId());
                tvMaxIme.setText("Ime: " + user2.getIme());
                tvMaxPrezime.setText("Prezime: " + user2.getPrezime());
                tvMaxAdresa.setText("Adresa: " + user2.getAdresa());
                tvMaxGrad.setText("Grad: " + user2.getGrad());
                tvMaxDatum.setText("Datum: " + user2.getDatum());
                tvMaxTelefon.setText("Telefon: " + user2.getTelefon());
                tvMaxSokovi.setText("Broj sokova: " + user2.getSokovi());
                tvMaxCena.setText("Cena 1 soka: " + user2.getCena());
                tvMaxIsplata.setText("Za isplatu: " + Double.parseDouble(user2.getSokovi()) * Double.parseDouble(user2.getCena()));
                tvMaxIsporuceno.setText("Isporuceno?: " + user2.getDobio());

                } else {
                    Toast.makeText(getContext(), "Pogledajte polja \"Cena 1 soka\" ili \"Broj sokova\"", Toast.LENGTH_SHORT).show();
                }
            }



    }
}
