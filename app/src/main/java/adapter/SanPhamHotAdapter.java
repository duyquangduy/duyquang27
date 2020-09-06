package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import activity.ChiTietSanPham;
import model.Sanpham;

public class SanPhamHotAdapter extends RecyclerView.Adapter<SanPhamHotAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanPhamHotAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphambanchay, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHotAdapter.ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanphamhot.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanphamhot.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(holder.imghinhanhsanphamhot);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhanhsanphamhot;
        public TextView txttensanphamhot, txtgiasanphamhot;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsanphamhot = itemView.findViewById(R.id.imageviewsanphamhot);
            txtgiasanphamhot = itemView.findViewById(R.id.textviewgiasanphamhot);
            txttensanphamhot = itemView.findViewById(R.id.textviewtensanphamhot);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    CheckConnection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
