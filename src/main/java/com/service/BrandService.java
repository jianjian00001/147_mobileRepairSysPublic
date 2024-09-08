package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Brand;
@Service("brandService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface BrandService {

	// 插入手机品牌表数据 调用brandDAO里的insertBrand配置
	public int insertBrand(Brand brand);

	// 更新手机品牌表数据 调用brandDAO里的updateBrand配置
	public int updateBrand(Brand brand);

	// 按主键删除手机品牌表数据 调用brandDAO里的deleteBrand配置
	public int deleteBrand(String brandid);

	// 批量删除手机品牌表数据 调用mapper包brand.xml里的deleteBrandByIds配置 返回值0(失败),大于0(成功)
	public int deleteBrandByIds(String[] ids);

	// 查询全部数据 调用brandDAO里的getAllBrand配置
	public List<Brand> getAllBrand();

	// 按照Brand类里面的字段名称精确查询 调用brandDAO里的getBrandByCond配置
	public List<Brand> getBrandByCond(Brand brand);

	// 按照Brand类里面的字段名称模糊查询 调用brandDAO里的getBrandByLike配置
	public List<Brand> getBrandByLike(Brand brand);

	// 按主键查询表返回单一的Brand实例 调用brandDAO里的getBrandById配置
	public Brand getBrandById(String brandid);

}

