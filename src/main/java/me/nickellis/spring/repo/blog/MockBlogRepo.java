package me.nickellis.spring.repo.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockBlogRepo implements BlogRepo {
  private static MockBlogRepo instance = new MockBlogRepo();
  public static MockBlogRepo getInstance() {
    return instance;
  }

  private AtomicLong idCursor = new AtomicLong();
  private List<Blog> blogs = new ArrayList<>();

  private MockBlogRepo() {
    blogs.add(new Blog(idCursor.incrementAndGet(), "Go up, up and away with your Google Assistant",
        "With holiday travel coming up, and 2018 just around the corner, " +
            "you may be already thinking about getaways for next year. Consider " +
            "the Google Assistant your new travel buddy, ready at the drop of a hat—or a passport"));
    blogs.add(new Blog(idCursor.incrementAndGet(), "Get local help with your Google Assistant",
        "No matter what questions you’re asking—whether about local traffic or " +
            "a local business—your Google Assistant should be able to help. And starting " +
            "today, it’s getting better at helping you, if you’re looking for nearby services " +
            "like an electrician, plumber, house cleaner and more"));
    blogs.add(new Blog(idCursor.incrementAndGet(), "The new maker toolkit: IoT, AI and Google Cloud Platform",
        "Voice interaction is everywhere these days—via phones, TVs, laptops and smart home devices " +
            "that use technology like the Google Assistant. And with the availability of maker-friendly " +
            "offerings like Google AIY’s Voice Kit, the maker community has been getting in on the action " +
            "and adding voice to their Internet of Things (IoT) projects."));
    blogs.add(new Blog(idCursor.incrementAndGet(), "Learn more about the world around you with Google Lens and the Assistant",
        "Looking at a landmark and not sure what it is? Interested in learning more about a movie as " +
            "you stroll by the poster? With Google Lens and your Google Assistant, you now have a helpful " +
            "sidekick to tell you more about what’s around you, right on your Pixel."));
    blogs.add(new Blog(idCursor.incrementAndGet(), "7 ways the Assistant can help you get ready for Turkey Day",
        "Thanksgiving is just a few days away and, as always, your Google Assistant is ready to help. " +
            "So while the turkey cooks and the family gathers, here are some questions to ask your Assistant."));
  }

  @Override
  public Optional<Blog> getBlogBy(long id) {
    return getBlogs()
        .stream()
        .filter(blog -> blog.getId() == id)
        .findFirst();
  }

  @Override
  public List<Blog> getBlogs() {
    return new ArrayList<>(blogs);
  }

  @Override
  public List<Blog> searchBlogsBy(BlogSearch search) {
    if (search.getKeywords() == null || search.getKeywords().isEmpty()) {
      return getBlogs();
    }

    return getBlogs()
        .stream()
        .filter(blog ->
            (blog.getTitle() != null && blog.getTitle().toLowerCase().contains(search.getKeywords()))
            || (blog.getContent() != null && blog.getContent().toLowerCase().contains(search.getKeywords()))
        )
        .collect(Collectors.toList());
  }

  @Override
  public Blog createBlog(Blog blog) {
    blogs.add(new Blog(idCursor.incrementAndGet(), blog.getTitle(), blog.getContent()));
    return blogs.get(blogs.size() - 1);
  }

  @Override
  public boolean updateBlog(long id, String title, String content) {
    Optional<Blog> optBlog = getBlogBy(id);

    optBlog.ifPresent(blog -> {
      blog.setTitle(title);
      blog.setContent(content);
    });

    return optBlog.isPresent();
  }

  @Override
  public boolean deleteBlog(long id) {
    OptionalInt index = IntStream.range(0, blogs.size())
        .filter(i -> blogs.get(i).getId() == id)
        .findFirst();

    index.ifPresent(i -> blogs.remove(i));

    return index.isPresent();
  }
}

