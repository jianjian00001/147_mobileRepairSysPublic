package com.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.entity.Orders;
@Service("ordersService") // 自动注册到Spring容器，不需要再在xml文件定义bean
public interface OrdersService {

	// 插入维修订单表数据 调用ordersDAO里的insertOrders配置
	public int insertOrders(Orders orders);

	// 更新维修订单表数据 调用ordersDAO里的updateOrders配置
	public int updateOrders(Orders orders);

	// 按主键删除维修订单表数据 调用ordersDAO里的deleteOrders配置
	public int deleteOrders(String ordersid);

	// 批量删除维修订单表数据 调用mapper包orders.xml里的deleteOrdersByIds配置 返回值0(失败),大于0(成功)
	public int deleteOrdersByIds(String[] ids);

	// 查询全部数据 调用ordersDAO里的getAllOrders配置
	public List<Orders> getAllOrders();

	// 按照Orders类里面的字段名称精确查询 调用ordersDAO里的getOrdersByCond配置
	public List<Orders> getOrdersByCond(Orders orders);

	// 按照Orders类里面的字段名称模糊查询 调用ordersDAO里的getOrdersByLike配置
	public List<Orders> getOrdersByLike(Orders orders);

	// 按主键查询表返回单一的Orders实例 调用ordersDAO里的getOrdersById配置
	public Orders getOrdersById(String ordersid);

}

