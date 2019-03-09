package me.nickellis.spring.repo.blog;

import java.util.Objects;

public class Blog {

  private final long id;
  private String title;
  private String content;

  public Blog(long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Blog blog = (Blog) o;
    return id == blog.id &&
        Objects.equals(title, blog.title) &&
        Objects.equals(content, blog.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content);
  }

  @Override
  public String toString() {
    return "Blog{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
