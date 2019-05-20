package com.example.qrproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonScan;
    private TextView textViewId,textViewName,textViewPrice,textViewDescription;
    private ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonScan=findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonScan_onClick(view);
            }
        });
        textViewId=findViewById(R.id.textViewId);
        textViewName=findViewById(R.id.textViewName);
        textViewPrice=findViewById(R.id.textViewPrice);
        textViewDescription=findViewById(R.id.textViewDescription);
        imageViewPhoto=findViewById(R.id.imageViewPhoto);
    }

    private void buttonScan_onClick(View view) {
        IntentIntegrator intentIntegrator=new IntentIntegrator( this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){

            String id=intentResult.getContents();
            ProductAPI productAPI=APIClient.getClient().create(ProductAPI.class);
            productAPI.find(id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()) {


                        try {
                            Product product=response.body();
                            textViewId.setText(product.getId());
                            textViewName.setText(product.getName());
                            textViewPrice.setText(String.valueOf(product.getPrice()));
                            textViewDescription.setText(product.getDescription());
                            Bitmap  bitmap = new ImageRequestAsk().execute(product.getPhoto()).get();
                            imageViewPhoto.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class ImageRequestAsk extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String...params) {

            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }

        }
    }

}
