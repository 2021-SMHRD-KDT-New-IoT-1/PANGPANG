package upload;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/videoCont")
public class videoCont extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		fileUploadMgr fileMgr = new fileUploadMgr();
		
		ArrayList<String> blackVideo = fileMgr.getFile();
		
		request.setAttribute("video", blackVideo);

		RequestDispatcher rd = request.getRequestDispatcher("videoList.jsp");	
		
		// 페이지를 이동할 시 request, response객체 전달
		rd.forward(request, response); // 현재 사용한 것을 그대로 넘겨줌	
			
	}
}
