package com.al.blog.dao;

import com.al.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from t_blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);
    // ?1 代表第一个参数
    // 注意要手动拼接% %
    // select * from t_blog where title like '%query%'
    @Query("select b from t_blog b where b.title like ?1 or  b.content like ?1")
    Page<Blog>  findByQuery(String query,Pageable pageable);
    
    // 一定要加事务
    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id = ?1 ")
    int updateViews(Long id);
    
    // group by 不能用之前定义的year order by可以
    @Query("select function('date_format',b.updateTime,'%Y') as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupByYear();
    
    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') =?1")
    List<Blog> findByYear(String year);
}
