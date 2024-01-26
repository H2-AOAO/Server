package kr.sesac.aoao.server.dino.service;

import java.util.List;

import kr.sesac.aoao.server.dino.controller.dto.GetUserDinoResponse;
import kr.sesac.aoao.server.dino.controller.dto.request.ExpChangeRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.NewDinoRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.RenameRequest;
import kr.sesac.aoao.server.dino.controller.dto.request.UsePointRequest;
import kr.sesac.aoao.server.dino.controller.dto.response.DinoSimpleInfo;
import kr.sesac.aoao.server.dino.controller.dto.response.FriendDinoInfo;
import kr.sesac.aoao.server.user.jwt.UserCustomDetails;

public interface DinoService {
	GetUserDinoResponse getDinoInfo(UserCustomDetails userDetails);
	String renameDino(UserCustomDetails userDetails,RenameRequest name);
	GetUserDinoResponse expChange(UserCustomDetails userDetails, ExpChangeRequest currentExp);
	GetUserDinoResponse usePoint(UserCustomDetails userDetails, UsePointRequest useItem);
	Boolean newDino(UserCustomDetails userDetails, NewDinoRequest newDino);
	List<DinoSimpleInfo> userPastDino(UserCustomDetails userDetails);
	FriendDinoInfo friendDino(Long friendId);
}
