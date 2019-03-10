package me.nickellis.spring.controller.blog;

import me.nickellis.spring.repo.blog.Blog;
import me.nickellis.spring.repo.blog.BlogRepo;
import me.nickellis.spring.repo.blog.MockBlogRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {

  private static final String ERROR_NO_BLOG_WITH_ID = "No blog with id %s was found";

  private BlogRepo repository = MockBlogRepo.getInstance();

  @RequestMapping("/blogs")
  public List<Blog> blogs() {
    return repository.getBlogs();
  }

  @RequestMapping("/blogs/{id}")
  public Blog blog(@PathVariable String id) {
    try {
      Optional<Blog> blog = repository.getBlogBy(Long.parseLong(id));

      if (!blog.isPresent()) {
        throw blogNotFoundWith(id);
      } else {
        return blog.get();
      }
    } catch (NumberFormatException ex) {
      throw blogNotFoundWith(id);
    }
  }

  private ResponseStatusException blogNotFoundWith(String id) {
    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, String.format(ERROR_NO_BLOG_WITH_ID, id));
  }

}
