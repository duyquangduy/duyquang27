package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import model.Sanpham;

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydienthoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arraydienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttendienthoai, txtgiadienthoai, txtmotadienthoai;
        public ImageView imgdienthoai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dien_thoai, null);

            viewHolder.txttendienthoai = convertView.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = convertView.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgdienthoai = convertView.findViewById(R.id.imageviewdienthoai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham()) + " vnd");

//        viewHolder.txtmotadienthoai.setMaxLines(2);
//        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END); //mo ta qua 2 dong thi co dau 3 cham
        viewHolder.txtmotadienthoai.setText(sanpham.getMotasanpham());

        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(viewHolder.imgdienthoai);
        return convertView;
    }
}
