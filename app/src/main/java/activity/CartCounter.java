package activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.appbanhang.R;

public class CartCounter {
    CardView cardView;
    ImageView img;
    private TextView cartnumber;

    public CartCounter(View view) {
        cartnumber = view.findViewById(R.id.number);
        cardView = view.findViewById(R.id.cartnumber);
        img = view.findViewById(R.id.imgcart);
    }

    public void setText(int sl) {
        if (sl < 1) {
            cardView.setVisibility(View.INVISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cartnumber.setText(String.valueOf(sl));
        }
    }
}
