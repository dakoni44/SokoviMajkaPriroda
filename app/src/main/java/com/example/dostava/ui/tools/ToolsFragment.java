package com.example.dostava.ui.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dostava.DatabaseHelper;
import com.example.dostava.ItemInfo;
import com.example.dostava.IzmenaActivity;
import com.example.dostava.R;
import com.example.dostava.model.User;

import java.util.ArrayList;
import java.util.List;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    DatabaseHelper mDatabaseHelper;
    ArrayList<String> users=new ArrayList<>();
    ListView lwItems;
    SearchView searchView;
    List<User> users2=new ArrayList<>();
    List<User> users4=new ArrayList<>();
    ListAdapter adapter;
    Cursor data;
    ArrayList<String> users3;
   // ArrayList<Integer> pozicija=new ArrayList<>();
    ListView lwItems2;
   // ArrayList<Integer> pozicijaProvera=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        toolsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchView=view.findViewById(R.id.item_search);
        lwItems = view.findViewById(R.id.lvItems3);
        mDatabaseHelper=new DatabaseHelper(getContext());
        lwItems2=view.findViewById(R.id.lvItems5);
       lwItems2.invalidateViews();

        data=mDatabaseHelper.getListContents();
        if(data.getCount()==0){
            Toast.makeText(getContext(),"Trenutno nema korisnika",Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                users.add(data.getString(0)+" "+data.getString(1)+" "+data.getString(2)+" | "+
                        data.getString(4)+" | "+data.getString(5));
                User user=new User(Integer.parseInt(data.getString(0)),data.getString(1),data.getString(2),data.getString(3),
                        data.getString(4),data.getString(5),data.getString(6),
                        data.getString(7),data.getString(8),data.getString(9));
                users2.add(user);
                adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,users){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                       // pozicija.add(position);
                        View view= super.getView(position, convertView, parent);
                        if(users2.get(position).getDobio().toLowerCase().startsWith("d") ||
                                users2.get(position).getDobio().toLowerCase().startsWith("y")
                                || users2.get(position).getDobio().toLowerCase().startsWith("j")){
                            view.setBackgroundColor(getResources().getColor(R.color.colorDa));
                        }else if(users2.get(position).getDobio().toLowerCase().startsWith("n") ||
                                users2.get(position).getDobio().isEmpty()
                        ){
                            view.setBackgroundColor(getResources().getColor(R.color.colorNe));
                        }
                        return view;
                    }
                };
                lwItems.setAdapter(adapter);
            }
        }

        lwItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), ItemInfo.class);
                i.putExtra("id", users2.get(position).getId());
                startActivity(i);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                users3 = new ArrayList<>();
                users4.clear();
                for(int i=0;i<users.size();i++){
                    String user = users.get(i);
                   // int pozicijaI=pozicija.get(i);
                    if(user.toLowerCase().contains(newText.toLowerCase())){
                        users3.add(user);
                        users4.add(users2.get(i));
                       // pozicijaProvera.add(pozicijaI);
                    }

                }

                adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,users3){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view= super.getView(position, convertView, parent);
                        if(users4.get(position).getDobio().toLowerCase().startsWith("d") ||
                                users4.get(position).getDobio().toLowerCase().startsWith("y")
                                || users4.get(position).getDobio().toLowerCase().startsWith("j")){
                            view.setBackgroundColor(getResources().getColor(R.color.colorDa));
                        }else if(users4.get(position).getDobio().toLowerCase().startsWith("n") ||
                                users4.get(position).getDobio().isEmpty()
                        ){
                            view.setBackgroundColor(getResources().getColor(R.color.colorNe));
                        }
                        return view;
                    }
                };
                lwItems2.setAdapter(adapter);
                lwItems2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        final int which_item=position;
                        new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_isporuceno).setTitle("Potvrdi isporuku")
                                .setMessage("Korisnik koji je dobiodostavu ce se oznaciti zelenom bojom")
                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        users4.get(position).setDobio("Jeste");
                                       String id=String.valueOf(users4.get(position).getId());
                                        mDatabaseHelper.updateData(id,users4.get(position).getIme(),
                                                users4.get(position).getPrezime(),users4.get(position).getAdresa(),users4.get(position).getGrad(),
                                                users4.get(position).getDatum(),users4.get(position).getTelefon(),users4.get(position).getSokovi(),
                                                users4.get(position).getCena(),users4.get(position).getDobio());
                                                lwItems.invalidateViews();
                                                lwItems2.invalidateViews();
                                    }
                                })
                                .setNegativeButton("Ne",null)
                                .setNeutralButton("Izmena podataka", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getContext(), IzmenaActivity.class);
                                        i.putExtra("id",users4.get(position).getId());
                                        startActivity(i);
                                    }
                                })
                                .show();
                        return true;
                    }

                });
                return true;
            }
        });
        lwItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int which_item=position;
                new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_isporuceno).setTitle("Potvrdi isporuku")
                        .setMessage("Korisnik koji je dobio dostavu ce se oznaciti zelenom bojom")
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                users2.get(position).setDobio("Jeste");
                                String id=String.valueOf(users2.get(position).getId());
                                mDatabaseHelper.updateData(id,users2.get(position).getIme(),
                                        users2.get(position).getPrezime(),users2.get(position).getAdresa(),users2.get(position).getGrad(),
                                        users2.get(position).getDatum(),users2.get(position).getTelefon(),users2.get(position).getSokovi(),
                                        users2.get(position).getCena(),users2.get(position).getDobio());
                                    lwItems.invalidateViews();
                                    lwItems2.invalidateViews();
                            }
                        })
                        .setNegativeButton("Ne",null)
                        .setNeutralButton("Izmena podataka", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getContext(), IzmenaActivity.class);
                                i.putExtra("id", users2.get(position).getId());
                                startActivity(i);
                            }
                        })
                        .show();
                return true;
            }

            });
        lwItems2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), ItemInfo.class);
                i.putExtra("id", users4.get(position).getId());
                startActivity(i);
            }
        });

    }

}



