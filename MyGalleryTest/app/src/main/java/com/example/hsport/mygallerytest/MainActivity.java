package com.example.hsport.mygallerytest;



        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.Toast;
        import java.io.*;
        import java.net.*;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

public class MainActivity extends Activity {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Button b1;
    EditText ed01;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button02);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = ((EditText) findViewById(R.id.editText)).getText().toString();
                ed01 = (EditText) findViewById(R.id.editText);

                //Client myClient = new Client(fname + ";" + sname);
                // myClient.execute();

                //Log.d("FROMSERVER", Boolean.toString(myClient.flag));
                if (fname.equals("")) {

                    ed01.setHint("Please Enter Plate Number!");
                } else {
                    Client myClient = new Client(imagePath);
                    myClient.execute();
                }

            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                 imagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));






            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
    public class Client extends AsyncTask<Void, Void, Void> {
        String ip;
        Client(String im) {
         ip = im;
        }


        @Override
        protected Void doInBackground(Void... params) {
try{
    String hostName = "205.178.20.186";
    int portNumber = 3003;


        Socket socket = new Socket(hostName, portNumber);


            PrintStream out =  new PrintStream(socket.getOutputStream());
            BufferedInputStream in = new BufferedInputStream( new FileInputStream( ip ) );
            byte[] buffer = new byte[ 4096 ];
            int cl = 0;



                int bytesRead;
                while ( (bytesRead = in.read(buffer )) > -1 ) {
                    out.write( buffer, 0, bytesRead );
                }
                out.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
    Log.d("myTag",  e.getMessage());
            }
            return null;
        }
    }


}