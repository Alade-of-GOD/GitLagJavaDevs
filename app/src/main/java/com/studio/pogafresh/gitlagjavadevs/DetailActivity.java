package com.studio.pogafresh.gitlagjavadevs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by PoG~Afresh on 8/28/2017.
 */

public class DetailActivity extends AppCompatActivity {

    //get all views
    TextView tvName;
    ImageView ivImageView;
    TextView tvUrl;
    LinearLayout mTitleHolder;
    String userUrl,userName,userImage ;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivImageView = (ImageView) findViewById(R.id.placeImage);
        tvName = (TextView) findViewById(R.id.textView);
        tvUrl = (TextView) findViewById(R.id.user_git_url);
        btnShare = (Button) findViewById(R.id.shareButton);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //what to do when share button is clicked
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //open a share intent and put github url of the user in it
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.home_url) + userName));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    //show error if there is an error as a toast
                    Toast.makeText(DetailActivity.this, getString(R.string.app_err), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

            String userDetails = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            String[] parts = userDetails.split("-");

            userName = parts[0];
            userUrl = parts[1];
            userImage = parts[2];

            tvName.setText(userName);
            tvUrl.setText(getString(R.string.home_url) + userName);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}