package apps.my.spotter.activities.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import apps.my.spotter.R;
import apps.my.spotter.activities.fragments.IssueFragment;
import apps.my.spotter.activities.models.Issue;

public class Base extends AppCompatActivity {

    public static int totalNumberOfIssues;
    public static String countyPicked, issuePicked;
    public static ArrayList<Issue> IssueList = new ArrayList<>();           //ALL issues
    public static ArrayList<Issue> ALLissueList = new ArrayList<>();   //filtered list of issues
    public Bundle activityInfo; // Used for persistence (of sorts)
    public IssueFragment issueFragment; // How we'll 'share' our List of Issues between Activities
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.appAbout))
                .setMessage(getString(R.string.appDesc)
                        + "\n\n"
                        + getString(R.string.appMoreInfo))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // we could put some code here too
                    }
                })
                .show();
    }

    public void menuHelp(MenuItem m) {
        startActivity(new Intent(this, Help.class));
    }
}
