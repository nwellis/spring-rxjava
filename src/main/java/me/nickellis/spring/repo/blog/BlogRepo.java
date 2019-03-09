package me.nickellis.spring.repo.blog;

import java.util.List;

public interface BlogRepo {

  Blog getBlogBy(long id);
  List<Blog> getBlogs();
  List<Blog> searchBlogsBy(String keywords);
  Blog createBlog(String title, String content);
  Blog updateBlog(String title, String content);
  boolean deleteBlog(long id);

}
