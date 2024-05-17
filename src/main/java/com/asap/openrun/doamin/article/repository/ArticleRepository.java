package com.asap.openrun.doamin.article.repository;

import com.asap.openrun.doamin.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
