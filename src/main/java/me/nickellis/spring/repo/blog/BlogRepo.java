package me.nickellis.spring.repo.blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepo {

  Optional<Blog> getBlogBy(long id);
  List<Blog> getBlogs();
  List<Blog> searchBlogsBy(BlogSearch search);
  Blog createBlog(Blog blog);
  boolean updateBlog(long id, String title, String content);
  boolean deleteBlog(long id);

}
