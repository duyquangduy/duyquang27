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
    private final int Max_number = 20;
    private final int Min_number = 0;
    private int cart_number_counter = 0;

    public CartCounter(View view){
        cartnumber = view.findViewById(R.id.number);
        cardView = view.findViewById(R.id.cartnumber);
        img = view.findViewById(R.id.imgcart);
    }

//    public void increaseNumber(){
//        cart_number_counter++;
//        if(cart_number_counter > Max_number){
//            Log.d("Counter", "Max number reached");
//        }else {
//            cardView.setVisibility(View.VISIBLE);
//            cartnumber.setText(String.valueOf(cart_number_counter));
//        }
//    }
//
//    public void decreaseNumber(){
//        cart_number_counter--;
//        if(cart_number_counter < Max_number){
//            Log.d("Counter", "Min number reached");
//        }else {
//            cardView.setVisibility(View.INVISIBLE);
//            cartnumber.setText(String.valueOf(cart_number_counter));
//        }
//    }

    public void setText(int sl){
        if (sl < 1){
            cardView.setVisibility(View.INVISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            cartnumber.setText(String.valueOf(sl));
        }
    }
}
