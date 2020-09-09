package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.LichSu;

public class LichSuAdapter extends BaseAdapter {
    Context context;
    ArrayList<LichSu> arraylichsu;
    ArrayList<LichSu> dataFilter;

    public LichSuAdapter(Context context, ArrayList<LichSu> arraylichsu) {
        this.context = context;
        this.arraylichsu = arraylichsu;
        this.dataFilter = arraylichsu;
    }

    @Override
    public int getCount() {
        return dataFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return dataFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttensanphamdamua, txtgiasanphamdamua, txtmahoadon, txtsoluongdamua, txttongtien, ngaymua;
        public ImageView imgsanphamdamua;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_lich_su_mua_hang, null);

            viewHolder.txttensanphamdamua = convertView.findViewById(R.id.txttensanphamdamua);
            viewHolder.txtmahoadon = convertView.findViewById(R.id.txtsohoadon);
            viewHolder.txtgiasanphamdamua = convertView.findViewById(R.id.txtgiasanphamdamua);
            viewHolder.txtmahoadon = convertView.findViewById(R.id.txtsohoadon);
            viewHolder.txtsoluongdamua = convertView.findViewById(R.id.txtsoluongsanphamdamua);
            viewHolder.txttongtien = convertView.findViewById(R.id.txtsotiendamua);
            viewHolder.imgsanphamdamua = convertView.findViewById(R.id.imglichsu);
            viewHolder.ngaymua = convertView.findViewById(R.id.ngaymua);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LichSu lichSu = (LichSu) getItem(position);
        viewHolder.txttensanphamdamua.setText(lichSu.getTensanpham());
        viewHolder.txtmahoadon.setText(lichSu.getMadonhang()+"");  //phải cộng thêm xâu nhé vì mã đơn hàng nó là kiểu int
        Log.d("madonhang", lichSu.getMadonhang()+"");
        viewHolder.txtsoluongdamua.setText("Số lượng: " + lichSu.getSoluong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasanphamdamua.setText("Giá: " + decimalFormat.format(lichSu.getGiasanpham() / lichSu.getSoluong()) + " vnd");
        viewHolder.txttongtien.setText("Tổng tiền: " + decimalFormat.format(lichSu.getGiasanpham()) + " vnd");
        viewHolder.ngaymua.setText(lichSu.getNgaymua());


//        viewHolder.txtmotadienthoai.setMaxLines(2);
//        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END); //mo ta qua 2 dong thi co dau 3 cham
        //viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());

        Picasso.get().load(lichSu.getHinhanh())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imgsanphamdamua);
        return convertView;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()) {
                    dataFilter = arraylichsu;
                } else {
                    List<LichSu> listfilter = new ArrayList<>();
                    for (LichSu item : arraylichsu) {
                        if (String.valueOf(item.getMadonhang()).toLowerCase().contains(key)) {
                            listfilter.add(item);
                        }
                    }
                    dataFilter = (ArrayList<LichSu>) listfilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataFilter = (ArrayList<LichSu>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
