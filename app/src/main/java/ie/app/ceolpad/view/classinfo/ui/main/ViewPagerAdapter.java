package ie.app.ceolpad.view.classinfo.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    long id;

    public ViewPagerAdapter(FragmentManager fm, long idnum) {
        super(fm);
        id = idnum;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new LessonFragment(id);
        }
        else if (position == 1)
        {
            fragment = new StudentFragment(id);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Lessons";
        }
        else if (position == 1)
        {
            title = "Students";
        }
        return title;
    }

}