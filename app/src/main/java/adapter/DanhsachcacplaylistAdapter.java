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

import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import activity.DanhsachbaihatActivity;
import model.Playlist;

public class DanhsachcacplaylistAdapter extends RecyclerView.Adapter<DanhsachcacplaylistAdapter.ViewHolder> {

    Context context;
    ArrayList<Playlist> mangplaylist;

    public DanhsachcacplaylistAdapter(Context context, ArrayList<Playlist> mangplaylist) {
        this.context = context;
        this.mangplaylist = mangplaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_cac_playlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mangplaylist.get(position);
        Picasso.with(context).load(playlist.getHinhPlaylist()).into(holder.imghinhnen);
        holder.txttenplaylist.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return mangplaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imghinhnen;
        TextView txttenplaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhnen = itemView.findViewById(R.id.imageviewdanhsachcacplaylist);
            txttenplaylist = itemView.findViewById(R.id.textviewtendanhsachcacplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
//                    for (int i = 0; i < mangplaylist.size() ; i++) {
//                        intent.putExtra("itemplaylist",mangplaylist.get(i));  //name lay trong DataInten cua DanhsachbaihatActivity
//                    }
                    intent.putExtra("itemplaylist",mangplaylist.get(getPosition()));
                    context.startActivity(intent);  //neu loi thi xem lai video 36
                }
            });
        }
    }
}
