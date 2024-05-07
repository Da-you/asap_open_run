package com.asap.openrun.doamin.article.service;

import com.asap.openrun.doamin.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepo;

    @Transactional
    public void saveArticle(String email) {

    }

}
