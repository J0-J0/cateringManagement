package com.jojo.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jojo.dao.DaoFactory;
import com.jojo.dao.FoodDao;
import com.jojo.util.DateUtil;

public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(500 * 1024);
		File repository = new File("D:\\Workspace\\test\\repository");
		factory.setRepository(repository);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setFileSizeMax(5 * 1024 * 1024);
		
		int foodId = 0;						// 数据准备
		String imageName = DateUtil.getCurrentDateStr();
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					foodId = Integer.parseInt(item.getString());
				} else {
					imageName = imageName + "." + item.getName().split("\\.")[1];
					item.write(new File("D:\\Workspace\\Ecplise\\cateringManagement\\WebContent\\images" + imageName));
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DaoFactory daoFactory = new DaoFactory();		// 网数据库填路径
		try {
			daoFactory.beginConnectionScope();
			daoFactory.beginTransaction();
			FoodDao foodDao = daoFactory.createFoodDao();
			
			foodDao.addFoodPic("images/"+imageName, foodId);
			daoFactory.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			daoFactory.abortTransaction();
		}finally{
			daoFactory.endConnectionScope();
		}
	}
}
