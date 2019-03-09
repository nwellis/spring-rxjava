package me.nickellis.spring;

import me.nickellis.spring.repo.blog.Blog;
import me.nickellis.spring.repo.blog.BlogRepo;
import me.nickellis.spring.repo.blog.MockBlogRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogController {

  private BlogRepo repository = MockBlogRepo.getInstance();

  @RequestMapping("/blogs")
  public List<Blog> blogs() {
    return repository.getBlogs();
  }

}
