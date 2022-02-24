package com.itheima.web.servlet;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;
import com.itheima.web.servlet.base.BaseServlet;


/**
 * 后台商品模块
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 跳转到添加的页面上
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用categoryservice 查询所有分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
		}
		return "/admin/product/add.jsp";
	}
	
	/**
	 * 展示已上架商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用service 查询已经上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			//将返回值放入request中,请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/product/list.jsp";
	}

	/**
	 * 后台根据商品id删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求参数，商品id
		String pid = request.getParameter("pid");
		//调用service 查询已经上架商品
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		// 调用service完成删除商品操作
		try {
			ps.deleteProductById(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
		return;
	}
	
	/**
	 * 后台根据商品id查找然后修改商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editfindProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到商品的id
		String pid = request.getParameter("pid");
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");	
		try {
			// 调用service层方法，通过id查找商品
			Product p = ps.editfindProductById(pid);
			request.setAttribute("list", p);
			request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台根据多条件查找商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单数据
		String pid = request.getParameter("pid"); // 商品id
		String pname = request.getParameter("pname"); // 商品名称
		String minprice = request.getParameter("minprice"); // 最小价格
		String maxprice = request.getParameter("maxprice"); // 最大价格
		ProductService service = (ProductService) BeanFactory.getBean("ProductService");	
		// 调用service层用于条件查询的方法
		List<Product> list;
		try {
			list = service.findproduct(pid, pname,minprice, maxprice);
			// 将条件查询的结果放进request域中
			request.setAttribute("list", list);
			// 请求重定向到商品管理首页list.jsp页面
			request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 创建javaBean,将上传数据封装.
		try {
			//使用fileuload保存图片和将商品的信息放入map中
			// 创建map 存放商品的信息
			Map<String,Object> map=new HashMap<>();
			// 创建磁盘文件项工厂 (设置临时文件的大小和位置)
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			//遍历list 获取每一个文件项
			for (FileItem fi : list) {
				//获取name属性值
				String key = fi.getFieldName();
				//判断是否是普通的上传组件
				if(fi.isFormField()){
					map.put(key, fi.getString("utf-8"));
				}else{
					//获取文件的名称  
					String name = fi.getName();
					//获取文件真实名称
					String realName = UploadUtils.getRealName(name);
					//获取文件的随机名称  
					String uuidName = UploadUtils.getUUIDName(realName);
					//获取随机目录 
					String dir = UploadUtils.getDir();
					//获取文件内容(输入流)
					InputStream is = fi.getInputStream();
					//创建输出流
					//获取products目录的真实路径
					String productPath = getServletContext().getRealPath("/products");
					//创建随机目录
					File dirFile = new File(productPath,dir);
					if(!dirFile.exists()){
						dirFile.mkdirs();
					}
					// d:/tomcat/webapps/store/prouduct/a/3/12312312434234.jpg
					FileOutputStream os = new FileOutputStream(new File(dirFile,uuidName));
					//对拷流
					IOUtils.copy(is, os);
					//释放资源
					os.close();
					is.close();
					//删除临时文件
					fi.delete();
					//将商品的路径放入map中   prouduct/a/3/12312312434234.jpg
					map.put(key, "products"+dir+"/"+uuidName);
				}
			}
			//封装product对象
			Product p = new Product();
			String pid = request.getParameter("pid");
			p.setPid(pid);
			//手动设置 pid
			map.put("pid",pid);
			//手动设置 pdate
			map.put("pdate", new Date());
			//手动设置 pflag  上架
			map.put("pflag", Constant.PRODUCT_IS_UP);
			//使用beanutils封装
			BeanUtils.populate(p, map);
			//手动设置 category
			String cid = request.getParameter("cid");
			Category c = new Category();
			c.setCid(cid);
			p.setCategory(c);
			//调用service 完成保存
			ProductService service = (ProductService) BeanFactory.getBean("ProductService");	
			try {
				service.editproduct(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改商品失败");
		}
		
	}
	
	
}
