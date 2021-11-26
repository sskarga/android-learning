package com.sskarga.downloadwebcontent;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.sskarga.downloadwebcontent.streamsdata.IStreamData;
import com.sskarga.downloadwebcontent.streamsdata.StreamDataBitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Request method GET. The value must be uppercase.
    private static final String REQUEST_METHOD_GET = "GET";

    private final String urlDownload = "https://most-beauty.ru/people/samye-krasivye-aktrisy-rossii.html";
    final Handler handler = new Handler(Looper.getMainLooper());

    private ImageView imageView;

    private ArrayList<String> actors;
    private ArrayList<String> foto;
    private ArrayList<Button> buttonsQuestion;

    private int questionNumber;
    private int questionAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        buttonsQuestion = new ArrayList<>();
        buttonsQuestion.add(findViewById(R.id.btn1));
        buttonsQuestion.add(findViewById(R.id.btn2));
        buttonsQuestion.add(findViewById(R.id.btn3));
        buttonsQuestion.add(findViewById(R.id.btn4));

        for (int i = 0; i < buttonsQuestion.size(); i++) {
            buttonsQuestion.get(i).setOnClickListener(this::onClickButtonAnswer);
        }

        actors = new ArrayList<>();
        foto = new ArrayList<>();

        if(savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("image");
            imageView.setImageBitmap(bitmap);

            actors = savedInstanceState.getStringArrayList("actors");
            foto = savedInstanceState.getStringArrayList("foto");
            questionNumber = savedInstanceState.getInt("questionNumber");
            questionAnswer = savedInstanceState.getInt("questionAnswer");

            ArrayList<String> btnArr;
            btnArr = savedInstanceState.getStringArrayList("buttons");
            for (int i = 0; i < buttonsQuestion.size(); i++) {
                buttonsQuestion.get(i).setText(btnArr.get(i));
            }
        } else {
            Thread thread = new Thread(() -> {
                getContent(urlDownload);
                handler.post(this::startGame);
            });
            thread.start();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("actors", actors);
        outState.putStringArrayList("foto", foto);
        outState.putInt("questionNumber", questionNumber);
        outState.putInt("questionAnswer", questionAnswer);

        ArrayList<String> btnArr = new ArrayList<>();
        for (int i = 0; i < buttonsQuestion.size(); i++) {
            btnArr.add(buttonsQuestion.get(i).getText().toString());
        }
        outState.putStringArrayList("buttons", btnArr);

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        outState.putParcelable("image", bitmap);
    }


    private void startGame() {
        generateQuestion();

        Thread threadImg = new Thread(() -> {
            Bitmap img = (Bitmap) downloadData(foto.get(questionNumber), new StreamDataBitmap());
            if (img != null) {
                handler.post(() -> imageView.setImageBitmap(img));
            }
        });
        threadImg.start();

        for (int i = 0; i < buttonsQuestion.size(); i++) {
            if (i == questionAnswer) {
                buttonsQuestion.get(i).setText(actors.get(questionNumber));
            } else {
                buttonsQuestion.get(i).setText(actors.get(generateWrongAnswer()));
            }
        }
    }

    private void generateQuestion() {
        questionNumber = (int) (Math.random() * actors.size());
        questionAnswer = (int) (Math.random() * buttonsQuestion.size());
    }

    private int generateWrongAnswer() {
        return (int) (Math.random() * actors.size());
    }

    private void getContent(String dataUrl) {
        try {
            Document doc = Jsoup.connect(dataUrl).get();
            Elements listImg = doc.select(".alignnone");

            actors.clear();
            foto.clear();

            for (Element element : listImg) {
                String strImg = element.attr("src");
                foto.add(strImg);

                String artist = element.attr("alt");
                actors.add(artist);

                Log.i("Url data", artist + " -> " + strImg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickButtonAnswer(View view) {

        Button button = (Button) view;
        Integer answer = Integer.parseInt(button.getTag().toString()) - 1;
        if (answer.equals(questionAnswer)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true)
                    .setPositiveButton(R.string.btn_dialog_ok, (dialogInterface, i) -> {
                        handler.post(() -> startGame());
                        dialogInterface.cancel();
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle(getString(R.string.answer_is_right));
            alertDialog.setIcon(R.drawable.success_icon);
            alertDialog.show();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(actors.get(questionAnswer))
                    .setCancelable(true)
                    .setPositiveButton(R.string.btn_dialog_ok, (dialogInterface, i) -> {
                        handler.post(() -> startGame());
                        dialogInterface.cancel();
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle(getString(R.string.answer_is_wrong));
            alertDialog.setIcon(R.drawable.wron_icon);
            alertDialog.show();
        }



    }

    private Object downloadData(String dataUrl, IStreamData streamData) {
        Object result = null;
        HttpURLConnection httpConn = null;
        try {
            httpConn = (HttpURLConnection) new URL(dataUrl).openConnection();
            // Set http request method to get.
            httpConn.setRequestMethod(REQUEST_METHOD_GET);
            // Set connection timeout and read timeout value.
            httpConn.setConnectTimeout(5000);
            httpConn.setReadTimeout(5000);
            httpConn.connect();

            int code = httpConn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new IOException("Invalid response from server: " + code);
            }

            InputStream inputStream = httpConn.getInputStream();
            result = streamData.getData(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

        return result;
    }

}