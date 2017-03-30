package com.classichu.classicimageshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.classichu.imageshow.ImageShowActivity;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.helper.ImageShowDataHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_xxx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ImageShowBean> XX=new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    ImageShowBean imageShowBean=new ImageShowBean();
                    imageShowBean.setTitle("sss"+i+"2121");
                    imageShowBean.setImageUrl("http://cn.bing.com/az/hprichbg/rb/DrizzlyBear_ZH-CN8074606058_1920x1080.jpg");
                    imageShowBean.setContent("dasdsadassadasdasdasdasdasdasdasdasdasdasdas"+i+"dsad");
                    XX.add(imageShowBean);
                }

                ImageShowDataHelper.setDataAndToImageShow(MainActivity.this, XX, 0, true);
            }
        });
    }


}
