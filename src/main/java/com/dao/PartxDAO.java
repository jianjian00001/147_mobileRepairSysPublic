package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Partx;

@Repository("partxDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到PartxServiceImpl中
public interface PartxDAO {


	/**
	* PartxDAO 接口 可以按名称直接调用partx.xml配置文件的SQL语句
	*/


	// 插入手机配件表数据 调用mapper包partx.xml里的insertPartx配置 返回值0(失败),1(成功)
	public int insertPartx(Partx partx);

	// 更新手机配件表数据 调用mapper包partx.xml里的updatePartx配置 返回值0(失败),1(成功)
	public int updatePartx(Partx partx);

	// 按主键删除手机配件表数据 调用mapper包partx.xml里的deletePartx配置 返回值0(失败),1(成功)
	public int deletePartx(String partxid);

	// 批量删除手机配件表数据 调用mapper包partx.xml里的deletePartxByIds配置 返回值0(失败),大于0(成功)
	public int deletePartxByIds(String[] ids);

	// 查询手机配件表全部数据 调用mapper包partx.xml里的getAllPartx配置 返回List<Partx>类型的数据
	public List<Partx> getAllPartx();

	// 按照Partx类里面的值精确查询 调用mapper包partx.xml里的getPartxByCond配置 返回List<Partx>类型的数据
	public List<Partx> getPartxByCond(Partx partx);

	// 按照Partx类里面的值模糊查询 调用mapper包partx.xml里的getPartxByLike配置 返回List<Partx>类型的数据
	public List<Partx> getPartxByLike(Partx partx);

	// 按主键查询手机配件表返回单一的Partx实例 调用mapper包partx.xml里的getPartxById配置
	public Partx getPartxById(String partxid);

}


