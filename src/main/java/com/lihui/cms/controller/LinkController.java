package com.lihui.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lihui.cms.domain.Link;
import com.lihui.cms.service.LinkService;

@Controller
@RequestMapping("link")
public class LinkController {

	
	@Autowired
	private LinkService linkService;
	
	@RequestMapping("selects")
	public Object selects(Model m,Link link,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		
		PageInfo<Link> page=linkService.selects(link,pageNum,pageSize);
		m.addAttribute("link", page.getList());
		m.addAttribute("text", link.getText());
		m.addAttribute("page", page);
		return "admin/link";
	}
	@ResponseBody
	@RequestMapping("selectLinkID")
	public Object selectLinkID(String id) {
		Link link=linkService.selectLinkID(id);
		return link;
	}
	@ResponseBody
	@RequestMapping("update")
	public Object update(Link link) {
		if(link.getText()==null || link.getText()=="") {
			return false;
		}
		if(link.getUrl()==null || link.getUrl()=="") {
			return false;
		}
		boolean flag=linkService.update(link);
		return flag;
	}
}
