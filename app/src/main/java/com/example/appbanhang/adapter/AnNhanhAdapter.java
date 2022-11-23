package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class AnNhanhAdapter extends RecyclerView.Adapter<AnNhanhAdapter.MyViewHoler> {
    Context context;
    List<SanPhamMoi> array;

    public AnNhanhAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return  new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);
        holder.tensp.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText("Giá" +decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"đ");
        holder.mota.setText(sanPhamMoi.getMota());
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.img_hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{
        TextView tensp, giasp, mota;
        ImageView img_hinhanh;
        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemchitiet_tensp);
            giasp = itemView.findViewById(R.id.itemchitiet_giasp);
            mota = itemView.findViewById(R.id.itemchitiet_mota);
            img_hinhanh = itemView.findViewById(R.id.itemchitiet_img);
        }
    }
}
