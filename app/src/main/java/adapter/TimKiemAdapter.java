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

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public TimKiemAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_tim_kiem_san_pham, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiemAdapter.ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanphamtimkiem.setText(sanpham.getTensanpham());
        holder.txtmotatimkiem.setText(sanpham.getMotasanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanphamtimkiem.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(holder.imghinhanhtimkiem);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imghinhanhtimkiem;
        public TextView txttensanphamtimkiem, txtgiasanphamtimkiem,txtmotatimkiem;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhtimkiem = itemView.findViewById(R.id.imageviewtimkiem);
            txtgiasanphamtimkiem = itemView.findViewById(R.id.textviewgiatimkiem);
            txttensanphamtimkiem = itemView.findViewById(R.id.textviewtentimkiem);
            txtmotatimkiem = itemView.findViewById(R.id.textviewmotatimkiem);

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
