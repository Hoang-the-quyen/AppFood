package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.appbanhang.R;
import com.example.appbanhang.Utils.Utils;
import com.example.appbanhang.adapter.MonChinhAdapter;
import com.example.appbanhang.adapter.SanPhamMoiAdapter;
import com.example.appbanhang.model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MonChinhActivity extends AppCompatActivity {
    Toolbar toolbarmonchinh;
    RecyclerView recyclerViewmonchinh;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int loai;
    MonChinhAdapter adapterMonC;
    List<SanPhamMoi> sanPhamMoiList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_chinh);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionBar();
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiBanHang.getSanPham(loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                sanPhamMoiList=sanPhamMoiModel.getResult();
                                adapterMonC = new MonChinhAdapter(getApplicationContext(), sanPhamMoiList);
                                recyclerViewmonchinh.setAdapter(adapterMonC);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "Không kết nối được với sever", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbarmonchinh);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void AnhXa() {
        toolbarmonchinh = findViewById(R.id.toolbarmonchinh);
        recyclerViewmonchinh = findViewById(R.id.recyclerviewmonchinh);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewmonchinh.setLayoutManager(layoutManager);
        recyclerViewmonchinh.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}