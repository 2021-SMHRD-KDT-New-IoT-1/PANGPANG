package upload;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class fileUploadMgr {
	
	private DBConnectionMgr pool;
	
	public static final String SAVEFOLDER = "C:\\Users\\smhrd\\Desktop\\Web\\app_server_test\\WebContent\\Videos";
	
	public static final String ENCODING = "UTF-8";
	
	public static final int MAXSIZE = 1280 * 720 * 50; // 50mb
	
	public fileUploadMgr() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public void uploadFile(HttpServletRequest req) {
		Connection con = null;
		PreparedStatement psmt = null;
		String sql = null;
		
		try {
			MultipartRequest multi = new MultipartRequest(req, 
					SAVEFOLDER, MAXSIZE, ENCODING, new DefaultFileRenamePolicy());
			String upFile = multi.getFilesystemName("video");
			
			File f = multi.getFile("video");
			long size = f.length();
			
			con = pool.getConnection();
			System.out.println(upFile);
			sql = "insert tbl_url(url_blackbox, url_size) values (?,?)";
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, upFile);
			psmt.setLong(2, size);	//int해도 관계는 없다.
			System.out.println(psmt.executeUpdate());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, psmt);
		}
		return;		
	}
	
	//파일리스트
		public Vector<fileloadBean> listFile(){
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			Vector<fileloadBean> vlist = new Vector<fileloadBean>();
			try {
				con = pool.getConnection();
				sql = "select * from tblFileload";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					fileloadBean bean = new fileloadBean();
					bean.setNum(rs.getInt(1));
					bean.setVideo(rs.getString(2));
					bean.setSize(rs.getInt(3));
					vlist.addElement(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return vlist;
		}

				
		//파일 가져오기
		public ArrayList<String> getFile() {
			
			ArrayList<String> Videos = new ArrayList<String>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String upFile=null;
			try {
				con = pool.getConnection();
				sql = "select url_blackbox from tbl_url";
				pstmt = con.prepareStatement(sql);
				// pstmt.setString(1, name);
				rs = pstmt.executeQuery();
				while (rs.next()){
				// if(rs.next()) 
					upFile=rs.getString(1);					
					Videos.add(upFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return Videos;
		}
		
		public int getLed() {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int ledOn = -1;
			try {
				con = pool.getConnection();
				sql = "select light_status from tbl_light";
				pstmt = con.prepareStatement(sql);				
				rs = pstmt.executeQuery();
				
				while (rs.next()){				 
					ledOn=rs.getInt(1);					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			return ledOn;
		}
		
		
	//파일삭제(파일삭제는 여러개를 동시에 삭제, 여러개라서 배열을 쓴다!!) db2 이용
//	public void deleteFile(int num[]) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		String sql = null;
//		try {
//			con = pool.getConnection();
//			//for문 이용
//			for (int i = 0; i < num.length; i++) {
//				//num에 맞는 파일명을 가져온다.
//				String upFile=getFile(num[i]);
//				//table에 있는 값만 삭제가 아니라 실제 파일도 같이 삭제를 해야한다.
//				File f=new File(SAVEFOLDER+upFile);
//				if(f.exists()) //파일이 존재한다면
//					f.delete();//파일을 삭제해라!!!
//				sql = "delete from tblFileload where num=?";
//				pstmt = con.prepareStatement(sql);
//				pstmt.setInt(1, num[i]);
//				pstmt.executeUpdate();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			pool.freeConnection(con, pstmt);
//		}
//		return;
//	}
				
	}
