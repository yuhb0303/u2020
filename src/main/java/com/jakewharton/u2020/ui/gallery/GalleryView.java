package com.jakewharton.u2020.ui.gallery;

import android.content.Context;
import android.util.AttributeSet;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.etsy.android.grid.StaggeredGridView;
import com.jakewharton.u2020.R;
import com.jakewharton.u2020.U2020App;
import com.jakewharton.u2020.data.GalleryDatabase;
import com.jakewharton.u2020.data.api.Section;
import com.jakewharton.u2020.data.api.model.Image;
import com.jakewharton.u2020.data.rx.EndlessObserver;
import com.jakewharton.u2020.ui.misc.BetterViewAnimator;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;

public class GalleryView extends BetterViewAnimator {
  @InjectView(R.id.gallery_grid) StaggeredGridView galleryView;

  @Inject Picasso picasso;
  @Inject GalleryDatabase galleryDatabase;

  private Section section = Section.HOT;
  private Subscription request;

  private final GalleryAdapter adapter;

  public GalleryView(Context context, AttributeSet attrs) {
    super(context, attrs);
    U2020App.get(context).inject(this);

    adapter = new GalleryAdapter(context, picasso);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.inject(this);

    galleryView.setAdapter(adapter);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    request = galleryDatabase.loadGallery(section, new EndlessObserver<List<Image>>() {
      @Override public void onNext(List<Image> images) {
        adapter.replaceWith(images);
        setDisplayedChildId(R.id.gallery_grid);
      }
    });
  }

  @Override protected void onDetachedFromWindow() {
    request.unsubscribe();
    super.onDetachedFromWindow();
  }
}
