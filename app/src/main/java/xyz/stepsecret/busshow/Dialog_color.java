package xyz.stepsecret.busshow;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

/**
 * Created by stepsecret on 20/2/2559.
 */
public class Dialog_color extends FragmentActivity {

    private RelativeLayout re;
    private LobsterPicker lobsterPicker;
    private LobsterShadeSlider shadeSlider;
    private LobsterOpacitySlider opacitySlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color);

        lobsterPicker = (LobsterPicker) findViewById(R.id.lobsterpicker);
        shadeSlider = (LobsterShadeSlider) findViewById(R.id.shadeslider);
        opacitySlider = (LobsterOpacitySlider) findViewById(R.id.opacityslider);

        re = (RelativeLayout) findViewById(R.id.RelativeLayout5);

        lobsterPicker.addDecorator(shadeSlider);
        lobsterPicker.addDecorator(opacitySlider);

        lobsterPicker.addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {

                Log.e("TAG", "onColorChanged : " + lobsterPicker.getColor());
                re.setBackgroundColor(lobsterPicker.getColor());

            }

            @Override
            public void onColorSelected(@ColorInt int color) {

                Log.e("TAG", "onColorSelected : " + lobsterPicker.getColor());

            }
        });

    }



}
