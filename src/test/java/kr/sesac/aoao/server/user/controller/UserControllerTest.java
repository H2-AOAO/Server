package kr.sesac.aoao.server.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.sesac.aoao.server.user.controller.dto.request.SignUpRequest;
import kr.sesac.aoao.server.user.domain.User;
import kr.sesac.aoao.server.user.service.UserService;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	// @InjectMocks
	// private UserController userController;

	@MockBean
	private UserService userService;

	@MockBean
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	// @BeforeEach
	// public void init(){
	// 	mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	// }

	@Test
	void signup_성공() throws Exception {
		// given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.email("test1234@gmail.com")
			.nickname("테스터")
			.password("test1234")
			.checkedPassword("test1234")
			.build();
		User response = User.from(signUpRequest, passwordEncoder);

		doReturn(response).when(userService)
			.signUp(any(SignUpRequest.class));

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest)) // Convert SignUpRequest to JSON
		);

		// then
		MvcResult mvcResult = (MvcResult)resultActions.andExpect(status().isCreated())
			// .andExpect(jsonPath("email", response.getEmail()).exists())
			// .andExpect(jsonPath("nickname", response.getNickname()).exists())
			.andReturn();
	}
}
