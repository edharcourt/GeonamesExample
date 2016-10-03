package edu.cs364.webservicesocketexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button submit_v = null;
    EditText zipcode_v = null;
    TextView output_area_v = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit_v = (Button) findViewById(R.id.submit);
        zipcode_v = (EditText) findViewById(R.id.zipcode);
        output_area_v = (TextView) findViewById(R.id.output_area);

        submit_v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    String zip = zipcode_v.getText().toString();
                    // do error checking on zip code
                    new HttpGetTask(MainActivity.this).execute(zip);
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
