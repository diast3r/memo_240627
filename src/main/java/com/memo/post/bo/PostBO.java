package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

//@Slf4j // lombok의 @Slf4j 어노테이션
@Service
public class PostBO {
	// private Logger log = LoggerFactory.getLogger(PostBO.class); // org.slf4j.Logger;
	private Logger log = LoggerFactory.getLogger(this.getClass()); // 클래스 명을 변경하지 않고 유동적으로 사용 가능
	
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	
	// input: userId(컨트롤러가 세션에서 꺼내서), userLoginId(컨트롤러가 세션에서 꺼내서), subject, content, file => imgPath
	// output: int(성공한 행 개수)
	public int addPost(int userId, String userLoginId, 
			String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		
		// file to imagePath
		// file이 있을 때에만 업로드 => 있을 시 imagePath를 얻어냄
		// imagePath를 얻는 코드는 common 패키지 안에 클래스를 만들어서 여러 곳에서 가져다 쓸 것임.
		if (file != null) {
			imagePath = fileManager.uploadFile(file, userLoginId);
		}
		
		return postMapper.insertPost(userId, subject, content, imagePath);
		
	}
	
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId);
	}
	
	
	/**
	 * post 업데이트<br>
	 * 
	 * @param loginId
	 * @param postId
	 * @param userId
	 * @param subject
	 * @param content
	 * @param file
	 */
	// input: userLoginId(파일), postId, userId, subject, content, file
	// output: X
	public void updatePostByPostIdUserId(String loginId, int postId, int userId, 
			String subject, String content, MultipartFile file) {
		
		// 기존 글 가져오기
		// 이유 1) 이미지 교체 시 기존 이미지 삭제를 위해
		// 이유 2) 업데이트 대상 존재 확인
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		// 가져온 post가 null일 수 있으므로 검증 
		if (post == null) {
			log.info("[글 수정] post is null. postId:{}, usreId:{}", postId, userId);
			return;
		}
		
		
		log.info("[글 수정 테스트] post is null. postId:{}, usreId:{}", postId, userId);
		// 업로드할 파일이 존재 시 새 이미지 업로드
		String imagePath = null;
		if (file != null) {
			// 새 이미지 업로드
			// 업로드 성공하면 기존 이미지 삭제
			imagePath = fileManager.uploadFile(file, loginId);
			
			// 업로드 성공(imagePath != null) && 기존 이미지 존재 시 삭제
			if (imagePath != null && post.getImagePath() != null) {
				// 기존 폴더, 이미지 제거(서버에 업로드된)
				fileManager.deleteFile(post.getImagePath());
			}
			
		} else {
			
		}
		
		
		// DB 업데이트
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	
	// 글 삭제
	// input: postId, userId(글쓴이가 맞는지 인증하기 위해) 
	// output: X
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존 글을 가져오기 (postId, userId) => 가져와진다? 글쓴이인 것이 인증된 거나 마찬가지.
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			log.info("[글 삭제] post is null. postId:{}, userId:{]", postId, userId);
		}
		
		// DB행 삭제
		int rowCount = postMapper.deletePostByPostId(postId);
		
		// 기존 글에 이미지가 있다면 폴더/파일 삭제
		if (rowCount > 0 && post.getImagePath() != null) {
			// DB 삭제 완료 && 기존글 이미지 존재 => 이미지 삭제 
			fileManager.deleteFile(post.getImagePath());
		}
	}
	
}
