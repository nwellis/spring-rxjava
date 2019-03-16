package me.nickellis.spring.test.repo;

import me.nickellis.spring.repo.blog.Blog;
import me.nickellis.spring.repo.blog.BlogRepo;
import me.nickellis.spring.repo.blog.BlogSearch;
import me.nickellis.spring.repo.blog.MockBlogRepo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BlogRepoTests {

  private BlogRepo repository;
  private Blog emptyBlog;
  private Blog testBlog;
  private Blog uniqueBlog;

  @Before
  public void setup() {
    repository = MockBlogRepo.getInstance();
    long idNotInRepo = Long.MAX_VALUE;
    emptyBlog = new Blog(idNotInRepo, "", "");
    testBlog = new Blog(idNotInRepo - 1, "New Blog", "Here's a new blog");
    uniqueBlog = new Blog(idNotInRepo - 2, uniqueString(), uniqueString());
  }

  @Test
  public void createdBlogHasUniqueId() {
    // Arrange
    Set<Long> ids = repository.getBlogs()
        .stream()
        .map(Blog::getId)
        .collect(Collectors.toSet());

    // Act
    Blog blog = repository.createBlog(testBlog);

    // Assert
    assertTrue("Newly created blog did not have a unique ID", !ids.contains(blog.getId()));
  }

  @Test
  public void searchByTitleReturnsResult() {
    // Arrange
    String keywords = uniqueBlog.getTitle();
    repository.createBlog(uniqueBlog);

    // Act
    List<Blog> results = repository.searchBlogsBy(new BlogSearch(keywords));

    // Assert
    assertEquals("Search should only return one result", 1, results.size());
    assertEquals("Search should return blog with correct title",
        uniqueBlog.getTitle(), results.stream().findFirst().orElse(emptyBlog).getTitle());
  }

  @Test
  public void searchByContentReturnsResult() {
    // Arrange
    String keywords = uniqueBlog.getContent();
    repository.createBlog(uniqueBlog);

    // Act
    List<Blog> results = repository.searchBlogsBy(new BlogSearch(keywords));

    // Assert
    assertEquals("Search should only return one result", 1, results.size());
    assertEquals("Search should return blog with correct title",
        uniqueBlog.getContent(), results.stream().findFirst().orElse(emptyBlog).getContent());
  }

  @Test
  public void updateBlog() {
    // Arrange
    String titleUpdate = uniqueString();
    String contentUpdate = uniqueString();
    Blog created = repository.createBlog(uniqueBlog);
    Long blogId = created.getId();

    // Act
    boolean updated = repository.updateBlog(blogId, titleUpdate, contentUpdate);
    Blog updatedBlog = repository.getBlogBy(blogId).orElse(emptyBlog);

    // Assert
    assertTrue("Blog update should return true for success", updated);
    assertEquals("Blog title update was incorrect", titleUpdate, updatedBlog.getTitle());
    assertEquals("Blog content update was incorrect", contentUpdate, updatedBlog.getContent());
  }

  @Test
  public void updateOnNonExistingBlogReturnsFalse() {
    // Arrange

    // Act
    boolean updated = repository.updateBlog(emptyBlog.getId(), "doesn't", "matter");

    // Assert
    assertFalse("Incorrectly updated a blog when ID wasn't present", updated);
  }

  @Test
  public void deleteBlog() {
    // Arrange
    Long createdId = repository.createBlog(uniqueBlog).getId();

    // Act
    boolean deleted = repository.deleteBlog(createdId);

    // Assert
    assertTrue("Blog delete should return true for success", deleted);
  }

  @Test
  public void deleteNonExistingBlogReturnsFalse() {
    // Arrange
    Long nonExistingId = emptyBlog.getId();

    // Act
    boolean deleted = repository.deleteBlog(nonExistingId);

    // Assert
    assertFalse("Blog delete should return false for invalid ID", deleted);
  }

  private String uniqueString() {
    return UUID.randomUUID().toString();
  }
}
