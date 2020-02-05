package com.example.dostava.ui.gallery;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.ItemInfo;
import com.example.dostava.MyAdapter;
import com.example.dostava.R;
import com.example.dostava.model.User;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    public static SimpleDateFormat dtf = new SimpleDateFormat("dd.MM.yyyy.");
    DatabaseHelper mDatabaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lwItems = view.findViewById(R.id.lwItems);
        mDatabaseHelper=new DatabaseHelper(getContext());
        ArrayList<String> imena=new ArrayList<>();
        Cursor data=mDatabaseHelper.getListContents();
        if(data.getCount()==0){
            Toast.makeText(getContext(),"The data is empty",Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                imena.add(data.getString(1)+" "+data.getString(2)+" "+data.getString(3)+" | "+
                        data.getString(5));
                ListAdapter adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,imena);
                lwItems.setAdapter(adapter);
            }
        }
        lwItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),ItemInfo.class);
                i.putExtra("position", position);
                startActivity(i);
            }
        });
    }
}

