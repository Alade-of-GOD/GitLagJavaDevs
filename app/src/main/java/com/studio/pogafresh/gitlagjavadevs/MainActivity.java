package com.studio.pogafresh.gitlagjavadevs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements ListUserAdapter.ListUserAdapterOnClickHandler{

    ListView lvItems;
    RecyclerView rvView;
    TextView errorMessage;
    Button btnRefreshButton;
    private ListUserAdapter listUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessage = (TextView) findViewById(R.id.error_message);
        btnRefreshButton = (Button) findViewById(R.id.btnRefresh);
        rvView = (RecyclerView) findViewById(R.id.rvView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        rvView.setHasFixedSize(true);
        listUserAdapter = new ListUserAdapter(this, this);
        rvView.setAdapter(listUserAdapter);
        fetchUsers();
    }

    private void fetchUsers() {
        URL gitSearchUrl = NetworkAccess.buildGitUrl(getString(R.string.SEARCH_STRING));
        if (!isNetworkAvailable()) {
            showErrorMessage();
            errorMessage.setText(getString(R.string.internet_failure));
        } else {
            new GitUserList().execute(gitSearchUrl);
        }
    }

    public class GitUserList extends AsyncTask<URL, Void, String[]> {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setMessage("Fetching Data from Github...");
            dialog.show();
        }

        @Override
        protected String[] doInBackground(URL... params) {
            URL location = params[0];
            String[] UserJsonData;
            try {
                String jsonResponse = NetworkAccess.getResponseFromHttpUrl(location);
                UserJsonData = UserJsonHandler.getUserStringsFromJson(MainActivity.this, jsonResponse);
                return UserJsonData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] userData) {
            dialog.dismiss();
            if (userData !=null && !userData.equals("") ){
                showData();
                listUserAdapter.setUserData(userData);
            }else {
                showErrorMessage();
            }
        }
    }

    private void showData(){
        errorMessage.setVisibility(View.INVISIBLE);
        btnRefreshButton.setVisibility(View.INVISIBLE);
        rvView.setVisibility(View.VISIBLE);

    }

    public void Refresh(View v){
        fetchUsers();
    }

    @Override
    public void onClick(String gitData) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, gitData);
        startActivity(intentToStartDetailActivity);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void showErrorMessage() {
        rvView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
        btnRefreshButton.setVisibility(View.VISIBLE);
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
