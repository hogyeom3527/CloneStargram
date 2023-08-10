package EL.WebProject.Clonestagram.DAO.Repository.Interface;

public interface FileRepositoryInterface {
    String saveFileSrc(String src, String userid);

    String getFileSrc(String userid);
}
