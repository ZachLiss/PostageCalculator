package edu.pitt.cs1635.zll1.prog1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements TextWatcher {

    private Button b;
    private EditText et;
    private TextView postage;
    private double[] letArray;
    private double[] envArray;
    private double[] pkgArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        letArray = new double[]{0, .46, .66, .86, 1.06};
        envArray = new double[]{0, .92, 1.12, 1.32, 1.52, 1.72, 1.92, 2.12, 2.32, 2.52, 2.72, 2.92, 3.12, 3.32};
        pkgArray = new double[]{0, 2.07, 2.07, 2.07, 2.24, 2.41, 2.58, 2.75, 2.92, 3.09, 3.26, 3.43, 3.6, 3.77};


        b = (Button) findViewById(R.id.calculate_button);
        et = (EditText) findViewById(R.id.editText_weight);
        postage = (TextView) findViewById(R.id.postage);

        et.addTextChangedListener(this);
    }

    public void showInfo(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ShowInfoActivity.class);
        startActivity(intent);
    }

    public void checkEnable(View view) {
        EditText et = (EditText) findViewById(R.id.editText_weight);
        String w = et.getText().toString();
        if(!w.matches("")) {
            b.setEnabled(true);
        }
    }

    public void calculatePostage(View view) {
        String message = "";
        if(et.getText() != null) {
            message = et.getText().toString();
        }
        double weight = Double.parseDouble(message);
        int cweight = (int) Math.ceil(weight);

        if(weight > 13 && weight > 0) {
            Toast.makeText(this, "Weight must be less than 13 oz", Toast.LENGTH_LONG).show();
            return;
        }

        RadioButton let = (RadioButton) findViewById(R.id.radio_let);
        RadioButton env = (RadioButton) findViewById(R.id.radio_env);
        RadioButton pkg = (RadioButton) findViewById(R.id.radio_pkg);

        if(let.isChecked()) {
            if(weight < 3.5) {
               postage.setText("$ " + String.format("%.2f", letArray[cweight]));
            } else {
                //do some toast
                Toast.makeText(this, "Letter must be less than 3.5 oz", Toast.LENGTH_LONG).show();
            }
        } else if(env.isChecked()) {
            postage.setText("$ " + String.format("%.2f", envArray[cweight]));
        } else if(pkg.isChecked()) {
            postage.setText("$ " + String.format("%.2f", pkgArray[cweight]));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.i("editText", charSequence.toString());

        if(charSequence.length() == 0) {
            b.setEnabled(false);
        } else {
            RadioButton let = (RadioButton) findViewById(R.id.radio_let);
            RadioButton env = (RadioButton) findViewById(R.id.radio_env);
            RadioButton pkg = (RadioButton) findViewById(R.id.radio_pkg);

            if(let.isChecked() || env.isChecked() || pkg.isChecked()) {
                b.setEnabled(true);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
