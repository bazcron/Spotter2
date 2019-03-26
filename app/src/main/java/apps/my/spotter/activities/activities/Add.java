package apps.my.spotter.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
import apps.my.spotter.R;
import apps.my.spotter.activities.models.Issue;

//*** REF: https://www.simplifiedcoding.net/firebase-realtime-database-crud/   used and adapted code on this page to help incorporate
//Firebase Database into my project.

public class Add extends Base {

    private static final String TAG = "db";
    private String roadName, town, info,county, type, countyText, typeText;
    private EditText roadNameText, townText, infoText;
    private RatingBar ratingBar;
    private int issueLevel;
    private boolean mostImportantIssue;
    Spinner countySpinner, issueSpinner;

    //database reference object
    DatabaseReference myDatabase;
    Issue issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the Firebase App
        FirebaseApp.initializeApp(this);

        //getting the reference of issue node
        myDatabase = FirebaseDatabase.getInstance().getReference("issue");

     /*   myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> updateSpotterDatabase = (Map<String, Object>) dataSnapshot.getValue();
                Toast toast = Toast.makeText(getApplicationContext(),""+updateSpotterDatabase.toString(),Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast toast = Toast.makeText(getApplicationContext(),"ERROR: New Issue Not Uploaded",Toast.LENGTH_LONG);
                toast.show();
            }
        });*/
        setContentView(R.layout.add);
        //  getActionBar().setTitle("FRed");
        countySpinner = findViewById(R.id.spinnerCounty);
        issueSpinner = findViewById(R.id.spinnerIssue);
        roadNameText = findViewById(R.id.addRoadName);
        townText =  findViewById(R.id.addTown);
        // ratingBar =  findViewById(R.id.addRatingBar);
        infoText =  findViewById(R.id.addInfo);

        ratingBar = findViewById(R.id.addRatingBar);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }
    public void addIssue(View v) {
        roadName = roadNameText.getText().toString();
        town = townText.getText().toString();
        info = infoText.getText().toString();
        Float issueLevelFloat = ratingBar.getRating();
        issueLevel = Math.round(issueLevelFloat);
        county = countySpinner.getSelectedItem().toString();
        type = issueSpinner.getSelectedItem().toString();

        //checks if there is a value in each field, if there is it creates an Issue object and sends it to the database, else it displays a message
        if ((roadName.length() > 0) && (town.length() > 0)
                && (issueLevel > 0)) {
            if(issueLevel>7){
                mostImportantIssue = true;
            }else{
                mostImportantIssue = false;
            }
           //.....................HANDLING ADDING TO DATABASE.................................
            //getting a unique id for a New Issue using push().getKey() method
            String id = myDatabase.push().getKey();

            //creating an Issue object
            Issue c = new Issue(id,type, county, roadName, town, info,
                    issueLevel, mostImportantIssue);

            //Saving the Issue to the database
            myDatabase.child(id).setValue(c);


        /*   myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild("this")) {
                        Log.d(TAG, "it worked ");
                        // it exists!
                    }else{
                        // does not exist
                        Log.d(TAG, "it didnt work ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

            totalNumberOfIssues++;      //increase number of issues by 1
            IssueList.add(c);
            ALLissueList.add(c);

            startActivity(new Intent(this, Home.class));
        } else
            Toast.makeText(
                    this,
                    "Please Enter Something for "
                            + "\'Location\', \'Town/City\' and \'Issue Level \'",
                    Toast.LENGTH_SHORT).show();
    }
}
