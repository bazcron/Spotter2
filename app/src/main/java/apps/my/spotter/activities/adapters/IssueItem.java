package apps.my.spotter.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import apps.my.spotter.activities.models.Issue;
import apps.my.spotter.R;


public class IssueItem {
    View view;

    public IssueItem(Context context, ViewGroup parent,
                     View.OnClickListener deleteListener, Issue issue)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.issue_row, parent, false);
        view.setTag(issue.issueId);

        updateControls(issue);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(issue);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Issue issue) {
        ((TextView) view.findViewById(R.id.rowCountyName)).setText(issue.county);
        ((TextView) view.findViewById(R.id.rowIssue)).setText(issue.type);
        ((TextView) view.findViewById(R.id.rowRating)).setText(issue.rating+"");

        //ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

      /*  if (issue.mostImportantIssue == true)
            imgIcon.setImageResource(R.drawable.favourites_72);
        else
            imgIcon.setImageResource(R.drawable.favourites_72);
*/

    }
}