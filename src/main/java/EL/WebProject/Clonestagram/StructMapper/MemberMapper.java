package EL.WebProject.Clonestagram.StructMapper;

import EL.WebProject.Clonestagram.DTO.Member;
import EL.WebProject.Clonestagram.Domain.JoinMemberForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    // joinMemberForm -> Member mapper 작성
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    ArrayList<Member> toMemberALDTO(ArrayList<JoinMemberForm> memberDomain);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    Member toMemberDTO(JoinMemberForm memberDomain);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", source = "password")
    JoinMemberForm toMemberDomain(Member memberDTO);
}
