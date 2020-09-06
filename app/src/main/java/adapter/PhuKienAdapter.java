package adapter;

import android.content.Context;
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

import model.Sanpham;

public class PhuKienAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayphukien;
    ArrayList<Sanpham> dataFilter;

    public PhuKienAdapter(Context context, ArrayList<Sanpham> arrayphukien) {
        this.context = context;
        this.arrayphukien = arrayphukien;
        this.dataFilter = arrayphukien;
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
        public TextView txttenphukien, txtgiaphukien, txtmotaphukien;
        public ImageView imgphukien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhuKienAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_phu_kien, null);

            viewHolder.txttenphukien = convertView.findViewById(R.id.textviewphukien);
            viewHolder.txtgiaphukien = convertView.findViewById(R.id.textviewgiaphukien);
            viewHolder.txtmotaphukien = convertView.findViewById(R.id.textviewmotaphukien);
            viewHolder.imgphukien = convertView.findViewById(R.id.imageviewphukien);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PhuKienAdapter.ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttenphukien.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaphukien.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");

        //viewHolder.txtmotaphukien.setText(sanpham.getMotasanpham());

        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imgphukien);
        return convertView;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()) {
                    dataFilter = arrayphukien;
                } else {
                    List<Sanpham> listfilter = new ArrayList<>();
                    for (Sanpham item : arrayphukien) {
                        if (item.getTensanpham().toLowerCase().contains(key)) {
                            listfilter.add(item);
                        }
                    }
                    dataFilter = (ArrayList<Sanpham>) listfilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataFilter = (ArrayList<Sanpham>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
