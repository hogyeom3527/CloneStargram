package EL.WebProject.Clonestagram.DAO.Repository.Interface;

import EL.WebProject.Clonestagram.DTO.ProfileImg;

public interface FileRepositoryInterface {
    String saveFileSrc(String src, String userid);

    String getFileSrc(String userid);
}
