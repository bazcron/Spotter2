package apps.my.spotter.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import apps.my.spotter.R;
import apps.my.spotter.activities.models.Issue;

public class EditIssues extends Base {
    public Context context;
    public boolean isFavourite;
    public Issue aIssue;
    public ImageView editFavourite;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_issues);
        context = this;
        activityInfo = getIntent().getExtras();
        aIssue = getIssueObject(activityInfo.getString("issueId"));

        Log.v("Issue", "EDIT : " + aIssue);

        ((TextView)findViewById(R.id.editRoadName)).setText(aIssue.street);
        ((TextView)findViewById(R.id.editTownCity)).setText(aIssue.city);
        ((TextView)findViewById(R.id.editNameOfIssue)).setText(aIssue.type);
        ((TextView)findViewById(R.id.editCountyName)).setText(aIssue.county);
        ((TextView)findViewById(R.id.editInfo)).setText(aIssue.info);

        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)aIssue.rating);
    }

    private Issue getIssueObject(String id) {

        for (Issue c : IssueList)
            if (c.issueId.equalsIgnoreCase(id))
                return c;

        return null;
    }

//    private int getIssueIndex(Issue obj) {
//
//        for (Issue c : IssueList)
//            if (c.IssueId == obj.IssueId)
//                return IssueList.indexOf(c);
//
//        return -1;
//    }

    public void saveIssue(View v) {

        String roadName = ((TextView) findViewById(R.id.editRoadName)).getText().toString();
        String town = ((TextView) findViewById(R.id.editTownCity)).getText().toString();
        String issue = ((TextView) findViewById(R.id.editNameOfIssue)).getText().toString();
        String county = ((TextView) findViewById(R.id.editCountyName)).getText().toString();
        String info = ((TextView) findViewById(R.id.editInfo)).getText().toString();

        int ratingValue =(int)((RatingBar) findViewById(R.id.editRatingBar)).getRating();

        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)aIssue.rating);

            aIssue.street = roadName;
            aIssue.city = town;
            aIssue.info = info;
            aIssue.type = issue;
            aIssue.county = county;
            aIssue.rating = ratingValue;

            //edit issue in database
           DatabaseReference dbEditOneIssue = FirebaseDatabase.getInstance().getReference("issue").child(aIssue.issueId);
            dbEditOneIssue.setValue(aIssue);

            startActivity(new Intent(this,Home.class));
    }

}
