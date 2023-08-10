package EL.WebProject.Clonestagram.StructMapper;

import EL.WebProject.Clonestagram.DTO.JoinInfo;
import EL.WebProject.Clonestagram.DTO.LoginInfo;
import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Domain.MemberDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    // joinMemberForm -> Member mapper 작성
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    ArrayList<Member> toMemberALDTO(ArrayList<MemberDomain> memberDomain);

    @Mapping(target = "userid", source = "userid")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    Member toMemberDTO(MemberDomain memberDomain);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    MemberDomain toMemberDomain(Member memberDTO);


    @Mapping(target = "email", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    Member toJoinMemberWithEmailDTO(JoinInfo joinInfo);


    @Mapping(target = "phoneNumber", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    Member toJoinMemberWithPhNumDTO(JoinInfo joinInfo);


    @Mapping(target = "email", source = "id")
    @Mapping(target = "password", source = "password")
    Member toLoginMemberWithEmailDTO(LoginInfo loginInfo);


    @Mapping(target = "phoneNumber", source = "id")
    @Mapping(target = "password", source = "password")
    Member toLoginMemberWithPhNumDTO(LoginInfo loginInfo);
}
