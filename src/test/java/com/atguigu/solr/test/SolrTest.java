package com.atguigu.solr.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void hardQuery(){
		
		//1.指定solr的服务器地址你好
		String solrServerPathString = "http://192.168.6.237:8080/solr/collection1";
		
		//2.创建slorServer对象
		SolrServer server = new HttpSolrServer(solrServerPathString);
		
		//3.创建query查询对象
		SolrQuery query = new SolrQuery();
		
		//4.设置查询关键词，以及分页信息
		query.setQuery("人");
		query.setStart(0);
		query.setRows(7);
		
		//5.设置默认查询字段，以及设置高亮显示和高亮显示字段
		query.set("df", "item_song_name");
		query.setHighlight(true);//设置让其显示高亮
		
		//设置显示高亮的字段
		query.addHighlightField("item_song_name");
		query.addHighlightField("item_writer");
		query.addHighlightField("item_singer");
		
		//6.设置高亮标签
		//7.开始执行查询
		//8.获取文档列表
		//9.获取高亮显示的内容
		
	}
	
	
	/*先测试简单查询*/
	@Test
	public void solrSimplQuery() throws SolrServerException{
		
		//1.指定solr的服务器地址
		String solrServerPathString = "http://192.168.6.237:8080/solr/collection1";
		
		//2.创建slorServer对象
		SolrServer server = new HttpSolrServer(solrServerPathString);
		
		//3.创建query查询对象，设置查询的关键词
		SolrQuery query = new SolrQuery("爱我");
		
		//3.1设置默认的查询字段
		query.set("df", "item_song_name");
		
		//4.执行查询
		QueryResponse result = server.query(query);
		
		//5.获取查询到的文档的list集合
		SolrDocumentList results = result.getResults();
		
		//6.获取查询的结果数量
		long numFound = results.getNumFound();
		System.out.println("查询的数量：" + numFound);
		
		//7.遍历solrDocumentList对象
		for (SolrDocument solrDocument : results) {
			Object id = solrDocument.getFieldValue("id");
			System.out.println("id=" + id);
			
			Object fielname = solrDocument.getFieldValue("item_song_name");
			System.out.println("文件名：" + fielname);
			
			Object author = solrDocument.getFieldValue("item_singer");
			System.out.println("作者：" + author);
			
			Object lyric = solrDocument.getFieldValue("item_lyric");
			System.out.println("歌词：" + lyric);
		}
		
		
	}
	
	
	@Test
	public void solrDeletDocument() throws SolrServerException, IOException{
		//1.申明服務器地址
		String solrServerPath = "http://192.168.6.237:8080/solr/collection1";
		
		//2.创建solrServer对象
		SolrServer solrServer = new HttpSolrServer(solrServerPath);
		
		//3.删除的条件
		solrServer.deleteByQuery("item_singer:AAA");
		
		//4.提交
		solrServer.commit();
	}
	
	@Test
	public void solrAddDocument() throws SolrServerException, IOException{
		
		//1.申明服務器地址
		String solrServerPath = "http://192.168.6.237:8080/solr/collection1";
		
		//2.创建solrServer对象
		SolrServer solrServer = new HttpSolrServer(solrServerPath);
		
		//3.添加文档数据
		SolrInputDocument document1 = new SolrInputDocument();
		SolrInputDocument document2 = new SolrInputDocument();
		
		/*将字段值添加到对应字段中，id字段不需要注册，默认包含*/
		document1.addField("id", 24);
		document1.addField("item_song_name", "伤害我还说爱我");
		document1.addField("item_singer", "被伤害的人");
		document1.addField("item_lyric", "不知道你还记不记得那天的烟火坠落的很寂寞不知道你还爱不爱我只因那天的分手说的太坚决又到了 这个伤人的季节"
				+ "外面下着雪心却在滴着血回忆在一点一滴的堆叠为何对你的爱永远不磨灭多少爱能从来 抛开曾经伤害还可以肆无忌惮的爱 忘了未来多少爱能从来 当心以被撕开"
				+ "再痴心的等待 也拼不回破碎的爱又到了 这个伤人的季节外面下着雪心却在滴着血"
				+ "回忆在一点一滴的堆叠为何对你的爱永远不磨灭多少爱能从来 抛开曾经伤害还可以肆无忌惮的爱 忘了未来多少爱能从来 当心以被撕开在痴心的等待 也拼不回......"
				+ "多少爱能从来抛开曾经伤害还可以肆无忌惮的爱 忘了未来多少爱能从来 当心以被撕开在痴心的等待 也拼不回破碎的爱");
		
		
		document2.addField("item_song_name", "test SongName AAA");
		document2.addField("id", 44);
		document2.addField("item_singer", "test Singer AAA");
		document2.addField("item_lyric", "你的泪水最能让我认输我不在乎理智变盲目你灿烂的笑容都让我多嫉妒我为了你爱的多辛苦情到了深处原来悲喜");
		
		
		solrServer.add(document1);
		solrServer.add(document2);
		
		
		//4.提交修改
		solrServer.commit();
	}

}
