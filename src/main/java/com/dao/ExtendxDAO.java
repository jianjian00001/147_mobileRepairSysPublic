package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Extendx;

@Repository("extendxDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到ExtendxServiceImpl中
public interface ExtendxDAO {


	/**
	* ExtendxDAO 接口 可以按名称直接调用extendx.xml配置文件的SQL语句
	*/


	// 插入配件消耗表数据 调用mapper包extendx.xml里的insertExtendx配置 返回值0(失败),1(成功)
	public int insertExtendx(Extendx extendx);

	// 更新配件消耗表数据 调用mapper包extendx.xml里的updateExtendx配置 返回值0(失败),1(成功)
	public int updateExtendx(Extendx extendx);

	// 按主键删除配件消耗表数据 调用mapper包extendx.xml里的deleteExtendx配置 返回值0(失败),1(成功)
	public int deleteExtendx(String extendxid);

	// 批量删除配件消耗表数据 调用mapper包extendx.xml里的deleteExtendxByIds配置 返回值0(失败),大于0(成功)
	public int deleteExtendxByIds(String[] ids);

	// 查询配件消耗表全部数据 调用mapper包extendx.xml里的getAllExtendx配置 返回List<Extendx>类型的数据
	public List<Extendx> getAllExtendx();

	// 按照Extendx类里面的值精确查询 调用mapper包extendx.xml里的getExtendxByCond配置 返回List<Extendx>类型的数据
	public List<Extendx> getExtendxByCond(Extendx extendx);

	// 按照Extendx类里面的值模糊查询 调用mapper包extendx.xml里的getExtendxByLike配置 返回List<Extendx>类型的数据
	public List<Extendx> getExtendxByLike(Extendx extendx);

	// 按主键查询配件消耗表返回单一的Extendx实例 调用mapper包extendx.xml里的getExtendxById配置
	public Extendx getExtendxById(String extendxid);

}


