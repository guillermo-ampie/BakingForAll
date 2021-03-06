package com.ampieguillermo.bakingforall.recipe.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

  private final List<Fragment> mFragmentPageList;
  private final List<String> mPageTitleList;

  public ViewPagerAdapter(final FragmentManager fm) {
    super(fm);
    mFragmentPageList = new ArrayList<>();
    mPageTitleList = new ArrayList<>();
  }

  /**
   * Return the number of views available.
   */
  @Override
  public int getCount() {
    return mFragmentPageList.size();
  }

  /**
   * Return the Fragment associated with a specified position.
   *
   * @param position: The position of the Fragment (Page) requested
   */
  @Override
  public Fragment getItem(int position) {
    return mFragmentPageList.get(position);
  }

  /**
   * This method may be called by the ViewPager to obtain a title string
   * to describe the specified page. This method may return null
   * indicating no title for this page. The default implementation returns
   * null.
   *
   * @param position The position of the title requested
   * @return A title for the requested page
   */
  @Override
  public CharSequence getPageTitle(int position) {
    return mPageTitleList.get(position);
  }

  public ViewPagerAdapter addFragmentPage(final Fragment fragment, final String title) {
    mFragmentPageList.add(fragment);
    mPageTitleList.add(title);

    return this;
  }
}
