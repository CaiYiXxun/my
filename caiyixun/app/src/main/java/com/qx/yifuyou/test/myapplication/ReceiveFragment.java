package com.qx.yifuyou.test.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.qx.yifuyou.test.data.Package;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiveFragment extends Fragment
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
        packageList=(ArrayList<Package>)bundle.getSerializable("receivePackageList");

        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        RecyclerView rec_recyclerView = view.findViewById(R.id.rec_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rec_recyclerView.setLayoutManager(linearLayoutManager);


        RVadapter rVadapter = new RVadapter(packageList, getActivity());
        rec_recyclerView.setAdapter(rVadapter);

        

        // Inflate the layout for this fragment
        return view;
    }
}