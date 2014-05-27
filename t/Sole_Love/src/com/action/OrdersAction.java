package com.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.model.Orderdetails;
import com.model.Orders;
import com.model.Products;
import com.model.Users;
import com.service.OrderdetailsService;
import com.service.OrdersService;
import com.service.ProductsService;

@Entity
@Controller
@Scope("prototype")
public class OrdersAction {

	@Resource
	OrdersService ordersService;
	@Resource
	OrderdetailsService orderdetailsService;
	@Resource
	ProductsService productsService;

	

	// ��������
	private String result;

	/**
	 * �ύ����
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue
	int pid;// ��Ʒid
	int count;// ������Ʒ����
	String city; // ���ͳ���
	String zip;// ��������
	String address;// ���͵�ַ
	String name;// �ռ�������
	String code;// �ײ�ʹ���Żݴ���
	String message;// ����
	String express;// ��ݹ�˾����
	float express_fee;// ��ݷ�
	List<Products> products;
	public String addOrdersInfo() {
		//System.out.println("---------------------------------------------------");
		for (int i = 0; i < products.size(); i++) {
			System.out.println(products.get(i));
		}
		Orders orders = new Orders();
		List<Orders> list = new ArrayList<Orders>();
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");// ȡ��session�е�User
		if (user != null) {// �ж��û��Ƿ��¼
			orders.setUid(user.getId());
			orders.setCity(city);
			orders.setZip(zip);
			orders.setAddress(address);
			orders.setName(name);
			orders.setCode(code);
			orders.setMessage(message);
			orders.setExpress(express);
			orders.setExpressFee(express_fee);
			orders = ordersService.addOrdersInfo(orders);// �õ����ι��򶩵�
			//List<Orderdetails> oList = orderdetailsService.addOrderdetailsInfo(orders.getId(), products, count);// ���ι��򶩵�����

			JSONArray ja = new JSONArray();
			ja.add(list);
			//ja.add(oList);
			StringBuffer sb = new StringBuffer();
			sb.append(ja.toString());
			sb.append("[{\"message\":\"�޴���\"}]");
			result = sb.toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}

	/**
	 * ��ѯ�û����ö���
	 * 
	 * @return
	 */
	public String findAllOrdersInfo() {
		Users user = (Users) ServletActionContext.getRequest().getSession()
				.getAttribute("User");
		if (user != null) {
			result = orderdetailsService.findOrderdetailsInfo(user.getId()).toString();
			return "success";
		}
		result = "[{\"message\":\"�û�δ��¼\"}]";
		return "success";
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public OrderdetailsService getOrderdetailsService() {
		return orderdetailsService;
	}

	public void setOrderdetailsService(OrderdetailsService orderdetailsService) {
		this.orderdetailsService = orderdetailsService;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductsService getProductsService() {
		return productsService;
	}

	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public float getExpress_fee() {
		return express_fee;
	}

	public void setExpress_fee(float express_fee) {
		this.express_fee = express_fee;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

}
