package me.nickellis.spring.repo.blog;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.List;

public interface BlogRepo {

  public Observable<Blog> getBlogBy(long id);
  public Single<List<Blog>> getBlogs();
  public Single<List<Blog>> searchBlogsBy(String keywords);
  public Observable<Blog> createBlog(String title, String content);
  public Observable<Blog> updateBlog(String title, String content);
  public boolean deleteBlog(long id);

}
