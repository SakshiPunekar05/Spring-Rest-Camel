package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

	@Autowired
	private  ArticleRepository articleRepository;

	public Article generateArticle(Article article) {
		return articleRepository.save(article);
	}

	public Iterable<Article> findArticles() {
		return articleRepository.findAll();
	}

	public Article findArticle(Long id) {
		return articleRepository.findById(id).get();
	}
	
	public void deleteArticle(Long id) {
		articleRepository.deleteById(id);
	}
}
