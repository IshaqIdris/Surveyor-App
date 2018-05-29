package cardno.csveditor;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Edit_CSV extends AppCompatActivity {
    private Button b;
    EditText lat,lon,elevation;
    TextView tv;
    String home = System.getProperty("user.home");
    String file = home + File.separator + "Documents" + File.separator + "Callibration.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__csv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b = (Button)findViewById(R.id.button);

        tv = (TextView)findViewById(R.id.output);
        tv.setMovementMethod(new ScrollingMovementMethod());

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                lat = (EditText)findViewById(R.id.lat);
                lon = (EditText)findViewById(R.id.lon);
                elevation = (EditText)findViewById(R.id.elevation);
                tv.setText("Lat : " + lat.getText().toString() + "\nLon :" + lon.getText().toString() + "\nElevation :" + elevation.getText().toString());
                try {
                    updateCSV(lat,lon,elevation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateCSV(EditText lat, EditText lon, EditText elevation) throws IOException {
        File inputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Documents");
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> csvBody = reader.readAll();
        Log.d("MyApp", "Working directory " + System.getProperty("user.dir"));
        csvBody.get(1)[1] = lat.getText().toString();
        reader.close();

        CSVWriter writer = new CSVWriter((new FileWriter(inputFile)));
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit__csv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
