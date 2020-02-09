package com.example.dostava.ui.send;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    Button btnDelete;
    DatabaseHelper mDataBaseHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBaseHelper=new DatabaseHelper(getContext());
        btnDelete=view.findViewById(R.id.buttonDelete);
        btnDelete.setText("Obrisi listu");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_obrisil).setTitle("Brisanje liste")
                        .setMessage("Potvrdite brisanje liste")
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean deleted=mDataBaseHelper.deleteAllData();
                                if(deleted){
                                    Toast.makeText(getContext(),"Usesno je obrisana lista",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(),"Lista neuspesno obrisana",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Ne",null)

                        .show();
            }
        });
    }
}