package com.example.julen.starwarsinvaders.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.julen.starwarsinvaders.R;


import java.util.List;

/**
 * Created by jonur on 2018/5/9.
 */

public class AdapterActivity extends BaseAdapter {

    private Context mContext;
    private List<Puntuazioa> mPuntuazioaList;

    public AdapterActivity(Context mContext, List<Puntuazioa> mPuntuazioaList) {
        this.mContext = mContext;
        this.mPuntuazioaList = mPuntuazioaList;
    }

    @Override
    public int getCount() {
        return mPuntuazioaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPuntuazioaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = View.inflate(mContext,R.layout.activity_adapter,null);

        Puntuazioa dir = mPuntuazioaList.get(position);

        TextView rank=(TextView)v.findViewById(R.id.topZenbakia);
        TextView izena = (TextView) v.findViewById(R.id.jokalariIzena);
        TextView puntuazioa =(TextView)v.findViewById(R.id.puntuazioa);
        rank.setText(dir.getZenbakia());
        izena.setText(dir.getIzena());
        puntuazioa.setText(String.valueOf(dir.getPuntuak()));

        return v;
    }
}
