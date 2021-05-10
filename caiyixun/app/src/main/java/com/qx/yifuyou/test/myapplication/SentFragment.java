package com.qx.yifuyou.test.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qx.yifuyou.test.data.Package;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SentFragment extends Fragment
{

    private ArrayList<Package> packageList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        Bundle bundle=getArguments();
        packageList=(ArrayList<Package>)bundle.getSerializable("sentPackageList");


        View view = inflater.inflate(R.layout.fragment_sent, container, false);

        RecyclerView rec_recyclerView = view.findViewById(R.id.sen_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rec_recyclerView.setLayoutManager(linearLayoutManager);


        SRVadapter sRVadapter = new SRVadapter(packageList, getActivity());
        rec_recyclerView.setAdapter(sRVadapter);

        // Inflate the layout for this fragment
        return view;
    }
}