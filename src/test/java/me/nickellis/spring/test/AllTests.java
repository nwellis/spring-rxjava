package me.nickellis.spring.test;

import me.nickellis.spring.test.repo.BlogRepoTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    BlogRepoTests.class
})

public class AllTests {
}
