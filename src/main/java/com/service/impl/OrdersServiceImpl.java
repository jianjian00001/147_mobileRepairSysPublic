package com.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.OrdersDAO;
import com.entity.Orders;
import com.service.OrdersService;

@Service("ordersService") //
public class OrdersServiceImpl implements OrdersService { // OrdersServiceImpl 实现了 OrdersService接口

	@Autowired // 它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作
	private OrdersDAO ordersDAO; // 实现Spring的AOP依赖注入 自动注入AdminDAO接口

	@Override // 继承接口的新增维修订单表数据 返回值0(失败),1(成功)
	public int insertOrders(Orders orders) {
		return this.ordersDAO.insertOrders(orders);
	}

	@Override // 继承接口的更新维修订单表数据 返回值0(失败),1(成功)
	public int updateOrders(Orders orders) {
		return this.ordersDAO.updateOrders(orders);
	}

	@Override // 继承接口的按主键删除维修订单表数据 返回值0(失败),1(成功)
	public int deleteOrders(String ordersid) {
		return this.ordersDAO.deleteOrders(ordersid);
	}

	@Override // 继承接口的批量删除维修订单表数据 返回值0(失败),大于0(成功)
	public int deleteOrdersByIds(String[] ids) {
		return this.ordersDAO.deleteOrdersByIds(ids);
	}

	@Override // 继承接口的查询维修订单表全部数据
	public List<Orders> getAllOrders() {
		return this.ordersDAO.getAllOrders();
	}

	@Override // 继承接口的按条件精确查询维修订单表数据
	public List<Orders> getOrdersByCond(Orders orders) {
		return this.ordersDAO.getOrdersByCond(orders);
	}

	@Override // 继承接口的按条件模糊查询维修订单表数据
	public List<Orders> getOrdersByLike(Orders orders) {
		return this.ordersDAO.getOrdersByLike(orders);
	}

	@Override // 继承接口的按主键查询维修订单表数据 返回Entity实例
	public Orders getOrdersById(String ordersid) {
		return this.ordersDAO.getOrdersById(ordersid);
	}

}

