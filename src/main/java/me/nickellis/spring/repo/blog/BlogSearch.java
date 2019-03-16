package me.nickellis.spring.repo.blog;

public class BlogSearch {

  private String keywords;

  public BlogSearch(String keywords) {
    this.keywords = keywords;
  }

  public String getKeywords() {
    return keywords;
  }
}
