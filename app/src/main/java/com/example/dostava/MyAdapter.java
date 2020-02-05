package com.example.dostava;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dostava.model.User;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter {

    List list=new ArrayList();


    public MyAdapter(Context context, int resource) {
        super(context,resource);;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public User getItem(int position) {
        return (User) list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class LayoutHandler{
        TextView id,ime,prezime,adresa,grad,datum,telefon,sokovi,cena,isporuceno;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        LayoutHandler layoutHandler=null;
        if(row==null){
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.my_single_item,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.ime=row.findViewById(R.id.tvName);
            layoutHandler.grad=row.findViewById(R.id.tvDescription);
            row.setTag(layoutHandler);
        }
       User user=(User)this.getItem(position);
        layoutHandler.ime.setText(user.getIme()+" "+user.getPrezime());
        layoutHandler.grad.setText(user.getGrad());


        return row;



    }
}
