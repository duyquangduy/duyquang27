package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.TinTuc;

public class TinTucAdapter extends ArrayAdapter<TinTuc> {

    public TinTucAdapter(Context context, int resource, List<TinTuc> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_doc_bao, null);
        }
        TinTuc docBao = getItem(position);
        if (docBao != null) {
            // Anh xa + Gan gia tri
            TextView txtTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            txtTitle.setText(docBao.getTitle());
            ImageView imageView = convertView.findViewById(R.id.imageView);
            //add ham picaso trong build.gradle module
            Picasso.get().load(docBao.image).into(imageView);
        }
        return convertView;
    }

}
