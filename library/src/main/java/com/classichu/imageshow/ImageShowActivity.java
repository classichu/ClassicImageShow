package com.classichu.imageshow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.classichu.imageshow.adapter.ImageShowPagerAdapter;
import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.bean.ImageShowDataWrapper;
import com.classichu.imageshow.helper.DownloadImageHelper;
import com.classichu.imageshow.tool.RegexTool;

import java.util.ArrayList;
import java.util.List;


public class ImageShowActivity extends AppCompatActivity {

    private TextView id_bottom_sheet_text;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private AppBarLayout id_appbar_layout;

    private ImageShowPagerAdapter mImageShowPagerAdapter;
    private List<ImageShowBean> mImageShowBeanList = new ArrayList<>();

    private ViewPager mViewPager;
    private TextView mToolbarTitleView;
    private Toolbar mToolbar;

    private ImageShowDataWrapper mImageShowDataWrapper;

    private boolean mIsToolbarTitleCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);


        //
        Bundle bundle = null;
        if (this.getIntent() != null) {
            bundle = this.getIntent().getExtras();
        }
        if (bundle != null) {
            mImageShowDataWrapper = (ImageShowDataWrapper) bundle.getSerializable("imageShowDataWrapper");
            if (mImageShowDataWrapper != null) {
                mIsToolbarTitleCenter = mImageShowDataWrapper.isTitleCenter();
            }

        }

        initToolbar();

        initBottomSheet();
        //
        initImageViewPager();
        //
        gainData();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolbar == null) {
            return;
        }
        mToolbarTitleView = (TextView) mToolbar.findViewById(R.id.id_toolbar_title);

        /**
         * setToolbarTitle
         */
        this.setToolbarTitle(mToolbar.getTitle() != null ? mToolbar.getTitle().toString() : "");

        mToolbar.setVisibility(View.VISIBLE);
        //替换ActionBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //必须设置在setSupportActionBar(mToolbar);后才有效
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前aty
                finish();
            }
        });
    }

    private void initBottomSheet() {
        id_bottom_sheet_text = (TextView) findViewById(R.id.id_bottom_sheet_text);

        View bottomSheet = findViewById(R.id.id_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setHideable(true);
        int peekHeight = (int) (getResources().getDisplayMetrics().heightPixels * 1.0f / 5);
        mBottomSheetBehavior.setPeekHeight(peekHeight);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                /** STATE_COLLAPSED: 关闭Bottom Sheets,显示peekHeight的高度，默认是STATE_COLLAPSED
                 STATE_DRAGGING: 用户拖拽Bottom Sheets时的状态
                 STATE_SETTLING: 当Bottom Sheets view释放时记录的状态。
                 STATE_EXPANDED: 当Bottom Sheets 展开的状态
                 STATE_HIDDEN: 当Bottom Sheets 隐藏的状态*/
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    //回归显示toolbar
                    id_appbar_layout.setExpanded(true);
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    //隐藏toolbar
                    id_appbar_layout.setExpanded(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void initImageViewPager() {
        id_appbar_layout = (AppBarLayout) findViewById(R.id.id_appbar_layout);

        //
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String content = mImageShowBeanList.get(position).getContent();
                if (content == null || content.equals("")) {
                    content = mImageShowBeanList.get(position).getTitle();
                }
                id_bottom_sheet_text.setText(content);

                setToolbarTitle((position + 1) + "/" + mImageShowBeanList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mImageShowPagerAdapter = new ImageShowPagerAdapter(mImageShowBeanList);
        mImageShowPagerAdapter.setPhotoViewAttacherListener(new ImageShowPagerAdapter.PhotoViewAttacherListener() {
            @Override
            public void onPhotoViewInited(final View photoView) {
                id_appbar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        //  收起 0   ~  -84      展开 -84  ~0
                        //  CLog.d("!!!onOffsetChanged:verticalOffset" + verticalOffset);
                        /**
                         * 修正当toobar慢慢收起后  mPhotoView相对位置不变化
                         */
                        photoView.setTranslationY(Math.abs(verticalOffset));
                    }
                });
            }

            @Override
            public void onPhotoViewTap(final View photoView, float x, float y) {
                //Toast.makeText(ImageShowActivity.this, "onViewTap", Toast.LENGTH_SHORT).show();
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    //设置为隐藏
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //设置为隐藏
                    id_appbar_layout.setExpanded(false);
                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    //设置为显示
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//默认半展开
                    //设置为显示
                    id_appbar_layout.setExpanded(true);
                }
            }
        });
        //
        mViewPager.setAdapter(mImageShowPagerAdapter);
    }

    protected void setToolbarTitle(String string) {
        if (mIsToolbarTitleCenter) {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText(string);
                mToolbar.setTitle("");
                mToolbarTitleView.setVisibility(View.VISIBLE);
            }
        } else {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText("");
                mToolbar.setTitle(string);
                mToolbarTitleView.setVisibility(View.GONE);
            }
        }
    }

    private void gainData() {
        /**
         *
         */
        int nowSeletedPos = mImageShowDataWrapper.getNowSeletedPos();

        List<ImageShowBean> imageShowBeanList = mImageShowDataWrapper.getImageShowBeanList();

        /**
         *
         */
        mImageShowPagerAdapter.refreshImageShowBeanList(imageShowBeanList);

        /**
         *
         */
        setToolbarTitle((nowSeletedPos + 1) + "/" + imageShowBeanList.size());


        String content = imageShowBeanList.get(nowSeletedPos).getContent();
        if (content == null || content.equals("")) {
            content = imageShowBeanList.get(nowSeletedPos).getTitle();
        }
        id_bottom_sheet_text.setText(content);
        //
        mViewPager.setCurrentItem(nowSeletedPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.id_menu_save) {
            saveImageOperator();

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImageOperator() {
        int pos = mViewPager.getCurrentItem();
        String imageUrl = mImageShowBeanList.get(pos) == null ? "" : mImageShowBeanList.get(pos).getImageUrl().toString();
        if (!imageUrl.equals("") && RegexTool.checkURL(imageUrl)) {
              /*  OkHttpClientSingleton.getInstance().doGetAsyncFile(imageUrl, new SimpleFileOkHttpCallback() {
                    @Override
                    public void OnSuccess(String filePath, int statusCode) {
                       //Toast.makeText(ImageShowActivity.this, "filePath:"+filePath, Toast.LENGTH_SHORT).show();
                        KLog.d("filePath:"+filePath);
                    }
                });*/
          //  KLog.d("imageUrl:" + imageUrl);
            downloadImage(imageUrl);
        } else {
            Toast.makeText(this, "已经是本地图片", Toast.LENGTH_SHORT).show();
           // ToastTool.showLong("已经是本地图片：" + imageUrl);
        }
    }

    private void downloadImage(String imageUrl) {
        DownloadImageHelper.downLoadFile(imageUrl, new DownloadImageHelper.OnDownLoadImageListener() {
            @Override
            public void onSuccess(String filepath) {
                //KLog.d("XXX onSuccess" + filepath);
                //ToastTool.showLong("已保存到" + filepath);
                Toast.makeText(ImageShowActivity.this,"已保存到" + filepath, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
               // KLog.d("XXX onError  保存失败");
                Toast.makeText(ImageShowActivity.this,"保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
