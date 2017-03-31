package binaron.com.httpurl;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String[] token_high = new String[100];
    String[] token_medium = new String[100];
    String[] token_low = new String[100];
    String[] high = new String[20];
    String[] medium = new String[20];
    String[] low = new String[20];

    String[][] video = new String[4][20];
    int index = 0;
    //String[] video_medium = new String[20];

    int j =0;
    int count = 0;
    Button High;
    Button Medium;
    Button Low;
    VideoView mVideoView;
    MediaController mediaC;
  //  private ArrayList<String> videolist = new ArrayList<String>();
   // private ArrayList<String> videolist_1 = new ArrayList<String>();

    Handler uiHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiHandler = new Handler();
        High = (Button) findViewById(R.id.high);
        Medium = (Button) findViewById(R.id.medium);
        Low = (Button) findViewById(R.id.low);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mediaC = new MediaController(this);

        High.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                index = 0;
            }
        });

        Medium.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                index = 1;
            }
        });

        Low.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                index = 2;
            }
        });




        Thread httpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder content_high = new StringBuilder();
                StringBuilder content_medium = new StringBuilder();
                StringBuilder content_low = new StringBuilder();

                //high
                try {
                    // wait for 3 seconds
                    Thread.sleep(3000);

                    URL url = new URL("http://192.168.1.67/series/upload/highs.m3u8");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // Timeout for reading InputStream arbitrarily set to 3000ms.
                    connection.setReadTimeout(9000);
                    // Timeout for connection.connect() arbitrarily set to 3000ms.
                    connection.setConnectTimeout(9000);
                    // For this use case, set HTTP method to GET.
                    connection.setRequestMethod("GET");
                    // Already true by default but setting just in case; needs to be true since this request
                    // is carrying an input (response) body.
                    connection.setDoInput(true);
                    // Open communications link (network traffic occurs here).
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpsURLConnection.HTTP_OK) {
                        Log.d(TAG, "HTTP connection error");
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    int m = 0;
                    // read from the urlconnection via the bufferedreader
                    while ((line = reader.readLine()) != null) {
                        content_high.append(line + "\n");
                        token_high[m] = line;
                        //  Log.d(TAG,line);
                        m++;
                    }
                    reader.close();

                }catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    // wait for 3 seconds
                    Thread.sleep(3000);

                    URL url_medium = new URL("http://192.168.1.67/series/upload/mediums.m3u8");
                    HttpURLConnection connection_medium = (HttpURLConnection) url_medium.openConnection();
                    // Timeout for reading InputStream arbitrarily set to 3000ms.
                    connection_medium.setReadTimeout(9000);
                    // Timeout for connection.connect() arbitrarily set to 3000ms.
                    connection_medium.setConnectTimeout(9000);
                    // For this use case, set HTTP method to GET.
                    connection_medium.setRequestMethod("GET");
                    // Already true by default but setting just in case; needs to be true since this request
                    // is carrying an input (response) body.
                    connection_medium.setDoInput(true);
                    // Open communications link (network traffic occurs here).
                    connection_medium.connect();

                    int responseCode = connection_medium.getResponseCode();
                    if (responseCode != HttpsURLConnection.HTTP_OK) {
                        Log.d(TAG, "HTTP connection error");
                    }
                    BufferedReader reader_medium = new BufferedReader(new InputStreamReader(connection_medium.getInputStream()));
                    String line;
                    int m = 0;
                    // read from the urlconnection via the bufferedreader
                    while ((line = reader_medium.readLine()) != null) {
                        content_medium.append(line + "\n");
                        token_medium[m] = line;
                        //  Log.d(TAG,line);
                        m++;
                    }
                    reader_medium.close();

                }catch (Exception e) {
                    e.printStackTrace();
                }

                    try {
                        // wait for 3 seconds
                        Thread.sleep(3000);
                        URL url_low = new URL("http://192.168.1.67/series/upload/lows.m3u8");
                        HttpURLConnection connection_low = (HttpURLConnection) url_low.openConnection();
                        connection_low.setReadTimeout(9000);
                        connection_low.setConnectTimeout(9000);
                        connection_low.setRequestMethod("GET");
                        connection_low.setDoInput(true);
                        connection_low.connect();
                        BufferedReader reader_low = new BufferedReader(new InputStreamReader(connection_low.getInputStream()));

                        String line_1;


                        int m=0;
                        while ((line_1 = reader_low.readLine()) != null) {
                            content_low.append(line_1 + "\n");
                            token_low[m] = line_1;
                            //  Log.d(TAG,line);
                            m++;
                        }
                        reader_low.close();



                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  connection.disconnect();







                    for (int i = 0; i <50; i++) {
                        if (token_high[i] == null)
                            break;


                        else{
                            char a_char = token_high[i].charAt(0);
                        if (a_char == 'h') {
                                high[j] = token_high[i];
                                j++;
                               //  Log.d(TAG, high[j-1]);
                            }
                        }
                    }

                    j=0;
                    for (int i = 0; i <50; i++) {
                        if (token_medium[i] == null)
                            break;


                        else{
                            char a_char = token_medium[i].charAt(0);
                            if (a_char == 'h') {
                                medium[j] = token_medium[i];
                                j++;
                                 //Log.d(TAG, medium[j-1]);
                            }
                        }
                    }

                j=0;
                for (int i = 0; i <50; i++) {
                    if (token_low[i] == null)
                        break;


                    else{
                        char a_char = token_low[i].charAt(0);
                        if (a_char == 'h') {
                            low[j] = token_low[i];
                            j++;
                            //Log.d(TAG, medium[j-1]);
                        }
                    }
                }


                    for(int k=0;k<=19;k++){
                        //video_high[k]=high[k];
                        //video_medium[k] =high_1[k];

                        video[0][k]=high[k];
                        video[1][k]=medium[k];
                        video[2][k]=low[k];

                        //videolist.add(high[k]);
                        //videolist_1.add(high_1[k]);
                       // Log.d(TAG,high[k]);
                    }

                    uiHandler.post(new Runnable(){
                       @Override
                        public void run(){
                           videoplay(video[0][count]);
                       }
                    });

            }
        });
        httpThread.start();

        /*
        try {
            httpThread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }*/
    }


        public  void videoplay (String path) {
         //   String link = "http://192.168.1.67/series/upload/out0.mp4";

           // Uri url = Uri.parse(link);
           mVideoView.setVideoURI(Uri.parse(path));
           // mVideoView.setVideoURI(url);
          //  mVideoView.setDataSource(videolist.get(0));
            mVideoView.setMediaController(mediaC);
            mVideoView.requestFocus();
//                mVideoView.postInvalidateDelayed(100);
            mediaC.setAnchorView(mVideoView);
            mVideoView.start();

            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer videoview){
                    count++;
                    mVideoView.setVideoURI(Uri.parse(video[index][count]));
                    mVideoView.start();
                }

            });
        }





}

