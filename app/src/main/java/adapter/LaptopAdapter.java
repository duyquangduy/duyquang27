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

import model.Sanpham;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;
    ArrayList<Sanpham> dataFilter;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
        this.dataFilter = arraylaptop;
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
        public TextView txttenlaptop, txtgialaptop, txtmotalaptop;
        public ImageView imglaptop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop, null);

            viewHolder.txttenlaptop = convertView.findViewById(R.id.textviewtenlaptop);
            viewHolder.txtgialaptop = convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop = convertView.findViewById(R.id.imageviewlaptop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");

//        viewHolder.txtmotadienthoai.setMaxLines(2);
//        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END); //mo ta qua 2 dong thi co dau 3 cham
        viewHolder.txtmotalaptop.setText(sanpham.getMotasanpham());

        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imglaptop);
        return convertView;
    }
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    dataFilter = arraylaptop;
                }else {
                    List<Sanpham> listfilter = new ArrayList<>();
                    for(Sanpham item : arraylaptop){
                        if(item.getTensanpham().toLowerCase().contains(key)){
                            Log.d("AAA",item.getTensanpham()+" - "+key);
                            listfilter.add(item);
                        }
                    }
                    dataFilter = (ArrayList<Sanpham>) listfilter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFilter;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataFilter = (ArrayList<Sanpham>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
