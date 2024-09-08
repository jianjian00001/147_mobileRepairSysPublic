package com.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.Orders;

@Repository("ordersDAO") // Repository标签定义数据库连接的访问 Spring中直接扫描加载
@Mapper // 不需要在spring配置中设置扫描地址 spring将动态的生成Bean后注入到OrdersServiceImpl中
public interface OrdersDAO {


	/**
	* OrdersDAO 接口 可以按名称直接调用orders.xml配置文件的SQL语句
	*/


	// 插入维修订单表数据 调用mapper包orders.xml里的insertOrders配置 返回值0(失败),1(成功)
	public int insertOrders(Orders orders);

	// 更新维修订单表数据 调用mapper包orders.xml里的updateOrders配置 返回值0(失败),1(成功)
	public int updateOrders(Orders orders);

	// 按主键删除维修订单表数据 调用mapper包orders.xml里的deleteOrders配置 返回值0(失败),1(成功)
	public int deleteOrders(String ordersid);

	// 批量删除维修订单表数据 调用mapper包orders.xml里的deleteOrdersByIds配置 返回值0(失败),大于0(成功)
	public int deleteOrdersByIds(String[] ids);

	// 查询维修订单表全部数据 调用mapper包orders.xml里的getAllOrders配置 返回List<Orders>类型的数据
	public List<Orders> getAllOrders();

	// 按照Orders类里面的值精确查询 调用mapper包orders.xml里的getOrdersByCond配置 返回List<Orders>类型的数据
	public List<Orders> getOrdersByCond(Orders orders);

	// 按照Orders类里面的值模糊查询 调用mapper包orders.xml里的getOrdersByLike配置 返回List<Orders>类型的数据
	public List<Orders> getOrdersByLike(Orders orders);

	// 按主键查询维修订单表返回单一的Orders实例 调用mapper包orders.xml里的getOrdersById配置
	public Orders getOrdersById(String ordersid);

}


