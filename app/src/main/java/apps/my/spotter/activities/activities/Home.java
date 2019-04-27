package apps.my.spotter.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.AllPermission;

import apps.my.spotter.R;
import apps.my.spotter.activities.fragments.IssueFragment;
import apps.my.spotter.activities.models.Issue;

import static apps.my.spotter.activities.fragments.IssueFragment.listAdapter;

// *** REF: Note... i used parts of the practical exercises for Android to help create this app.

public class Home extends Base {
//countyPicked, issuePicked static variables in Base class...
    TextView emptyList;
    Spinner countySpinner, issueSpinner;
    int countyCurrentSelection, issueCurrentSelection;
    boolean firstTime = true;
    TextView issueQtyMessage;   //used to display info about quantities
    int allIssues;
   // ListView IssueListView;
   // ArrayAdapter<Issue> IssueAdapter;

    // *** References are for implementing firebase database
    // *** REF: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
    // *** REF: https://www.youtube.com/watch?v=EM2x33g4syY&list=PLk7v1Z2rk4hj6SDHf_YybDeVhUT9MXaj1
    //database reference object
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseApp.initializeApp(this);

        emptyList = findViewById(R.id.emptyList);
        countySpinner = findViewById(R.id.spinnerCounty);
        issueSpinner = findViewById(R.id.spinnerIssue);
        issueQtyMessage = findViewById(R.id.textView3);


        //getting the reference of issue nodes
        myDatabase = FirebaseDatabase.getInstance().getReference("issue");

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ALLissueList.clear();
                IssueList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //add to allIssueCount
                    allIssues++;
                    //getting issue
                    Issue issue = (ds.getValue(Issue.class));
                    //adding issue to the list
                    Log.d("check",issue.city);
                    ALLissueList.add(issue);
                    IssueList.add(issue);

                    firstTime = false;
                  filterIssues(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //checks if value changes in county spinner............
        countyCurrentSelection = countySpinner.getSelectedItemPosition();
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (countyCurrentSelection != i){
                    firstTime = false;
                    filterIssues(countyCurrentSelection);
                }
                countyCurrentSelection = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        //..........................

        //checks if value changes in issue spinner............
        issueCurrentSelection = issueSpinner.getSelectedItemPosition();
        issueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (issueCurrentSelection != i){
                    firstTime = false;
                    filterIssues(issueCurrentSelection);
                }
                issueCurrentSelection = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        filterIssues(0);
    }//....................end of onCreate ..................................

    @Override
    protected void onStart() {
        super.onStart();
        filterIssues(0);

    }

    //on Add button click, this opens Add class
    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Issuemate","Home : " + IssueList);

        issueFragment = IssueFragment.newInstance(); //get a new Fragment instance
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, issueFragment)
                .commit(); // add it to the current activity
        if(IssueList.isEmpty())
            emptyList.setText((getString(R.string.emptyMessageLbl)));
        else emptyList.setText("");
        filterIssues(0);
    }

    //filters issue on county, issue
    private void filterIssues(int currentPosition){
       if (firstTime){
            //do nothing...
        }else {
            int count = 0;
            IssueList.clear();   //clears all issues from issueList
            //gets selected strings for county and issue
            countyPicked = countySpinner.getSelectedItem().toString();
            issuePicked = issueSpinner.getSelectedItem().toString();

            //checks if both county and issue is set to All....if they are displays everything
            //else filters the list
            if(countyPicked.equalsIgnoreCase("ALL") && issuePicked.equalsIgnoreCase("ALL")){
                for (Issue issue : ALLissueList) {
                    IssueList.add(issue);
                    count = count + 1;
                }
            }
            else {   //steps through All issue objects and checks if county and issue are the same, if they are it updates the IssueList
                //with the newest filtered objects
                for (Issue issue : ALLissueList) {
                    if ((issue.county.equalsIgnoreCase(countyPicked) || countyPicked.equalsIgnoreCase("ALL")) &&
                            (issue.type.equalsIgnoreCase(issuePicked) || issuePicked.equalsIgnoreCase("ALL"))) {
                        IssueList.add(issue);
                        count = count + 1;
                    }
                }
            }
            IssueFragment.listAdapter.notifyDataSetChanged();
            Toast.makeText(this, "SEARCH COMPLETED", Toast.LENGTH_LONG).show();

           String nameOfIssue = issueSpinner.getSelectedItem().toString();
            if (nameOfIssue.equals("ALL")){
                issueQtyMessage.setText("ALL Issues ="+allIssues);

            }else{
                issueQtyMessage.setText(nameOfIssue+"'s ="+count+"  ALL Issues ="+allIssues);
            }
        }//end of else.........
    }

}

