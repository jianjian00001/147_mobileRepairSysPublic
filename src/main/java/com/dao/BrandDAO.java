package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Brand;

@Repository("brandDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到BrandServiceImpl中
public interface BrandDAO {


	/**
	* BrandDAO 接口 可以按名称直接调用brand.xml配置文件的SQL语句
	*/


	// 插入手机品牌表数据 调用mapper包brand.xml里的insertBrand配置 返回值0(失败),1(成功)
	public int insertBrand(Brand brand);

	// 更新手机品牌表数据 调用mapper包brand.xml里的updateBrand配置 返回值0(失败),1(成功)
	public int updateBrand(Brand brand);

	// 按主键删除手机品牌表数据 调用mapper包brand.xml里的deleteBrand配置 返回值0(失败),1(成功)
	public int deleteBrand(String brandid);

	// 批量删除手机品牌表数据 调用mapper包brand.xml里的deleteBrandByIds配置 返回值0(失败),大于0(成功)
	public int deleteBrandByIds(String[] ids);

	// 查询手机品牌表全部数据 调用mapper包brand.xml里的getAllBrand配置 返回List<Brand>类型的数据
	public List<Brand> getAllBrand();

	// 按照Brand类里面的值精确查询 调用mapper包brand.xml里的getBrandByCond配置 返回List<Brand>类型的数据
	public List<Brand> getBrandByCond(Brand brand);

	// 按照Brand类里面的值模糊查询 调用mapper包brand.xml里的getBrandByLike配置 返回List<Brand>类型的数据
	public List<Brand> getBrandByLike(Brand brand);

	// 按主键查询手机品牌表返回单一的Brand实例 调用mapper包brand.xml里的getBrandById配置
	public Brand getBrandById(String brandid);

}


