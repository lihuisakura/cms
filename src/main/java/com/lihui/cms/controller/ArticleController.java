package com.lihui.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Article;
import com.lihui.cms.service.ArticleService;


@Controller
@RequestMapping("article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 
	 * @Title: selects 
	 * @Description: 查询文章列表，实现分页
	 * @param m
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selects")
	public Object selects(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {

		PageInfo<Article> page = articleService.getArticleList(article.getTitle(), article.getStatus(), pageNum,
				pageSize);

		m.addAttribute("page", page);
		m.addAttribute("title", article.getTitle());
		m.addAttribute("status", article.getStatus());
		m.addAttribute("list", page.getList());

		return "admin/article";
	}

	/**
	 * 
	 * @Title: selectArticle 
	 * @Description: 根据id查看文章详情
	 * @param id
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selectArticle")
	@ResponseBody
	public Object selectArticle(String id) {
		Article article = articleService.selectArticle(id);
		return article;
	}

	/**
	 * 
	 * @Title: updateStatus 
	 * @Description: 修改文章状态
	 * @param status
	 * @param id
	 * @return
	 * @return: Object
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public Object updateStatus(String status, String id) {
		int i = articleService.updateStatus(status, id);
		return i >= 1 ? true : false;
	}

	/**
	 * 
	 * @Title: selectArticleList 
	 * @Description: TODO
	 * @param m
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: Object
	 */
	@RequestMapping("selectArticleList")
	public Object selectArticleList(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {

		PageInfo<Article> page = articleService.getArticleList(article.getTitle(), article.getStatus(), pageNum,
				pageSize);

		m.addAttribute("page", page);
		m.addAttribute("title", article.getTitle());
		m.addAttribute("status", article.getStatus());
		m.addAttribute("list", page.getList());
		return "my/article";
	}

	/**
	 * 
	 * @Title: toAdd 
	 * @Description: TODO
	 * @return
	 * @return: Object
	 */
	@RequestMapping("toAdd")
	public Object toAdd() {
		return "my/release";
	}

}
